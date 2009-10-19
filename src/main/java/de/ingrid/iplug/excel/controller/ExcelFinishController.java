package de.ingrid.iplug.excel.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.command.PlugdescriptionCommandObject;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;

@Controller
@SessionAttributes(value = { "plugDescription", "sheets", })
@RequestMapping("/iplug/finish.html")
public class ExcelFinishController {

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public String postFinish(
			@ModelAttribute("plugDescription") PlugdescriptionCommandObject plugdescriptionCommandObject,
			@ModelAttribute("sheets") de.ingrid.iplug.excel.model.Sheets sheets)
			throws IOException {

		if (!plugdescriptionCommandObject.containsKey("fields")) {
			plugdescriptionCommandObject.put("fields", new ArrayList<String>());
		}
		List<String> fields = (List<String>) plugdescriptionCommandObject
				.get("fields");

		Sheet sheet = sheets.getSheets().get(0);
		List<Column> columns = sheet.getColumns();
		for (Column column : columns) {
			String label = column.getLabel();
			if (!"".equals(label.trim()) && !fields.contains(label)) {
				fields.add(label);
			}
		}
		if (!plugdescriptionCommandObject.containsKey("sheets")) {
			plugdescriptionCommandObject.put("sheets", new Sheets());
		}
		Sheets savedSheets = (Sheets) plugdescriptionCommandObject
				.get("sheets");
		if (savedSheets.existsSheet(sheet)) {
			savedSheets.removeSheet(sheet);
			List<Column> columns2 = sheet.getColumns();
			for (Column column : columns2) {
				String label = column.getLabel();
				fields.remove(label);
			}
		}
		savedSheets.addSheet(sheet);
		plugdescriptionCommandObject.setRecordLoader(false);
		File workinDirectory = plugdescriptionCommandObject
				.getWorkinDirectory();
		workinDirectory.mkdirs();
		int length = workinDirectory.listFiles().length;
		byte[] workBookBytes = sheet.getWorkbook();
		if (workBookBytes != null && workBookBytes.length > 0) {
			File newXlsFile = new File(workinDirectory, sheet.getFileName()
					+ "_" + length);
			sheet.setFileName(newXlsFile.getName());
			FileOutputStream outputStream = new FileOutputStream(newXlsFile);
			outputStream.write(workBookBytes);
			outputStream.close();
		}
		return "redirect:/base/save.html";
	}

}
