function addQuestion(){
	var evaluationId=$("#evaluationIdDasbord").val();
	var content=$("#content").val();
	var marks=$("#marks").val();
	var maxOptions=$("#maxAllowedOptions").val();
	if(evaluationId!=""&&evaluationId!=" "){
	$.ajax({type : 'GET',
	    url : getAppContetGlobal()+'/evaluations/addQuestion',
	    data : {evaluationId:evaluationId,content:content,marks:marks,maxOptions:maxOptions},
	    success : function(data){
	    	if(data.message!=undefined){
		    	$("#questionAdded").html(data.message);
		    	loadQuestions();}
	  },error:function(e){
		  alert("Error  ?"+JSON.stringify(e));
	  },async:false
	});
	}
}
function loadQuestions(){
	$('#questionList').html(""); 
		var tableRows="<table>";
		var evaluationId=$("#evaluationIdDasbord").val();
	$.ajax({type : 'GET',
	    url : getAppContetGlobal()+'/evaluations/loadQuestions',
	    data : {evaluationId:evaluationId},
	    success : function(data){
	    	 for(var i in data){
	    		 if(i==0)
	    			 tableRows=tableRows+"<tr><td>Question</td><td>Marks</td><td>Options<td><td>Status</td><td>By</td><td>On</td></tr>";
	     	tableRows=tableRows+"<tr><td><a href=\"#\">"+data[i].content+"</a></td><td>"+data[i].marks+"</td><td>"+data[i].maxAllowedOptions+"</td><td>"+data[i].qStatus+"</td><td>"+data[i].creator.username+"</td><td>"+data[i].createdOn+"</td><td><span class=\"crudListDelete\"><a href=\"#\" rel=\""+data[i].id+"\" title=\""+data[i].content+"\" onclick=\"removeQuestion(this);\" >Remove</a></span>\</td></tr>";
	           }
	  },error:function(e){
		  alert("Error  ?"+JSON.stringify(e));
	  },async:false
	});
	tableRows=tableRows+"<table>";
	$('#questionList').html(tableRows); 

	
}
function removeQuestion(event){
	$.ajax({type : 'GET',
	    url : getAppContetGlobal()+'/evaluations/removeQuestion',
	    data : {questionId:event.rel},
	    success : function(data){
	    	if(data.message!=undefined){
	    	$("#questionAdded").html(data.message);
	    	loadQuestions();}
	  },error:function(e){
		  alert("Error  ?"+JSON.stringify(e));
	  },async:false
	});
	tableRows=tableRows+"<table>";
	$('#questionList').html(tableRows); 

	
}