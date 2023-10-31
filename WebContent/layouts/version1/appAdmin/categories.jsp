<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
var subCategoryTemplate = null;
$(document).ready(function(e) {
	subCategoryTemplate = Handlebars.compile($('#subCategory-template').html());
});

function loadSubCategories(c, catTitle){
	$.getJSON('${pageContext.request.contextPath}/appAdmin/loadSubCategory.do', {
		c:c
	})
	.done(function(data) {
		if (catTitle != null) {
			$('#subCategoryHeader').html(' - ' + catTitle);
		}
		$('#c').val(c);
		
		drawSubCategories(data);
	});
}

function drawSubCategories(subCategories) {
	var subCategoryHtml = "";
	$.each(subCategories, function(i, sc) {
		if (sc.subCategoryId != "") {
			var context = {s:sc.subCategoryId,c:sc.categoryId,name:sc.name,i18n:sc.i18n,text:sc.text};
			subCategoryHtml = subCategoryHtml + subCategoryTemplate(context);
		}
	});
	
	if (subCategoryHtml != ""){
		$('#subCategoryTable').html(subCategoryHtml);
		
		$('[data-toggle="confirmation"]').confirmation({popout:true});
	} else {
		$('#subCategoryTable').html('<tr><td colspan="4"><spring:message code="75.msg.none.found"/></td></tr>');
	}
}
</script>

<script id="subCategory-template" type="text/x-handlebars-template">
<tr id="category{{s}}">
	<td>
		<div class="toolbox">
			<span class="dropdown">
				<span class="glyphicon glyphicon-menu-hamburger editIcon" id="menu{{s}}" data-toggle="dropdown"></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="dropDown">
					<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:editCategory('{{s}}','s')"><span class="glyphicon glyphicon-edit"></span> <spring:message code="0.txt.edit"/></a></li>
					<li role="presentation"><a role="menuitem" tabIndex="-1" data-onConfirm="deleteCategory('{{s}}','s')" data-container="body" data-singleton="true" data-placement="top" data-toggle="confirmation" data-title="<spring:message code="75.msg.delete.warning"/>"><span class="glyphicon glyphicon-trash"></span> <spring:message code="0.txt.delete"/></a></li>
				</ul>
			</span>
		</div>
	</td>
	<td>{{name}}</td>
	<td>{{i18n}}</td>
	<td>{{{text}}}</td>
</tr>
</script>

<button type="button" class="btn btn-primary" onclick="addCategory('c')"><spring:message code="75.btn.1"/></button>
<div style="height:5px"></div>

<div class="table-responsive table-bordered">
	<table class="table table-hover table-condensed">
		<thead>
			<tr>
				<th><spring:message code="75.col.1"/></th>
				<th><spring:message code="75.col.2"/></th>
				<th><spring:message code="75.col.3"/></th>
				<th><spring:message code="75.col.4"/></th>
				<th><spring:message code="75.col.5"/></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${categories.size() == 0}">
				<tr>
					<td colspan="5"><spring:message code="75.msg.none.found"/></td>
				</tr>
			</c:if>
			
			<c:forEach items="${categories}" var="category">
				<tr>
					<td>
						<div class="toolbox" id="deleteContainer${category.categoryId}">
							<span class="dropdown">
								<span class="glyphicon glyphicon-menu-hamburger editIcon" id="menu'${category.categoryId}'" data-toggle="dropdown"></span>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dropDown">
									<li role="presentation"><a role="menuitem" tabIndex="-1" href="javascript:editCategory('${category.categoryId}','c')"><span class="glyphicon glyphicon-edit"></span> <spring:message code="0.txt.edit"/></a></li>
									<li role="presentation"><a role="menuitem" tabIndex="-1" data-onConfirm="deleteCategory('${category.categoryId}','c')" data-container="body" data-singleton="true" data-placement="top" data-toggle="confirmation" data-title="<spring:message code="75.msg.delete.warning"/>"><span class="glyphicon glyphicon-trash"></span> <spring:message code="0.txt.delete"/></a></li>
								</ul>
							</span>
						</div>
					</td>
					<td><a href="javascript:loadSubCategories('${category.categoryId}','${category.name}')">${category.name}</a></td>
					<td>${category.i18n}</td>
					<td><spring:message code="${category.i18n}"/></td>
					<td><spring:message code="${category.type.i18n}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div style="height:5px"></div>

<div class="col-xs-offset-1 col-xs-10">
	<div class="panel panel-default">
		<div class="panel-heading">
			<spring:message code="75.txt.subcategory"/><span id="subCategoryHeader"></span>
		</div>
		
		<div class="panel-body">
			<button type="button" class="btn btn-primary" onclick="addCategory('s')"><spring:message code="75.btn.2"/></button>
			<div style="height:5px"></div>
		
			<div class="table-responsive table-bordered">
				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th><spring:message code="75.col.1"/></th>
							<th><spring:message code="75.col.2"/></th>
							<th><spring:message code="75.col.3"/></th>
							<th><spring:message code="75.col.4"/></th>
						</tr>
					</thead>
					<tbody id="subCategoryTable">
						<tr>
							<td colspan="4"><spring:message code="75.msg.none.found"/></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<jsp:include page="category.jsp" flush="true" />