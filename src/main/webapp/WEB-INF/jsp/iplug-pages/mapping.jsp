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
<link rel="StyleSheet" href="../css/iplug-pages/iplug_excel.css" type="text/css" media="all" />
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
		<h1 id="head">Mapping der Daten auf den Index</h1>
		<div class="controls">
			<a href="#" onclick="document.location='../iplug-pages/settings.html';">Zurück</a>
			<a href="#" onclick="document.location='../base/welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('submit').submit();">Mapping hinzufügen</a>
		</div>
		<div class="controls cBottom">
			<a href="#" onclick="document.location='../iplug-pages/settings.html';">Zurüäßßck</a>
			<a href="#" onclick="document.location='../base/welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('submit').submit();">Mapping hinzufügen</a>
		</div>
		<form action="../iplug-pages/finish.html" method="post" style="display:none" id="submit">
			
		</form>
		
		<div id="content">
			<h2>Definieren Sie, was indexiert werden soll</h2>
			<a href="selectArea.html">Globalen Bereich Auswählen</a>
			<c:choose>
				<c:when test="${sheet.selected}">
					von ${sheet.fromColumn.label}/${sheet.fromRow.label} bis ${sheet.toColumn.label}/${sheet.toRow.label}
				</c:when>
				<c:otherwise>
					nicht gesetzt
				</c:otherwise>
			</c:choose>
			
			<br/>
			<c:choose>
				<c:when test="${sheet.documentType == 'COLUMN'}">
					<a href="../iplug-pages/excludeDocument.html">Einzelne Spalte ausschließen</a>
					<c:forEach var="col" items="${sheet.excludedColumns}">
					    ${col.label} <a href="../iplug-pages/removeExclusion.html?index=${col.index}"> <img src="../images/iplug-pages/delete.png" border="0" align="absmiddle"/></a>
					</c:forEach>
				</c:when>
				<c:when test="${sheet.documentType == 'ROW'}">
					<a href="../iplug-pages/excludeDocument.html">Einzelne Zeile ausschließen</a>
					<c:forEach var="row" items="${sheet.excludedRows}">
					    ${row.label} <a href="../iplug-pages/removeExclusion.html?index=${row.index}"> <img src="../images/iplug-pages/delete.png" border="0" align="absmiddle"/></a>
					</c:forEach>
				</c:when>
			</c:choose>
			
			<br/>
			<br/>
			
			<div style="overflow:auto">
			    <c:set var="render" value="true" />
			    <%@ include file="renderSheet.jsp" %>
	      	</div>      	   
	      	
	      	<c:if test="${begin > 0}">
	      		<div class="input full">
					<input type="button" value="&laquo; ${prev + 1} - ${begin} anzeigen" onclick="document.location = 'mapping.html?begin=${prev}'" />
				</div>
	      	</c:if>
	      	<c:if test="${nextBegin > end && nextBegin <= last}">
	      		<div class="input full">
					<input type="button" value="${nextBegin + 1} - ${nextEnd + 1} anzeigen &raquo;" onclick="document.location = 'mapping.html?begin=${nextBegin}'" />
				</div>
	      	</c:if>
        </div>	
	</div>
	<div id="footer" style="height:100px; width:90%"></div>
</body>
</html>
