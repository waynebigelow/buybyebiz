<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container admin-container">
	<div class="row">
		<div class="col-xs-12 text-center-xs">
			<h1>Contact Information</h1>
		</div>
		
		<div class="col-xs-12">
			<p>
				You can reach ${application.name} support by:
			</p>
			
			<ul>
				<li>Email at ${application.supportEmail}, or</li>
				<li>Toll free at ${application.supportPhone}</li>
			</ul>
		</div>
	</div>
</div>