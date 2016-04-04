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

			<s:if test="roles != null">
				<table class = "table">
					<tr>
						<th>ID</th>
						<th>角色名</th>
						<th>角色描述</th>
						<th>修改（信息&权限）</th>
						<th>删除</th>
					</tr>
					<s:iterator value = "roles" var = "role">
						<tr>
							<td>${role.roleId }</td>
							<td>${role.roleName }</td>
							<td>${role.roleDesc }</td>
							<td><s:a action = "roleAction_toAddOrUpdateRole?roleId=%{roleId}">修改</s:a> </td>
							<td> <s:a action = "roleAction_deleteRole?roleId=%{roleId}">删除</s:a>  </td>
						</tr>
					</s:iterator>
				</table>
				
			</s:if>
			<s:else>
				还没有创建任何角色
			</s:else> 	
 		</div>
 	</section>

</body>
</html>