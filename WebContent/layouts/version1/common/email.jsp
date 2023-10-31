<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.mail.GlobalEmailType"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#emailForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		sendEnquiry();
	});

	$('#sendMailBtn').on('click', function(el) {
		$('#emailForm').submit();
	});
	
	$('#emailModal').on('hide.bs.modal', function (e) {
		resetRecaptcha2();
		$('#alertEmailDiv').html('');
		$('#alertEmailDiv').hide();
		$('#emailForm').bootstrapValidator('resetForm', true);
		$('#emailForm').trigger('reset');
	});
	
	$('#emailModal').on('shown.bs.modal', function () {
		$('#firstName').focus();
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		$('#emailModal').modal('hide');
	});
});

function sendEnquiry() {
	var postData = $('#emailForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/sendEnquiry.do',
		type:'post',
		data:postData,
		success:function(data, textStatus, jqXHR) {
			if (data.success == false) {
				resetRecaptcha2();
				$('#alertEmailDiv').html(data.error);
				$('#alertEmailDiv').show();
			} else {
				showAlert('success', 'md', '<spring:message code="125.msg.action.completed" javaScriptEscape="true" />', 1500);
			}
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function displayEmail(){
	$('#emailModal').modal();
}
</script>

<div class="modal fade" id="emailModal" tabIndex="-1" role="dialog" aria-labelledby="emailModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<form name="emailForm" id="emailForm" class="form-horizontal" role="form">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title" id="emailModalLabel"><spring:message code="125.frm.title"/></h4>
				</div>
				
				<input id="<%=TokenFieldType.LISTING.getAlias()%>" name="<%=TokenFieldType.LISTING.getAlias()%>" type="hidden" value="${site.listing.token}" />
				<input id="emailTypeId" name="emailTypeId" type="hidden" value="<%=GlobalEmailType.LISTING_ENQUIRY.getId()%>" />
				<div class="modal-body">
					<div id="alertEmailDiv" class="alert alert-danger" style="display:none"></div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="firstName"><spring:message code="30.lbl.first.name"/>:</label></div>
						<div class="col-xs-8">
							<input id="firstName" name="firstName" type="text"  class="form-control" maxLength="256"
								<c:if test="${pageAccess.loggedIn}">value="${pageAccess.user.firstName}" readonly="readonly"</c:if>
								<c:if test="${!pageAccess.loggedIn}">data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>"</c:if> />
						</div>
					</div>
			
					<div class="form-group">
						<div class="col-xs-4"><label for="lastName"><spring:message code="30.lbl.last.name"/>:</label></div>
						<div class="col-xs-8">
							<input id="lastName" name="lastName" type="text"  class="form-control" maxLength="256"
								<c:if test="${pageAccess.loggedIn}">value="${pageAccess.user.lastName}" readonly="readonly"</c:if>
								<c:if test="${!pageAccess.loggedIn}">data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>"</c:if> />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="email"><spring:message code="30.lbl.user.name"/>:</label></div>
						<div class="col-xs-8">
							<input id="email" name="email" type="email"  class="form-control" maxLength="256"
								<c:if test="${pageAccess.loggedIn}">value="${pageAccess.user.email}" readonly="readonly"</c:if>
								<c:if test="${!pageAccess.loggedIn}">data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>"</c:if> />
						</div>
					</div>
					
					<div class="form-group">
						<label for="comment" class="sr-only"><spring:message code="125.lbl.comment "/></label>
						<div class="col-xs-12">
							<textarea id="comment" name="comment" class="form-control" rows="3" placeholder="<spring:message code="125.plchr.comment "/> >" maxLength="1000"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>"></textarea>
						</div>
					</div>
					
					<c:if test="${pageAccess.loggedIn}">
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
					</c:if>
					
					<div class="form-group">
						<div class="col-xs-12">
							<div style="padding-top:5px;padding-bottom:5px;">
								<p class=instructionText>
									<spring:message code="0.msg.recaptcha.disclaimer" arguments="${application.name}"/>
								</p>
								<spring:message code="0.msg.recaptcha.instructions"/>
								<div id="recaptcha2"></div>
							</div>
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
					<button type="button" class="btn btn-primary" id="sendMailBtn"><spring:message code="0.txt.send"/></button>
				</div>
			</form>
		</div>
	</div>
</div>