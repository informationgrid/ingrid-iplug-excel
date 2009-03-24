package de.ingrid.iplug.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import de.ingrid.admin.IDocumentProducer;
import de.ingrid.utils.PlugDescription;
import de.ingrid.utils.xml.XMLSerializer;

@Service
public class DocumentProducer implements IDocumentProducer {

	private int _numberOfSheets;
	private int _currentSheet;
	private HSSFWorkbook _workbook;
	private Iterator<HSSFRow> _rowIterator;

	public boolean hasNext() {
		boolean hasNext = _rowIterator == null ? false : _rowIterator.hasNext();
		if (!hasNext) {
			if (_currentSheet < _numberOfSheets) {
				HSSFSheet sheet = _workbook.getSheetAt(_currentSheet);
				_currentSheet++;
				_rowIterator = sheet.rowIterator();
				return hasNext();
			}
		}
		return hasNext;

	}

	public Document next() {
		HSSFRow row = _rowIterator.next();
		Iterator<HSSFCell> cellIterator = row.cellIterator();
		Document document = new Document();
		while (cellIterator.hasNext()) {
			HSSFCell cell = cellIterator.next();
			String cellContent = cell.toString();
			Fieldable field = new Field("name", cellContent, Store.YES,
					Index.ANALYZED);
			document.add(field);
		}
		return document;
	}

	public void initialize() throws Exception {
		String property = System.getProperty("plugDescription");
		File pdFile = new File(property);
		PlugDescription plugDescription = (PlugDescription) new XMLSerializer()
				.deSerialize(pdFile);
		File workinDirectory = plugDescription.getWorkinDirectory();
		File xlsFile = new File(workinDirectory, "datasource.xls");

		_workbook = new HSSFWorkbook(new FileInputStream(xlsFile));
		_numberOfSheets = _workbook.getNumberOfSheets();

	}

}
