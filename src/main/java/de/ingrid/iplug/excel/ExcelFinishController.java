package de.ingrid.iplug.excel;

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
import de.ingrid.iplug.excel.SheetContainer.Sheet;
import de.ingrid.iplug.excel.SheetContainer.Sheet.Column;

@Controller
@SessionAttributes(value = { "plugDescription", "tableListCommand",
		"uploadBean" })
@RequestMapping("/iplug/finish.html")
public class ExcelFinishController {

	@RequestMapping(method = RequestMethod.GET)
	public String finish() {
		return "/iplug/finish";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postFinish(
			@ModelAttribute("plugDescription") PlugdescriptionCommandObject plugdescriptionCommandObject,
			@ModelAttribute("tableListCommand") TableListCommand tableListCommand,
			@ModelAttribute("uploadBean") UploadBean uploadBean)
			throws IOException {

		List<TableCommand> tableCommands = tableListCommand.getTableCommands();
		SheetContainer sheetContainer = new SheetContainer();
		for (TableCommand tableCommand : tableCommands) {
			Sheet sheet = new SheetContainer.Sheet();
			TableHeadCommand head = tableCommand.getHead();
			List<String> headers = head.getHeaders();
			for (String headString : headers) {
				Column column = new SheetContainer.Sheet.Column();
				column.setName(headString);
				sheet.addColumn(column);
				plugdescriptionCommandObject.addToList("fields", headString);
			}
			sheetContainer.addSheet(sheet);
		}
		plugdescriptionCommandObject.put("sheets", sheetContainer);
		plugdescriptionCommandObject.setRecordLoader(false);
		File workinDirectory = plugdescriptionCommandObject
				.getWorkinDirectory();
		FileOutputStream outputStream = new FileOutputStream(new File(
				workinDirectory, "datasource.xls"));
		outputStream.write(uploadBean.getFile());
		outputStream.close();
		return "redirect:/base/save.html";
	}
}
