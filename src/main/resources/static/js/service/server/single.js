$(function () {
    loadServer();
})


function loadServer() {

    $.ajax({
        type: 'GET',
        url: '/api/server/' + serverName,
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            // data 세팅 필요.
            settingContainerStat(data.containers);
            settingServerInfo(data.server);
            createContainerTable(data.containers);
            createImageTable(data.images);
        },
        error: function (err) {
            alert(err);
        }
    })
}

function createImageTable(images) {
    $("#imageTable").DataTable({
        "processing": true,
        "paging": false,
        "searching": false,
        "ordering": true,
        "info": false,
        "lengthChange": false,
        "data": images,
        "columns": [
            {
                "data": "id",
                render: function (data) {
                    return '<a href=/server/' + data + '>' + data + '</a>';
                }
            },
            {"data": "repository"},
            {"data": "tag"},
            {"data": "size"},
        ]
    });
}

function createContainerTable(containers) {
    containers.forEach(container => {
        console.log(container.created);
        container.created = new Date(container.created);
    });
    $("#containerTable").DataTable({
        "processing": true,
        "paging": false,
        "searching": false,
        "ordering": true,
        "info": false,
        "lengthChange": false,
        "data": containers,
        "columns": [
            {
                "data": "state.running",
                render: function (data) {
                    return data === true ?
                        '<span class="font-weight-bold text-primary">' + "RUNNING" + '</span>'
                        :
                        '<span class="font-weight-bold text-danger">' + "EXIT" + '</span>'
                },
                className: "text-center"
            },
            {
                "data": "name",
                className: "text-center font-weight-bold"
            },
            {
                "data": "id",
                render: function (data) {
                    return '<a href=/container/' + data + '>' + data + '</a>';
                }
            },
            {
                "data": "config.image",
                render: function (data) {
                    return '<a href=/image/' + data + '>' + data + '</a>';
                }
            },
            {
                "data": "platform",
                className: "text-center"
            },
            {"data": "created"},
            {
                "data": "id",
                render: function (data) {
                    return '<button class="btn btn-warning btn-icon-split">' +
                        '<span class="text">Stop</span>' +
                        '</button>'
                },
                className: "text-center"
            },
            {
                "data": "id",
                render: function (data) {
                    return '<button class="btn btn-danger btn-icon-split">' +
                        '<span class="text">Delete</span>' +
                        '</button>'
                },
                className: "text-center"
            },
        ]
    });
}

function settingContainerStat(containers) {
    let total = containers.length;
    console.log(total);
    let running = 0;
    let exit = 0;
    containers.forEach(container => {
        if (container.state.running === true) {
            running = running + 1;
        } else {
            exit = exit + 1;
        }
    });
    let runningStat = (running / total) * 100;
    let exitStat = (exit / total) * 100;
    console.log(runningStat);
    console.log(exitStat);
    let runningStatComponent = $('#runningStat');
    runningStatComponent.css('width', runningStat + '%');
    runningStatComponent.text(runningStat + '%');
    let exitStatComponent = $('#exitStat');
    exitStatComponent.css('width', exitStat + '%')
    exitStatComponent.text(exitStat + '%');
}

function settingServerInfo(server) {
    $('#name').text(server.name);
    $('#url').text(server.url);
    $('#serverType').text(server.serverType);
    $('#managerId').text(server.managerId);
}

$('#modifyServerBtn').click(function () {
    if (confirm('Are you sure you want to modify it?')) {
    window.location.href = '/server/' + serverName + '/modify';
    }
});

$('#deleteServerBtn').click(function () {
    if (confirm('Are you sure you want to delete it?')) {
        deleteServer();
    }
});

function deleteServer() {
    $.ajax({
        type: 'DELETE',
        url: '/api/server/' + serverName,
        success: function (data) {
            alert(data);
            window.location.href = '/server';
        },
        error: function (err) {
            alert(err);
        }
    })
}