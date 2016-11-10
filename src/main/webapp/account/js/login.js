//this method runs every time when the page is reloading
$(document).ready(function() {
    assignAction();
});

function assignAction() {

    $("#submitBtn").click(function(e) {
        e.preventDefault();

        var usrname = $("#loginUsername").val();
        var passwd = $("#loginPassword").val();

        login(usrname, passwd);
    });
}

function login(username, password) {

    var url = "/tricast-2016-sportsbook/services/accounts/login";

    var req = {};
    req.userName = username;
    req.password = password;

    sendAjax("POST", url, JSON.stringify(req), function(data, textStatus, xhr) {
        $("#userPlacholder").html("HI " + data.firstName);
//        window.location.href = "account/playerhome.html";
//        window.location.href = "account/operatorhome.html";
    }, null);

}