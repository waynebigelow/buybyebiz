<%@ page import="ca.app.model.user.ActivityType"%>
<%@ page import="ca.app.model.usage.Area"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="paging" uri="/WEB-INF/paging.tld"%>

<style>
.no-margins {
	margin: 0px 0px 5px 0px;
}
</style>

<script type="text/javascript">
$(document).ready(function(e) {
	var filter = "?area=${page.getInt('area',0)}&action=${page.getInt('action',0)}&ipAddress=${page.getString('ipAddress','')}";
	buildSelect("${pageContext.request.contextPath}/common/listPageHitAreas.json"+filter, "area", "${page.getInt('area',0)}");
	buildSelect("${pageContext.request.contextPath}/common/listPageHitActions.json"+filter, "action", "${page.getInt('action',0)}");
	buildSelect("${pageContext.request.contextPath}/common/listPageHitIPAddresses.json"+filter, "ipAddress", "${page.getString('ipAddress','')}");
});

function handleSearch() {
	document.forms[0].submit();
}

function resetForm() {
	$('#area').val('');
	$('#action').val('');
	$('#ipAddress').val('');
	$('#searchForm').submit();
}
</script>

<form class="form-horizontal" role="form" name="searchForm" id="searchForm">
	<div class="form-group no-margins">
		<div class="col-xs-12 col-sm-3">
			<select id="area" name="area" onchange="handleSearch()" class="form-control"></select>
		</div>

		<div class="col-xs-12 col-sm-3">
			<select id="action" name="action" onchange="handleSearch()" class="form-control"></select>
		</div>
		
		<div class="col-xs-12 col-sm-3">
			<select id="ipAddress" name="ipAddress" onchange="handleSearch()" class="form-control"></select>
		</div>
		
		<div class="col-xs-12 col-sm-3">
			<button type="button" class="btn btn-primary" onclick="resetForm()"><spring:message code="0.txt.reset"/></button>
		</div>
	</div>
</form>

<c:if test="${page.limit > 0 && page.total > page.limit}">
	<ul class="pagination pagination-sm no-margins">
		<paging:first page="${page}" url="${pageContext.request.contextPath}${tab.action}" />
		<paging:previous page="${page}" url="${pageContext.request.contextPath}${tab.action}" />
		<paging:indexes page="${page}" url="${pageContext.request.contextPath}${tab.action}" numIndexes="5" />
		<paging:next page="${page}" url="${pageContext.request.contextPath}${tab.action}" />
		<paging:last page="${page}" url="${pageContext.request.contextPath}${tab.action}" />
	</ul>
</c:if>

<div class="table-responsive table-bordered">
	<table class="table table-hover table-condensed">
		<thead>
			<tr>
				<th width="200"><spring:message code="100.col.1"/></th>
				<th width="300"><spring:message code="100.col.2"/></th>
				<th width="300"><spring:message code="100.col.3"/></th>
				<th>IP Address</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${page.items.size() == 0}">
				<tr>
					<td colspan="4"><spring:message code="100.msg.none.found"/></td>
				</tr>
			</c:if>
			
			<c:forEach items="${page.items}" var="item">
				<tr>
					<td>${item.area}</td>
					<td>${item.action}</td>
					<td>${item.dateTimeFormatted}</td>
					<td>${item.ipAddress}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>