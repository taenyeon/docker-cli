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
            createDockerFileModal(data.dockerFile);
            createContainerCreateModal(data.images);
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
                        '<span class="font-weight-bold text-success">' + "RUNNING" + '</span>'
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
                    return '<button id="deleteContainer" data-id="'+data+'" class="btn btn-sm btn-danger btn-icon-split">' +
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
    let runningStatComponent = $('#runningStat');
    runningStatComponent.css('width', runningStat + '%');
    runningStatComponent.text(runningStat + '%');
    let exitStatComponent = $('#exitStat');
    exitStatComponent.css('width', exitStat + '%')
    exitStatComponent.text(exitStat + '%');
}

function settingServerInfo(server) {
    $('#name').text(server.name);
    $('#url').text('localhost' + server.url);
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

$('#uploadDockerFile').click(function () {
    uploadDockerFile();
});

function uploadDockerFile() {
    let dockerFile = $('#dockerFile')[0];
    if (dockerFile.files.length === 1) {
        let formData = new FormData();
        formData.append("dockerFile", dockerFile.files[0]);
        $.ajax({
            type: 'POST',
            url: '/dockerfile/' + serverName,
            processData: false,
            contentType: false,
            data: formData,
            success: function (data) {
                alert(data);
                window.location.reload();
            },
            error: function (err) {
                alert(err);
            }
        })
    } else {
        alert('파일을 1개 선택하여주세요.')
    }
}

function createDockerFileModal(dockerFile) {
    $("#dockerfilePayload").text(dockerFile.payload);
}

function createContainerCreateModal(images) {
    images.forEach(image => {
        let option = '<option value="' + image.id + '">' + image.repository + ':' + image.tag + '</option>';
        $('#selectedImageId').append(option)
    })
}

$('#createContainerBtn').click(function () {
    let selectedImageId = $('#selectedImageId').val()[0];
    alert(selectedImageId);
    $.ajax({
        type: 'POST',
        url: '/api/container',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            imageId : selectedImageId,
            serverName: serverName
        }),
        success: function (data) {
            alert(data);
            window.location.reload();
        },
        error: function (err) {
            alert(err);
        }
    });
});

$('#deleteContainer').click(function (){
   let containerId = $(this).attr('data-id');
    alert(containerId);
    $.ajax({
        type: 'DELETE',
        url: '/api/container/' + containerId,
        success: function (data) {
            alert(data);
            window.location.reload();
        },
        error: function (err) {
            alert(err);
        }
    });
});

