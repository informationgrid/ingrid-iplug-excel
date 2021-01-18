/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2021 wemove digital solutions GmbH
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

import java.io.Serializable;
import java.util.BitSet;

import de.ingrid.admin.mapping.Filter;
import de.ingrid.admin.mapping.Filter.FilterType;
import de.ingrid.iplug.excel.model.AbstractEntry;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Point;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Values;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Define filters for sheet.
 *
 */
public class SheetFilter {

    private static Logger log = LogManager.getLogger(SheetFilter.class);

    public static boolean trimSheet(final Sheet sheet) {
        // return that sheet is already empty if no columns or rows exists
        if (sheet.getColumnsMap().isEmpty() || sheet.getRowsMap().isEmpty()) {
            return true;
        }

        // true means have content
        final BitSet cols = new BitSet(sheet.getLastColumnIndex());
        final BitSet rows = new BitSet(sheet.getLastRowIndex());

        // collect columns and rows with content
        final Values values = sheet.getValues();
        for (final Point point : values) {
            final Comparable<? extends Object> value = values.getValue(point);
            final int col = point.getX();
            final int row = point.getY();
            if (value != null && !value.toString().trim().equals("")) {
                cols.set(col);
                rows.set(row);
            }
        }

        // if all values are false, sheet is empty
        if (cols.isEmpty() || rows.isEmpty()) {
            return true;
        }

        // flip all bits, then true means exclude
        cols.flip(0, sheet.getLastColumnIndex() + 1);
        rows.flip(0, sheet.getLastRowIndex() + 1);

        // remove empty rows and cols
        removeColumns(sheet, cols);
        removeRows(sheet, rows);

        return false;
    }

    /**
     * Select excel sheet field.
     * 
     * @param sheet
     * @param fromCol
     * @param toCol
     * @param fromRow
     * @param toRow
     */
    public static void select(final Sheet sheet, final int fromCol, final int toCol, final int fromRow,
            final int toRow) {
        // set from and to
        sheet.setFrom(fromCol, fromRow);
        sheet.setTo(toCol, toRow);

        // exclude cols
        for (final Column column : sheet.getColumns()) {
            final int index = column.getIndex();
            column.setExcluded(index < fromCol || index > toCol);
        }
        // exclude rows
        for (final Row row : sheet.getRows()) {
            final int index = row.getIndex();
            row.setExcluded(index < fromRow || index > toRow);
        }
    }

    /**
     * Filter row or column.
     * 
     * @param sheet
     * @param entry
     * @param filter
     */
    public static void filter(final Sheet sheet, final AbstractEntry entry, final Filter filter) {
        final DocumentType type = sheet.getDocumentType();
        final Values values = sheet.getValues();

        switch (type) {
        case ROW:
            // filter all row in column entry.getIndex()
            for (final Point point : values) {
                if (point.getX() == entry.getIndex()) {
                    final Row row = sheet.getRow(point.getY());
                    if (row != null && !row.isExcluded()) {
                        final Comparable<? extends Object> value = values.getValue(point);
                        final boolean match = row.isMatchFilter() && matchFilter(value, filter);
                        row.setMatchFilter(match);
                    }
                }
            }
            break;
        case COLUMN:
            // filter all columns in row entry.getIndex()
            for (final Point point : values) {
                if (point.getY() == entry.getIndex()) {
                    final Column column = sheet.getColumn(point.getX());
                    if (column != null && !column.isExcluded()) {
                        final Comparable<? extends Object> value = values.getValue(point);
                        final boolean match = column.isMatchFilter() && matchFilter(value, filter);
                        column.setMatchFilter(match);
                    }
                }
            }
            break;
        default:
            break;
        }
    }

    /**
     * Filter row or column.
     * 
     * @param sheet
     */
    public static void filter(final Sheet sheet) {
        final DocumentType type = sheet.getDocumentType();
        final Values values = sheet.getValues();

        // set all to matching filter
        for (final Row row : sheet.getRows()) {
            row.setMatchFilter(true);
        }
        for (final Column col : sheet.getColumns()) {
            col.setMatchFilter(true);
        }

        switch (type) {
        case ROW:
            // use all column's filter to filter a row
            for (final Point point : values) {
                final Row row = sheet.getRow(point.getY());
                if (row != null && !row.isExcluded()) {
                    boolean match = row.isMatchFilter();
                    final Column column = sheet.getColumn(point.getX());
                    if (column != null) {
                        final Comparable<? extends Object> value = values.getValue(point);
                        for (final Filter filter : column.getFilters()) {
                            if (!match) {
                                break;
                            }
                            match = match && matchFilter(value, filter);
                        }
                    }
                    row.setMatchFilter(match);
                }
            }
            break;
        case COLUMN:
            // use all row's filter to filter a column
            for (final Point point : values) {
                final Column column = sheet.getColumn(point.getX());
                if (column != null && !column.isExcluded()) {
                    boolean match = column.isMatchFilter();
                    final Row row = sheet.getRow(point.getY());
                    if (row != null) {
                        final Comparable<? extends Object> value = values.getValue(point);
                        for (final Filter filter : row.getFilters()) {
                            if (!match) {
                                break;
                            }
                            match = match && matchFilter(value, filter);
                        }
                    }
                    column.setMatchFilter(match);
                }
            }
            break;
        default:
            break;
        }
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static boolean matchFilter(final Comparable<? extends Object> value, final Filter filter) {
        final FilterType type = filter.getFilterType();
        final Comparable<? extends Serializable> expression = filter.getExpression();
        try {
            switch (type) {
            case GREATER_THAN:
                return Double.parseDouble(value.toString()) > (Double) expression;
            case AFTER:
                return ((Comparable) value).compareTo(expression) > 0;
            case LOWER_THAN:
                return Double.parseDouble(value.toString()) < (Double) expression;
            case BEFORE:
                return ((Comparable) value).compareTo(expression) < 0;
            case EQUAL:
                return ((Comparable) value).compareTo(expression) == 0;
            case NOT_EQUAL:
                return ((Comparable) value).compareTo(expression) != 0;
            case CONTAINS:
                return value.toString().indexOf(expression.toString()) != -1;
            case NOT_CONTAINS:
                return value.toString().indexOf(expression.toString()) == -1;
            default:
                return false;
            }
        } catch (final Exception e) {
            log.error("Error matching filter", e);
            return false;
        }
    }

    /**
     * Remove column.
     * 
     * @param sheet
     * @param bitSet
     */
    private static void removeColumns(final Sheet sheet, final BitSet bitSet) {
        for (int i = bitSet.nextSetBit(0); i > -1; i = bitSet.nextSetBit(i + 1)) {
            sheet.removeColumn(i);
        }
    }

    /**
     * Remove row.
     * 
     * @param sheet
     * @param bitSet
     */
    private static void removeRows(final Sheet sheet, final BitSet bitSet) {
        for (int i = bitSet.nextSetBit(0); i > -1; i = bitSet.nextSetBit(i + 1)) {
            sheet.removeRow(i);
        }
    }
}
