package de.ingrid.iplug.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.ingrid.admin.search.IngridIndexSearcher;
import de.ingrid.iplug.HeartBeatPlug;
import de.ingrid.iplug.IPlugdescriptionFieldFilter;
import de.ingrid.iplug.PlugDescriptionFieldFilters;
import de.ingrid.utils.IngridHit;
import de.ingrid.utils.IngridHitDetail;
import de.ingrid.utils.IngridHits;
import de.ingrid.utils.metadata.IMetadataInjector;
import de.ingrid.utils.processor.IPostProcessor;
import de.ingrid.utils.processor.IPreProcessor;
import de.ingrid.utils.query.IngridQuery;

@Service
public class ExcelPlug extends HeartBeatPlug {

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

	@Override
	public void close() throws Exception {
		_indexSearcher.close();
	}

	public IngridHits search(final IngridQuery query, final int start,
			final int length) throws Exception {
		return _indexSearcher.search(query, start, length);
	}

	public IngridHitDetail getDetail(final IngridHit hit,
			final IngridQuery query, final String[] fields) throws Exception {
		final IngridHitDetail detail = _indexSearcher.getDetail(hit, query,
				fields);
		return detail;
	}

	public IngridHitDetail[] getDetails(final IngridHit[] hitArray,
			final IngridQuery query, final String[] fields) throws Exception {
		final IngridHitDetail[] details = _indexSearcher.getDetails(hitArray,
				query, fields);
		return details;
	}
}
