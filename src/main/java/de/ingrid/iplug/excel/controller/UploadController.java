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
@RequestMapping(value = "/iplug-pages/upload.html")
@SessionAttributes(value = { "uploadBean", "sheets" })
public class UploadController {

	private final SheetsService _sheetsService;
	private final EmptySheetFilter _excludeFilter;

	@Autowired
	public UploadController(final SheetsService sheetsService,
			final EmptySheetFilter excludeFilter) {
		_sheetsService = sheetsService;
		_excludeFilter = excludeFilter;
	}

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.GET)
	public String firstRow() {
        return "/iplug-pages/upload";
	}

	@ModelAttribute("uploadBean")
	public UploadBean injectUploadBean() {
		return new UploadBean();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String upload(@ModelAttribute("uploadBean") final UploadBean uploadBean,
			final Model model) throws IOException {
		final MultipartFile multipartFile = uploadBean.getFile();
		final byte[] uploadBytes = multipartFile.getBytes();

		final Sheets sheets = _sheetsService.createSheets(uploadBytes);
		final List<Sheet> sheetList = sheets.getSheets();
		for (final Sheet sheet : sheetList) {
			_excludeFilter.excludeEmtpyRowsAndColumns(sheet);
			sheet.setFileName(multipartFile.getOriginalFilename());
			sheet.setWorkbook(uploadBytes);
		}
		model.addAttribute("sheets", sheets);

		if (sheets.getSheets().size() == 1) {
            return "redirect:/iplug-pages/settings.html";
		}

        return "redirect:/iplug-pages/previewExcelFile.html";

	}

}
