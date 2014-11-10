<%--
  **************************************************-
  ingrid-iplug-excel
  ==================================================
  Copyright (C) 2014 wemove digital solutions GmbH
  ==================================================
  Licensed under the EUPL, Version 1.1 or – as soon they will be
  approved by the European Commission - subsequent versions of the
  EUPL (the "Licence");
  
  You may not use this work except in compliance with the Licence.
  You may obtain a copy of the Licence at:
  
  http://ec.europa.eu/idabc/eupl5
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the Licence is distributed on an "AS IS" basis,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the Licence for the specific language governing permissions and
  limitations under the Licence.
  **************************************************#
  --%>
<c:if test="${empty begin}"><c:set var="begin" value="0" /></c:if>
<c:if test="${empty end}"><c:set var="end" value="9" /></c:if>
<c:set var="values" value="${sheet.visibleValues}" />
<c:if test="${empty render}"><c:set var="render" value="false" /></c:if>

<table class="sheet" cellpadding="0" cellspacing="0">
    <c:if test="${render && sheet.documentType == 'ROW'}">
		<!-- index functions row -->
		<tr>
			<td class="fn">&nbsp;</td>
			<c:forEach items="${sheet.visibleColumns}" var="col" >
				<td class="fn" valign="top">
					<c:choose>
						<c:when test="${col.isMapped}">
						    <b>${col.label}</b> <a href="../iplug-pages/removeFromIndex.html?index=${col.index}"><img src="../images/iplug-pages/delete.png" border="0" align="absmiddle"/></a><br/>
						</c:when>
						<c:otherwise>
						    <img src="../images/iplug-pages/add.png" border="0" align="absmiddle"> <a href="../iplug-pages/addToIndex.html?index=${col.index}">Indizieren</a><br/>
						</c:otherwise>
					</c:choose>
					<c:forEach var="f" items="${col.filters}" varStatus="i">
					    ${f.filterType} ${f.expression} <a href="../iplug-pages/removeFilter.html?index=${col.index}&filterIndex=${i.index}"><img src="../images/iplug-pages/delete.png" border="0" align="absmiddle"/></a><br/>
					</c:forEach>
					<c:if test="${col.isMapped}">
					    <img src="../images/iplug-pages/add.png" border="0" align="absmiddle"> <a href="../iplug-pages/addFilter.html?index=${col.index}">Filter</a>
					</c:if>
				
			</c:forEach>
		</tr>
	    <!-- index functions row -->
	</c:if>
    
	<tr>
	   <c:if test="${render && sheet.documentType == 'COLUMN'}">
           <td class="fn">&nbsp;</td>
       </c:if>
	   <th>&nbsp;</th>
	   <c:forEach items="${sheet.visibleColumns}" var="column" >
	       <th
	           <c:if test="${render && sheet.documentType == 'COLUMN'}">
		           <c:choose>
		               <c:when test="${column.matchFilter}">style="background: #77EF99"</c:when>
		               <c:otherwise>style="background: #EF9977"</c:otherwise>
		           </c:choose>
	           </c:if>
	       >${column.label}</th>
	   </c:forEach>
	</tr>
	
	<c:forEach items="${sheet.visibleRows}" var="row" begin="${begin}" end="${end}" varStatus="r">
	   <tr>
	       <c:if test="${render && sheet.documentType == 'COLUMN'}">
	           <!-- index functions column -->
               <td class="fn">
                   <c:choose>
                       <c:when test="${row.isMapped}">
                           <b>${row.label}</b> <a href="../iplug-pages/removeFromIndex.html?index=${row.index}"><img src="../images/iplug-pages/delete.png" border="0" align="absmiddle"/></a><br/>
                       </c:when>
                       <c:otherwise>
                           <img src="../images/iplug-pages/add.png" border="0" align="absmiddle"> <a href="../iplug-pages/addToIndex.html?index=${row.index}">Indizieren</a><br/>
                       </c:otherwise>
                   </c:choose>
                   <c:forEach var="f" items="${row.filters}" varStatus="i">
                       ${f.filterType} ${f.expression} <a href="../iplug-pages/removeFilter.html?index=${row.index}&filterIndex=${i.index}"><img src="../images/iplug-pages/delete.png" border="0" align="absmiddle"/></a><br/>
                   </c:forEach>
                   <c:if test="${row.isMapped}">
                       <img src="../images/iplug-pages/add.png" border="0" align="absmiddle"> <a href="../iplug-pages/addFilter.html?index=${row.index}">Filter</a>
                   </c:if>
               </td>
	           <!-- index functions column -->
           </c:if>
	       <td class="rowCountLabel"
	           <c:if test="${render && sheet.documentType == 'ROW'}">
                   <c:choose>
                       <c:when test="${row.matchFilter}">style="background: #77EF99"</c:when>
                       <c:otherwise>style="background: #EF9977"</c:otherwise>
                   </c:choose>
               </c:if>
	       >${row.label}</td>
	       <c:forEach items="${values[r.index]}" var="value">
	           <td>${value}&nbsp;</td>
	       </c:forEach>
	   </tr>
	</c:forEach>
</table>