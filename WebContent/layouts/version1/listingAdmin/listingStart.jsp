<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>
<%@ page import="ca.app.model.common.AddressType"%>
<%@ page import="ca.app.model.common.CountryType"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#startForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		saveStart();
	});
	
	$('#subCategory').remoteChained({
		parents:'#category',
		url:'${pageContext.request.contextPath}/common/listSubCategories.json?c=1'
	});
	
	$("#price").keydown(function (e) {
		if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 || 
			(e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) || 
			(e.keyCode >= 35 && e.keyCode <= 40)) {
				return;
			}

			if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
				e.preventDefault();
			}
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
	
	$('#saveStartBtn').on('click', function(el) {
		$('#startForm').submit();
	});
	
	$('#startModal').on('hide.bs.modal', function (e) {
		$('#startForm').bootstrapValidator('resetForm', true);
		$('#startForm').trigger('reset');
	});
	
	$('#startModal').on('shown.bs.modal', function () {
		$('#title').focus();
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
	
	<c:if test="${site.listing.listingId eq 0}">
		loadStart();
	</c:if>
});

function loadStart() {
	buildSelect("${pageContext.request.contextPath}/common/listCategories.json", "category", "${site.listing.category.categoryId}");
	buildSelect("${pageContext.request.contextPath}/common/listSubCategories.json?category=${site.listing.category.categoryId}", "subCategory", "${site.listing.subCategory.subCategoryId}");
	
	var country = "${site.listing.address.country}";
	if (country == "") {
		country = "<%=CountryType.CA.getShortName()%>";
	}
	buildSelect("${pageContext.request.contextPath}/common/listProvincesOrStates.json?country=" + country, "province", "${site.listing.address.province}");
	buildSelect("${pageContext.request.contextPath}/common/listCountries.json", "country", country);
	
	displayStart();
}

function buildListingURI() {
	var checkString = "";
	var titleString = $('#title').val();
	var titleArray = titleString.split(" ");
	if (titleArray.length > 0) {
		titleString = "";
		for (x=0; x < titleArray.length; x++) {
			checkString = titleArray[x].replace(/([^a-z0-9]+)/gi, '')
			
			if (checkString != "") {
				titleString += (titleString != "" ? "-" : "");
				titleString += checkString.charAt(0).toUpperCase() + checkString.substr(1).toLowerCase();
			}
		}
	}
	
	$('#listingURI').val(titleString);
}

function saveStart() {
	buildListingURI();
	
	var postData = $('#startForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/saveBasic.do',
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

function displayStart(){
	$('#startModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="startModal" tabIndex="-1" role="dialog" aria-labelledby="startModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="startModalLabel">Required Listing Details</h4>
			</div>
			
			<div class="modal-body">
				<form class="form-horizontal" role="form" name="startForm" id="startForm">
					<input id="<%=TokenFieldType.LISTING.getAlias()%>" name="<%=TokenFieldType.LISTING.getAlias()%>" type="hidden" value="${site.listing.token}" />
					
					<div class="form-group">
						<div class="col-xs-4"><label for="title"><spring:message code="20.lbl.business.name"/>:</label></div>
						<div class="col-xs-8">
							<input id="title" name="title" type="text" value="${site.listing.title}" class="form-control" maxLength="256"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
					
					<div class="form-group" <c:if test="${!pageAccess.systemAdmin}">style="display:none"</c:if>>
						<div class="col-xs-4"><label for="listingURI"><spring:message code="20.lbl.uri"/>:</label></div>
						<div class="col-xs-8">
							<input id="listingURI" name="listingURI" type="text" value="${site.listing.listingURI}" class="form-control" maxLength="256"/>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="category"><spring:message code="20.lbl.category"/>:</label></div>
						<div class="col-xs-8">
							<select id="category" name="category" class="form-control"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>">
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="subCategory"><spring:message code="20.lbl.sub.category"/>:</label></div>
						<div class="col-xs-8">
							<select id="subCategory" name="subCategory" class="form-control"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>">
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="description"><spring:message code="20.lbl.description"/>:</label></div>
						<div class="col-xs-8">
							<textarea id="description" name="description" class="form-control" rows="3" maxLength="2000"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>">${site.listing.description}</textarea>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="price"><spring:message code="20.lbl.price"/>:</label></div>
						<div class="col-xs-4">
							<input id="price" name="price" type="text" value="${site.listing.price}" class="form-control" maxLength="10" />
						</div>
					</div>
					
					<hr/>
					
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
				<button id="saveStartBtn" type="button" class="btn btn-primary"><spring:message code="0.txt.save"/></button>
			</div>
		</div>
	</div>
</div>