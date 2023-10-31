//This can be called for any type of bootstrap related alert (info, success, warning, danger)
//You must also specify a size (sm, md, lg)
//If you include a timeout then the alert will fade out, if you don't then it'll require a click to close
//This relies on a <div id="alert">...</div> structure in template.jsp
function showAlert(type, size, message, timeout) {
	$('#alert').attr("class", "newClass");
	$('#alert').addClass('alert alert-'+type+' alert-dismissible');
	
	$('#alertDialog').attr("class", "newClass");
	$('#alertDialog').addClass('modal-dialog modal-' + size);
	
	$('#alertMessage').html(message);
	$('#alertModal').modal({
		show:true,
		backdrop:false
	});
	
	if (timeout){
		setTimeout(function() {$('#alertModal').modal('hide');}, timeout);
	}
}

// Given a jQuery selector that identifies a set of select tags, this
// method calls the given url and iterates over the json response to
// inject options into the select boxes. It assumes that the ajax 
// response contains an array of objects that have id and name attributes
// (i.e. our OptionsDTO)
function initSelectBoxOptions(inputSelector, url, idAttr, nameAttr) {
	idAttr = (idAttr==null) ? 'id' : idAttr;
	nameAttr = (nameAttr==null) ? 'name' : nameAttr;
	
	$.ajax({
		url : url,
		type: 'get',
		success: function(data, textStatus, jqXHR) {
			if (data.rows!=null) {
				$.each(data.rows, function (i, item) {
					$(inputSelector).append($('<option>', { 
						value: item[idAttr],
						text : item[nameAttr]
					}));
				});
			}
		}
	});
}

//This function will toggle the chevron up and down for accordions requiring
//that feedback
//Two event handlers must be initialized within the onready of the jsp:	
//$('#accordion').on('hidden.bs.collapse', toggleChevron);
//$('#accordion').on('shown.bs.collapse', toggleChevron);
//As well, use the following where the chevron resides within the accordion:
//<i class="indicator glyphicon glyphicon-chevron-up pull-right"></i>
function toggleChevron(e) {
	$(e.target)
	.prev('.panel-heading')
	.find("i.indicator")
	.toggleClass('glyphicon-chevron-down glyphicon-chevron-up');
}

function inputLimiter(e,allow) {
	var AllowableCharacters = '';
	
	if (allow == 'Letters'){AllowableCharacters='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';}
	if (allow == 'Numbers'){AllowableCharacters='1234567890';}
	if (allow == 'URIs'){AllowableCharacters='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890-_';}

	var k = document.all?parseInt(e.keyCode): parseInt(e.which);
	if (k!=13 && k!=8 && k!=0){
		if ((e.ctrlKey==false) && (e.altKey==false)) {
			return (AllowableCharacters.indexOf(String.fromCharCode(k))!=-1);
		} else {
			return true;
		}
	} else {
		return true;
	}
}

function buildSelect(method, fieldName, compareVal){
	$.getJSON(
		method,
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