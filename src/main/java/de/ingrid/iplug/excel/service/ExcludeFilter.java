package de.ingrid.iplug.excel.service;

import java.util.BitSet;

import org.springframework.beans.factory.annotation.Autowired;

import de.ingrid.iplug.excel.model.Sheet;

public abstract class ExcludeFilter implements ISheetFilter {

	private final SheetService _sheetService;

	@Autowired
	public ExcludeFilter(SheetService sheetService) {
		_sheetService = sheetService;
	}

	public void filter(Sheet sheet) {
		BitSet bitSet = filterRows(sheet);
		for (int i = 0; i < bitSet.size(); i++) {
			if (bitSet.get(i)) {
				_sheetService.excludeRow(sheet, i);
			}
		}
		bitSet = filterColumns(sheet);
		for (int i = 0; i < bitSet.size(); i++) {
			if (bitSet.get(i)) {
				_sheetService.excludeColumn(sheet, i);
			}
		}
	}

}
