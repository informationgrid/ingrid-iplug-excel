package de.ingrid.iplug.excel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

public class SheetContainer implements Externalizable {

	public static class Sheet implements Externalizable {

		public static class Column implements Externalizable {
			private String _name;

			public String getName() {
				return _name;
			}

			public void setName(String name) {
				_name = name;
			}

			public void readExternal(ObjectInput in) throws IOException,
					ClassNotFoundException {
				_name = in.readUTF();
			}

			public void writeExternal(ObjectOutput out) throws IOException {
				out.writeUTF(_name);
			}

		}

		private List<Column> _columns = new ArrayList<Column>();

		public List<Column> getColumns() {
			return _columns;
		}

		public void setColumns(List<Column> columns) {
			_columns = columns;
		}

		public void addColumn(Column column) {
			_columns.add(column);
		}

		public void readExternal(ObjectInput in) throws IOException,
				ClassNotFoundException {
			_columns = (List<Column>) in.readObject();
		}

		public void writeExternal(ObjectOutput out) throws IOException {
			out.writeObject(_columns);
		}
	}

	private List<Sheet> _sheets = new ArrayList<Sheet>();

	public List<Sheet> getSheets() {
		return _sheets;
	}

	public void setSheets(List<Sheet> sheets) {
		_sheets = sheets;
	}

	public void addSheet(Sheet sheet) {
		_sheets.add(sheet);
	}

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		_sheets = (List<Sheet>) in.readObject();
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(_sheets);
	}

}
