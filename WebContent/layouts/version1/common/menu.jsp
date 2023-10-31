<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
.top-bar {
	position:absolute;
	width:100%;
	height:80px;
	opacity:.95;
	top:0px;
	z-index:1000;
	background-color:#e7eaed;
}
.menu {
	font-size:1.1em;
	margin:5px 0px 0px 50px;
	color:#000000;
	float:left;
}
.controls {
	margin:10px 20px 5px 5px;
	float:right;
}
.controls-text {
	font-size:1.2em;
}
.controls-text > li > a:link {
	color:#337ab7;
}
.controls-text > li > a:visited {
	color:#337ab7;
}
.controls-text > li > a:hover {
	color:#63b8ff;
	text-decoration:underline;
}
.controls-text > li > a:active {
	color:#337ab7;
}
</style>

<script type="text/javascript">
//<c:if test="${pageAccess.loggedIn}">
function doLogout() {
	$.ajax({
		url:'${pageContext.request.contextPath}/security/logout.do',
		type:'post',
		cache:'false',
		success:function(data, textStatus, jqXHR){
			window.location.href = "${pageContext.request.contextPath}/home.html";
		},
		error:function(qXHR, textStatus, errorThrown){
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}
//</c:if>
</script>

<nav class="nav navbar-default hidden-sm hidden-md hidden-lg">
	<div class="navbar-header">
		<button id="navBtn" type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".app-micro-nav" aria-expanded="false">
			<span class="sr-only">Toggle navigation</span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" target="_new" href="#">
			<img class="img-responsive" width="100%" src="${pageContext.request.contextPath}/buybyemedia/app/buybyebiz/images/bbb.png" />
		</a>
	</div>
		
	<div class="collapse navbar-collapse app-micro-nav">
		<ul class="nav navbar-nav controls-text">
			<c:if test="${pageAccess.systemAdmin}">
				<li><a href="${pageContext.request.contextPath}/appAdmin/users.html?alias=u"><spring:message code="0.txt.menu.1" /></a></li>
			</c:if>
			<c:if test="${!pageAccess.systemAdmin && pageAccess.accountOwner}">
				<li><a href="${pageContext.request.contextPath}/userAdmin/admin.html?alias=li"><spring:message code="0.txt.menu.2" /></a></li>
			</c:if>
			
			<c:if test="${pageAccess.loggedIn}">
				<li><a href="javascript:doLogout()"><spring:message code="0.txt.logout"/></a></li>
			</c:if>
			<c:if test="${!pageAccess.loggedIn}">
				<li style="padding-top:10px;padding-bottom:10px;line-height:20px;padding-left:15px">
					<span><a id="loginLink" href="#"><spring:message code="0.txt.login"/></a>&nbsp;<spring:message code="0.txt.or"/>&nbsp;<a href="javascript:modalRegister()"><spring:message code="0.txt.register" /></a></span>
				</li>
			</c:if>
		</ul>
	</div>
		
	<div class="collapse navbar-collapse app-micro-nav">
		<ul class="nav navbar-nav controls-text">
			<li><a href="${pageContext.request.contextPath}/home.html"><spring:message code="0.txt.menu.3"/></a></li>
			<li><a href="${pageContext.request.contextPath}/search.html"><spring:message code="0.txt.menu.4"/></a></li>
			<li><a href="${pageContext.request.contextPath}/listIt.html"><spring:message code="0.txt.menu.5"/></a></li>
			<li><a href="${pageContext.request.contextPath}/faq.html"><spring:message code="0.txt.menu.6"/></a></li>
			<li><a href="${pageContext.request.contextPath}/contact.html"><spring:message code="0.txt.menu.7"/></a></li>
		</ul>
	</div>
	
	<div class="collapse navbar-collapse app-micro-nav">
		<ul class="nav navbar-nav controls-text">
			<li><a href="javascript:displayIssue()"><spring:message code="0.txt.menu.10"/></a></li>
			<li><a href="${pageContext.request.contextPath}/tou.html" class="menulink"><spring:message code="0.txt.menu.8"/></a></li>
			<li><a href="${pageContext.request.contextPath}/pp.html" class="menulink"><spring:message code="0.txt.menu.9"/></a></li>
			
		</ul>
	</div>
</nav>

<div class="top-bar hidden-xs">
	<div class="menu controls-text">
		<table>
			<tr>
				<td>
					<img height="100" width="100" class="img-responsive" src="${pageContext.request.contextPath}/buybyemedia/app/buybyebiz/images/bbb.png" />
				</td>
				<td width="50">&nbsp;</td>
				<td style="padding:5px 20px 5px 5px">
					<a href="${pageContext.request.contextPath}/home.html"><spring:message code="0.txt.menu.3"/></a>
				</td>
				<td style="padding:5px 20px 5px 5px">
					<a href="${pageContext.request.contextPath}/search.html"><spring:message code="0.txt.menu.4"/></a>
				</td>
				<td style="padding:5px 20px 5px 5px">
					<a href="${pageContext.request.contextPath}/listIt.html"><spring:message code="0.txt.menu.5"/></a>
				</td>
				<td style="padding:5px 20px 5px 5px">
					<a href="${pageContext.request.contextPath}/faq.html"><spring:message code="0.txt.menu.6"/></a>
				</td>
				<td style="padding:5px 20px 5px 5px">
					<a href="${pageContext.request.contextPath}/contact.html"><spring:message code="0.txt.menu.7"/></a>
				</td>
			</tr>
		</table>
	</div>

	<div class="controls controls-text">
		<span>
			<c:if test="${pageAccess.loggedIn}">
				Welcome back ${pageAccess.user.firstName}!<br/>
				<c:if test="${pageAccess.systemAdmin}">
					<a href="${pageContext.request.contextPath}/appAdmin/users.html?alias=u"><spring:message code="0.txt.menu.1"/></a>
				</c:if>
				<c:if test="${!pageAccess.systemAdmin && pageAccess.accountOwner}">
					<a href="${pageContext.request.contextPath}/userAdmin/admin.html?alias=li"><spring:message code="0.txt.menu.2"/></a>
				</c:if>
				&nbsp;|&nbsp;
				<a href="javascript:doLogout()"><spring:message code="0.txt.logout" /></a>
			</c:if>
			
			<c:if test="${!pageAccess.loggedIn}">
				<a id="loginLink" href="javascript:doLogin()"><spring:message code="0.txt.login" /></a> <spring:message code="0.txt.or" /> <a href="javascript:modalRegister()"><spring:message code="0.txt.register" /></a>
			</c:if>
		</span>
	</div>
</div>