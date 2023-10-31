<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.util.ProjectUtil"%>

<style>
.loginPanelBox {
	margin-top: 50px;
}

.form-horizontal .form-group {
	margin-right: 0px;
	margin-left: 0px;
}
</style>

<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/other/js/js.cookie.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	$('#loginForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		$('#j_username').val('${application.token}:' + $('#loginUserName').val());
		$('#j_password').val($('#loginPassword').val());
		
		var postData = $('#loginForm').serializeArray();
		console.log(postData);
		$.ajax({
			url:'${pageContext.request.contextPath}/j_spring_security_check',
			type:'post',
			cache:'false',
			data:postData,
			success:function(data, textStatus, jqXHR){
				window.location.href="${pageContext.request.contextPath}/home.html";
			},
			error:function(qXHR, textStatus, errorThrown){
				$('#j_password').val('');
				$('#loginPassword').val('');
				$('#alertLoginFailedDiv').show();
			}
		});
	});
	
	var username = Cookies.get('rememberUsername');
	if (username) {
		$('#loginUserName').val(username);
		$('#rememberUsername').prop('checked',true);
		$('#loginModal').on('shown.bs.modal', function () {
			$('#loginPassword').focus();
		});
	} else {
		$('#loginModal').on('shown.bs.modal', function () {
			$('#loginUserName').focus();
		});
	}
	
	$('#loginBtn').on('click', function(el) {
		var cookieDomain = '<%=ProjectUtil.getProperty("sso.cookie.domain")%>';
		if ($('#rememberUsername').prop('checked')) {
			Cookies.set('rememberUsername', $('#loginUserName').val(), {
				path:'',
				domain:cookieDomain,
				secure:true,
				expires:(365*5)
			});
		} else {
			Cookies.remove('rememberUsername',{path:'',domain:cookieDomain});
		}
		$('#loginForm').submit();
	});
	
	$('#changePwdLink').click(function(){
		loginHandler();
		$('#pwdRequestTitle').html('<spring:message code="65.title"/>');
		$('#pwdRequestBox').show();
		$('#changePwdDiv').show();
		$('#changePwdUsername').val($('#loginUserName').val());
		$('#changePwdUsername').focus();
		requestHandler = "changePwd";
	});
	
	$('#forgotPwdLink').click(function(){
		loginHandler();
		$('#pwdRequestTitle').html('<spring:message code="60.title"/>');
		$('#pwdRequestBox').show();
		$('#forgotPwdDiv').show();
		$('#forgotPwdUsername').val($('#loginUserName').val());
		$('#forgotPwdUsername').focus();
		requestHandler = "forgotPwd";
	});
	
	$('#loginLink').on('click', function(el) {
		doLogin();
	});
	
	$('#loginModal').on('hide.bs.modal', function (e) {
		$('#loginForm').bootstrapValidator('resetForm', true);
		$('#loginForm').trigger('reset');
	});
});

function loginHandler() {
	$('#loginForm').bootstrapValidator('resetForm');
	$('#loginBox').hide();
	$('#alertLoginFailedDiv').html('');
	$('#alertLoginFailedDiv').hide();
}

<c:if test="${pageAccess.loginRequired}">
	doLogin();
</c:if>

function doLogin() {
	$('#loginModal').modal();
}
</script>

<div class="modal fade" id="loginModal" tabIndex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="false">
	<div class="modal-dialog modal-md">
		<div class="modal-body">
			<div class="loginPanelBox">
				<div id="loginBox" class="panel panel-default">
					<div class="panel-heading">
						<div class="panel-title">
							<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
							<spring:message code="70.title"/>
						</div>
					</div>
					
					<div class="panel-body">
						<form id="loginForm" name="loginForm" class="form-horizontal" role="form">
							<input type="hidden" id="j_username" name="j_username" value=""/>
							<input type="hidden" id=j_password name="j_password" value=""/>
							<input type="hidden" id="listingId" name="listingId" value=""/>
							
							<div id="alertLoginFailedDiv" class="alert alert-danger" style="display:none">
								<spring:message code="0.msg.username.password.warning"/>
							</div>
				
							<div class="form-group">
								<div class="col-xs-12">
									<div class="input-group">
										<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
										<input id="loginUserName" name="loginUserName" type="email" class="form-control" value="${username}" 
											placeholder="<spring:message code="30.plchr.email"/>" autocomplete="on"
											data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" 
											data-bv-emailaddress-message="<spring:message code="0.msg.invalid.email.warning"/>" />
									</div>
								</div>
							</div>
						
							<div class="form-group">
								<div class="col-xs-12">
									<div class="input-group">
										<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
										<input id="loginPassword" name="loginPassword" type="password" class="form-control" 
											placeholder="<spring:message code="30.plchr.password"/>" autocomplete="off"
											data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
									</div>
								</div>
							</div>
							
							<div class="col-xs-12" style="margin-bottom:25px">
								<div class="checkbox">
									<label>
										<input id="rememberUsername" name="rememberUsername" type="checkbox" value="1" />
										<spring:message code="70.lbl.remember.me"/>
									</label>
								</div>
							</div>
							
							<div class="col-xs-12">
								<button id="loginBtn" type="button" class="btn btn-primary"><spring:message code="0.txt.login"/></button>
							</div>
							
							<div class="col-xs-12"  style="text-align:center;font-size:.85em;margin-top:25px">
								<a id="changePwdLink" href="#"><spring:message code="70.txt.change.password"/></a> 
								| <a id="forgotPwdLink" href="#"><spring:message code="70.txt.forgot.password"/></a>
							</div>
						</form>
					</div>
				</div>
				
				<jsp:include page="pwdRequest.jsp" flush="true" />
			</div>
		</div>
	</div>
</div>