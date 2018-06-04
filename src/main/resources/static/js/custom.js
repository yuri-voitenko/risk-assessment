$(document).ready(function () {
    $(".sortTable").tablesorter();

    $('.js-group-ranking').click(function () {
        $('.js-rate-table').each(function () {
            if (!$(this).hasClass('group-ranking')) {
                $(this).hide();
            } else {
                $(this).show();
            }
        });
    });

    $('.js-interval-ranking').click(function () {
        $('.js-rate-table').each(function () {
            if (!$(this).hasClass('interval-ranking')) {
                $(this).hide();
            } else {
                $(this).show();
            }
        });
    });
});