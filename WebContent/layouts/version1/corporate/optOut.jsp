<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<c:if test="${empty error}">
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/bootstrap/checkbox/js/bootstrap-checkbox.min.js"></script>
<script type="text/javascript">
$(document).ready(function(e) {
	$('#optOutBtn').on('click', function(el) {
		submitOptOut();
	});
	
	$(':checkbox').checkboxpicker();
});

function submitOptOut() {
	var <%=TokenFieldType.EMAIL_ADDRESS.getAlias()%>="${token}";
	$.ajax({
		url:'${pageContext.request.contextPath}/optOut.do',
		type:'post',
		data:{
			<%=TokenFieldType.EMAIL_ADDRESS.getAlias()%>:<%=TokenFieldType.EMAIL_ADDRESS.getAlias()%>,
			enablePromotionalEmail:$('#enablePromotionalEmail').prop('checked')
		},
		success:function(data, textStatus, jqXHR) {
			showAlert('success', 'md', "Your request to opt out of promotional emails was successful.", 2000);
			setTimeout(function() {redirect();}, 2000);
			
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
	
	function redirect() {
		window.location.replace("${pageContext.request.contextPath}/home.html");
	}
}
</script>
</c:if>

<div class="container" style="margin-top:80px">
	<div class="row">
		<div class="col-xs-12" style="text-align:center;">
			<h1>Promotional Email Opt Out</h1>
		</div>
		
		<div class="col-xs-12">
			<p>
				<c:if test="${empty error}">
					 Unsubscribe from promotional emails for ${user.email}: <input  type="checkbox" name="enablePromotionalEmail" id="enablePromotionalEmail" data-group-cls="btn-group-sm" data-off-label="off" data-on-label="on" checked="checked">
					<div style="height:5px"></div>
					<button id="optOutBtn" type="button" class="btn btn-primary">Submit</button>
				</c:if>
				
				<c:if test="${not empty error}">
					There was a problem processing your request: ${error}. Please contact support toll free at ${application.supportPhone} for assistance.
				</c:if>
			</p>
		</div>
	</div>
</div>