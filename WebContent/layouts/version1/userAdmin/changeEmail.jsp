<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="recaptcha" uri="/WEB-INF/recaptcha.tld"%>

<script type="text/javascript">
$(document).ready(function() {
	$('#changeEmailForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		handleEmailRequest();
	});
	
	$('#submitChangeEmailBtn').on('click', function(el) {
		$('#changeEmailForm').submit();
	});
	
	$('#changeEmailModal').on('hide.bs.modal', function (e) {
		resetRecaptcha1();
		$('#changeEmailForm').bootstrapValidator('resetForm', true);
		$('#changeEmailForm').trigger('reset');
		$('#alertChangeEmailDiv').html('');
		$('#alertChangeEmailDiv').hide();
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
});

function handleEmailRequest(){
	var postData = $('#changeEmailForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/security/changeEmail.do',
		type:'post',
		cache:'false',
		data:postData,
		success:function(data, textStatus, jqXHR){
			if (data.success == false) {
				resetRecaptcha1();
				$('#alertChangeEmailDiv').html(data.error);
				$('#alertChangeEmailDiv').show();
			} else {
				showAlert('info', 'sm', '<spring:message code="65.msg.success" javaScriptEscape="true" />', 2000);
			}
		},
		error:function(qXHR, textStatus, errorThrown){
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function viewChangeEmail() {
	$('#changeEmailModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="changeEmailModal" tabIndex="-1" role="dialog" aria-labelledby="changeEmailModalLabel" aria-hidden="false">
	<div class="modal-dialog modal-md">
		<form id="changeEmailForm" name="changeEmailForm" class="form-horizontal" role="form">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
					Change User Name/Email Address
				</div>
				
				<div class="modal-body">
					<div id="alertChangeEmailDiv" class="alert alert-danger" style="display:none"></div>
					Changing your User Name/Email Address will require a password reset. Upon submitting your change BuyByeBiz will send you an email where you will 
					be required to click a "Change Email" link to complete the request. After which another email will be sent to you with a new randomn password attached.
					It is advisable that you change the password at your earliest convenience.
					<div style="height:10px"></div>
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								<input id="changeEmailUsername" name="changeEmailUsername" value="${user.email}" type="email" class="form-control" />
							</div>
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
					<button id="submitChangeEmailBtn" type="button" class="btn btn-primary"><spring:message code="0.txt.submit"/></button>
				</div>
			</div>
		</form>
	</div>
</div>