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
	function doAdd(){
		$('#addPeopleWindow').window("open");
	}
	
	function doView(){
		$('#editPeopleWindow').window("open");
	}
	
	function doDelete(){
		//获得选中的行
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length == 0){
			//没有选中，提示
			$.messager.alert("提示信息","请选择需要删除的记录！","warning");
		}else{
			var array = new Array();
			//选中了记录,获取选中行的id
			for(var i=0;i<rows.length;i++){
				var id = rows[i].pid;
				
				array.push(id);
			}
			var ids = array.join(",");
			alert(ids);
			//发送请求，传递ids参数
			window.location.href = '${pageContext.request.contextPath}/people_delete.action?ids='+ids;
		}
	}
	function doSearch(){
		$('#searchPeopleWindow').window("open");
	}
	
	//工具栏
	var toolbar = [{
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
	}];
	// 定义列
	var columns = [ [ {
		field : 'pid',
		checkbox : true,
	},{
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center'
	}, {
		field : 'sex',
		title : '性别',
		width : 120,
		align : 'center'
	}, {
		field : 'age',
		title : '年龄',
		width : 120,
		align : 'center'
	},{
		field : 'phone',
		title : '手机号',
		width : 120,
		align : 'center'
	}, {
		field : 'job',
		title : '职位',
		width : 120,
		align : 'center'
	}, {
		field : 'dept',
		title : '单位',
		width : 120,
		align : 'center'
	}] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 员工数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pagination : true,
			pageList: [2,4,8],
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/people_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加区域窗口
		$('#addPeopleWindow').window({
	        title: '员工添加区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 修改区域窗口
		$('#editPeopleWindow').window({
	        title: '员工修改区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 查询区域窗口
		$('#searchPeopleWindow').window({
	        title: '员工查询区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 250,
	        resizable:false
	    });
		
	});

	//双击一行数据
	function doDblClickRow(rowIndex,rowData){
		$('#editPeopleWindow').window("open");
		
		$("#editPeopleForm").form("load",rowData);
	}
	
	//手机校验规则
	$(function(){
		var reg = /^1[3|4|5|7|8|9][0-9]{9}$/;
		$.extend($.fn.validatebox.defaults.rules, { 
				phonenumber: { 
							validator: function(value, param){ 
								return reg.test(value);
							}, 
							message: '手机号输入有误！' 
							} 
				}); 
		});
	//校验年龄
	$(function(){
		var reg = /^[1-9]{2}$/;
		$.extend($.fn.validatebox.defaults.rules, { 
				agejiaoyan: { 
							validator: function(value, param){ 
								return reg.test(value);
							}, 
							message: '年龄输入有误！' 
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
	<div class="easyui-window" title="员工添加" id="addPeopleWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
				<script type="text/javascript">
				$(function(){
					$("#save").click(function(){
						//校验表单输入项
						var v = $("#addPeopleForm").form("validate");
						if(v){
							//校验通过，提交表单
							$("#addPeopleForm").submit();
							$.messager.alert("提示信息", "添加成功！",
							"info");
						}
						
					});
				});
				</script>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addPeopleForm" action="${pageContext.request.contextPath }/people_add.action"
					 method="post">
					 
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">员工添加</td>
					</tr>
					
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>性别</td>
						<td><input type="text" name="sex" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>年龄</td>
						<td><input type="text" name="age" class="easyui-validatebox" required="true"
						data-options="validType:'agejiaoyan'"/></td>
					</tr>
					<tr>
						<td>手机号</td>
						<td><input type="text" name="phone" class="easyui-validatebox" required="true"
						data-options="validType:'phonenumber'"/></td>
					</tr>
					<tr>
						<td>职位</td>
						<td><input type="text" name="job" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="dept" class="easyui-validatebox" required="true"/></td>
					</tr>
					
					</table>
			</form>
		</div>
	</div>
	
	
	
	//修改窗口
	<div class="easyui-window" title="员工修改" id="editPeopleWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
				<script type="text/javascript">
				$(function(){
					$("#edit").click(function(){
						//校验表单输入项
						var v = $("#editPeopleForm").form("validate");
						if(v){
							//校验通过，提交表单
							$("#editPeopleForm").submit();
							$.messager.alert("提示信息", "修改成功！",
							"info");
						}
						
					});
				});
				</script>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editPeopleForm" action="${pageContext.request.contextPath }/people_edit.action"
					 method="post">
				<input type="hidden" name="pid" class="easyui-validatebox" required="true"/>
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">员工修改</td>
					</tr>
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>性别</td>
						<td><input type="text" name="sex" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>年龄</td>
						<td><input type="text" name="age" class="easyui-validatebox" required="true"
						data-options="validType:'agejiaoyan'"/></td>
					</tr>
					<tr>
						<td>手机号</td>
						<td><input type="text" name="phone" class="easyui-validatebox" required="true"
						data-options="validType:'phonenumber'"/></td>
					</tr>
					<tr>
						<td>职位</td>
						<td><input type="text" name="job" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="dept" class="easyui-validatebox" required="true"/></td>
					</tr>
					
					</table>
			</form>
		</div>
	</div>
	
	
	
	//查询窗口
	<div class="easyui-window" title="员工查询" id="searchPeopleWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="search" icon="icon-search" href="#" class="easyui-linkbutton" plain="true" >查询</a>
				<script type="text/javascript">
				$(function(){
					
					//工具方法，可以将指定的表单中的输入项目序列号为json数据
					$.fn.serializeJson=function(){  
			            var serializeObj={};  
			            var array=this.serializeArray();
			            $(array).each(function(){  
			                if(serializeObj[this.name]){  
			                    if($.isArray(serializeObj[this.name])){  
			                        serializeObj[this.name].push(this.value);  
			                    }else{  
			                        serializeObj[this.name]=[serializeObj[this.name],this.value];  
			                    }  
			                }else{  
			                    serializeObj[this.name]=this.value;   
			                }  
			            });  
			            return serializeObj;  
			        }; 
					
					
					//绑定事件
					$("#search").click(function(){
						var p = $("#searchPeopleForm").serializeJson();//{id:xx,name:yy,age:zz}
						//重新发起ajax请求，提交参数
						$("#grid").datagrid("load",p);
						//关闭查询窗口
						$("#searchPeopleWindow").window("close");
					});
				});
				</script>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="searchPeopleForm" method="post">
				
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">员工查询</td>
					</tr>
					
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>职位</td>
						<td><input type="text" name="job" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="dept" class="easyui-validatebox" /></td>
					</tr>
					
					</table>
			</form>
		</div>
	</div>
</body>
</html>