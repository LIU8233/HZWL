<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>车辆设置</title>
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
		$('#addCarWindow').window("open");
	}

	function doView() {
		//获得选中的行
		var rows = $("#mydatagrid").datagrid("getSelections");
		if (rows.length == 1) {
			
			$('#editCarWindow').window("open");
		
			for (var i = 0; i < rows.length; i++) {
				
				$("#editCarForm").form("load", rows[i]);
			} 
			
		} else {
			
			//提示
			$.messager.alert("提示信息", "请选择一条记录进行修改！", "warning");
			
		}
	}

	function doSearch() {
		$('#searchWindow').window("open");
	}

	function doDelete() {
		//获得选中的行
		var rows = $("#mydatagrid").datagrid("getSelections");
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
			window.location.href = '${pageContext.request.contextPath}/car_delete.action?ids='
					+ ids;
		}
	}

	//工具栏
	var toolbar = [ {
		id : 'button-view',
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-edit',
		text : '修改',
		iconCls : 'icon-edit',
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
	} ];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'cartype',
		title : '车型',
		width : 120,
		align : 'center'
	}, {
		field : 'carnumber',
		title : '车牌',
		width : 120,
		align : 'center'
	}, {
		field : 'carname',
		title : '车名',
		width : 120,
		align : 'center'
	}, {
		field : 'driver',
		title : '司机',
		width : 120,
		align : 'center'
	} , {
		field : 'nuclearload',
		title : '核载(千克)',
		width : 120,
		align : 'center'
	}, {
		field : 'mycheck',
		title : '是否已检验',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			if (data == "1") {
				return "是";
			} else {
				return "否";
			}
		}
	}] ];

	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility : "visible"
		});

		// 收派标准数据表格
		$('#mydatagrid')
				.datagrid(
						{
							iconCls : 'icon-forward',
							fit : true,
							border : false,
							rownumbers : true,
							striped : true,
							pageList : [ 30, 50, 100 ],
							pagination : true,
							toolbar : toolbar,
							url : "${pageContext.request.contextPath }/car_pageQuery.action",
							idField : 'id',
							columns : columns,
							onDblClickRow : doDblClickRow
						});

		// 添加、修改区域窗口
		$('#addCarWindow').window({
			title : '添加修改区域',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});

		//修改区域窗口
		$('#editCarWindow').window({
			title : '添加修改区域',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		
		//查询区域窗口
		$('#searchWindow').window({
			title : '查询窗口',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});

	});

	//双击事件处理函数，回显数据
	function doDblClickRow(rowIndex, rowData) {
		
		$('#editCarWindow').window("open");
		
		$("#editCarForm").form("load", rowData);
	}

	//扩展校验规则
	$(function() {
		var reg = /^[0-9]{6}$/;
		$.extend($.fn.validatebox.defaults.rules, {
			postcodeNumber : {
				validator : function(value, param) {
					return reg.test(value);
				},
				message : '邮编输入有误！'
			}
		});
	});
</script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
		<table id="mydatagrid"></table>
	</div>

	<div class="easyui-window" title="车辆添加" id="addCarWindow"
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
							var v = $("#addCarForm").form("validate");
							if (v) {
								//校验通过，提交表单
								$("#addCarForm").submit();
								$('#editCarWindow').window("close");
							}
						});
					});
				</script>
			</div>
		</div>

		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addCarForm"
				action="${pageContext.request.contextPath }/car_addcar.action"
				method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">车辆信息</td>
					</tr>
					<tr>
						<td>车型</td>
						<td><input type="text" name="cartype" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>车牌</td>
						<td><input type="text" name="carnumber"
							class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>车名</td>
						<td><input type="text" name="carname"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>司机</td>
						<td><input type="text" name="driver"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>核载</td>
						<td><input type="text" name="nuclearload"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="checkbox" name="mycheck"
							value="1" /> 是否已检验</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	
	//区域编辑窗口
	<div class="easyui-window" title="车辆添加" id="editCarWindow"
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
							var v = $("#editCarForm").form("validate");
							if (v) {
								//校验通过，提交表单
								$("#editCarForm").submit();
								$('#editCarWindow').window("close");
							}
						});
					});
				</script>
			</div>
		</div>

		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editCarForm"
				action="${pageContext.request.contextPath }/car_editCar.action"
				method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">车辆信息</td>
						<input type="hidden" name="id">
					</tr>
					<tr>
						<td>车型</td>
						<td><input type="text" name="cartype" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>车牌</td>
						<td><input type="text" name="carnumber"
							class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>车名</td>
						<td><input type="text" name="carname"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>司机</td>
						<td><input type="text" name="driver"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>核载</td>
						<td><input type="text" name="nuclearload"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="checkbox" name="mycheck"
							value="1" /> 是否已检验</td>
					</tr>
				</table>
			</form>
		</div>
	</div>


	//区域查询窗口
	<div class="easyui-window" title="车辆查询" id="searchWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="search" icon="icon-search" href="#" class="easyui-linkbutton"
					plain="true">查询</a>
			</div>
		</div>
		<script type="text/javascript">
			$(function() {

				//工具方法，可以将指定的表单中的输入项目序列号为json数据
				$.fn.serializeJson = function() {
					var serializeObj = {};
					var array = this.serializeArray();
					$(array).each(
							function() {
								if (serializeObj[this.name]) {
									if ($.isArray(serializeObj[this.name])) {
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
					$("#mydatagrid").datagrid("load", p);
					//关闭查询窗口
					$("#searchWindow").window("close");
				});
			});
		</script>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm"
				action="${pageContext.request.contextPath }/car_pageQuery.action"
				method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>车型</td>
						<td><input type="text" name="cartype" class="easyui-validatebox"
							/></td>
					</tr>
					<tr>
						<td>车牌</td>
						<td><input type="text" name="carnumber"
							class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>车名</td>
						<td><input type="text" name="carname"
							class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>司机</td>
						<td><input type="text" name="driver"
							class="easyui-validatebox" /></td>
					</tr>
					<tr>
					
				</table>
			</form>
		</div>
	</div>

</body>
</html>