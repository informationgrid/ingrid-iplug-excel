/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2015 wemove digital solutions GmbH
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
package de.ingrid.iplug.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.lucene.document.Document;

import de.ingrid.admin.StringUtils;
import de.ingrid.admin.mapping.FieldType;
import de.ingrid.admin.search.GermanStemmer;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.service.SheetsService;
import de.ingrid.utils.PlugDescription;

public class DocumentProducerTest extends TestCase {

	public void testProduce() throws Exception {
        final DocumentProducer documentProducer = new DocumentProducer(new GermanStemmer());

		// setup plugdescription
		final PlugDescription plugDescription = new PlugDescription();
		final File xls = new File("src/test/resources/mapping/test.xls");
		plugDescription.setWorkinDirectory(xls.getParentFile().getParentFile());
        final Sheets sheets = SheetsService.createSheets(xls);
		// setup 0te sheet
		Sheet sheet = sheets.getSheets().get(0);
		sheet.setFileName("test.xls");
		sheet.setDocumentType(DocumentType.ROW);
        Column column = sheet.getColumn(1);
		column.setFieldType(FieldType.TEXT);
		column.setMapped(true);
        column = sheet.getColumn(2);
		column.setFieldType(FieldType.NUMBER);
		column.setMapped(true);
        for (final Row row : sheet.getRows()) {
            if (row.getIndex() >= 2 && row.getIndex() <= 3) {
                row.setMatchFilter(true);
			} else {
                row.setMatchFilter(false);
			}
		}

		// setup 1te sheet
		sheet = sheets.getSheets().get(1);
		sheet.setFileName("test.xls");
		sheet.setDocumentType(DocumentType.ROW);
        column = sheet.getColumn(1);
		column.setFieldType(FieldType.NUMBER);
		column.setMapped(true);
        column = sheet.getColumn(2);
        column.setFieldType(FieldType.TEXT);
		column.setMapped(true);
        for (final Row row : sheet.getRows()) {
            if (row.getIndex() >= 2 && row.getIndex() <= 4) {
                row.setMatchFilter(true);
			} else {
                row.setMatchFilter(false);
			}
		}

		sheets.getSheets().remove(2);

		plugDescription.put("sheets", sheets);

		documentProducer.configure(plugDescription);
		final List<Document> documents = new ArrayList<Document>();
		while (documentProducer.hasNext()) {
			final Document next = documentProducer.next();
			assertNotNull(next);
			documents.add(next);
		}
		assertEquals(5, documents.size());
		Document document = documents.get(0);
		assertEquals("Foo", document.get("B"));
		assertEquals(StringUtils.padding(32), document.get("C"));

		document = documents.get(1);
		assertEquals("Foobar", document.get("B"));
		assertEquals(StringUtils.padding(2323), document.get("C"));

		document = documents.get(2);
		assertEquals(StringUtils.padding(139), document.get("B"));
		assertEquals("130,90", document.get("C"));

		document = documents.get(3);
		assertEquals(StringUtils.padding(229), document.get("B"));
		assertEquals("200,99", document.get("C"));

		document = documents.get(4);
		assertEquals(StringUtils.padding(189), document.get("B"));
		assertEquals("150", document.get("C"));

	}
}
