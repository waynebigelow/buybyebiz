<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
$(document).ready(function() {
	$('#pendingModal').modal({
		backdrop:'static',
		keyboard:false
	});
	
	$('#pendingModal').on('hide.bs.modal', function (e) {
		location.href = '${pageContext.request.contextPath}/userAdmin/admin.html';
	});
});
</script>

<div class="modal fade" id="pendingModal" tabIndex="-1" role="dialog" aria-labelledby="pendingModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md alert alert-info">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="pendingModalLabel">Transaction Pending</h4>
			</div>
			
			<div class="modal-body">
				<p>
					Thank you!! Your payment can take up to 15 minutes to be processed. You'll be notified by email once the transaction has been completed. As well, Paypal, 
					will email you a copy of the completed transaction. It would be prudent to keep a copy of this it for your records.
				</p>
			</div>
		</div>
	</div>
</div>