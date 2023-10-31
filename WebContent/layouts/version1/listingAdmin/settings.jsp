<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
$(document).ready(function() {
	$('#settingsModal').on('hide.bs.modal', function (e) {
		$('#settingsForm').bootstrapValidator('resetForm', true);
		$('#settingsForm').trigger('reset');
	});
	
	$('#settingsModal').on('shown.bs.modal', function () {
		$('#showName').focus();
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
});

function editSettings(v) {
	displaySettings();
}

function saveSettings() {
	var postData = $('#settingsForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/saveContactSettings.do',
		type:'post',
		cache:'false',
		data:postData,
		success:function(data, textStatus, jqXHR){
			window.location.href='${pageContext.request.contextPath}/listingAdmin/preview.html?<%=TokenFieldType.LISTING.getAlias()%>=' + data.success;
		},
		error:function(qXHR, textStatus, errorThrown){
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function displaySettings(){
	$('#settingsModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="settingsModal" tabIndex="-1" role="dialog" aria-labelledby="settingsModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title" id="settingsModalLabel"><spring:message code="20.acrdn.5"/></h4>
			</div>
			
			<div class="modal-body">
				<form class="form-horizontal" name="settingsForm" id="settingsForm" role="form">
					<input id="<%=TokenFieldType.LISTING.getAlias()%>" name="<%=TokenFieldType.LISTING.getAlias()%>" type="hidden" value="${site.listing.token}" />
					
					<div class="form-group">
						<div class="col-xs-12">
							<div class="checkbox">
								<label>
									<input type="checkbox" id="includeContactName" name="includeContactName" <c:if test="${site.listing.includeContactName}">checked="checked"</c:if> />
									Show contact name
								</label>
							</div>
						</div>
					</div>
						
					<div class="form-group">
						<div class="col-xs-12">
							<div class="checkbox">
								<label>
									<input type="checkbox" id="includeContactPhone" name="includeContactPhone" <c:if test="${site.listing.includeContactTelephone}">checked="checked"</c:if> />
									Show contact telephone number
								</label>
							</div>
						</div>
					</div>
				</form>
			</div>
				
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
				<button type="button" class="btn btn-primary" onclick="saveSettings()"><spring:message code="0.txt.save"/></button>
			</div>
		</div>
	</div>
</div>