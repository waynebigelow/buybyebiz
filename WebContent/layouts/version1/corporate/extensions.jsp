<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ca.app.util.ProjectUtil"%>

<div class="row">
	<div class="col-xs-hidden col-sm-2"></div>
	<div class="col-xs-12 col-sm-8 text-center" style="border:1px solid #c1c1c1">
		<c:set var="duration" value='<%=ProjectUtil.getProperty("business.listing.promo.duration") %>' />
		<h1>${duration} Months Free Promo!!</h1>
		<p>
			<c:set var="count" value='<%=ProjectUtil.getProperty("business.listing.promo.count") %>' />
			To the first <font color="blue"><b>${count}</b></font> published listings.<br/>
			There are <font color="red"><b>${promoRemaing}</b></font> remaining.
		</p>
	</div>
	<div class="col-xs-hidden col-sm-2"></div>
</div>

<div style="height:10px">&nbsp;</div>

<div class="row">
	<div class="col-xs-12 text-center">
	<strong>Note:</strong> The price of the extension is determined by the location of the business listing that is for sale.
	</div>
</div>

<div class="row">
	<div class="col-xs-hidden col-sm-2"></div>
		<c:set var="border" value="" />
		<c:forEach items="${application.packagesDTO}" var="appPackage">
			<c:if test="${appPackage.applicationPackageType ne 'TRIAL'}">
				<div class="col-xs-6 col-sm-2 text-center" style="border:1px solid #c1c1c1;${border}">
					${appPackage.name}<br/>
					<hr/>
					<c:forEach items="${appPackage.packagePrices}" var="packagePrice">
						<c:if test="${packagePrice.currencyType.shortName eq 'CAD'}">
							${packagePrice.priceFormatted} ${packagePrice.currencyType.shortName}<br/>
							+ HST (${packagePrice.taxFormatted})
						</c:if>
						
						<c:if test="${packagePrice.currencyType.shortName eq 'USD'}">
							<br/>or<br/>
							${packagePrice.priceFormatted} ${packagePrice.currencyType.shortName}
						</c:if>
					</c:forEach>
				</div>
				<c:set var="border" value="border-left:0px;" />
			</c:if>
		</c:forEach>
	<div class="col-xs-hidden col-sm-2"></div>
</div>