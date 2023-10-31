<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld"%>
<%@ taglib prefix="paging" uri="/WEB-INF/paging.tld"%>
<%@ page import="ca.app.model.configuration.AdminTabType"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>
<%@ page import="ca.app.model.listing.ListingStatus"%>

<style>
.no-margins {
	margin: 0px 0px 5px 0px;
}
</style>

<script type="text/javascript">
var listingsTemplate = null;
$(document).ready(function(e) {
	listingsTemplate = Handlebars.compile($('#listing-template').html());
});

function loadUserListings(v){
	$.getJSON('${pageContext.request.contextPath}/appAdmin/loadUserListings.do', {
		<%=TokenFieldType.USER.getAlias()%>:v
	})
	.done(function(data) {
		drawListings(data);
	});
}

function drawListings(listings) {
	var listingsHtml = "";
	
	$.each(listings, function(i, listing) {
		var metaDataLink = "";
		if (listing.pendingCount > 0) {
			metaDataLink = "&nbsp;<a href=\"javascript:loadApprovals('"+listing.token+"')\"><span class='glyphicon glyphicon-inbox'></a>";
		}
		var context = {a:listing.application.name,c:listing.title,l:listing.token,u:listing.user.token,e:listing.icon,f:listing.oppositeIcon,g:listing.toggleText,b:listing.statusId,p:listing.primaryToken,r:metaDataLink,s:listing.expirationDateFormatted};
		listingsHtml = listingsHtml + listingsTemplate(context);
	});
	
	if (listingsHtml != ""){
		$('#listingsTable').html(listingsHtml);
		$.each(listings, function(i, listing) {
			$('#status'+listing.token).val(listing.statusId);
		});
		
		$('[data-toggle="confirmation"]').confirmation({popout:true});
		$('#listingsDiv').show();
	} else {
		$('#listingsDiv').hide();
	}
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

function appendSavedIcon() {
	return "<span><span class=\"glyphicon glyphicon-saved\"></span></span>";
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
			$('#status'+v).attr('data-original-value', $('#status'+v).val());
			$('#statusHandler'+v).html('');
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function preview(v) {
	window.location.href="${pageContext.request.contextPath}/listingAdmin/preview.html?<%=TokenFieldType.LISTING.getAlias()%>="+v;
}
</script>

<script id="listing-template" type="text/x-handlebars-template">
<tr id="listing{{l}}">
	<td>
		<div class="toolbox">
			<span class="dropdown">
				<span class="glyphicon glyphicon-menu-hamburger editIcon" id="menu{{l}}" data-toggle="dropdown"></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="dropDown">
					<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:preview('{{l}}')"><span class="glyphicon glyphicon-edit"></span> <spring:message code="0.txt.edit"/></a></li>
					<li role="presentation"><a role="menuitem" tabIndex="-1" data-onConfirm="deleteListing('{{l}}')" data-container="body" data-singleton="true" data-placement="top" data-toggle="confirmation" data-title="<spring:message code="20.msg.delete.warning"/>"><span class="glyphicon glyphicon-trash"></span> <spring:message code="0.txt.delete"/></a></li>
					<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:toggleListing('{{l}}','{{u}}')"><span class="glyphicon glyphicon-thumbs-{{e}}"></span> {{g}}</a></li>
					<li role="presentation" class="divider"></li>
					<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:activityLog('{{p}}','l')">Activity Log</a></li>
					<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:notificationLog('{{p}}','l')">Notification Log</a></li>
				</ul>
			</span>
		<span>{{{r}}}
		<span class="glyphicon glyphicon-thumbs-{{f}}"></span></span>
		</div>
	</td>
	<td><a href="javascript:preview('{{l}}')">{{c}}</a></td>
	<td>
		<select id="status{{l}}" name="status{{l}}" onchange="displayHandler('{{l}}')" data-original-value="{{b}}">
			<option value="">--Select Status--</option>
			<c:forEach items="<%=ListingStatus.values()%>" var="status">
				<option value="${status.id}">${status}</option>
			</c:forEach>
		</select>
		<span class="statusHandler" id="statusHandler{{l}}"></span>
	</td>
	<td>{{s}}</td>
	<td>{{a}}</td>
</tr>
</script>

<button type="button" class="btn btn-primary" onclick="displayUser()">Create User</button>
<div style="height:5px"></div>

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
				<th width="15"><spring:message code="30.col.1"/></th>
				<th width="250"><spring:message code="30.col.2"/></th>
				<th width="200"><spring:message code="30.col.3"/></th>
				<th width="150"><spring:message code="30.col.4"/></th>
				<th width="50">Agent</th>
				<th width="200">Company</th>
				<th><spring:message code="30.col.5"/></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${users.size() == 0}">
				<tr>
					<td colspan="7"><spring:message code="30.msg.none.found"/></td>
				</tr>
			</c:if>
			
			<c:forEach items="${users}" var="user">
				<tr>
					<td>
						<div class="toolbox" id="deleteContainer${user.token}">
							<span class="dropdown">
								<span class="glyphicon glyphicon-menu-hamburger editIcon" id="menu'${user.token}'" data-toggle="dropdown"></span>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dropDown">
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:editUser('${user.token}')"><span class="glyphicon glyphicon-edit"></span> <spring:message code="0.txt.edit"/></a></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" data-onConfirm="deleteUser('${user.token}')" data-container="body" data-singleton="true" data-placement="top" data-toggle="confirmation" data-title="<spring:message code="30.msg.delete.warning"/>"><span class="glyphicon glyphicon-trash"></span> <spring:message code="0.txt.delete"/></a></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:toggleUser('${user.token}')">${user.toggleSpan}</a></li>
									<li role="presentation" class="divider"></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:activityLog('${user.primaryToken}','u')">Activity Log</a></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:notificationLog('${user.primaryToken}','u')">Notification Log</a></li>
								</ul>
							</span>
						</div>
					</td>
					<td><a href="javascript:loadUserListings('${user.token}')">${user.email}</a></td>
					<td>${user.tableDisplayName}</td>
					<td>${user.telephone}</td>
					<td><common:toggle enabled="${user.agent}" /></td>
					<td>${user.companyName}</td>
					<td>${user.enableSpan}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div style="height:5px"></div>

<div id="listingsDiv" class="col-xs-offset-1 col-xs-11" style="display:none;">
	<div class="panel-group" id="userAccordion" role="tablist" aria-multiselectable="true">
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="listingsHeading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#userAccordion" href="#listingsPanel" aria-expanded="true" aria-controls="listingsPanel">
						<spring:message code="20.txt.listings"/>
					</a>
				</h4>
			</div>
			
			<div id="listingsPanel" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="listingsHeading">
				<div class="panel-body">
					<div class="table-responsive table-bordered">
						<table class="table table-hover table-condensed">
							<thead>
								<tr>
									<th width="70"><spring:message code="20.col.1"/></th>
									<th width="250"><spring:message code="20.col.2"/></th>
									<th width="200"><spring:message code="20.col.6"/></th>
									<th width="200"><spring:message code="20.col.4"/></th>
									<th><spring:message code="20.col.3"/></th>
								</tr>
							</thead>
							<tbody id="listingsTable">
								<tr>
									<td colspan="7"><spring:message code="20.msg.none.found"/></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="userDetails.jsp" flush="true" />
<jsp:include page="notificationLog.jsp" flush="true" />
<jsp:include page="activityLog.jsp" flush="true" />
<jsp:include page="approvals.jsp" flush="true" />