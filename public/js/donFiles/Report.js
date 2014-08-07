function getSchoolReports(event) {
	var tableRows = "<table>";
	var operatorId = event.rel;
	$.ajax({type : 'GET',
				url : getAppContetGlobal() + '/reports/getSchoolReports',
				data : {
					operatorId : operatorId
				},
				success : function(data) {
					alert(JSON.stringify(data));
					var qNum = 1;
					for ( var i in data) {
						if (i == 0) {
							tableRows = tableRows
									+ "<tr><td>Number</td><td>Student</td><td>Year</td><td>Lebel<td><td>classe</td><td>Tutulaire</td><td>On</td></tr>";
						}
						tableRows = tableRows
								+ "<tr><td>"
								+ qNum
								+ "</td><td>"
								+ data[i].content
								+ "</td><td>"
								+ data[i].marks
								+ "</td><td><a href=\"#\" rel=\""
								+ data[i].id
								+ "\" onclick=\"showAddOptionDialog(this);\"> <span class=\"crudListAdd\">"
								+ data[i].maxAllowedOptions
								+ "</span></a></td><td>"
								+ data[i].qStatus
								+ "</td><td>"
								+ data[i].creator.username
								+ "</td><td>"
								+ data[i].createdOn
								+ "</td><td><span class=\"crudListDelete\"><a href=\"#\" rel=\""
								+ data[i].id
								+ "\" title=\""
								+ data[i].content
								+ "\" onclick=\"removeQuestion(this);\" >Remove</a></span>\</td></tr>";

						if (data[i].options != undefined) {
							var optNum = 1;
							var tableOption = "<tr><td colspan=\"8\"><table class=\"formTable\">";
							for ( var j in data[i].options) {
								tableOption = tableOption
										+ "<tr><td>"
										+ qNum
										+ "."
										+ optNum
										+ "</td><td>"
										+ data[i].options[j].content
										+ "</td><td>"
										+ data[i].options[j].marks
										+ "</td></td><td>"
										+ data[i].options[j].createdBy.username
										+ "</td><td>"
										+ data[i].options[j].createdOn
										+ "</td><td><span class=\"crudListDelete\"><a href=\"#\" rel=\""
										+ data[i].options[j].id
										+ "\" title=\""
										+ data[i].options[j].content
										+ "\" onclick=\"removeQuestionOption(this);\" >Remove</a></span>\</td></tr>";
								optNum = optNum + 1;
							}
							tableOption = tableOption + "</table></td></tr>";
							tableRows = tableRows + tableOption;
						}

						totalMars = parseFloat(totalMars)
								+ parseFloat(data[i].marks);
						qNum = qNum + 1;
					}
					tableRows = tableRows + "<tr><td> Total Marks :</td><td>"
							+ totalMars + "</td><td colspan=\"4\"> </td></tr>";

				},
				error : function(e) {
					alert("Error  ?" + JSON.stringify(e));
				},
				async : false
			});
	tableRows = tableRows + "<table>";
	alert(tableRows);
}