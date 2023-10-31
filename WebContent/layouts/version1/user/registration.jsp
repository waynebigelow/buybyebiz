<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/passwordStrength/css/strength.css" >

<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/passwordStrength/js/strength.min.js"></script>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#registrationForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		doRegistration();
	});
	
	$('#registrationModal').on('hide.bs.modal', function (e) {
		resetRecaptcha2();
		$('#alertRegDiv').html('');
		$('#alertRegDiv').hide();
		$('#registrationForm').bootstrapValidator('resetForm', true);
	});

	$('#registerLink').on('click', function(el) {
		modalRegister();
	});
	
	$('#registrationModal').on('shown.bs.modal', function () {
		$('#firstName').focus()
	});
});

function doRegistration(){
	var postData = $('#registrationForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/security/registration.do',
		type:'post',
		cache:'false',
		data:postData,
		success:function(data, textStatus, jqXHR){
			if (data.success == false) {
				resetRecaptcha2();
				$('#alertRegDiv').html(data.error);
				$('#alertRegDiv').show();
			} else {
				$('#registrationModal').hide();
				$('#confirmationModal').modal({
					backdrop:'static',
					keyboard:false
				});
			}
		},
		error:function(qXHR, textStatus, errorThrown){
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function modalRegister() {
	$('#registrationModal').modal();
}
</script>

<div class="modal fade" id="registrationModal" tabIndex="-1" role="dialog" aria-labelledby="registrationModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<form class="form-horizontal" role="form" name="registrationForm" id="registrationForm">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
					<h4 class="modal-title" id="registrationModalLabel"><spring:message code="55.title"/></h4>
				</div>
				
				<div class="modal-body">
					<div id="alertRegDiv" class="alert alert-danger" style="display:none"></div>

					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								<input id="firstName" name="firstName" type="text" class="form-control" placeholder="<spring:message code="30.plchr.first.name"/>" maxLength="256"
									data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								<input id="lastName" name="lastName" type="text" class="form-control" placeholder="<spring:message code="30.plchr.last.name"/>" maxLength="256"
									data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								<input id="userName" name="userName" type="email" class="form-control" placeholder="<spring:message code="30.plchr.user.name"/>" maxLength="256"
									data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" 
									data-bv-emailaddress-message="<spring:message code="0.msg.invalid.email.warning"/>" />
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								<input id="password1" name="password1" type="password" class="form-control" placeholder="<spring:message code="30.plchr.password"/>" maxLength="256"
									data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								<input id="password2" name="password2" type="password" class="form-control" placeholder="<spring:message code="30.plchr.confirm.password"/>" maxLength="256"
									data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
							</div>
						</div>
					</div>
					
					<div class="col-md-12 col-lg-6">
						<div class="form-group">
							<div class="checkbox">
								<label>
									<input id="acceptedTOU" name="acceptedTOU" type="checkbox" value="true"
										data-bv-notempty="true" data-bv-notempty-message="<spring:message code="55.msg.tou.warning"/>" />
										<spring:message code="55.lbl.tou" arguments="${pageContext.request.contextPath}/tou.html"/>
								</label>
							</div>
						</div>
					</div>
					
					<div class="col-md-12 col-lg-6">
						<div class="form-group">
							<div class="checkbox">
								<label>
									<input id="acceptedPP" name="acceptedPP" type="checkbox" value="true"
										data-bv-notempty="true" data-bv-notempty-message="<spring:message code="55.msg.pp.warning"/>" />
										<spring:message code="55.lbl.pp" arguments="${pageContext.request.contextPath}/pp.html"/>
								</label>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-12">
							<div style="padding-top:5px;padding-bottom:5px;">
								<p class=instructionText>
									reCAPTCHA is a service that protects BuyByeBiz from spam and abuse. It uses advanced risk analysis techniques to tell humans from harmful bots.
								</p>
								<div id="recaptcha2"></div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
					<button type="submit" class="btn btn-primary"><spring:message code="0.txt.register"/></button>
				</div>
			</form>
		</div>
	</div>
</div>