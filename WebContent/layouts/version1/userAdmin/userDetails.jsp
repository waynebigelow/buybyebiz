<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
.panel-tabbed {
	border-top: 0px;
	border-top-left-radius: 0px;
	border-top-right-radius: 0px;
}
</style>

<script type="text/javascript">
$(document).ready(function(e) {

});
</script>

<a style="float:right;font-size:1.3em" tabIndex="-1" href="javascript:viewUD()"><span class="glyphicon glyphicon-question-sign"></span></a>

<div class="col-xs-12">
	<jsp:include page="../commonAdmin/userDetails.jsp" flush="true" />
</div>

<jsp:include page="changePwd.jsp" flush="true" />
<jsp:include page="changeEmail.jsp" flush="true" />
<jsp:include page="../help/userAdmin/helpUserDetails.jsp" flush="true" />