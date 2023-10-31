<%@page import="ca.app.model.common.CurrencyType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.model.application.ApplicationType"%>
<%@ page import="ca.app.model.application.TimePeriod"%>
<%@ page import="ca.app.model.application.ApplicationPackageType"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#packageForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		savePackage();
	});
	
	$('#packageModal').on('shown.bs.modal', function () {
		$('#name').focus();
	});
	
	$('#packageModal').on('hide.bs.modal', function (e) {
		$('#packageForm').bootstrapValidator('resetForm', true);
		$('#packageForm').trigger('reset');
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
	
	$("#price").keydown(function (e) {
		if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
			(e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) || 
			(e.keyCode >= 35 && e.keyCode <= 40)) {
				return;
			}

			if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
				e.preventDefault();
			}
	});
});

function addPackage() {
	displayPackage();
}

function displayPackage(){
	$('#packageModal').modal({
		backdrop:'static',
		keyboard:false
	});
}

function savePackage(){
	var postData = $('#packageForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/savePackage.do',
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

function editPackage(v){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/editPackage.do',
		type: 'get',
		data:{
			<%=TokenFieldType.APP_PACKAGE.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			$('#<%=TokenFieldType.APP_PACKAGE.getAlias()%>').val(v);
			$('#name').val(data.appPackage.name);
			$('#description').val(data.appPackage.description);
			$('#appType').val(data.appPackage.applicationId);
			$('#type').val(data.appPackage.typeId);
			$('#timePeriod').val(data.appPackage.timePeriodId);
			$('#duration').val(data.appPackage.duration);
			$('#price').val(data.appPackage.price);
			$('#tax').val(data.appPackage.tax);
			$('#currencyType').val(data.appPackage.currencyTypeId);
			$('#agentDiscount').val(data.appPackage.agentDiscount);
			$('#linkId').val(data.appPackage.linkId);
			
			displayPackage();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function deletePackage(v){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/deletePackage.do',
		type:'post',
		data:{
			<%=TokenFieldType.APP_PACKAGE.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			window.location.reload(true);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function togglePackage(v){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/togglePackage.do',
		type:'post',
		data:{
			<%=TokenFieldType.APP_PACKAGE.getAlias()%>:v
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

<div class="modal fade" id="packageModal" tabIndex="-1" role="dialog" aria-labelledby="packageModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<form class="form-horizontal" role="form" name="packageForm" id="packageForm">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
					<h4 class="modal-title" id="packageModalLabel"><spring:message code="105.txt.package"/></h4>
				</div>
				<input id="<%=TokenFieldType.APP_PACKAGE.getAlias()%>" name="<%=TokenFieldType.APP_PACKAGE.getAlias()%>" type="hidden" value="" />
				
				<div class="modal-body">
					<div class="form-group">
						<div class="col-xs-3"><label for="name"><spring:message code="105.lbl.name"/>:</label></div>
						<div class="col-xs-9">
							<input id="name" name="name" type="text" class="form-control" maxLength="256"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="description"><spring:message code="105.lbl.description"/>:</label></div>
						<div class="col-xs-9">
							<textarea id="description" name="description" class="form-control" rows="2" maxLength="1000"></textarea>
						</div>
					</div>
				
					<div class="form-group">
						<div class="col-xs-3"><label for="appType"><spring:message code="35.lbl.type"/>:</label></div>
						<div class="col-xs-5">
							<select id="appType" name="appType" class="form-control" 
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>">
								<option value="">--Select Type--</option>
								<c:forEach items="<%=ApplicationType.values()%>" var="type">
									<option value="${type.id}"><spring:message code="${type.i18n}" /></option>
								</c:forEach>
							</select>
						</div>
					</div>
				
					<div class="form-group">
						<div class="col-xs-3"><label for="type"><spring:message code="105.lbl.type"/>:</label></div>
						<div class="col-xs-5">
							<select id="type" name="type" class="form-control" 
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>">
								<option value="">--Select Type--</option>
								<c:forEach items="<%=ApplicationPackageType.values()%>" var="type">
									<option value="${type.id}">${type}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="form-group">
						<div class="col-xs-3"><label for="timePeriod"><spring:message code="105.lbl.time.period"/>:</label></div>
						<div class="col-xs-5">
							<select id="timePeriod" name=timePeriod class="form-control" 
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>">
								<option value="">--Select Time Period--</option>
								<c:forEach items="<%=TimePeriod.values()%>" var="type">
									<option value="${type.id}">${type}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="duration"><spring:message code="105.lbl.duration"/>:</label></div>
						<div class="col-xs-5">
							<input id="duration" name="duration" type="text" class="form-control"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="price"><spring:message code="105.lbl.price"/>:</label></div>
						<div class="col-xs-5">
							<input id="price" name="price" type="text" class="form-control"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="tax">Tax:</label></div>
						<div class="col-xs-5">
							<input id="tax" name="tax" type="text" class="form-control" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="currencyType"><spring:message code="35.lbl.type"/>:</label></div>
						<div class="col-xs-5">
							<select id="currencyType" name="currencyType" class="form-control" 
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>">
								<option value="">--Select Currency Type--</option>
								<c:forEach items="<%=CurrencyType.values()%>" var="type">
									<option value="${type.id}">${type.shortName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="agentDiscount">Agent Discount:</label></div>
						<div class="col-xs-5">
							<input id="agentDiscount" name="agentDiscount" type="text" class="form-control" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="linkId">Link Id:</label></div>
						<div class="col-xs-5">
							<input id="linkId" name="linkId" type="text" class="form-control"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
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