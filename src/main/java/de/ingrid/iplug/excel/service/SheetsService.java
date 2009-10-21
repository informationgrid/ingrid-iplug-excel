package de.ingrid.iplug.excel.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Point;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.model.Values;

@Service
public class SheetsService {

	public Sheets createSheets(File file) throws IOException {
		return createSheets(new FileInputStream(file));
	}

	public Sheets createSheets(byte[] uploadBytes) throws IOException {
		return createSheets(new ByteArrayInputStream(uploadBytes));
	}

	private Sheets createSheets(InputStream inputStream) throws IOException {
		Sheets sheets = new Sheets();

		Workbook workbook = new HSSFWorkbook(inputStream);
		FormulaEvaluator eval = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
			org.apache.poi.ss.usermodel.Sheet poiSheet = workbook
					.getSheetAt(sheetNum);
			// ingrid sheet
			Sheet sheet = new Sheet();
			sheet.setSheetIndex(sheetNum);
			sheets.addSheet(sheet);
			Values values = new Values();
			sheet.setValues(values);
			for (org.apache.poi.ss.usermodel.Row poiRow : poiSheet) {
				// ingrid row
				Row row = new Row(poiRow.getRowNum());
				sheet.addRow(row);
				for (Cell poiCell : poiRow) {
					// ingrid column
					if (poiRow.getRowNum() == 0) {
						Column column = new Column(poiCell.getColumnIndex());
						sheet.addColumn(column);
					}

					Comparable<? extends Object> value = null;
					switch (poiCell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						value = new Boolean(poiCell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(poiCell)) {
							value = getFormattedDateString(poiCell);
						}else{
							value = new Double(poiCell.getNumericCellValue());
						}
						break;
					case Cell.CELL_TYPE_STRING:
						value = poiCell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_FORMULA:
						value = calculateFormula(poiCell, eval);
						break;
					default:
						value = "";
						break;
					}

					// ingrid point and value
					Point point = new Point(poiCell.getColumnIndex(), poiCell
							.getRowIndex());
					values.addValue(point, value);

				}
			}
		}

		return sheets;
	}
	
	private Comparable<? extends Object> calculateFormula(Cell poiCell, FormulaEvaluator eval){
		Comparable<? extends Object> ret = null;
		int type = eval.evaluateFormulaCell(poiCell);
		switch (type) {
			case Cell.CELL_TYPE_NUMERIC :
				if (DateUtil.isCellDateFormatted(poiCell)) {
					ret = getFormattedDateString(poiCell);
				}else{
					ret = poiCell.getNumericCellValue();
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN :
				ret =  poiCell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_BLANK :
			case Cell.CELL_TYPE_ERROR:
			case Cell.CELL_TYPE_FORMULA:
			case Cell.CELL_TYPE_STRING :
			default:
				ret = poiCell.getStringCellValue();
		} 
		return ret;
	}
	
	private String getFormattedDateString(Cell cell) {
		Date date = cell.getDateCellValue();
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" );
		String formatedDate = formatter.format(date);
		return formatedDate;
	} 

}
