<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人工调度</title>
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
		
		var ids;
		
		function diaodu() {
			
			//获得选中的行
			var rows = $("#grid").datagrid("getSelections");
			if (rows.length == 0) {
				//没有选中，提示
				$.messager.alert("提示信息", "请选择需要调度的订单！", "warning");
			}else{
				// 弹出窗口
				var array = new Array();
				//选中了记录,获取选中行的id
				for (var i = 0; i < rows.length; i++) {
					var id = rows[i].id;
					array.push(id);
				}
				ids = array.join(",");
				$("input[name='id']").val(ids);
				$("#diaoduWindow").window('open');
			}
			
		}
		
		
		$("#grid").datagrid({
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			toolbar : [ {
				id : 'diaodu',
				text : '人工调度',
				iconCls : 'icon-edit',
				handler : diaodu
			} ],
			url : "${pageContext.request.contextPath}/notice_listNoStaffNoticeBill.action",
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 200
			}, {
				field : 'delegater',
				title : '联系人',
				width : 100
			}, {
				field : 'telephone',
				title : '电话',
				width : 100
			}, {
				field : 'pickaddress',
				title : '取件地址',
				width : 100
			}, {
				field : 'product',
				title : '商品名称',
				width : 100
			}, {
				field : 'formatdate',
				title : '取件日期',
				width : 100
				
			} ] ]
		});

		// 点击保存按钮，为通知单 进行分单 --- 生成工单
		$("#save").click(function() {
			// 对form 进行校验
			if($('#diaoduForm').form('validate')){
				$('#diaoduForm').submit();
			}
		});
	});
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<table id="grid"></table>
	</div>
	<div class="easyui-window" title="人工调度" id="diaoduWindow" closed="true"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:100px;left:200px;width: 500px; height: 300px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="diaoduForm" method="post" action="${pageContext.request.contextPath }/notice_diaodu.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">人工调度</td>
					</tr>
					<tr>
						<td>通知单编号</td>
						<td><input type="text" name="id" id="noticebillId" /> <span
							id="noticebillIdView"></span>
					</tr>
					<tr>
						<td>选择取派员</td>
						<td><input class="easyui-combobox" required="true"
							name="staff.id"
							data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath }/staff_listStaff.action'" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>