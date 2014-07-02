$(document).ready(function() {
	$('[title]').blur(function() {
		if ($(this).val() == '') {
			$(this).addClass('formatHint');
			$(this).val($(this).attr('title'));
		}
	});
	$('[title]').focus(function() {
		if ($(this).val() == $(this).attr('title')) {
			$(this).removeClass('formatHint');
			$(this).val('');
		}
	});
	$('[title]').blur();
});


