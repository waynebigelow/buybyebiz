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
var <%=TokenFieldType.LISTING.getAlias()%>;
var inboxTemplate = null;
var postingTemplate = null;
$(document).ready(function(e) {
	inboxTemplate = Handlebars.compile($('#inbox-template').html());
	postingTemplate = Handlebars.compile($('#posting-template').html());
	
	$('#inboxModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
	
	$('#alertModal').on('hide.bs.modal', function (e) {
		window.location.reload(true);
	});
});

function loadInbox(v) {
	<%=TokenFieldType.LISTING.getAlias()%> = v;
	$.getJSON('${pageContext.request.contextPath}/listingAdmin/loadInbox.do', {
		<%=TokenFieldType.LISTING.getAlias()%>:v
	})
	.done(function(data) {
		drawInbox(data);
	});
	
	displayInbox();
}

function drawInbox(enquiries) {
	$.each(enquiries, function(i, enquiry) {
		var context = {a:enquiry.posterFormatted,c:enquiry.token};
		$('#inboxDiv').html($('#inboxDiv').html() + inboxTemplate(context));
		
		$.each(enquiry.posts, function(j, post) {
			var postBg = "poster_bg";
			var postLayout = "";
			
			if (enquiry.poster.userId != post.authorId) {
				postBg = "alert-success lister_bg";
				postLayout = "col-xs-offset-4"; 
			}
			
			var postingContext = {a:post.commentFormatted,b:postBg,e:postLayout};
			$('#posts'+enquiry.token).html($('#posts'+enquiry.token).html() + postingTemplate(postingContext));
		});
	});
	
	$('[data-toggle="confirmation"]').confirmation({popout:true});
}

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
			var totalCount = $('#badge'+<%=TokenFieldType.LISTING.getAlias()%>+'Total').html();
			var total = parseInt(totalCount) - parseInt(postCount);
			if (total == 0) {
				$('#badge'+v+'Total').css('background-color', '#0000ff');
			}
			
			$('#badge'+v+'Total').html(total+' Msgs');
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

function displayInbox(){
	$('#inboxModal').modal({
		backdrop:'static',
		keyboard:false
	});
}
</script>

<script id="inbox-template" type="text/x-handlebars-template">
<div class="panel panel-default">
	<div class="panel-heading" role="tab" id="heading{{c}}" style="background-color:#96a5b3;color:#ffffff">
		<h4 class="panel-title">
			<a data-toggle="collapse" data-parent="#accordion" href="#heading{{c}}Panel" onclick="panelState('{{c}}')" aria-expanded="true" aria-controls="heading{{c}}Panel">
				<span id="heading{{c}}Glyph" class="glyphicon glyphicon-arrow-down"></span>&nbsp;{{{a}}}
			</a>
		</h4>
	</div>
	
	<div id="heading{{c}}Panel" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading{{c}}">
		<div class="panel-body">
			<div id="posts{{c}}"></div>

			<div class="col-xs-12">
				<textarea id="comment{{c}}" name="comment{{c}}" class="form-control" rows="3" style="margin-top:20px" placeholder="Reply >" maxLength="1000"
					data-bv-notempty="true" data-bv-notempty-message="<spring:message code="0.msg.mandatory.field.warning"/>"></textarea>
				<div style="float:right"><a href="javascript:sendReply('{{c}}')"><span class="glyphicon glyphicon-envelope"></span>&nbsp;Send Reply</a></div>
			</div>
		</div>
	</div>
</div>
</script>

<script id="posting-template" type="text/x-handlebars-template">
<div class="{{e}} col-xs-8">
	<div class="panel panel-default {{b}}" style="margin-bottom:3px">
		<div class="panel-body">
			{{a}}
		</div>
	</div>
</div>
</script>

<div class="modal fade" id="inboxModal" tabIndex="-1" role="dialog" aria-labelledby="inboxModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" tabIndex="-1"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="0.txt.close"/></span></button>
				<h4 class="modal-title" id="inboxModalLabel">Inbox</h4>
			</div>
			
			<div class="modal-body">
				<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
					<div id="inboxDiv"></div>
				</div>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="0.txt.close"/></button>
			</div>
		</div>
	</div>
</div>