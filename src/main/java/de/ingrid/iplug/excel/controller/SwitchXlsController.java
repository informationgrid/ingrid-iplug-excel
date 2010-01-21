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
	public void initBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@ModelAttribute("uploadBean")
	public UploadBean injectUploadBean() {
		return new UploadBean();
	}

    @RequestMapping(value = "/iplug-pages/switchXls.html", method = RequestMethod.GET)
	public String switchXls(
			@ModelAttribute("plugDescription") final PlugdescriptionCommandObject plugdescriptionCommandObject,
			@RequestParam(value = "sheetIndex", required = true) final int sheetIndex,
			final Model model) throws IOException {
		model.addAttribute("sheetIndex", sheetIndex);
        return "/iplug-pages/switchXls";
	}

    @RequestMapping(value = "/iplug-pages/switchXls.html", method = RequestMethod.POST)
	public String upload(
			@RequestParam(value = "sheetIndex", required = true) final int sheetIndex,
			@ModelAttribute("plugDescription") final PlugdescriptionCommandObject plugdescriptionCommandObject,
			@ModelAttribute("uploadBean") final UploadBean uploadBean, final Model model)
			throws IOException {
		final MultipartFile multipartFile = uploadBean.getFile();
		final byte[] uploadBytes = multipartFile.getBytes();
		final Sheets sheets = (Sheets) plugdescriptionCommandObject.get("sheets");
		final String fileName = sheets.getSheets().get(sheetIndex).getFileName();
		final File mappingDir = new File(plugdescriptionCommandObject
				.getWorkinDirectory(), "mapping");
		final File newXlsFile = new File(mappingDir, fileName);
		final FileOutputStream outputStream = new FileOutputStream(newXlsFile);
		outputStream.write(uploadBytes);
		outputStream.close();
        return "redirect:/iplug-pages/listMappings.html";

	}
}
