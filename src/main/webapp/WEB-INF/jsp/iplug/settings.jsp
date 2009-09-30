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
<link rel="StyleSheet" href="../css/iplug/iplug_excel.css" type="text/css" media="all" />
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
		<h1 id="head">Welche Daten?</h1>
		<div class="controls">
			<a href="#" onclick="document.location='listMappings.html';">Zurück</a>
			<a href="#" onclick="document.location='welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('settings').submit();">Weiter</a>
		</div>
		<div class="controls cBottom">
			<a href="#" onclick="document.location='listMappings.html';">Zurück</a>
			<a href="#" onclick="document.location='welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('settings').submit();">Weiter</a>
		</div>
		<div id="content">
			<h2>Weitere Einstellungen</h2>
			<form method="post" action="" id="settings"> 
				<table id="konfigForm">
					<tr>
						<td class="leftCol">Ein Datensatz ist:</td>
						<td>
							<select>
								<option value="row">eine Zeile</option>
								<option value="column">eine Spalte</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="leftCol">Label:</td>
						<td>
							<input type="checkbox" name="ignoreFirst" value="true"/> Erste Zeile / Spalte enthält Überschriften
						</td>
					</tr>
					<tr>
						<td class="leftCol">Sheet:</td>
						<td>
							<select name="sheet">
								<option value="0">Sheet1</option>
								<option value="1">Sheet2</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
			
			
			<c:set var="tableCounter" value="0" />
			<h2>Vorschau Sheet${tableCounter+1}</h2>
			<c:forEach items="${tableListCommand.tableCommands}" var="table" >
			<table id="table_${tableCounter}" class="sheet" cellpadding="0" cellspacing="0">
				<tr>
					<th>&nbsp;</th>
					<c:forEach items="${table.head.headers}" var="head" >
    					<th>${head}</th>       	   		
	            	</c:forEach>
				</tr>
	    	   	<c:forEach items="${table.rows}" var="row" begin="0" end="4" varStatus="i">
	    	   		<tr>
						<td class="rowCountLabel">${i.index +1}</td>
						<c:forEach items="${row.cells}" var="cell" >
							<td>${cell}</td>
						</c:forEach>
					</tr>
		        </c:forEach>
	      	</table>
	      	<c:set var="tableCounter" value="${tableCounter+1}" />      	   
	      	</c:forEach>
	      </div>	
	</div>
	<div id="footer" style="height:100px; width:90%"></div>
</body>
</html>