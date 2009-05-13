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

					<hr/>
					<a href="finish.html">Weiter</a>



	<c:set var="tableCounter" value="0" />
	<c:forEach items="${tableListCommand.tableCommands}" var="table" >
		<div id="markup_${tableCounter}">
			<table id="table_${tableCounter}">
				<thead>
					<tr>
						<c:forEach items="${table.head.headers}" var="head" >
	    					<th>${head}</th>       	   		
		            	</c:forEach>
					</tr>
				</thead>
				<tbody>
		    	   	<c:forEach items="${table.rows}" var="row" >
		    	   		<tr>
							<c:forEach items="${row.cells}" var="cell" >
								<td>${cell}</td>
							</c:forEach>
						</tr>
			        </c:forEach>
		        </tbody>
	      	</table>
      	</div>

		<script>
		YAHOO.util.Event.addListener(window, "load", function() {
		    YAHOO.example.EnhanceFromMarkup = new function() {
		        var myColumnDefs = [
						<c:set var="headerCounter" value="0" />                            	
						<c:forEach items="${table.head.headers}" var="head" >
							{key:"<form id=\"${tableCounter}_${headerCounter}\" action=\"mapping.html\" method=\"post\"><input name=\"val\" value=\"${head}\"/><input type=\"hidden\" name=\"table\" value=\"${tableCounter}\" /><input type=\"hidden\" name=\"column\" value=\"${headerCounter}\" /><input type=\"submit\"/></form>"},
							<c:set var="headerCounter" value="${headerCounter+1}" />
		            	</c:forEach>
		        ];
		
		
		        this.myDataSource = new YAHOO.util.DataSource(YAHOO.util.Dom.get("table_${tableCounter}"));
		        this.myDataSource.responseType = YAHOO.util.DataSource.TYPE_HTMLTABLE;
		        this.myDataSource.responseSchema = {
		            fields: [
							<c:set var="headerCounter" value="0" />
		     				<c:forEach items="${table.head.headers}" var="head" >
			     				{key:"<form id=\"${tableCounter}_${headerCounter}\" action=\"mapping.html\" method=\"post\"><input name=\"val\" value=\"${head}\"/><input type=\"hidden\" name=\"table\" value=\"${tableCounter}\" /><input type=\"hidden\" name=\"column\" value=\"${headerCounter}\" /><input type=\"submit\"/></form>"},
								<c:set var="headerCounter" value="${headerCounter+1}" />
			            	</c:forEach>
		
		            ]
		        };
		
		        this.myDataTable = new YAHOO.widget.DataTable("markup_${tableCounter}", myColumnDefs, this.myDataSource);
		    };
		});
		
		</script>
      	
      	
	<c:set var="tableCounter" value="${tableCounter+1}" />      	         
	</c:forEach>
</div>







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
