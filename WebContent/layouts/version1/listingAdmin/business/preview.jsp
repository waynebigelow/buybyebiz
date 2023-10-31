<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld"%>
<%@ page import="ca.app.model.photo.PhotoType"%>
<%@ page import="ca.app.model.listing.ListingStatus"%>
<%@ page import="ca.app.model.listing.MetaDataType"%>

<style>
.profile-wrapper {
	border:1px solid #c1c1c1;
	padding:2px;
	display:block;
	margin:0 auto;
}
.Collage {
	padding:2px;
}
.Collage img {
	margin:0;
	padding:0;
	display:inline-block;
	vertical-align:bottom;
	opacity:1.5;
	border:4px solid #ffffff;
}
.Caption {
	font-size:1em;
}
.Caption_Content {
	text-align:center;
	color:#ffffff;
}
</style>

<script type="text/javascript">
$(window).load(function() {
	collage();
	$('.Collage').collageCaption();
	
	$('#accordion2').on('hidden.bs.collapse', toggleChevron);
	$('#accordion2').on('shown.bs.collapse', toggleChevron);
	
	function collage() {
		$('.Collage').removeWhitespace().collagePlus({
			'fadeSpeed':3000,
			'targetHeight':85,
			'targetWidth':85,
			'effect':'effect-4',
			'direction':'vertical',
			'allowPartialLastRow':true
		});
	};
	
	$('.image-wrapper').show();
});

function displayEmail(){
	showAlert('info', 'md', 'Email functionality is disabled while previewing.');
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

<div class="row">
	<div class="col-xs-12">
		<h1 class="text-center">
			<c:if test="${site.listing.title != ''}">${site.listing.title}</c:if>
		</h1>
		
		<h2>
			${site.getCurrencyFormatted(site.listing.price)}&nbsp;
			<a role="menuitem" tabIndex="-1" style="font-size:.6em" href="javascript:viewHelp(7)"><span class="glyphicon glyphicon-question-sign"></span></a>
			<div style="float:right" class="fb-like" data-href="https://buybyebiz.com" data-layout="button_count" data-action="like" data-size="small" data-show-faces="true" data-share="true"></div>
		</h2>
	</div>

	<div class="col-xs-12 col-sm-9">
		<div class="panel panel-default">
			<div style="padding:0px">
				<div class="text-center" style="width:95%;background-color:#000000;color:#ffffff;zindex:1000;position:absolute;font-size:1.5em">Click the theme photo to add/edit.</div>
				<common:photo editable="true" site="${site}" photo="${site.themePhoto}" baseURL="${pageContext.request.contextPath}" clazz="img-responsive"/>
				
				<section id="collageDiv" class="Collage">
					<c:forEach items="${site.photoList}" var="photo">
						<div class="image-wrapper popup-gallery" data-caption="${photo.caption}"  style="display:none">
							<common:photo editable="true" site="${site}" photo="${photo}" baseURL="${pageContext.request.contextPath}"/>
						</div>
					</c:forEach>
				</section>
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading" style="background-color:#96a5b3;color:#ffffff">
				<h4 class="panel-title">
					Listing Details
					<div style="float:right">
						<a role="menuitem" tabIndex="-1" href="javascript:editListingDetails()" style="color:#ffffff"><span class="glyphicon glyphicon-pencil"></span></a>
						<a role="menuitem" tabIndex="-1" href="javascript:viewHelp(2)" style="color:#ffffff"><span class="glyphicon glyphicon-question-sign"></span></a>
					</div>
				</h4>
			</div>
			<div class="panel-body">
				<c:if test="${not empty site.listing.description}">
					<p>${site.listing.description}</p>
				</c:if>
			</div>
		</div>
		
		<form class="form-horizontal">
			<div class="panel-group" id="accordion2" role="tablist" aria-multiselectable="true">
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="financials2Heading" style="background-color:#96a5b3;color:#ffffff">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion2" href="#financials2Panel" aria-expanded="true" aria-controls="financials2Panel">
								<i class="indicator glyphicon glyphicon-chevron-up"></i>
								Financial Details
							</a>
						
							<div style="float:right">
								<a role="menuitem" tabIndex="-1" href="javascript:editBusinessDetails()" style="color:#ffffff"><span class="glyphicon glyphicon-pencil"></span></a>
								<a role="menuitem" tabIndex="-1" href="javascript:viewHelp(3)" style="color:#ffffff"><span class="glyphicon glyphicon-question-sign"></span></a>
							</div>
						</h4>
					</div>
					
					<div id="financials2Panel" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="financials2Heading">
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
				
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="property2Heading" style="background-color:#96a5b3;color:#ffffff">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion2" href="#property2Panel" aria-expanded="true" aria-controls="property2Panel">
								<i class="indicator glyphicon glyphicon-chevron-down"></i>
								Operational Details
							</a>
						</h4>
					</div>
					
					<div id="property2Panel" class="panel-collapse collapse" role="tabpanel" aria-labelledby="property2Heading">
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
								<div class="col-xs-12 col-sm-4"><label for="numberOfEmployees">Year Established:</label></div>
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
				
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="operations2Heading" style="background-color:#96a5b3;color:#ffffff">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion2" href="#operations2Panel" aria-expanded="true" aria-controls="operations2Panel">
								<i class="indicator glyphicon glyphicon-chevron-down"></i>
								Property Details
							</a>
						</h4>
					</div>
					
					<div id="operations2Panel" class="panel-collapse collapse" role="tabpanel" aria-labelledby="operations2Heading">
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
				
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="other2Heading" style="background-color:#96a5b3;color:#ffffff">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion2" href="#other2Panel" aria-expanded="true" aria-controls="other2Panel">
								<i class="indicator glyphicon glyphicon-chevron-down"></i>
								Other Details
							</a>
						</h4>
					</div>
					
					<div id="other2Panel" class="panel-collapse collapse" role="tabpanel" aria-labelledby="other2Heading">
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
			</div>
		</form>
	</div>
	
	<div class="col-xs-12 col-sm-3">
		<c:if test="${site.listing.user.agent}">
			<div class="panel panel-default">
				<div class="panel-heading" style="background-color:#96a5b3;color:#ffffff">
					<h4 class="panel-title">
						Agent &amp; MLS Links
						<div style="float:right">
							<a role="menuitem" tabIndex="-1" href="javascript:editMlsLinks()" style="color:#ffffff"><span class="glyphicon glyphicon-pencil"></span></a>
							<a role="menuitem" tabIndex="-1" href="javascript:viewHelp(4)" style="color:#ffffff"><span class="glyphicon glyphicon-question-sign"></span></a>
						</div>
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
						<div style="float:right">
							<a role="menuitem" tabIndex="-1" href="javascript:editSettings()" style="color:#ffffff"><span class="glyphicon glyphicon-pencil"></span></a>
							<a role="menuitem" tabIndex="-1" href="javascript:viewHelp(4)" style="color:#ffffff"><span class="glyphicon glyphicon-question-sign"></span></a>
						</div>
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
		
		<div class="panel panel-default">
			<div class="panel-heading" style="background-color:#96a5b3;color:#ffffff">
				<h4 class="panel-title">
					Location
					<div style="float:right">
						<a role="menuitem" tabIndex="-1" href="javascript:editLocation()" style="color:#ffffff"><span class="glyphicon glyphicon-pencil"></span></a>
						<a role="menuitem" tabIndex="-1" href="javascript:viewHelp(5)" style="color:#ffffff"><span class="glyphicon glyphicon-question-sign"></span></a>
					</div>
				</h4>
			</div>
			<div class="panel-body">
				<c:if test="${not empty listings}">
					<jsp:include page="../../common/map.jsp" flush="true"/>
				</c:if>
				<c:if test="${empty listings}">
					<div style="text-align:center;border:1px solid red;padding:3px">
						Add location information to enable the map! You can be as vague or precise as you want.
					</div>
				</c:if>
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading" style="background-color:#96a5b3;color:#ffffff">
				<h4 class="panel-title">
					Business Marketing
					<div style="float:right">
						<a role="menuitem" tabIndex="-1" href="javascript:editAdvertising()" style="color:#ffffff"><span class="glyphicon glyphicon-pencil"></span></a>
						<a role="menuitem" tabIndex="-1" href="javascript:viewHelp(6)" style="color:#ffffff"><span class="glyphicon glyphicon-question-sign"></span></a>
					</div>
				</h4>
			</div>
			<div class="panel-body">
				<c:if test="${!site.listing.businessDetails.hasSocialMedia()}">
					<div style="text-align:center;border:1px solid red;padding:3px">
						Be sure to include any website, facebook, twitter or tripadvisor accounts that the business uses for marketing purposes!
					</div>
				</c:if>
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
	</div>
</div>

<c:if test="${site.listing.listingId eq 0}">
	<jsp:include page="../listingStart.jsp" flush="true" />
</c:if>

<c:if test="${site.listing.listingId gt 0}">
	<jsp:include page="../listingDetails.jsp" flush="true" />
	<jsp:include page="../location.jsp" flush="true" />
	
	<c:if test="${!site.listing.user.agent}">
		<jsp:include page="../settings.jsp" flush="true" />
	</c:if>
	
	<c:if test="${site.listing.user.agent}">
		<jsp:include page="../mlsLinks.jsp" flush="true" />
	</c:if>

	<jsp:include page="../business/businessDetails.jsp" flush="true" />
	<jsp:include page="../business/advertising.jsp" flush="true" />
	<jsp:include page="../photo.jsp" flush="true" />
	<jsp:include page="../../help/listingAdmin/business/listingHelp.jsp" flush="true" />
</c:if>