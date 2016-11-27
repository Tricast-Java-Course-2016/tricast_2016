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

    $("#registerBtn").click(function(e) {
        e.preventDefault();
        window.location.replace('register.html');
    });
}

function login(username, password) {

    var url = "/tricast-2016-sportsbook/services/accounts/login";

    var req = {};
    req.userName = username;
    req.password = password;

    sendAjax("POST", url, JSON.stringify(req), function(data, textStatus, xhr) {
    	
    	// get the token from the header
    	var token = xhr.getResponseHeader('Authorization');
    	// save it in the session storage
		sessionStorage.setItem('Token', token);
		
        if (data.type === "OPERATOR") {
            window.location.href = "/tricast-2016-sportsbook/account/operatorhome.html?id=" + data.id;
        } else {
            window.location.href = "/tricast-2016-sportsbook/account/playerhome.html?id=" + data.id;
        }
        $("#userPlacholder").html("Welcome " + data.firstName);
    }, null);

}