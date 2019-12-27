var prefix = "/common/dict"
$(function () {
    load();
});

// 通用方法封装处理
var common = {
    // 判断字符串是否为空
    isEmpty: function (value) {
        if (value == null || this.trim(value) == "") {
            return true;
        }
        return false;
    },
    // 判断一个字符串是否为非空串
    isNotEmpty: function (value) {
        return !$.common.isEmpty(value);
    },
    // 空对象转字符串
    nullToStr: function (value) {
        if ($.common.isEmpty(value)) {
            return "-";
        }
        return value;
    },
    // 是否显示数据 为空默认为显示
    visible: function (value) {
        if ($.common.isEmpty(value) || value == true) {
            return true;
        }
        return false;
    },
    // 空格截取
    trim: function (value) {
        if (value == null) {
            return "";
        }
        return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
    },
    // 比较两个字符串（大小写敏感）
    equals: function (str, that) {
        return str == that;
    },
    // 比较两个字符串（大小写不敏感）
    equalsIgnoreCase: function (str, that) {
        return String(str).toUpperCase() === String(that).toUpperCase();
    },
    // 将字符串按指定字符分割
    split: function (str, sep, maxLen) {
        if ($.common.isEmpty(str)) {
            return null;
        }
        var value = String(str).split(sep);
        return maxLen ? value.slice(0, maxLen - 1) : value;
    },
    // 字符串格式化(%s )
    sprintf: function (str) {
        var args = arguments, flag = true, i = 1;
        str = str.replace(/%s/g, function () {
            var arg = args[i++];
            if (typeof arg === 'undefined') {
                flag = false;
                return '';
            }
            return arg;
        });
        return flag ? str : '';
    },
    // 指定随机数返回
    random: function (min, max) {
        return Math.floor((Math.random() * max) + min);
    },
    // 判断字符串是否是以start开头
    startWith: function (value, start) {
        var reg = new RegExp("^" + start);
        return reg.test(value)
    },
    // 判断字符串是否是以end结尾
    endWith: function (value, end) {
        var reg = new RegExp(end + "$");
        return reg.test(value)
    },
    // 数组去重
    uniqueFn: function (array) {
        var result = [];
        var hashObj = {};
        for (var i = 0; i < array.length; i++) {
            if (!hashObj[array[i]]) {
                hashObj[array[i]] = true;
                result.push(array[i]);
            }
        }
        return result;
    },
    // 数组中的所有元素放入一个字符串
    join: function (array, separator) {
        if ($.common.isEmpty(array)) {
            return null;
        }
        return array.join(separator);
    },
    // 获取form下所有的字段并转换为json对象
    formToJSON: function (formId) {
        var json = {};
        $.each($("#" + formId).serializeArray(), function (i, field) {
            if (json[field.name]) {
                json[field.name] += ("," + field.value);
            } else {
                json[field.name] = field.value;
            }
        });
        return json;
    },
    // 获取obj对象长度
    getLength: function (obj) {
        var count = 0;
        for (var i in obj) {
            if (obj.hasOwnProperty(i)) {
                count++;
            }
        }
        return count;
    }
};

function selectLoad() {
    var html = "";
    $.ajax({
        url: '/common/dict/type',
        success: function (data) {
            //加载数据
            for (var i = 0; i < data.length; i++) {
                html += '<option value="' + data[i].type + '">' + data[i].description + '</option>'
            }
            $(".chosen-select").append(html);
            $(".chosen-select").chosen({
                maxHeight: 200
            });
            //点击事件
            $('.chosen-select').on('change', function (e, params) {
                console.log(params.selected);
                var opt = {
                    query: {
                        type: params.selected,
                    }
                }
                $('#exampleTable').bootstrapTable('refresh', opt);
            });
        }
    });
}

function load() {
    selectLoad();
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams: function (params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset: params.offset,
                        // name:$('#searchName').val(),
                        type: $('#searchName').val(),
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns: [
                    {
                        checkbox: true
                    },
                    {
                        field: 'id',
                        title: '编号'
                    },
                    {
                        field: 'name',
                        title: '标签名'
                    },
                    {
                        field: 'value',
                        title: '数据值',
                        width: '100px'
                    },
                    {
                        field: 'type',
                        title: '类型'
                    },
                    {
                        field: 'description',
                        title: '描述'
                    },
                    {
                        visible: false,
                        field: 'sort',
                        title: '排序（升序）'
                    },
                    {
                        visible: false,
                        field: 'parentId',
                        title: '父级编号'
                    },
                    {
                        visible: false,
                        field: 'createBy',
                        title: '创建者'
                    },
                    {
                        visible: false,
                        field: 'createDate',
                        title: '创建时间'
                    },
                    {
                        visible: false,
                        field: 'updateBy',
                        title: '更新者'
                    },
                    {
                        visible: false,
                        field: 'updateDate',
                        title: '更新时间'
                    },
                    {
                        visible: false,
                        field: 'remarks',
                        title: '备注信息'
                    },
                    {
                        visible: false,
                        field: 'delFlag',
                        title: '删除标记'
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            var f = '<a class="btn btn-success btn-sm ' + s_add_h + '" href="#" title="增加"  mce_href="#" onclick="addD(\''
                                + row.type + '\',\'' + row.description
                                + '\')"><i class="fa fa-plus"></i></a> ';
                            return e + d + f;
                        }
                    }]
            });
}

function reLoad() {
    var opt = {
        query: {
            type: $('.chosen-select').val(),
        }
    }
    $('#exampleTable').bootstrapTable('refresh', opt);
}

function add() {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add' // iframe的url
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function addD(type, description) {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add/' + type + '/' + description // iframe的url
    });
}

function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {
    });
}

// 导出数据
function exportExcel() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要导出的数据");
        return;
    }
    layer.confirm("确定导出'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        layer.load("正在导出数据，请稍后...");
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/export',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    window.location.href = prefix + "/downloadExcel?fileName=" + encodeURI(r.msg) + "&delete=" + true;
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {
    });
}

// 导入数据
function importExcel() {

    layer.open({
        type: 1,
        area: ['400px', '230px'],
        fix: false,
        //不固定
        maxmin: true,
        shade: 0.3,
        title: '导入数据',
        content: $('#importTpl').html(),
        btn: ['<i class="fa fa-check"></i> 导入', '<i class="fa fa-remove"></i> 取消'],
        // 弹层外区域关闭
        shadeClose: true,
        btn1: function (index, layero) {
            var file = layero.find('#file').val();
            if (file == '' || (!common.endWith(file, '.xls') && !common.endWith(file, '.xlsx'))) {
                layer.open("请选择后缀为 “xls”或“xlsx”的文件。");
                return false;
            }
            var index = layer.load(2, {shade: false});
            var formData = new FormData();
            formData.append("file", $('#file')[0].files[0]);
            formData.append("updateSupport", $("input[name='updateSupport']").is(':checked'));
            $.ajax({
                url: prefix + '/importExcel',
                data: formData,
                cache: false,
                contentType: false,
                processData: false,
                type: 'POST',
                success: function (result) {
                    if (result.code == 0) {
                        layer.msg(result.msg);
                        reLoad();
                    } else {
                        layer.close(index);
                        layer.msg(result.msg);
                    }
                }
            });
        }
    });
}
