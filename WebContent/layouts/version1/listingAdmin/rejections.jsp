<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.model.configuration.AdminTabType"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
var metaDataTemplate = null;
var photoTemplate = null;
$(document).ready(function(e) {
	metaDataTemplate = Handlebars.compile($('#meta-data-template').html());
	photoTemplate = Handlebars.compile($('#photo-template').html());
});

function loadRejections(v) {
	$.getJSON('${pageContext.request.contextPath}/listingAdmin/metaDataRejections.do', {
		<%=TokenFieldType.LISTING.getAlias()%>:v
	})
	.done(function(data) {
		console.log("metaData :"+data);
		drawMetaData(data);
	});
	
	$.getJSON('${pageContext.request.contextPath}/listingAdmin/photoRejections.do', {
		<%=TokenFieldType.LISTING.getAlias()%>:v
	})
	.done(function(data) {
		console.log("photos: "+data);
		drawPhoto(data);
	});
	
	displayRejections();
}

function drawMetaData(metaDataRejections) {
	var html = "";
	$.each(metaDataRejections, function(i, metaData) {
		var context = {a:metaData.metaDataType,b:metaData.value,c:metaData.reason};
		html = html + metaDataTemplate(context);
	});
	
	if (html != ""){
		$('#metaDataTable').html(html);
	}
}

function drawPhoto(photos) {
	var html = "";
	$.each(photos, function(i, photo) {
		var context = {a:photo.photoPath,b:photo.fileName,c:photo.reason,d:photo.caption};
		html = html + photoTemplate(context);
	});
	
	if (html != ""){
		$('#photoTable').html(html);
	}
}

function displayRejections(){
	$('#rejectionsModal').modal();
}
</script>

<script id="meta-data-template" type="text/x-handlebars-template">
<tr>
	<td>{{a}}</td>
	<td>{{b}}</td>
	<td>{{c}}</td>
</tr>
</script>

<script id="photo-template" type="text/x-handlebars-template">
<tr>
	<td><img class="img-responsive" src="${pageContext.request.contextPath}/{{a}}/{{b}}" title="{{d}}"/></td>
	<td>{{b}}<br/>{{d}}</td>
	<td>{{c}}</td>
</tr>
</script>

<div class="modal fade" id="rejectionsModal" tabIndex="-1" role="dialog" aria-labelledby="rejectionsModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="rejectionsModalLabel">Rejections</h4>
			</div>
					
			<div class="modal-body">
				<div class="table-responsive table-bordered">
					<table class="table table-hover table-condensed">
						<thead>
							<tr>
								<th width="200">Type</th>
								<th width="350">Value</th>
								<th>Reason</th>
							</tr>
						</thead>
						<tbody id="metaDataTable">
							<tr>
								<td colspan="3">There were no meta data rejections found.</td>
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
								<th width="350">FileName/Caption</th>
								<th>Reason</th>
							</tr>
						</thead>
						<tbody id="photoTable">
							<tr>
								<td colspan="3">There were no photo rejections found.</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.close"/></button>
			</div>
		</div>
	</div>
</div>