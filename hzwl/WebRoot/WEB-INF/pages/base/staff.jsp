<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
	function doAdd() {
		//alert("增加...");
		$('#addStaffWindow').window("open");
	}

	function doView() {
		$('#searchWindow').window("open");
	}

	//批量删除取派员
	function doDelete() {
		//获得选中的行
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length == 0) {
			//没有选中，提示
			$.messager.alert("提示信息", "请选择需要删除的记录！", "warning");
		} else {
			var array = new Array();
			//选中了记录,获取选中行的id
			for (var i = 0; i < rows.length; i++) {
				var id = rows[i].id;
				array.push(id);
			}
			var ids = array.join(",");
			//发送请求，传递ids参数
			window.location.href = '${pageContext.request.contextPath}/staff_deleteStaff.action?ids='
					+ ids;
		}
	};

	function doEdit() {
		//获得选中的行
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length == 1) {
			
			$('#editStaffWindow').window("open");
		
			for (var i = 0; i < rows.length; i++) {
				
				$("#editStaffForm").form("load", rows[i]);
			} 
			
		} else {
			
			//提示
			$.messager.alert("提示信息", "请选择一条记录进行修改！", "warning");
			
		}
		
		
	};
	
	//工具栏
	var toolbar = [ {
		id : 'button-view',
		text : '查询',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}, {
		id : 'button-edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : doEdit
	} ];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center'
	}, {
		field : 'telephone',
		title : '手机号',
		width : 120,
		align : 'center'
	}, {
		field : 'haspda',
		title : '是否有PDA',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			if (data == "1") {
				return "有";
			} else {
				return "无";
			}
		}
	}, {
		field : 'standard',
		title : '取派标准',
		width : 120,
		align : 'center'
	}, {
		field : 'station',
		title : '所谓单位',
		width : 200,
		align : 'center'
	} ] ];

	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility : "visible"
		});

		// 取派员信息表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,//显示行号
			striped : true,
			pageList : [ 3, 5, 10 ],
			pagination : true,
			toolbar : toolbar,//工具栏
			url : "${pageContext.request.contextPath}/staff_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		//指定数据表格的双击行事件
		});

		// 添加取派员窗口
		$('#addStaffWindow').window({
			title : '添加取派员',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});

		// 修改取派员窗口
		$('#editStaffWindow').window({
			title : '修改取派员',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});

		// 查询员窗口
		$('#searchWindow').window({
			title : '查询取派员',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 300,
			resizable : false
		});

	});

	//双击事件处理函数，回显数据
	function doDblClickRow(rowIndex, rowData) {
		
		$('#editStaffWindow').window("open");
		$("#editStaffForm").form("load", rowData);
	}

	//扩展校验规则
	$(function() {
		var reg = /^1[3|4|5|7|8|9][0-9]{9}$/;
		$.extend($.fn.validatebox.defaults.rules, {
			phonenumber : {
				validator : function(value, param) {
					return reg.test(value);
				},
				message : '手机号输入有误！'
			}
		});
	});
</script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
		<table id="grid"></table>
	</div>

	//添加窗口
	<div class="easyui-window" title="对收派员进行添加" id="addStaffWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
				<script type="text/javascript">
					$(function() {
						//绑定事件
						$("#save").click(function() {
							//校验表单输入项
							var v = $("#addStaffForm").form("validate");
							if (v) {
								//校验通过，提交表单
								$("#addStaffForm").submit();
							}
						});
					});
				</script>
			</div>
		</div>

		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addStaffForm"
				action="${pageContext.request.contextPath }/staff_addStaff.action"
				method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->

					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone"
							class="easyui-validatebox" required="true"
							data-options="validType:'phonenumber'" /></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="checkbox" name="haspda"
							value="1" /> 是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td><input type="text" name="standard"
							class="easyui-validatebox" required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>



	<!-- 修改窗口 -->
	<div class="easyui-window" title="对收派员进行修改" id="editStaffWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
				<script type="text/javascript">
					$(function() {
						//绑定事件
						$("#edit").click(function() {
							//校验表单输入项
							var v = $("#editStaffForm").form("validate");
							if (v) {
								//校验通过，提交表单
								$("#editStaffForm").submit();
							}
						});
					});
				</script>
			</div>
		</div>

		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editStaffForm"
				action="${pageContext.request.contextPath }/staff_editStaff.action"
				method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->

					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone"
							class="easyui-validatebox" required="true"
							data-options="validType:'phonenumber'" /></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="checkbox" name="haspda"
							value="1" /> 是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td><input type="text" name="standard"
							class="easyui-validatebox" required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>


	<!-- 查询窗口 -->
	<div class="easyui-window" title="对收派员进行查询" id="searchWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="search" icon="icon-search" href="#" class="easyui-linkbutton"
					plain="true">查询</a>
				<script type="text/javascript">
					$(function() {

						//工具方法，可以将指定的表单中的输入项目序列号为json数据
						$.fn.serializeJson = function() {
							var serializeObj = {};
							var array = this.serializeArray();
							$(array)
									.each(
											function() {
												if (serializeObj[this.name]) {
													if ($
															.isArray(serializeObj[this.name])) {
														serializeObj[this.name]
																.push(this.value);
													} else {
														serializeObj[this.name] = [
																serializeObj[this.name],
																this.value ];
													}
												} else {
													serializeObj[this.name] = this.value;
												}
											});
							return serializeObj;
						};

						//绑定事件
						$("#search").click(function() {
							var p = $("#searchForm").serializeJson();//{id:xx,name:yy,age:zz}
							//重新发起ajax请求，提交参数
							$("#grid").datagrid("load", p);
							//关闭查询窗口
							$("#searchWindow").window("close");
						});
					});
				</script>
			</div>
		</div>

		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm"
				action="${pageContext.request.contextPath }/staff_pageQuery.action"
				method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->

					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone"
							class="easyui-validatebox" data-options="validType:'phonenumber'" /></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station"
							class="easyui-validatebox" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>
