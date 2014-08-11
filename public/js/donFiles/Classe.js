function getClasses(event){
	var tableId="classeList";
if(event.value!=""&&event.value!=" "){
	var tableRows="";
$.ajax({type : 'GET',
    url : getAppContetGlobal()+'/classes/getClassesByCriterion',
    data : {criterion:event.value},
    success : function(data){
    	 for(var i in data){
    		 if(i==0)
    		 clearTable(tableId);
     	tableRows=tableRows+"<tr><td><a href=\"dashboard/"+data[i].id+"\">"+data[i].fullName+"</a></td><td>"+data[i].emailAddress+"</td><td>"+data[i].phoneNumber+"</td><td>"+data[i].physicalAddress+"</td><td>"+data[i].classlabel+"</td><td>"+data[i].classlevel+"</td><td>"+data[i].tuturaire.lastName+"&nbsp;"+data[i].tuturaire.username+"</td><td>"+data[i].creator.username+"</td><td>"+data[i].createdOn+"</td><td><span class=\"crudListEdit\"><a href=\"show/"+data[i].id+"/Editing\">Edit</a></span>\</td></tr>";
           }
  },error:function(e){
	  alert("Error  ?"+JSON.stringify(e));
  },async:false
});
$('#classeList').append(tableRows); 

}
}
function editClass(){
	var  classId=$("#classId").val(); 
	var fullName=$("#fullNameIdClass").val(); 
	var  emailAddress=$("#emailAddressIdClass").val();
	var  phoneNumber=$("#phoneNumberIdClass").val();
	var  physicalAddress=$("#physicalAddressIdClass").val(); 
	var  box=$("#boxIdClass").val();
	var  webSite=$("#webSiteIdClass").val(); 
	var  classlabel=$("#classlabelIdClass").val();
	var  classlevel=$("#classlevelIdClass").val();
	  $.ajax({type :'GET',
          url : getAppContetGlobal()+'/classes/modifyClasse',
          data:{classId:classId,fullName:fullName, emailAddress:emailAddress,phoneNumber:phoneNumber,physicalAddress:physicalAddress,box:box,webSite:webSite,classlabel:classlabel,classlevel:classlevel},
          success : function(data){
                $("#msgAlertClass").html(data.message);
            },error:function(e){
            	alert("External Error :"+JSON.stringify(e));
            },async:false
        });
}



function getClassStudents(){
	$("#studentsDialog").dialog("open");
	loadClassStudents();
}

function loadClassStudents(){
	var classId=$("#classIdDasbord").val();
	if(classId!=""&&classId!=" "){
		var tableRows="<table>";
	$.ajax({type : 'GET',
	    url : getAppContetGlobal()+'/classes/loadClassStudents',
	    data : {classId:classId},
	    success : function(data){
	    	if(data.length>0)
	    	 for(var i in data){
	    		 if(i==0){
		             tableRows=tableRows+"<tr><th> Username   First name</th><th>Last Name</th><th>E-mail Address</th></tr>";
		    		 }
	     	     tableRows=tableRows+"<tr><td>"+data[i].student.username+"&nbsp;&nbsp;&nbsp;"+data[i].student.firstName+"</td><td>"+data[i].student.lastName+"</td><td>"+data[i].student.emailAddress+"</td></tr>";
	           }
	  },error:function(e){
		  alert("Error  ?"+JSON.stringify(e));
	  },async:false
	});
	tableRows=tableRows+"</table>";
	$("#studentsListing").html(tableRows);
	}
}
function addStudentToClass(event){
	var classId=$("#classIdDasbord").val();
	var studentId=event.value;
	if(studentId!=""&&studentId!=" "){
	$.ajax({type : 'GET',
	    url : getAppContetGlobal()+'/classes/addStudentToClass',
	    data : {classId:classId,studentId:studentId},
	    success : function(data){
	    	$("#studentMsg").html(data.message);
	    	loadClassStudents();
	  },error:function(e){
		  alert("Error  ?"+JSON.stringify(e));
	  },async:false
	});
	}
}
function getClassTechers(){
	$("#teachersDialog").dialog("open");
	loadClassTeacherCourses();
}
function loadClassTeacherCourses(){
	var classId=$("#classIdDasbord").val();
	$("#techersListing").html("");
	if(classId!=""&&classId!=" "){
		var tableRows="<table>";
	$.ajax({type : 'GET',
	    url : getAppContetGlobal()+'/classes/loadClassTeacherCourses',
	    data : {classId:classId},
	    success : function(data){
	    	if(data.length>0)
	    	 for(var i in data){
	    		 if(i==0){
		           tableRows=tableRows+"<tr><th colspan=\"3\">Teacher</th><th colspan=\"2\">Course</th></tr>";
	               tableRows=tableRows+"<tr><td> Username ; First name</td><td>Last Name</td><td>E-mail Address</td><td> Code</td><td> Name</td></tr>";
	    		 }
	    		 tableRows=tableRows+"<tr><td>"+data[i].teacher.username+""+data[i].teacher.firstName+"</td><td>"+data[i].teacher.lastName+"</td><td>"+data[i].teacher.emailAddress+"</td><td>"+data[i].course.code+"</td><td>"+data[i].course.name+"</td></tr>";
	           }
	  },error:function(e){
		  alert("Error  ?"+JSON.stringify(e));
	  },async:false
	});
	tableRows=tableRows+"</table>";
	$("#techersListing").html(tableRows);
	}
}
function addTeacherCourseToClass(event){
	var classId=$("#classIdDasbord").val();
	var courseId=$("#courseId").val();
	var teacherId=event.value;
	if(teacherId!=""&&classId!=""){
	$.ajax({type : 'GET',
	    url : getAppContetGlobal()+'/classes/addTeacherToClass',
	    data : {classId:classId,teacherId:teacherId,courseId:courseId},
	    success : function(data){
	    	alert("data"+JSON.stringify(data));
	    	$("#teacherMsg").html(data.message);
	    	loadClassTeacherCourses();
	  },error:function(e){
		  alert("Error  ?"+JSON.stringify(e));
	  },async:false
	});
	}
}