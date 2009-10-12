package de.ingrid.iplug.excel.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.model.Values;

@Service
public class SheetsService {

	private final SheetService _sheetService;

	@Autowired
	public SheetsService(SheetService sheetService) {
		_sheetService = sheetService;
	}

	public Sheets createSheets(File file) throws IOException {
		return createSheets(new FileInputStream(file));

	}

	private void filter(Sheet sheet) {
		filterEmptyRows(sheet);
		filterEmptyColumns(sheet);
	}

	private void filterEmptyColumns(Sheet sheet) {
		List<Row> rows = sheet.getRows();
		List<Column> columns = sheet.getColumns();
		Values values = sheet.getValues();
		BitSet bitSet = new BitSet();
		for (Column column : columns) {
			boolean empty = true;
			for (Row row : rows) {
				Serializable value = values.getValue(column.getIndex(), row
						.getIndex());
				if (value != null && !value.toString().equals("")) {
					empty = false;
				}
			}
			bitSet.set(column.getIndex(), empty);
		}
		for (int i = 0; i < bitSet.size(); i++) {
			if (bitSet.get(i)) {
				_sheetService.excludeColumn(sheet, i);
			}
		}
	}

	private void filterEmptyRows(Sheet sheet) {
		List<Row> rows = sheet.getRows();
		List<Column> columns = sheet.getColumns();
		Values values = sheet.getValues();
		BitSet bitSet = new BitSet();
		for (Row row : rows) {
			boolean empty = true;
			for (Column column : columns) {
				Serializable value = values.getValue(column.getIndex(), row
						.getIndex());
				if (value != null && !value.toString().equals("")) {
					empty = false;
				}
			}
			bitSet.set(row.getIndex(), empty);
		}
		for (int i = 0; i < bitSet.size(); i++) {
			if (bitSet.get(i)) {
				_sheetService.excludeRow(sheet, i);
			}
		}

	}

	public Sheets createSheets(byte[] uploadBytes) throws IOException {
		return createSheets(new ByteArrayInputStream(uploadBytes));
	}

	private Sheets createSheets(InputStream inputStream) throws IOException {
		Sheets sheets = new Sheets();

		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			HSSFSheet hssfSheet = workbook.getSheetAt(i);
			// create sheet
			Sheet sheet = new Sheet();
			sheet.setSheetIndex(i);
			sheets.addSheet(sheet);
			Values values = new Values();
			sheet.setValues(values);

			Iterator<HSSFRow> rowIterator = hssfSheet.rowIterator();
			int rowCounter = 0;
			while (rowIterator.hasNext()) {
				HSSFRow hssfRow = (HSSFRow) rowIterator.next();

				// create excel row
				Row row = new Row(rowCounter);
				row.setIndex(rowCounter);

				// add row
				sheet.addRow(row);

				// create values
				Iterator<HSSFCell> cellIterator = hssfRow.cellIterator();
				int columnCount = 0;
				while (cellIterator.hasNext()) {
					HSSFCell cell = cellIterator.next();

					// create column
					if (rowCounter == 0) {
						Column column = new Column(columnCount);
						sheet.addColumn(column);
					}

					de.ingrid.iplug.excel.model.Point point = new de.ingrid.iplug.excel.model.Point(
							columnCount, rowCounter);
					// TODO handle different values for example: formula, number
					// TODO set filename
					// etc
					values.addValue(point, cell.toString());
					columnCount++;
				}
				rowCounter++;
			}
		}

		List<Sheet> sheetsList = sheets.getSheets();
		for (Sheet sheet : sheetsList) {
			filter(sheet);
		}

		return sheets;
	}
}
