package de.ingrid.iplug.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.ingrid.admin.search.IngridIndexSearcher;
import de.ingrid.iplug.HeartBeatPlug;
import de.ingrid.iplug.IPlugdescriptionFieldFilter;
import de.ingrid.iplug.PlugDescriptionFieldFilters;
import de.ingrid.utils.IRecordLoader;
import de.ingrid.utils.IngridHit;
import de.ingrid.utils.IngridHitDetail;
import de.ingrid.utils.IngridHits;
import de.ingrid.utils.dsc.Record;
import de.ingrid.utils.metadata.IMetadataInjector;
import de.ingrid.utils.processor.IPostProcessor;
import de.ingrid.utils.processor.IPreProcessor;
import de.ingrid.utils.query.IngridQuery;

@Service
public class ExcelPlug extends HeartBeatPlug implements IRecordLoader {

	private final IngridIndexSearcher _indexSearcher;

	@Autowired
	public ExcelPlug(final IngridIndexSearcher indexSearcher,
			final IPlugdescriptionFieldFilter[] fieldFilters,
			final IMetadataInjector[] metadataInjectors,
			final IPreProcessor[] preProcessors, final IPostProcessor[] postProcessors) {
        super(60000, new PlugDescriptionFieldFilters(fieldFilters),
				metadataInjectors, preProcessors, postProcessors);
		_indexSearcher = indexSearcher;
	}

	/* (non-Javadoc)
	 * @see de.ingrid.iplug.HeartBeatPlug#close()
	 */
	@Override
	public void close() throws Exception {
		_indexSearcher.close();
	}

	/* (non-Javadoc)
	 * @see de.ingrid.utils.ISearcher#search(de.ingrid.utils.query.IngridQuery, int, int)
	 */
	public IngridHits search(final IngridQuery query, final int start,
			final int length) throws Exception {
        preProcess(query);
		return _indexSearcher.search(query, start, length);
	}

	/* (non-Javadoc)
	 * @see de.ingrid.utils.IDetailer#getDetail(de.ingrid.utils.IngridHit, de.ingrid.utils.query.IngridQuery, java.lang.String[])
	 */
	public IngridHitDetail getDetail(final IngridHit hit,
			final IngridQuery query, final String[] fields) throws Exception {
		final IngridHitDetail detail = _indexSearcher.getDetail(hit, query,
				fields);
		return detail;
	}

	/* (non-Javadoc)
	 * @see de.ingrid.utils.IDetailer#getDetails(de.ingrid.utils.IngridHit[], de.ingrid.utils.query.IngridQuery, java.lang.String[])
	 */
	public IngridHitDetail[] getDetails(final IngridHit[] hitArray,
			final IngridQuery query, final String[] fields) throws Exception {
		final IngridHitDetail[] details = _indexSearcher.getDetails(hitArray,
				query, fields);
		return details;
	}

    /* (non-Javadoc)
     * @see de.ingrid.utils.IRecordLoader#getRecord(de.ingrid.utils.IngridHit)
     */
    @Override
    public Record getRecord(final IngridHit hit) throws Exception {
        return _indexSearcher.getRecord(hit);
    }
}
