package de.ingrid.iplug.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.lucene.document.Document;

import de.ingrid.admin.StringUtils;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.FieldType;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.service.SheetsService;
import de.ingrid.utils.PlugDescription;

public class DocumentProducerTest extends TestCase {

	public void testProduce() throws Exception {
		DocumentProducer documentProducer = new DocumentProducer(
				new SheetsService());

		// setup plugdescription
		PlugDescription plugDescription = new PlugDescription();
		File xls = new File("src/test/resources/test.xls");
		plugDescription.setWorkinDirectory(xls.getParentFile());
		Sheets sheets = new SheetsService().createSheets(xls);
		// setup 0te sheet
		Sheet sheet = sheets.getSheets().get(0);
		sheet.setFileName("test.xls");
		sheet.setDocumentType(DocumentType.ROW);
		Column column = sheet.getColumns().get(1);
		column.setFieldType(FieldType.TEXT);
		column.setMapped(true);
		column = sheet.getColumns().get(2);
		column.setFieldType(FieldType.NUMBER);
		column.setMapped(true);
		Row row = sheet.getRows().get(2);
		row.setMatchFilter(true);
		row = sheet.getRows().get(3);
		row.setMatchFilter(true);

		// setup 1te sheet
		sheet = sheets.getSheets().get(1);
		sheet.setFileName("test.xls");
		sheet.setDocumentType(DocumentType.ROW);
		column = sheet.getColumns().get(1);
		column.setFieldType(FieldType.NUMBER);
		column.setMapped(true);
		column = sheet.getColumns().get(2);
		column.setFieldType(FieldType.TEXT);
		column.setMapped(true);
		row = sheet.getRows().get(2);
		row.setMatchFilter(true);
		row = sheet.getRows().get(3);
		row.setMatchFilter(true);
		row = sheet.getRows().get(4);
		row.setMatchFilter(true);

		sheets.getSheets().remove(2);

		plugDescription.put("sheets", sheets);

		documentProducer.configure(plugDescription);
		List<Document> documents = new ArrayList<Document>();
		while (documentProducer.hasNext()) {
			Document next = documentProducer.next();
			assertNotNull(next);
			documents.add(next);
		}
		assertEquals(5, documents.size());
		Document document = documents.get(0);
		assertEquals("Foo", document.get("B"));
		assertEquals(StringUtils.padding(32), document.get("C"));

		document = documents.get(1);
		assertEquals("Foobar", document.get("B"));
		assertEquals(StringUtils.padding(2323), document.get("C"));

		document = documents.get(2);
		assertEquals(StringUtils.padding(139), document.get("B"));
		assertEquals("130,90", document.get("C"));

		document = documents.get(3);
		assertEquals(StringUtils.padding(229), document.get("B"));
		assertEquals("200,99", document.get("C"));

		document = documents.get(4);
		assertEquals(StringUtils.padding(189), document.get("B"));
		assertEquals("150", document.get("C"));


	}
}
