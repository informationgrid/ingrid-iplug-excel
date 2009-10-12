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
		<h1 id="head">Mapping der Daten auf den Index</h1>
		<div class="controls">
			<a href="#" onclick="document.location='settings.html';">Zurück</a>
			<a href="#" onclick="document.location='welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('submit').submit();">Mapping Beenden und Speichern</a>
		</div>
		<div class="controls cBottom">
			<a href="#" onclick="document.location='settings.html';">Zurück</a>
			<a href="#" onclick="document.location='welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('submit').submit();">Mapping Beenden und Speichern</a>
		</div>
		<form action="finish.html" method="post" style="display:none" id="submit">
			
		</form>
		
		<div id="content">
			<c:set var="sheet" value="${sheets.sheets[0]}"/>
			<h2>Definieren Sie, was indexiert werden soll</h2>
			<a href="selectArea.html">Globalen Bereich Auswählen</a>
			&nbsp;|&nbsp;
			<c:choose>
				<c:when test="${sheet.documentType == 'COLUMN'}">
					<a href="excludeDocument.html">Einzelne Spalte ausschließen</a>
				</c:when>
				<c:when test="${sheet.documentType == 'ROW'}">
					<a href="excludeDocument.html">Einzelne Zeile ausschließen</a>
				</c:when>
			</c:choose>
			
			<br/>
			<div style="overflow:auto">
			<table class="sheet" cellpadding="0" cellspacing="0">
				<c:if test="${sheet.documentType == 'ROW'}">
					<!-- index functions column -->
					<tr>
						<td class="fn">&nbsp;</td>
						<c:forEach items="${sheet.columns}" var="col" >
    						<c:if test="${!col.excluded}">
    						<td class="fn" valign="top">
    							<c:choose>
    								<c:when test="${col.isMapped}">
    									<b>${col.label}</b> <a href="removeFromIndex.html?type=col&index=${col.index}"><img src="../images/iplug/delete.png" border="0" align="absmiddle"/></a><br/>
    								</c:when>
    								<c:otherwise>
		    							<img src="../images/iplug/add.png" border="0" align="absmiddle"> <a href="addToIndex.html?type=col&index=${col.index}&label=${col.label}">Indizieren</a><br/>
    								</c:otherwise>
    							</c:choose>
    							
   								<c:forEach var="f" items="${col.filters}" varStatus="i">
   									${f.fieldType} ${f.expression} <a href="removeFilter.html?type=col&index=${col.index}&filterIndex=${i.index}"><img src="../images/iplug/delete.png" border="0" align="absmiddle"/></a><br/>
   								</c:forEach>
		    					<c:if test="${col.isMapped}">
		    						<img src="../images/iplug/add.png" border="0" align="absmiddle"> <a href="addFilter.html?type=col&index=${col.index}&label=${col.label}">Filter</a>
		    					</c:if>
    						</td>
    						</c:if>    	   		
	            		</c:forEach>
					</tr>
				
				</c:if>
				
				<tr>
					<c:if test="${sheet.documentType == 'COLUMN'}">
						<td class="fn">&nbsp;</td>
					</c:if>
					<th>&nbsp;</th>
					<c:forEach items="${sheet.columns}" var="col" >
						<c:if test="${!col.excluded}">
	    					<th <c:if test="${col.isMapped}">style="background:#DDE9BD"</c:if>>${col.label}</th>       	   		
						</c:if>
	            	</c:forEach>
				</tr>
	    	   	<c:forEach items="${sheet.rows}" var="row" begin="0" end="9">
	    	   		<c:if test="${!row.excluded}">
	    	   		<tr>
		    	   		<c:if test="${sheet.documentType == 'COLUMN'}">
		    	   			<!-- index functions column -->
		    	   			<td class="fn">
		    	   				<c:choose>
    								<c:when test="${row.isMapped}">
    									<b>${row.label}</b> <a href="removeFromIndex.html?type=row&index=${row.index}"><img src="../images/iplug/delete.png" border="0" align="absmiddle"/></a><br/>
    								</c:when>
    								<c:otherwise>
		    							<img src="../images/iplug/add.png" border="0" align="absmiddle"> <a href="addToIndex.html?type=row&index=${row.index}&label=${row.label}">Indizieren</a><br/>
    								</c:otherwise>
    							</c:choose>
    							
    							<c:forEach var="f" items="${row.filters}" varStatus="i">
   									${f.fieldType} ${f.expression} <a href="removeFilter.html?type=row&index=${row.index}&filterIndex=${i.index}"><img src="../images/iplug/delete.png" border="0" align="absmiddle"/></a><br/>
   								</c:forEach>
		    					<c:if test="${row.isMapped}">
		    					<img src="../images/iplug/add.png" border="0" align="absmiddle"> <a href="addFilter.html?type=row&index=${row.index}&label=${row.label}">Filter</a>
		    					</c:if>
		    	   			</td>
		    	   		</c:if>
						
						<td class="rowCountLabel">${row.label}</td>
						<c:forEach items="${sheet.columns}" var="col">
							<c:if test="${!col.excluded}">
							<td <c:if test="${col.isMapped || row.isMapped}">class="mapped"</c:if>>${sheet.valuesAsMap[row.index][col.index]}&nbsp;</td>
							</c:if>
						</c:forEach>
					</tr>
					</c:if>
		        </c:forEach>
	      	</table>
	      	</div>      	   
        </div>	
	</div>
	<div id="footer" style="height:100px; width:90%"></div>
</body>
</html>