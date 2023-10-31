<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
$(document).ready(function() {
	//<c:if test="${actionId eq 1}">
	$('#successModal').modal({
		backdrop:'static',
		keyboard:false
	});
	
	//<c:remove var="actionId" scope="request"/>
	//</c:if>
});
</script>

<div class="modal fade" id="successModal" tabIndex="-1" role="dialog" aria-labelledby="successModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md alert alert-info">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="successModalLabel"><spring:message code="57.title"/></h4>
			</div>
			
			<div class="modal-body">
				<p>
					<spring:message code="57.msg"/>
				</p>
			</div>
		</div>
	</div>
</div>