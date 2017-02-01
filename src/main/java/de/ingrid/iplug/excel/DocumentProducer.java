/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2017 wemove digital solutions GmbH
 * ==================================================
 * Licensed under the EUPL, Version 1.1 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 * 
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl5
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 * **************************************************#
 */
package de.ingrid.iplug.excel;

import java.io.File;
import java.io.IOException;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.ingrid.admin.StringUtils;
import de.ingrid.admin.elasticsearch.IndexInfo;
import de.ingrid.admin.elasticsearch.StatusProvider;
import de.ingrid.admin.mapping.FieldType;
import de.ingrid.admin.object.IDocumentProducer;
import de.ingrid.iplug.excel.model.AbstractEntry;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.model.Values;
import de.ingrid.iplug.excel.service.SheetsService;
import de.ingrid.utils.ElasticDocument;
import de.ingrid.utils.IConfigurable;
import de.ingrid.utils.PlugDescription;

@Service
public class DocumentProducer implements IDocumentProducer, IConfigurable {

    @Autowired
    private StatusProvider statusProvider;
    
    private SheetDocumentIterator _sheetDocumentIterator;
	
	private int numberOfDocuments = -1;

	static class SheetDocumentIterator implements Iterator<ElasticDocument> {

		@SuppressWarnings("unused")
        private static final Logger LOG = Logger.getLogger(SheetDocumentIterator.class);

		private final SheetDocumentIterator _prev;

		private final BitSet _documentBitSet = new BitSet();

		private final BitSet _mappedBitSet = new BitSet();

        private Collection<? extends AbstractEntry> _documentEntries;

		private Collection<? extends AbstractEntry> _mappedEntries;

		private final Sheet _sheet;

        private Values _values;

        public SheetDocumentIterator(final String name, final SheetDocumentIterator prev, final Sheet sheet) {
			_prev = prev;
			_sheet = sheet;

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
        
        public int getNumberOfDocuments() {
            return _prev != null ? _prev.getNumberOfDocuments() + _documentEntries.size() : _documentEntries.size();
        }

		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			boolean hasNext = _prev != null ? _prev.hasNext() : false;
			hasNext = !hasNext ? _documentBitSet.nextSetBit(0) >= 0 : true;
			return hasNext;

		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public ElasticDocument next() {
			final ElasticDocument document = _prev != null && _prev.hasNext() ? _prev.next()
					: createDocument(_documentBitSet.nextSetBit(0));
			return document;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			if (_prev != null && _prev.hasNext()) {
				_prev.remove();
			} else {
				final int nextBit = _documentBitSet.nextSetBit(0);
				_documentBitSet.flip(nextBit);
			}
		}

		/**
		 * Create Document.
		 * 
		 * @param nextBit
		 * @return
		 * 		Created document.
		 */
		private ElasticDocument createDocument(final int nextBit) {
			final ElasticDocument document = new ElasticDocument();
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
				    document.put( label, value.toString() );
					break;
				case KEYWORD:
				case BOOLEAN:
				case DATE:
				    document.put( label, value.toString() );
					break;
				case NUMBER:
				    document.put(  label, StringUtils.padding(Double.parseDouble(value.toString())) );
					break;

				default:
					break;
				}

				document.put( "content", value.toString());
                //document.add(new Field("content", value.toString(), Store.NO, Index.ANALYZED));

			}
			remove();
			return document;
		}

		/**
		 * Get entry.
		 * 
		 * @param index
		 * @return
		 * 		Existing entry.
		 */
		private AbstractEntry getEntry(final int index) {
		    for (final AbstractEntry entry : _mappedEntries) {
		        if (entry.getIndex() == index) {
		            return entry;
		        }
		    }
		    return null;
		}
	}

	/* (non-Javadoc)
	 * @see de.ingrid.admin.object.IDocumentProducer#hasNext()
	 */
	public boolean hasNext() {
        if (numberOfDocuments < 0) {
            numberOfDocuments = _sheetDocumentIterator.getNumberOfDocuments();
            statusProvider.addState( "FETCH", "Found " + numberOfDocuments + " records.");
        }
		return _sheetDocumentIterator.hasNext();
	}

	/* (non-Javadoc)
	 * @see de.ingrid.admin.object.IDocumentProducer#next()
	 */
	public ElasticDocument next() {
		return _sheetDocumentIterator.next();
	}

	public void initialize() throws Exception {
	}

	/* (non-Javadoc)
	 * @see de.ingrid.utils.IConfigurable#configure(de.ingrid.utils.PlugDescription)
	 */
	public void configure(final PlugDescription plugDescription) {
		final File workinDirectory = plugDescription.getWorkinDirectory();
		final Sheets sheets = (Sheets) plugDescription.get("sheets");
		numberOfDocuments = -1;
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
                                    + sheet.getSheetIndex(), _sheetDocumentIterator, sheet);
                        }
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
		}
	}

    @Override
    public Integer getDocumentCount() {
        return null;
    }

    @Override
    public IndexInfo getIndexInfo() {
        return null;
    }
    
    public void setStatusProvider(StatusProvider statusProvider) {
        this.statusProvider = statusProvider;
    }
}
