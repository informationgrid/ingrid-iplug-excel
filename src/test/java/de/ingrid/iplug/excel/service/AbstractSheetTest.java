/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 wemove digital solutions GmbH
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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Point;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Values;

public abstract class AbstractSheetTest extends TestCase {

	protected List<Column> getTestColumns() {
		final List<Column> columns = new ArrayList<Column>();
		final Column column1 = new Column(0);
		column1.setLabel("one");

		final Column column2 = new Column(1);
		column2.setLabel("two");

		final Column column3 = new Column(2);
		column3.setLabel("");

		columns.add(column1);
		columns.add(column2);
		columns.add(column3);
		return columns;
	}

	protected List<Row> getTestRows() {
		final List<Row> rows = new ArrayList<Row>();
		final Row row1 = new Row(0);
		row1.setLabel("1");
		final Row row2 = new Row(1);
		row2.setLabel("2");
		final Row row3 = new Row(2);
        row3.setLabel(" ");
		rows.add(row1);
		rows.add(row2);
		rows.add(row3);
		return rows;
	}

	protected Sheet getTestSheet() {
		final Sheet sheet = new Sheet();
        sheet.setDocumentType(DocumentType.ROW);
		final List<Row> rows = getTestRows();
		for (final Row row : rows) {
			sheet.addRow(row);
		}
		final List<Column> columns = getTestColumns();
		for (final Column column : columns) {
			sheet.addColumn(column);
		}
		final Values values = new Values();
		for (final Row row : rows) {
			for (final Column column : columns) {
				String label = "" + column.getLabel() + row.getLabel();
				if (row.getIndex() == 2 || column.getIndex() == 2) {
					label = "";
				}
				values.addValue(new Point(column.getIndex(), row.getIndex()),
						label);
			}
		}
		sheet.setValues(values);
		return sheet;
	}
}
