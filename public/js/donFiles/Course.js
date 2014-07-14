function getCourses(event){
	var tableId="courseList";
if(event.value!=""&&event.value!=" "){
	var tableRows="";
$.ajax({type : 'GET',
    url : getAppContetGlobal()+'/courses/getCoursesByCriterion',
    data : {criterion:event.value},
    success : function(data){
    	 for(var i in data){
    		 if(i==0)
    		 clearTable(tableId);
     	tableRows=tableRows+"<tr><td>"+data[i].code+"</td><td><a href=\"dashboard/"+data[i].id+"\">"+data[i].name+"</a></td><td>"+data[i].content+"</td><td>"+data[i].creator.username+"</td><td>"+data[i].createdOn+"</td><td><span class=\"crudListEdit\"><a href=\"show/"+data[i].id+"/Editing\">Edit</a></span>\</td></tr>";
    	 }
  },error:function(e){
	  alert("Error  ?"+JSON.stringify(e));
  },async:false
});
$('#courseList').append(tableRows); 

}
}
