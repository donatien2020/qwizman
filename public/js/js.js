$(document).ready(function() {
						   
	var hash = window.location.hash.substr(1);
	var href = $('#nav li a').each(function(){
		var href = $(this).attr('href');
		if(hash==href.substr(0,href.length-5)){
			var toLoad = hash+'.html #_content';
			$('#_content').load(toLoad)
		}											
	});

	$('#nav li a').click(function(){
								  
		var toLoad = $(this).attr('href')+' #_content';
		$('#_content').hide('fast',loadContent);
		$('#load').remove();
		$('#wrapper').append('<span id="load">LOADING...</span>');
		$('#load').fadeIn('normal');
		window.location.hash = $(this).attr('href').substr(0,$(this).attr('href').length-5);
		function loadContent() {
			$('#_content').load(toLoad,'',showNewContent())
		}
		function showNewContent() {
			$('#_content').show('normal',hideLoader());
		}
		function hideLoader() {
			$('#load').fadeOut('normal');
		}
		return false;
		
	});

});