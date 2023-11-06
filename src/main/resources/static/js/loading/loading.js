$(function () {

    $('#Progress_Loading').hide(); //첫 시작시 로딩바를 숨겨준다.
})
    .ajaxStart(function () {
        $('#Progress_Loading').show(); //ajax실행시 로딩바를 보여준다.
    })
    .ajaxStop(function () {
        $('#Progress_Loading').hide(); //ajax종료시 로딩바를 숨겨준다.
    });