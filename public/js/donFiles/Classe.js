function getClasses(event){
	alert("");
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
     	tableRows="<tr><td>"+data[i].schoolName+"</td><td>"+data[i].ownerFirstName+"</td><td>"+data[i].ownerLastName+"</td><td>"+data[i].ownerPhoneNumber+"</td><td>"+data[i].typeOf+"</td><td>"+data[i].category+"</td><td>"+data[i].createdOn+"</td><td><a href=\"show/"+data[i].id+"/Editing\">Edit</td></tr>";
           }
  },error:function(e){
	  alert("Error  ?"+JSON.stringify(e));
  },async:false
});
$('#classeList').append(tableRows); 

}
}
function editClass(){
	alert("hello");
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