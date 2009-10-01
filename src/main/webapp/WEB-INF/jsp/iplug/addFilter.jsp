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
		<h1 id="head">Filter anwenden</h1>
		<div class="controls">
			<a href="#" onclick="document.location='mapping.html';">Zur�ck</a>
			<a href="#" onclick="document.location='welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('addFilter').submit();">Speichern</a>
		</div>
		<div class="controls cBottom">
			<a href="#" onclick="document.location='mapping.html';">Zur�ck</a>
			<a href="#" onclick="document.location='welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('addFilter').submit();">Speichern</a>
		</div>
		<div id="content">
			<h2>Legen Sie �ber einen Filter fest, welche Daten indiziert werden sollen</h2>
			<form method="post" action="addFilter.html" id="addFilter">
				<table id="konfigForm">
					<tr>
						<td class="leftCol">Zeile / Spalte:</td>
						<td>A</td>
					</tr>
					<tr>
						<td class="leftCol">Filter:</td>
						<td>
							<select name="filter">
								<option value="">ist gleich</option>
								<option value="">ist ungleich</option>
								<option value="">enth�lt</option>
								<option value="">enth�lt nicht</option>
								<option value="">ist gr��er als</option>
								<option value="">ist kleiner als</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="leftCol">Wert:</td>
						<td>
							<input type="text" name="value" value=""/>
						</td>
					</tr>
				</table>
			</form>
			
		</div>
	</div>
	<div id="footer" style="height:100px; width:90%"></div>
</body>
</html>