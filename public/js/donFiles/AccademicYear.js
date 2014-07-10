function getAccademicYears(event){
	var tableId="accYearList";
if(event.value!=""&&event.value!=" "){
	var tableRows="";
$.ajax({type : 'GET',
    url : getAppContetGlobal()+'/accademicyears/getAccademicYearsByCriterion',
    data : {criterion:event.value},
    success : function(data){
    	 for(var i in data){
    		 if(i==0)
    		 clearTable(tableId);
     	tableRows=tableRows+"<tr><td>"+data[i].description+"</td><td>"+data[i].startAt+"</td><td>"+data[i].endAt+"</td><td>"+data[i].createdBy.username+"</td><td>"+data[i].yearStatus+"</td><td>"+data[i].createdOn+"</td><td>"+data[i].school.schoolName+"</td><td><a href=\"show/"+data[i].id+"/Editing\">Edit</td></tr>";
           }
  },error:function(e){
	  alert("Error  ?"+JSON.stringify(e));
  },async:false
});
$('#accYearList').append(tableRows); 

}
}
function editAccYear(){
	var  yearId=$("#yearId").val(); 
	var description=$("#yearaccdescription").val(); 
	var  yearaccStartAt=$("#yearaccStartAt").val();
	var  yearaccEndAt=$("#yearaccEndAt").val();
	  $.ajax({type :'GET',
          url : getAppContetGlobal()+'/accademicyears/modifyAccademicYear',
          data:{yearId:yearId,description:description, startAt:yearaccStartAt,endAt:yearaccEndAt},
          success : function(data){
                $("#msgAlertYear").html(data.message);
            },error:function(e){
            	alert("External Error :"+JSON.stringify(e));
            },async:false
        });
}
// Year devisions
function editAccYearDevision(){
	var  yearId=$("#yearIdDiv").val(); 
	var description=$("#yearaccdescriptionDiv").val(); 
	var lebel=$("#devisionLabelDiv").val(); 
	var  yearaccStartAt=$("#yearaccStartAtDiv").val();
	var  yearaccEndAt=$("#yearaccDivEndAt").val();
	  $.ajax({type :'GET',
          url : getAppContetGlobal()+'/academicyeardevisions/modifyAcademicYearDevision',
          data:{yearDevisionId:yearId,description:description, devisionLabel:lebel,startAt:yearaccStartAt,endAt:yearaccEndAt},
          success : function(data){
                $("#msgAlertYearDivision").html(data.message);
            },error:function(e){
            	alert("External Error :"+JSON.stringify(e));
            },async:false
        });
}