<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="recaptcha" uri="/WEB-INF/recaptcha.tld"%>

<script type="text/javascript">
var requestHandler;
$(document).ready(function() {
	$('#pwdRequestForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		handleRequest();
	});
	
	$('#cancelPwdRequestBtn').click(function(){
		resetRecaptcha1();
		$('#pwdRequestForm').bootstrapValidator('resetForm', true);
		$('#pwdRequestBox').hide();
		$('#changePwdDiv').hide();
		$('#forgotPwdDiv').hide();
		$('#alertPwdRequestDiv').html('');
		$('#alertPwdRequestDiv').hide();
		$('#loginBox').show();
		$('#loginUserName').focus();
	});
	
	$('#submitPwdRequestBtn').on('click', function(el) {
		$('#pwdRequestForm').submit();
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
});

function handleRequest(){
	var postData = $('#pwdRequestForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/security/'+requestHandler+'.do',
		type:'post',
		cache:'false',
		data:postData,
		success:function(data, textStatus, jqXHR){
			if (data.success == false) {
				resetRecaptcha1();
				$('#alertPwdRequestDiv').html(data.error);
				$('#alertPwdRequestDiv').show();
			} else {
				var msg = '<spring:message code="65.msg.success" javaScriptEscape="true" />';
				if (requestHandler == "forgotPwd") {
					msg = '<spring:message code="60.msg.success" javaScriptEscape="true" />';
				}
				showAlert('info', 'sm', msg, 2000);
			}
		},
		error:function(qXHR, textStatus, errorThrown){
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}
</script>

<div id="pwdRequestBox" class="panel panel-default panel-white" style="display:none;">
	<div class="panel-heading">
		<div class="panel-title">
			<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
			<div id="pwdRequestTitle"></div>
		</div>
	</div>
	
	<div class="panel-body">
		<form id="pwdRequestForm" name="pwdRequestForm" class="form-horizontal" role="form">
			<div id="alertPwdRequestDiv" class="alert alert-danger" style="display:none"></div>
		
			<div id="changePwdDiv" style="display:none">
				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
							<input id="changePwdUsername" name="changePwdUsername" type="email" class="form-control" value="" placeholder="<spring:message code="30.plchr.email"/>"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" 
								data-bv-emailaddress-message="<spring:message code="0.msg.invalid.email.warning"/>" />
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
			</div>
			
			<div id="forgotPwdDiv" style="display:none">
				<div class="col-xs-12" style="margin-bottom:10px">
					<spring:message code="60.msg.instruction" />
				</div>
	
				<div style="margin-bottom:25px">
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								<input id="forgotPwdUsername" name="forgotPwdUsername" type="email" class="form-control" value="" placeholder="<spring:message code="30.plchr.email"/>"
									data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" 
									data-bv-emailaddress-message="<spring:message code="0.msg.invalid.email.warning"/>" />
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-xs-12">
				${recaptcha:getReCaptcha('en_US')}
			</div>
			
			<div class="col-xs-12">
				<div style="float:right">
				<button id="cancelPwdRequestBtn" type="button" class="btn btn-default"><spring:message code="0.txt.cancel"/></button>
				<button id="submitPwdRequestBtn" type="button" class="btn btn-primary"><spring:message code="0.txt.submit"/></button>
				</div>
			</div>
		</form>
	</div>
</div>