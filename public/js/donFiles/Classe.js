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