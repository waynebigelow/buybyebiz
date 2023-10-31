<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
$(document).ready(function() {
	$('#cancelledModal').modal({
		backdrop:'static',
		keyboard:false
	});
	
	$('#cancelledModal').on('hide.bs.modal', function (e) {
		location.href = '${pageContext.request.contextPath}/userAdmin/admin.html';
	});
});
</script>

<div class="modal fade" id="cancelledModal" tabIndex="-1" role="dialog" aria-labelledby="cancelledModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md alert alert-info">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="cancelledModalLabel">Transaction Cancelled</h4>
			</div>
			
			<div class="modal-body">
				<p>
					Extending your listing will ensure that your business is getting the exposure it needs to get sold. Extend your listing today!
				</p>
			</div>
		</div>
	</div>
</div>