var holidayId = null;

//this method runs every time when the page is reloading
$(document).ready(function(){	
	//get the holidayId from the url
	var url = document.URL;
	if(url.indexOf("?") > -1) {
		holidayId = url.split("?")[1];	
	}
	//if the holidayId is not null, then load the holiday fields from the server
	if(holidayId !== null){
		getHoliday();
	}
});

function getHoliday() {
	var url = "/days-off-calendar/services/holidays/" + holidayId;
	sendAjax("GET", url, null, 
	 	function(data) {
			setHolidayParams(data);
		}, null
	);
}

function setHolidayParams(holiday) {
	var dateFormat = "YYYY-MM-DD";
	$("#holidayId").val(holiday.id);
	$("#fromDate").val(moment(holiday.start).format(dateFormat));
	$("#toDate").val(moment(holiday.end).format(dateFormat));	
	$("#holidayType").val(holiday.type);		
}
