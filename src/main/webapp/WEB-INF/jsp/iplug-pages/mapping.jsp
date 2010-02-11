<%@ include file="/WEB-INF/jsp/base/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		<div id="language"><a href="#">Englisch</a></div>
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
			<a href="#" onclick="document.location='../iplug-pages/settings.html';">Zurück</a>
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
	      		<input type="button" value="&laquo; ${prev + 1} - ${begin} anzeigen" onclick="document.location = 'mapping.html?begin=${prev}'" />
	      	</c:if>
	      	<c:if test="${nextBegin > end && nextBegin <= last}">
	      		<input type="button" value="${nextBegin + 1} - ${nextEnd + 1} anzeigen &raquo;" onclick="document.location = 'mapping.html?begin=${nextBegin}'" />
	      	</c:if>
        </div>	
	</div>
	<div id="footer" style="height:100px; width:90%"></div>
</body>
</html>