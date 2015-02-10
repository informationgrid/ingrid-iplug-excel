Excel iPlug
========

The Excel-iPlug connects the data of Excel Files to the InGrid data space.

Features
--------

- index Excel Files at a certain schedule
- user friendly, GUI based mapping of Excel Sheets to an index
- provides search functionality on the indexed data
- GUI for easy administration


Requirements
-------------

- a running InGrid Software System

Installation
------------

Download from https://dev.informationgrid.eu/ingrid-distributions/ingrid-iplug-excel/
 
or

build from source with `mvn package assembly:single`.

Execute

```
java -jar ingrid-iplug-excel-x.x.x-installer.jar
```

and follow the install instructions.

Obtain further information at https://dev.informationgrid.eu/


Contribute
----------

- Issue Tracker: https://github.com/informationgrid/ingrid-iplug-excel/issues
- Source Code: https://github.com/informationgrid/ingrid-iplug-excel
 
### Set up eclipse project

```
mvn eclipse:eclipse
```

and import project into eclipse.

### Debug under eclipse

- execute `mvn install` to expand the base web application
- set up a java application Run Configuration with start class `de.ingrid.iplug.excel.ExcelPlug`
- add the VM argument `-Djetty.webapp=src/main/webapp` to the Run Configuration
- add src/main/resources to class path
- the admin gui starts per default on port 8082, change this with VM argument `-Djetty.port=8083`

Support
-------

If you are having issues, please let us know: info@informationgrid.eu

License
-------

The project is licensed under the EUPL license.
