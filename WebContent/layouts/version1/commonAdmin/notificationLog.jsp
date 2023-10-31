<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="paging" uri="/WEB-INF/paging.tld"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

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
	buildSelect("${pageContext.request.contextPath}/common/listAddressees.json", "addressee", "${notificationLog.getInt('userId',0)}");
});

function deleteNotificationLog(v){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/deleteNotificationLog.do',
		type:'post',
		data:{
			<%=TokenFieldType.NOTIFICATION_LOG.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			window.location.reload(true);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function handleSearch() {
	document.forms[0].submit();
}

function resetForm() {
	$('#addressee').val('');
	$('#searchForm').submit();
}

<c:if test="${pageAccess.systemAdmin}">
function sendEmailNow(v) {
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/sendEmail.do',
		type:'post',
		data:{
			<%=TokenFieldType.NOTIFICATION_LOG.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			showAlert('success', 'sm', "The email has been sent.", 1500);
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}
</c:if>
</script>

<form class="form-horizontal" role="form" name="searchForm" id="searchForm">
	<div class="form-group no-margins margin-bottom">
		<div class="col-xs-12 col-sm-4">
			<select id="addressee" name="addressee" onchange="handleSearch()" class="form-control"></select>
		</div>
		
		<div class="col-xs-12 col-sm-3">
			<button type="button" class="btn btn-primary" onclick="resetForm()"><spring:message code="0.txt.reset"/></button>
		</div>
	</div>
</form>

<c:if test="${notificationLog.limit > 0 && notificationLog.total > notificationLog.limit}">
	<ul class="pagination pagination-sm no-margins">
		<paging:previous page="${notificationLog}" url="${pageContext.request.contextPath}${tab.action}" />
		<paging:indexes page="${notificationLog}" url="${pageContext.request.contextPath}${tab.action}" numIndexes="10" />
		<paging:next page="${notificationLog}" url="${pageContext.request.contextPath}${tab.action}" />
	</ul>
</c:if>
<div style="height:5px"></div>
<c:if test="${pageAccess.systemAdmin}">
	<button type="button" class="btn btn-primary" onclick="displayEmail()">Send Email</button>
	<div style="height:5px"></div>
</c:if>

<div class="table-responsive table-bordered">
	<table class="table table-hover table-condensed">
		<thead>
			<tr>
				<th><spring:message code="40.col.1"/></th>
				<th><spring:message code="40.col.2"/></th>
				<th><spring:message code="40.col.3"/></th>
				<th><spring:message code="40.col.4"/></th>
				<th><spring:message code="40.col.5"/></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${notificationLog.items.size() == 0}">
				<tr>
					<td colspan="5"><spring:message code="40.msg.none.found"/></td>
				</tr>
			</c:if>
			
			<c:forEach items="${notificationLog.items}" var="entry">
				<tr>
					<td>
						<div class="toolbox">
							<span class="dropdown">
								<span class="glyphicon glyphicon-menu-hamburger editIcon" id="menu${entry.token}" data-toggle="dropdown"></span>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dropDown">
									<c:if test="${pageAccess.systemAdmin}"><li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:sendEmailNow('${entry.token}')"><span class="glyphicon glyphicon-pencil"></span> <spring:message code="40.btn.1"/></a></li></c:if>
									<li role="presentation"><a role="menuitem" tabIndex="-1" data-onConfirm="deleteNotificationLog('${entry.token}')" data-container="body" data-singleton="true" data-placement="top" data-toggle="confirmation" data-title="<spring:message code="40.msg.delete.warning"/>"><span class="glyphicon glyphicon-trash"></span> <span class="editIcon"><spring:message code="0.txt.delete"/></span></a></li>
								</ul>
							</span>
						</div>
					</td>
					<td>${entry.addressee}</td>
					<td>${entry.subject}</td>
					<td>${entry.sentTimeFormatted}</td>
					<td>${entry.application.name}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<c:if test="${pageAccess.systemAdmin}">
	<jsp:include page="../appAdmin/email.jsp" flush="true" />
</c:if>