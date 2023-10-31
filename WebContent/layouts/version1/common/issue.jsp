<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.mail.GlobalEmailType"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#issueForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		sendIssue();
	});

	$('#sendIssueBtn').on('click', function(el) {
		$('#issueForm').submit();
	});
	
	$('#issueModal').on('hide.bs.modal', function (e) {
		$('#issueForm').bootstrapValidator('resetForm', true);
		$('#issueForm').trigger('reset');
	});
	
	$('#issueModal').on('shown.bs.modal', function () {
		$('#issueFirstName').focus();
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		$('#issueModal').modal('hide');
	});
});

function sendIssue() {
	var postData = $('#issueForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/sendIssue.do',
		type:'post',
		data:postData,
		success:function(data, textStatus, jqXHR) {
			showAlert('success', 'md', "Thank you! The issue has been reported to BuyByeBiz support.", 1500);
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function displayIssue(){
	$('#issueModal').modal();
}
</script>

<div class="modal fade" id="issueModal" tabIndex="-1" role="dialog" aria-labelledby="issueModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<form name="issueForm" id="issueForm" class="form-horizontal" role="form">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title" id="issueModalLabel">Support Issue</h4>
				</div>
				
				<input id="emailTypeId" name="emailTypeId" type="hidden" value="<%=GlobalEmailType.SUPPORT_ISSUE.getId()%>" />
				<div class="modal-body">
					<div class="form-group">
						<div class="col-xs-4"><label for="issueFirstName">First Name:</label></div>
						<div class="col-xs-8">
							<input id="issueFirstName" name="issueFirstName" type="text"  class="form-control" maxLength="256"
								<c:if test="${pageAccess.loggedIn}">value="${pageAccess.user.firstName}" readonly="readonly"</c:if>
								<c:if test="${!pageAccess.loggedIn}">data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>"</c:if> />
						</div>
					</div>
			
					<div class="form-group">
						<div class="col-xs-4"><label for="issueLastName">Last Name:</label></div>
						<div class="col-xs-8">
							<input id="issueLastName" name="issueLastName" type="text"  class="form-control" maxLength="256"
								<c:if test="${pageAccess.loggedIn}">value="${pageAccess.user.lastName}" readonly="readonly"</c:if>
								<c:if test="${!pageAccess.loggedIn}">data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>"</c:if> />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="issueEmail">Email Address:</label></div>
						<div class="col-xs-8">
							<input id="issueEmail" name="issueEmail" type="email"  class="form-control" maxLength="256"
								<c:if test="${pageAccess.loggedIn}">value="${pageAccess.user.email}" readonly="readonly"</c:if>
								<c:if test="${!pageAccess.loggedIn}">
									data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" 
									data-bv-emailaddress-message="<spring:message code="0.msg.invalid.email.warning"/>"</c:if> />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="summary">Summary:</label></div>
						<div class="col-xs-8">
							<input id="email" name="summary" type="text"  class="form-control" maxLength="256" 
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
					
					<div class="form-group">
						<label for="description" class="sr-only">Description</label>
						<div class="col-xs-12">
							<textarea id="description" name="description" class="form-control" rows="3" placeholder="Describe the Issue >" maxLength="1000"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>"></textarea>
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary" id="sendIssueBtn">Send</button>
				</div>
			</form>
		</div>
	</div>
</div>