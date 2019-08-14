jQuery(document).ready(function () {

    $('.page-container form').submit(function () {
        var username = $(this).find('.username').val();
        var password = $(this).find('.password').val();
        if (username == '') {
            $(this).find('.error').fadeOut('fast', function () {
                $(this).css('top', '27px');
            });
            $(this).find('.error').fadeIn('fast', function () {
                $(this).parent().find('.username').focus();
            });
            return false;
        }
        if (password == '') {
            $(this).find('.error').fadeOut('fast', function () {
                $(this).css('top', '96px');
            });
            $(this).find('.error').fadeIn('fast', function () {
                $(this).parent().find('.password').focus();
            });
            return false;
        }
    });

    $('.page-container form .username, .page-container form .password').keyup(function () {
        $(this).parent().find('.error').fadeOut('fast');
    });

});
function switchToMobile (){
    $("#account").css("display", "none");
    $("#mobile").css("display", "block");
}
function switchToAccount (){
    $("#mobile").css("display", "none");
    $("#account").css("display", "block");

}
function sendSms () {
    $.ajax({
        url: "http://localhost:9000/code/sms?mobile=18701429557",
        type: "GET",
        dataType: "json",
        success: function(data) {
        }
    });
}

