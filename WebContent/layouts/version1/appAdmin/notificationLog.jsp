<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.model.configuration.AdminTabType"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
var notificationTemplate = null;
$(document).ready(function(e) {
	notificationTemplate = Handlebars.compile($('#notification-template').html());
});

function notificationLog(v, type){
	loadNotifications(v, type);
		
	displayNotificationLog();
}

function loadNotifications(v, type) {
	$('#notificationLogTable').html('<tr><td colspan="3"><spring:message code="40.msg.none.found"/></td></tr>');
	$.getJSON('${pageContext.request.contextPath}/admin/notificationLog.do', {
		<%=TokenFieldType.NOTIFICATION_LOG.getAlias()%>:v,
		logType:type
	})
	.done(function(data) {
		drawNotifications(data);
	});
}

function drawNotifications(notifications) {
	var notificationsHtml = "";
	$.each(notifications, function(i, notification) {
		var context = {n:notification.token,subject:notification.subject,sentTime:notification.sentTimeFormatted};
		notificationsHtml = notificationsHtml + notificationTemplate(context);
	});
	
	if (notificationsHtml != ""){
		$('#notificationLogTable').html(notificationsHtml);
		
		$('[data-toggle="confirmation"]').confirmation({popout:true});
	}
}

function displayNotificationLog(){
	$('#notificationLogModal').modal();
}

function deleteNotificationLog(v, p, type){
	$.ajax({
		url:'${pageContext.request.contextPath}/Appdmin/deleteNotificationLog.do',
		type:'post',
		data:{
			<%=TokenFieldType.NOTIFICATION_LOG.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			loadNotifications(v, type);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function sendEmailNow(n) {
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/sendEmail.do',
		type:'post',
		data:{
			nl:n,
			type:type
		},
		success:function(data, textStatus, jqXHR) {
			showAlert('success', 'sm', "The email has been sent.", 1500);
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}
</script>

<script id="notification-template" type="text/x-handlebars-template">
<tr>
	<td>
		<div class="toolbox">
			<span class="dropdown">
				<span class="glyphicon glyphicon-menu-hamburger editIcon" id="menu{{n}}" data-toggle="dropdown"></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="dropDown">
					<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:sendEmailNow('{{n}}')"><span class="glyphicon glyphicon-pencil"></span> <spring:message code="40.btn.1"/></a></li>
					<li role="presentation"><a role="menuitem" tabIndex="-1" data-onConfirm="deleteNotificationLog('{{n}}','{{p}}','{{type}}')" data-container="body" data-singleton="true" data-placement="top" data-toggle="confirmation" data-title="<spring:message code="40.msg.delete.warning"/>"><span class="glyphicon glyphicon-trash"></span> <span class="editIcon"><spring:message code="0.txt.delete"/></span></a></li>
				</ul>
			</span>
		</div>
	</td>
	<td>{{subject}}</td>
	<td>{{sentTime}}</td>
</tr>
</script>

<div class="modal fade" id="notificationLogModal" tabIndex="-1" role="dialog" aria-labelledby="notificationLogModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="notificationLogModalLabel"><spring:message code="<%=AdminTabType.NOTIFICATION_LOG.getI18n()%>"/></h4>
			</div>
					
			<div class="modal-body">
				<div class="table-responsive table-bordered">
					<table class="table table-hover table-condensed">
						<thead>
							<tr>
								<th><spring:message code="40.col.1"/></th>
								<th><spring:message code="40.col.3"/></th>
								<th><spring:message code="40.col.4"/></th>
							</tr>
						</thead>
						<tbody id="notificationLogTable">
							<tr>
								<td colspan="3"><spring:message code="40.msg.none.found"/></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.close"/></button>
			</div>
		</div>
	</div>
</div>