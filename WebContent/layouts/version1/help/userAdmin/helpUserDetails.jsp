<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#UDAccordion').on('hidden.bs.collapse', toggleChevron);
	$('#UDAccordion').on('shown.bs.collapse', toggleChevron);
});

function viewUD() {
	$('#UDModal').modal();
}

function closeUD(){
	$('#UDModal').modal('hide')
}
</script>

<div class="modal fade" id="UDModal" tabIndex="-1" role="dialog" aria-labelledby="UDModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="UDModalLabel">Help for the Account Page</h4>
			</div>
			
			<div class="modal-body">
				<div class="panel-group" style="margin-bottom:5px" id="UDAccordion" role="tablist" aria-multiselectable="true">
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="UD1Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#UDAccordion" href="#UD1Panel" aria-expanded="false" aria-controls="UD1Panel">
									<i class="indicator glyphicon glyphicon-chevron-up"></i>
									Account Page
								</a>
							</h4>
						</div>
						
						<div id="UD1Panel" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="UD1Heading">
							<div class="panel-body">
								<p>
									The Account page gives you the ability to change your contact details, user name/email address, password and notification settings.
								</p>
							</div>
						</div>
					</div>
				
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="UD2Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#UDAccordion" href="#UD2Panel" aria-expanded="false" aria-controls="UD2Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Changing User Name/Email Address
								</a>
							</h4>
						</div>
						
						<div id="UD2Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="UD2Heading">
							<div class="panel-body">
								<p>
									You can change your User Name/Email Address by doing the following:
								</p>
								
								<ol>
									<li>Click the "Change" link located to the right of the User Name/Email Address field.
									<li>Upon clicking the "Change" link a pop-up window will open where you will be able to make the relevant change.</li>
									<li>Click the "Submit" button.</li>
									<li>If successful, an email will be sent to the new email address you specified.</li>
									<li>Within the email there will be a "Change Email" link. You must click the link to complete the change.</li>
									<li>Upon clicking the link, BuyByeBiz will open within a browser and you will be presented with a confirmation message.</li>
									<li>Another email will be emailed to you and within the email will be your new randomnly generated password.</li>
									<li>As soon as possible be sure to change the password.</li>
								</ol>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="UD3Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#UDAccordion" href="#UD3Panel" aria-expanded="false" aria-controls="UD3Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Changing your Password
								</a>
							</h4>
						</div>
						
						<div id="UD3Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="UD3Heading">
							<div class="panel-body">
								<p>
									There are two ways to change your password. Either from the Sign In pop-up window or you can change your password here, by doing the following:
								</p>
								
								<ol>
									<li>Click the "Change" link located to the right of Password field.
									<li>Upon clicking the "Change" link a pop-up window will open.</li>
									<li>Enter your old password, followed by your new password and the new password confirmation.</li>
									<li>Click the reCAPTCHA checkbox and follow through with its instructions.</li>
									<li>Click the "Submit" button.</li>
									<li>If successful, an email will be sent to you indicating that your password was successfully changed.</li>
								</ol>
								
								<p>
									<b>What is reCAPTCHA?</b>
									reCAPTCHA is a service from Google that helps protect websites from spam and abuse. 
									A "CAPTCHA" is a test to tell human and bots apart. It is easy for humans to solve, but hard for "bots" and other malicious software 
									to figure out.
								</p>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="UD6Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#UDAccordion" href="#UD6Panel" aria-expanded="false" aria-controls="UD6Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									User Details
								</a>
							</h4>
						</div>
						
						<div id="UD6Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="UD6Heading">
							<div class="panel-body">
								<p>
									The user details essentially comprises of a First and Last Name and Contact Number.
								</p>
								
								<p>
									<b>First &amp; Last Name</b>: These are mandatory fields. Your name will appear within the Contact Information section
									of your listing. However, you can exclude them from showing by disabling it from the listing template. See the help documentation
									within the template for more details.
								</p>
								
								<p>
									<b>Contact Number</b>: This is an optional field. Your contact number will appear within the Contact Information section
									of your listing. However, you can exclude it from showing by disabling it from the listing template. See the help documentation
									within the template for more details.
								</p>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="UD4Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#UDAccordion" href="#UD4Panel" aria-expanded="false" aria-controls="UD4Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Commercial Real Estate
								</a>
							</h4>
						</div>
						
						<div id="UD4Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="UD4Heading">
							<div class="panel-body">
								<p>
									If you are a commercial real estate representative then identify yourself as such and include your real estate brokerage name. This is important
									as it will include the name under the Contact Information section of the listing.
								</p>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="UD5Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#UDAccordion" href="#UD5Panel" aria-expanded="false" aria-controls="UD5Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Notification Settings
								</a>
							</h4>
						</div>
						
						<div id="UD5Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="UD5Heading">
							<div class="panel-body">
								<p>
									BuyByeBiz needs to communicate with you. Some notifications are necessary for the sake of security and data integrity. Other emails
									may be necessary should you be contacted by potential buyers. The following are a description of some of the notifications that
									can be disabled.
								</p>
								
								<ol>
									<li><b>Listing Enquiry Notifications</b>; these emails are sent to you when a potential buyer wants to correspond with you regarding your business listing.
									You can disable it but BuyByeBiz doesn't recommend it.</li>
									<li><b>Listing Expiration Notifications</b>; these emails are sent to you when your listing is approaching expiration (2 days prior to expiration) and when it has expired.</li>
									<li><b>Promotion Notifications</b>; these emails are sent to you when BuyByeBiz has a promotion that you may be interested in.</li>
								</ol>
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