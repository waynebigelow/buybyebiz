<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="paging" uri="/WEB-INF/paging.tld"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ca.app.model.common.CountryType"%>
<%@ page import="ca.app.model.common.ProvinceType"%>

<style type="text/css">
.no-margins {
	margin: 0px 0px 0px 0px;
}
.sold{
	position:absolute;
	z-index:1;
	left:25%;
	top:0px;
}
</style>

<script type="text/javascript" src="${pageContext.request.contextPath}/layouts/version1/3rdparty/jquery/chainedRemote/js/jquery.chained.remote.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	$('#accordion').on('hidden.bs.collapse', toggleChevron);
	$('#accordion').on('shown.bs.collapse', toggleChevron);
	
	$('#subCategory').remoteChained({
		parents:'#category',
		url:'${pageContext.request.contextPath}/common/listSubCategories.json?c=1'
	});
	
	$('#province').remoteChained({
		parents:'#country',
		url:'${pageContext.request.contextPath}/common/listProvincesOrStates.json?c=1&type='
	});
	
	buildSelect("${pageContext.request.contextPath}/common/listCategories.json", "category", "${listings.getString('categoryId', '0')}");
	buildSelect("${pageContext.request.contextPath}/common/listSubCategories.json?category=${listings.getString('categoryId', '0')}", "subCategory", "${listings.getString('subCategoryId', '0')}");
	
	var country = "${listings.getString('country', '')}";
	if (country == "") {
		country = "<%=CountryType.CA.getShortName()%>";
	}
	buildSelect("${pageContext.request.contextPath}/common/listProvincesOrStates.json?country=" + country, "province", "${listings.getString('province', '')}");
	buildSelect("${pageContext.request.contextPath}/common/listCountries.json", "country", country);
});

function handleSearch() {
	$('#searchForm').submit();
}

function resetForm() {
	$('#category').val('');
	$('#subCategory').val('');
	$('#minPrice').val('');
	$('#maxPrice').val('');
	$('#country').val('<%=CountryType.CA.getShortName()%>');
	$('#province').val('<%=ProvinceType.ON.getShortName()%>');
	buildSelect("${pageContext.request.contextPath}/common/listProvincesOrStates.json?country=<%=CountryType.CA.getLongName()%>", "province", "<%=ProvinceType.ON.getShortName()%>");
	$('#searchForm').submit();
}
</script>

<div class="container search-container">
	<form id="searchForm" name="searchForm" action="${pageContext.request.contextPath}/search.html" method="get" class="form-horizontal" role="form">
		<div class="row">
			<div class="col-xs-12">
				
				<div class="col-xs-12">
					<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
						<div class="panel panel-default">
							<div class="panel-heading" role="tab" id="searchHeading" style="background-color:#96a5b3;color:#ffffff">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion" href="#searchPanel" aria-expanded="true" aria-controls="searchPanel">
										<i class="indicator glyphicon glyphicon-chevron-up"></i>
										<spring:message code="0.txt.criteria"/>
									</a>
								</h4>
							</div>
							
							<div id="searchPanel" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="searchHeading">
								<div class="panel-body">
									<div class="col-xs-12 col-sm-4">
										<div class="form-group">
											<label for="category" class="sr-only"><spring:message code="20.lbl.category"/>:</label>
											<div class="col-xs-12">
												<select id="category" name="category" class="form-control">
												</select>
											</div>
										</div>
										
										<div class="form-group">
											<label for="subCategory" class="sr-only"><spring:message code="20.lbl.sub.category"/>:</label>
											<div class="col-xs-12">
												<select id="subCategory" name="subCategory" class="form-control">
												</select>
											</div>
										</div>
									</div>
											
									<div class="col-xs-12 col-sm-4">
										<div class="form-group">
											<label for="minPrice"class="sr-only"><spring:message code="115.lbl.min.price"/>:</label>
											<div class="col-xs-12">
												<input id="minPrice" name="minPrice" type="number" value="${listings.getString('minPrice', '')}" class="form-control" placeholder="--<spring:message code="115.lbl.min.price"/>--" 
													data-fv-integer-message="<spring:message code="0.msg.invalid.number.warning"/>" />
											</div>
										</div>
										
										<div class="form-group">
											<label for="maxPrice" class="sr-only"><spring:message code="115.lbl.max.price"/>:</label>
											<div class="col-xs-12">
												<input id="maxPrice" name="maxPrice" type="number" value="${listings.getString('maxPrice', '')}" class="form-control" placeholder="--<spring:message code="115.lbl.max.price"/>--" 
													data-fv-integer-message="<spring:message code="0.msg.invalid.number.warning"/>" />
											</div>
										</div>
									</div>
										
									<div class="col-xs-12 col-sm-4">
										<div class="form-group">
											<label for="country" class="sr-only"><spring:message code="25.lbl.country"/>:</label>
											<div class="col-xs-12">
												<select id="country" name="country" class="form-control">
												</select>
											</div>
										</div>
									
										<div class="form-group">
											<label for="province" class="sr-only"><spring:message code="25.lbl.province"/>:</label>
											<div class="col-xs-12">
												<select id="province" name="province" class="form-control">
												</select>
											</div>
										</div>
									</div>
										
									<div class="text-center-xs">
										<button type="button" class="btn btn-primary" onclick="handleSearch()"><spring:message code="0.txt.search"/></button>
										<button type="button" class="btn btn-default" onclick="resetForm()"><spring:message code="0.txt.reset"/></button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-4">
					<div class="panel panel-default">
						<div class="panel-heading" style="background-color:#96a5b3;color:#ffffff">
							<spring:message code="115.header.search.results"/>:&nbsp;<paging:info page="${listings}" label="0.txt.count" />
						</div>
				
						<div class="panel-body">
							<c:if test="${not empty listings.items}">
								<div class="row">
									<c:if test="${listings.limit > 0 && listings.total > listings.limit}">
										<div class="col-xs-6 col-sm-12 col-lg-6" style="padding-right:0px">
											<div>
												<ul class="pagination pagination-sm no-margins">
													<paging:previous page="${listings}" url="${pageContext.request.contextPath}/search.html?1=1" />
													<paging:indexes page="${listings}" url="${pageContext.request.contextPath}/search.html?1=1" numIndexes="2" />
													<paging:next page="${listings}" url="${pageContext.request.contextPath}/search.html?1=1" />
												</ul>
											</div>
										</div>
									</c:if>
									
									<div class="col-xs-6 col-sm-12 col-lg-6" style="padding-right:0px">
										<div style="margin:5px;">
											Price:&nbsp;
											<select name="dir" id="dir" style="width:100px" onchange="handleSearch()">
												<option value="asc" <c:if test="${listings.dir eq 'asc'}">selected="selected"</c:if>>Ascending</option>
												<option value="desc" <c:if test="${listings.dir eq 'desc'}">selected="selected"</c:if>>Descending</option>
											</select>
										</div>
									</div>
								</div>
								
								<div style="height:425px;overflow:auto;">
									<c:forEach items="${listings.items}" var="listing">
										<div class="panel panel-default">
											<div class="panel-body" style="position:relative">
												<c:if test="${listing.sold}">
													<img class="sold" src="${pageContext.request.contextPath}/layouts/version1/images/sold.png" />
												</c:if>
											
												<a href="${pageContext.request.contextPath}/${listing.listingURIFormatted}">${listing.title}</a><br/>
												${listing.priceFormatted}
												<c:if test="${not empty listing.themePhoto}">
													<img class="img-responsive" width="100%" src="${pageContext.request.contextPath}/${listing.photoPath}/${listing.themePhoto}" />
												</c:if>
											</div>
										</div>
									</c:forEach>
								</div>
							</c:if>
							
							<c:if test="${empty listings.items}">
								<spring:message code="115.msg.none.found"/>
							</c:if>
						</div>
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-8">
					<div class="panel panel-default">
						<div class="panel-body">
							<jsp:include page="../common/map.jsp" flush="true"/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>