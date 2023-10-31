<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#HEAccordion').on('hidden.bs.collapse', toggleChevron);
	$('#HEAccordion').on('shown.bs.collapse', toggleChevron);
});

function viewHE() {
	$('#HEModal').modal();
}

function closeHE(){
	$('#HEModal').modal('hide')
}
</script>

<div class="modal fade" id="HEModal" tabIndex="-1" role="dialog" aria-labelledby="HEModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="HEModalLabel">Help for Buyer Enquiries</h4>
			</div>
			
			<div class="modal-body">
				<div class="panel-group" style="margin-bottom:5px" id="HEAccordion" role="tablist" aria-multiselectable="true">
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="HE1Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#HEAccordion" href="#HE1Panel" aria-expanded="false" aria-controls="HE1Panel">
									<i class="indicator glyphicon glyphicon-chevron-up"></i>
									Enquiries Page
								</a>
							</h4>
						</div>
						
						<div id="HE1Panel" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="HE1Heading">
							<div class="panel-body">
								<p>
									The Enquiries page gives you the ability to read or reply to enquiries you have made to listing owners. Each 
									enquiry thread is unique to a listing.
								</p>
							</div>
						</div>
					</div>
				
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="HE2Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#HEAccordion" href="#HE2Panel" aria-expanded="false" aria-controls="HE2Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Reading an Enquiry
								</a>
							</h4>
						</div>
						
						<div id="HE2Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="HE2Heading">
							<div class="panel-body">
								<p>
									Should a listing owner respond to one of your enquiries then you will receive an email indicating as much. After signing in, and navigating to
									"My Account", you'll notice a red badge in the "Enquiries" tab with a count of unread messages. After clicking the tab you will be presented with all 
									of your enquiry threads in collapsible panels. The panel's title will also have a red badge indicating the number of unread messages for that listing. 
								</p>
								
								<p>
									Clicking the title link, will expand the panel where you'll be able to read the replies. It is from here that you will be able
									to post new correspondence to the listing owner.
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