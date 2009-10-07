package de.ingrid.iplug.excel.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.command.PlugdescriptionCommandObject;
import de.ingrid.iplug.excel.UploadBean;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Sheet;

@Controller
@SessionAttributes(value = { "plugDescription", "sheets", "uploadBean" })
@RequestMapping("/iplug/finish.html")
public class ExcelFinishController {

	@RequestMapping(method = RequestMethod.GET)
	public String finish() {
		return "/iplug/finish";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postFinish(
			@ModelAttribute("plugDescription") PlugdescriptionCommandObject plugdescriptionCommandObject,
			@ModelAttribute("sheets") de.ingrid.iplug.excel.model.Sheets sheets,
			@ModelAttribute("uploadBean") UploadBean uploadBean)
			throws IOException {

		List<Sheet> sheetsList = sheets.getSheets();
		for (Sheet sheet : sheetsList) {
			List<Column> columns = sheet.getColumns();
			for (Column column : columns) {
				String label = column.getLabel();
				plugdescriptionCommandObject.addToList("fields", label);
			}
		}
		plugdescriptionCommandObject.put("sheets", sheets);
		plugdescriptionCommandObject.setRecordLoader(false);
		File workinDirectory = plugdescriptionCommandObject
				.getWorkinDirectory();
		workinDirectory.mkdirs();
		FileOutputStream outputStream = new FileOutputStream(new File(
				workinDirectory, "datasource.xls"));
		outputStream.write(uploadBean.getFile());
		outputStream.close();
		return "redirect:/base/save.html";
	}
}
