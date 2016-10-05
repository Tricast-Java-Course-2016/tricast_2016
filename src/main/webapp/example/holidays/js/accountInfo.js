//these global variables contains information about the selected objects
var accountId = null;

//this method runs every time when the page is reloading
$(document).ready(function(){
	//get the accountId from the url
	var url = document.URL;
	accountId = url.split("?")[1];
	//if the accountId is not null, then load the account info from the server
	if(accountId !== null){
		displayAccountInfo();	 
	}
});

//--------Account functions-------
function displayAccountInfo() {
	var url = "/days-off-calendar/services/accounts/"+accountId;
	sendAjax("GET", url, null, 
	 	function(data) {
			setAccountInfo(data);
		}, null
	);	
}
function setAccountInfo(account){
	loggedInAccount = account;
	$("#welcomeMsg").html("Hi "+ account.realName + "!");
	$("#holidayMax").html(account.daysOffPerYear);
	var holidayUsed = account.daysOffPerYear - account.holidaysLeft; 
	$("#holidayUsed").html(holidayUsed);
	$("#holidayRemain").html(account.holidaysLeft);
	$("#sickMax").html(account.sickLeavePerYear);
	var sickUsed = account.sickLeavePerYear - account.sickLeavesLeft;
	$("#sickUsed").html(sickUsed);
	$("#sickRemain").html(account.sickLeavesLeft);	
}
