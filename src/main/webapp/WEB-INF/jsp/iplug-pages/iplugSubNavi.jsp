<%@ include file="/WEB-INF/jsp/base/include.jsp" %>
<!-- mapping -->
<li <c:if test="${active == 'mapping'}">class="active"</c:if>>
<c:choose>
    <c:when test="${plugdescriptionExists}"><a href="../iplug-pages/listMappings.html">Excel Daten Mapping</a></c:when>
    <c:otherwise>Excel Daten Mapping</c:otherwise>
</c:choose>
</li>