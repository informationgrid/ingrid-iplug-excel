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
package de.ingrid.iplug.excel;

import com.thoughtworks.xstream.XStream;
import de.ingrid.admin.IConfig;
import de.ingrid.admin.command.PlugdescriptionCommandObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

@org.springframework.context.annotation.Configuration
public class Configuration implements IConfig {

    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog( Configuration.class );

    @Value("${plugdescription.sheets:}")
    public String sheets;

    @Override
    public void initialize() {}

    @Override
    public void addPlugdescriptionValues(PlugdescriptionCommandObject pdObject) {
        pdObject.put( "iPlugClass", "de.ingrid.iplug.excel.ExcelPlug" );

        if (pdObject.get( "sheets" ) == null) {
            if (!sheets.equals( "" )) {
                XStream xstream = new XStream();
                pdObject.put( "sheets", xstream.fromXML( sheets ) );
            }
        }
    }

    @Override
    public void setPropertiesFromPlugdescription(Properties props, PlugdescriptionCommandObject pd) {
        if (pd.get( "sheets" ) != null) {
            props.setProperty( "plugdescription.sheets", this.sheets );
        }
    }
}
