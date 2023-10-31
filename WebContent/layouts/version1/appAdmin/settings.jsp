<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
function doAction(type){
	$.ajax({
		url:'${pageContext.request.contextPath}/appAdmin/action.do',
		type:'post',
		data:{
			action:type
		},
		success:function(data, textStatus, jqXHR) {
			showAlert('info', 'md', '<spring:message code="85.msg.action.completed" javaScriptEscape="true" />', 1500);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}
</script>

<button type="button" class="btn btn-default" onclick="doAction('cc')"><spring:message code="85.btn.1" /></button>
<button type="button" class="btn btn-default" onclick="doAction('rp')"><spring:message code="85.btn.2" /></button>
<button type="button" class="btn btn-default" onclick="doAction('rr')"><spring:message code="85.btn.3" /></button>