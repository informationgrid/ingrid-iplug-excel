package de.ingrid.iplug.excel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

@Controller
@RequestMapping(value = "/iplug/upload.html")
@SessionAttributes(value = { "uploadBean", "tableListCommand" })
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
		List<TableCommand> list = new ArrayList<TableCommand>();
		for (int i = 0; i < numberOfSheets; i++) {
			HSSFSheet sheet = workbook.getSheetAt(i);
			TableCommand tableCommand = new TableCommand();
			tableCommand.setHead(new TableHeadCommand());
			list.add(tableCommand);
			Iterator<HSSFRow> rowIterator = sheet.rowIterator();

			int columnCount = 0;
			while (rowIterator.hasNext()) {
				HSSFRow row = (HSSFRow) rowIterator.next();
				RowCommand rowCommand = new RowCommand();
				tableCommand.addRow(rowCommand);
				Iterator<HSSFCell> cellIterator = row.cellIterator();
				int counter = 0;
				while (cellIterator.hasNext()) {
					counter++;
					HSSFCell cell = cellIterator.next();
					rowCommand.addCell(cell.toString());
				}
				columnCount = columnCount < counter ? counter : columnCount;
			}
			for (int j = 0; j < columnCount; j++) {
				tableCommand.getHead().addHeader("Index_Field_" + j);
			}
		}
		model.addAttribute("tableListCommand", new TableListCommand(list));

		return "redirect:/iplug/settings.html";

	}

}
