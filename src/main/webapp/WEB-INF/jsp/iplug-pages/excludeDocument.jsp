<%--
  **************************************************-
  ingrid-iplug-excel
  ==================================================
  Copyright (C) 2014 - 2022 wemove digital solutions GmbH
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
		<h1 id="head">Einzelnen Datensatz ausschließen</h1>
		<div class="controls">
			<a href="#" onclick="document.location='../iplug-pages/mapping.html';">Zurück</a>
			<a href="#" onclick="document.location='../base/welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('excludeDocument').submit();">Speichern</a>
		</div>
		<div class="controls cBottom">
			<a href="#" onclick="document.location='../iplug-pages/mapping.html';">Zurück</a>
			<a href="#" onclick="document.location='../base/welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('excludeDocument').submit();">Speichern</a>
		</div>
		<div id="content">
			<h2>Sie können für einzelne Datensätze definieren, dass diese nicht berücksichtigt werden sollen</h2>
			<form method="post" action="../iplug-pages/excludeDocument.html" id="excludeDocument">
				<div class="input full">
					<input type="hidden" name="type" value="${sheet.documentType}"/>
				</div>
				<table id="konfigForm">
					<tr>
						<td class="leftCol">
							<c:choose>
								<c:when test="${sheet.documentType == 'COLUMN'}">Spalte:</c:when>
								<c:when test="${sheet.documentType == 'ROW'}">Zeile:</c:when>
							</c:choose>
						</td>
						<td>
							<div class="input full">
								<select name="index">
									<c:choose>
										<c:when test="${sheet.documentType == 'COLUMN'}">
											<c:set var="docs" value="${sheet.visibleColumns}" />
										</c:when>
										<c:when test="${sheet.documentType == 'ROW'}">
											<c:set var="docs" value="${sheet.visibleRows}" />
										</c:when>
									</c:choose>
									<c:forEach var="doc" items="${docs}">
	                                    <option value="${doc.index}">${doc.label}</option>
	                                </c:forEach>
								</select>
							</div>
						</td>
					</tr>
				</table>
			</form>
			
		</div>
	</div>
	<div id="footer" style="height:100px; width:90%"></div>
</body>
</html>
