package de.ingrid.iplug.excel.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Filter implements Externalizable {

	public static enum FilterType {
		All, GREATER_THAN, LOWER_THAN, CONTAINS, NOT_CONTAINS, EQUAL, NOT_EQUAL
	}

	private String _expression;

	private FilterType _filterType = FilterType.All;

	public Filter() {
		// externalizable
	}

	public Filter(String expression, FilterType filterType) {
		_expression = expression;
		_filterType = filterType;
	}

	public String getExpression() {
		return _expression;
	}

	public FilterType getFilterType() {
		return _filterType;
	}

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		_filterType = FilterType.valueOf(in.readUTF());
		_expression = in.readUTF();
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(_filterType.name());
		out.writeUTF(_expression);
	}

}
