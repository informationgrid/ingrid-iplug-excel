<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/WEB-INF/jsp/base/include.jsp" %>
<html>
<head>
	<title>iPlug Excel</title>
	<link rel="stylesheet" type="text/css" href="../css/yui/reset-fonts-grids/reset-fonts-grids.css">
	
	<!-- Combo-handled YUI CSS files: -->
	<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/combo?2.7.0/build/datatable/assets/skins/sam/datatable.css">
	<!-- Combo-handled YUI JS files: -->
	<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.7.0/build/yahoo-dom-event/yahoo-dom-event.js&2.7.0/build/dragdrop/dragdrop-min.js&2.7.0/build/element/element-min.js&2.7.0/build/datasource/datasource-min.js&2.7.0/build/datatable/datatable-min.js"></script>
	 
	<!--begin custom header content for this example-->
	<style type="text/css">
		/* custom styles for this example */
		.yui-skin-sam .yui-dt-liner { white-space:nowrap; } 
	</style>
	
	
						
<script type="text/javascript" >
<c:set var="tableCounter" value="0" />
<c:forEach items="${tableListCommand.tableCommands}" var="table" >
YAHOO.example.Data_${tableCounter} = {
    bookorders: [
        <c:forEach items="${table.rows}" var="row" >
        {
        	<c:set var="columnCounter" value="0" />
			<c:forEach items="${row.cells}" var="cell" >
				${table.head.headers[columnCounter]}:"${cell}",
				<c:set var="columnCounter" value="${columnCounter+1}" />
			</c:forEach>
        },
        </c:forEach>         
    ]
}

YAHOO.util.Event.addListener(window, "load", function() {
    YAHOO.example.Basic = function() {
        var myColumnDefs = [
            <c:forEach items="${table.head.headers}" var="head" >
        	   	{key:"${head}", resizeable:true, editor: new YAHOO.widget.TextareaCellEditor()},
            </c:forEach>         
        ];

        var myDataSource = new YAHOO.util.DataSource(YAHOO.example.Data_${tableCounter}.bookorders);
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
        myDataSource.responseSchema = {
            fields: [
         			<c:forEach items="${table.head.headers}" var="head" >
                	   	"${head}",
                    </c:forEach>         
                    ]
        };

        var myDataTable = new YAHOO.widget.DataTable("basic_${tableCounter}",
                myColumnDefs, myDataSource, {caption:"DataTable Caption"});

        myDataTable.subscribe("cellClickEvent", myDataTable.onEventShowCellEditor); 
        
        return {
            oDS: myDataSource,
            oDT: myDataTable
        };
    }();
});


<c:set var="tableCounter" value="${tableCounter+1}" />
</c:forEach>
</script>	
	
	
	
</head>




<body class=" yui-skin-sam">


<!-- the id on the containing div determines the page width. -->
<!-- #doc = 750px; #doc2 = 950px; #doc3 = 100%; #doc4 = 974px -->
<div id="doc">					
	<div id="hd">
		<p>Header</p>
	</div>
	<div id="bd">

		<!-- Use Standard Nesting Grids and Special Nesting Grids to subdivid regions of your layout. -->
		<!-- Special Nesting Grid B tells three children to split space evenly -->
		<div class="yui-gb">
	
			<!-- the first child of a Grid needs the "first" class -->
			<div class="yui-u first">
				<p>
				</p>
			</div>	
	
			<div class="yui-u">
				<p>

					<c:set var="tableCounter" value="0" />
					<c:forEach items="${tableListCommand.tableCommands}" var="table" >
						<div id="basic_${tableCounter}"></div>
						<form:form method="post" modelAttribute="tableListCommand">
			    			<fieldset>
	        					<legend>Index Feldnamen abändern</legend>
	        					<c:set var="headerCounter" value="0" />
	        					<c:forEach items="${table.head.headers}" var="head" >
							        <form:input path="tableCommands[${tableCounter}].head.headers[${headerCounter}]" />
							        <c:set var="headerCounter" value="${headerCounter+1}" />
            					</c:forEach>         
						        <input type="submit" value="Umbenennen"> 
							</fieldset>
						</form:form>
						
						<c:set var="tableCounter" value="${tableCounter+1}" />
					</c:forEach>						
					
					<hr/>
					<form:form method="post" modelAttribute="tableListCommand">
						<input type="hidden" name="finish"/>
						<input type="submit" value="Weiter">
					</form:form>
					
				</p>
			</div>
	
			<div class="yui-u">
				<p>C</p>
			</div>
	
		</div>
	
	</div>
	<div id="ft">
		<p>Footer</p>
	</div>
</div>
</body>
</html>
