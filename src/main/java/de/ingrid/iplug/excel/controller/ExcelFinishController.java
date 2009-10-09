package de.ingrid.iplug.excel.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.command.PlugdescriptionCommandObject;
import de.ingrid.iplug.excel.UploadBean;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;

@Controller
@SessionAttributes(value = { "plugDescription", "sheets", "uploadBean" })
@RequestMapping("/iplug/finish.html")
public class ExcelFinishController {

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public String postFinish(
			@ModelAttribute("plugDescription") PlugdescriptionCommandObject plugdescriptionCommandObject,
			@ModelAttribute("sheets") de.ingrid.iplug.excel.model.Sheets sheets,
			@ModelAttribute("uploadBean") UploadBean uploadBean)
			throws IOException {

		Sheet sheet = sheets.getSheets().get(0);
		List<Column> columns = sheet.getColumns();
		for (Column column : columns) {
			String label = column.getLabel();
			plugdescriptionCommandObject.addToList("fields", label);
		}
		if (!plugdescriptionCommandObject.containsKey("sheets")) {
			plugdescriptionCommandObject.put("sheets", new Sheets());
		}
		Sheets savedSheets = (Sheets) plugdescriptionCommandObject
				.get("sheets");
		savedSheets.addSheet(sheet);
		plugdescriptionCommandObject.setRecordLoader(false);
		File workinDirectory = plugdescriptionCommandObject
				.getWorkinDirectory();
		workinDirectory.mkdirs();
		byte[] workBookBytes = sheet.getWorkbook();
		FileOutputStream outputStream = new FileOutputStream(new File(
				workinDirectory, sheet.getFileName()));
		outputStream.write(workBookBytes);
		outputStream.close();
		return "redirect:/base/save.html";
	}

	public static void main(String[] args) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(
				"/tmp/excel/bsp2.xls")));
		System.out.println(workbook.getSummaryInformation());
	}
}
