<!DOCTYPE html>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ca.app.model.configuration.UserAdminTabType"%>

<html lang="en">
<head>
<title>${metaPageSettings.pageTitle}</title>
<link rel="${application.name} icon" type="image/x-icon" href="${pageContext.request.contextPath}/layouts/version1/images/buybyebiz.ico">

<meta charset="utf-8">
<meta name="pragma" content="no-cache">
<meta name="robots" content="noarchive,noindex,nofollow">
<meta name="author" content="${metaPageSettings.metaAuthor}">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="content-type" content="text/html;charset=UTF-8">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING:Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/3rdparty/bootstrap/core/css/bootstrap.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/css/extensions.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/css/version1.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/3rdparty/bootstrap/validator/css/bootstrapValidator.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/3rdparty/bootstrap/tabdrop/css/tabdrop.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/collagePlus/css/collagePlus-transitions.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/core/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/core/js/jquery.form.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/bootstrap/core/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/bootstrap/validator/js/bootstrapValidator.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/js/common.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/bootstrap/tabdrop/js/bootstrap-tabdrop.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/bootstrap/confirmation/js/bootstrap.confirmation.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/handlebars/js/handlebars-v2.0.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/chainedRemote/js/jquery.chained.remote.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/collagePlus/js/jquery.collagePlus.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/other/js/jquery.removeWhitespace.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/other/js/jquery.collageCaption.min.js"></script>

<style type="text/css">
body{
	font-family:"Futura Bold",sans-serif;
	margin:0px 0px 200px 0px;
	height:100%;
}
html {
	position:relative;
	min-height:100%;
}
h1, h2, h3, h4, h5, h6 {
	font-family:"Futura Bold",sans-serif;
}
.text-muted a:link, a:active, a:hover, a:visited {
	outline:none;
}
.panel-tabbed {
	border-top: 0px;
	border-top-left-radius: 0px;
	border-top-right-radius: 0px;
}
.toolbox {
	position: absolute;
}
.editIcon {
	cursor: pointer;
	cursor: hand;
	font-size: 1em;
}
</style>

<script type="text/javascript">
$(document).ready(function(e) {
	$('.nav-tabs').tabdrop({
		text:'<span class="glyphicon glyphicon-align-justify"></span>'
	});
	
	$('[data-toggle="confirmation"]').confirmation({popout:true});
});
</script>
</head>

<body>
	<tiles:insertDefinition name="version1:menu" />
	<div class="container admin-container">
		<div class="row">
			<div class="col-xs-12">
				<c:if test="${empty site}">
					<h1><spring:message code="15.title"/></h1>
					<hr/>
				</c:if>
				<c:if test="${not empty site}">
					<h1>Draft</h1>
					<hr/>
					<c:if test="${pageAccess.systemAdmin}">
						<a href="${pageContext.request.contextPath}/appAdmin/users.html?alias=u"><span class="glyphicon glyphicon-backward"></span> Return to Admin</a>
					</c:if>
					<c:if test="${!pageAccess.systemAdmin && pageAccess.accountOwner}">
						<a href="${pageContext.request.contextPath}/userAdmin/admin.html?alias=li"><span class="glyphicon glyphicon-backward"></span> Return to My Account</a>
					</c:if>
				</c:if>
				
				<ul class="nav nav-tabs" role="tablist">
					<c:forEach items="${tabs}" var="tabType">
						<c:choose>
						<c:when test="${tabType.alias eq tab.alias}">
							<li role="presentation" class="active">
								<a href="#${tab.tabName}" role="tab">
									<spring:message code="${tab.i18n}" />
									
									<c:if test="${tabType.alias eq 'in' }">
										<c:set var="bgColor" value="blue" />
										<c:set var="msg" value="Msgs" />
										<c:if test="${unread gt 0}">
											<c:set var="bgColor" value="red" />
											<c:if test="${unread eq 1}">
												<c:set var="msg" value="Msg" />
											</c:if>
										</c:if>
										&nbsp;<span id="badgeTotal" class="badge" style="background-color:${bgColor}">${unread}&nbsp;${msg}</span>
									</c:if>
								</a>
							</li>
						</c:when>
						<c:otherwise>
							<li role="presentation">
								<a href="${pageContext.request.contextPath}${tabType.action}" role="tab">
									<spring:message code="${tabType.i18n}" />
									
									<c:if test="${tabType.alias eq 'in' }">
										<c:set var="bgColor" value="blue" />
										<c:set var="msg" value="Msgs" />
										<c:if test="${unread gt 0}">
											<c:set var="bgColor" value="red" />
											<c:if test="${unread eq 1}">
												<c:set var="msg" value="Msg" />
											</c:if>
										</c:if>
										&nbsp;<span class="badge" style="background-color:${bgColor}">${unread}&nbsp;${msg}</span>
									</c:if>
								</a>
							</li>
						</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
	
				<div class="panel panel-default panel-tabbed contentPanel">
					<div class="panel-body panel-white">
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active" id="${tab.tabName}">
								<div style="height:5px"></div>
								<tiles:insertAttribute name="admin.content" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<tiles:insertDefinition name="version1:footer" />
	
	<c:if test="${!pageAccess.loggedIn}">
		<jsp:include page="security/login.jsp" flush="true" />
	</c:if>
	<jsp:include page="common/alert.jsp" flush="true" />
</body>
</html>