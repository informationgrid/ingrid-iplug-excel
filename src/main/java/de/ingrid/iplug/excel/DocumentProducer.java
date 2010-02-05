package de.ingrid.iplug.excel;

import java.io.File;
import java.io.IOException;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.ingrid.admin.StringUtils;
import de.ingrid.admin.mapping.FieldType;
import de.ingrid.admin.object.IDocumentProducer;
import de.ingrid.admin.search.Stemmer;
import de.ingrid.iplug.excel.model.AbstractEntry;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.model.Values;
import de.ingrid.iplug.excel.service.SheetsService;
import de.ingrid.utils.IConfigurable;
import de.ingrid.utils.PlugDescription;

@Service
public class DocumentProducer implements IDocumentProducer, IConfigurable {

	private SheetDocumentIterator _sheetDocumentIterator;
	private final Stemmer _stemmer;

	static class SheetDocumentIterator implements Iterator<Document> {

		private static final Logger LOG = Logger
				.getLogger(SheetDocumentIterator.class);

		private final SheetDocumentIterator _prev;

		private final BitSet _documentBitSet = new BitSet();

		private final BitSet _mappedBitSet = new BitSet();

        private Collection<? extends AbstractEntry> _documentEntries;

		private Collection<? extends AbstractEntry> _mappedEntries;

		private final Sheet _sheet;

		private final String _name;

		private final Stemmer _stemmer;

        private Values _values;

        public SheetDocumentIterator(final String name, final SheetDocumentIterator prev, final Sheet sheet,
                final Stemmer stemmer) {
			_name = name;
			_prev = prev;
			_sheet = sheet;
			_stemmer = stemmer;

			if (_sheet != null) {
				final DocumentType documentType = _sheet.getDocumentType();
				switch (documentType) {
				case ROW:
                    _documentEntries = _sheet.getVisibleRows();
                    _mappedEntries = _sheet.getVisibleColumns();
					break;
				case COLUMN:
                    _documentEntries = _sheet.getVisibleColumns();
                    _mappedEntries = _sheet.getVisibleRows();
					break;

				default:
					break;
				}

                for (final AbstractEntry doc : _documentEntries) {
                    _documentBitSet.set(doc.getIndex(), doc.isMatchFilter());
                    for (final AbstractEntry entry : _mappedEntries) {
                        _mappedBitSet.set(entry.getIndex(), entry.isMapped());
					}
				}

				_values = _sheet.getValues();
			}
		}

		public boolean hasNext() {
			boolean hasNext = _prev != null ? _prev.hasNext() : false;
			hasNext = !hasNext ? _documentBitSet.nextSetBit(0) >= 0 : true;
			return hasNext;

		}

		public Document next() {
			final Document document = _prev != null && _prev.hasNext() ? _prev.next()
					: createDocument(_documentBitSet.nextSetBit(0));
			return document;
		}

		public void remove() {
			if (_prev != null && _prev.hasNext()) {
				_prev.remove();
			} else {
				final int nextBit = _documentBitSet.nextSetBit(0);
				_documentBitSet.flip(nextBit);
			}
		}

		private Document createDocument(final int nextBit) {
			final Document document = new Document();
			for (int i = _mappedBitSet.nextSetBit(0); i >= 0; i = _mappedBitSet
					.nextSetBit(i + 1)) {

				Comparable<? extends Object> value = null;
				switch(_sheet.getDocumentType()) {
				case ROW:
				    value = _values.getValue(i, nextBit);
				    break;
				case COLUMN:
				    value = _values.getValue(nextBit, i);
				    break;
				default:
				    break;
				}
				if (value == null ) {
				    continue;
				}
				final AbstractEntry entry = getEntry(i);
				final FieldType fieldType = entry.getFieldType();
				final String label = entry.getLabel();
				switch (fieldType) {
				case TEXT:
					document.add(new Field(label, value.toString(), Store.YES,
							Index.ANALYZED));
					break;
				case KEYWORD:
				case BOOLEAN:
				case DATE:
					document.add(new Field(label, value.toString(), Store.YES,
							Index.NOT_ANALYZED));
					break;
				case NUMBER:
					document.add(new Field(label, StringUtils.padding(Double
							.parseDouble(value.toString())), Store.YES,
							Index.NOT_ANALYZED));
					break;

				default:
					break;
				}
				try {
					document.add(new Field("content", _stemmer.stem(value
							.toString()), Store.NO, Index.ANALYZED));
				} catch (final IOException e) {
					LOG.warn(_name + ": can not stem content: "
							+ value.toString(), e);
				}
                document.add(new Field("content", value.toString(), Store.NO,
						Index.ANALYZED));

			}
			remove();
			return document;
		}

		private AbstractEntry getEntry(final int index) {
		    for (final AbstractEntry entry : _mappedEntries) {
		        if (entry.getIndex() == index) {
		            return entry;
		        }
		    }
		    return null;
		}
	}

    @Autowired
    public DocumentProducer(final Stemmer stemmer) {
        _stemmer = stemmer;
    }

	public boolean hasNext() {
		return _sheetDocumentIterator.hasNext();
	}

	public Document next() {
		return _sheetDocumentIterator.next();
	}

	public void initialize() throws Exception {
	}

	public void configure(final PlugDescription plugDescription) {
		final File workinDirectory = plugDescription.getWorkinDirectory();
		final Sheets sheets = (Sheets) plugDescription.get("sheets");
        if (sheets != null) {
            final List<Sheet> sheetsList = sheets.getSheets();
            _sheetDocumentIterator = null;
            for (final Sheet sheet : sheetsList) {
                final String fileName = sheet.getFileName();
                final File xls = new File(workinDirectory, "mapping" + File.separator + fileName);
                try {
                    final Sheets tmpSheets = SheetsService.createSheets(xls);
                    final List<Sheet> tmpSheetList = tmpSheets.getSheets();
                    for (final Sheet tmpSheet : tmpSheetList) {
                        if (tmpSheet.getSheetIndex() == sheet.getSheetIndex()) {
                            final Values values = tmpSheet.getValues();
                            sheet.setValues(values);
                            _sheetDocumentIterator = new SheetDocumentIterator(sheet.getFileName() + ".sheet."
                                    + sheet.getSheetIndex(), _sheetDocumentIterator, sheet, _stemmer);
                        }
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
		}
	}
}
