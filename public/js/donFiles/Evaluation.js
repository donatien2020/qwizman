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
		    	$("#content").val("");
		    	$("#marks").val("");
		    	$("#maxAllowedOptions").val("");
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
		var totalMars=0.0;
	$.ajax({type : 'GET',
	    url : getAppContetGlobal()+'/evaluations/loadQuestions',
	    data : {evaluationId:evaluationId},
	    success : function(data){
	    	var qNum=1;
	    	 for(var i in data){
	    		 if(i==0){
	    			 tableRows=tableRows+"<tr><td colspan=\"6\"> List Of Questions</td></tr>";

	    			 tableRows=tableRows+"<tr><td>Number</td><td>Question</td><td>Marks</td><td>Options<td><td>Status</td><td>By</td><td>On</td></tr>";
	    		 }
	    			 tableRows=tableRows+"<tr><td>"+qNum+"</td><td>"+data[i].content+"</td><td>"+data[i].marks+"</td><td><a href=\"#\" rel=\""+data[i].id+"\" onclick=\"showAddOptionDialog(this);\"> <span class=\"crudListAdd\">"+data[i].maxAllowedOptions+"</span></a></td><td>"+data[i].qStatus+"</td><td>"+data[i].creator.username+"</td><td>"+data[i].createdOn+"</td><td><span class=\"crudListDelete\"><a href=\"#\" rel=\""+data[i].id+"\" title=\""+data[i].content+"\" onclick=\"removeQuestion(this);\" >Remove</a></span>\</td></tr>";
	    			
	    			 if(data[i].options!=undefined){
	    				 var optNum=1;
	    				 var tableOption="<tr><td colspan=\"8\"><table class=\"formTable\">";
	    				 for(var j in data[i].options){
	    					 tableOption =tableOption+"<tr><td>"+qNum+"."+optNum+"</td><td>"+data[i].options[j].content+"</td><td>"+data[i].options[j].marks+"</td></td><td>"+data[i].options[j].createdBy.username+"</td><td>"+data[i].options[j].createdOn+"</td><td><span class=\"crudListDelete\"><a href=\"#\" rel=\""+data[i].options[j].id+"\" title=\""+data[i].options[j].content+"\" onclick=\"removeQuestionOption(this);\" >Remove</a></span>\</td></tr>";
	    					 optNum=optNum+1;
	    				 }
	    					 tableOption=tableOption+"</table></td></tr>";
		    			 tableRows=tableRows+tableOption;
	    			 }
	    			
	    			 totalMars =parseFloat(totalMars)+parseFloat(data[i].marks);
	    			 qNum=qNum+1;
	    	 }
			 tableRows=tableRows+"<tr><td> Total Marks :</td><td>"+totalMars+"</td><td colspan=\"4\"> </td></tr>";

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
}
function showAddOptionDialog(event){
	$('#questionOptionDialog').dialog("open"); 
	$("#questionIdOption").val(event.rel);
}

function addQuestionOption(){
	var qestionId=$("#questionIdOption").val();
	var content=$("#contentOption").val();
	var marks=$("#marksOption").val();
	if(qestionId!=""&&qestionId!=" "){
	$.ajax({type : 'GET',
	    url : getAppContetGlobal()+'/evaluations/addQuestionOption',
	    data : {qestionId:qestionId,content:content,marks:marks},
	    success : function(data){
	    	if(data.message!=undefined){
		    	$("#optionAdded").html(data.message);
		    	$("#contentOption").val("");
		    	$("#marksOption").val("");
		    	loadQuestions();
		    	}
	  },error:function(e){
		  alert("Error  ?"+JSON.stringify(e));
	  },async:false
	});
	}
}
function removeQuestionOption(event){
	$.ajax({type : 'GET',
	    url : getAppContetGlobal()+'/evaluations/removeQuestionOption',
	    data : {optionId:event.rel},
	    success : function(data){
	    	if(data.message!=undefined){
	    	$("#questionAdded").html(data.message);
	    	loadQuestions();}
	  },error:function(e){
		  alert("Error  ?"+JSON.stringify(e));
	  },async:false
	});
}