<!DOCTYPE html>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="str" uri="http://jakarta.apache.org/taglibs/string-1.1" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="en">
<head>
<jsp:include page="common/head.jsp" flush="true" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/3rdparty/bootstrap/core/css/bootstrap.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/css/extensions.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/css/version1.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/3rdparty/bootstrap/validator/css/bootstrapValidator.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/core/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/core/js/jquery.form.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/bootstrap/core/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/bootstrap/validator/js/bootstrapValidator.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/js/common.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/chainedRemote/js/jquery.chained.remote.min.js"></script>

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
li.a:hover {
	text-decoration:underline;
}
</style>
</head>

<body>
	<tiles:insertDefinition name="version1:menu" />
	<tiles:insertAttribute name="content.page" />
	<tiles:insertDefinition name="version1:footer" />
	
	<c:if test="${!pageAccess.loggedIn}">
		<jsp:include page="user/registration.jsp" flush="true" />
		<jsp:include page="user/confirmation.jsp" flush="true" />
		<jsp:include page="security/login.jsp" flush="true" />
		<jsp:include page="user/registrationSuccess.jsp" flush="true" />
	</c:if>
	<jsp:include page="common/alert.jsp" flush="true" />
</body>
</html>