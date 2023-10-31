<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
var udtkn='${user.token}';
$(document).ready(function(e) {
	$('#userDetailsForm').bootstrapValidator({
		excluded:':disabled',
		fields:{
			telephone:{
				validators:{
					phone:{
						country:'US',
						message:'* Invalid Phone Number'
					}
				}
			}
		}
	})
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		saveUser();
	});

	$('#saveUserBtn').on('click', function(el) {
		$('#userDetailsForm').submit();
	});

	$('#telephone').keyup( function(e) {
		var key = e.charCode || e.keyCode || 0;
		$phone = $(this);
		
		if (key != 8 && key != 9) {
			if ($phone.val().length == 0) {
				$phone.val('(');
			}
			if ($phone.val().length == 1) {
				if ($phone.val().indexOf('(') == -1) {
					$phone.val('(' + $phone.val());
				}
			}
			if ($phone.val().length == 4) {
				$phone.val($phone.val() + ')');
			}
			if ($phone.val().length == 8) {
				$phone.val($phone.val() + '-');
			}
		}
		
		return (key == 8 ||  key == 9 || key == 46 || (key >= 48 && key <= 57) || (key >= 96 && key <= 105));
	});
});

function saveUser(){
	var postData = $('#userDetailsForm').serializeArray();
	postData[postData.length] = {name:'<%=TokenFieldType.USER.getAlias()%>',value:udtkn};
	$.ajax({
		url:'${pageContext.request.contextPath}/user/saveUser.do',
		type:'post',
		cache:'false',
		data:postData,
		success:function(data, textStatus, jqXHR){
			showAlert('success', 'sm', '<spring:message code="0.msg.changes.saved" javaScriptEscape="true" />', 2000);
		},
		error:function(qXHR, textStatus, errorThrown){
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

//<c:if test="${pageAccess.systemAdmin}">
function editUser(v){
	udtkn = v;
	$.ajax({
		url:'${pageContext.request.contextPath}/user/editUser.do',
		type:'get',
		data:{
			<%=TokenFieldType.USER.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			$('#email').val(data.user.email);
			$('#firstName').val(data.user.firstName);
			$('#lastName').val(data.user.lastName);
			$('#telephone').val(data.user.telephone);
			$('#companyName').val(data.user.companyName);
			
			if (data.user.agent) {
				$('#agent').attr("checked", true);
			}
			if (data.user.enableEnquiryEmail) {
				$('#enableEnquiry').attr("checked", true);
			}
			if (data.user.enableExpirationEmail) {
				$('#enableExpiry').attr("checked", true);
			}
			if (data.user.enablePromotionalEmail) {
				$('#enablePromo').attr("checked", true);
			}
			
			displayUser();
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function deleteUser(v){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/deleteUser.do',
		type:'post',
		data:{
			<%=TokenFieldType.USER.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			window.location.reload(true);
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function toggleUser(v){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/toggleUser.do',
		type:'post',
		data:{
			<%=TokenFieldType.USER.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			window.location.reload(true);
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}
//</c:if>
</script>

<form class="form-horizontal" role="form" name="userDetailsForm" id="userDetailsForm">
	<c:if test="${pageAccess.systemAdmin}">
		<div class="form-group">
			<div class="col-xs-12 col-sm-5 col-md-4"><label for="email"><spring:message code="30.lbl.user.name"/>:</label></div>
			<div class="col-xs-12 col-sm-6 col-md-7">
				<input type="text" id="email" name="email" value="${user.email}" class="form-control" maxLength="256" />
			</div>
		</div>
	
		<div class="form-group">
			<div class="col-xs-12 col-sm-5 col-md-4"><label for="email"><spring:message code="30.lbl.password"/>:</label></div>
			<div class="col-xs-12 col-sm-6 col-md-7">
				<input type="password" id="password" name="password" class="form-control" maxLength="256" />
			</div>
		</div>
	</c:if>
	
	<c:if test="${!pageAccess.systemAdmin}">
		<div class="form-group">
			<div class="col-xs-12 col-sm-5 col-md-4"><label for="email"><spring:message code="30.lbl.user.name"/>:</label></div>
			<div class="col-xs-10 col-sm-5 col-md-6">
				<input type="text" id="email" name="email" value="${user.email}" readonly="readonly" class="form-control" maxLength="256" />
			</div>
			<div class="col-xs-2 col-sm-2 col-md-2">
				<a id="changePwdLink" href="javascript:viewChangeEmail()">Change</a>
			</div>
		</div>
	
		<div class="form-group">
			<div class="col-xs-12 col-sm-5 col-md-4"><label for="email"><spring:message code="30.lbl.password"/>:</label></div>
			<div class="col-xs-10 col-sm-5 col-md-6">
				<input type="password" id="password" name="password" readonly="readonly" value="placeholder" class="form-control" maxLength="256" />
			</div>
			<div class="col-xs-2 col-sm-2 col-md-2">
				<a id="changePwdLink" href="javascript:viewChangePwd()">Change</a>
			</div>
		</div>
	</c:if>
	
	<div class="form-group">
		<div class="col-xs-12 col-sm-5 col-md-4"><label for="firstName"><spring:message code="30.lbl.first.name"/>:</label></div>
		<div class="col-xs-12 col-sm-6 col-md-7">
			<input type="text" id="firstName" name="firstName" value="${user.firstName}" class="form-control" maxLength="256"
				data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
		</div>
	</div>

	<div class="form-group">
		<div class="col-xs-12 col-sm-5 col-md-4"><label for="lastName"><spring:message code="30.lbl.last.name"/>:</label></div>
		<div class="col-xs-12 col-sm-6 col-md-7">
			<input type="text" id="lastName" name="lastName" value="${user.lastName}" class="form-control" maxLength="256"
				data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
		</div>
	</div>
	
	<div class="form-group">
		<div class="col-xs-12 col-sm-5 col-md-4"><label for="telephone"><spring:message code="30.lbl.contact.number"/>:</label></div>
		<div class="col-xs-12 col-sm-4 col-md-4">
			<input type="text" id="telephone" name="telephone" value="${user.telephone}"placeholder="ex: (555)555-5555" class="form-control" maxLength="13" />
		</div>
	</div>
	
	<hr/>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			Commercial Real Estate
		</div>
		<div class="panel-body">
			<div class="form-group">
				<div class="col-xs-12 col-sm-5 col-md-4"><label for="agent">Commercial Agent:</label></div>
				<div class="col-xs-12 col-sm-7 col-md-8">
					<input type="checkbox" name="agent" id="agent" value="true" <c:if test="${user.agent}">checked="checked"</c:if> />
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-xs-12 col-sm-5 col-md-4"><label for="companyName">Company/Brokerage Name:</label></div>
				<div class="col-xs-12 col-sm-6 col-md-7">
					<input type="text" id="companyName" name="companyName" value="${user.companyName}" class="form-control" maxLength="256" />
				</div>
			</div>
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			Notification Settings
		</div>
		<div class="panel-body">
			<div class="form-group">
				<div class="col-xs-12">
					Enable listing enquiry notifications: <input type="checkbox" name="enableEnquiry" id="enableEnquiry" value="true" <c:if test="${user.enableEnquiryEmail }">checked="checked"</c:if> />
				</div>
			</div>
				<div class="form-group">
				<div class="col-xs-12">
					Enable listing expiration notifications: <input type="checkbox" name="enableExpiry" id="enableExpiry" value="true" <c:if test="${user.enableExpirationEmail }">checked="checked"</c:if> />
				</div>
			</div>
				<div class="form-group">
				<div class="col-xs-12">
					Enable promotional notifications: <input type="checkbox" name="enablePromo" id="enablePromo" value="true" <c:if test="${user.enablePromotionalEmail }">checked="checked"</c:if> />
				</div>
			</div>
		</div>
	</div>
	
	<c:if test="${!pageAccess.systemAdmin}">
		<div class="form-group">
			<div class="col-xs-12">
				<button id="saveUserBtn" type="button" class="btn btn-primary"><spring:message code="30.btn.1"/></button>
			</div>
		</div>
	</c:if>
</form>