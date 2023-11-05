$(function () {
    loadContainer();
})


function loadContainer() {

    $.ajax({
        type: 'GET',
        url: '/api/container/' + containerId,
        contentType: 'application/json',
        dataType: 'json',
        success: function (container) {
            // data 세팅 필요.
            settingContainerInfo(container);
            settingStatInfo(container.stat);
            settingStateInfo(container.state);
            settingConfigInfo(container.config);
            createEnvTable(container.config.env);
            settingMemoryStat(container.stat.memory.percent);
        },
        error: function (err) {
            alert(err);
        }
    })
}


function settingMemoryStat(percent) {
    $('#memoryPer').text(percent + '%');
    $('#memoryPer').css('width',percent + '%');
}
function createEnvTable(envs) {
    let datas = [];
    envs.forEach(env => {
        console.log(env);
        let split = env.split("=");
        let data = {
            key : split[0],
            value : split[1]};
        datas.push(data)
    });
    $("#envTable").DataTable({
        "processing": true,
        "paging": false,
        "searching": false,
        "ordering": true,
        "info": false,
        "lengthChange": false,
        "data": datas,
        "columns" : [
            {"data": "key"},
            {"data": "value"},
        ]
    });
}


function settingContainerInfo(container) {
    $('#id').text(container.id);
    $('#name').text(container.name);
    $('#platform').text(container.platform);
    $('#createdAt').text(container.created);
}

function settingStateInfo(state){
    if (state.running === true) {
        $('#running').text('RUN');
        $('#statusChangeBtn').addClass('btn btn-sm btn-icon-split btn-warning');
        $('#statusChangeText').text('OFF');
    } else {
        $('#running').text('OFF');
        $('#statusChangeBtn').addClass('btn btn-sm btn-icon-split btn-primary');
        $('#statusChangeText').text('RUN');
    }
    $('#pid').text(state.pid);
    $('#startedAt').text(state.startedAt);
    $('#finishedAt').text(state.finishedAt);
}

function settingConfigInfo(config){
    $('#user').text(config.user);
    $('#image').text(config.image);
}

function settingStatInfo(stat){
    $('#memory').text(stat.memory.raw);
    $('#cpuPer').text(stat.cpuPer);
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