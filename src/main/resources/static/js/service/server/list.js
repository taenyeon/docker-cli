
$("#dataTable").DataTable({
    "serverSide":true,
    "processing":true,
    "paging": false,
    "searching" : false,
    "ordering" : true,
    "info" : false,
    "lengthChange" : false,
    "ajax":{
        "url": "/api/server",
        "type": "GET",
        "dataSrc": function (res) {
            return res;
        }
    },
    "columns" : [
        {"data":"name",
            render: function (data) {
                return '<a href=/server/' + data+'>'+ data +'</a>';
            }
        },
        {"data":"url"},
        {"data":"serverType"},
        {"data":"managerId"},
    ]
});
