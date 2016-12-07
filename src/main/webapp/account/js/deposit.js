//these global variables contains information about the selected objects
var accountId = null;
var bankCardNumber = null;
var pin = null;
// this method runs every time when the page is reloading
$(document).ready(function() {
	logInCheck();
	
    // get the accountId from the url
    var url = document.URL;
    accountId = url.split("id=")[1];
    // if the accountId is not null, then load the account info from the server
    if (accountId !== null) {
    	getCardInfo();
        assignActions();
    }
});

function assignActions() {
    // assign action to Home button
    $("#homeBtn").click(function(e) {
        window.history.back();
    });

    // assign save account action
    $("#applyBtn").click(function(e) {
        e.preventDefault();
        saveDeposit();
    });
}

// --------Account functions-------
function saveDeposit() {
    var transaction = {};
    
    transaction.accountId = accountId;
    transaction.betId = null;
    transaction.description = "Pay in " + $("#amount").val() + " HUF"; 
    transaction.amount = $("#amount").val();
   
    if(($("#bankcardnumber").val() === bankCardNumber) && ($("#pin").val() === pin)){
        var method = "POST";
        var url = "/tricast-2016-sportsbook/services/transactions/";
        sendAjax(method, url, JSON.stringify(transaction), function(data, textStatus, xhr) {
            $("#depositMsg").html("Succesfully saved");
        }, function(xhr) {
            $("#depositMsg").html(getErrorMsg(xhr));
        });	
    } else {
    	alert("Hibás bankkártya adatok!");
    }
}

function getCardInfo() {
    var url = "/tricast-2016-sportsbook/services/accounts/" + accountId;
    sendAjax("GET", url, null, function(data) {
    	setBankCardInfo(data);
    }, function(xhr) {
       alert(getErrorMsg(xhr));
    });
}

function setBankCardInfo(account) {
	loggedInAccount = account;
	bankCardNumber = account.bankCardNumber;
	pin = account.pin;
}