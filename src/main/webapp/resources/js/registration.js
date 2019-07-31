$(document).ready(function(){

    $('#phoneNumber').change(function(e) {
        let number = $('#phoneNumber').val();
        $.ajax({
            url: window.location.href.replace('registration', 'getclient?phonenumber=') + number
        }).then(function (data) {
            $('#numberExists').attr("hidden", data.clientId == undefined);
            $('#register').attr("disabled", data.clientId != undefined);
        });
    });
});