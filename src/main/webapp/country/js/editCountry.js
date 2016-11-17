var countryId = null;

$(document).ready(function() {
	assignAction();
});


function assignAction() {
	$("#btnReplaceCountry").click(function(e) {		
		 replaceCountry();
		 alert("megynyomtad a replacet");
		 
	});
}


function replaceCountry() {
	
	var country = getCountryParams();
	var method = "PUT";
	var url ="/tricast-2016-sportsbook/services/countries";
	
	
	  sendAjax(method, url, JSON.stringify(country),
		  function(data, textStatus, xhr ) {
		  	alert("Succesfully replaced");
		  	
	    }, function(xhr) {
	        var errormsg = getErrorMsg(xhr);
	        alert(errormsg);
	    });
}

function getCountryParams(){
	var country ={};
	country.id = $('#replaceCountryId').val();
	country.description = $('#replaceCountry').val();
	
	return country;
}
