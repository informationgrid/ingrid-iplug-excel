package de.ingrid.iplug.excel.service;

import java.io.Serializable;
import java.util.BitSet;
import java.util.List;

import org.springframework.stereotype.Service;

import de.ingrid.iplug.excel.model.AbstractEntry;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Filter;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Values;
import de.ingrid.iplug.excel.model.Filter.FilterType;

@Service
public class AddToIndexFilter implements ISheetFilter {

	public void addRowsToIndex(Sheet sheet, BitSet bitSet) {
		List<Row> rows = sheet.getRows();
		for (int i = 0; i < rows.size(); i++) {
			boolean addToIndex = bitSet.get(i);
			rows.get(i).setMatchFilter(addToIndex);
		}
	}

	public void addColumnsToIndex(Sheet sheet, BitSet bitSet) {
		List<Column> columns = sheet.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			boolean addToIndex = bitSet.get(i);
			columns.get(i).setMatchFilter(addToIndex);
		}
	}

	public BitSet filterColumns(Sheet sheet) {
		List<Row> rows = sheet.getRows();
		List<Column> columns = sheet.getColumns();
		Values values = sheet.getValues();
		return filter(columns, rows, values);
	}

	public BitSet filterRows(Sheet sheet) {
		List<Row> rows = sheet.getRows();
		List<Column> columns = sheet.getColumns();
		Values values = sheet.getValues();
		return filter(rows, columns, values);
	}

	private BitSet filter(List<? extends AbstractEntry> documentEntries,
			List<? extends AbstractEntry> mappedEntries, Values values) {
		BitSet bitSet = new BitSet();
		for (AbstractEntry documentEntry : documentEntries) {
			if (documentEntry.isExcluded()) {
				continue;
			}
			boolean exclude = false;
			for (AbstractEntry mappedEntry : mappedEntries) {
				if (!mappedEntry.isMapped() || mappedEntry.isExcluded()) {
					continue;
				}
				Comparable<? extends Object> value = values.getValue(
						mappedEntry.getIndex(), documentEntry.getIndex());
				List<Filter> filters = mappedEntry.getFilters();
				for (Filter filter : filters) {
					FilterType filterType = filter.getFilterType();
					Comparable<? extends Serializable> expression = filter
							.getExpression();
					switch (filterType) {
					case GREATER_THAN:
						exclude = ((Comparable) value)
								.compareTo((Comparable) expression) <= 0;
						break;
					case LOWER_THAN:
						exclude = ((Comparable) value)
								.compareTo((Comparable) expression) >= 0;
						break;
					case EQUAL:
						exclude = ((Comparable) value)
								.compareTo((Comparable) expression) != 0;
						break;
					case NOT_EQUAL:
						exclude = ((Comparable) value)
								.compareTo((Comparable) expression) == 0;
						break;
					case CONTAINS:
						exclude = value.toString().indexOf(
								expression.toString()) == -1;
						break;
					case NOT_CONTAINS:
						exclude = value.toString().indexOf(
								expression.toString()) != -1;
						break;

					default:
						break;
					}
					if (exclude) {
						break;
					}
				}
				if (exclude) {
					break;
				}
			}
			bitSet.set(documentEntry.getIndex(), exclude);
		}
		return bitSet;
	}
}
