package de.ingrid.iplug.excel;

import org.springframework.stereotype.Service;

import de.ingrid.iplug.IPlugdescriptionFieldFilter;

@Service
public class MappingFilter implements IPlugdescriptionFieldFilter {

	/* (non-Javadoc)
	 * @see de.ingrid.iplug.IPlugdescriptionFieldFilter#filter(java.lang.Object)
	 */
	public boolean filter(Object object) {
		String key = object.toString();
		return "sheets".equals(key) ? true : false;
	}

}
