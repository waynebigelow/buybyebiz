<%@page import="ca.app.model.listing.ListingStatus"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
var <%=TokenFieldType.LISTING.getAlias()%>;
$(document).ready(function(e) {
	$('#saveStatusBtn').on('click', function(el) {
		changeStatus();
	});
});

function editStatus(v,w) {
	<%=TokenFieldType.LISTING.getAlias()%> = v;
	$('#listingStatus').val(w);
	
	displayStatus();
}

function changeStatus(){
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/changeStatus.do',
		type:'post',
		data:{
			<%=TokenFieldType.LISTING.getAlias()%>:<%=TokenFieldType.LISTING.getAlias()%>,
			statusId:$('#listingStatus').val()
		},
		success:function(data, textStatus, jqXHR) {
			window.location.reload(true);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function displayMsg() {
	var statusId = $('#listingStatus').val();
	var msg = "";
	if (statusId == 2) {
		msg = "Reactivating your listing... ";
	} else if (statusId == 3) {
		msg = "Putting your listing on SUSPENSION will...";
	} else if (statusId == 4) {
		msg = "Marking your listing as SOLD will be removed from BuyByeBiz listings search after 2 weeks.";
	}
	$('#alertChangeStatusDiv').html(msg);
	$('#alertChangeStatusDiv').show();
}

function displayStatus(){
	$('#statusModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<div class="modal fade" id="statusModal" tabIndex="-1" role="dialog" aria-labelledby="statusModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<form class="form-horizontal" role="form" name="statusForm" id="statusForm">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
					<h4 class="modal-title" id="statusModalLabel">Change Status</h4>
				</div>
				
				<div class="modal-body">
					<div id="alertChangeStatusDiv" class="alert alert-info" style="display:none"></div>
					<div class="form-group">
						<div class="col-xs-12">
							<select id="listingStatus" name="listingStatus" class="form-control" onchange="displayMsg()">
								<option value="">--Select Status--</option>
								<c:forEach items="<%=ListingStatus.values()%>" var="status">
									<c:if test="${status.userAdmin}">
										<option value="${status.id}"><spring:message code="${status.i18n}" /></option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.cancel"/></button>
					<button type="button" id="saveStatusBtn" class="btn btn-primary"><spring:message code="0.txt.save"/></button>
				</div>
			</form>
		</div>
	</div>
</div>