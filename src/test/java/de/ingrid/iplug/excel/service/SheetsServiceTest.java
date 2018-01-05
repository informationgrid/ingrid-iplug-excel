/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2018 wemove digital solutions GmbH
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
package de.ingrid.iplug.excel.service;

import java.io.File;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.model.Values;

public class SheetsServiceTest extends TestCase {

	public void testCreateSheets() throws Exception {
        final Sheets sheets = SheetsService.createSheets(new File("src/test/resources/mapping/test.xls"));
		final List<Sheet> sheetList = sheets.getSheets();
		assertEquals(3, sheetList.size());
		final Sheet sheet = sheetList.get(0);

        final Collection<Row> rows = sheet.getRows();
        assertEquals(4, rows.size());

        final Collection<Column> columns = sheet.getColumns();
        assertEquals(3, columns.size());

		final Values values = sheet.getValues();
        assertEquals(12, values.getSize());

		assertEquals("Vorname", values.getValue(0, 0));
		assertEquals("Name", values.getValue(1, 0));
		assertEquals("Alter", values.getValue(2, 0));

		assertEquals("Foo", values.getValue(0, 1));
		assertEquals("Bar", values.getValue(1, 1));
		assertEquals(23.0, values.getValue(2, 1));

		assertEquals("Bar", values.getValue(0, 2));
		assertEquals("Foo", values.getValue(1, 2));
		assertEquals(32.0, values.getValue(2, 2));

		assertEquals("Foobar", values.getValue(0, 3));
		assertEquals("Foobar", values.getValue(1, 3));
		assertEquals(2323.0, values.getValue(2, 3));

	}
}
