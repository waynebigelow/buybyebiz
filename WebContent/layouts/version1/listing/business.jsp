<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld"%>
<%@ page import="ca.app.model.photo.PhotoType"%>
<%@ page import="ca.app.model.listing.ListingStatus"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/collagePlus/css/collagePlus-transitions.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/layouts/version1/3rdparty/magnificPopup/css/magnific-popup.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/collagePlus/js/jquery.collagePlus.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/other/js/jquery.removeWhitespace.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/other/js/jquery.collageCaption.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/magnificPopup/js/magnific.popup.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/touchswipe/js/touchswipe.min.js"></script>

<style>
.collage {
	padding:5px;
}
.collage img {
	margin:0;
	padding:0;
	display:inline-block;
	vertical-align:bottom;
	opacity:1.5;
	border:4px solid #fffff;
}
.image-wrapper {
	opacity: 0;
	-moz-box-shadow:0px 2px 4px rgba(0, 0, 0, 0.1);
	-webkit-box-shadow:0px 2px 4px rgba(0, 0, 0, 0.1);
	box-shadow:0px 2px 4px rgba(0, 0, 0, 0.1);
	-moz-border-radius:3px;
	-webkit-border-radius:3px;
	border-radius:3px;
}
.profile-wrapper {
	border:1px solid #c1c1c1;
	padding:2px;
	display:block;
	margin:0 auto;
}
.sold{
	position:absolute;
	z-index:1;
	top:75px;
	left:50px;
}
</style>

<script>
$(window).load(function() {
	$(document).ready(function() {
		$('#accordion2').on('hidden.bs.collapse', toggleChevron);
		$('#accordion2').on('shown.bs.collapse', toggleChevron);
		
		collage();
		
		$('.collage').collageCaption();
		
		$('.popup-gallery').magnificPopup({
			delegate:'a',
			type:'image',
			tLoading:'Loading',
			mainClass:'mfp-img-mobile',
			gallery:{
				enabled:true,
				navigateByImgClick:true,
				preload:[0,1]
			},
			image:{
				titleSrc:function(item) {
					return item.el.attr('title');
				}
			},
			zoom:{
				enabled:true,
				duration:300,
				easing:'ease-in-out',
				opener:function(openerElement) {
					return openerElement.is('img') ? openerElement : openerElement.find('img');
				}
			}
		});
		
		$('.popup-gallery').on('mfpImageLoadComplete', function(e) {
			$(".mfp-img").swipe({
				swipe:function(event, direction, distance, duration, fingerCount, fingerData) {
					if (direction == 'right'){
						$('.popup-gallery').magnificPopup('next');
					} else if (direction == 'left'){
						$('.popup-gallery').magnificPopup('prev');
					}
				},
				threshold:0
			});
		});
		
		$('.collageDiv').show();
	});
});

var resizeTimer = null;
$(window).bind('resize', function() {
	$('.collage .imgWrapper').css("opacity", 0);
	if (resizeTimer) {
		clearTimeout(resizeTimer);
	}
	resizeTimer = setTimeout(collage, 200);
});

function collage() {
	$('.collage').removeWhitespace().collagePlus({
		'fadeSpeed':3000,
		'targetHeight':85,
		'effect':'effect-4',
		'direction':'vertical',
		'allowPartialLastRow':true
	});
}
</script>

<div id="fb-root"></div>
<script>(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) return;
	js = d.createElement(s); js.id = id;
	js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.8";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<div class="container admin-container">
	<div class="row">
		<div class="col-xs-12">
			<c:if test="${site.listing.sold}">
				<img class="sold" src="${pageContext.request.contextPath}/layouts/version1/images/sold-lg.png" />
			</c:if>
			
			<h1 class="text-center">
				${site.listing.title}
			</h1>
			
			<c:if test="${site.listing.price gt 0}">
				<h2>${site.getCurrencyFormatted(site.listing.price)}
					<c:if test="${!site.listing.sold}">
						<div style="float:right" class="fb-like" data-href="${application.secureApplicationURL}/${site.listing.listingURIFormatted}" data-layout="button_count" data-action="like" data-size="small" data-show-faces="true" data-share="true"></div>
					</c:if>
				</h2>
			</c:if>
		</div>
		
		<div class="col-xs-12 col-sm-9">
			<c:if test="${not empty site.themePhoto || not empty site.photoList}">
				<div class="panel panel-default">
					<div  style="padding:0px">
						<c:if test="${not empty site.themePhoto}">
							<common:photo editable="false" site="${site}" photo="${site.themePhoto}" baseURL="${pageContext.request.contextPath}" clazz="img-responsive"/>
						</c:if>
						
						<c:if test="${not empty site.photoList}">
							<section class="collageDiv collage effect-parent" style="display:none">
								<c:forEach items="${site.photoList}" var="photo">
									<div class="image_wrapper popup-gallery" data-caption="${photo.caption}">
										<common:photo editable="false" site="${site}" photo="${photo}" baseURL="${pageContext.request.contextPath}" gallery="true" />
									</div>
								</c:forEach>
							</section>
						</c:if>
					</div>
				</div>
			</c:if>
			
			<div class="panel panel-default">
				<div class="panel-body">
					<p>
						${site.listing.description}
					</p>
				</div>
			</div>
			
			<form class="form-horizontal">
				<div class="panel-group" id="accordion2" role="tablist" aria-multiselectable="true">
					<c:set var="indicator" value="up" />
					<c:set var="collapse" value="in" />
					<c:if test="${site.listing.businessDetails.hasFinancialDetails()}">
						<div class="panel panel-default">
							<div class="panel-heading" role="tab" id="financials2Heading" style="background-color:#96a5b3;color:#ffffff">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#financials2Panel" aria-expanded="true" aria-controls="financials2Panel">
										<i class="indicator glyphicon glyphicon-chevron-${indicator}"></i>
										Financial Details
									</a>
								</h4>
							</div>
							
							<div id="financials2Panel" class="panel-collapse collapse ${collapse}" role="tabpanel" aria-labelledby="financials2Heading">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="salesRevMin"><spring:message code="20.lbl.min.sales.rev.label"/>:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.getCurrencyFormatted(site.listing.businessDetails.salesRevenueMin)}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="salesRevMax"><spring:message code="20.lbl.max.sales.rev.label"/>:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.getCurrencyFormatted(site.listing.businessDetails.salesRevenueMax)}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="cashFlow"><spring:message code="20.lbl.cash.flow.label"/>:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.getCurrencyFormatted(site.listing.businessDetails.cashFlow)}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="inventoryTotal"><spring:message code="20.lbl.inventory.label"/>:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.getCurrencyFormatted(site.listing.businessDetails.inventoryTotal)}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="chattelTotal"><spring:message code="20.lbl.chattel.label"/>:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.getCurrencyFormatted(site.listing.businessDetails.chattelTotal)}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="financialOther">Other Financial Details:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.financialOther}
										</div>
									</div>
								</div>
							</div>
						</div>
						<c:set var="indicator" value="down" />
						<c:set var="collapse" value="" />
					</c:if>
					
					<c:if test="${site.listing.businessDetails.hasOperationalDetails()}">
						<div class="panel panel-default">
							<div class="panel-heading" role="tab" id="property2Heading" style="background-color:#96a5b3;color:#ffffff">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#property2Panel" aria-expanded="true" aria-controls="property2Panel">
										<i class="indicator glyphicon glyphicon-chevron-${indicator}"></i>
										Operational Details
									</a>
								</h4>
							</div>
							
							<div id="property2Panel" class="panel-collapse collapse ${collapse}" role="tabpanel" aria-labelledby="property2Heading">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="hoursOfOperation">Hours of Operation:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.hoursOfOperation}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="numberOfEmployees">Number of Employees:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.numberOfEmployees}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="yearEstablished">Year Established:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.yearEstablished}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="operationOther">Other Operation Details:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.operationOther}
										</div>
									</div>
								</div>
							</div>
						</div>
						<c:set var="indicator" value="down" />
						<c:set var="collapse" value="" />
					</c:if>
					
					<c:if test="${site.listing.businessDetails.hasPropertyDetails()}">
						<div class="panel panel-default">
							<div class="panel-heading" role="tab" id="operations2Heading" style="background-color:#96a5b3;color:#ffffff">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#operations2Panel" aria-expanded="true" aria-controls="operations2Panel">
										<i class="indicator glyphicon glyphicon-chevron-${indicator}"></i>
										Property Details
									</a>
								</h4>
							</div>
							
							<div id="operations2Panel" class="panel-collapse collapse ${collapse}" role="tabpanel" aria-labelledby="operations2Heading">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="propertyType">Property Type:</label></div>
										<div class="col-xs-12 col-sm-8">
											<c:if test="${site.listing.businessDetails.propertyTypeId gt 0}">
												<spring:message code="${site.listing.businessDetails.propertyType.i18n}"/>
											</c:if>
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="propertyTax">Property Tax:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.propertyTax}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="frontage">Frontage & Lot Depth:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.frontage}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="squareFootage">Square Footage:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.squareFootage}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="acreage">Acreage:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.acreage}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="ownersResidence">Owners Residence:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.ownersResidence}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="propertyOther">Other Property Details:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.propertyOther}
										</div>
									</div>
								</div>
							</div>
						</div>
						<c:set var="indicator" value="down" />
						<c:set var="collapse" value="" />
					</c:if>
					
					<c:if test="${site.listing.businessDetails.hasOtherDetails()}">
						<div class="panel panel-default">
							<div class="panel-heading" role="tab" id="other2Heading" style="background-color:#96a5b3;color:#ffffff">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#other2Panel" aria-expanded="true" aria-controls="other2Panel">
										<i class="indicator glyphicon glyphicon-chevron-${indicator}"></i>
										Other Details
									</a>
								</h4>
							</div>
							
							<div id="other2Panel" class="panel-collapse collapse ${collapse}" role="tabpanel" aria-labelledby="other2Heading">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="sellingReason">Reason for Selling:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.sellingReason}
										</div>
									</div>
								
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="support">Support / Training:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.support}
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12 col-sm-4"><label for="ownerFinancing">Owner Financing:</label></div>
										<div class="col-xs-12 col-sm-8">
											${site.listing.businessDetails.ownerFinancing}
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</form>
		</div>
		
		
		<div class="col-xs-12 col-sm-3">
			<c:if test="${!site.listing.sold}">
				<c:if test="${site.listing.user.agent}">
					<div class="panel panel-default">
						<div class="panel-heading" style="background-color:#96a5b3;color:#ffffff">
							<h4 class="panel-title">
								<c:if test="${site.listing.businessDetails.hasListingLinks()}">
									Agent &amp; MLS Links
								</c:if>
								<c:if test="${!site.listing.businessDetails.hasListingLinks()}">
									Agent Information
								</c:if>
							</h4>
						</div>
						<div class="panel-body">
							<div class="text-center">
								<p><strong>${site.contact.displayName}</strong></p>
								
								<p>${site.listing.user.companyName}</p>
								
								<common:photo editable="false" site="${site}" photo="${site.profilePhoto}" baseURL="${pageContext.request.contextPath}" clazz="profile-wrapper img-responsive"/>
								
								<div style="margin-top:5px"></div>
								<p>${site.contact.telephone}</p>
								
								<a type="button" class="btn btn-primary" href="mailto:${site.listing.user.email}?subject=re: ${site.listing.title} (${application.name})">By Email</a>
							</div>
							
							<c:if test="${site.listing.businessDetails.hasListingLinks()}">
								<hr/>
								
								<div>
									MLS Listing Links
									<div style="float:right;">
										<c:if test="${not empty site.listing.businessDetails.multiMediaLink}">
											<a href="${site.listing.businessDetails.multiMediaLink}" target="_new"><span class="glyphicon glyphicon-film"></span></a>
										</c:if>
										<c:if test="${not empty site.listing.businessDetails.agentListingLink}">
											<a href="${site.listing.businessDetails.agentListingLink}" target="_new"><span class="glyphicon glyphicon-link"></span></a>
										</c:if>
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</c:if>
				
				<c:if test="${!site.listing.user.agent}">
					<div class="panel panel-default">
						<div class="panel-heading" style="background-color:#96a5b3;color:#ffffff">
							<h4 class="panel-title">
								Contact Information
							</h4>
						</div>
						<div class="panel-body">
							<div class="text-center">
								<c:if test="${site.listing.includeContactName}">
									<p><strong>${site.contact.displayName}</strong></p>
								</c:if>
								
								<common:photo editable="false" site="${site}" photo="${site.profilePhoto}" baseURL="${pageContext.request.contextPath}" clazz="profile-wrapper img-responsive"/>
								
								<div style="margin-top:5px"></div>
								<c:if test="${site.listing.includeContactTelephone}">
									<p>${site.contact.telephone}</p>
								</c:if>

								<a type="button" class="btn btn-primary" <c:if test="${!demo}">onclick="displayEmail()"</c:if>>By Email</a>
							</div>
						</div>
					</div>
				</c:if>
			</c:if>
			
			<div class="panel panel-default">
				<div class="panel-heading" style="background-color:#96a5b3;color:#ffffff">
					<h4 class="panel-title">
						Location
					</h4>
				</div>
				<div class="panel-body">
					<jsp:include page="../common/map.jsp" flush="true"/>
				</div>
			</div>
			
			<c:if test="${site.listing.businessDetails.hasSocialMedia()}">
				<div class="panel panel-default">
					<div class="panel-heading" style="background-color:#96a5b3;color:#ffffff">
						<h4 class="panel-title">
							Internet Marketing
						</h4>
					</div>
					<div class="panel-body">
						<c:if test="${not empty site.listing.businessDetails.websiteURL}">
							<a target="_blank" href="${site.listing.businessDetails.websiteURL}" style="text-decoration:none">
								<img src="${pageContext.request.contextPath}/layouts/version1/images/website.png" width="50" height="50" border="0" title="Business Website" />
							</a>
						</c:if>
						<c:if test="${not empty site.listing.businessDetails.facebookURL}">
							<a target="_blank" href="${site.listing.businessDetails.facebookURL}" style="text-decoration:none">
								<img src="${pageContext.request.contextPath}/layouts/version1/images/facebook.png" width="50" height="50" border="0" title="Facebook" />
							</a>
						</c:if>
						<c:if test="${not empty site.listing.businessDetails.twitterURL}">
							<a target="_blank" href="${site.listing.businessDetails.twitterURL}" style="text-decoration:none">
								<img src="${pageContext.request.contextPath}/layouts/version1/images/twitter.png" width="50" height="50" border="0" title="Twitter" />
							</a>
						</c:if>
						<c:if test="${not empty site.listing.businessDetails.tripAdvisorURL}">
							<a target="_blank" href="${site.listing.businessDetails.tripAdvisorURL}" style="text-decoration:none">
								<img src="${pageContext.request.contextPath}/layouts/version1/images/tripadvisor.png" width="50" height="50" border="0" title="Trip Advisor" />
							</a>
						</c:if>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>
<jsp:include page="email.jsp" flush="true"/>