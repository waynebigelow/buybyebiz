<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.model.application.ApplicationType"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#applicationForm')
	.bootstrapValidator({
		excluded:':disabled',
		fields:{
			type:{
				validators:{
					notEmpty:{
						message:'<spring:message code="0.msg.mandatory.field.warning" javaScriptEscape="true" />'
					}
				}
			}
		}
	})
	.on('success.form.bv', function(e) {
		e.preventDefault();
		saveApplication();
	});
	
	$('#applicationModal').on('hide.bs.modal', function (e) {
		$('#applicationForm').bootstrapValidator('resetForm', true);
		$('#applicationForm').trigger('reset');
	});

	$('#addAppBtn').on('click', function(el) {
		displayApplication();
	});
});

function displayApplication(){
	$('#applicationModal').modal({
		backdrop:'static',
		keyboard:false
	});
}

function saveApplication(){
	var postData = $('#applicationForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/saveApplication.do',
		type:'post',
		cache:'false',
		data:postData,
		success:function(data, textStatus, jqXHR){
			window.location.reload(true);
		},
		error:function(qXHR, textStatus, errorThrown){
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function editApplication(v){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/editApplication.do',
		type: 'get',
		data:{
			<%=TokenFieldType.APPLICATION.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			$('#<%=TokenFieldType.APPLICATION.getAlias()%>').val(v);
			$('#name').val(data.application.name);
			$('#description').val(data.application.description);
			$('#type').val(data.application.typeId);
			$('#replyEmail').val(data.application.replyEmail);
			$('#supportEmail').val(data.application.supportEmail);
			$('#supportPhone').val(data.application.supportPhone);
			$('#key').val(data.application.key);
			$('#domain').val(data.application.domain);
			$('#defaultLocaleCode').val(data.application.defaultLocaleCode);
			
			displayApplication();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function deleteApplication(v){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/deleteApplication.do',
		type:'post',
		data:{
			<%=TokenFieldType.APPLICATION.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			window.location.reload(true);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function toggleApplication(v){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/toggleApplication.do',
		type:'post',
		data:{
			<%=TokenFieldType.APPLICATION.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			window.location.reload(true);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}
</script>

<div class="modal fade" id="applicationModal" tabIndex="-1" role="dialog" aria-labelledby="applicationModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<form class="form-horizontal" role="form" name="applicationForm" id="applicationForm">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
					<h4 class="modal-title" id="applicationModalLabel"><spring:message code="35.txt.application"/></h4>
				</div>
				<input id="<%=TokenFieldType.APPLICATION.getAlias()%>" name="<%=TokenFieldType.APPLICATION.getAlias()%>" type="hidden" value="" />
				
				<div class="modal-body">
					<div class="form-group">
						<div class="col-xs-3"><label for="name"><spring:message code="35.lbl.name"/>:</label></div>
						<div class="col-xs-9">
							<input id="name" name="name" type="text" class="form-control" maxLength="256"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="description"><spring:message code="35.lbl.description"/>:</label></div>
						<div class="col-xs-9">
							<textarea id="description" name="description" class="form-control" rows="2" maxLength="1000"></textarea>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="type"><spring:message code="35.lbl.type"/>:</label></div>
						<div class="col-xs-9">
							<select id="type" name="type" class="form-control">
								<option value="">--Select Type--</option>
								<c:forEach items="<%=ApplicationType.values()%>" var="type">
									<option value="${type.id}"><spring:message code="${type.i18n}" /></option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="domain"><spring:message code="35.lbl.domain"/>:</label></div>
						<div class="col-xs-9">
							<input id="domain" name="domain" type="text" value="" class="form-control" maxLength="256"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="replyEmail"><spring:message code="35.lbl.reply.email"/>:</label></div>
						<div class="col-xs-9">
							<input id="replyEmail" name="replyEmail" type="text" value="" class="form-control" maxLength="256" 
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="supportEmail"><spring:message code="35.lbl.support.email"/>:</label></div>
						<div class="col-xs-9">
							<input id="supportEmail" name="supportEmail" type="text" value="" class="form-control" maxLength="256" 
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="supportPhone"><spring:message code="35.lbl.support.phone"/>:</label></div>
						<div class="col-xs-9">
							<input id="supportPhone" name="supportPhone" type="text" value="" class="form-control" maxLength="256" 
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="key"><spring:message code="35.lbl.key"/>:</label></div>
						<div class="col-xs-4">
							<input id="key" name="key" type="text" value="" class="form-control" maxLength="30" 
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="defaultLocaleCode"><spring:message code="35.lbl.default.locale"/>:</label></div>
						<div class="col-xs-4">
							<select id="defaultLocaleCode" name="defaultLocaleCode" class="form-control">
								<c:forEach items="${globalLocales}" var="globalLocale">
									<option value="${globalLocale.value}">${globalLocale.key}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
					<button type="submit" class="btn btn-primary"><spring:message code="0.txt.save"/></button>
				</div>
			</form>
		</div>
	</div>
</div>