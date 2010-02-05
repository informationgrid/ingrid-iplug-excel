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
		<h1 id="head">Zum Index hinzufügen</h1>
		<div class="controls">
			<a href="#" onclick="document.location='/iplug-pages/mapping.html';">Zurück</a>
			<a href="#" onclick="document.location='/base/welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('addToIndex').submit();">Speichern</a>
		</div>
		<div class="controls cBottom">
			<a href="#" onclick="document.location='/iplug-pages/mapping.html';">Zurück</a>
			<a href="#" onclick="document.location='/base/welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('addToIndex').submit();">Speichern</a>
		</div>
		<div id="content">
			<h2>Geben Sie die Eigenschaften des zu indizierenden Feldes an</h2>
			<form method="post" action="addToIndex.html" id="addToIndex">
				<input type="hidden" name="index" value="${index}"/>
				
				<table id="konfigForm">
					<tr>
						<td class="leftCol">
							<c:choose>
								<c:when test="${type == 'COLUMN'}">Spalte:</c:when>
								<c:when test="${type == 'ROW'}">Zeile:</c:when>
							</c:choose>
						</td>
						<td>${label}</td>
					</tr>
					<tr>
						<td class="leftCol">Index Feldname:</td>
						<td>
							<select name="fieldName">
								<option value="">-- bitte wählen --</option>
								<option value="title">title</option>
								<option value="summary">summary</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="leftCol">Oder eigener Index Feldname:</td>
						<td>
							<input type="text" name="ownFieldName" value=""/>
						</td>
					</tr>
					<tr>
						<td class="leftCol">Feld Typ:</td>
						<td>
							<select name="fieldType">
								<c:forEach var="fieldType" items="${fieldTypes}">
									<option value="${fieldType}">${fieldType}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="leftCol">Relative Wertigkeit:</td>
						<td>
							<select name="rank">
								<c:set var="preset" value="5"/>
								<c:forEach var="i" begin="1" end="10">
									<option value="${i}" <c:if test="${i == preset}">selected="selected"</c:if>>${i}</option>
								</c:forEach>
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