<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.util.ProjectUtil"%>

<div class="container admin-container">
	<div class="row">
		<div class="col-xs-12 text-center-xs">
			<h1>List It!!</h1>
		</div>
		
		<div class="col-xs-12">
			<p>
				${application.name} is FREE! You can create your business listing and publish it for free with no obligation to extend. Listing extensions are competitively priced,
				the longer the extension the more money you save, the more exposure your business has and therefore the more opportunity it has to sell. 
				Should your business sell before the trial has expired then it won't cost you anything. See below for the extensions price list.
			</p>
			
			<p>
				Many of the "For Sale" websites generate revenue through pay per click advertising. The websites are cluttered, busy and littered with
				advertising that is not relevant to selling your business. BuyByeBiz does not present any unsolicited ads to its users and gives buyers a clear
				approach to finding the right business for sale.
			</p>
			
			<p>
				<c:set var='demoURI' value='<%=ProjectUtil.getProperty("system.demo.listingURI")%>' />
				<c:set var="demoURL" value="${pageContext.request.contextPath}/${demoURI}" />
				As a business owner you've worked hard to make your business successful and so your listing should reflect that.
				Have a look at our <a href="${demoURL}">Demo Listing</a> and compare against other providers.
			</p>
			
			<c:if test="${!pageAccess.loggedIn}">
				<p>
					<a href="javascript:modalRegister()">Click here to register!</a>
				</p>
			</c:if>
			
			<jsp:include page="extensions.jsp" flush="true" />
		</div>
	</div>
</div>