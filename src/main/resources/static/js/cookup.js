
$('input:radio[name="type"]').on('change', function(){
    $('#romantic-opts').toggle();
});

function submitCookFriends() {
	var romantic = $('#cookup-form input:radio[name="type"]').val();

}
