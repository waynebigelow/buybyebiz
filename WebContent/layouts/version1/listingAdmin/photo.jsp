<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/fileUpload/css/jquery.fileupload.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/3rdparty/croppie/css/croppie.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/croppie/js/croppie.min.js"></script>

<script type="text/javascript">
var $uploadCrop;
var imgType = "png";
$(document).ready(function() {
	function readFile(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function (e) {
				$uploadCrop.croppie('bind', {
					url:e.target.result
				});
				
				$('.cropCanvas').addClass('ready');
			}
			
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	$('#savePhotoBtn').on('click', function (ev) {
		if ($('#fileName').val() == "") {
			$('#alertPhotoErrorDiv').html('You must choose a file to upload.');
			$('#alertPhotoErrorDiv').show();
			return;
		}
		
		$('#deletePhotoBtn').prop("disabled",true);
		$('#savePhotoBtn').prop("disabled",true);
		showAlert('info', 'md', 'Please wait ... the photo upload may take a few moments to complete.');
		
		$uploadCrop.croppie('result', {
			type:'canvas',
			size:'original'
		}).then(function (resp) {
			$.ajax({
				url:'${pageContext.request.contextPath}/listingAdmin/savePhoto.do',
				type:'post',
				cache:'false',
				data:{
					<%=TokenFieldType.LISTING.getAlias()%>:$('#<%=TokenFieldType.LISTING.getAlias()%>').val(),
					<%=TokenFieldType.PHOTO.getAlias()%>:$('#<%=TokenFieldType.PHOTO.getAlias()%>').val(),
					fileName:$('#fileName').val(),
					photoType:$('#photoType').val(),
					caption:$('#caption').val(),
					imgBase64:resp
				},
				success:function(data, textStatus, jqXHR){
					window.location.href='${pageContext.request.contextPath}/listingAdmin/preview.html?<%=TokenFieldType.LISTING.getAlias()%>=' + data.success;
				},
				error:function(qXHR, textStatus, errorThrown){
					showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
				}
			});
		});
	});
	
	$('#deletePhotoBtn').on('click', function (ev) {
		$.ajax({
			url:'${pageContext.request.contextPath}/listingAdmin/deletePhoto.do',
			type:'post',
			data:{
				<%=TokenFieldType.PHOTO.getAlias()%>:$('#<%=TokenFieldType.PHOTO.getAlias()%>').val(),
				<%=TokenFieldType.LISTING.getAlias()%>:$('#<%=TokenFieldType.LISTING.getAlias()%>').val()
			},
			success:function(data, textStatus, jqXHR) {
				window.location.reload(true);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
			}
		});
	});
	
	$('#photoModal').on('hide.bs.modal', function (e) {
		$('#photoForm').bootstrapValidator('resetForm', true);
		$('#photoForm').trigger('reset');
		$uploadCrop.croppie('destroy');
		$('#fileName').val('');
		$('.cr-boundary').append('');
		$('#cropCanvas').append('');
		$('#cropCanvas').hide();
		$('#alertPhotoErrorDiv').hide();
	});

	$('.fileinput-button :file').on('fileselect', function(event, numFiles, label) {
		var input = $(this).parents('.input-group').find(':text');
		input.val(label);
		$('#fileName').val(label);
		readFile(this);
		$('#cropCanvas').show();
		$('#alertPhotoErrorDiv').hide();
	});
});

$(document).on('change', '.fileinput-button :file', function() {
	var input = $(this),
	numFiles = input.get(0).files ? input.get(0).files.length:1,
	label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
	input.trigger('fileselect', [numFiles, label]);
});

function editPhoto(type, v, vwWd, vwHt, bdWd, bdHt) {
	$('#photoType').val(type);
	$('#<%=TokenFieldType.PHOTO.getAlias()%>').val(v);
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/editPhoto.do',
		type:'get',
		data:{
			<%=TokenFieldType.PHOTO.getAlias()%>:v,
			<%=TokenFieldType.LISTING.getAlias()%>:$('#<%=TokenFieldType.LISTING.getAlias()%>').val()
		},
		success:function(data, textStatus, jqXHR) {
			$('#viewPhoto').html("<img height='"+vwHt+"' width='"+vwWd+"' class='img-responsive' src='${pageContext.request.contextPath}/"+data.photo.photoPath+"' />");
			$('#caption').val(data.photo.caption);
			$('#btnLabel').html('Replace Photo');
			$('#viewPhotoSect').show();
			$('#deletePhotoBtn').show();
			
			handleCroppie(vwWd, vwHt, bdWd, bdHt);
			displayPhoto();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function addPhoto(type, v, vwWd, vwHt, bdWd, bdHt) {
	$('#photoType').val(type);
	$('#<%=TokenFieldType.PHOTO.getAlias()%>').val(v);
	$('#btnLabel').html('Add Photo');
	$('#viewPhotoSect').hide();
	$('#deletePhotoBtn').hide();
	
	handleCroppie(vwWd, vwHt, bdWd, bdHt);
	displayPhoto();
}

function handleCroppie(vwWd, vwHt, bdWd, bdHt) {
	$uploadCrop = $('#cropCanvas').croppie({
		viewport: {
			width:vwWd,
			height:vwHt,
			type:'square'
		},
		boundary: {
			width:bdWd,
			height:bdHt
		}
	});
	$('.cr-boundary').append('<div class="text-center" style="width:100%;color:#ffffff;zindex:1000;position:absolute;opacity:1">Click picture to position.</div>');
	$('#cropCanvas').append('<div class="text-center">Slide to Zoom</div>');
}

function displayPhoto() {
	$('#photoModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="photoModal" tabIndex="-1" role="dialog" aria-labelledby="photoModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<form role="form" class="form-horizontal" name="photoForm" id="photoForm" enctype="multipart/form-data">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
					<h4 class="modal-title" id="photoModalLabel">Photo</h4>
				</div>
				
				<div class="modal-body">
					<input type="hidden" id="<%=TokenFieldType.LISTING.getAlias()%>" name="<%=TokenFieldType.LISTING.getAlias()%>" value="${site.listing.token}" />
					<input type="hidden" id="<%=TokenFieldType.PHOTO.getAlias()%>" name="<%=TokenFieldType.PHOTO.getAlias()%>" value="" />
					<input type="hidden" id="photoType" name="photoType" value="" />
					<input type="hidden" id="fileName" name="fileName" value="" />
					
					<div id="alertPhotoErrorDiv" class="alert alert-danger" style="display:none"></div>
					<div class="form-group" id="viewPhotoSect" style="display:none;">
						<div class="col-xs-3"><label for="caption">Current Photo:</label></div>
						<div class="col-xs-9">
							<div id="viewPhoto"></div>
						</div>
					</div>
					
					<div id="addPhoto">
						<div class="form-group">
							<div class="col-xs-12">
								<div class="input-group">
									<span class="input-group-btn">
										<span class="btn btn-primary fileinput-button">
											<span id="btnLabel"></span>
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
						
						<div id="cropCanvas" style="display:none"></div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-3"><label for="caption">Caption:</label></div>
						<div class="col-xs-9">
							<input type="text" id="caption" name="caption" value="" class="form-control" maxLength="256" />
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
					<button type="button" id="deletePhotoBtn" class="btn btn-primary" style=""><spring:message code="0.txt.delete"/></button>
					<button type="button" id="savePhotoBtn" class="btn btn-primary"><spring:message code="0.txt.save"/></button>
				</div>
			</form>
		</div>
	</div>
</div>