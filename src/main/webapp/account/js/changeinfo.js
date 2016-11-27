//these global variables contains information about the selected objects
var accountId = null;
// this method runs every time when the page is reloading
$(document).ready(function() {
	logInCheck();
	
    // get the accountId from the url
    var url = document.URL;
    accountId = url.split("id=")[1];
    // if the accountId is not null, then load the account info from the server
    if (accountId !== null) {
        displayAccountInfo();
        assignActions();
    }
});

function assignActions() {
    // assign action to Home button
    $("#homeBtn").click(function(e) {
        window.history.back();
    });

    // assign save account action
    $("#updateAccount").click(function(e) {
        e.preventDefault();
        saveAccount();
    });
}

// --------Account functions-------
function saveAccount() {
    var account = getAccountParams();

    var method = "PUT";
    var url = "/tricast-2016-sportsbook/services/accounts";
    sendAjax(method, url, JSON.stringify(account), function(data, textStatus, xhr) {
        $("#accountMsg").html("Succesfully saved");
    }, function(xhr) {
        $("#accountMsg").html(getErrorMsg(xhr));
    });
}

function displayAccountInfo() {
    var url = "/tricast-2016-sportsbook/services/accounts/" + accountId;
    sendAjax("GET", url, null, function(data) {
        setAccountInfo(data);
    }, null);
}

function setAccountInfo(account) {
    loggedInAccount = account;
    document.getElementById('password').value = account.password;
    document.getElementById('firstname').value = account.firstName;
    document.getElementById('lastname').value = account.lastName;
    document.getElementById('dob').value = account.dob;
    document.getElementById('address').value = account.address;
    document.getElementById('emailaddress').value = account.emailAddress;
    document.getElementById('phonenumber').value = account.phoneNumber;
    document.getElementById('pin').value = account.pin;
    document.getElementById('bankaccountnumber').value = account.bankAccountNumber;
    document.getElementById('bankcardnumber').value = account.bankCardNumber;
}

function getAccountParams() {
    var account = {};

    account.password = $("#password").val();
    account.firstName = $("#firstname").val();
    account.lastName = $("#lastname").val();
    account.dob = $("#dob").val();
    account.address = $("#address").val();
    account.emailAddress = $("#emailaddress").val();
    account.phoneNumber = $("#phonenumber").val();
    account.pin = $("#pin").val();
    account.bankAccountNumber = $("#bankaccountnumber").val();
    account.bankCardNumber = $("#bankcardnumber").val();
    account.id = accountId;

    console.log(account);
    return account;
}
