//this method runs every time when the page is reloading
$(document).ready(function() {
    assignAction();
});

function assignAction() {
    var countries = getAllCountries();

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



