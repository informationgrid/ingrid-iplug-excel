package de.ingrid.iplug.excel;

import java.util.ArrayList;
import java.util.List;

public class TableHeadCommand {

	private List<String> _headers = new ArrayList<String>();

	public List<String> getHeaders() {
		return _headers;
	}

	public void setHeaders(List<String> headers) {
		_headers = headers;
	}

	public void addHeader(String header) {
		_headers.add(header);
	}

}
