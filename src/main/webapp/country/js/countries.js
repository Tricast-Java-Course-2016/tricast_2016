//this method runs every time when the page is reloading
var countryId = null;
$(document).ready(function() {
	assignAction();
});

function assignAction() {
	var countries = getAllCountries();
	var addCountry = addCountries();

	deleteButtonMove();
	editCountry();
	
}

function editCountry(){
	var countryD ={};
	$('table').on('click','#btnEditCountry',function() {		
		 $('#EditCountryModal').modal('show');

		 	//var ezazid= ($(this).parents("tr").find("td:first").text());
		 countryId= ($(this).parents("tr").find("td:first").text());
		 countryD= $('#editTextarea').val($(this).parents("tr").find("td:first").next().text()).val();
		 
		 	$("#btnSaveEditCountry").click(function(e) {

		 		//alert($('#inputEditCountry').val()+" "+countryId+" "+countryD);	
		 		if($('#inputEditCountry').val()==""){
		 			alert("No input data");
		 		}else{
		 		
		 		
		 		var country=getAbcParams();
		 		var method = "PUT";
		 		var url ="/tricast-2016-sportsbook/services/countries";
		 		
		 		  sendAjax(method, url, JSON.stringify(country),
		 			  function(data, textStatus, xhr ) {
		 			  	alert("Succesfully replaced");
		 			  	
		 		    }, function(xhr) {
		 		        var errormsg = getErrorMsg(xhr);
		 		        alert(errormsg);
		 		    });

		 		
		 		location.reload();
		 		}
		 	});
	});
	
}

function getAbcParams(){
		
	//alert("idebejövök?");
		var country={};
		country.id=countryId
		country.description=$('#inputEditCountry').val();
	
		return country;
	
	}


function addCountry() {
	var addcountry = getCountryParams();
	var method = "POST";
	var url = "/tricast-2016-sportsbook/services/countries";

	sendAjax(method, url, JSON.stringify(addcountry), function(data,
			textStatus, xhr) {
		alert("Succesfully saved");
		location.reload();

	}, function(xhr) {
		var errormsg = getErrorMsg(xhr);
		alert(errormsg);
	});
}

function getCountryParams() {
	var addcountry = {};
	addcountry.id = countryId;

	addcountry.description = $('#inputCountry').val();

	return addcountry;
}
function addCountries() {

	$("#btnAddCountry").click(function(e) {
		if ($('#inputCountry').val() == "") {
			alert("No input data!");
		} else {
			addCountry();
		}
	});
}

function getAllCountries() {

	var url = "/tricast-2016-sportsbook/services/countries";
	var countries = new Map();

	sendAjax(
			"GET",
			url,
			null,
			function(data, textStatus, xhr) {
				for (var i = 0; i < data.length; i++) {
					countries.set(data[i].id, data[i].description);
					$('#countryTable > tbody:last-child')
							.append(
									'<tr><td>'
											+ data[i].id
											+ '</td><td>'
											+ data[i].description
											+ '</td><td>  <button  type="button" class="btn btn-primary" id ="btnEditCountry">Edit</button>'
											+ '</td><td>  <button  type="button" class="btn btn-primary" id="btnDelete"  >Delete</button>'
											+ '</td></tr>');
				}

			}, function(xhr) {
				var errormsg = getErrorMsg(xhr);
				alert(errormsg);
			});

	return countries;
}



function deleteButtonMove() {
	$('table').on('click','#btnDelete',function() {

		// alert($(this).closest('td').parent()[0].sectionRowIndex);
		// sorindex
		var ezaz= ($(this).parents("tr").find("td:first").text());

		var countryDelete;
		countryDelete = {};
		countryDelete.id = ($(this).parents("tr").find("td:first")
				.text());

		var method = "DELETE";
		var url = "/tricast-2016-sportsbook/services/countries/"+ezaz;

		sendAjax(method, url, function(
				data, textStatus, xhr) {
			alert("Succesfully deleted");
			location.reload();

		}, function(xhr) {
			var errormsg = getErrorMsg(xhr);
			alert(errormsg);
		});

	});
	
}
