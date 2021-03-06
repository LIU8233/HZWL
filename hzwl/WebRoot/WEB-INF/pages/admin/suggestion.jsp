<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程定义列表</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#grid")
				.datagrid(
						{
							striped : true,
							rownumbers : true,
							singleSelect : true,
							fitColumns : true,
						});
	});

	//定义删除方法
	function del(id) {
		$.messager
				.confirm(
						"确认信息",
						"你确定删除吗？",
						function(r) {
							if (r) {
								//发送请求，删除数据
								window.location.href = '${pageContext.request.contextPath}/bug_delete?id='
										+ id;
							}
						});
	}
	
	function edithandle(id){
		
		window.location.href = '${pageContext.request.contextPath}/bug_edit?id='
			+ id;
	}
</script>

</head>
<body class="easyui-layout">
	<div region="center">
		<table id="grid" class="easyui-datagrid">
			<thead>
				<tr>
					<th data-options="field:'id'" width="10">编号</th>
					<th data-options="field:'idea'" width="100">问题与意见</th>
					<th data-options="field:'handle'" width="15">是否已处理</th>
					<th data-options="field:'viewpng'" width="50">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="list" var="processDefinition">
					<!-- 在循环过程中 ，将  processDefinition 对象，同时放入 root 和 map 中-->
					<tr>
						<td>
							<%--
  							<s:property value="id"/> <!-- 从root找 -->
	  						<s:property value="#processDefinition.id"/> <!-- 从map找 -->
  						 --%> ${id }
						</td>
						<td><s:property value="idea" /></td>
						<td>${handle==0?"否":"是"}</td>
						
						<td>
						<a onclick="edithandle('${id}')"
							class="easyui-linkbutton" data-options="iconCls:'icon-edit'">
								处理 </a> 
								<a onclick="del('${id}')" class="easyui-linkbutton"
							data-options="iconCls:'icon-remove'"> 删除 </a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript">
	var deltag = '${deltag}';
	if (deltag == '1') {
		//存在关联数据，不能删除
		$.messager.alert("提示信息", "当前流程定义正在使用，不能删除！", "warning");
	}
</script>
</html>