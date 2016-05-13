<%--
  **************************************************-
  ingrid-iplug-excel
  ==================================================
  Copyright (C) 2014 - 2016 wemove digital solutions GmbH
  ==================================================
  Licensed under the EUPL, Version 1.1 or â€“ as soon they will be
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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="de.ingrid.admin.security.IngridPrincipal"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="de">
<head>
<title>Portal U Administration</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<meta name="author" content="wemove digital solutions" />
<meta name="copyright" content="wemove digital solutions GmbH" />
<link rel="StyleSheet" href="../css/base/portal_u.css" type="text/css" media="all" />
<link rel="StyleSheet" href="../css/iplug-pages/iplug_excel.css" type="text/css" media="all" />
</head>
<body>
	<div id="header">
		<img src="../images/base/logo.gif" width="168" height="60" alt="Portal U" />
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
		<h1 id="head">Sheet auswählen</h1>
		<div class="controls">
			<a href="#" onclick="document.location='../iplug-pages/listMappings.html';">Zurück</a>
			<a href="#" onclick="document.location='../base/welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('sheets').submit();">Weiter</a>
		</div>
		<div class="controls cBottom">
			<a href="#" onclick="document.location='../iplug-pages/listMappings.html';">Zurück</a>
			<a href="#" onclick="document.location='../base/welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('sheets').submit();">Weiter</a>
		</div>
		<div id="content">
			<h2>Welches Sheet dieser Datei möchten Sie indexieren?</h2>
			<form:form action="../iplug-pages/previewExcelFile.html" method="post" modelAttribute="sheets"> 
			<c:forEach items="${sheets.sheets}" var="sheet" >
			<h2>
				<div class="input full">
					<input type="radio" name="sheetIndex" value="${sheet.sheetIndex}"/> Sheet ${sheet.sheetIndex+1}
				</div>
			</h2>
			<div style="overflow:auto">
			    <%@ include file="renderSheet.jsp" %>
	      	</div>      	   
	      	<br/><br/>
	      	</c:forEach>
	      	</form:form>
	      </div>	
	</div>
	<div id="footer" style="height:100px; width:90%"></div>
</body>
</html>
