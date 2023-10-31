<%@ page import="ca.app.util.ProjectUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style type="text/css">
.title-bar1 {
	text-align:center;
	font-weight:bold;
	font-size:4em;
	color:#ffffff;
	position:absolute;
	width:800px;
	left:50%;
	height:70px;
	top:140px;
	margin-left:-400px;
	z-index:1000;
}
.sold{
	position:absolute;
	z-index:1;
	top:50px;
	left:75px;
}
</style>

<script type="text/javascript">
$(document).ready(function(e) {
	buildSelect("${pageContext.request.contextPath}/common/listedCategories.json", "category", "");
	
	$('#location').remoteChained({
		parents:'#category',
		url:'${pageContext.request.contextPath}/common/listedLocations.json?c=1'
	});
});
</script>

<img width="100%" class="img-responsive" src="${pageContext.request.contextPath}/buybyemedia/app/buybyebiz/images/theme.png" />

<div class="style-bar panel panel-default">
	<div class="panel-body" style="padding:0px">
		<div class="search-bar panel panel-default">
			<div class="panel-body">
				<form role="form" id="searchForm" name="searchForm" action="${pageContext.request.contextPath}/search.html">
					<div class="title-bar">
						<spring:message code="110.msg.welcome" />
					</div>

					<div class="col-xs-5 col-sm-5" style="padding:2px;">
						<select id="category" name="category" class="form-control"></select>
					</div>
					
					<div class="col-xs-5 col-sm-5" style="padding:2px;">
						<select id="location" name="location" class="form-control" disabled="disabled">
							<option value="">--<spring:message code="25.lbl.country" />--</option>
						</select>
					</div>
					
					<div class="col-xs-1 col-sm-1 col-md-offset-1 col-md-1" style="padding:2px;">
						<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span></button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<div class="container">
	<div class="row" style="margin-top:20px">
		<div class="col-xs-12 col-sm-6">
			<div class="panel panel-default">
				<div class="panel-heading" style="background:#99ffff">
					<h3 id="listLink"><spring:message code="110.header.listing" /></h3>
				</div>
				<div class="panel-body" style="font-size:16px">
					<p>
						<spring:message code="110.msg.listing.1" arguments="${application.name}" htmlEscape="true" />
						<a href="${pageContext.request.contextPath}/listIt.html"><spring:message code="110.link.read.more" /></a>
					</p>
					
					<c:if test="${!pageAccess.loggedIn}">
						<button type="button" class="btn btn-primary" onclick="modalRegister()"><spring:message code="110.link.listing" /></button>
					</c:if>
				</div>
			</div>
		</div>
		<div class="hidden-xs col-sm-6">
			<div style="text-align:center;">
				<img class="sold" src="${pageContext.request.contextPath}/layouts/version1/images/sold-lg.png" />
				<img width="100%" class="img-responsive" src="${pageContext.request.contextPath}/buybyemedia/app/buybyebiz/images/seller.jpg" />
			</div>
		</div>
	</div>
	
	<div class="row" style="margin-top:20px">
		<div class="col-xs-12 col-sm-6 hidden-xs" style="margin-bottom:20px">
			<div style="text-align:center;">
				<img width="100%" class="img-responsive" src="${pageContext.request.contextPath}/buybyemedia/app/buybyebiz/images/buyer.jpg" />
			</div>
		</div>
		
		<div class="col-xs-12 col-sm-6">
			<div class="panel panel-default">
				<div class="panel-heading" style="background:#cce0ff">
					<h3 id="searchLink"><spring:message code="110.header.search" /></h3>
				</div>
				<div class="panel-body" style="font-size:16px">
					<p><spring:message code="110.msg.search" arguments="${application.name}" /></p>
					<br/>
					<a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/search.html"><spring:message code="110.link.search" /></a>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row" style="margin-top:20px">

		
		<div class="col-xs-12 col-sm-6">
			<div class="panel panel-default success">
				<div class="panel-heading" style="background:#ffcccc">
					<h3 id="faqLink"><spring:message code="110.header.faq" /></h3>
				</div>
				<div class="panel-body" style="font-size:16px">
					<p><spring:message code="110.msg.faq" arguments="${application.name}" /></p>
					<br/>
					<a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/faq.html"><spring:message code="110.link.faq" /></a>
				</div>
			</div>
		</div>
		
		<div class="hidden-xs col-sm-6">
			<div style="text-align:center;">
				<img width="100%" class="img-responsive" src="${pageContext.request.contextPath}/buybyemedia/app/buybyebiz/images/faq.png" />
			</div>
		</div>
	</div>
</div>

<c:if test="${!pageAccess.loggedIn}">
	<c:if test="${isPromoActive}">
		<jsp:include page="../corporate/promo.jsp" flush="true" />
	</c:if>
</c:if>