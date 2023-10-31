<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ca.app.util.ProjectUtil"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/other/js/js.cookie.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	var isHidden = Cookies.get('hidePromo');
	if (!isHidden) {
		$('#promoModal').modal({
			backdrop:'static',
			keyboard:false
		});
	}
	
	$('#promoModal').on('hide.bs.modal', function () {
		var cookieDomain = '<%=ProjectUtil.getProperty("sso.cookie.domain")%>';
		if ($('#hidePromo').prop('checked')) {
			Cookies.set('hidePromo', 'true', {
				path:'',
				domain:cookieDomain,
				secure:true 
			});
		}
	});
});

function launchRegistration() {
	$('#promoModal').modal('hide');
	modalRegister();
}
</script>

<div class="modal fade" id="promoModal" tabIndex="-1" role="dialog" aria-labelledby="promoModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md alert alert-info">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="promoModalLabel" style="color:#000000">
					<c:set var="duration" value='<%=ProjectUtil.getProperty("business.listing.promo.duration") %>' />
					<spring:message code="130.frm.title" arguments="${application.name},${duration}" htmlEscape="true" argumentSeparator="," />
				</h4>
			</div>
			
			<div class="modal-body">
				<p style="color:#000000">
					<c:set var="count" value='<%=ProjectUtil.getProperty("business.listing.promo.count") %>' />
					<spring:message code="130.msg.1" arguments="${count},${duration},${promoRemaing}" htmlEscape="false" argumentSeparator="," />
					<br/><br/>
					<button type="button" class="btn btn-primary" onclick="launchRegistration()"><spring:message code="110.link.listing" /></button>
					<br/><br/>
					<spring:message code="130.msg.2" /> <input type="checkbox" id="hidePromo" name="hidePromo"/>
				</p>
			</div>
		</div>
	</div>
</div>