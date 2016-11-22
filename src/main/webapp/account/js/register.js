//these global variables contains information about the selected objects
var accountId = null;
// this method runs every time when the page is reloading
$(document).ready(function() {

    // assign actions to links and buttons
    assignActions();
});

function assignActions() {
    // assign action to Home button
    $("#homeBtn").click(function(e) {
        window.location.href = "home.html";
    });

    // assign save account action
    $("#saveAccount").click(function(e) {
        e.preventDefault();
        saveAccount();
    });
}

// --------Account functions-------
function saveAccount() {
    var account = getAccountParams();

    var method = "PUT";
    var url = "/tricast-2016-sportsbook/services/accounts";
    if (account.id === null || account.id === undefined) {
        method = "POST";
    }
    sendAjax(method, url, JSON.stringify(account), function(data, textStatus, xhr) {
        $("#accountMsg").html("Succesfully saved");
    }, function(xhr) {
        $("#accountMsg").html(getErrorMsg(xhr));
    });
}

function getAccountParams() {
    var account = {};

    if (document.getElementById('Operator').checked) {
        account.type = "OPERATOR";
    } else {
        account.type = "PLAYER";
    }
    account.userName = $("#username").val();
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

    console.log(account);
    return account;
}
