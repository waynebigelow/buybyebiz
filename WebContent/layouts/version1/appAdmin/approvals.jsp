<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.model.configuration.AdminTabType"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/bootstrap/checkbox/js/bootstrap-checkbox.min.js"></script>

<script type="text/javascript">
var approvalFormListingId;
var metaDataTemplate = null;
var photoTemplate = null;
$(document).ready(function(e) {
	metaDataTemplate = Handlebars.compile($('#meta-data-template').html());
	photoTemplate = Handlebars.compile($('#photo-template').html());
	
	$('#approvalForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		saveApprovals();
	});
});

function loadApprovals(v) {
	approvalFormListingId = v;
	
	$.getJSON('${pageContext.request.contextPath}/appAdmin/metaDataApprovals.do', {
		<%=TokenFieldType.LISTING.getAlias()%>:v
	})
	.done(function(data) {
		drawMetaData(data);
	});
	
	$.getJSON('${pageContext.request.contextPath}/appAdmin/photoApprovals.do', {
		<%=TokenFieldType.LISTING.getAlias()%>:v
	})
	.done(function(data) {
		drawPhoto(data);
	});
	
	displayApprovals();
}

function drawMetaData(metaDataApprovals) {
	var html = "";
	$.each(metaDataApprovals, function(i, metaDataApproval) {
		var context = {a:metaDataApproval.metaDataType,b:metaDataApproval.value,c:metaDataApproval.metaDataId};
		html = html + metaDataTemplate(context);
	});
	
	if (html != ""){
		$('#metaDataTable').html(html);
		
		$(':checkbox').checkboxpicker();
	}
}

function drawPhoto(photos) {
	var html = "";
	$.each(photos, function(i, photo) {
		var context = {a:photo.photoPath,b:photo.fileName,c:photo.photoId,d:photo.caption};
		html = html + photoTemplate(context);
	});
	
	if (html != ""){
		$('#photoTable').html(html);
		
		$(':checkbox').checkboxpicker();
	}
}

function displayApprovals(){
	$('#approvalsModal').modal();
}

function saveApprovals(){
	var postData = $('#approvalForm').serializeArray();
	postData[postData.length] = {name:"<%=TokenFieldType.LISTING.getAlias()%>",value:approvalFormListingId};
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/saveApprovals.do',
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

<script id="meta-data-template" type="text/x-handlebars-template">
<tr>
	<td>{{a}}</td>
	<td>{{b}}</td>
	<td><input  type="checkbox" name="approveMeta{{c}}" id="approveMeta{{c}}" data-off-label="false" data-on-label="false" data-off-icon-cls="glyphicon-thumbs-down" data-on-icon-cls="glyphicon-thumbs-up"></td>
	<td><textarea id="metaReason{{c}}" name="metaReason{{c}}" class="form-control" rows="1" maxLength="500"></textarea></td>
</tr>
</script>

<script id="photo-template" type="text/x-handlebars-template">
<tr>
	<td><img class="img-responsive" src="${pageContext.request.contextPath}/{{a}}/{{b}}" title="{{d}}"/></td>
	<td>{{b}}<br/>{{d}}</td>
	<td><input  type="checkbox" name="approvePhoto{{c}}" id="approvePhoto{{c}}" data-off-label="false" data-on-label="false" data-off-icon-cls="glyphicon-thumbs-down" data-on-icon-cls="glyphicon-thumbs-up"></td>
	<td><textarea id="photoReason{{c}}" name="photoReason{{c}}" class="form-control" rows="1" maxLength="500"></textarea></td>
</tr>
</script>

<div class="modal fade" id="approvalsModal" tabIndex="-1" role="dialog" aria-labelledby="approvalsModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form class="form-horizontal" role="form" name="approvalForm" id="approvalForm">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
					<h4 class="modal-title" id="approvalsModalLabel">Pending Approval</h4>
				</div>
						
				<div class="modal-body">
					<div class="table-responsive table-bordered">
						<table class="table table-hover table-condensed">
							<thead>
								<tr>
									<th width="200">Type</th>
									<th width="350">Value</th>
									<th width="100">Reject / Accept</th>
									<th>Reason</th>
								</tr>
							</thead>
							<tbody id="metaDataTable">
								<tr>
									<td colspan="4"><spring:message code="100.msg.none.found"/></td>
								</tr>
							</tbody>
						</table>
					</div>
					<br/>
					<div class="table-responsive table-bordered">
						<table class="table table-hover table-condensed">
							<thead>
								<tr>
									<th width="200">Photo</th>
									<th width="350">FileName / Caption</th>
									<th width="100">Reject / Accept</th>
									<th>Reason</th>
								</tr>
							</thead>
							<tbody id="photoTable">
								<tr>
									<td colspan="4"><spring:message code="100.msg.none.found"/></td>
								</tr>
							</tbody>
						</table>
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