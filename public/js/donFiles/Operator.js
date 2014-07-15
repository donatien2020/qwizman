
$(function() {
	$("#teachersDialog").dialog({
		overlay : {
			backgroundColor : "#000",
			opacity : 0.5
		},
		position : {
			my : "left+100",
			at : "left top+200",
			of : window
		},
		title : 'Teacher-Class-Course Maping Form',
		autoOpen : false,
		closeOnEscape : true,
		height : 'auto',
		width : 1000,
		modal : true,
		draggable : true,
		resizable : true,
		buttons : {
			Cancel : function() {
				$(this).dialog("close");
			}
		},
		close : function() {
			$(this).dialog("close");
		}
	}).dialogExtend({
		minimizable : true,
		collapsable : true
	});
	$("#studentsDialog").dialog({
		overlay : {
			backgroundColor : "#000",
			opacity : 0.5
		},
		position : {
			my : "left+100",
			at : "left top+200",
			of : window
		},
		title : 'Student-Class Maping Form',
		autoOpen : false,
		closeOnEscape : true,
		height : 'auto',
		width : 1000,
		modal : true,
		draggable : true,
		resizable : true,
		buttons : {
			Cancel : function() {
				$(this).dialog("close");
			}
		},
		close : function() {
			$(this).dialog("close");
		}
	}).dialogExtend({
		minimizable : true,
		collapsable : true
	});
	
	
	
	$("#questionOptionDialog").dialog({
		overlay : {
			backgroundColor : "#000",
			opacity : 0.5
		},
		position : {
			my : "left+100",
			at : "left top+200",
			of : window
		},
		title : 'Add Question Options',
		autoOpen : false,
		closeOnEscape : true,
		height : 'auto',
		width : 500,
		modal : true,
		draggable : true,
		resizable : true,
		buttons : {
			Cancel : function() {
				$(this).dialog("close");
			}
		},
		close : function() {
			$(this).dialog("close");
		}
	}).dialogExtend({
		minimizable : true,
		collapsable : true
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	var closeBtn3 = $('.ui-dialog-titlebar-close');
	closeBtn3
			.append('<span class="ui-button-icon-primary ui-icon ui-icon-closethick">');
	$(".ui-dialog-titlebar").addClass("ui-widget-header-custom");
});



function getAppContetGlobal(){
	var currentPageUrl = "";
	if (typeof this.href === "undefined") {
	    currentPageUrl = document.location.toString().toLowerCase();
	}
	else {
	    currentPageUrl = this.href.toString().toLowerCase();
	}
	if(currentPageUrl.contains("umwarimu_test"))
		 return "/umwarimu_test";
	else if(currentPageUrl.contains("umwarimu"))
		 return "/umwarimu";
	return "/umwarimu";
}
function getLogoutUrl(){
	var currentPageUrl = "";
	if (typeof this.href === "undefined") {
	    currentPageUrl = document.location.toString().toLowerCase();
	}
	else {
	    currentPageUrl = this.href.toString().toLowerCase();
	}
	if(currentPageUrl.contains("dms_test"))
		return "https://pchat.pivotaccess.com/pplus_test/"
		else if(currentPageUrl.contains("dms"))
		return "https://www.pivotaccess.com/pplus/";
	return currentPageUrl;
}
function refreshRedirect(){
	 window.location.replace(""+getLogoutUrl().replace("#","")+"");
}
function logout(){
	if(confirmLogout($("#username").val())){
	$("#token").val("");
	$("#orgCode").val("");
	$("#username").val("");
	$("#usernameType").val("");
	$("#warehouseCode").val("");
	$.ajax({type : 'GET',
        url : getAppContetGlobal()+'/application/clearSession',
        data : {},
        success : function(data){
      },error:function(e){
      },async:false
    });
	 window.location.replace(""+getAppContetGlobal()+"");
	}
}

function clearTable(tableID) {
    try {
    var table = document.getElementById(tableID);
    var rowCount = table.rows.length;
    for(var i=1; i<rowCount; i++) {
        var row = table.rows[i];
            table.deleteRow(i);
            rowCount--;
            i--;
        }
    }catch(e) {
        alert(e);
    }
}
function getUsers(event){
	var tableId="operatoList";
if(event.value!=""&&event.value!=" "){
	var tableRows="";
$.ajax({type : 'GET',
    url : getAppContetGlobal()+'/operators/getUsersByCriterion',
    data : {criterion:event.value},
    success : function(data){
    	alert(JSON.stringify(data));
    	 for(var i in data){
    		 if(i==0)
    		 clearTable(tableId);
      	tableRows="<tr><td>"+data[i].username+"</td><td>"+data[i].firstName+"</td><td>"+data[i].lastName+"</td><td>"+data[i].emailAddress+"</td><td>"+data[i].phoneNumber+"</td><td>"+data[i].role.name+"</td><td><span class=\"crudListEdit\"><a href=\"show/"+data[i].id+"/Editing\">Edit</a></span></td></tr>";
           }
  },error:function(e){
	  alert("Error  ?"+JSON.stringify(e));
  },async:false
});
$('#operatoList').append(tableRows); 

}
}

function checkForm(form) {
	if(form.username.value == "") {
		alert("Error: Username cannot be blank!"); 
		form.username.focus(); 
		return false; 
		} 
	var re = /^\w+$/;
	if(!re.test(form.username.value)) {
		alert("Error: Username must contain only letters, numbers and underscores!");
		form.username.focus();
		return false; 
} if(form.password.value != "" && form.password.value == form.passwordConfirm.value) {
	if(form.password.value.length < 6) {
		alert("Error: Password must contain at least six characters!"); 
		form.password.focus(); 
		return false; 
	} if(form.password.value == form.username.value) {
		alert("Error: Password must be different from Username!"); 
		form.password.focus();
		return false; 
	} 
	var re = /[0-9]/; 
	if(!re.test(form.password.value)) {
	alert("Error: password must contain at least one number (0-9)!");
	form.password.focus(); 
	return false; 
	} 
	var re = /[a-z]/;
	if(!re.test(form.password.value)) {
		alert("Error: password must contain at least one lowercase letter (a-z)!");
		form.password.focus(); 
		return false; 
	} 
	var re = /[A-Z]/;
	if(!re.test(form.password.value)) {
		alert("Error: password must contain at least one uppercase letter (A-Z)!"); 
		form.password.focus(); 
		return false; 
		} 
} else {					
alert("Error: Please check that you've entered and confirmed your password!");
form.password.focus(); 
return false;
} 
alert("You entered a valid password: " + form.password.value+" And username :"+form.username.value); 
return true; 
}


function confirmUsername(event){
	var username=event.value;
	if(username == ""||username == " ") {
		$("#usernameError").html("Error: Username cannot be blank!"); 
		 $("#usernameValid").html(""); 
		 return false; 
		} else{
		$("#usernameError").html(""); 
		}
	var re = new RegExp(/^\w+$/);
	if(!re.test(username)) {
		$("#usernameError").html("Error: Username must be less than or Equal 10 Characters<br/> containing only letters, numbers and underscores!");
		 $("#usernameValid").html(""); 
		return false; 
	}else
	 $("#usernameError").html(""); 
	if(username.length<5||username.length>10) {
		$("#usernameError").html("Error: Username must be less than or Equal 10 Characters<br/> containing only letters, numbers and underscores!");
		return false; 
	}else
	 $("#usernameError").html("");
	$("#usernameError").html("");
	 $("#usernameValid").html("You entered a valid Username: " + event.value); 
	 return true;
}
function checkFormPassword(event) {
var password=$("#password").val();
if(password==""|| password==""){
	$("#passwrodConfimation").html("Error: Empty Password is not allowed!"); 
	$("#passwordValid").html(""); 
	return false;
}else
	$("#passwrodConfimation").html("Ok: Password Confirmition mutch!"); 
 if(event.value != "") {
	if(event.value.length < 6) {
		$("#passwrodConfimation").html("Error: Password must contain at least six characters!"); 
		$("#passwordValid").html(""); 
		return false; 
	} 
	if(event.value!=password) {
		$("#passwrodConfimation").html("Error: Your Password does not mutch the Password Confirmation"); 
		$("#passwordValid").html(""); 

		return false; 
	} 
	var re = /[0-9]/; 
	if(!re.test(event.value)) {
		$("#passwrodConfimation").html("Error: password must contain at least one number (0-9)!");
		$("#passwordValid").html(""); 

	return false; 
	} 
	var re = /[a-z]/;
	if(!re.test(event.value)) {
		$("#passwrodConfimation").html("Error: password must contain at least one lowercase letter (a-z)!");
		$("#passwordValid").html(""); 

		return false; 
	} 
	var re = /[A-Z]/;
	if(!re.test(event.value)) {
		$("#passwrodConfimation").html("Error: password must contain at least one uppercase letter (A-Z)!"); 
		$("#passwordValid").html(""); 

		return false; 
		} 
} else {					
	$("#passwrodConfimation").html("Error: Enter an Equvalent Password Confimation!");
	$("#passwordValid").html(""); 
	return true; 
return false;
} 
 $("#passwrodConfimation").html(""); 
 $("#passwordValid").html("You entered a valid password: " + event.value); 
return true; 
}
function confirmUsernameEdit(event){
	var username=event.value;
	if(username == ""||username == " ") {
		$("#usernameErrorEdit").html("Error: Username cannot be blank!"); 
		 $("#usernameValidEdit").html(""); 
		 return false; 
		} else{
		$("#usernameErrorEdit").html(""); 
		}
	var re = new RegExp(/^\w+$/);
	if(!re.test(username)) {
		$("#usernameErrorEdit").html("Error: Username must be less than or Equal 10 Characters<br/> containing only letters, numbers and underscores!");
		 $("#usernameValidEdit").html(""); 
		return false; 
	}else
	 $("#usernameErrorEdit").html(""); 
	if(username.length<5||username.length>10) {
		$("#usernameErrorEdit").html("Error: Username must be less than or Equal 10 Characters<br/> containing only letters, numbers and underscores!");
		return false; 
	}else
	 $("#usernameErrorEdit").html("");
	$("#usernameErrorEdit").html("");
	 $("#usernameValidEdit").html("You entered a valid Username: " + event.value); 
	 return true;
}
function checkFormPasswordEdit(event) {
var password=$("#passwordEdit").val();
if(password==""|| password==""){
	$("#passwrodConfimationEdit").html("Error: Empty Password is not allowed!"); 
	$("#passwordValidEdit").html(""); 
	return false;
}else
	$("#passwrodConfimationEdit").html("Ok: Password Confirmition mutch!"); 
 if(event.value != "") {
	if(event.value.length < 6) {
		$("#passwrodConfimationEdit").html("Error: Password must contain at least six characters!"); 
		$("#passwordValidEdit").html(""); 
		return false; 
	} 
	if(event.value!=password) {
		$("#passwrodConfimationEdit").html("Error: Your Password does not mutch the Password Confirmation"); 
		$("#passwordValidEdit").html(""); 

		return false; 
	} 
	var re = /[0-9]/; 
	if(!re.test(event.value)) {
		$("#passwrodConfimationEdit").html("Error: password must contain at least one number (0-9)!");
		$("#passwordValidEdit").html(""); 

	return false; 
	} 
	var re = /[a-z]/;
	if(!re.test(event.value)) {
		$("#passwrodConfimationEdit").html("Error: password must contain at least one lowercase letter (a-z)!");
		$("#passwordValidEdit").html(""); 

		return false; 
	} 
	var re = /[A-Z]/;
	if(!re.test(event.value)) {
		$("#passwrodConfimationEdit").html("Error: password must contain at least one uppercase letter (A-Z)!"); 
		$("#passwordValidEdit").html(""); 

		return false; 
		} 
} else {					
	$("#passwrodConfimationEdit").html("Error: Enter an Equvalent Password Confimation!");
	$("#passwordValidEdit").html(""); 
	return true; 
return false;
} 
 $("#passwrodConfimationEdit").html(""); 
 $("#passwordValidEdit").html("You entered a valid password: " + event.value); 
return true; 
}
function editOperator(){
	var userId=$("#userId").val(); 
	var  firstName=$("#firstName").val(); 
	var  lastName=$("#lastName").val();
	var  phoneNumber=$("#phoneNumber").val();
	var  emailAddress=$("#emailAddress").val(); 
	var  username=$("#username").val();
	var  password=$("#passwordEdit").val(); 
	var  physicalAddress=$("#physicalAddress").val();
	var  box=$("#box").val();
	var  webSite=$("#webSite").val(); 
	var  role=$("#select-Role").val();
	var company=$("#select-School").val();
	var degree =$("#degree").val();
	  $.ajax({type :'GET',
          url : getAppContetGlobal()+'/operators/modifyUser',
          data:{userId:userId,firstName:firstName,lastName:lastName,phoneNumber:phoneNumber,emailAddress:emailAddress,username:username,password:password,physicalAddress:physicalAddress,box:box,webSite:webSite,role:role,school:company,degree:degree},
          success : function(data){
                $("#msgAlert").html(data.message);
            },error:function(e){
            	alert("test call error ok:"+JSON.stringify(e));

            },async:false
        });
}