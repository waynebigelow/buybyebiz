<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.util.StringUtil"%>
<%@ page import="ca.app.model.common.CountryType"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/chainedRemote/js/jquery.chained.remote.min.js"></script>

<script type="text/javascript">
var countryDefault = "<%=CountryType.CA.getShortName()%>";
$(document).ready(function(e) {
	$('#province').remoteChained({
		parents:'#country',
		url:'${pageContext.request.contextPath}/common/listProvincesOrStates.json?c=1&type='
	});
});

function buildSelect(method, fieldName, compareVal){
	$.getJSON(
		"${pageContext.request.contextPath}/common/" + method,
		function(data) {
			var options = "";
			var idVal = "";
			
			$.each(data.rows, function(key, val) {
				idVal = val.id;
				options += '<option value="' + idVal + '" ' + (idVal==compareVal?"selected=\"selected\"":"") + '>' + val.name + '</option>';
			});
			
			var select = $("#" + fieldName);
			select.empty();
			select.html(options);
	});
}
</script>

<div class="form-group">
	<div class="col-xs-4"><label for="address1"><spring:message code="25.lbl.address1"/>:</label></div>
	<div class="col-xs-8">
		<input id="address1" name="address1" type="text" value="${user.address.address1}" class="form-control" maxLength="256" />
	</div>
</div>

<div class="form-group">
	<div class="col-xs-4"><label for="address2"><spring:message code="25.lbl.address2"/>:</label></div>
	<div class="col-xs-8">
		<input id="address2" name="address2" type="text" value="${user.address.address2}" class="form-control" maxLength="256" />
	</div>
</div>

<div class="form-group">
	<div class="col-xs-4"><label for="city"><spring:message code="25.lbl.city"/>:</label></div>
	<div class="col-xs-8">
		<input id="city" name="city" type="text" value="${user.address.city}" class="form-control" maxLength="256" 
			data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
	</div>
</div>

<div class="form-group">
	<div class="col-xs-4"><label for="province"><spring:message code="25.lbl.province"/>:</label></div>
	<div class="col-xs-8">
		<select id="province" name="province" class="form-control" maxLength="256">
		</select>
	</div>
</div>

<div class="form-group">
	<div class="col-xs-4"><label for="country"><spring:message code="25.lbl.country"/>:</label></div>
	<div class="col-xs-8">
		<select id="country" name="country" class="form-control" maxLength="256">
		</select>
	</div>
</div>

<div class="form-group">
	<div class="col-xs-4"><label for="postalCode"><spring:message code="25.lbl.postal.code"/>:</label></div>
	<div class="col-xs-4">
		<input id="postalCode" name="postalCode" type="text" value="${user.address.postalCode}" class="form-control" placeholder="K1K1K1" maxLength="6" 
			data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>" />
	</div>
</div>