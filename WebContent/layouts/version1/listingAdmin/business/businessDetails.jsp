<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>
<%@ page import="ca.app.model.listing.PropertyType"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#accordion').on('hidden.bs.collapse', toggleChevron);
	$('#accordion').on('shown.bs.collapse', toggleChevron);
	
	$('#businessDetailsModal').on('hide.bs.modal', function (e) {
		$('#businessDetailsForm').bootstrapValidator('resetForm', true);
		$('#businessDetailsForm').trigger('reset');
	});
	
	$('#businessDetailsModal').on('shown.bs.modal', function () {
		$('#salesRevMin').focus();
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
	
	$("#salesRevMin, #salesRevMax, #inventoryTotal, #cashFlow, #chattelTotal").keydown(function (e) {
		if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
			(e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) || 
			(e.keyCode >= 35 && e.keyCode <= 40)) {
				return;
			}

			if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
				e.preventDefault();
			}
	});
});

function editBusinessDetails() {
	displayBusinessDetails();
}

function saveBusinessDetails() {
	var postData = $('#businessDetailsForm').serializeArray();
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/saveBusinessDetails.do',
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

function displayBusinessDetails(){
	$('#businessDetailsModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="businessDetailsModal" tabIndex="-1" role="dialog" aria-labelledby="businessDetailsModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="businessDetailsModalLabel">Business Details</h4>
			</div>
			
			<div class="modal-body">
				<form class="form-horizontal" role="form" name="businessDetailsForm" id="businessDetailsForm">
					<input id="<%=TokenFieldType.LISTING.getAlias()%>" name="<%=TokenFieldType.LISTING.getAlias()%>" type="hidden" value="${site.listing.token}" />
					<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
						<div class="panel panel-default">
							<div class="panel-heading" role="tab" id="financialsHeading" style="background-color:#96a5b3;color:#ffffff">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion" href="#financialsPanel" aria-expanded="true" aria-controls="financialsPanel">
										<i class="indicator glyphicon glyphicon-chevron-up"></i>
										Financial Details
									</a>
								</h4>
							</div>
							
							<div id="financialsPanel" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="financialsHeading">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="salesRevMin"><spring:message code="20.lbl.min.sales.rev.label"/>:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="salesRevMin" name="salesRevMin" type="text" value="${site.listing.businessDetails.salesRevenueMin}" class="form-control" maxLength="10"
												data-fv-integer-message="<spring:message code="0.msg.invalid.number.warning"/>" />
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="salesRevMax"><spring:message code="20.lbl.max.sales.rev.label"/>:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="salesRevMax" name="salesRevMax" type="text" value="${site.listing.businessDetails.salesRevenueMax}" class="form-control" maxLength="10"
												data-fv-integer-message="<spring:message code="0.msg.invalid.number.warning"/>" />
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="cashFlow"><spring:message code="20.lbl.cash.flow.label"/>:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="cashFlow" name="cashFlow" type="text" value="${site.listing.businessDetails.cashFlow}" class="form-control" maxLength="10"
												data-fv-integer-message="<spring:message code="0.msg.invalid.number.warning"/>" />
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="inventoryTotal"><spring:message code="20.lbl.inventory.label"/>:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="inventoryTotal" name="inventoryTotal" type="text" value="${site.listing.businessDetails.inventoryTotal}" class="form-control" maxLength="10"
												data-fv-integer-message="<spring:message code="0.msg.invalid.number.warning"/>" />
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="chattelTotal"><spring:message code="20.lbl.chattel.label"/>:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="chattelTotal" name="chattelTotal" type="text" value="${site.listing.businessDetails.chattelTotal}" class="form-control" maxLength="10"
												data-fv-integer-message="<spring:message code="0.msg.invalid.number.warning"/>" />
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="financialOther">Other Financial Details:</label></div>
										<div class="col-xs-12 col-sm-9">
											<textarea id="financialOther" name="financialOther" rows="2" class="form-control" maxLength="1000">${site.listing.businessDetails.financialOther}</textarea>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="panel panel-default">
							<div class="panel-heading" role="tab" id="propertyHeading" style="background-color:#96a5b3;color:#ffffff">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion" href="#propertyPanel" aria-expanded="true" aria-controls="propertyPanel">
										<i class="indicator glyphicon glyphicon-chevron-down"></i>
										Operational Details
									</a>
								</h4>
							</div>
							
							<div id="propertyPanel" class="panel-collapse collapse" role="tabpanel" aria-labelledby="propertyHeading">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="hoursOfOperation">Hours of Operation:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="hoursOfOperation" name="hoursOfOperation" type="text" value="${site.listing.businessDetails.hoursOfOperation}" class="form-control" maxLength="256" />
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="numberOfEmployees">Number of Employees:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="numberOfEmployees" name="numberOfEmployees" type="text" value="${site.listing.businessDetails.numberOfEmployees}" class="form-control" maxLength="256" />
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="numberOfEmployees">Year Established:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="yearEstablished" name="yearEstablished" type="text" value="${site.listing.businessDetails.yearEstablished}" class="form-control" maxLength="256" />
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="operationOther">Other Operational Details:</label></div>
										<div class="col-xs-12 col-sm-9">
											<textarea id="operationOther" name="operationOther" rows="2" class="form-control" maxLength="1000">${site.listing.businessDetails.operationOther}</textarea>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="panel panel-default">
							<div class="panel-heading" role="tab" id="operationsHeading" style="background-color:#96a5b3;color:#ffffff">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion" href="#operationsPanel" aria-expanded="true" aria-controls="operationsPanel">
										<i class="indicator glyphicon glyphicon-chevron-down"></i>
										Property Details
									</a>
								</h4>
							</div>
							
							<div id="operationsPanel" class="panel-collapse collapse" role="tabpanel" aria-labelledby="operationsHeading">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="propertyType">Property Type:</label></div>
										<div class="col-xs-12 col-sm-9">
											<select id="propertyType" name="propertyType" class="form-control">
												<option value="">--Select Property Type--</option>
												<c:forEach items="<%=PropertyType.values()%>" var="type">
													<option value="${type.id}" <c:if test="${site.listing.businessDetails.propertyTypeId eq type.id}">selected="selected"</c:if>><spring:message code="${type.i18n}" /></option>
												</c:forEach>
											</select>
										</div>
									</div>
										
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="propertyTax">Property Tax:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="propertyTax" name="propertyTax" type="text" value="${site.listing.businessDetails.propertyTax}" class="form-control" maxLength="256" />
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="frontage">Frontage & Lot Depth:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="frontage" name="frontage" type="text" value="${site.listing.businessDetails.frontage}" class="form-control" maxLength="256" />
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="squareFootage">Square Footage:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="squareFootage" name="squareFootage" type="text" value="${site.listing.businessDetails.squareFootage}" class="form-control" maxLength="256" />
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="acreage">Acreage:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="acreage" name="acreage" type="text" value="${site.listing.businessDetails.acreage}" class="form-control" maxLength="256" />
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="ownersResidence">Owners Residence:</label></div>
										<div class="col-xs-12 col-sm-9">
											<textarea id="ownersResidence" name="ownersResidence" rows="2" class="form-control" maxLength="1000">${site.listing.businessDetails.ownersResidence}</textarea>
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="propertyOther">Other Property Details:</label></div>
										<div class="col-xs-12 col-sm-9">
											<textarea id="propertyOther" name="propertyOther" rows="2" class="form-control" maxLength="1000">${site.listing.businessDetails.propertyOther}</textarea>
										</div>
									</div>
								</div>
							</div>
						</div>
							
						<div class="panel panel-default">
							<div class="panel-heading" role="tab" id="otherHeading" style="background-color:#96a5b3;color:#ffffff">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion" href="#otherPanel" aria-expanded="true" aria-controls="otherPanel">
										<i class="indicator glyphicon glyphicon-chevron-down"></i>
										Other Details
									</a>
								</h4>
							</div>
							
							<div id="otherPanel" class="panel-collapse collapse" role="tabpanel" aria-labelledby="otherHeading">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="sellingReason">Reason for Selling:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="sellingReason" name="sellingReason" type="text" value="${site.listing.businessDetails.sellingReason}" class="form-control" maxLength="256" />
										</div>
									</div>
								
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="support">Support / Training:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="support" name="support" type="text" value="${site.listing.businessDetails.support}" class="form-control" maxLength="256" />
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-3"><label for="ownerFinancing">Owner Financing:</label></div>
										<div class="col-xs-12 col-sm-9">
											<input id="ownerFinancing" name="ownerFinancing" type="text" value="${site.listing.businessDetails.ownerFinancing}" class="form-control" maxLength="256" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
				<button type="button" class="btn btn-primary" onclick="saveBusinessDetails()"><spring:message code="0.txt.save"/></button>
			</div>
		</div>
	</div>
</div>