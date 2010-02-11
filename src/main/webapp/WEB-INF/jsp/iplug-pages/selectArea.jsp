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
		<h1 id="head">Globalen Bereich auswählen</h1>
		<div class="controls">
			<a href="#" onclick="document.location='../iplug-pages/mapping.html';">Zurück</a>
			<a href="#" onclick="document.location='../base/welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('selectArea').submit();">Speichern</a>
		</div>
		<div class="controls cBottom">
			<a href="#" onclick="document.location='../iplug-pages/mapping.html';">Zurück</a>
			<a href="#" onclick="document.location='../base/welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('selectArea').submit();">Speichern</a>
		</div>
		<div id="content">
			<h2>Wählen Sie einen Teilbereich Ihres Sheets aus. Außerhalb liegende Daten werden verworfen.</h2>
			<form method="post" action="../iplug-pages/selectArea.html" id="selectArea">
				<table id="konfigForm">
					<tr>
						<td class="leftCol">Von Spalte:</td>
						<td>
							<select name="fromCol">
								<c:forEach var="col" items="${columns}">
									<option value="${col.index}">${col.label}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="leftCol">Bis Spalte:</td>
						<td>
							<select name="toCol">
								<c:forEach var="col" items="${columns}" varStatus="status">
									<option value="${col.index}" <c:if test="${status.last}">selected="selected"</c:if>>${col.label}</option>
								</c:forEach>
							</select>
							</select>
						</td>
					</tr>
					<tr>
						<td class="leftCol">Von Zeile:</td>
						<td>
							<select name="fromRow">
								<c:forEach var="row" items="${rows}">
									<option value="${row.index}">${row.label}</option>
								</c:forEach>
							</select>
							</select>
						</td>
					</tr>
					<tr>
						<td class="leftCol">Bis Zeile:</td>
						<td>
							<select name="toRow">
								<c:forEach var="row" items="${rows}" varStatus="status">
									<option value="${row.index}" <c:if test="${status.last}">selected="selected"</c:if>>${row.label}</option>
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