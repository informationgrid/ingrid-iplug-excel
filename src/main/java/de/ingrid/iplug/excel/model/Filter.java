package de.ingrid.iplug.excel.model;

public class Filter {

	public static enum FilterType {
		GREATER_THAN, LOWER_THAN, CONTAINS, NOT_CONTAINS, EQUAL, NOT_EQUAL
	}

	private String _expression;

	private FilterType _filterType;

	public Filter(String expression, FilterType filterType) {
		_expression = expression;
		_filterType = filterType;
	}

	public String getExpression() {
		return _expression;
	}

	public FilterType getFieldType() {
		return _filterType;
	}

}
