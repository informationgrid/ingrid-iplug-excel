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
		<h1 id="head">Daten Mappings</h1>
		<div id="content">
			<h2>Folgende Excel Sheets sind bereits gemappt</h2>
			<table class="data">
				<tr>
					<th>Pfad</th>
					<th>Sheet</th>
					<th>&nbsp;</th>
				</tr>
				<tr>
					<td>/abc/test.xls</td>
					<td>Sheet1</td>
					<td>
						<form>
							<button>Löschen</button>
							<button>Bearbeiten</button>
						</form>
					</td>
				</tr>
			</table>
			
			<br/>
			<br/>
			<button onclick="document.location = 'upload.html'">Neue Excel Datei mappen</button>
			
		</div>
	</div>
	<div id="footer" style="height:100px; width:90%"></div>
</body>
</html>