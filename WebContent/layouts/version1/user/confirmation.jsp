<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
$(document).ready(function() {
	$('#confirmationModal').on('hide.bs.modal', function (e) {
		location.href = '${pageContext.request.contextPath}/home.html';
	});
});
</script>

<div class="modal fade" id="confirmationModal" tabIndex="-1" role="dialog" aria-labelledby="confirmationModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md alert alert-info">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="confirmationModalLabel"><spring:message code="50.title"/></h4>
			</div>
			
			<div class="modal-body">
				<p>
					<spring:message code="50.msg"/>
				</p>
			</div>
		</div>
	</div>
</div>