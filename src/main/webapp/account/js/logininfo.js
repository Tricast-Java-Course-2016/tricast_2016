//this method runs every time when the page is reloading
$(document).ready(function() {
    assignAction();
});

function assignAction() {




function login(username, password) {

    var url = "/tricast-2016-sportsbook/account/operatorhome.html";

    var req = {};
    req.userName = username;
    req.password = password;

    sendAjax("POST", url, JSON.stringify(req), function(data, textStatus, xhr) {
        if (data.type === "OPERATOR") {
            window.location.href = "/tricast-2016-sportsbook/account/operatorhome.html?id=" + data.id;
        } else {
            window.location.href = "/tricast-2016-sportsbook/account/playerhome.html?id=" + data.id;
        }
        $("#userPlacholder").html("Welcome " + data.firstName);
    }, null);

}