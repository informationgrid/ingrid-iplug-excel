package de.ingrid.iplug.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.ingrid.admin.IngridIndexSearcher;
import de.ingrid.utils.IPlug;
import de.ingrid.utils.IngridHit;
import de.ingrid.utils.IngridHitDetail;
import de.ingrid.utils.IngridHits;
import de.ingrid.utils.PlugDescription;
import de.ingrid.utils.query.IngridQuery;

@Service
public class ExcelPlug implements IPlug {

	private final IngridIndexSearcher _indexSearcher;

	@Autowired
	public ExcelPlug(IngridIndexSearcher indexSearcher) {
		_indexSearcher = indexSearcher;
	}

	public void close() throws Exception {
		_indexSearcher.close();
	}

	public void configure(PlugDescription plugDescription) throws Exception {
		_indexSearcher.configure(plugDescription);
	}

	public IngridHits search(IngridQuery query, int start, int length)
			throws Exception {
		IngridHits ingridHits = _indexSearcher.search(query, start, length);
		return ingridHits;
	}

	public IngridHitDetail getDetail(IngridHit hit, IngridQuery query,
			String[] fields) throws Exception {
		IngridHitDetail detail = _indexSearcher.getDetail(hit, query, fields);
		return detail;
	}

	public IngridHitDetail[] getDetails(IngridHit[] hitArray,
			IngridQuery query, String[] fields) throws Exception {
		IngridHitDetail[] details = _indexSearcher.getDetails(hitArray, query,
				fields);
		return details;
	}

}
