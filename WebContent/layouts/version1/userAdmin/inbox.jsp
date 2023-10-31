<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.service.common.TokenFieldType"%>

<style>
.poster_bg {
	background-color:#d9edf7;
}
.lister_bg {
	background-color:#dff0d8;
}
.read_bg {
	background-color:#0000ff;
}
.unread_bg {
	background-color:#ff0000;
}
</style>

<script type="text/javascript">
$(document).ready(function(e) {
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
});

function sendReply(v) {
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/sendReply.do',
		type:'post',
		data:{
			<%=TokenFieldType.ENQUIRY.getAlias()%>:v,
			comment:$("#comment"+v).val()
		},
		success:function(data, textStatus, jqXHR) {
			if (data.success == false) {
				$('#alertEmailDiv').html(data.error);
				$('#alertEmailDiv').show();
			} else {
				showAlert('success', 'sm', "Your reply has been sent.", 1500);
			}
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function markAsRead(v) {
	$.ajax({
		url:'${pageContext.request.contextPath}/listingAdmin/markAsRead.do',
		type:'post',
		data:{
			<%=TokenFieldType.ENQUIRY.getAlias()%>:v,
		},
		success:function(data, textStatus, jqXHR) {
			var postCount = $('#badge'+v).html();
			var totalCount = $('#badgeTotal').html();
			var total = parseInt(totalCount) - parseInt(postCount);
			if (total == 0) {
				$('#badgeTotal').css('background-color', '#0000ff');
			}
			
			$('#badgeTotal').html(total+' Msgs');
			$('#badge'+v).html('0 Msgs');
			$('#badge'+v).css('background-color', '#0000ff');
		},
		error:function(jqXHR, textStatus, errorThrown) {
			showAlert('danger', 'md', '<spring:message code="0.msg.generic.warning" javaScriptEscape="true" />');
		}
	});
}

function panelState(v) {
	var glyphId = '#heading'+v+'Glyph';
	var glyph = $(glyphId).attr('class');
	if (glyph == "glyphicon glyphicon-arrow-up") {
		$(glyphId).attr('class', 'glyphicon glyphicon-arrow-down');
	} else {
		$(glyphId).attr('class', 'glyphicon glyphicon-arrow-up');
	}

	markAsRead(v);
}
</script>

<c:if test="${not empty enquiries}">
	<a style="float:right;font-size:1.3em" tabIndex="-1" href="javascript:viewHE()"><span class="glyphicon glyphicon-question-sign"></span></a>
	<div class="col-xs-12">
		<c:forEach items="${enquiries}" var="enquiry">
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="heading${enquiry.token}" style="background-color:#96a5b3;color:#ffffff">
				<h4 class="panel-title">
					<c:if test="${!enquiry.listing.sold}">
						<a data-toggle="collapse" data-parent="#accordion" href="#heading${enquiry.token}Panel" onclick="panelState('${enquiry.token}')" aria-expanded="true" aria-controls="heading${enquiry.token}Panel">
							<span id="heading${enquiry.token}Glyph" class="glyphicon glyphicon-arrow-down"></span>&nbsp;${enquiry.posterFormatted}
						</a>
					</c:if>
					
					<c:if test="${enquiry.listing.sold}">
						${enquiry.posterFormatted}
					</c:if>
				</h4>
			</div>
			
			<div id="heading${enquiry.token}Panel" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${enquiry.token}">
				<div class="panel-body">
					<c:forEach items="${enquiry.posts}" var="post">
						<c:set var="postBg" value="poster_bg" />
						<c:set var="postLayout" value="" />
						<c:if test="${enquiry.poster.userId ne post.authorId}">
							<c:set var="postBg" value="alert-success lister_bg" />
							<c:set var="postLayout" value="col-xs-offset-4" />
						</c:if>
						<div class="${postLayout} col-xs-8">
							<div class="panel panel-default ${postBg}" style="margin-bottom:3px">
								<div class="panel-body">
									${post.commentFormatted}
								</div>
							</div>
						</div>
					</c:forEach>
					
					<div class="col-xs-12">
						<textarea id="comment${enquiry.token}" name="comment${enquiry.token}" class="form-control" rows="3" style="margin-top:20px" placeholder="Reply >" maxLength="1000"
							data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>"></textarea>
						<div style="float:right"><a href="javascript:sendReply('${enquiry.token}')"><span class="glyphicon glyphicon-envelope"></span>&nbsp;Send Reply</a></div>
					</div>
				</div>
			</div>
		</div>
		</c:forEach>
	</div>
</c:if>

<c:if test="${empty enquiries}">
	<a style="float:right;font-size:1.3em" tabIndex="-1" href="javascript:viewHE()"><span class="glyphicon glyphicon-question-sign"></span></a>
	You have posted no listing enquiries.
</c:if>

<jsp:include page="../help/userAdmin/helpEnquiries.jsp" flush="true" />