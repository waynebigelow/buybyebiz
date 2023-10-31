<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.mail.GlobalEmailType"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
});

function sendEmail() {
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/sendEmail.do',
		type:'post',
		data:{
			ti:$('#emailType').val(),
			it:$('#isTest').prop('checked'),
			cs:$('#customMessage').val(),
			em:$('#enabledEmails').val()
		},
		success:function(data, textStatus, jqXHR) {
			showAlert('success', 'sm', "The email has been sent.", 1500);
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function updateCheckedValue(fieldId) {
	var fieldValue = $('#promo' + fieldId).val();
	if (fieldValue == "true") {
		$('#promo' + fieldId).val('false');
		
		var enabled = $('#enabledEmails').val().replace(fieldId + ',','');
		$('#enabledEmails').val(enabled)
	} else {
		$('#promo' + fieldId).val('true');
		$('#enabledEmails').val($('#enabledEmails').val() + fieldId + ",");
	}
}

function displayEmail(){
	$('#emailModal').modal();
}
</script>

<div class="modal fade" id="emailModal" tabIndex="-1" role="dialog" aria-labelledby="emailModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form name="emailForm" id="emailForm" class="form-horizontal" role="form">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title" id="emailModalLabel">Email</h4>
				</div>
				
				<div class="modal-body">
					<div class="form-group">
						<div class="col-xs-4"><label for="emailType">Email Type:</label></div>
						<div class="col-xs-8">
							<input type="hidden" id="emailType" name="emailType" value="<%=GlobalEmailType.PROMOTIONAL_WELCOME.getId()%>" />
							<input type="text" id="displayType" name="displayType" value="<%=GlobalEmailType.PROMOTIONAL_WELCOME.name()%>" class="form-control" readonly="readonly" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="customMessage">Custom Message:</label></div>
						<div class="col-xs-8">
							<textarea id="customMessage" name="customMessage" class="form-control" rows="8" maxLength="1000"></textarea>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="isTest">Is Test:</label></div>
						<div class="col-xs-8">
							<input type="checkbox" id="isTest" name="isTest" checked="checked" />
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button id="sendMailBtn" type="button" class="btn btn-primary" onclick="sendEmail()">Send</button>
				</div>
			</form>
		</div>
	</div>
</div>