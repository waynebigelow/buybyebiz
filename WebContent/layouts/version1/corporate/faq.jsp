<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ca.app.util.ProjectUtil"%>

<div class="container admin-container">
	<div class="row">
		<div class="col-xs-12 text-center-xs">
			<h1>Frequently Asked Questions</h1>
		</div>
		
		<div class="col-xs-12">
			<div class="panel-group" style="margin-bottom:5px" id="faqAccordion" role="tablist" aria-multiselectable="true">
				<div class="panel panel-info">
					<div class="panel-heading" role="tab" id="faq8Heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#faqAccordion" href="#faq8Panel" aria-expanded="true" aria-controls="faq8Panel">
								<i class="indicator glyphicon glyphicon-chevron-up"></i>
								Why should I use ${application.name}?
							</a>
						</h4>
					</div>
					
					<div id="faq8Panel" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="faq8Heading">
						<div class="panel-body">
							It's FREE!! Its free to register, free to create a listing, free to publish your listing and it has free support.<br/>
							Should your business sell before the free trial has ended, then it cost you nothing! There is no obligation to extend, you can just walk away.
						</div>
					</div>
				</div>
				
				<div class="panel panel-info">
					<div class="panel-heading" role="tab" id="faq3Heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#faqAccordion" href="#faq3Panel" aria-expanded="true" aria-controls="faq3Panel">
								<i class="indicator glyphicon glyphicon-chevron-down"></i>
								Will ${application.name} be able to sell my business?
							</a>
						</h4>
					</div>
					
					<div id="faq3Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="faq3Heading">
						<div class="panel-body">
							<p>
								We make no claims that listing with ${application.name} will accelerate the purchase of your business. However,
								selling a business takes time and needs exposure. Your listing on ${application.name} is optimized for viewing on any device.
								That means your business listing looks great whether its viewed on a smart phone, tablet or computer. As well,
								business listings can be shared easily on Facebook for even more exposure.
							</p>
							
							<p>
								${application.name} is not a commercial 
								brokerage and offers up no advice as to the process of buying/selling a business. We connect buyers with business owners.
							</p>
						</div>
					</div>
				</div>
				
				<div class="panel panel-info">
					<div class="panel-heading" role="tab" id="faq4Heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#faqAccordion" href="#faq4Panel" aria-expanded="true" aria-controls="faq4Panel">
								<i class="indicator glyphicon glyphicon-chevron-down"></i>
								Who is the target audience?
							</a>
						</h4>
					</div>
					
					<div id="faq4Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="faq4Heading">
						<div class="panel-body">
							<p>
								${application.name} is focusing on buyers who don't want to be encumbered by cluttered websites. ${application.name} contains no pay per click advertising or pages
								that are misleading or irrelevant. It connects buyers with sellers.
							</p>
						</div>
					</div>
				</div>
				
				<div class="panel panel-info">
					<div class="panel-heading" role="tab" id="faq10Heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#faqAccordion" href="#faq10Panel" aria-expanded="true" aria-controls="faq10Panel">
								<i class="indicator glyphicon glyphicon-chevron-down"></i>
								Do you track page hit statistics?
							</a>
						</h4>
					</div>
					
					<div id="faq10Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="faq10Heading">
						<div class="panel-body">
							We track and make available to listing owners a page hit count.
						</div>
					</div>
				</div>
				
				<div class="panel panel-info">
					<div class="panel-heading" role="tab" id="faq2Heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#faqAccordion" href="#faq2Panel" aria-expanded="true" aria-controls="faq2Panel">
								<i class="indicator glyphicon glyphicon-chevron-down"></i>
								Is my personal and business information secure?
							</a>
						</h4>
					</div>
					
					<div id="faq2Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="faq2Heading">
						<div class="panel-body">
							<p>
								${application.name} uses SSL (Secure Socket Layer) as the transfer protocol of choice, which means your
								data is encrypted. ${application.name} is also certified by GoDaddy and McAfee Secure. This lets users know that ${application.name} is not trying to steal information
								and that its a legitimate enterprise.
							</p>
							<p>
								As well, we only ask for information you are willing to give; with minimal exceptions (i.e. name, email). To be effective, however, business listings
								have to adhere to a few additional details.
							</p>
						</div>
					</div>
				</div>
				
				<div class="panel panel-info">
					<div class="panel-heading" role="tab" id="faq5Heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#faqAccordion" href="#faq5Panel" aria-expanded="true" aria-controls="faq5Panel">
								<i class="indicator glyphicon glyphicon-chevron-down"></i>
								Does ${application.name} provide support?
							</a>
						</h4>
					</div>
					
					<div id="faq5Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="faq5Heading">
						<div class="panel-body">
							<p>
								${application.name} offers free support. Support hours are 9 AM to 5 PM EST Monday to Friday.
							</p>
							
							<p>
								Toll free at ${application.supportPhone} or by email at ${application.supportEmail}.
							</p>
						</div>
					</div>
				</div>
				
				<div class="panel panel-info">
					<div class="panel-heading" role="tab" id="faq6Heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#faqAccordion" href="#faq6Panel" aria-expanded="true" aria-controls="faq6Panel">
								<i class="indicator glyphicon glyphicon-chevron-down"></i>
								Why does ${application.name} use PayPal as its payment provider?
							</a>
						</h4>
					</div>
					
					<div id="faq6Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="faq6Heading">
						<div class="panel-body">
							PayPal is a world leader as an eCommerce portal. It is a secure, reliable and responsible platform that will manage your purchase transaction from payment to invoicing.
						</div>
					</div>
				</div>
				
				<div class="panel panel-info">
					<div class="panel-heading" role="tab" id="faq7Heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#faqAccordion" href="#faq7Panel" aria-expanded="true" aria-controls="faq7Panel">
								<i class="indicator glyphicon glyphicon-chevron-down"></i>
								What do the listing extensions cost?
							</a>
						</h4>
					</div>
					
					<div id="faq7Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="faq7Heading">
						<div class="panel-body">
							<jsp:include page="extensions.jsp" flush="true" />
						</div>
					</div>
				</div>
				
				<div class="panel panel-info">
					<div class="panel-heading" role="tab" id="faq9Heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#faqAccordion" href="#faq9Panel" aria-expanded="true" aria-controls="faq9Panel">
								<i class="indicator glyphicon glyphicon-chevron-down"></i>
								Are refunds available?
							</a>
						</h4>
					</div>
					
					<div id="faq9Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="faq9Heading">
						<div class="panel-body">
							<p>
								Refunds are available any time on a pro-rated basis.
							</p>
							
							<p>
								As an example, if you pay for a 1 year extension and desire a refund after 3 months then ${application.name} will refund you the 1 year cost 
								less the cost of a 3 month extension.
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>