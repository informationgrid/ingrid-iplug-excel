package de.ingrid.iplug.excel;

import java.util.List;
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

@PropertiesFiles({ "config" })
@PropertyLocations(directories = { "conf" }, fromClassLoader = true)
public class Configuration implements IConfig {

    private static Log log = LogFactory.getLog( Configuration.class );

    @PropertyValue("plugdescription.fields")
    @DefaultValue("")
    public List<String> fields;

    @PropertyValue("plugdescription.sheets")
    @DefaultValue("")
    public String sheets;

    @PropertyValue("plugdescription.isRecordLoader")
    @DefaultValue("false")
    public boolean recordLoader;

    @PropertyValue("plugdescription.ranking")
    public String rankings;

    private XStream xstream;

    @Override
    public void initialize() {}

    @Override
    public void addPlugdescriptionValues(PlugdescriptionCommandObject pdObject) {
        pdObject.put( "iPlugClass", "de.ingrid.iplug.excel.ExcelPlug" );

        for (String field : fields) {
            pdObject.remove( field );
            pdObject.addField( field );
        }

        pdObject.setRecordLoader( recordLoader );
        if (pdObject.getRankingTypes().length == 0) {
            if (rankings != null) {
                String[] rankingList = rankings.split( "," );
                boolean score = false;
                boolean date = false;
                boolean notRanked = false;
                for (String ranking : rankingList) {
                    if (ranking.equals( "score" )) {
                        score = true;
                    } else if (ranking.equals( "date" )) {
                        date = true;
                    } else if (ranking.equals( "notRanked" )) {
                        notRanked = true;
                    }
                }
                pdObject.setRankinTypes( score, date, notRanked );
            }
        }

        if (pdObject.get( "sheets" ) == null) {
            if (!sheets.equals( "" )) {
                xstream = new XStream();
                pdObject.put( "sheets", xstream.fromXML( sheets ) );
            }
        }
    }

    @Override
    public void setPropertiesFromPlugdescription(Properties props, PlugdescriptionCommandObject pd) {
        if (pd.get( "sheets" ) != null) {
            xstream = new XStream();
            props.setProperty( "plugdescription.sheets", xstream.toXML( pd.get( "sheets" ) ) );
        }
    }
}
