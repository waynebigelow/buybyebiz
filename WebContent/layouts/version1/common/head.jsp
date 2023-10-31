<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="str" uri="http://jakarta.apache.org/taglibs/string-1.1" %>

<title>${metaPageSettings.pageTitle}</title>
<link rel="${application.name} icon" type="image/x-icon" href="${pageContext.request.contextPath}/buybyemedia/app/buybyebiz/images/bbb.ico">
<c:if test="${not empty listing}">
<link rel="canonical" href="${listing.secureApplicationURL}/${listing.listingURI}" />
</c:if>

<meta charset="utf-8">
<meta name="pragma" content="no-cache">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="<str:truncateNicely lower="145" upper="155">${metaPageSettings.metaDescription}</str:truncateNicely>">
<meta name="robots" content="noarchive,index,follow">
<meta name="author" content="${metaPageSettings.metaAuthor}">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta property="og:type" content="website"/>
<meta property="og:url" content="${application.secureApplicationURL}/${site.listing.listingURIFormatted}"/>
<meta property="og:description" content="${metaPageSettings.ogDescription}"/>
<meta property="og:title" content="${metaPageSettings.ogTitle}"/>
<meta property="og:image:secure_url" content="${metaPageSettings.ogImage}"/>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING:Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->