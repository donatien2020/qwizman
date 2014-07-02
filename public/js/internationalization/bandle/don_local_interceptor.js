$(function(){
	
		var myDictionary = {
			fr : {
                                'Welcome!' : 'Murakaza neza !',
                                'Scheduled Visits To day':'Isurwa riteganyijwe ku munsi',
                                'Actions':'Ibikorwa',
                                'Assigned By':'Bitanzwe na',
                                'Assigned To':'Bihawe',
                                'Plat Number,Name ,Description':'Inomero ya purake,Izina ,Ibindi bimuranga',
                                'Van':'Imodoka',
                                'Route Description':'Imiterere y'Inzira ',
                                'Route Name':'Izina Ry'inzira','Status':'Imiterere','Visit Date':'Itariki y'isurwa','Visitor Van':'Imodoka Isura','Visit Objective':'Intego y'Isura',
                                'Visited Outlet':'Isurwa ry'abafatanyabikorwa','Visit Value':'Akaciro k'isura','Total Visit value :':'Igiteranyo cy'agaciro ko gusura :',
                                'Return Value':'Agaciro kibyagarutse','Force Closing':'Gufunga bihatiwe','Price':'Igiciro','Sales Person':'Ugurisha'
                        },kin : {'Welcome!' : 'Murakaza neza',
                                'Scheduled Visits To day':'Isurwa Riteganyijwe ku munsi',
                                'Actions':'Ibikorwa',
                                'Assigned By':'Bitanzwe na',
                                'Assigned To':'Bihawe',
                                'Plat Number,Name ,Description':'Inomero ya purake,Izina ,Ibindi Bimuranga',
                                'Van':'Imodoka',
                                'Route Description':'Imiterere y'inzira',
                                'Route Name':'Izina ry'inzira','Status':'Imiterere','Visit Date':'Itariki y'isurwa','Visitor Van':'Imodoka Isura','Visit Objective':'Intego y'Isura',
                                'Visited Outlet':'Isurwa ry'abafatanyabikorwa','Visit Value':'Akaciro k'isura','Total Visit value :':'Igiteranyo cy'agaciro ko gusura :',
                                'Return Value':'Agaciro kibyagarutse','Force Closing':'Gufunga bihatiwe','Price':'Igiciro','Sales Person':'Ugurisha'
			}
		}
		$.tr.dictionary(myDictionary);
			$('#language').change(function(){
				$('#lacalTracker').val($(this).val());
				var lang=$(this).val();
				$.tr.language($(this).val());
				});
			var language = $.tr.language("en", true);

});
