//these global variables contains information about the sleected objects
var selectedHolidayId = null;
var loggedInAccount = null;
var holidays = [];

//this method runs every time when the page is reloading
$(document).ready(function(){
	//set the date variables for the fullcalendar library
	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();
	
	//create the calendar object
	createCalendar();
	//assign actions to links and buttons
	assignActions();	 
});


function assignActions() {
	//--------Account functions-------
	//assign login action 
	$("#loginSubmit").click(function(e) {
		//prevent the default form behaviour
		e.preventDefault();
		
		var user = $("#lgusername").val();
		var pwd = $("#lgpassword").val();		
		if (user == "" || pwd == "") {
			return;
		}
		login(user, pwd);		
	});
	
	//assign register action 
	$("#register").click(function(e) {
		e.preventDefault();
		//display registration view
		$("#cancelUpdAccount").hide();
		$("#registerBox").show();				
	});
	
	//assign update account action 
	$("#updateAccount").click(function(e) {	
		//modify the Registration view (change some labels, and display the account info) and display it
		$("#regTitle").html("Update account");				
		$("#username").val(loggedInAccount.userName);
		$("#realname").val(loggedInAccount.realName);
		$("#daysOffPerYear").val(loggedInAccount.daysOffPerYear);
		$("#sickLeavePerYear").val(loggedInAccount.sickLeavePerYear);		
		$("#saveAccount").text("Update account");
		$("#cancelUpdAccount").show();
		
		$("#registerBox").show();
	});
	
	//assign canceling account update action 
	$("#cancelUpdAccount").click(function(e) {		
		$("#registerBox").hide();
	});
	
	//assign create account action 
	$("#saveAccount").click(function(e) {
		e.preventDefault();
		saveAccount();			
	});
		
	//--------Holiday functions-------
	//assign save holiday action 
	$("#saveHoliday").click(function(e) {
		e.preventDefault();
		if($("#fromDate").val() == "" || $("#toDate").val() == ""){
			return;
		}		
		saveHoliday();	
	});
	
	//assign delete holiday action
	$("#deleteHoliday").click(function(e) {
		e.preventDefault();
		deleteHoliday();
	});
	
	//assign create new holiday action
	$("#newHoliday").click(function(e) {
		setHolidayParams();
	});
		
	//--------Calendar functions-------
	//assign actions to the calendar previous and next buttons
	$('.fc-button-prev').click(function(){
		setEvents(holidays);
	});
	$('.fc-button-next').click(function(){
		setEvents(holidays);
	});	
}

//--------Account functions-------
function login(user, pwd) {
	var url = "/days-off-calendar/services/accounts/login";
	var request = {};
	request.userName = user;
	request.password = pwd;
	sendAjax("POST", url, JSON.stringify(request), 
	 	function(data, textStatus, xhr ) {
			setAccountInfo(data);
			
			var url = "/days-off-calendar/services/holidays";
			sendAjax("GET", url, null, 
			 	function(data) {
					holidays = data;
					setEvents(data);
				}, null
			);			
			$("#loginMsg").html("");
			$(".fullpageBox").hide();
		},
		function(xhr) {
			$("#loginMsg").html(getErrorMsg(xhr));
		}
	);	
}

function saveAccount() {	
	var account = getAccountParams();
	
	var method = "PUT";
	var url = "/days-off-calendar/services/accounts";
	if(account.id === null || account.id === undefined){
		method = "POST";
	}
	sendAjax(method, url, JSON.stringify(account), 
	 	function(data, textStatus, xhr ) {
			login(account.userName, account.password);									
		},
		function(xhr) {
			$("#accountMsg").html(getErrorMsg(xhr));
		}
	);
}

function getAccountParams() {
	var account = {};	
	account.id = loggedInAccount == null ? null : loggedInAccount.id;
	account.userName =  $("#username").val();	
	account.password =  $("#password").val();
	account.realName =  $("#realname").val();
	account.daysOffPerYear =  $("#daysOffPerYear").val();
	account.sickLeavePerYear =  $("#sickLeavePerYear").val();
	
	console.log(account);
	return account;
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


//--------Holiday functions-------
function saveHoliday() {
	var holiday = getHolidayParams();
	
	var method = "PUT";
	var url = "/days-off-calendar/services/holidays";
	if(holiday.id === null || holiday.id === undefined){
		method = "POST";
	}
	sendAjax(method, url, JSON.stringify(holiday), 
	 	function(data, textStatus, xhr ) {
			selectedHolidayId = data.id;
			
			var url = "/days-off-calendar/services/holidays";
			sendAjax("GET", url, null, 
			 	function(data) {
					holidays = data;
					setEvents(data);
//					$("#saveInfo").text("Succesfully saved");
//					$("#saveInfo2").html("Succesfully saved");
//					$("#saveInfoDiv").addClass("alert-success");
//					$("#saveInfoDiv").removeClass("alert-danger");
					console.log("Saved");
				},
				function(xhr) {
//					$("#saveInfo").html(getErrorMsg(xhr));
//					$("#saveInfoDiv").removeClass("alert-success");
//					$("#saveInfoDiv").addClass("alert-danger");
//					$("#saveInfo2").html(getErrorMsg(xhr));
					console.log("Error");
				}
			);
			
			var url = "/days-off-calendar/services/accounts/" + loggedInAccount.id;
			sendAjax("GET", url, null, 
			 	function(data) {
				setAccountInfo(data);
				}, null
			);
			
//			$("#saveInfo").text("Succesfully saved");
//			$("#saveInfo2").html("Succesfully saved");
//			$("#saveInfoDiv").addClass("alert-success");
//			$("#saveInfoDiv").removeClass("alert-danger");
			console.log("Saved");
		},
		function(xhr) {
			$("#saveInfo").html(getErrorMsg(xhr));
			// $("#saveInfoDiv").removeClass("alert-success");
			// $("#saveInfoDiv").addClass("alert-danger");
		}
	);
}

function deleteHoliday() {
	var method = "DELETE";
	var url = "/days-off-calendar/services/holidays/" + selectedHolidayId;
	sendAjax(method, url, null, 
	 	function(data, textStatus, xhr ) {
			var url = "/days-off-calendar/services/holidays";
			sendAjax("GET", url, null, 
			 	function(data) {
					holidays = data;
					setEvents(data);
					setHolidayParams(null);
					$("#saveInfo").html("Succesfully deleted");
				},
				function(xhr) {
					$("#saveInfo").html(getErrorMsg(xhr));
				}
			);			
		},
		function(xhr) {
			$("#saveInfo").html(getErrorMsg(xhr));
		}
	);
}

function setHolidayParams(event) {
	console.log(event);
	if(event === null || event === undefined){
		selectedHolidayId = null;
		$("#holidayId").val("");
		$("#fromDate").val("");
		$("#toDate").val("");
		$("#holidayType").val("");
		$("#fromDate").prop("disabled", false);
		$("#toDate").prop("disabled", false);
	} else {
		selectedHolidayId = event.id;
		var dateFormat = "YYYY-MM-DD";
		$("#holidayId").val(event.id);
		$("#fromDate").val(moment(event.start).format(dateFormat));
		if(event.end != null){
			$("#toDate").val(moment(event.end).format(dateFormat));	
		} else {
			$("#toDate").val(moment(event.start).format(dateFormat));
		}
		$("#holidayType").val(event.type);	
		$("#fromDate").prop("disabled", true);
		$("#toDate").prop("disabled", true);
	}	
}

function getHolidayParams() {
	var holiday = {};
	
	holiday.id = selectedHolidayId;
	holiday.accountId = loggedInAccount.id;
	var dateFormat = "YYYYMMDD";
		
	holiday.fromDay = moment($("#fromDate").val()).format(dateFormat);
	holiday.toDay = moment($("#toDate").val()).format(dateFormat);
	holiday.type = $("#holidayType").val();	
	
	console.log(holiday);
	return holiday;
}


//--------Calendar functions-------
//function createCalendar() {	
//	$('#calendar').fullCalendar({
//		eventClick: function(event) {
//			setHolidayParams(event);
//		},
//	    dayClick: function() {
//           alert('a day has been click');
//        },
//		editable: false,
//	    weekMode: 'liquid',
//	    url:'#',
//	    events: []
//	});
//	//$('#calendar').find('.fc-header-center').text('Holidays');
//	
//	$('#calendar').fullCalendar('today');
//	$('#calendar').fullCalendar('render');
//}

function createCalendar() {	
	$('#calendar').fullCalendar({
		eventClick: function(event) {
			setHolidayParams(event);
		},
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek'
		}
	});
	
}

function setEvents(holidayList){
	var eventList = [];
	$('#calendar').fullCalendar('removeEvents');
	for(var i = 0; i<holidayList.length; i++){
		
		var holiday = holidayList[i];
		var event = {};
		event.id = holiday.id;
		var title = holiday.accountRealName === undefined ? loggedInAccount.realName : holiday.accountRealName;
		event.title = title;
		event.allDay = true;
		event.start = getDateFromString(holiday.fromDay);
		var eventToDay = Number(holiday.toDay)+1;
		event.end = getDateFromString(eventToDay.toString());
		event.type = holiday.type;
		if(event.type == "SICKLEAVE"){
			event.color = "red";	
		}
				
		console.log(event);
		eventList[i] = event;
		
		$('#calendar').fullCalendar( 'renderEvent', event );	
	}
}

function getDateFromString(dayString){
	var year = dayString.substring(0, 4);
	var month = dayString.substring(4, 6);
	month = month - 1;
	var day = dayString.substring(6, 8);
	var date = new Date(year, month, day, 0, 0, 0, 0);
    return date;
}


