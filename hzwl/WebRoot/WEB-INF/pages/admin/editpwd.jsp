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
	
</head>
<body class="easyui-layout">

	<!--修改密码窗口-->
	<div id="editPwdWindow" class="easyui-window" 
		align="center">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding: 50px; background: #fff; border: 1px solid #ccc;">
				<form id="editPasswordForm">
					<table cellpadding=3>
						<tr>
							<td>新密码：</td>
							<td><input id="txtNewPass" type="Password"
								class="txt01 easyui-validatebox" required="true"
								data-options="validType:'length[4,8]'" /></td>
						</tr>
						<tr>
							<td>确认密码：</td>
							<td><input id="txtRePass" type="Password"
								class="txt01 easyui-validatebox" required="true"
								data-options="validType:'length[4,8]'" /></td>
						</tr>
						<tr>
							<td></td>
							<td >
								<a id="btnEp" class="easyui-linkbutton" icon="icon-ok"
					href="javascript:void(0)">确定</a> <a id="btnReset"
					class="easyui-linkbutton" icon="icon-cancel"
					href="javascript:void(0)">清空</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//为“确定”按钮绑定事件
		$("#btnEp")
				.click(
						function() {
							//进行表单校验
							var v = $("#editPasswordForm").form("validate");//对应表单中的所有输入框进行校验
							if (v) {//表单校验通过
								//判断两次输入是否一致
								var v1 = $("#txtNewPass").val();
								var v2 = $("#txtRePass").val();
								if (v1 == v2) {
									//输入一致，发送ajax请求，修改当前用户的密码
									var url = "${pageContext.request.contextPath}/user_editPassWord.action";
									$.post(url, {
										"password" : v1
									}, function(data) {
										if (data == '1') {
											//修改密码成功
											$.messager.alert("提示信息", "密码修改成功,请重新登录！",
													"info",function(r){
												location.href="${pageContext.request.contextPath }/user_logout.action";
											});
											
										} else {
											//修改失败
											$.messager.alert("提示信息", "密码修改失败！",
													"warning");
										}
										
									});
								} else {
									//输入不一致，提示用户输入不一致
									$.messager.alert("提示信息", "两次输入密码不一致！",
											"warning");
								}
							}
						});
		
		$("#btnReset").click(function() {
			$.messager.alert("提示", "hahaha");
			$("#txtNewPass").val("");
			$("#txtRePass").val("");
		});
	
		
	</script>
</body>
</html>