<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="paging" uri="/WEB-INF/paging.tld"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>
<%@ page import="ca.app.model.listing.ListingStatus"%>

<style>
.no-margins {
	margin: 0px 0px 5px 0px;
}
</style>

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

function toggleListing(v, u){
	$.ajax({
		url:'${pageContext.request.contextPath}/${pageAccess.actionType}/toggleListing.do',
		type:'post',
		data:{
			<%=TokenFieldType.LISTING.getAlias()%>:v
		},
		success:function(data, textStatus, jqXHR) {
			loadUserListings(u);
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function displayHandler(v) {
	$('.statusHandler').html('');
	$('#statusHandler'+v).html(appendSaveIcon(v));
	setTimeout(function() {handleTimeout(v)}, 3000);
}

function handleTimeout(v) {
	if ($('.statusHandler').html() != '' && $('.statusHandler').html().indexOf('glyphicon-saved') == -1) {
		$('.statusHandler').html('');
		$('#status'+v).val($('#status'+v).attr('data-original-value'));
	}
}

function appendSaveIcon(v) {
	return "<span><a href=\"javascript:saveListingStatus('"+v+"')\"><span class=\"glyphicon glyphicon-save\"></span></a></span>";
}

function saveListingStatus(v){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/saveListingStatus.do',
		type: 'post',
		data:{
			<%=TokenFieldType.LISTING.getAlias()%>:v,
			listingStatus:$('#status'+v).val()
		},
		success:function(data, textStatus, jqXHR) {
			window.location.reload(true);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function newListing() {
	window.location.href="${pageContext.request.contextPath}/listingAdmin/preview.html";
}

function preview(v) {
	window.location.href="${pageContext.request.contextPath}/listingAdmin/preview.html?<%=TokenFieldType.LISTING.getAlias()%>=" + v;
}
</script>


<button type="button" class="btn btn-primary" onclick="newListing()"><spring:message code="20.btn.1"/></button>
<div style="height:5px"></div>

<form class="form-horizontal" role="form" name="searchForm" id="searchForm">
	<div class="form-group no-margins">
		<div class="col-xs-12 col-sm-4">
			<select id="area" name="area" onchange="handleSearch()" class="form-control">
				<option value="0">--Filter by Application--</option>
			</select>
		</div>
	</div>
</form>

<c:if test="${page.limit > 0 && page.total > page.limit}">
	<ul class="pagination pagination-sm no-margins">
		<paging:previous page="${page}" url="${pageContext.request.contextPath}${tab.action}" />
		<paging:indexes page="${page}" url="${pageContext.request.contextPath}${tab.action}" numIndexes="10" />
		<paging:next page="${page}" url="${pageContext.request.contextPath}${tab.action}" />
	</ul>
</c:if>

<div class="table-responsive table-bordered">
	<table class="table table-hover table-condensed">
		<thead>
			<tr>
				<th width="90"><spring:message code="20.col.1"/></th>
				<th width="250"><spring:message code="20.col.2"/></th>
				<th width="100">Page Hits</th>
				<th width="200"><spring:message code="20.col.6"/></th>
				<th width="200"><spring:message code="20.col.4"/></th>
				<th><spring:message code="20.col.3"/></th>
			</tr>
		</thead>
		<tbody>
		<c:if test="${listingPage.items.size() eq 0}">
			<tr>
				<td colspan="6"><spring:message code="20.msg.none.found"/></td>
			</tr>
		</c:if>
			<c:forEach items="${listingPage.items}" var="listing">
				<tr>
					<td>
						<div class="toolbox">
							<span class="dropdown">
								<span class="glyphicon glyphicon-menu-hamburger editIcon" id="menu${listing.token}" data-toggle="dropdown"></span>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dropDown">
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:preview('${listing.token}')"><span class="glyphicon glyphicon-edit"></span>&nbsp;<spring:message code="0.txt.edit"/></a></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" data-onConfirm="deleteListing('${listing.token}')" data-container="body" data-singleton="true" data-placement="top" data-toggle="confirmation" data-title="<spring:message code="20.msg.delete.warning"/>"><span class="glyphicon glyphicon-trash"></span>&nbsp;<spring:message code="0.txt.delete"/></a></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:toggleListing('${listing.token}','${listing.user.token}')"><span class="glyphicon glyphicon-thumbs-${listing.icon}"></span>&nbsp;${listing.toggleText}</a></li>
									<li role="presentation" class="divider"></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:activityLog('${listing.primaryToken}','l')">Activity Log</a></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:notificationLog('${listing.primaryToken}','l')">Notification Log</a></li>
								</ul>
							</span>
							<span>
								&nbsp;<span class="glyphicon glyphicon-thumbs-${listing.oppositeIcon}"></span>
								<c:if test="${listing.pendingCount gt 0}">
									&nbsp;<a href="javascript:loadApprovals('${listing.token}')"><span class='glyphicon glyphicon-inbox' ></span></a>
								</c:if>
								&nbsp;<a href="javascript:loadPayment('${listing.token}')"><span class='glyphicon glyphicon-piggy-bank' ></span></a>
							</span>
						</div>
					</td>
					<td><a href="javascript:preview('${listing.token}')">${listing.title}</a></td>
					<td>${listing.pageHitCount}</td>
					<td>
						<select id="status${listing.token}" name="status${listing.token}" onchange="displayHandler('${listing.token}')" data-original-value="${listing.statusId}">
							<option value="">--Select Status--</option>
							<c:forEach items="<%=ListingStatus.values()%>" var="status">
								<option value="${status.id}" <c:if test="${listing.statusId==status.id}">selected="selected"</c:if>>${status}</option>
							</c:forEach>
						</select>
						<span class="statusHandler" id="statusHandler${listing.token}"></span>
					</td>
					<td>${listing.expirationDateFormatted}</td>
					<td>${listing.application.name}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<jsp:include page="approvals.jsp" flush="true" />
<jsp:include page="payment.jsp" flush="true" />