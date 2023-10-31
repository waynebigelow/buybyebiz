<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#helpAccordion').on('hidden.bs.collapse', toggleChevron);
	$('#helpAccordion').on('shown.bs.collapse', toggleChevron);
	
	$('#helpModal').on('hide.bs.modal', function (e) {
		$('.panel-collapse.in').collapse('hide');
	});
});

function viewHelp(v) {
	$('#help'+v+'Panel').collapse({
		toggle:true
	});
	$('#help'+v+'Panel').collapse('show');
	$('#helpModal').modal();
}

function closeHelp(){
	$('#helpModal').modal('hide')
}
</script>

<div class="modal fade" id="helpModal" tabIndex="-1" role="dialog" aria-labelledby="helpModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="helpModalLabel">Help</h4>
			</div>
			
			<div class="modal-body">
				<div class="panel-group" style="margin-bottom:5px" id="helpAccordion" role="tablist" aria-multiselectable="true">
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="help7Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#helpAccordion" href="#help7Panel" aria-expanded="false" aria-controls="help7Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									How to Enter Listing Information
								</a>
							</h4>
						</div>
						
						<div id="help7Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="help7Heading">
							<div class="panel-body">
								<p>
									A business listing comprises of 5 different sections for informational purposes. It includes Listing Details, Business Details,
									Contact Information, Location and Business Marketing. Further help information, for each of these sections, can be found below.
								</p>
								
								<p>
									To add or edit information simply click the <span class="glyphicon glyphicon-pencil"></span> found in the corner of each
									of the sections.
								</p>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="help1Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#helpAccordion" href="#help1Panel" aria-expanded="false" aria-controls="help1Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									How to Add Photos
								</a>
							</h4>
						</div>
						
						<div id="help1Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="help1Heading">
							<div class="panel-body">
								There are 3 types of photos:
								<ul>
									<li>Theme Photo; best used to highlight the business listing, like a store front. The photo is automatically sized
									to a preset width and height for a landscaped layout.</li>
									<li>Gallery Photo; these are photos that give a visual representation of your business assets. The photo is automatically
									sized to a preset width and height, optimized for the gallery.</li>
									<li>Contact Photo; this is a photo placeholder for the contact. There is no obligation to add one, however, it gives buyers
									a reference and gives the listing legitimacy should a potential buyer want to contact the business owner.</li>
								</ul>
								
								<p>
									<b>Adding a Photo</b>
								</p>
								<ol>
									<li>Click the relevant placeholder. Placeholders are represented by a graphic image with a "+" sign.</li>
									<li>Click the "Add Photo" button.</li>
									<li>Locate and select the relevant photo from your files. The selected photo will be presented back to you.</li>
									<li>You can modify the photo by moving the slidebar below it and you can alter the photo's positioning by clicking on the photo and moving your mouse.</li>
									<li>You can also add a caption that you can use to describe the photo to potential buyers.</li>
									<li>Click the "Save" button.</li>
								</ol>
								
								<p>
									<b>Editing the Photo Caption</b>
								</p>
								<ol>
									<li>Click the photo whose caption you want to edit.</li>
									<li>Change the caption.</li>
									<li>Click the "Save" button.</li>
								</ol>
								
								<p>
									<b>Replacing a Photo</b>
								</p>
								<ol>
									<li>Click the photo you want to replace.</li>
									<li>Click the 'Replace Photo' button.</li>
									<li>If necessary, manipulate the photo per the above instructions regarding adding a photo.</li>
									<li>If necessary, change the caption.</li>
									<li>Click the "Save" button.</li>
								</ol>
								
								<p>
									<b>Deleting a Photo</b>
								</p>
								<ol>
									<li>Click the photo that you want to delete.</li>
									<li>Click the "Delete" button.</li>
								</ol>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="help2Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#helpAccordion" href="#help2Panel" aria-expanded="false" aria-controls="help2Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Listing Details
								</a>
							</h4>
						</div>
						
						<div id="help2Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="help2Heading">
							<div class="panel-body">
								<p>
									The listing details describes the most essential components of the listing, which is the title of the listing, listing categorization,
									description and price.
								</p>
								
								<p>
									<b>Title</b>: This is a mandatory field. The title does not need to be the business name but for the sake of legitimacy 
									it is recommended. Also, the listing title will become the listing's URI (Uniform Resource Identifier) locator. For example, 
									Stonepipe Wells General Store will have a URI locator of "Stonepipe-Wells-General-Store-For-Sale.html". 
									This will help optimize your listing with search engines like Google and Bing.
								</p>
								
								<p>
									<b>Category</b>: This is a mandatory field. The first level categorization of the business. This will help potential buyers find
									businesses specific to their primary interest. For example, a chef is interested in "Food and Beverages", which is a high-level 
									categorization.
								</p>
								
								<p>
									<b>Sub-Category</b>: This is a mandatory field. A second level categorization of the business. This will help potential buyers find
									businesses specific to their immediate interest. For example, a chef is interested in "Restaurants", which is a more refined filter
									for the "Food and Beverages" category.
								</p>
								
								<p>
									<b>Description</b>: This is a mandatory field. A clear and thorough description of the business will automatically attract potential buyers.
								</p>
								
								<p>
									<b>Price</b>: Not a mandatory field. You can publish your listing without a price but that's not recommended. Buyers most likely already have a price in 
									mind and not disclosing yours will limit a buyer's interest.
								</p>
								
								<a role="menuitem" tabIndex="-1" href="javascript:closeHelp();editListingDetails();"><span class="glyphicon glyphicon-pencil"></span>&nbsp;Edit Listing Details</a>
							</div>
						</div>
					</div>

					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="help3Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#helpAccordion" href="#help3Panel" aria-expanded="false" aria-controls="help3Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Business Details
								</a>
							</h4>
						</div>
						
						<div id="help3Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="help3Heading">
							<div class="panel-body">
								<p>
									The business details of a listing comprises of Financial Details, Operational Details, Property Details and Other Details. None 
									of the fields for these sections are mandatory, however, not advisable. The more information you provide the more
									interest you'll receive. A potential buyer would want to know what the business revenues are, that is probably the single most
									important piece of information you can provide. That being said, BuyByeBiz understands the need for privacy. If you're uncomfortable
									disclosing these details then be non-specific, but be truthful.
								</p>
								
								<p>
									<b>Financial Details</b>: The financial breakdown of the business including minimum and maximum revenues, cost of chattel and inventory.
								</p>
								
								<p>
									<b>Operational Details</b>: The operational breakdown of the business including hours of operation, number of employees and
									year established.
								</p>
								
								<p>
									<b>Property Details</b>: The property breakdown of the business including property type, acreage, frontage and total square footage.
								</p>
								
								<p>
									<b>Other Details</b>: Some other details including reason for selling, are you willing to train or help finance the acquisition.
								</p>
								
								<a role="menuitem" tabIndex="-1" href="javascript:closeHelp();editBusinessDetails();"><span class="glyphicon glyphicon-pencil"></span>&nbsp;Edit Business Details</a>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="help4Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#helpAccordion" href="#help4Panel" aria-expanded="false" aria-controls="help4Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Contact Information
								</a>
							</h4>
						</div>
						
						<div id="help4Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="help4Heading">
							<div class="panel-body">
								<p>
									The contact information comprises of a contact name, contact number and photo and if you're a commercial real estate representative then
									the brokerage name will also be shown. However, they are not mandatory and can be disabled. A contact photo will display in the case
									where one has been uploaded. You can include your name and telephone number, as well, or you can exclude them altogether.
								</p>
								
								<a role="menuitem" tabIndex="-1" href="javascript:closeHelp();editSettings();"><span class="glyphicon glyphicon-pencil"></span>&nbsp;Edit Contact Information</a>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="help5Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#helpAccordion" href="#help5Panel" aria-expanded="false" aria-controls="help5Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Location
								</a>
							</h4>
						</div>
						
						<div id="help5Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="help5Heading">
							<div class="panel-body">
								<p>
									Identifying the business location is important marketing material, therefore, you can not publish a listing without providing a location. 
									However, the location only needs to be specific to the city/town/village it's in.
								<p>
									<b>Address</b>: Not a mandatory field. A civic address of the business.
								</p>
								
								<p>
									<b>City, Province/State, Country</b>: These are mandatory fields.
								</p>
								
								<a role="menuitem" tabIndex="-1" href="javascript:closeHelp();editLocation();"><span class="glyphicon glyphicon-pencil"></span>&nbsp;Edit Location</a>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="help6Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#helpAccordion" href="#help6Panel" aria-expanded="false" aria-controls="help6Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Business Marketing
								</a>
							</h4>
						</div>
						
						<div id="help6Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="help6Heading">
							<div class="panel-body">
								<p>
									Does your business have a website, Facebook page, Twitter account or are you found on TripAdvisor? Then this section
									makes it possible for you to link your listing to those marketing streams.
								</p>
								
								<p>
									<b>Note</b>: be sure your link is fully qualified. As an example, buybyebiz.com needs to be https://buybyebiz.com.
								</p>
								
								<a role="menuitem" tabIndex="-1" href="javascript:closeHelp();editAdvertising();"><span class="glyphicon glyphicon-pencil"></span>&nbsp;Edit Business Marketing</a>
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