<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>
<%@ page import="ca.app.util.ProjectUtil" %>
<%@ page import="ca.app.model.common.CurrencyType"%>
<%@ page import="ca.app.model.application.Provider"%>

<script type="text/javascript">
var <%=TokenFieldType.LISTING.getAlias()%>;
var w;
var x;
$(document).ready(function() {
	$('#payNowBtn').on('click', function(el) {
		//saveListingPackage();
		$('#paymentForm').submit();
	});
	
	$('#paymentModal').on('hide.bs.modal', function (e) {
		$('#paymentForm').bootstrapValidator('resetForm', true);
		$('#paymentForm').trigger('reset');
	});
});

function loadPaymentDetails(v,w,x){
	this.v = v;
	this.w = w;
	this.x = x;
	
	if (w == '<%=CurrencyType.CAD.getId()%>') {
		if (x == 'en_CA' || x == 'en_US' || x == 'en_GB') {
			$('#cdnFrPayment').html('');
			$('#usPayment').html('');
		} else {
			$('#cdnEnPayment').html('');
			$('#usPayment').html('');
		}
	} else {
		$('#cdnEnPayment').html('');
		$('#cdnFrPayment').html('');
	}
	
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/loadPaymentDetails.do',
		type:'get',
		success:function(data, textStatus, jqXHR) {
			$('#email').val(data.user.email);
			$('#first_name').val(data.user.firstName);
			$('#last_name').val(data.user.lastName);
			
			displayPayment();
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

//<c:if test="false">
function saveListingPackage() {
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/saveListingPackage.do',
		type:'post',
		data:{
			<%=TokenFieldType.LISTING.getAlias()%>:v,
			os0:$('#<%=Provider.PAYPAL.getFieldName()%>').val()
		},
		success:function(data, textStatus, jqXHR) {
			$('#item_number').val(data.<%=TokenFieldType.PURCHASE.getAlias()%>);
			
			$('#paymentForm').submit();
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}
//</c:if>

function displayPayment(){
	$('#paymentModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="paymentModal" tabIndex="-1" role="dialog" aria-labelledby="paymentModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="paymentModalLabel"><spring:message code="120.txt.paypal"/></h4>
			</div>
		
			<form class="form-horizontal" name="paymentForm" id="paymentForm" action="<%=ProjectUtil.getProperty("paypal.commerce.url")%>" method="post" target="_top">
				<div class="modal-body">
					<jsp:include page="cadEnPayment.jsp" flush="true"/>
					<jsp:include page="cadFrPayment.jsp" flush="true"/>
					<jsp:include page="usdPayment.jsp" flush="true"/>
					
					<input type="hidden" name="business" id="business" value="<%=ProjectUtil.getProperty("paypal.business.number")%>" />
					<input type="hidden" name="cmd" id="cmd" value="_s-xclick" />
					<input type="hidden" name="on0" id="on0" value="BuyByeBiz" />
					<input type="hidden" name="cbt" id="cbt" value="Return to BuyByeBiz" />
					<input type="hidden" name="item_number" id="item_number" value="" />
					<input type="hidden" name="discount_rate" id="discount_rate" value="" />
					<input type="hidden" name="return" id="return" value="<%=ProjectUtil.getProperty("paypal.return.url")%>" />
					<input type="hidden" name="rm" id="rm" value="2" />
					<input type="hidden" name="cancel_return" id="cancel_return" value="<%=ProjectUtil.getProperty("paypal.cancel.url")%>" />
					<input type="hidden" name="email" id="email" value="" />
					<input type="hidden" name="first_name" id="first_name" />
					<input type="hidden" name="last_name" id="last_name" />
				</div>
			</form>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
				<button type="button" id="payNowBtn" class="btn btn-primary">Pay Now!</button>
			</div>
		</div>
	</div>
</div>