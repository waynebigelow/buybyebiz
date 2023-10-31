<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
$(document).ready(function() {
	$('#mlsModal').on('hide.bs.modal', function (e) {
		$('#mlsForm').bootstrapValidator('resetForm', true);
		$('#mlsForm').trigger('reset');
	});
	
	$('#mlsModal').on('shown.bs.modal', function () {
		$('#showName').focus();
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
});

function editMlsLinks() {
	displayMls();
}

function saveMls() {
	var postData = $('#mlsForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/saveMlsLinks.do',
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

function displayMls(){
	$('#mlsModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="mlsModal" tabIndex="-1" role="dialog" aria-labelledby="mlsModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title" id="mlsModalLabel">MLS Listing Links</h4>
			</div>
			
			<div class="modal-body">
				<form class="form-horizontal" name="mlsForm" id="mlsForm" role="form">
					<input id="<%=TokenFieldType.LISTING.getAlias()%>" name="<%=TokenFieldType.LISTING.getAlias()%>" type="hidden" value="${site.listing.token}" />

					<div class="form-group">
						<div class="col-xs-4"><label for="agentListingLink">Link to MLS Listing:</label></div>
						<div class="col-xs-8">
							<input id="agentListingLink" name="agentListingLink" type="text" value="${site.listing.businessDetails.agentListingLink}" class="form-control" maxLength="256"
							 placeholder="ex: https://buybyebiz.com" maxLength="256" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="multiMediaLink">Link to Video:</label></div>
						<div class="col-xs-8">
							<input id="multiMediaLink" name="multiMediaLink" type="text" value="${site.listing.businessDetails.multiMediaLink}" class="form-control" maxLength="256"
							 placeholder="ex: https://buybyebiz.com" maxLength="256" />
						</div>
					</div>
				</form>
			</div>
				
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
				<button type="button" class="btn btn-primary" onclick="saveMls()"><spring:message code="0.txt.save"/></button>
			</div>
		</div>
	</div>
</div>