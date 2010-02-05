package de.ingrid.iplug.excel.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import de.ingrid.admin.command.PlugdescriptionCommandObject;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Point;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.model.Values;

public class SheetsService {

    public static Sheets loadSheets(final PlugdescriptionCommandObject plugDescription, final int sheetIndex)
            throws IOException {
        // get sheet
        final Sheets sheets = (Sheets) plugDescription.get("sheets");
        final Sheet sheet = sheets.getSheets().get(sheetIndex);

        // load sheet
        final File workinDirectory = plugDescription.getWorkinDirectory();
        final File mappingDir = new File(workinDirectory, "mapping");
        final File file = new File(mappingDir, sheet.getFileName());
        final Sheets loadedSheets = SheetsService.createSheets(file);

        // add required params
        for (final Sheet loadedSheet : loadedSheets) {
            loadedSheet.setFileName(file.getName());
        }

        return loadedSheets;
    }

    public static Sheet loadSheet(final PlugdescriptionCommandObject plugDescription, final int sheetIndex)
            throws IOException {
        // get sheet
        final Sheets sheets = (Sheets) plugDescription.get("sheets");
        final Sheet sheet = sheets.getSheets().get(sheetIndex);

        // load sheet
        final File workinDirectory = plugDescription.getWorkinDirectory();
        final File mappingDir = new File(workinDirectory, "mapping");
        final File file = new File(mappingDir, sheet.getFileName());
        final Sheets loadedSheets = SheetsService.createSheets(file);
        final Sheet loadedSheet = loadedSheets.getSheet(sheet.getSheetIndex());
        sheet.setValues(loadedSheet.getValues());
        return sheet;
    }

    public static Sheets createSheets(final File file) throws IOException {
        return createSheets(new FileInputStream(file));
	}

    public static Sheets createSheets(final MultipartFile multipartFile) throws IOException {
        final byte[] bytes = multipartFile.getBytes();
        final Sheets sheets = createSheets(new ByteArrayInputStream(bytes));
        for (final Sheet sheet : sheets) {
            sheet.setFileName(multipartFile.getOriginalFilename());
            sheet.setWorkbook(bytes);
        }
        return sheets;
	}

    public static Sheets createSheets(final InputStream inputStream) throws IOException {
        // sheets
        final Sheets sheets = new Sheets();
        // create workbook
        final Workbook workbook = new HSSFWorkbook(inputStream);
        final FormulaEvaluator eval = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            final org.apache.poi.ss.usermodel.Sheet poiSheet = workbook.getSheetAt(sheetNum);
			// ingrid sheet
            final Sheet sheet = new Sheet();
			sheet.setSheetIndex(sheetNum);
			sheets.addSheet(sheet);
            final Values values = new Values();
			sheet.setValues(values);
            for (final org.apache.poi.ss.usermodel.Row poiRow : poiSheet) {
                boolean hasValues = false;
                final Map<Point, Comparable<? extends Object>> valuesInCell = new HashMap<Point, Comparable<? extends Object>>();
				for (final Cell poiCell : poiRow) {

					Comparable<? extends Object> value = null;
					switch (poiCell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						value = new Boolean(poiCell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(poiCell)) {
							value = getFormattedDateString(poiCell);
                        } else {
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
                    // trim strings
                    if (value instanceof String) {
                        value = ((String) value).trim();
                    }
                    // only add if at least one value does exist in row
                    if (!value.equals("")) {
                        hasValues = true;
                        // ingrid column
                        if (sheet.getColumn(poiCell.getColumnIndex()) == null) {
                            final Column column = new Column(poiCell.getColumnIndex());
                            sheet.addColumn(column);
                        }
                    }

    				// ingrid point and value
                    final Point point = new Point(poiCell.getColumnIndex(), poiCell.getRowIndex());
                    valuesInCell.put(point, value);
                }
                // ingrid row
                // ! only add if at least one value does exist
                if (hasValues) {
                    final Row row = new Row(poiRow.getRowNum());
                    sheet.addRow(row);
                    for (final Point point : valuesInCell.keySet()) {
                        //
                        if (sheet.getColumn(point.getX()) != null) {
                            values.addValue(point, valuesInCell.get(point));
                        }
                    }
				}
			}
		}

		return sheets;
	}

    private static Comparable<? extends Object> calculateFormula(final Cell poiCell, final FormulaEvaluator eval) {
		Comparable<? extends Object> ret = null;
		final int type = eval.evaluateFormulaCell(poiCell);
		switch (type) {
        case Cell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(poiCell)) {
                ret = getFormattedDateString(poiCell);
            } else {
                ret = poiCell.getNumericCellValue();
            }
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            ret = poiCell.getBooleanCellValue();
            break;
        case Cell.CELL_TYPE_BLANK:
        case Cell.CELL_TYPE_ERROR:
        case Cell.CELL_TYPE_FORMULA:
        case Cell.CELL_TYPE_STRING:
        default:
            ret = poiCell.getStringCellValue();
		}
		return ret;
	}

    private static String getFormattedDateString(final Cell cell) {
		final Date date = cell.getDateCellValue();
		final SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" );
		final String formatedDate = formatter.format(date);
		return formatedDate;
	}

}
