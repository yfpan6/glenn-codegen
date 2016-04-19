<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../common/common.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${"$"}{ctx}/resources/shared/css/common_form.css"/>
<script type="text/javascript" src="${"$"}{ctx}/resources/${jsPath}/${entityVar}.js"></script>
<title>${modelName}</title>
</head>
<body>
  <!-- 主列表 -->
  <div id="list">
    <!-- 列表顶部操作栏栏 -->
	<div id="list_toolbar">
	  <table cellpadding="0" cellspacing="0">
        <tr>
          <td style="padding-left:2px">
		    <a class="easyui-linkbutton" href="javascript:void(0)" iconCls="icon-add" plain="true" onclick="openAddFormWindow()">新增</a>
			<a class="easyui-linkbutton" href="javascript:void(0)" iconCls="icon-edit" plain="true" onclick="openUpdateFormWindow()">修改</a>
			<a class="easyui-linkbutton" href="javascript:void(0)" iconCls="icon-remove" plain="true" onclick="deleteRecord()">删除</a>
			<a class="easyui-linkbutton" href="javascript:void(0)" iconCls="icon-detail" plain="true" onclick="showDetail()">查看详情</a>
          </td>
          <td><div class="datagrid-btn-separator"></div></td>
          <td style="padding-left:2px">
            <input class="easyui-searchbox" data-options="searcher:queryWithParams, prompt:'查询条件'" style="width:150px"></input>
          </td>
        </tr>
      </table>
    </div>
  </div>

  <!-- 新增、修改弹出窗 -->
  <div class="easyui-dialog" id="form_dlg" style="width:500px;height:400px;padding:10px 20px"
        closed="true" buttons="#form_dlg_btns" modal="true">
    <form id="fm" method="post" novalidate>
      <table class="myform-table">
 	  <#list fieldList as obj>
    	<#if (obj.javaProperty4AddForm)??>
		<tr>
		  <td>${obj.chinenies4AddForm}:</td>
		  <td><input class="<#if (obj.columnType == 'datetime' || obj.columnType == 'date') >easyui-datebox<#elseif (obj.columnType == 'int')>easyui-numberbox<#else>easyui-textbox</#if><#if (obj.requiredInForm)??> easyui-validatebox</#if>" name="${obj.javaProperty4AddForm}"<#if (obj.requiredInForm)??> required="true"</#if>></td>
		</tr>
		</#if>
	</#list>
      </table>
    </form>
    <!-- 新增、修改窗口底部按钮 -->
    <div id="form_dlg_btns">
      <a class="easyui-linkbutton" href="javascript:void(0)" iconCls="icon-ok" onclick="submitForm()">保存</a>
      <a class="easyui-linkbutton" href="javascript:void(0)" iconCls="icon-cancel" onclick="javascript:$('#form_dlg').dialog('close')">取消</a>
    </div>
  </div>

  <!-- 查看详情  -->
  <div id="detail_dlg" class="easyui-dialog" style="width:500px;height:400px;padding:10px 20px"
        closed="true" buttons="#detail_dlg_btns" modal="true">
   	<table id="detail_table" class="myform-table">
      <tbody>
 	  <#list fieldList as obj>
    	<#if (obj.javaProperty4AddForm)??>
   		<tr>
   		  <td>${obj.chinenies4AddForm}:</td>
   		  <td><label id="d_${obj.javaProperty4AddForm}"></label></td>
   		</tr>
		</#if>
	  </#list>
	  </tbody>
   	</table>
    <div id="detail_dlg_btns">
      <a class="easyui-linkbutton" href="javascript:void(0)" iconCls="icon-cancel" onclick="javascript:$('#detail_dlg').dialog('close')">取消</a>
    </div>
  </div>  
</body>
</html>