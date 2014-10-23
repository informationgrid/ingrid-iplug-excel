package de.ingrid.iplug.excel;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertiesFiles;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLocations;
import com.tngtech.configbuilder.annotation.valueextractor.DefaultValue;
import com.tngtech.configbuilder.annotation.valueextractor.PropertyValue;

import de.ingrid.admin.IConfig;
import de.ingrid.admin.command.PlugdescriptionCommandObject;

@PropertiesFiles( {"config"} )
@PropertyLocations(directories = {"conf"}, fromClassLoader = true)
public class Configuration implements IConfig {
    
    private static Log log = LogFactory.getLog(Configuration.class);
    
    @PropertyValue("plugdescription.sheets")
    @DefaultValue("")
    public String sheets;
    
    private XStream xstream;
    
    @Override
    public void initialize() {
    }

    @Override
    public void addPlugdescriptionValues( PlugdescriptionCommandObject pdObject ) {
        pdObject.addField("metainfo");
        pdObject.put( "iPlugClass", "de.ingrid.iplug.excel.ExcelPlug");
        
        if(pdObject.get("sheets") == null){
        	if(sheets != null){
        		xstream = new XStream();
            	pdObject.put("sheets", xstream.fromXML(sheets));
        	}
        }
    }

    @Override
    public void setPropertiesFromPlugdescription( Properties props, PlugdescriptionCommandObject pd ) {
    	if(pd.get("sheets") != null){
    		xstream = new XStream();
        	props.setProperty("plugdescription.sheets", xstream.toXML(pd.get("sheets")));
    	}
    }
}
