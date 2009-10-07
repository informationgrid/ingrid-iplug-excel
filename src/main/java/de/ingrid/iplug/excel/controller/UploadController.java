package de.ingrid.iplug.excel.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import de.ingrid.iplug.excel.UploadBean;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.model.Values;

@Controller
@RequestMapping(value = "/iplug/upload.html")
@SessionAttributes(value = { "uploadBean", "sheets" })
public class UploadController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET)
	public String firstRow() {
		return "/iplug/upload";
	}

	@ModelAttribute("uploadBean")
	public UploadBean injectUploadBean() {
		return new UploadBean();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String upload(@ModelAttribute("uploadBean") UploadBean uploadBean,
			Model model) throws IOException {
		byte[] bytes = uploadBean.getFile();
		HSSFWorkbook workbook = new HSSFWorkbook(
				new ByteArrayInputStream(bytes));
		int numberOfSheets = workbook.getNumberOfSheets();
		Sheets sheets = new Sheets();
		for (int i = 0; i < numberOfSheets; i++) {
			HSSFSheet hssfSheet = workbook.getSheetAt(i);
			Iterator<HSSFRow> rowIterator = hssfSheet.rowIterator();

			// create sheet
			Sheet sheet = new Sheet();
			sheets.addSheet(sheet);

			Values values = new Values();
			sheet.setValues(values);

			int rowCounter = 0;
			while (rowIterator.hasNext()) {
				HSSFRow hssfRow = (HSSFRow) rowIterator.next();

				// create excel row
				Row row = new Row(values, rowCounter);
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
						Column column = new Column(values, columnCount);
						column.setIndex(columnCount);
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
		model.addAttribute("sheets", sheets);
		
		if(sheets.getSheets().size() == 1){
			return "redirect:/iplug/settings.html";
		}

		return "redirect:/iplug/previewExcelFile.html";

	}

}
