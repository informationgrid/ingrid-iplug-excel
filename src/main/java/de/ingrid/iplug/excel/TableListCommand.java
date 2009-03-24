package de.ingrid.iplug.excel;

import java.util.ArrayList;
import java.util.List;

public class TableListCommand {

	private List<TableCommand> _tableCommands = new ArrayList<TableCommand>();

	public TableListCommand() {
	}

	public TableListCommand(List<TableCommand> list) {
		_tableCommands.addAll(list);
	}

	public List<TableCommand> getTableCommands() {
		return _tableCommands;
	}

	public void setTableCommands(List<TableCommand> tableCommands) {
		_tableCommands = tableCommands;
	}

}
