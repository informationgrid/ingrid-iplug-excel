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
@RequestMapping("/iplug-pages/finish.html")
@SessionAttributes(value = { "plugDescription", "sheets" })
public class ExcelFinishController {

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public String postFinish(
			@ModelAttribute("plugDescription") final PlugdescriptionCommandObject plugdescriptionCommandObject,
			@ModelAttribute("sheets") final de.ingrid.iplug.excel.model.Sheets sheets)
			throws IOException {

		if (!plugdescriptionCommandObject.containsKey("fields")) {
			plugdescriptionCommandObject.put("fields", new ArrayList<String>());
		}
		final List<String> fields = (List<String>) plugdescriptionCommandObject
				.get("fields");

		final Sheet sheet = sheets.getSheets().get(0);
		final List<Column> columns = sheet.getColumns();
		for (final Column column : columns) {
			final String label = column.getLabel();
			if (!"".equals(label.trim()) && !fields.contains(label)) {
				fields.add(label);
			}
		}
		if (!plugdescriptionCommandObject.containsKey("sheets")) {
			plugdescriptionCommandObject.put("sheets", new Sheets());
		}
		final Sheets savedSheets = (Sheets) plugdescriptionCommandObject
				.get("sheets");
		if (savedSheets.existsSheet(sheet)) {
			savedSheets.removeSheet(sheet);
			final List<Column> columns2 = sheet.getColumns();
			for (final Column column : columns2) {
				final String label = column.getLabel();
				fields.remove(label);
			}
		}
		savedSheets.addSheet(sheet);
		plugdescriptionCommandObject.setRecordLoader(false);
		final byte[] workBookBytes = sheet.getWorkbook();
		if (workBookBytes != null && workBookBytes.length > 0) {
			final File mappingDir = new File(plugdescriptionCommandObject
					.getWorkinDirectory(), "mapping");
			mappingDir.mkdirs();
			final int length = mappingDir.listFiles().length;
			final File newXlsFile = new File(mappingDir, sheet.getFileName()
					+ "_" + length);
			sheet.setFileName(newXlsFile.getName());
			final FileOutputStream outputStream = new FileOutputStream(newXlsFile);
			outputStream.write(workBookBytes);
			outputStream.close();
		}
		return "redirect:/base/save.html";
	}

}
