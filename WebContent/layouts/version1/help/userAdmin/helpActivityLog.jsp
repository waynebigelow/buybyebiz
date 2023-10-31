<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#ALAccordion').on('hidden.bs.collapse', toggleChevron);
	$('#ALAccordion').on('shown.bs.collapse', toggleChevron);
});

function viewAL() {
	$('#ALModal').modal();
}

function closeAL(){
	$('#ALModal').modal('hide')
}
</script>

<div class="modal fade" id="ALModal" tabIndex="-1" role="dialog" aria-labelledby="ALModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="ALModalLabel">Help for the Activity Page</h4>
			</div>
			
			<div class="modal-body">
				<div class="panel-group" style="margin-bottom:5px" id="ALAccordion" role="tablist" aria-multiselectable="true">
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="AL1Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#ALAccordion" href="#AL1Panel" aria-expanded="false" aria-controls="AL1Panel">
									<i class="indicator glyphicon glyphicon-chevron-up"></i>
									Activity Page
								</a>
							</h4>
						</div>
						
						<div id="AL1Panel" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="AL1Heading">
							<div class="panel-body">
								<p>
									The Activity page gives you the ability to review any activity related to either your user account changes or your listing changes.
								</p>
							</div>
						</div>
					</div>
				
					<div class="panel panel-info">
						<div class="panel-heading" role="tab" id="AL2Heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#ALAccordion" href="#AL2Panel" aria-expanded="false" aria-controls="AL2Panel">
									<i class="indicator glyphicon glyphicon-chevron-down"></i>
									Filtering Activity
								</a>
							</h4>
						</div>
						
						<div id="AL2Panel" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="AL2Heading">
							<div class="panel-body">
								<p>
									There are two options to filter; by Area or by Type. Selecting either filter will automatically update the list of activities per your selection. As well,
									a reset button is available to quickly return the filter to the defaults.
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