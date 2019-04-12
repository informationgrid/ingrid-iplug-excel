<%--
  **************************************************-
  ingrid-iplug-excel
  ==================================================
  Copyright (C) 2014 - 2019 wemove digital solutions GmbH
  ==================================================
  Licensed under the EUPL, Version 1.1 or – as soon they will be
  approved by the European Commission - subsequent versions of the
  EUPL (the "Licence");
  
  You may not use this work except in compliance with the Licence.
  You may obtain a copy of the Licence at:
  
  http://ec.europa.eu/idabc/eupl5
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the Licence is distributed on an "AS IS" basis,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the Licence for the specific language governing permissions and
  limitations under the Licence.
  **************************************************#
  --%>
<%@ include file="/WEB-INF/jsp/base/include.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="de.ingrid.admin.security.IngridPrincipal"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="de">
<head>
<title>iPlug Administration</title>
<meta name="description" content="" />
<meta name="keywords" content="" />
<meta name="author" content="wemove digital solutions" />
<meta name="copyright" content="wemove digital solutions GmbH" />
<link rel="StyleSheet" href="../css/base/portal_u.css" type="text/css" media="all" />
</head>
<body>
	<div id="header">
		<img src="../images/base/logo.gif" width="168" height="60" alt="Portal" />
		<h1>Konfiguration</h1>
		<%
		java.security.Principal  principal = request.getUserPrincipal();
		if(principal != null && !(principal instanceof IngridPrincipal.SuperAdmin)) {
		%>
			<div id="language"><a href="../base/auth/logout.html">Logout</a></div>
		<%
		}
		%>
	</div>
	
	<div id="help"><a href="#">[?]</a></div>
	
	<c:set var="active" value="mapping" scope="request"/>
	<c:import url="../base/subNavi.jsp"></c:import>
	
	<div id="contentBox" class="contentMiddle">
		<h1 id="head">Daten Mappings</h1>
		<div class="controls">
            <a href="#" onclick="document.location='../base/fieldQuery.html';">Zurück</a>
            <a href="#" onclick="document.location='../base/welcome.html';">Abbrechen</a>
            <a href="#" onclick="document.location='../base/save.html';">Mapping beenden und speichern</a>
        </div>
        <div class="controls cBottom">
            <a href="#" onclick="document.location='../base/fieldQuery.html';">Zurück</a>
            <a href="#" onclick="document.location='../base/welcome.html';">Abbrechen</a>
            <a href="#" onclick="document.location='../base/save.html';">Mapping beenden und speichern</a>
        </div>
		<div id="content">
			<h2>Folgende Excel Sheets sind bereits gemappt</h2>
			
			<table class="data">
				<tr>
					<th>Datei</th>
					<th>Sheet</th>
					<th>Bereich</th>
					<th>Beschreibung</th>
					<th>&nbsp;</th>
				</tr>
				<c:forEach var="sheet" items="${plugDescription.sheets.sheets}" varStatus="status">
				<tr>
					<td>${sheet.fileName}</td>
					<td>Sheet ${sheet.sheetIndex + 1}</td>
					<td><c:if test="${sheet.selected}">von ${sheet.fromColumn.label}/${sheet.fromRow.label} bis ${sheet.toColumn.label}/${sheet.toRow.label}</c:if></td>
					<td>${sheet.description}&nbsp;</td>
					<td>
						<form action="../iplug-pages/deleteMapping.html" method="POST" style="float:left">
							<input type="submit" value="Löschen"/>
							<input type="hidden" name="sheetIndex" value="${status.index}"/>
						</form>
						<form action="../iplug-pages/editMapping.html" method="GET" style="float:left">
							<input type="submit" value="Bearbeiten"/>
							<input type="hidden" name="sheetIndex" value="${status.index}"/>
						</form>
						<form action="../iplug-pages/addMapping.html" method="GET" style="float:left">
							<input type="submit" value="Weiteres Sheet"/>
							<input type="hidden" name="sheetIndex" value="${status.index}"/> 
						</form>
						<form action="../iplug-pages/switchXls.html" method="GET" style="float:left">
							<input type="submit" value="Datei aktualisieren"/>
							<input type="hidden" name="sheetIndex" value="${status.index}"/>
						</form>
					</td>
				</tr>
				</c:forEach>
			</table>
			
			<br/>
			<br/>
			<button onclick="document.location = 'upload.html'">Neue Excel Datei mappen</button>
			
		</div>
	</div>
	<div id="footer" style="height:100px; width:90%"></div>
</body>
</html>
