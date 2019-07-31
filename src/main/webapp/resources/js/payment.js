$(document).ready(function() {



    $('#servicepicker').change(function (e) {
        $('.payinf').hide();
        $('#' + $('#servicepicker option:selected').text()).show();
    });
    
    $('#paySum').change(function (e) {
        let message = $('#' + $('#servicepicker option:selected').text()).html();
        let min = parseInt(message.match(/\d+/));
        message = message.replace(min, '');
        let max = parseInt(message.match(/\d+/));
        let paySumVal = $('#paySum').val();
        $('#pay').attr("disabled", paySumVal < min || paySumVal > max);
    });

});
