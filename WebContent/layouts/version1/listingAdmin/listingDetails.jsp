<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#listingDetailsForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		saveListingDetails();
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
	
	$('#saveListingDetailsBtn').on('click', function(el) {
		$('#listingDetailsForm').submit();
	});
	
	$('#listingDetailsModal').on('hide.bs.modal', function (e) {
		$('#listingDetailsForm').bootstrapValidator('resetForm', true);
		$('#listingDetailsForm').trigger('reset');
	});
	
	$('#listingDetailsModal').on('shown.bs.modal', function () {
		$('#title').focus();
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
	
	<c:if test="${site.listing.listingId eq 0}">
		editListingDetails();
	</c:if>
});

function editListingDetails() {
	buildSelect("${pageContext.request.contextPath}/common/listCategories.json", "category", "${site.listing.category.categoryId}");
	buildSelect("${pageContext.request.contextPath}/common/listSubCategories.json?category=${site.listing.category.categoryId}", "subCategory", "${site.listing.subCategory.subCategoryId}");
	
	displayListingDetails();
}

function saveListingDetails() {
	var postData = $('#listingDetailsForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/saveListingDetails.do',
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

function displayListingDetails(){
	$('#listingDetailsModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="listingDetailsModal" tabIndex="-1" role="dialog" aria-labelledby="listingDetailsModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="listingDetailsModalLabel">Listing Details</h4>
			</div>
			
			<div class="modal-body">
				<form class="form-horizontal" role="form" name="listingDetailsForm" id="listingDetailsForm">
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
				</form>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
				<button id="saveListingDetailsBtn" type="button" class="btn btn-primary"><spring:message code="0.txt.save"/></button>
			</div>
		</div>
	</div>
</div>