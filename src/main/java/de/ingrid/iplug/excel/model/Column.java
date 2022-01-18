/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2022 wemove digital solutions GmbH
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
package de.ingrid.iplug.excel.model;

/**
 * Class to define column objects.
 *
 */
public class Column extends AbstractEntry {

	public static final String[] LABELS = new String[] { "A", "B", "C", "D",
			"E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public Column() {
		super();
	}

	public Column(final int columnIndex) {
		super(columnIndex);
        setLabel(getDefaultLabel(getIndex()));
	}

    public static String getDefaultLabel(int index) {
	    String label = "";
        while (index >= 0) {
	        final int pos = index % 26;
	        label = LABELS[pos] + label;
	        index = index/26 - 1;
	    }
	    return label;
	}

}
