//this method runs every time when the page is reloading
var accountId = null;
$(document).ready(function() {
	var url = document.URL;
	accountId = url.split("id=")[1];
	if(accountId !== null){
		displayAccountInfo();	 
	}
	assignActions();
	
});

function assignActions() {
	
	$("#updateBtn").click(function(e) {
        e.preventDefault();

        if(accountId !== null){
        	window.location.href = "/tricast-2016-sportsbook/account/changeinfo.html?id=" + accountId;	 
    	}
        
    });
	
	$("#betBtn").click(function(e) {
		e.preventDefault();
		
		window.location.href = "/tricast-2016-sportsbook/events/events.html?account=" + accountId;
	});	
}

function displayAccountInfo() {
	var url = "/tricast-2016-sportsbook/services/accounts/"+accountId;
	sendAjax("GET", url, null, 
	 	function(data) {
			$("#welcomeUser").html("Hi "+ data.firstName + "!");
		}, null
	);	
}

function goToChangeAccountInfo() {

}
function goToTransaction() {

}

function goToEventList() {
    window.location.href = "/tricast-2016-sportsbook/events/events.html";
}

function goToAccountHistory() {

}