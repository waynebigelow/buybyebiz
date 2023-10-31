<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<button type="button" class="btn btn-primary" onclick="displayApplication()"><spring:message code="35.btn.1"/></button>
<div style="height:5px"></div>

<div class="table-responsive table-bordered">
	<table class="table table-hover table-condensed">
		<thead>
			<tr>
				<th><spring:message code="35.col.1"/></th>
				<th><spring:message code="35.col.2"/></th>
				<th><spring:message code="35.col.3"/></th>
				<th><spring:message code="35.col.4"/></th>
				<th><spring:message code="35.col.5"/></th>
				<th><spring:message code="35.col.6"/></th>
				<th><spring:message code="35.col.9"/></th>
				<th><spring:message code="35.col.10"/></th>
				<th><spring:message code="35.col.8"/></th>
				<th><spring:message code="35.col.7"/></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${applications.size() eq 0}">
				<tr>
					<td colspan="10"><spring:message code="35.msg.none.found"/></td>
				</tr>
			</c:if>
			
			<c:forEach items="${applications}" var="application">
				<tr>
					<td>
						<div class="toolbox">
							<span class="dropdown">
								<span class="glyphicon glyphicon-menu-hamburger editIcon" id="menu${application.token}" data-toggle="dropdown"></span>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dropDown">
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:editApplication('${application.token}')"><span class="glyphicon glyphicon-pencil"></span> <spring:message code="0.txt.edit"/></a></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" data-onConfirm="deleteApplication('${application.token}')" data-container="body" data-singleton="true" data-placement="top" data-toggle="confirmation" data-title="<spring:message code="35.msg.delete.warning"/>"><span class="glyphicon glyphicon-trash"></span> <span class="editIcon"><spring:message code="0.txt.delete"/></span></a></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:toggleApplication('${application.token}')">${application.toggleSpan}</a></li>
								</ul>
							</span>
						</div>
					</td>
					<td>${application.name}</td>
					<td><spring:message code="${application.applicationType.i18n}"/></td>
					<td>${application.key}</td>
					<td>${application.domain}</td>
					<td>${application.replyEmail}</td>
					<td>${application.supportEmail}</td>
					<td>${application.supportPhone}</td>
					<td>${application.defaultLocale}</td>
					<td>${application.enableSpan}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<jsp:include page="application.jsp" flush="true" />