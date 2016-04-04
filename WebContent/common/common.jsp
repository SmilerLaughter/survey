<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri = "/struts-tags" %>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8"/>
<title>问卷调查系统</title>
<meta name="author" content="DeathGhost" />
<link rel="stylesheet" type="text/css" href="css/style.css" />

<script src="js/jquery.js"></script>
<script src="js/jquery.mCustomScrollbar.concat.min.js"></script>

</head>
<body>
<!--header-->
<header>
 	<p style="font-size:40px;color: white;margin-top: 13px">问卷调查系统</p>
 <ul class="rt_nav">
  <li><a href="#" class="admin_icon"> ${session.user.name} </a></li>
  <li><a href="#" class="set_icon">账号设置</a></li>
  <li><a href="login.php" class="quit_icon">安全退出</a></li>
 </ul>
</header>

<!--aside nav-->
<aside class="lt_aside_nav content mCustomScrollbar">
 <h2><a href="index.php">起始页</a></h2>
 <ul>
  <li>
   <dl>
    <dt>调查</dt>
    <dd><s:a action = "surveyAction_createSurvey">新建调查</s:a></dd>
    <dd><s:a action="surveyAction_toListPage">我的调查</s:a></dd>
    <dd><s:a action="doSurveyAction_toListOpenSurveys">参与调查</s:a></dd>
   </dl>
  </li>
	<li>
	  <dl>
	   <dt>权限管理</dt>
	   <!--当前链接则添加class:active-->
	   <dd><s:a action="permissionAction_toListPermissionsPage" >查看权限</s:a></dd>
	   <dd><s:a action="permissionAction_toAddOrUpdatePermissionPage">新增权限</s:a></dd>
	  </dl>
	 </li>
  <li>
   <dl>
    <dt>角色管理</dt>
    <dd><s:a action="roleAction_toAddOrUpdateRole">增加角色</s:a></dd>
    <dd><s:a action="roleAction_toListRolesPage">查看角色</s:a></dd>
   </dl>
  </li>
  <li>
   <dl>
    <dt>用户授权管理</dt>
    <!--当前链接则添加class:active-->
    <dd><s:a action="authorizeAction_toAuthorizePage" >用户授权</s:a></dd>
   </dl>
  </li>
<li>
<!--    <dl> -->
<!--     <dt>日志管理</dt> -->
<!--     当前链接则添加class:active -->
<%--     <dd><s:a action="#" class="active">日志列表</s:a></dd> --%>
   
<!--    </dl> -->
<!--   </li> -->
   <p class="btm_infor">© 版权所有</p>
  </li>
 </ul>
</aside>

</body>
</html>