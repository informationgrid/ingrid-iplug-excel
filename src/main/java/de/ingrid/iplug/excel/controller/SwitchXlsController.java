package de.ingrid.iplug.excel.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

/**
 * Controller to upgrade excel files.
 *
 */
@Controller
@SessionAttributes( { "plugDescription" })
public class SwitchXlsController {

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@ModelAttribute("uploadBean")
	public UploadBean injectUploadBean() {
		return new UploadBean();
	}

    /**
     * Add sheet index to modelMap.
     * 
     * @param sheetIndex
     * @param modelMap
     * @return
     * 		Web request "/iplug-pages/switchXls"
     * @throws IOException
     */
    @RequestMapping(value = "/iplug-pages/switchXls.html", method = RequestMethod.GET)
    public String switchXls(@RequestParam final Integer sheetIndex, final ModelMap modelMap) throws IOException {
        modelMap.addAttribute("sheetIndex", sheetIndex);
        return "/iplug-pages/switchXls";
	}

    /**
     * Upload excel files.
     * 
     * @param sheetIndex
     * @param plugDescription
     * @param uploadBean
     * @return
     * 		Web request "redirect:/iplug-pages/listMappings.html"
     * @throws IOException
     */
    @RequestMapping(value = "/iplug-pages/switchXls.html", method = RequestMethod.POST)
    public String upload(@RequestParam final Integer sheetIndex,
            @ModelAttribute("plugDescription") final PlugdescriptionCommandObject plugDescription,
            @ModelAttribute("uploadBean") final UploadBean uploadBean) throws IOException {
		final MultipartFile multipartFile = uploadBean.getFile();
		final byte[] uploadBytes = multipartFile.getBytes();
        final Sheets sheets = (Sheets) plugDescription.get("sheets");
        final String fileName = sheets.getSheets().get(sheetIndex).getFileName();
        final File mappingDir = new File(plugDescription.getWorkinDirectory(), "mapping");
		final File newXlsFile = new File(mappingDir, fileName);
		final FileOutputStream outputStream = new FileOutputStream(newXlsFile);
		outputStream.write(uploadBytes);
		outputStream.close();
        return "redirect:/iplug-pages/listMappings.html";

	}
}
