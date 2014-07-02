
$(function(){
	 $("#intara").change(function(){
         $.ajax({
                 type : 'GET',
                 url : getAppContetGlobal()+'/application/getChildreenLocations',
                 data :{parentCode:$("#intara").val()},
                 success : function(data) {
                 if(data!=undefined)
                  $("#akarere option").remove();                                
                for(var i in data){
                                     $("#akarere").append($("<option></option>").text(data[i].name).val(data[i].code));
                                      }
              }});
           });
$("#akarere").change(function(){
$.ajax({
      type : 'GET',
      url : getAppContetGlobal()+'/application/getChildreenLocations',
      data :{parentCode:$("#akarere").val()},
      success : function(data) {
      if(data!=undefined)
       $("#umurenge option").remove();                                
     for(var i in data){
         $("#umurenge").append($("<option></option>").text(data[i].name).val(data[i].code));
          }
   }});
});
$("#umurenge").change(function(){
$.ajax({
      type : 'GET',
      url : getAppContetGlobal()+'/application/getChildreenLocations',
      data :{parentCode:$("#umurenge").val()},
      success : function(data) {
      if(data!=undefined)
       $("#akagali option").remove();                                
     for(var i in data){
                          $("#akagali").append($("<option></option>").text(data[i].name).val(data[i].code));
                           }
   }});
});
$("#akagali").change(function(){
$.ajax({
      type : 'GET',
      url : getAppContetGlobal()+'/application/getChildreenLocations',
      data :{parentCode:$("#akagali").val()},
      success : function(data) {
      if(data!=undefined)
       $("#umudugudu option").remove();                                
     for(var i in data){
                          $("#umudugudu").append($("<option></option>").text(data[i].name).val(data[i].code));
                           }
   }});
}); 
//edit company
$("#intaraEdit").change(function(){
    $.ajax({
            type : 'GET',
            url : getAppContetGlobal()+'/application/getChildreenLocations',
            data :{parentCode:$("#intaraEdit").val()},
            success : function(data) {
            if(data!=undefined)
             $("#akarereEdit option").remove();                                
           for(var i in data){
                                $("#akarereEdit").append($("<option></option>").text(data[i].name).val(data[i].code));
                                 }
         }});
      });
$("#akarereEdit").change(function(){
$.ajax({
 type : 'GET',
 url : getAppContetGlobal()+'/application/getChildreenLocations',
 data :{parentCode:$("#akarereEdit").val()},
 success : function(data) {
 if(data!=undefined)
  $("#umurengeEdit option").remove();                                
for(var i in data){
    $("#umurengeEdit").append($("<option></option>").text(data[i].name).val(data[i].code));
     }
}});
});
$("#umurengeEdit").change(function(){
$.ajax({
 type : 'GET',
 url : getAppContetGlobal()+'/application/getChildreenLocations',
 data :{parentCode:$("#umurengeEdit").val()},
 success : function(data) {
 if(data!=undefined)
  $("#akagaliEdit option").remove();                                
for(var i in data){
                     $("#akagaliEdit").append($("<option></option>").text(data[i].name).val(data[i].code));
                      }
}});
});
$("#akagaliEdit").change(function(){
$.ajax({
 type : 'GET',
 url : getAppContetGlobal()+'/application/getChildreenLocations',
 data :{parentCode:$("#akagaliEdit").val()},
 success : function(data) {
 if(data!=undefined)
  $("#umuduguduEdit option").remove();                                
for(var i in data){
                     $("#umuduguduEdit").append($("<option></option>").text(data[i].name).val(data[i].code));
                      }
}});
}); 
});

function getCompanies(event){
	var tableId="organizationList";
if(event.value!=""&&event.value!=" "){
	var tableRows="";
$.ajax({type : 'GET',
    url : getAppContetGlobal()+'/organizations/getCompanysByCriterion',
    data : {criterion:event.value},
    success : function(data){
    	 for(var i in data){
    		 if(i==0)
    		 clearTable(tableId);
     	tableRows="<tr><td>"+data[i].name+"</td><td>"+data[i].ownerFirstName+"</td><td>"+data[i].ownerLastName+"</td><td>"+data[i].ownerPhoneNumber+"</td><td>"+data[i].typeOf+"</td><td>"+data[i].createdOn+"</td><td><a href=\"show/"+data[i].id+"/Editing\">Edit</td></tr>";
           }
  },error:function(e){
	  alert("Error  ?"+JSON.stringify(e));
  },async:false
});
$('#organizationList').append(tableRows); 

}
}
function editCompany(){
	var  companyId=$("#companyId").val(); 
	var  companyTinNumber=$("#companyTinNumber").val(); 
	var  companyName=$("#companyName").val();
	var  companyDescription=$("#companyDescription").val();
	var  ownerFistName=$("#ownerFistName").val(); 
	var  ownerLastName=$("#ownerLastName").val();
	var  typeOfId=$("#typeOfId").val(); 
	var  ownerPhoneNumber=$("#ownerPhoneNumber").val();
	var  ownerEmail=$("#ownerEmail").val();
	var  webSite=$("#webSite").val(); 
	var  companyPoBox=$("#companyPoBox").val();
	var location=$("#umuduguduEdit").val();
	var level=$("#select-level").val();
	  $.ajax({type :'GET',
          url : getAppContetGlobal()+'/organizations/modifyCompany',
          data:{companyId:companyId,tinNumber:companyTinNumber,typeOf:typeOfId,name:companyName,description:companyDescription,ownerFirstName:ownerFistName,ownerLastName:ownerLastName,ownerPhoneNumber:ownerPhoneNumber,ownerEmail:ownerEmail,poBox:companyPoBox,website:webSite,level:level,location:location},
          success : function(data){
                $("#msgAlertCompany").html(data.message);
            },error:function(e){
            	alert("test call error ok:"+JSON.stringify(e));
            },async:false
        });
}