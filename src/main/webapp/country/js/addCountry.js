var countryId = null;

$(document).ready(function() {
    assignAction();
});


function assignAction() {
	$("#btnAddCountry").click(function(e) {
    addCountry();
	});
}

function addCountry() {
	var country = getCountryParams();
	var method = "POST";
	var url ="/tricast-2016-sportsbook/services/countries";
	
	  sendAjax(method, url, JSON.stringify(country),
		  function(data, textStatus, xhr ) {
		  	alert("Succesfully saved");
	
	    }, function(xhr) {
	        var errormsg = getErrorMsg(xhr);
	        alert(errormsg);
	    });
}

function getCountryParams(){
	var country ={};
	country.id = countryId;
	country.description = $('#addCountryId').val();
	
	return country;
}

