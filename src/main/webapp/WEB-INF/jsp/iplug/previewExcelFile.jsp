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
		<h1 id="head">Sheet auswählen</h1>
		<div class="controls">
			<a href="#" onclick="document.location='listMappings.html';">Zurück</a>
			<a href="#" onclick="document.location='welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('sheets').submit();">Weiter</a>
		</div>
		<div class="controls cBottom">
			<a href="#" onclick="document.location='listMappings.html';">Zurück</a>
			<a href="#" onclick="document.location='welcome.html';">Abbrechen</a>
			<a href="#" onclick="document.getElementById('sheets').submit();">Weiter</a>
		</div>
		<div id="content">
			<h2>Welches Sheet dieser Datei möchten Sie indexieren?</h2>
			<form:form method="post" action="" modelAttribute="sheets"> 
			<c:forEach items="${sheets.sheets}" var="sheet" varStatus="i">
			<h2>
				<input type="radio" name="sheetIndex" value="${i.index}"/> Sheet${i.index+1}
			</h2>
			<div style="overflow:auto">
			<table class="sheet" cellpadding="0" cellspacing="0">
				<tr>
					<th>&nbsp;</th>
					<c:forEach items="${sheet.columns}" var="column" >
    					<th>${column.label}</th>       	   		
	            	</c:forEach>
				</tr>
	    	   	<c:forEach items="${sheet.rows}" var="row" begin="0" end="9" varStatus="j">
	    	   		<tr>
						<td class="rowCountLabel">${row.label}</td>
						<c:forEach items="${sheet.columns}" var="col" varStatus="k">
							<td>${sheet.valuesAsMap[j.index][k.index]}</td>
						</c:forEach>
					</tr>
		        </c:forEach>
	      	</table>
	      	</div>      	   
	      	<br/><br/>
	      	</c:forEach>
	      	</form:form>
	      </div>	
	</div>
	<div id="footer" style="height:100px; width:90%"></div>
</body>
</html>