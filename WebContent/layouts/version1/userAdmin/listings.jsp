<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<script type="text/javascript">
$(document).ready(function(e) {

});

function deleteListing(v){
	$.ajax({
		url:'${pageContext.request.contextPath}/${pageAccess.actionType}/deleteListing.do',
		type:'post',
		data:{
			<%=TokenFieldType.LISTING.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			window.location.reload(true);
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function publishListing(v) {
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/publishListing.do',
		type:'post',
		data:{
			<%=TokenFieldType.LISTING.getAlias()%>:v,
		},
		success:function(data, textStatus, jqXHR) {
			if (data.success == false) {
				$('#alertEmailDiv').html(data.error);
				$('#alertEmailDiv').show();
			} else {
				window.location.reload(true);
			}
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function listingExpired() {
	showAlert('danger', 'md', 'The inbox is disabled for this listing becuase it has expired. You must extend the listing to enable this feature.');
}

function newListing() {
	window.location.href="${pageContext.request.contextPath}/listingAdmin/preview.html";
}

function preview(v) {
	window.location.href="${pageContext.request.contextPath}/listingAdmin/preview.html?<%=TokenFieldType.LISTING.getAlias()%>=" + v;
}
</script>

<button type="button" class="btn btn-primary" onclick="newListing()"><spring:message code="20.btn.1"/></button>
<a style="float:right;font-size:1.3em" tabIndex="-1" href="javascript:viewHL(1)"><span class="glyphicon glyphicon-question-sign"></span></a>

<div style="height:5px"></div>

<div class="table-responsive table-bordered">
	<table class="table table-hover table-condensed">
		<thead>
			<tr>
				<th width="70"><spring:message code="20.col.1"/></th>
				<th width="250"><spring:message code="20.col.2"/></th>
				<th width="100">Page Hits</th>
				<th width="100">Inbox</th>
				<th width="200">Approvals</th>
				<th width="125"><spring:message code="20.col.6"/></th>
				<th><spring:message code="20.col.4"/></th>
			</tr>
		</thead>
		<tbody>
		<c:if test="${listings.size() eq 0}">
			<tr>
				<td colspan="7"><spring:message code="20.msg.none.found"/></td>
			</tr>
		</c:if>
			<c:forEach items="${listings}" var="listing">
				<tr id="listing${listing.token}">
					<td>
						<div class="toolbox">
							<span class="dropdown">
								<span class="glyphicon glyphicon-menu-hamburger editIcon" id="menu${listing.token}" data-toggle="dropdown"></span>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dropDown">
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:preview('${listing.token}')"><span class="glyphicon glyphicon-edit"></span>&nbsp;<spring:message code="0.txt.edit"/></a></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" data-onConfirm="deleteListing('${listing.token}')" data-container="body" data-singleton="true" data-placement="top" data-toggle="confirmation" data-title="<spring:message code="20.msg.delete.warning"/>"><span class="glyphicon glyphicon-trash"></span>&nbsp;<spring:message code="0.txt.delete"/></a></li>
								</ul>
							</span>
						</div>
					</td>
					<td><a href="javascript:preview('${listing.token}')"><span class="glyphicon glyphicon-edit"></span>&nbsp;${listing.title}</a></td>
					<td>${listing.pageHitCount}</td>
					<td>
						<c:if test="${not empty listing.enquiries}">
							<c:if test="${!listing.expired}">
								<a href="javascript:loadInbox('${listing.token}')">
									<span id="badge${listing.token}Total" class="badge" style="background-color:${listing.enquiryCountColor}">${listing.enquiryUnreadCount} Msgs</span>
								</a>
							</c:if>
							<c:if test="${listing.expired}">
								<a href="javascript:listingExpired()">
									<span class="badge" style="background-color:${listing.enquiryCountColor}">${listing.enquiryUnreadCount} Msgs</span>
								</a>
							</c:if>
						</c:if>
						<c:if test="${empty listing.enquiries}">
							No enquiries
						</c:if>
					</td>
					<td>
						Pending: ${listing.pendingCount}
						<c:if test="${listing.rejectedCount gt 0}">
							&nbsp;<a href="javascript:loadRejections('${listing.token}')"><span class="glyphicon glyphicon-share"></span>&nbsp;Rejected: ${listing.rejectedCount}</a>
						</c:if>
					</td>
					<td>
						<c:choose>
						<c:when test="${listing.rejectedCount eq 0 and listing.draft and not empty listing.address.city}">
							<a href="javascript:displayPublish('${listing.token}')"><span class="glyphicon glyphicon-share"></span>&nbsp;Publish Listing</a>
						</c:when>
						<c:when test="${listing.expired}">
							<div style="color:red"><spring:message code="${listing.status.i18n}"/></div>
						</c:when>
						<c:when test="${listing.statusEditable}">
							<a href="javascript:editStatus('${listing.token}','${listing.statusId}')"><span class="glyphicon glyphicon-edit"></span>&nbsp;<spring:message code="${listing.status.i18n}"/></a>
						</c:when>
						<c:otherwise>
							<spring:message code="${listing.status.i18n}"/>
						</c:otherwise>
						</c:choose>
					</td>
					<td>
						${listing.expirationDateFormatted}
						<c:if test="${listing.expired or (listing.active and not empty listing.expirationDate)}">
							<br/><a href="javascript:loadPaymentDetails('${listing.token}','${listing.currencyTypeId}','${listing.user.preferredLocale}')"><span class="glyphicon glyphicon-time"></span>&nbsp;Extend Listing</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<jsp:include page="../listingAdmin/inbox.jsp" flush="true"/>
<jsp:include page="../listingAdmin/listingStatus.jsp" flush="true"/>
<jsp:include page="../listingAdmin/rejections.jsp" flush="true"/>
<jsp:include page="../listingAdmin/commerce/payment.jsp" flush="true"/>
<jsp:include page="../listingAdmin/commerce/publish.jsp" flush="true"/>

<c:if test="${actionId eq 1}">
	<jsp:include page="../listingAdmin/commerce/pending.jsp" flush="true"/>
</c:if>
<c:if test="${actionId eq 2}">
	<jsp:include page="../listingAdmin/commerce/cancelled.jsp" flush="true"/>
</c:if>
<jsp:include page="../help/userAdmin/helpListings.jsp" flush="true" />