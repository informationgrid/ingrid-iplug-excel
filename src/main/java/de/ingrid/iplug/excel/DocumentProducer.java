package de.ingrid.iplug.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.ingrid.admin.StringUtils;
import de.ingrid.admin.object.IDocumentProducer;
import de.ingrid.iplug.excel.model.AbstractEntry;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.FieldType;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.model.Values;
import de.ingrid.iplug.excel.service.SheetsService;
import de.ingrid.utils.IConfigurable;
import de.ingrid.utils.PlugDescription;

@Service
public class DocumentProducer implements IDocumentProducer, IConfigurable {

	private final SheetsService _sheetsService;
	private SheetDocumentIterator _sheetDocumentIterator;

	class SheetDocumentIterator implements Iterator<Document> {

		private SheetDocumentIterator _prev;

		private BitSet _documentBitSet = new BitSet();

		private BitSet _mappedBitSet = new BitSet();

		private List<? extends AbstractEntry> _documentEntries = new ArrayList<AbstractEntry>();

		private List<? extends AbstractEntry> _mappedEntries = new ArrayList<AbstractEntry>();

		private final Sheet _sheet;

		private final String _name;

		public SheetDocumentIterator(String name, SheetDocumentIterator prev,
				Sheet sheet) {
			_name = name;
			_prev = prev;
			_sheet = sheet;

			if (_sheet != null) {
				DocumentType documentType = _sheet.getDocumentType();
				switch (documentType) {
				case ROW:
					_documentEntries = _sheet.getRows();
					_mappedEntries = _sheet.getColumns();
					break;
				case COLUMN:
					break;

				default:
					break;
				}

				for (AbstractEntry entry : _documentEntries) {
					_documentBitSet.set(entry.getIndex(), !entry.isExcluded()
							&& entry.isMatchFilter());
					for (AbstractEntry entry2 : _mappedEntries) {
						_mappedBitSet.set(entry2.getIndex(), !entry2
								.isExcluded()
								&& entry2.isMapped());
					}
				}

			}
		}

		public boolean hasNext() {
			boolean hasNext = _prev != null ? _prev.hasNext() : false;
			hasNext = !hasNext ? _documentBitSet.nextSetBit(0) >= 0 : true;
			return hasNext;

		}

		public Document next() {
			Document document = _prev != null && _prev.hasNext() ? _prev.next()
					: createDocument(_documentBitSet.nextSetBit(0));
			return document;
		}

		public void remove() {
			if (_prev != null && _prev.hasNext()) {
				_prev.remove();
			} else {
				int nextBit = _documentBitSet.nextSetBit(0);
				_documentBitSet.flip(nextBit);
			}
		}

		private Document createDocument(int nextBit) {
			Document document = new Document();
			for (int i = _mappedBitSet.nextSetBit(0); i >= 0; i = _mappedBitSet
					.nextSetBit(i + 1)) {
				Comparable<? extends Object> value = _sheet.getValues()
						.getValue(i, nextBit);
				AbstractEntry entry = _mappedEntries.get(i);
				FieldType fieldType = entry.getFieldType();
				String label = entry.getLabel();
				switch (fieldType) {
				case TEXT:
					document.add(new Field(label, value.toString(), Store.NO,
							Index.ANALYZED));
					break;
				case KEYWORD:
				case BOOLEAN:
					document.add(new Field(label, value.toString(), Store.NO,
							Index.NOT_ANALYZED));
					break;
				case NUMBER:
					document.add(new Field(label, StringUtils.padding(Double
							.parseDouble(value.toString())), Store.NO,
							Index.ANALYZED));
				default:
					break;
				}

			}
			remove();
			return document;
		}
	}

	@Autowired
	public DocumentProducer(SheetsService sheetsService) {
		_sheetsService = sheetsService;
	}

	public boolean hasNext() {
		return _sheetDocumentIterator.hasNext();
	}

	public Document next() {
		return _sheetDocumentIterator.next();
	}

	public void initialize() throws Exception {
	}

	public void configure(PlugDescription plugDescription) {
		File workinDirectory = plugDescription.getWorkinDirectory();
		Sheets sheets = (Sheets) plugDescription.get("sheets");
		List<Sheet> sheetsList = sheets.getSheets();
		_sheetDocumentIterator = null;
		for (Sheet sheet : sheetsList) {
			String fileName = sheet.getFileName();
			File xls = new File(workinDirectory, fileName);
			try {
				Sheets tmpSheets = _sheetsService.createSheets(xls);
				List<Sheet> tmpSheetList = tmpSheets.getSheets();
				for (Sheet tmpSheet : tmpSheetList) {
					if (tmpSheet.getSheetIndex() == sheet.getSheetIndex()) {
						Values values = tmpSheet.getValues();
						sheet.setValues(values);
						_sheetDocumentIterator = new SheetDocumentIterator(
								sheet.getFileName() + ".sheet."
										+ sheet.getSheetIndex(),
								_sheetDocumentIterator, sheet);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
