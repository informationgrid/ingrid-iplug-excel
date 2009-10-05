package de.ingrid.iplug.excel;

import org.springframework.stereotype.Service;

import de.ingrid.admin.object.AbstractDataType;

@Service
public class ExcelDatatype extends AbstractDataType {

	public ExcelDatatype() {
		super("excel");
	}

}
