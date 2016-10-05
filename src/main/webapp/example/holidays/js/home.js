//this method runs every time when the page is reloading
$(document).ready(function(){
	
	//assign actions to links and buttons
	assignActions();	 
});


function assignActions() {
	//--------Navigation----------
	$("#openHolidays").click(function(e) {
		window.location.href = "holidayList.html";
	});	
}
