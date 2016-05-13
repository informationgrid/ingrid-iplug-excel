/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2016 wemove digital solutions GmbH
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tngtech.configbuilder.ConfigBuilder;

import de.ingrid.admin.JettyStarter;
import de.ingrid.admin.elasticsearch.IndexImpl;
import de.ingrid.iplug.HeartBeatPlug;
import de.ingrid.iplug.IPlugdescriptionFieldFilter;
import de.ingrid.iplug.PlugDescriptionFieldFilters;
import de.ingrid.utils.IRecordLoader;
import de.ingrid.utils.IngridCall;
import de.ingrid.utils.IngridDocument;
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

	private final IndexImpl _indexSearcher;
	public static Configuration conf;
	
	@Autowired
	public ExcelPlug(final IndexImpl indexSearcher,
			final IPlugdescriptionFieldFilter[] fieldFilters,
			final IMetadataInjector[] metadataInjectors,
			final IPreProcessor[] preProcessors, final IPostProcessor[] postProcessors) {
        super(60000, new PlugDescriptionFieldFilters(fieldFilters),
				metadataInjectors, preProcessors, postProcessors);
		_indexSearcher = (IndexImpl) indexSearcher;
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
    
    public static void main(String[] args) throws Exception {
        conf = new ConfigBuilder<Configuration>(Configuration.class).withCommandLineArgs(args).build();
        new JettyStarter( conf );
    }

    @Override
    public IngridDocument call(IngridCall targetInfo) throws Exception {
        throw new RuntimeException( "call-function not implemented in Excel-iPlug" );
    }
}
