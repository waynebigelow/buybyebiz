<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/fileUpload/css/jquery.fileupload.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/fileUpload/css/jquery.fileupload-ui.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/fileUpload/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/fileUpload/js/load-image.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/fileUpload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/fileUpload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/fileUpload/js/jquery.fileupload-process.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/fileUpload/js/jquery.fileupload-image.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/fileUpload/js/jquery.fileupload-audio.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/fileUpload/js/jquery.fileupload-validate.js"></script>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#uploadForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		//setupFileName();

		$('#uploadForm').ajaxSubmit({
			url:'${pageContext.request.contextPath}/listingAdmin/uploadPhoto.do?${listing.requestParamURL}',
			type:'post',
			cache:'false',
			success:function(){
				window.location.reload(true);
			},
			error:function(err){
				showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
			}
		});
	});
	
	$('#uploadModal').on('hide.bs.modal', function (e) {
		$('#uploadForm').bootstrapValidator('resetForm', true);
		$('#uploadForm').trigger('reset');
	});
	
	$('.fileinput-button :file').on('fileselect', function(event, numFiles, label) {
		var input = $(this).parents('.input-group').find(':text');
		input.val(label);
	});
});

$(document).on('change', '.fileinput-button :file', function() {
	var input = $(this),
	numFiles = input.get(0).files ? input.get(0).files.length:1,
	label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
	input.trigger('fileselect', [numFiles, label]);
});

function setupFileName(){
	var file = $("#fileChooser").val();
	var pathSeparator = "\\";
	if(file.indexOf("\\\\") > 1){
		pathSeparator = "/";
	}
	var index = file.lastIndexOf(pathSeparator);
	file = file.substring(index + 1, file.length);

	$("#fileName").val(file);
}

function upload(v){
	$('#photoType').val(v);
	
	$('#uploadModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="uploadModal" tabIndex="-1" role="dialog" aria-labelledby="uploadModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<form role="form" name="uploadForm" id="uploadForm" method="POST" enctype="multipart/form-data">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
					<h4 class="modal-title" id="uploadModalLabel">Upload</h4>
				</div>
				
				<div class="modal-body">
					<input id="<%=TokenFieldType.LISTING.getAlias()%>" name="<%=TokenFieldType.LISTING.getAlias()%>" type="hidden" value="${site.listing.token}" />
					<input id="fileName" name="fileName" value="" type="hidden" />
					<input id="photoType" name="photoType" value="" type="hidden" />
					
					<div class="form-group col-xs-12">
						<div class="input-group">
							<span class="input-group-btn">
								<span class="btn btn-primary fileinput-button">
									<span>Add Photo</span>
									<input id="fileChooser" name="fileChooser" type="file" maxlength="256"
										data-bv-notempty="true" data-bv-notempty-message="Select Photo" 
										data-bv-file="true" data-bv-file-extension="png,jpg,gif,bmp,jpeg" 
										data-bv-file-type="image/png,image/jpg,image/jpeg,image/bmp,image/gif" 
										data-bv-file-message="Supported formats are: png, jpg, gif, bmp, jpeg" />
								</span>
							</span>
							<input type="text" class="form-control" readonly="readonly" />
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