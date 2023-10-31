<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#HLAccordion').on('hidden.bs.collapse', toggleChevron);
	$('#HLAccordion').on('shown.bs.collapse', toggleChevron);
});

function viewHL() {
	$('#HLModal').modal();
}

function closeHL(){
	$('#HLModal').modal('hide')
}
</script>

<div class="modal fade" id="HLModal" tabIndex="-1" role="dialog" aria-labelledby="HLModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="HLModalLabel">Help for the Listings Page</h4>
			</div>
			
			<div class="modal-body">
				<div class="panel-group" style="margin-bottom:5px" id="HLAccordion" role="tablist" aria-multiselectable="true">
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="HL1Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#HLAccordion" href="#HL1Panel" aria-expanded="false" aria-controls="HL1Panel">
									<i class="indicator glyphicon glyphicon-chevron-up"></i>
									Listings Page
								</a>
							</h4>
						</div>
						
						<div id="HL1Panel" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="HL1Heading">
							<div class="panel-body">
								<p>
									The Listings page gives you the ability to Create, Edit, Delete, Publish and Extend Listings. As well, you will 
									have the ability to manage your listing enquiries; where you will be able to read and reply to them.
								</p>
							</div>
						</div>
					</div>
				
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="HL2Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#HLAccordion" href="#HL2Panel" aria-expanded="false" aria-controls="HL2Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Creating, Editing or Deleting a Listing
								</a>
							</h4>
						</div>
						
						<div id="HL2Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="HL2Heading">
							<div class="panel-body">
								<p>
									<b>Creating:</b>
									You can create multiple listings by clicking the "Create Listing" button. Upon clicking the button you
									will be presented with a draft template of the listing where you can add content for Listing Details, Business Details, Location, Contact Information,
									Business Marketing Details and Photos.
									<br/><br/>
									You may not proceed with the listing creation until, at the very least, you've provided Listing Details and a Location. After this is done
									you will be presented with the template in its entirety. You will find more help on how to complete your listing within the template.
								</p>
								
								<hr/>
								
								<p>
									<b>Editing:</b>
									There are two ways to edit a listing.
								</p>
								<ol>
									<li>By clicking the <span class="glyphicon glyphicon-menu-hamburger"></span> under the "Actions" column and then clicking "Edit".</li> 
									<li>By clicking the title of the business</li>
								</ol>
								
								<hr/>
								
								<p>
									<b>Deleting:</b>
									You can delete a listing by by clicking the <span class="glyphicon glyphicon-menu-hamburger"></span> under the "Actions" column and then clicking "Delete".
								</p>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="HL3Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#HLAccordion" href="#HL3Panel" aria-expanded="false" aria-controls="HL3Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Publishing your Listing
								</a>
							</h4>
						</div>
						
						<div id="HL3Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="HL3Heading">
							<div class="panel-body">
								<p>
									You can publish a listing any time after you've created one by clicking the "Publish Listing" link. Upon clicking the 
									link you will be presented with a pop-up window. From that window you can either "Cancel" or "Publish". Clicking the "Publish" button
									will send a request to ${application.name} for content review.
								</p>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="HL9Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#HLAccordion" href="#HL9Panel" aria-expanded="false" aria-controls="HL9Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Content Reviews
								</a>
							</h4>
						</div>
						
						<div id="HL9Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="HL9Heading">
							<div class="panel-body">
								<p>
									The review can take approximately 1 hour to complete. If the review passes
									then the listing will be made active and viewable on the internet. If the review does not pass then you 
									will be notified via email that it was rejected. Rejected content is made viewable to you by clicking the "Rejected" link under the "Approvals" column.
									<b>The listing can not be published until all rejected content has been resolved.</b>
									
									
									<br/><br/>
									If changes have been made to the listing after it's been published then those changes will also need to be approved. This will happen
									automatically by moderators for ${application.name}.
								</p>
								
								<hr/>
								
								<p>
									<b>Approvals Column:</b>
									The "Approvals" column is a quick reference for you to see the status of your content changes. There are two status types; Pending and Rejected.
									If content has been rejected, then there will be a link made available to you that when clicked will open a pop-up window containing
									a list of rejected content and the reasons for it.
								</p>
								
								<hr/>
								
								<p>
									<b>Why Review Content:</b>
									The content review is necessary to protect ${application.name} from illicit businesses, hackers and persons with malicious intentions. Please familiarize
									yourself with our Terms of Use and Privacy Policy. 
								</p>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="HL5Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#HLAccordion" href="#HL5Panel" aria-expanded="false" aria-controls="HL5Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Listing Enquiries
								</a>
							</h4>
						</div>
						
						<div id="HL5Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="HL5Heading">
							<div class="panel-body">
								<p>
									<b>Buyer Correspondence:</b>
									When a potential buyer is interested in your business listing, they will have to correspond with you through ${application.name}'s 
									enquiry process. This is necessary to help protect you from persons with malicious intentions. A potential buyer will be able to correspond with you 
									by clicking the "Email" button within your listing and only after they have followed through with the registration process.
								</p>
									
								<p>
									<b>Enquiry Notification Email:</b>
									You will receive an email when a buyer has submitted an enquiry.
								</p>
								
								<p>
									<b>Inbox:</b>
									If an enquiry has been submitted for the listing then a link under the "Inbox" column will be made available. The link will display
									an aggregate number of unread enquiries. When clicked a pop-up window will open displaying all correspondence for the chosen listing. You
									will be able to read and reply from this window.
								</p>
								
								<p>
									<b>Disclosure:</b>
									It is <b>strongly advised</b> that you not disclose sensitive information until a proper vetting has been achieved. ${application.name} makes it possible for you to 
									correspond with anonymity and security.
								</p>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="HL4Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#HLAccordion" href="#HL4Panel" aria-expanded="false" aria-controls="HL4Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Expired Listings
								</a>
							</h4>
						</div>
						
						<div id="HL4Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="HL4Heading">
							<div class="panel-body">
								<p>
									Expired listings will not be visible on the internet and correspondence will be disabled. You can extend the listing by clicking the "Extend Listing" link under the "Expiration" column.
									<br/></br>
									There are two types of expiration related notifications:
								</p>
									
								<ol>
									<li>Pending expiration notices are sent to you 2 days prior to and leading up to expiration.</li>
									<li>Expired notifications are sent to you the moment a listing has expired.</li>
								</ol>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="HL7Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#HLAccordion" href="#HL7Panel" aria-expanded="false" aria-controls="HL7Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Extending a Listing
								</a>
							</h4>
						</div>
						
						<div id="HL7Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="HL7Heading">
							<div class="panel-body">
								<p>
									You can extend the listing by clicking the "Extend Listing" link under the "Expiration" column. Upon clicking the link a pop-up window will be presented from which you can either click "Cancel" or "Pay Now".
									You must first choose a time period from the drop down before clicking the "Pay Now" button.
								</p>
								
								<hr/>
								
								<p>
									<b>Payment Process:</b>
									Upon clicking the "Pay Now" button you will be redirected to PayPal where the payment transaction will be completed.
								</p>
								
								<hr/>
								
								<p>
									<b>Why use PayPal:</b>
									${application.name} is a marketing software that connects Buyers with Sellers 
									and recognizes the delicate nature of online financial transactions. ${application.name} uses PayPal because it is a leading eCommerce provider. 
									It is a secure, reliable and responsible platform that will manage your purchase transaction from payment to invoicing.
								</p>
								
								<hr/>
								
								<p>
									<b>Listing Activation:</b>
									${application.name} will reactivate the listing once PayPal has confirmed the successful transaction, usually within 15 minutes.
								</p>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="HL6Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#HLAccordion" href="#HL6Panel" aria-expanded="false" aria-controls="HL6Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Sold or Suspending Listings
								</a>
							</h4>
						</div>
						
						<div id="HL6Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="HL6Heading">
							<div class="panel-body">
								<p>
									Active listings can be marked as sold or can be placed in suspension. This is achieved by clicking the "Active" link
									under the "Status" column. Upon clicking the link a pop-up window will open and a change of status can be made
									by choosing a new status from the drop-down list. Clicking "Save" will complete the change.
								</p>
								
								<p>
									<b>Sold:</b>
									Listings marked as sold will disable all active enquiries, as well a "SOLD" banner will be displayed across searches 
									and also within the business listing. It is recommended that you mark a listing as "SOLD" for a period of time. This will
									make it clear to interested buyers that the business is no longer available.
								</p>
								
								<p>
									<b>Suspended:</b>
									Listings marked as suspended will remove the listing from the search feature. However, you will be able to continue
									corresponding with any active business enquiries.
								</p>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="HL8Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#HLAccordion" href="#HL8Panel" aria-expanded="false" aria-controls="HL8Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Refunds
								</a>
							</h4>
						</div>
						
						<div id="HL8Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="HL8Heading">
							<div class="panel-body">
								<p>
									${application.name} will issue you a refund on a pro-rated basis. For example, if you purchased a one year extension and you've requested a refund after a month, 
									then the refund will be the cost of the one year extension less the cost of a one month extension.
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.close"/></button>
			</div>
		</div>
	</div>
</div>