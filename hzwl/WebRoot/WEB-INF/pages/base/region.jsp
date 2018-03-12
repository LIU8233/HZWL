<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域设置</title>
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
		$('#addRegionWindow').window("open");
	}

	function doView() {
		//获得选中的行
		var rows = $("#mydatagrid").datagrid("getSelections");
		if (rows.length == 1) {
			
			$('#editWindow').window("open");
		
			for (var i = 0; i < rows.length; i++) {
				
				/* $("input[name=province]").val(rows[i].province);
				$("input[name=city]").val(rows[i].city);
				$("input[name=district]").val(rows[i].district);
				$("input[name=postcode]").val(rows[i].postcode); */
				$("#editForm").form("load", rows[i]);
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
			window.location.href = '${pageContext.request.contextPath}/region_deleteRegion.action?ids='
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
	}, {
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo'
	} ];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'province',
		title : '省',
		width : 120,
		align : 'center'
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center'
	}, {
		field : 'district',
		title : '县  / 区',
		width : 120,
		align : 'center'
	}, {
		field : 'postcode',
		title : '邮编',
		width : 120,
		align : 'center'
	} ] ];

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
							url : "${pageContext.request.contextPath }/region_pageQuery.action",
							idField : 'id',
							columns : columns,
							onDblClickRow : doDblClickRow
						});

		// 添加、修改区域窗口
		$('#addRegionWindow').window({
			title : '添加修改区域',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});

		//修改区域窗口
		$('#editWindow').window({
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
		
		$('#editWindow').window("open");
		
		$("#editForm").form("load", rowData);
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

	//区域添加窗口
	<div class="easyui-window" title="区域添加" id="addRegionWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
			</div>
		</div>
		<script type="text/javascript">
			$(function() {
				//绑定事件
				$("#save").click(function() {
					//校验表单输入项
					var v = $("#addForm").form("validate");
					if (v) {
						//校验通过，提交表单
						$("#addForm").submit();

						$.messager.alert("提示信息", "添加成功！", "info");
					}
				});
			});
		</script>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addForm"
				action="${pageContext.request.contextPath }/region_saveRegion.action"
				method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="province"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>县&nbsp/&nbsp区</td>
						<td><input type="text" name="district"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode"
							class="easyui-validatebox"
							data-options="validType:'postcodeNumber'" required="true" /></td>
					</tr>

				</table>
			</form>
		</div>
	</div>

	//区域编辑窗口
	<div class="easyui-window" title="区域添加" id="editWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton"
					plain="true">修改</a>
			</div>
		</div>
		<script type="text/javascript">
			$(function() {
				//绑定事件
				$("#edit").click(function() {
					//校验表单输入项
					var v = $("#editForm").form("validate");
					if (v) {
						//校验通过，提交表单
						$("#editForm").submit();

						$.messager.alert("提示信息", "修改成功！", "info");
					}
				});
			});
		</script>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editForm"
				action="${pageContext.request.contextPath }/region_updateRegion.action"
				method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="province"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>县&nbsp/&nbsp区</td>
						<td><input type="text" name="district"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode"
							class="easyui-validatebox"
							data-options="validType:'postcodeNumber'" required="true" /></td>
					</tr>

				</table>
			</form>
		</div>
	</div>


	//区域查询窗口
	<div class="easyui-window" title="区域添加" id="searchWindow"
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
				action="${pageContext.request.contextPath }/region_pageQuery.action"
				method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="province"
							class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>县&nbsp/&nbsp区</td>
						<td><input type="text" name="district"
							class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode"
							class="easyui-validatebox" /></td>
					</tr>
					
				</table>
			</form>
		</div>
	</div>

</body>
</html>