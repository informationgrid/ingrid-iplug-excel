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

import de.ingrid.admin.PlugdescriptionCommandObject;
import de.ingrid.iplug.excel.SheetContainer.Sheet;
import de.ingrid.iplug.excel.SheetContainer.Sheet.Column;
import de.ingrid.utils.PlugDescription;
import de.ingrid.utils.xml.XMLSerializer;

@Controller
@SessionAttributes(value = { "plugDescription", "tableListCommand",
		"uploadBean" })
@RequestMapping("/iplug/finish.html")
public class FinishController {

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

		File workinDirectory = new File(plugdescriptionCommandObject
				.getWorkingDir());
		PlugDescription plugDescription = new PlugDescription();
		plugDescription.setWorkinDirectory(workinDirectory);
		plugDescription.addPartner(plugdescriptionCommandObject.getPartner());
		plugDescription.addProvider(plugdescriptionCommandObject.getProvider());
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
			}
			sheetContainer.addSheet(sheet);
		}
		plugDescription.put("sheets", sheetContainer);

		String plugDescriptionFile = System.getProperty("plugDescription");
		workinDirectory.mkdirs();
		XMLSerializer serializer = new XMLSerializer();
		serializer.serialize(plugDescription, new File(plugDescriptionFile));

		FileOutputStream outputStream = new FileOutputStream(new File(
				workinDirectory, "datasource.xls"));
		outputStream.write(uploadBean.getFile());
		outputStream.close();
		return "redirect:/base/welcome.html";
	}
}
