package de.ingrid.iplug.excel.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import de.ingrid.iplug.excel.UploadBean;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.service.EmptySheetFilter;
import de.ingrid.iplug.excel.service.SheetsService;

@Controller
@RequestMapping(value = "/iplug/upload.html")
@SessionAttributes(value = { "uploadBean", "sheets" })
public class UploadController {

	private final SheetsService _sheetsService;
	private final EmptySheetFilter _excludeFilter;

	@Autowired
	public UploadController(SheetsService sheetsService,
			EmptySheetFilter excludeFilter) {
		_sheetsService = sheetsService;
		_excludeFilter = excludeFilter;
	}

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
		MultipartFile multipartFile = uploadBean.getFile();
		byte[] uploadBytes = multipartFile.getBytes();

		Sheets sheets = _sheetsService.createSheets(uploadBytes);
		List<Sheet> sheetList = sheets.getSheets();
		for (Sheet sheet : sheetList) {
			_excludeFilter.excludeEmtpyRowsAndColumns(sheet);
			sheet.setFileName(multipartFile.getOriginalFilename());
			sheet.setWorkbook(uploadBytes);
		}
		model.addAttribute("sheets", sheets);

		if (sheets.getSheets().size() == 1) {
			return "redirect:/iplug/settings.html";
		}

		return "redirect:/iplug/previewExcelFile.html";

	}

}
