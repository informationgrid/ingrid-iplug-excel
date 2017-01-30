/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2017 wemove digital solutions GmbH
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

import de.ingrid.admin.mapping.Filter;
import de.ingrid.admin.mapping.Filter.FilterType;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Point;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Values;

public class SheetFilterTest extends AbstractSheetTest {

    public void testTrim() {
        final Sheet emptySheet = new Sheet();
        emptySheet.addColumn(new Column(0));
        emptySheet.addRow(new Row(0));

        final Values values = new Values();
        values.addValue(new Point(0, 0), "");
        emptySheet.setValues(values);

        assertTrue(SheetFilter.trimSheet(emptySheet));

        final Sheet sheet = getTestSheet();
        assertFalse(SheetFilter.trimSheet(sheet));
        assertEquals(2, sheet.getColumns().size());
        assertEquals(2, sheet.getRows().size());
    }

    public void testSelect() throws Exception {
        final Sheet sheet = getTestSheet();

        for (final Column column : sheet.getColumns()) {
            assertFalse(column.isExcluded());
        }
        for (final Row row : sheet.getRows()) {
            assertFalse(row.isExcluded());
        }

        SheetFilter.select(sheet, 1, 2, 2, 2);

        for (final Column column : sheet.getColumns()) {
            if (column.getIndex() >= 1 && column.getIndex() <= 2) {
                assertFalse(column.isExcluded());
            } else {
                assertTrue(column.isExcluded());
            }
        }
        for (final Row row : sheet.getRows()) {
            if (row.getIndex() == 2) {
                assertFalse(row.isExcluded());
            } else {
                assertTrue(row.isExcluded());
            }
        }
    }

    public void testFilterColumn() {
        final Sheet sheet = getTestSheet();
        for (final Column column : sheet.getColumns()) {
            column.setMapped(true);
        }

        SheetFilter.filter(sheet);
        for (final Row row : sheet.getRows()) {
            assertTrue(row.isMatchFilter());
        }

        final Column column = sheet.getColumn(0);
        final Filter filter = new Filter("one1", FilterType.NOT_EQUAL);
        column.addFilter(filter);

        SheetFilter.filter(sheet, column, filter);
        for (final Row row : sheet.getRows()) {
            final boolean match = row.isMatchFilter();
            if (row.getIndex() == 0) {
                assertFalse(match);
            } else {
                assertTrue(match);
            }
        }
    }

    public void testFilterAll() {
        final Sheet sheet = getTestSheet();
        for (final Column column : sheet.getColumns()) {
            column.setMapped(true);
        }

        SheetFilter.filter(sheet);
        for (final Row row : sheet.getRows()) {
            assertTrue(row.isMatchFilter());
        }

        final Column column = sheet.getColumn(0);
        final Filter filter = new Filter("one1", FilterType.NOT_EQUAL);
        column.addFilter(filter);
        SheetFilter.filter(sheet);
        for (final Row row : sheet.getRows()) {
            final boolean match = row.isMatchFilter();
            if (row.getIndex() == 0) {
                assertFalse(match);
            } else {
                assertTrue(match);
            }
        }
    }
}
