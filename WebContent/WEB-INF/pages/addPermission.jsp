<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<section class="rt_wrap content mCustomScrollbar">
 		<div class="rt_content">
 			<s:form action = "permissionAction_addOrUpdatePermission" method = "post">
 				<s:hidden name = "permissionId"></s:hidden>
 				<s:textfield name = "permissionName" label="权限名"></s:textfield>
 				<s:textarea name = "permissionDesc" label = "权限描述"></s:textarea>
 				<s:textfield name = "url" label = "权限路径"></s:textfield>
 				
 				<s:submit value = "提交"></s:submit>
 			</s:form>
 	
 		</div>
 	</section>

</body>
</html>