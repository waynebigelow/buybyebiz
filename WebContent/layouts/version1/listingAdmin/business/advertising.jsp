<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#advertisingModal').on('hide.bs.modal', function (e) {
		$('#advertisingForm').bootstrapValidator('resetForm', true);
		$('#advertisingForm').trigger('reset');
	});
	
	$('#advertisingModal').on('shown.bs.modal', function () {
		$('#websiteURL').focus();
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
});

function editAdvertising() {
	displayAdvertising();
}

function saveAdvertising() {
	var postData = $('#advertisingForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/saveAdvertising.do',
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

function displayAdvertising(){
	$('#advertisingModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="advertisingModal" tabIndex="-1" role="dialog" aria-labelledby="advertisingModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="advertisingModalLabel">Business Marketing</h4>
			</div>
			
			<div class="modal-body">
				<form class="form-horizontal" role="form" name="advertisingForm" id="advertisingForm">
					<input id="<%=TokenFieldType.LISTING.getAlias()%>" name="<%=TokenFieldType.LISTING.getAlias()%>" type="hidden" value="${site.listing.token}" />
					
					<div class="form-group">
						<div class="col-xs-4"><label for="websiteURL"><spring:message code="20.lbl.website.url"/>:</label></div>
						<div class="col-xs-8">
							<input id="websiteURL" name="websiteURL" type="text" value="${site.listing.businessDetails.websiteURL}" class="form-control" maxLength="256"
							 placeholder="ex: https://buybyebiz.com" maxLength="256" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="facebookURL"><spring:message code="20.lbl.facebook.url"/>:</label></div>
						<div class="col-xs-8">
							<input id="facebookURL" name="facebookURL" type="text" value="${site.listing.businessDetails.facebookURL}" class="form-control" maxLength="256"
							 placeholder="ex: https://buybyebiz.com" maxLength="256" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="twitterURL"><spring:message code="20.lbl.twitter.url"/>:</label></div>
						<div class="col-xs-8">
							<input id="twitterURL" name="twitterURL" type="text" value="${site.listing.businessDetails.twitterURL}" class="form-control" maxLength="256"
							 placeholder="ex: https://buybyebiz.com" maxLength="256" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="tripAdvisorURL"><spring:message code="20.lbl.tripadvisor.url"/>:</label></div>
						<div class="col-xs-8">
							<input id="tripAdvisorURL" name="tripAdvisorURL" type="text" value="${site.listing.businessDetails.tripAdvisorURL}" class="form-control" maxLength="256"
							 placeholder="ex: https://buybyebiz.com" maxLength="256" />
						</div>
					</div>
				</form>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
				<button type="button" class="btn btn-primary" onclick="saveAdvertising()"><spring:message code="0.txt.save"/></button>
			</div>
		</div>
	</div>
</div>