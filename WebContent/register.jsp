<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  prefix = "s" uri = "/struts-tags" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css" />
<style type="text/css">
	body{height:100%;background:#16a085;overflow:hidden;}
	canvas{z-index:-1;position:absolute;}

	.login{
		margin-top:400px;
		margin-left: 800px;
		
	}	
</style>

<script src="js/jquery.js"></script>
<script src="js/verificationNumbers.js"></script>
<script src="js/Particleground.js"></script>
<script>
	$(document).ready(function() {
	  //粒子背景特效
	  $('body').particleground({
	    dotColor: '#5cbdaa',
	    lineColor: '#5cbdaa'
	  });
	  //验证码
	  createCode();
	  //测试提交，对接程序删除即可
	  $(".submit_btn").click(function(){
		  alert('用户名或密码错误');
		  });
	});
</script>
</head>
<body>
	<s:debug/>
<div class = "login">
	<s:form action = "userAction_register" method = "get">
		<s:textfield name="name" label="用户名"> </s:textfield>
		<s:password name="password" label="密码"></s:password>
		<s:password name="confirmPassword" label="确认密码"></s:password>
		<s:textfield name = "nikeName" label = "昵称"></s:textfield>
		<s:submit value="注册"></s:submit>
	</s:form>
</div>
</body>
</html>