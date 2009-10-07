package de.ingrid.iplug.excel.model;

public class Filter {

	public static enum FilterType {
		GREATER_THAN, LOWER_THAN, CONTAINS, NOT_CONTAINS, EQUAL, NOT_EQUAL
	}

	private String _expression;

	private FilterType _fieldType;

	public Filter(String expression, FilterType fieldType) {
		_expression = expression;
		_fieldType = fieldType;
	}

	public String getExpression() {
		return _expression;
	}

	public FilterType getFieldType() {
		return _fieldType;
	}

}
