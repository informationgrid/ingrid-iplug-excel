package de.ingrid.iplug.excel.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import de.ingrid.admin.command.PlugdescriptionCommandObject;
import de.ingrid.iplug.excel.UploadBean;
import de.ingrid.iplug.excel.model.Sheets;

@Controller
@SessionAttributes( { "plugDescription", "sheets" })
public class SwitchXlsController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@ModelAttribute("uploadBean")
	public UploadBean injectUploadBean() {
		return new UploadBean();
	}

	@RequestMapping(value = "/iplug/switchXls.html", method = RequestMethod.GET)
	public String switchXls(
			@ModelAttribute("plugDescription") PlugdescriptionCommandObject plugdescriptionCommandObject,
			@RequestParam(value = "sheetIndex", required = true) int sheetIndex,
			Model model) throws IOException {
		model.addAttribute("sheetIndex", sheetIndex);
		return "/iplug/switchXls";
	}

	@RequestMapping(value = "/iplug/switchXls.html", method = RequestMethod.POST)
	public String upload(
			@RequestParam(value = "sheetIndex", required = true) int sheetIndex,
			@ModelAttribute("plugDescription") PlugdescriptionCommandObject plugdescriptionCommandObject,
			@ModelAttribute("uploadBean") UploadBean uploadBean, Model model)
			throws IOException {
		MultipartFile multipartFile = uploadBean.getFile();
		byte[] uploadBytes = multipartFile.getBytes();
		Sheets sheets = (Sheets) plugdescriptionCommandObject.get("sheets");
		String fileName = sheets.getSheets().get(sheetIndex).getFileName();
		File mappingDir = new File(plugdescriptionCommandObject
				.getWorkinDirectory(), "mapping");
		File newXlsFile = new File(mappingDir, fileName);
		FileOutputStream outputStream = new FileOutputStream(newXlsFile);
		outputStream.write(uploadBytes);
		outputStream.close();
		return "redirect:/iplug/listMappings.html";

	}
}
