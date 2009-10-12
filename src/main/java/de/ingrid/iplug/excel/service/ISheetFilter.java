package de.ingrid.iplug.excel.service;

import java.util.BitSet;

import de.ingrid.iplug.excel.model.Sheet;

public interface ISheetFilter {

	BitSet filterRows(Sheet sheet);

	BitSet filterColumns(Sheet sheet);
}
