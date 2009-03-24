package de.ingrid.iplug.excel;

import java.util.ArrayList;
import java.util.List;

public class TableCommand {

	private List<RowCommand> _rows = new ArrayList<RowCommand>();
	private TableHeadCommand _head;

	public List<RowCommand> getRows() {
		return _rows;
	}

	public void setRows(List<RowCommand> rows) {
		_rows = rows;
	}

	public void addRow(RowCommand command) {
		_rows.add(command);
	}

	public TableHeadCommand getHead() {
		return _head;
	}

	public void setHead(TableHeadCommand head) {
		_head = head;
	}

}
