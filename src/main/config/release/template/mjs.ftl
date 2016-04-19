$(function() {
	$('#form_dlg').dialog({
		onClose:function(){
			$('#fm').form('clear');
		}
	});
	
	$('#list').datagrid({
        url:getUrl('${baseUrl}/query'),
        fit:true,
//        title:'${modelName}',
        border:false,
        pagination:true,
        pageSize:20,
        rownumbers:true,
        striped:true,
        fitColumns:true,
        checkOnSelect:true,
        selectOnCheck:true,<#if multiSelect??><#else>singleSelect:true,</#if>
        toolbar:'#list_toolbar',
        pageList:[10,15,20,30,40],
        idField:'id',
        remoteSort:false,
        columns:[[
        <#if multiSelect??>
        	{field:'id',checkbox:true},
        </#if>
	 <#list fieldList as conf>
		<#if (conf.javaProperty4List)??>
			{field:'${conf.javaProperty4List}',title:'${conf.chinenies4List}',align:'left',width:<#if (conf.isGtOne == "true")>${conf.colWidthInt}<#else>$('#list').width() * ${conf.colWidth}</#if>},
	 	</#if>
	 </#list>
        ]],
        onBeforeLoad:function() {
        	var options = $('#list').datagrid('options');
    		var queryParams=options.queryParams;
            return true;
        },
        onLoadSuccess:function(data) {
        	if(data.message){//message存在表示后台没有正常运行
        		Msg.alert(data.message);
        	}
        },
		onLoadError: function(response) {
			MyTool.parseResponse(
				response.responseText, 
        		{
        			failureMsg: MyLocal.BaseMainPaeg.list.onLoadError_parseResponse_failureMsg
        		}
    		);
		}
    });
});
/**
 * 条件查询，刷新datagrid
 * @returns {Boolean}
 */
function queryWithParams() {
	var options = $('#list').datagrid('options');
	var queryParams=options.queryParams;
	$('#list').datagrid('load');
}

var url;
/**
 * 打开新增表单对话框
 */
function openAddFormWindow() {
    $('#fm').form('clear');
    $('#form_dlg').dialog('open').dialog('setTitle', MyLocal.BaseMainPaeg.openAddFormWindow_title);
	url = getUrl('${baseUrl}/save');
}

/**
 * 打开修改表单对话框
 */
function openUpdateFormWindow() {
    var record = $('#list').datagrid('getSelected');
    if (record) {
        $('#form_dlg').dialog('open').dialog('setTitle', MyLocal.BaseMainPaeg.openUpdateFormWindow_title);
        $('#fm').form('load',record);
        url = getUrl('${baseUrl}/update?id=' + record.id);
        return;
    }
    Msg.alert(MyLocal.BaseMainPaeg.noSelectedRowMsg.u);
}

/**
 * 新增、修改表单提交
 */
function submitForm() {
	var mask = undefined;
    $('#fm').form('submit', {
        url: url,
        onSubmit: function() {
        	if ($(this).form('validate')) {
        		mask = new Mask().show();
        		return true;
        	}
            return false;
        },
        success: function(responseText) {
        	mask.hide();
            if (MyTool.parseResponse(responseText, 
            		{
            			successMsg: MyLocal.BaseMainPaeg.save_form_parseResponse_successMsg,
            			failureMsg: MyLocal.BaseMainPaeg.save_form_parseResponse_failureMsg
        			}
    			))
			{
                $('#form_dlg').dialog('close');
                $('#list').datagrid('reload');
            }
        }
    });
}

/**
 * 删除
 */
function deleteRecord() {
    var record = $('#list').datagrid('getSelected');
    if (record) {
        $.messager.confirm(
        	MyLocal.BaseMainPaeg.delete_confirm_title,
        	MyLocal.BaseMainPaeg.delete_confirm_msg,
	        function(flag) {
	            if (flag) {
	            	var mask = new Mask().show();
	                $.post(
	               		getUrl('${baseUrl}/delete'),
	               		{id:record.id},
	               		function(response) {
	               			mask.hide();
	               			if (MyTool.parseResponse(response)) {
                				$('#list').datagrid('reload');
	               			}
	                	},
	               		'json'
	               	);
	            }
	        }
        );
        return;
    }
    Msg.alert(MyLocal.BaseMainPaeg.noSelectedRowMsg.d);
}

/**
 * 查看详情
 */
function showDetail() {
    var record = $('#list').datagrid('getSelected');
    if (record) {
        $('#detail_dlg').dialog('open').dialog('setTitle', 
        	MyLocal.BaseMainPaeg.showDetail_title);
        for (var prop in record) {
            $('#d_' + prop).html(record[prop]);
        }
        return;
    }
}

/**
 * 重置查询表单
 */
function resetForm() {
	$('#fm').form('clear');
}