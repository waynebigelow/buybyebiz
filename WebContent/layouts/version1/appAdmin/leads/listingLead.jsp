<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.mail.GlobalEmailType"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#leadForm').bootstrapValidator()
	.on('success.form.bv', function(e) {
		e.preventDefault();
		
		saveLead();
	});

	$('#saveLeadBtn').on('click', function(el) {
		$('#leadForm').submit();
	});
	
	$('#leadModal').on('hide.bs.modal', function (e) {
		$('#leadForm').bootstrapValidator('resetForm', true);
		$('#leadForm').trigger('reset');
	});
	
	$('#leadModal').on('shown.bs.modal', function () {
		$('#leadBusinessName').focus();
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		$('#leadModal').modal('hide');
	});
});

function editLead(v) {
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/editLead.do',
		type: 'get',
		data:{
			<%=TokenFieldType.LISTING_LEAD.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			$('#<%=TokenFieldType.LISTING_LEAD.getAlias()%>').val(v);
			$('#leadBusinessName').val(data.lead.businessName);
			$('#leadFirstName').val(data.lead.firstName);
			$('#leadLastName').val(data.lead.lastName);
			$('#leadEmail').val(data.lead.email);
			$('#leadTelephone').val(data.lead.telephone);
			$('#leadWebsite').val(data.lead.website);
			
			displayLead();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function saveLead() {
	var postData = $('#leadForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/saveLead.do',
		type:'post',
		data:postData,
		success:function(data, textStatus, jqXHR) {
			window.location.reload(true);
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function deleteLead(v) {
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/deleteLead.do',
		type:'post',
		data:{
			<%=TokenFieldType.LISTING_LEAD.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			window.location.reload(true);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function displayLead(){
	$('#leadModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="leadModal" tabIndex="-1" role="dialog" aria-labelledby="leadModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<form name="leadForm" id="leadForm" class="form-horizontal" role="form">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title" id="leadModalLabel">Listing Lead</h4>
				</div>
				<input id="<%=TokenFieldType.LISTING_LEAD.getAlias()%>" name="<%=TokenFieldType.LISTING_LEAD.getAlias()%>" type="hidden" value="" />
				
				<div class="modal-body">
					<div class="form-group">
						<div class="col-xs-4"><label for="leadBusinessName">Business Name:</label></div>
						<div class="col-xs-8">
							<input id="leadBusinessName" name="leadBusinessName" type="text" class="form-control" maxLength="256" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="leadFirstName">First Name:</label></div>
						<div class="col-xs-8">
							<input id="leadFirstName" name="leadFirstName" type="text" class="form-control" maxLength="256" />
						</div>
					</div>
			
					<div class="form-group">
						<div class="col-xs-4"><label for="leadLastName">Last Name:</label></div>
						<div class="col-xs-8">
							<input id="leadLastName" name="leadLastName" type="text" class="form-control" maxLength="256" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="leadEmail">Email Address:</label></div>
						<div class="col-xs-8">
							<input id="leadEmail" name="leadEmail" type="email" class="form-control" maxLength="256"
									data-bv-emailaddress-message="<spring:message code="0.msg.invalid.email.warning"/>" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="leadTelephone">Telephone:</label></div>
						<div class="col-xs-8">
							<input id="leadTelephone" name="leadTelephone" type="text" class="form-control" maxLength="256" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="leadWebsite">Website:</label></div>
						<div class="col-xs-8">
							<input id="leadWebsite" name="leadWebsite" type="text" class="form-control" maxLength="256" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-4"><label for="leadPromoSent">Is Promo Sent:</label></div>
						<div class="col-xs-8">
							<select id="leadPromoSent" name="leadPromoSent">
								<option id="true" <c:if test="${!lead.promoSent}">selected="selected"</c:if>>true</option>
								<option id="false" <c:if test="${lead.promoSent}">selected="selected"</c:if>>false</option>
							</select>
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary" id="saveLeadBtn">Save</button>
				</div>
			</form>
		</div>
	</div>
</div>