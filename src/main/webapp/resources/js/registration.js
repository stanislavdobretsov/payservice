window.onload = function() {

    $('#phoneNumber').change(function(e) {
        let number = $('#phoneNumber').val();
        $.ajax({
            url: "http://127.0.0.1:8080/getclient?phonenumber=" + number
        }).then(function (data) {
            if(data.clientId != undefined) {

            }
        });
    });
};