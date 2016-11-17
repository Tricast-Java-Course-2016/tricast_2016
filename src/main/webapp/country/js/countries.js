//this method runs every time when the page is reloading

var countryId = null;
$(document).ready(function() {
    assignAction();
});

function assignAction() {
    var countries = getAllCountries();
    $("#btnAddCountry").click( function(e) {
    	addCountry();
    } );
}

function addCountry() {
	var addcountry = getCountryParams();
	var method = "POST";
	var url ="/tricast-2016-sportsbook/services/countries";
	
	  sendAjax(method, url, JSON.stringify(addcountry),
		  function(data, textStatus, xhr ) {
		  	alert("Succesfully saved");
	
	    }, function(xhr) {
	        var errormsg = getErrorMsg(xhr);
	        alert(errormsg);
	    });
}

function getAllCountries() {

    var url = "/tricast-2016-sportsbook/services/countries";
    var countries = new Map();

    sendAjax("GET", url, null, function(data, textStatus, xhr) {
        for (var i = 0; i < data.length; i++) {
            countries.set(data[i].id, data[i].description);
            
            $('#countryTable > tbody:last-child').append(
                    '<tr><td>' +data[i].id+
                    '</td><td>' +data[i].description+
            		'</td></tr>');
        }

    }, function(xhr) {
        var errormsg = getErrorMsg(xhr);
        alert(errormsg);
    });

    return countries;
}

function getCountryParams(){
	var addcountry ={};
	addcountry.id = countryId;
	addcountry.description = $('#inputCountry').val();
	
	return addcountry;
}

function goToEditCountry() {
	window.location.href = "/tricast-2016-sportsbook/country/editCountry.html";
}




