<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld"%>

<button type="button" class="btn btn-primary" onclick="addPackage()"><spring:message code="105.btn.1"/></button>
<div style="height:5px"></div>

<div class="table-responsive table-bordered">
	<table class="table table-hover table-condensed">
		<thead>
			<tr>
				<th width="15"><spring:message code="105.col.1"/></th>
				<th><spring:message code="105.col.2"/></th>
				<th><spring:message code="35.col.3"/></th>
				<th><spring:message code="105.col.3"/></th>
				<th><spring:message code="105.col.4"/></th>
				<th><spring:message code="105.col.5"/></th>
				<th><spring:message code="105.col.7"/></th>
				<th>Discount</th>
				<th>Link</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty packages}">
				<tr>
					<td colspan="12"><spring:message code="105.msg.none.found"/></td>
				</tr>
			</c:if>
			
			<c:forEach items="${packages}" var="appPackage">
				<tr>
					<td>
						<div class="toolbox" id="deleteContainer${appPackage.token}">
							<span class="dropdown">
								<span class="glyphicon glyphicon-menu-hamburger editIcon" id="menu${appPackage.token}" data-toggle="dropdown"></span>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dropDown">
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:editPackage('${appPackage.token}')"><span class="glyphicon glyphicon-edit"></span> <spring:message code="0.txt.edit"/></a></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" data-onConfirm="deletePackage('${appPackage.token}')" data-container="body" data-singleton="true" data-placement="top" data-toggle="confirmation" data-title="<spring:message code="105.msg.delete.warning"/>"><span class="glyphicon glyphicon-trash"></span> <spring:message code="0.txt.delete"/></a></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:togglePackage('${appPackage.token}')"><common:toggle enabled="${appPackage.enabled}" includeLabel="true" /></a></li>
								</ul>
							</span>
						</div>
					</td>
					<td>${appPackage.name}</td>
					<td>${appPackage.applicationType}</td>
					<td>${appPackage.applicationPackageType}</td>
					<td>${appPackage.timePeriod}</td>
					<td>${appPackage.duration}</td>
					<td><common:toggle enabled="${appPackage.enabled}" /></td>
					<td>${appPackage.agentDiscount}</td>
					<td>${appPackage.linkId}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<jsp:include page="package.jsp" flush="true" />