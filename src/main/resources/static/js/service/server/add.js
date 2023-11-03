$('#addBtn').click(function () {
    addServer();
});

function addServer() {
    let name = $('#name').val();
    alert(name);
    let url = $('#url').val();
    let serverType = $('#serverType').val();
    let data = {
        'name': name,
        'url': url,
        'serverType': serverType
    }
    $.ajax({
        type: 'POST',
        url: '/api/server',
        contentType:'application/json',
        dataType: 'json',
        data: JSON.stringify(data),
        success: function (data) {
            alert(data);
            window.location.href = '/server';
        },
        error : function (err) {
            alert(err);
        }
    })
}