function getSchoolReports(event) {
	var tableRows = "<table>";
	var operatorId = event.rel;
	$('#schoolReports').dialog("open");
	$.ajax({type : 'GET',
				url : getAppContetGlobal() + '/reports/getSchoolReports',
				data : {
					operatorId : operatorId
				},
				success : function(data) {
					var qNum = 1;
					for ( var i in data) {
						if (i == 0) {
							tableRows = tableRows
									+ "<tr><td>Number</td><td>Student</td><td>Year</td><td>Lebel</td><td>classe</td><td>Tutulaire</td><td>On</td></tr>";
						}
						tableRows=tableRows+"<tr><td>"+qNum+"</td><td><a href=\"#\" rel=\""+data[i].id+"\" onclick=\"getReportView(this);\">"+ data[i].student.username+"</a></td><td>"+data[i].accademicYear.description+"</td><td>"+data[i].reportLabel+"</td><td>"+data[i].classe.fullName+"</td><td>"+ data[i].creatorTeacher.username+"</td><td>"+data[i].lastUpdatedOn+"</td></tr>";
				}},
				error : function(e) {
					alert("Error  ?" + JSON.stringify(e));
				},
				async : false
			});
	tableRows = tableRows + "<table>";
	$('#schoolReportsContent').html(tableRows);
	$('#schoolReportsContent').show();

}
function getReportView(event){
	$('#schoolReportView').dialog("open");
	var reportId = event.rel;
	var tableRows="<table>";
	$.ajax({type : 'GET',
		url : getAppContetGlobal() + '/reports/getSchoolReportView',
		data : {
			reportid : reportId
		},
		success : function(data) {
			var qNum = 1;
			var divOneTjTotal=0;
			var divOneExTotal=0;
			var divOneTotTotal=0;
			
			var divTwoTjTotal=0;
			var divTwoExTotal=0;
			var divTwoTotTotal=0;
			
			
			var divThreeTjTotal=0;
			var divThreeExTotal=0;
			var divThreeTotTotal=0;
			
			
			
			var yearTjTotal=0;
			var yearExTotal=0;
			var yearTotTotal=0;
			var yearAvgTotal=0;
			
			
			
			
			for ( var i in data.marks) {
				if (i == 0) {
					tableRows = tableRows
					+ "<tr><td colspan=\"2\"></td><td colspan=\"3\">First Term</td><td colspan=\"3\">Second Term</td><td colspan=\"3\">Fird Term</td><td colspan=\"4\">Year</td></tr>";
					tableRows = tableRows
							+ "<tr><td>Nr.</td><td>course</td><td>Tj</td><td>Ex</td><td>Tot.</td><td>Tj</td><td>Ex</td><td>Tot</td><td>Tj</td><td>Ex</td><td>Tot</td><td>Tj</td><td>Ex</td><td>Avg</td><td>Tot</td></tr>";
				}
				tableRows=tableRows+"<tr><td>"+qNum+"</td><td>"+data.marks[i].course.name+"</td><td><input type=\"text\" size=\"2\" name=\""+data.marks[i].id+"\" id=\"divOneTj_"+qNum+"\" onchange=\"addReportMarks(this);\" value=\""+data.marks[i].divOneTj+"\"></td><td><input type=\"text\" size=\"2\" name=\""+data.marks[i].id+"\" id=\"divOneEx_"+qNum+"\" onchange=\"addReportMarks(this);\" value=\""+data.marks[i].divOneEx+"\"> / "+data.marks[i].course.overEx+"</td><td>"+data.marks[i].divOneTot+"</td><td><input type=\"text\" size=\"2\" name=\""+data.marks[i].id+"\" id=\"divTwoTj_"+qNum+"\" onchange=\"addReportMarks(this);\" value=\""+data.marks[i].divTwoTj+"\"></td><td><input type=\"text\" size=\"2\" name=\""+data.marks[i].id+"\" id=\"divTwoEx_"+qNum+"\" onchange=\"addReportMarks(this);\" value=\""+data.marks[i].divTwoEx+"\"></td><td>"+data.marks[i].divTwoTot+"</td><td><input type=\"text\" size=\"2\" name=\""+data.marks[i].id+"\" id=\"divThreeTj_"+qNum+"\" onchange=\"addReportMarks(this);\" value=\""+data.marks[i].divThreeTj+"\"></td><td><input type=\"text\" size=\"2\" name=\""+data.marks[i].id+"\" id=\"divThreeEx"+qNum+"\" onchange=\"addReportMarks(this);\" value=\""+data.marks[i].divThreeEx+"\"></td><td>"+data.marks[i].divThreeTot+"</td><td>"+data.marks[i].yearTj+"</td><td>"+data.marks[i].yearEx+"</td><td>"+data.marks[i].yearAvg+"</td><td>"+data.marks[i].yearTot+"</td></tr>";
				divOneTjTotal=parseFloat(divOneTjTotal)+parseFloat(data.marks[i].divOneTj);
				divOneExTotal=parseFloat(divOneExTotal)+parseFloat(data.marks[i].divOneEx);
				divOneTotTotal=parseFloat(divOneTotTotal)+parseFloat(data.marks[i].divOneTot);

				divTwoTjTotal=parseFloat(divTwoTjTotal)+parseFloat(data.marks[i].divTwoTj);
				divTwoExTotal=parseFloat(divTwoExTotal)+parseFloat(data.marks[i].divTwoEx);
				divTwoTotTotal=parseFloat(divTwoTotTotal)+parseFloat(data.marks[i].divTwoTot);
				
			    divThreeTjTotal=parseFloat(divThreeTjTotal)+parseFloat(data.marks[i].divThreeTj);
				divThreeExTotal=parseFloat(divThreeExTotal)+parseFloat(data.marks[i].divThreeEx);
				divThreeTotTotal=parseFloat(divThreeTotTotal)+parseFloat(data.marks[i].divThreeTot);
				
				yearTjTotal=parseFloat(yearTjTotal)+parseFloat(data.marks[i].yearTj);
				yearExTotal=parseFloat(yearExTotal)+parseFloat(data.marks[i].yearEx);
				yearAvgTotal=parseFloat(yearAvgTotal)+parseFloat(data.marks[i].yearAvg);
				yearTotTotal=parseFloat(yearTotTotal)+parseFloat(data.marks[i].yearTot);
				qNum=qNum+1;
		}
			tableRows=tableRows+"<tr><td>Total :</td><td></td><td>"+divOneTjTotal+"</td><td>"+divOneExTotal+"</td><td>"+divOneTotTotal+"</td><td>"+divTwoTjTotal+"</td><td>"+divTwoExTotal+"</td><td>"+divTwoTotTotal+"</td><td>"+divThreeTjTotal+"</td><td>"+divThreeExTotal+"</td><td>"+divThreeTotTotal+"</td><td>"+yearTjTotal+"</td><td>"+yearExTotal+"</td><td>"+yearAvgTotal+"</td><td>"+yearTotTotal+"</td></tr>";

			},
		error : function(e) {
			alert("Error  ?" + JSON.stringify(e));
		},
		async : false
	});
	tableRows = tableRows + "<table>";
	$('#schoolReportViewContent').html(tableRows);
	$('#schoolReportViewContent').show();
}
function addReportMarks(event){
	$.ajax({type : 'GET',
		url : getAppContetGlobal() + '/reports/addReportMarks',
		data : {
			markid:event.name,markvalue:event.value,trimester:event.id
		},
		success : function(data) {
			$('#marksUpdator').html(data.message);
			},
		error : function(e) {
			alert("Error  ?" + JSON.stringify(e));
		},
		async : false
	});
}