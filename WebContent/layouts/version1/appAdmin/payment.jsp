<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.model.configuration.AdminTabType"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
var paymentFormListingId;
$(document).ready(function(e) {
	$('#paymentForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		savePayment();
	});
});

function loadPayment(v){
	paymentFormListingId = v;
	
	var url = "${pageContext.request.contextPath}/common/listAppPackages.json?<%=TokenFieldType.LISTING.getAlias()%>="+paymentFormListingId;
	buildSelect(url, "<%=TokenFieldType.APP_PACKAGE.getAlias()%>", "");
	
	$('#paymentModal').modal();
}

function savePayment(){
	var postData = $('#paymentForm').serializeArray();
	postData[postData.length] = {name:"<%=TokenFieldType.LISTING.getAlias()%>",value:paymentFormListingId};
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/savePayment.do',
		type:'post',
		cache:'false',
		data:postData,
		success:function(data, textStatus, jqXHR){
			window.location.reload(true);
		},
		error:function(qXHR, textStatus, errorThrown){
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}
</script>

<div class="modal fade" id="paymentModal" tabIndex="-1" role="dialog" aria-labelledby="paymentModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<form class="form-horizontal" role="form" name="paymentForm" id="paymentForm">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
					<h4 class="modal-title" id="paymentModalLabel">Payment Handler</h4>
				</div>
						
				<div class="modal-body">
					<div class="form-group">
						<div class="col-xs-4"><label for="transactionId">Application Package:</label></div>
						<div class="col-xs-8">
							<select id="<%=TokenFieldType.APP_PACKAGE.getAlias()%>" name="<%=TokenFieldType.APP_PACKAGE.getAlias()%>" class="form-control" data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>"></select>
						</div>
					</div>
				
					<div class="form-group">
						<div class="col-xs-4"><label for="transactionId">Transaction Id:</label></div>
						<div class="col-xs-8">
							<input id="transactionId" name="transactionId" type="text" class="form-control" maxLength="256"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.close"/></button>
					<button type="submit" class="btn btn-primary"><spring:message code="0.txt.save"/></button>
				</div>
			</form>
		</div>
	</div>
</div>