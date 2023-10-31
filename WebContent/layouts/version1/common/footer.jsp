<%@ page import="ca.app.util.ProjectUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
footer {
	position:absolute;
	left:0;
	bottom:0;
	height:160px;
	width:100%;
	background-color:#e7eaed;
}
.social {
	font-size:24px;
	color:#ffffff;
}
</style>

<footer>
	<div style="margin-top:30px">
		<div class="col-xs-12 text-center">
			<span id="siteseal"><script async type="text/javascript" src="https://seal.godaddy.com/getSeal?sealID=<%=ProjectUtil.getProperty("godaddy.seal.id")%>"></script></span>
			<script type="text/javascript" src="https://cdn.ywxi.net/js/1.js" async></script>
		</div>
		
		<div class="col-xs-12 text-center">
				${application.supportEmail}<br/>
				${application.supportPhone}
		</div>
		
		<div class="col-xs-12 text-center">
			<a href="${pageContext.request.contextPath}/tou.html" class="menulink">Terms of Use</a>
			&nbsp;|&nbsp;
			<a href="${pageContext.request.contextPath}/pp.html" class="menulink">Privacy Policy</a>
		</div>
		
		<div class="col-xs-12 text-center">
			<a href="javascript:displayIssue()" class="menulink">Support Issue?</a>
		</div>
	</div>
</footer>

<jsp:include page="issue.jsp" flush="true" />