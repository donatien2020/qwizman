function addSanctionDialog(event) {
	alert(event.rel);
	loadAssignableSunctions(event.rel);
	$("#faultIdAddSanction").val(event.rel);
	$("#addSanctionDialogUi").dialog("open");
	$("#addSanctionDialogUi").show();
}

function loadAssignableSunctions(fault) {
	$.ajax({
		type : 'GET',
		url : getAppContetGlobal() + '/sanctions/getAssignableSanctions',
		data : {
			fault : fault
		},
		success : function(data) {
			$("#choseSanction option").remove();
			$("#choseSanction").append(
					$("<option></option>").text("Select Sanction").val(""));
			$.each(data, function(index, item) {
				$("#choseSanction").append(
						$("<option></option>").text(item.name).val(item.id));
			});
		},
		error : function(e) {
			alert("Error  ?" + JSON.stringify(e));
		},
		async : false
	});

}
function addSanction(event) {
	var falut = $("#faultIdAddSanction").val();
	if (falut != "" && event.value !="") {
		$.ajax({
			type : 'GET',
			url : getAppContetGlobal() + '/sanctions/addSanctionToFault',
			data : {
				fault : falut,
				sanction : event.value
			},
			success : function(data) {
			
			},complet:function(){
				
			},
			error : function(e) {
				alert("Error  ?" + JSON.stringify(e));
			},
			async : false
		});
	}
}