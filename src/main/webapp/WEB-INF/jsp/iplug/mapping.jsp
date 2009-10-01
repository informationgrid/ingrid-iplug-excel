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
			<a href="#" onclick="document.location='settings.html';">Zurück</a>
			<a href="#" onclick="document.location='welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.location='../base/save.html';">Mapping Beenden und Speichern</a>
		</div>
		<div class="controls cBottom">
			<a href="#" onclick="document.location='settings.html';">Zurück</a>
			<a href="#" onclick="document.location='welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.location='../base/save.html';">Mapping Beenden und Speichern</a>
		</div>
		<div id="content">
			<h2>Excel Sheet</h2>
			<a href="">Globalen Bereich Auswählen</a>
			<c:forEach items="${tableListCommand.tableCommands}" var="table" >
			<div style="overflow:auto">
			<table id="table_${tableCounter}" class="sheet" cellpadding="0" cellspacing="0">
				<tr>
					<td style="background:#F4F4F4">&nbsp;</td>
					<td style="background:#F4F4F4">&nbsp;</td>
					<c:forEach items="${table.head.headers}" var="head" varStatus="i">
    					<td style="background:#F4F4F4">
    						<!-- instead of i.index use isIndexed or isFiltered -->
    						<c:choose>
    							<c:when test="${i.index == 2 }">
    								<b>title</b> <a href=""><img src="../images/iplug/delete.png" border="0" align="absmiddle"/></a><br/>
    								<b>!= 'aaa'</b> <a href=""><img src="../images/iplug/delete.png" border="0" align="absmiddle"></a>
    							</c:when>
    							<c:otherwise>
		    						<img src="../images/iplug/add.png" border="0" align="absmiddle"> <a href="addToIndex.html">Indizieren</a><br/>
		    						<img src="../images/iplug/add.png" border="0" align="absmiddle"> <a href="addFilter.html">Filter</a>
    							</c:otherwise>
    						</c:choose>
    					</td>       	   		
	            	</c:forEach>
				</tr>
				<tr>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
					<c:forEach items="${table.head.headers}" var="head" varStatus="i">
    					<c:choose>
    							<c:when test="${i.index == 2 }">
			    					<th style="background:#DDE9BD;">title</th>       	   		
    							</c:when>
    							<c:otherwise>
    								<th>${head}</th>
    							</c:otherwise>
    						</c:choose>
	            	</c:forEach>
				</tr>
	    	   	<c:forEach items="${table.rows}" var="row" begin="0" end="9" varStatus="i">
	    	   		<tr>
						<td style="background:#F4F4F4; white-space:nowrap;">
							<img src="../images/iplug/add.png" border="0" align="absmiddle"> <a href="addToIndex.html">Indizieren</a><br/>
		    				<img src="../images/iplug/add.png" border="0" align="absmiddle"> <a href="addFilter.html">Filter</a>
						</td>
						<td class="rowCountLabel">${i.index +1}</td>
						<c:forEach items="${row.cells}" var="cell" varStatus="j">
							<td <c:if test="${j.index == 2 }">style="background:#F5F8EB"</c:if>>${cell}</td>
						</c:forEach>
					</tr>
		        </c:forEach>
	      	</table>
	      	</div>
	      	<button>&laquo; Vorherige</button>
	      	<button>Nächste &raquo;</button>
	      	</c:forEach>
	      	
	      	<br/><br/>
	      	<h2>Index Vorschau</h2>
	      	<table class="data">
	      		<tr>
	      			<th width="40">Doc #</th>
	      			<th>title</th>
	      			<th>description</th>
	      		</tr>
	      		<tr>
	      			<td>1</td>
	      			<td>headline</td>
	      			<td>description</td>
	      		</tr>
	      	</table>
	      	<br/><br/>
	      	
	      </div>	
	</div>
	<div id="footer" style="height:100px; width:90%"></div>
</body>
</html>