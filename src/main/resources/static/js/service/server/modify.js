$('#addBtn').click(function () {
    addServer();
});

function addServer() {
    let name = $('#name').val();
    let url = $('#url').val();
    let serverType = $('#serverType').val();

    let data = {
        'name': name,
        'url': url,
        'serverType': serverType
    }

    $.ajax({
        type: 'PUT',
        url: '/api/server/' + name,
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(data),
        success: function () {
            window.location.href = '/server';
        },
        error: function (err) {
            alert(err);
        }
    })
}