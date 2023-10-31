<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="paging" uri="/WEB-INF/paging.tld"%>
<%@ page import="ca.app.model.usage.Area"%>
<style>
.no-margins {
	margin: 0px 0px 0px 0px;
}

.margin-bottom {
	margin-bottom: 5px;
}
</style>
<script type="text/javascript">
function handleSearch() {
	document.forms[0].submit();
}
</script>

<form class="form-horizontal" role="form" name="searchForm" id="searchForm">
	<div class="form-group no-margins margin-bottom">
		<div class="col-xs-4 col-sm-2"><label for="filter">Filter by:</label></div>
		<div class="col-xs-8 col-sm-3">
			<select id="area" name="area" onchange="handleSearch()" class="form-control">
				<option value="0">--All Activity--</option>
				<option value="<%=Area.LISTING.getId()%>" <c:if test="${areaId eq '40'}">selected="selected"</c:if>><%=Area.LISTING.name()%></option>
				<option value="<%=Area.USER_ACCOUNT.getId()%>" <c:if test="${areaId eq '50'}">selected="selected"</c:if>><%=Area.USER_ACCOUNT.name()%></option>
			</select>
		</div>
	</div>
</form>

<c:if test="${activityLog.limit > 0 && activityLog.total > activityLog.limit}">
	<ul class="pagination pagination-sm no-margins">
		<paging:previous page="${activityLog}" url="${pageContext.request.contextPath}${tab.action}" />
		<paging:indexes page="${activityLog}" url="${pageContext.request.contextPath}${tab.action}" numIndexes="10" />
		<paging:next page="${activityLog}" url="${pageContext.request.contextPath}${tab.action}" />
	</ul>
</c:if>

<div class="table-responsive table-bordered">
	<table class="table table-hover table-condensed">
		<thead>
			<tr>
				<th><spring:message code="100.col.1"/></th>
				<th><spring:message code="100.col.2"/></th>
				<th><spring:message code="100.col.3"/></th>
				<th><spring:message code="100.col.4"/></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${activityLog.items.size() == 0}">
				<tr>
					<td colspan="4"><spring:message code="100.msg.none.found"/></td>
				</tr>
			</c:if>
			
			<c:forEach items="${activityLog.items}" var="entry">
				<tr>
					<td>${entry.area}</td>
					<td>${entry.type}</td>
					<td>${entry.activityDateFormatted}</td>
					<td>${entry.modifyUser.displayName}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
