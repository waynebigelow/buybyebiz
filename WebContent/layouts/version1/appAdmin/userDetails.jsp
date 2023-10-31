<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#userDetailsModal').on('shown.bs.modal', function () {
		$('#email').focus();
	});
	
	$('#userDetailsModal').on('hide.bs.modal', function (e) {
		$('#userDetailsForm').bootstrapValidator('resetForm', true);
		$('#userDetailsForm').trigger("reset");
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
});

function displayUser(v){
	$('#userDetailsModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="userDetailsModal" tabIndex="-1" role="dialog" aria-labelledby="userDetailsModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="userDetailsModalLabel"><spring:message code="30.txt.account.details"/></h4>
			</div>

			<div class="modal-body">
				<jsp:include page="../commonAdmin/userDetails.jsp" flush="true" />
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
				<button id="saveUserBtn" type="button" class="btn btn-primary"><spring:message code="0.txt.save"/></button>
			</div>
		</div>
	</div>
</div>