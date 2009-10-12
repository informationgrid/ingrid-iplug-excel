package de.ingrid.iplug.excel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.ingrid.iplug.excel.model.Sheet;

@Service
public class ExcludeFilters {

	private final ExcludeFilter[] _filters;

	@Autowired
	public ExcludeFilters(ExcludeFilter... filters) {
		_filters = filters;
	}

	public void filter(Sheet sheet) {
		for (ExcludeFilter filter : _filters) {
			filter.filter(sheet);
		}
	}

}
