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
		<h1 id="head">Einzelnen Datensatz ausschließen</h1>
		<c:set var="sheet" value="${sheets.sheets[0]}"/>
		<div class="controls">
			<a href="#" onclick="document.location='mapping.html';">Zurück</a>
			<a href="#" onclick="document.location='welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('excludeDocument').submit();">Speichern</a>
		</div>
		<div class="controls cBottom">
			<a href="#" onclick="document.location='mapping.html';">Zurück</a>
			<a href="#" onclick="document.location='welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('excludeDocument').submit();">Speichern</a>
		</div>
		<div id="content">
			<h2>Sie können für einzelne Datensätze definieren, dass diese nicht berücksichtigt werden sollen</h2>
			<form method="post" action="excludeDocument.html" id="excludeDocument">
				<input type="hidden" name="type" value="${sheet.documentType}"/>
				<table id="konfigForm">
					<tr>
						<td class="leftCol">
							<c:choose>
								<c:when test="${sheet.documentType == 'COLUMN'}">Spalte:</c:when>
								<c:when test="${sheet.documentType == 'ROW'}">Zeile:</c:when>
							</c:choose>
						</td>
						<td>
							<select name="index">
								<c:choose>
									<c:when test="${sheet.documentType == 'COLUMN'}">
										<c:forEach var="doc" items="${sheet.columns}">
											<option value="${doc.index}">${doc.label}</option>
										</c:forEach>
									</c:when>
									<c:when test="${sheet.documentType == 'ROW'}">
										<c:forEach var="doc" items="${sheet.rows}">
											<option value="${doc.index}">${doc.label}</option>
										</c:forEach>
									</c:when>
								</c:choose>
							</select>
						</td>
					</tr>
				</table>
			</form>
			
		</div>
	</div>
	<div id="footer" style="height:100px; width:90%"></div>
</body>
</html>