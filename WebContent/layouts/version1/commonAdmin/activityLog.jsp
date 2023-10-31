<%@ page import="ca.app.model.user.ActivityType"%>
<%@ page import="ca.app.model.usage.Area"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="paging" uri="/WEB-INF/paging.tld"%>

<style>
.no-margins {
	margin: 0px 0px 0px 0px;
}

.margin-bottom {
	margin-bottom: 5px;
}
</style>

<script type="text/javascript">
$(document).ready(function(e) {
	var filter = "?area=${page.getInt('areaId',0)}&type=${page.getInt('typeId',0)}";
	<c:if test="${pageAccess.systemAdmin}">
		filter = filter + "&author=${page.getInt('userId',0)}";
	</c:if>
	buildSelect("${pageContext.request.contextPath}/common/listUserActivityAreas.json"+filter, "area", "${page.getInt('areaId',0)}");
	buildSelect("${pageContext.request.contextPath}/common/listUserActivityTypes.json"+filter, "type", "${page.getInt('typeId',0)}");
	<c:if test="${pageAccess.systemAdmin}">
	buildSelect("${pageContext.request.contextPath}/common/listActivityAuthors.json"+filter, "author", "${page.getInt('userId',0)}");
	</c:if>
});

function handleSearch() {
	document.forms[0].submit();
}

function resetForm() {
	$('#area').val('');
	$('#type').val('');
	$('#author').val('');
	$('#searchForm').submit();
}
</script>

<form class="form-horizontal" role="form" name="searchForm" id="searchForm">
	<c:if test="${!pageAccess.systemAdmin}"><a style="float:right;font-size:1.3em" tabIndex="-1" href="javascript:viewAL()"><span class="glyphicon glyphicon-question-sign"></span></a></c:if>
	<div class="form-group no-margins margin-bottom">
		<div class="col-xs-12 col-sm-3">
			<select id="area" name="area" onchange="handleSearch()" class="form-control"></select>
		</div>

		<div class="col-xs-12 col-sm-3">
			<select id="type" name="type" onchange="handleSearch()" class="form-control"></select>
		</div>
		
		<c:if test="${pageAccess.systemAdmin}">
			<div class="col-xs-12 col-sm-3">
				<select id="author" name="author" onchange="handleSearch()" class="form-control"></select>
			</div>
		</c:if>
		
		<div class="col-xs-12 col-sm-3">
			<button type="button" class="btn btn-primary" onclick="resetForm()"><spring:message code="0.txt.reset"/></button>
		</div>
	</div>
</form>

<c:if test="${page.limit > 0 && page.total > page.limit}">
	<ul class="pagination pagination-sm no-margins">
		<paging:first page="${page}" url="${pageContext.request.contextPath}${tab.action}" />
		<paging:previous page="${page}" url="${pageContext.request.contextPath}${tab.action}" />
		<paging:indexes page="${page}" url="${pageContext.request.contextPath}${tab.action}" numIndexes="10" />
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
				<th><spring:message code="100.col.4"/></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${page.items.size() == 0}">
				<tr>
					<td colspan="4"><spring:message code="100.msg.none.found"/></td>
				</tr>
			</c:if>
			
			<c:forEach items="${page.items}" var="entry">
				<tr>
					<td><spring:message code="${entry.area.i18n}"/></td>
					<td><spring:message code="${entry.type.i18n}"/></td>
					<td>${entry.activityDateFormatted}</td>
					<td>${entry.modifyUserNameFormatted}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<jsp:include page="../help/userAdmin/helpActivityLog.jsp" flush="true" />