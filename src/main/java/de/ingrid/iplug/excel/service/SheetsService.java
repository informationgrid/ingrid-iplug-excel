package de.ingrid.iplug.excel.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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
						value = new Double(poiCell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						value = poiCell.getStringCellValue();
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

	public static void main(String[] args) {
		Comparable<? extends Serializable> c = 1;
		Comparable<Object> c1 = null;
		c1.compareTo(c);

	}
}
