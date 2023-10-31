<%@ page import="ca.app.service.common.TokenFieldType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="paging" uri="/WEB-INF/paging.tld"%>

<style>
.no-margins {
	margin: 0px 0px 0px 0px;
}

.margin-bottom {
	margin-bottom: 5px;
}
</style>

<script type="text/javascript">
function deleteLead(v){
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

function sendEmailNow(v) {
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/sendEmail.do',
		type:'post',
		data:{
			<%=TokenFieldType.LISTING_LEAD.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			showAlert('success', 'sm', "The email has been sent.", 1500);
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function updateCheckedValue(fieldId) {
	var fieldValue = $('#promo' + fieldId).val();
	if (fieldValue == "true") {
		$('#promo' + fieldId).val('false');
		
		var enabled = $('#enabledEmails').val().replace(fieldId + ',','');
		$('#enabledEmails').val(enabled)
	} else {
		$('#promo' + fieldId).val('true');
		$('#enabledEmails').val($('#enabledEmails').val() + fieldId + ",");
	}
}
</script>

<button type="button" class="btn btn-primary" onclick="displayLead()">New Lead</button>
<button type="button" class="btn btn-primary" onclick="displayEmail()">Send Email</button>
<div style="height:5px"></div>

<c:if test="${leadsPage.limit > 0 && leadsPage.total > leadsPage.limit}">
	<ul class="pagination pagination-sm no-margins">
		<paging:previous page="${leadsPage}" url="${pageContext.request.contextPath}${tab.action}" />
		<paging:indexes page="${leadsPage}" url="${pageContext.request.contextPath}${tab.action}" numIndexes="5" />
		<paging:next page="${leadsPage}" url="${pageContext.request.contextPath}${tab.action}" />
	</ul>
	<div style="height:5px"></div>
</c:if>

<input type="hidden" id="enabledEmails" name="enabledEmails" value=""/>
<div class="table-responsive table-bordered">
	<table class="table table-hover table-condensed">
		<thead>
			<tr>
				<th width="15" colspan="2">Actions</th>
				<th>Business Name</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Telephone</th>
				<th>Subscribed</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${leadsPage.items.size() eq 0}">
				<tr>
					<td colspan="7">There were no leads found.</td>
				</tr>
			</c:if>
			
			<c:forEach items="${leadsPage.items}" var="lead">
				<tr>
					<td>
						<div class="toolbox">
							<span class="dropdown">
								<span class="glyphicon glyphicon-menu-hamburger editIcon" id="menu${lead.token}" data-toggle="dropdown"></span>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dropDown">
									<c:if test="${pageAccess.systemAdmin}"><li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:editLead('${lead.token}')"><span class="glyphicon glyphicon-pencil"></span>&nbsp;Edit</a></li></c:if>
									<li role="presentation"><a role="menuitem" tabIndex="-1" data-onConfirm="deleteLead('${lead.token}')" data-container="body" data-singleton="true" data-placement="top" data-toggle="confirmation" data-title="<spring:message code="40.msg.delete.warning"/>"><span class="glyphicon glyphicon-trash"></span>&nbsp;<span class="editIcon"><spring:message code="0.txt.delete"/></span></a></li>
								</ul>
							</span>
						</div>
					</td>
					<td>
						<c:if test="${!lead.promoSent}">
							<input type="checkBox" id="promo${lead.token}" value="promo${lead.token}" onclick="updateCheckedValue('${lead.token}')" />
						</c:if>
					</td>
					<td>
						${lead.businessName}
						<c:if test="${not empty lead.website}">
							&nbsp;<a href="${lead.website}" target="_new"><span class="glyphicon glyphicon-globe"></span></a>
						</c:if>
					</td>
					<td>${lead.firstName}</td>
					<td>${lead.lastName}</td>
					<td>${lead.email}</td>
					<td>${lead.telephone}</td>
					<td>${lead.enablePromotionalEmail}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<jsp:include page="listingLead.jsp" flush="true" />
<jsp:include page="email.jsp" flush="true" />