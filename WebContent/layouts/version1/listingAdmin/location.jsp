<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>
<%@ page import="ca.app.model.common.AddressType"%>
<%@ page import="ca.app.model.common.CountryType"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#locationForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		saveLocation();
	});

	$('#saveLocationBtn').on('click', function(el) {
		$('#locationForm').submit();
	});
	
	$('#province').remoteChained({
		parents:'#country',
		url:'${pageContext.request.contextPath}/common/listProvincesOrStates.json?c=1&type='
	});
	
	$('#country').on('change', function(el) {
		$('#address').val('');
		$('#city').val('');
		$('#postalCode').val('');
		
	});
	
	$('#locationModal').on('hide.bs.modal', function (e) {
		$('#locationForm').bootstrapValidator('resetForm', true);
		$('#locationForm').trigger('reset');
	});
	
	$('#locationModal').on('shown.bs.modal', function () {
		$('#address1').focus();
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
});

function editLocation() {
	var country = "${site.listing.address.country}";
	if (country == "") {
		country = "<%=CountryType.CA.getShortName()%>";
	}
	buildSelect("${pageContext.request.contextPath}/common/listProvincesOrStates.json?country=" + country, "province", "${site.listing.address.province}");
	buildSelect("${pageContext.request.contextPath}/common/listCountries.json", "country", country);
	
	displayLocation();
}

function saveLocation() {
	var postData = $('#locationForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/saveLocation.do',
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

function displayLocation(){
	$('#locationModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="locationModal" tabIndex="-1" role="dialog" aria-labelledby="locationModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="locationModalLabel">Business Location</h4>
			</div>
			
			<div class="modal-body">
				<form class="form-horizontal" role="form" name="locationForm" id="locationForm">
					<input id="<%=TokenFieldType.LISTING.getAlias()%>" name="<%=TokenFieldType.LISTING.getAlias()%>" type="hidden" value="${site.listing.token}" />
					
					<div class="form-group">
						<div class="col-xs-4"><label for="address1"><spring:message code="25.lbl.address1"/>:</label></div>
						<div class="col-xs-8">
							<input id="address1" name="address1" type="text" value="${site.listing.address.address1}" class="form-control" maxLength="256" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="city"><spring:message code="25.lbl.city"/>:</label></div>
						<div class="col-xs-8">
							<input id="city" name="city" type="text" value="${site.listing.address.city}" class="form-control"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" maxLength="256" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="province"><spring:message code="25.lbl.province"/>:</label></div>
						<div class="col-xs-8">
							<select id="province" name="province" class="form-control"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>">
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="country"><spring:message code="25.lbl.country"/>:</label></div>
						<div class="col-xs-8">
							<select id="country" name="country" class="form-control"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>">
							</select>
						</div>
					</div>
				</form>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
				<button id="saveLocationBtn" type="button" class="btn btn-primary"><spring:message code="0.txt.save"/></button>
			</div>
		</div>
	</div>
</div>