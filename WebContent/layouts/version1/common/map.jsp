<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
#mapCanvas {
	height:${site.mapData.height}px;
	width:100%;
}
</style>

<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBSqfCiUS5NUh3eV1832n8eiY_rFQt6J-k&callback=initialize" async defer></script>

<script type="text/javascript">
function initialize() {
	var mapCanvas = document.getElementById('mapCanvas');
	var mapOptions = {
		mapTypeId:google.maps.MapTypeId.ROADMAP
	};
	
	var map = new google.maps.Map(mapCanvas, mapOptions);
	var geocoder = new google.maps.Geocoder();

	<c:if test="${empty listings.items}">
		var province = "${listings.getString('province', '')}";
		var country = "${listings.getString('countryLongName', '')}";
		var defaultAddress = province;
		var zoom = ${listings.getString('zoom', '')};
		
		if (province == "") {
			defaultAddress = country;
		} else {
			defaultAddress = province + "," + country;
		}
		
		geocoder.geocode({'address':defaultAddress}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				var latLng = new google.maps.LatLng(results[0].geometry.location.lat(), results[0].geometry.location.lng());
				bounds.extend(latLng);
				map.fitBounds(bounds);
				map.setZoom(zoom);
			}
		});
	</c:if>
	
	var bounds = new google.maps.LatLngBounds();
	var markerCount = 0;
	<c:forEach items="${listings.items}" var="listing">
	geocoder.geocode({'address':"${listing.address.formattedString}"}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			var latLng = new google.maps.LatLng(results[0].geometry.location.lat(), results[0].geometry.location.lng());
			bounds.extend(latLng);
			
			var marker = new google.maps.Marker({
				position:latLng,
				map:map,
				title:"${listing.title}"
			});

			var info = new google.maps.InfoWindow();
			info.setContent("<div><a href='${pageContext.request.contextPath}/${listing.listingURIFormatted}'>${listing.title}</a></div><div>${listing.address.formattedString}</div>");

			google.maps.event.addListener(marker, 'click', function() {
				info.open(map, marker);
			});
			
			marker.addListener('mouseover', function() {
				console.log('mouseover');
				//info.open(map, this);
			});
			
			map.fitBounds(bounds);
			
			if (markerCount == 0) {
				var zoom = ${listing.address.zoomForAddress};
				map.setZoom(zoom);
				markerCount++;
			}
		} else {
			console.log('not good');
		}
	});
	</c:forEach>
}
</script>

<div id="mapCanvas" style="height:${mapHeight}"></div>