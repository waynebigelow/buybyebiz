<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.model.configuration.AdminTabType"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
var activityTemplate = null;
$(document).ready(function(e) {
	activityTemplate = Handlebars.compile($('#activity-template').html());
});

function activityLog(v, type){
	loadActivity(v, type);
		
	displayActivityLog();
}

function loadActivity(v, type) {
	$('#activityLogTable').html('<tr><td colspan="4"><spring:message code="100.msg.none.found"/></td></tr>');
	console.log(v);
	console.log(type);
	$.getJSON('${pageContext.request.contextPath}/admin/activityLog.do', {
		<%=TokenFieldType.PRIMARY_ID.getAlias()%>:v,
		logType:type
	})
	.done(function(data) {
		drawActivity(data);
	});
}

function drawActivity(activities) {
	var activityHtml = "";
	console.log(activities);
	$.each(activities, function(i, activity) {
		var context = {a:activity.area,b:activity.type,c:activity.activityDateFormatted,d:activity.modifyUser.displayName};
		activityHtml = activityHtml + activityTemplate(context);
	});
	
	if (activityHtml != ""){
		$('#activityLogTable').html(activityHtml);
		
		$('[data-toggle="confirmation"]').confirmation({popout:true});
	}
}

function displayActivityLog(){
	$('#activityLogModal').modal();
}
</script>

<script id="activity-template" type="text/x-handlebars-template">
<tr>
	<td>{{a}}</td>
	<td>{{b}}</td>
	<td>{{c}}</td>
	<td>{{d}}</td>
</tr>
</script>

<div class="modal fade" id="activityLogModal" tabIndex="-1" role="dialog" aria-labelledby="activityLogModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="activityLogModalLabel"><spring:message code="<%=AdminTabType.ACTIVITY_LOG.getI18n()%>"/></h4>
			</div>
					
			<div class="modal-body">
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
						<tbody id="activityLogTable">
							<tr>
								<td colspan="4"><spring:message code="100.msg.none.found"/></td>
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