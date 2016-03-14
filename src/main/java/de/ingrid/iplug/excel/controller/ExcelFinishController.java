/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2016 wemove digital solutions GmbH
 * ==================================================
 * Licensed under the EUPL, Version 1.1 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 * 
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl5
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 * **************************************************#
 */
package de.ingrid.iplug.excel.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.thoughtworks.xstream.XStream;

import de.ingrid.admin.command.PlugdescriptionCommandObject;
import de.ingrid.iplug.excel.ExcelPlug;
import de.ingrid.iplug.excel.model.AbstractEntry;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.utils.tool.PlugDescriptionUtil;

@Controller
@RequestMapping("/iplug-pages/finish.html")
@SessionAttributes({ "plugDescription", "sheet" })
public class ExcelFinishController {

    /**
     * Mapping finish. Create excel file for mapping. 
     * 
     * @param plugDescription
     * @param sheet
     * @return
     * 		Web request "redirect:/iplug-pages/listMappings.html" 
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST)
    public String postFinish(@ModelAttribute("plugDescription") final PlugdescriptionCommandObject plugDescription,
            @ModelAttribute("sheet") final Sheet sheet) throws IOException {
        final byte[] bytes = sheet.getWorkbook();
        if (!sheet.isExisting() && bytes != null && bytes.length > 0) {
            // get mapping dir
			final File mappingDir = new File(plugDescription.getWorkinDirectory(), "mapping");
			mappingDir.mkdirs();

            // create file name
            final File file = createFile(mappingDir, sheet.getFileName());
            sheet.setFileName(file.getName());

            // write file
            final FileOutputStream os = new FileOutputStream(file);
            os.write(bytes);
            os.close();
		}
        sheet.setExisting(true);

        // add sheet
        Sheets sheets = (Sheets) plugDescription.get("sheets");
        if (sheets == null) {
            sheets = new Sheets();
            plugDescription.put("sheets", sheets);
        }
        if (sheets.existsSheet(sheet)) {
            sheets.removeSheet(sheet);
        }
        sheets.addSheet(sheet);
        
        // save mapping to config object
        XStream xstream = new XStream();
        ExcelPlug.conf.sheets = xstream.toXML( sheets );

        // update fields
        for (final Sheet aSheet : sheets) {
            Collection<? extends AbstractEntry> entries = null;
            switch (aSheet.getDocumentType()) {
            case COLUMN:
                entries = aSheet.getRows();
                break;
            case ROW:
                entries = aSheet.getColumns();
                break;
            default:
                break;
            }

            for (final AbstractEntry entry : entries) {
                if (entry.isMapped()) {
                    final String label = entry.getLabel().trim();
                    if ( !label.isEmpty() ) {
                        PlugDescriptionUtil.addFieldToPlugDescription( plugDescription, label );
                    }
                }
            }
        }

        return "redirect:/iplug-pages/listMappings.html";
    }

    /**
     * Create excel sheet file.
     * 
     * @param mappingDir
     * @param fileName
     * @return
     * 		excel sheet file
     */
    private static File createFile(final File mappingDir, final String fileName) {
        File file = new File(mappingDir, fileName);
        if (!file.exists()) {
            return file;
        }
        for (int i = 0;; i++) {
            file = new File(mappingDir, fileName + "_" + i);
            if (!file.exists()) {
                return file;
            }
        }
    }
}
