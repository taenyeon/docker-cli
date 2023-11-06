$(function () {
    loadContainer();
})

let status = 'stop';
function loadContainer() {

    $.ajax({
        type: 'GET',
        url: '/api/container/' + containerId,
        contentType: 'application/json',
        dataType: 'json',
        success: function (container) {
            status = container.state.running === true ? 'stop' : 'start';
            // data 세팅 필요.
            settingTitle(container.name);
            settingContainerInfo(container);
            settingStatInfo(container.stat);
            settingStateInfo(container.state);
            settingConfigInfo(container.config);
            createEnvTable(container.config.env);
            settingNetworkSettingsInfo(container.networkSettings);
            if (container.stat != null) {
            settingMemoryStat(container.stat);
            }
        },
        error: function (err) {
            alert(err);
        }
    })
}

function settingTitle(name) {
    $('#mainTitle').text(name);
    $('#subTitle').text(name);
}

function settingMemoryStat(stat) {
    $('#memoryPer').text(stat.memory.percent + '%');
    $('#memoryPer').css('width',stat.memory.percent + '%');

    $('#cpuPerStat').text(stat.cpuPer + '%');
    $('#cpuPerStat').css('width',stat.cpuPer + '%');
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
        $('#running').text('Running');
        $('#running').addClass('text-success');
        $('#statusChangeBtn').addClass('btn btn-sm btn-icon-split btn-warning');
        $('#statusChangeText').text('Stop');
    } else {
        $('#running').text('Stopped');
        $('#running').addClass('text-warning');
        $('#statusChangeBtn').addClass('btn btn-sm btn-icon-split btn-success');
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
    if (stat != null) {
    $('#memory').text(stat.memory.raw);
    $('#cpuPer').text(stat.cpuPer + ' %');
    }
}

function settingNetworkSettingsInfo(networkSettings){
    $('#gateway').text(networkSettings.gateway);
    $('#ipAddress').text(networkSettings.ipAddress);
    alert(JSON.stringify(Array.from( networkSettings.ports.entries())));
    if (networkSettings.ports.length > 0) {
    let datas = [];
    networkSettings.ports.forEach(function (value, index, array) {
        let data = {
            key : value,
            value : index
        };
        datas.push(data);
    } );

    $("#ports").DataTable({
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
}

$('#statusChangeBtn').click(function () {
    if (confirm('Are you sure you want to '+status+' it?')) {
        $.ajax({
            type: 'GET',
            url : '/api/container/'+containerId+'/'+ status,
            success: function (data) {
                alert(data);
                window.location.reload();
            },
            error: function (err) {
                alert(err);
            }
        });
    }
});

$('#deleteContainerBtn').click(function () {
    if (confirm('Are you sure you want to delete it?')) {
        deleteServer();
    }
});

function deleteServer() {
    let serverName = $('#mainTitle').text();
    $.ajax({
        type: 'DELETE',
        url: '/api/container/' + containerId,
        success: function (data) {
            alert(data);
            window.location.href = '/server/' + serverName;
        },
        error: function (err) {
            alert(err);
        }
    })
}