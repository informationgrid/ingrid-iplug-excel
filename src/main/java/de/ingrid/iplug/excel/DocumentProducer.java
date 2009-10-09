package de.ingrid.iplug.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.springframework.stereotype.Service;

import de.ingrid.admin.object.IDocumentProducer;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.utils.IConfigurable;
import de.ingrid.utils.PlugDescription;

@Service
public class DocumentProducer implements IDocumentProducer, IConfigurable {

	class RowIterator implements Iterator<HSSFRow> {

		private Iterator<HSSFRow> _prev = null;
		private Iterator<HSSFRow> _iterator = new ArrayList<HSSFRow>()
				.iterator();

		public RowIterator() {
		}

		public RowIterator(Iterator<HSSFRow> rowIterator,
				Iterator<HSSFRow> iterator) {
			_prev = rowIterator;
			_iterator = iterator;
		}

		public boolean hasNext() {
			boolean hasNext = _prev != null ? _prev.hasNext() : false;
			hasNext = !hasNext ? _iterator.hasNext() : false;
			return hasNext;
		}

		public HSSFRow next() {
			HSSFRow row = _prev != null && _prev.hasNext() ? _prev.next()
					: _iterator.next();
			return row;
		}

		public void remove() {
			if (_prev != null && _prev.hasNext()) {
				_prev.remove();
			} else {
				_iterator.remove();
			}
		}

	}

	private Iterator<HSSFRow> _rowIterator;

	public boolean hasNext() {
		return _rowIterator.hasNext();
	}

	public Document next() {
		// HSSFRow row = _rowIterator.next();
		// Iterator<HSSFCell> cellIterator = row.cellIterator();
		// Document document = new Document();
		// int columnCounter = 0;
		// while (cellIterator.hasNext()) {
		// HSSFCell cell = cellIterator.next();
		// String cellContent = cell.toString();
		// Column column = _currentSheet.getColumns().get(columnCounter);
		// Fieldable field = new Field(column.getName(), cellContent,
		// Store.YES, Index.ANALYZED);
		// document.add(field);
		// columnCounter++;
		// }
		// return document;
		return null;
	}

	public void initialize() throws Exception {
	}

	public void configure(PlugDescription plugDescription) {
		File workinDirectory = plugDescription.getWorkinDirectory();
		Sheets sheets = (Sheets) plugDescription.get("sheets");
		List<Sheet> sheetsList = sheets.getSheets();
		_rowIterator = new RowIterator();
		// for (Sheet sheet : sheetsList) {
		// try {
		// String fileName = sheet.getFileName();
		// File xls = new File(workinDirectory, fileName);
		// HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
		// xls));
		// int numberOfSheets = workbook.getNumberOfSheets();
		// for (int i = 0; i < numberOfSheets; i++) {
		// if (sheet.getSheetIndex() == i) {
		// HSSFSheet hssfSheet = workbook.getSheetAt(i);
		// Iterator<HSSFRow> iterator = hssfSheet.rowIterator();
		// DocumentType documentType = sheet.getDocumentType();
		// switch (documentType.ordinal()) {
		// case DocumentType.COLUMN.ordinal():
		// break;
		// case DocumentType.ROW.ordinal():
		//
		// default:
		// break;
		// }
		// _rowIterator = new RowIterator(_rowIterator, iterator);
		// }
		//
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

	}
}
