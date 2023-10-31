<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="recaptcha" uri="/WEB-INF/recaptcha.tld"%>

<script type="text/javascript">
$(document).ready(function() {
	$('#changePwdForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		handleRequest();
	});
	
	$('#submitChangePwdBtn').on('click', function(el) {
		$('#changePwdForm').submit();
	});
	
	$('#changePwdModal').on('hide.bs.modal', function (e) {
		resetRecaptcha1();
		$('#changePwdForm').bootstrapValidator('resetForm', true);
		$('#changePwdForm').trigger('reset');
		$('#alertChangePwdDiv').html('');
		$('#alertChangePwdDiv').hide();
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
});

function handleRequest(){
	var postData = $('#changePwdForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/security/changePwd.do',
		type:'post',
		cache:'false',
		data:postData,
		success:function(data, textStatus, jqXHR){
			if (data.success == false) {
				resetRecaptcha1();
				$('#alertChangePwdDiv').html(data.error);
				$('#alertChangePwdDiv').show();
			} else {
				showAlert('info', 'sm', '<spring:message code="65.msg.success" javaScriptEscape="true" />', 2000);
			}
		},
		error:function(qXHR, textStatus, errorThrown){
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function viewChangePwd() {
	$('#changePwdModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="changePwdModal" tabIndex="-1" role="dialog" aria-labelledby="changePwdModalLabel" aria-hidden="false">
	<div class="modal-dialog modal-md">
		<form id="changePwdForm" name="changePwdForm" class="form-horizontal" role="form">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
					Change Password
				</div>
				
				<div class="modal-body">
					<div id="alertChangePwdDiv" class="alert alert-danger" style="display:none"></div>
						
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								<input id="changePwdUsername" name="changePwdUsername" value="${user.email}" type="email" class="form-control" readonly="readonly" />
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								<input id="oldPassword" name="oldPassword" type="password" class="form-control" placeholder="<spring:message code="30.plchr.old.password"/>"
									data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
							</div>
						</div>
					</div>
						
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								<input id="newPassword1" name="newPassword1" type="password" class="form-control" placeholder="<spring:message code="30.plchr.new.password"/>"
									data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
							</div>
						</div>
					</div>
					
					<div class="form-group" style="margin-bottom:25px">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								<input id="newPassword2" name="newPassword2" type="password" class="form-control" placeholder="<spring:message code="30.plchr.confirm.password"/>"
									data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-12">
							${recaptcha:getReCaptcha('en_US')}
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
					<button id="submitChangePwdBtn" type="button" class="btn btn-primary"><spring:message code="0.txt.submit"/></button>
				</div>
			</div>
		</form>
	</div>
</div>