<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.model.application.ApplicationType"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#categoryForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		saveCategory();
	});
	
	$('#categoryModal').on('hide.bs.modal', function (e) {
		$('#categoryForm').bootstrapValidator('resetForm', true);
		$('#categoryForm').trigger('reset');
		$('#subCategoryHeader').html('');
	});
});

function addCategory(type) {
	$("#type").val(type);
	if (type == "s") {
		if ($("#c").val() == "") {
			showAlert('danger', 'md', '<spring:message code="75.msg.select.category.warning" javaScriptEscape="true" />');
			return;
		}
		
		$('#typeGrp').hide();
		$("#s").val("");
	} else if (type == "c") {
		$('#typeGrp').show();
		$("#c").val("");
	}
	displayCategory();
}

function displayCategory(){
	$('#categoryModal').modal({
		backdrop:'static',
		keyboard:false
	});
}

function saveCategory(){
	if ($("#type").val() == "c") {
		var postData = $('#categoryForm').serializeArray();
		$.ajax({
			url:'${pageContext.request.contextPath}/appAdmin/saveCategory.do',
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
	} else {
		saveSubCategory();
	}
}

function saveSubCategory(){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/saveCategory.do',
		type: 'get',
		data:{
			s:$('#s').val(),
			c:$('#c').val(),
			name:$('#name').val(),
			i18n:$('#i18n').val(),
			type:'s'
		},
		success:function(data, textStatus, jqXHR) {
			loadSubCategories($('#c').val(), null);
			
			$('#categoryModal').modal('hide');
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function editCategory(c, type){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/editCategory.do',
		type: 'get',
		data:{
			c:c,
			type:type
		},
		success:function(data, textStatus, jqXHR) {
			if (type == "s") {
				$('#typeGrp').hide();
				$('#s').val(c);
				$('#c').val(data.category.categoryId);
			} else {
				$('#typeGrp').show();
				$('#appType').val(data.category.typeId);
				$('#c').val(c);
			}
			$('#type').val(type);
			$('#name').val(data.category.name);
			$('#i18n').val(data.category.i18n);
			
			displayCategory();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function deleteCategory(c, type){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/deleteCategory.do',
		type:'post',
		data:{
			c:c,
			type:type
		},
		success:function(data, textStatus, jqXHR) {
			if (type == "s") {
				loadSubCategories($('#c').val(), null);
			} else {
				window.location.reload(true);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}
</script>

<div class="modal fade" id="categoryModal" tabIndex="-1" role="dialog" aria-labelledby="categoryModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<form class="form-horizontal" role="form" name="categoryForm" id="categoryForm">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
					<h4 class="modal-title" id="categoryModalLabel"><spring:message code="75.txt.category"/></h4>
				</div>
				<input id="c" name="c" type="hidden" value="" />
				<input id="s" name="s" type="hidden" value="" />
				<input id="type" name="type" type="hidden" value="" />
				
				<div class="modal-body">
					<div class="form-group" id="typeGrp" style="display:none">
						<div class="col-xs-3"><label for="appType"><spring:message code="35.lbl.type"/>:</label></div>
						<div class="col-xs-9">
							<select id="appType" name="appType" class="form-control">
								<option value="">--Select Type--</option>
								<c:forEach items="<%=ApplicationType.values()%>" var="type">
									<option value="${type.id}"><spring:message code="${type.i18n}" /></option>
								</c:forEach>
							</select>
						</div>
					</div>
				
					<div class="form-group">
						<div class="col-xs-3"><label for="name"><spring:message code="75.lbl.name"/>:</label></div>
						<div class="col-xs-9">
							<input id="name" name="name" type="text" class="form-control" maxLength="256"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="name"><spring:message code="75.lbl.i18n"/>:</label></div>
						<div class="col-xs-9">
							<input id="i18n" name="i18n" type="text" class="form-control" maxLength="256"
								data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
					<button type="submit" class="btn btn-primary"><spring:message code="0.txt.save"/></button>
				</div>
			</form>
		</div>
	</div>
</div>