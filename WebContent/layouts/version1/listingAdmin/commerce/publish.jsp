<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
var <%=TokenFieldType.LISTING.getAlias()%>;
$(document).ready(function() {
	$('#publishBtn').on('click', function(el) {
		publishListing();
	});
});

function publishListing() {
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/publishListing.do',
		type:'post',
		data:{
			<%=TokenFieldType.LISTING.getAlias()%>:<%=TokenFieldType.LISTING.getAlias()%>,
		},
		success:function(data, textStatus, jqXHR) {
			window.location.reload(true);
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function displayPublish(v){
	<%=TokenFieldType.LISTING.getAlias()%> = v;
	$('#publishModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="publishModal" tabIndex="-1" role="dialog" aria-labelledby="publishModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="publishModalLabel">Publishing a Listing</h4>
			</div>
			
			<div class="modal-body">
				<p>
					BuyByeBiz reviews all listing content before publishing. The review process can take approximately 1 hour. Before publishing BuyByeBiz strongly advises you to review the Terms of Use and Privacy Policy.
				</p>
				<p>
					Your ${promoDuration} month free trial will commence upon approval. Are you ready to publish?
				</p>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
				<button type="button" id="publishBtn" class="btn btn-primary">Publish</button>
			</div>
		</div>
	</div>
</div>