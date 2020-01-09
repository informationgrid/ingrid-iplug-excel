/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2020 wemove digital solutions GmbH
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

import de.ingrid.admin.StringUtils;
import de.ingrid.admin.mapping.FieldType;
import de.ingrid.iplug.excel.model.*;
import de.ingrid.iplug.excel.service.SheetsService;
import de.ingrid.utils.ElasticDocument;
import de.ingrid.utils.PlugDescription;
import de.ingrid.utils.statusprovider.StatusProviderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DocumentProducerTest {
    
    StatusProviderService statusProviderService;

    @Test
	public void testProduce() throws Exception {
		statusProviderService = new StatusProviderService();
        final DocumentProducer documentProducer = new DocumentProducer();
        documentProducer.setStatusProviderService( statusProviderService );

		// setup plugdescription
		final PlugDescription plugDescription = new PlugDescription();
		final File xls = new File("src/test/resources/mapping/test.xls");
		plugDescription.setWorkinDirectory(xls.getParentFile().getParentFile());
        final Sheets sheets = SheetsService.createSheets(xls);
		// setup 1st sheet
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

		// setup 2nd sheet
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
		final List<ElasticDocument> documents = new ArrayList<ElasticDocument>();
		while (documentProducer.hasNext()) {
			final ElasticDocument next = documentProducer.next();
			assertThat( next, is( not( nullValue() )));
			documents.add(next);
		}
		assertThat(documents.size(), is(5));
		ElasticDocument document = documents.get(0);
		assertThat((String)document.get("B"), is("Foo"));
		assertThat((String)document.get("C"), is(StringUtils.padding(32)));

		document = documents.get(1);
		assertThat((String)document.get("B"), is("Foobar"));
		assertThat((String)document.get("C"), is(StringUtils.padding(2323)));

		document = documents.get(2);
		assertThat((String)document.get("B"), is(StringUtils.padding(139)));
		assertThat((String)document.get("C"), is("130,90"));

		document = documents.get(3);
		assertThat((String)document.get("B"), is(StringUtils.padding(229)));
		assertThat((String)document.get("C"), is("200,99"));

		document = documents.get(4);
		assertThat((String)document.get("B"), is(StringUtils.padding(189)));
		assertThat((String)document.get("C"), is("150"));

	}
}
