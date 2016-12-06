//this method runs every time when the page is reloading
var accountId = null;
$(document).ready(function() {
	logInCheck();
	
    var url = document.URL;
    accountId = url.split("id=")[1];

    assignActions();

});

function assignActions() {
	if (accountId !== undefined && accountId !== null && accountId !== "null") {
		var transactions = displayTransactions();
	}
}

function displayTransactions() {
    var url = "/tricast-2016-sportsbook/services/transactions/account/" + accountId;

    sendAjax("GET", url, null, function(data) {
        for (var i = 0; i < data.length; i++) {        		

                $('#transactionTable > tbody:last-child').append(
                		'<tr id="'  + data[i].id +
                        '"><td>'    + data[i].createdDate +
                        '</td><td>' + data[i].description + 
                        '</td><td>' + data[i].amount + '</td></tr>');		            	
        }
    }, function(xhr) {
        var errormsg = getErrorMsg(xhr);
        alert(errormsg);
    });
}