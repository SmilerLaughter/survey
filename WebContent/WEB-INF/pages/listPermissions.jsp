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
			
			<s:if test = "%{permissions != null && permissions.size != 0}">
				<table class="table">
					<tr>
						<th>ID</th>
						<th>权限名</th>
						<th>权限描述</th>
						<th>URL</th>
						<th>权限位</th>
						<th>权限码</th>
						<th>是否对所有人开放</th>
						<th>开放/关闭</th>
						<th>修改</th>
						<th>删除</th>
					</tr>
				
					<s:iterator value = "permissions" var = "permission">
						<tr>
							<td>${permission.permissionId }</td>
							<td>${permission.permissionName }</td>
							<td>${permission.permissionDesc }</td>
							<td>${permission.url }</td>
							<td>${permission.position }</td>
							<td>${permission.permissionNum }</td>
							<td>
							<s:if test="open">
								是
							</s:if>
							<s:else>
								否
							</s:else>
							</td>
							<td> <s:a action="permissionAction_swichStatus?permissionId=%{permissionId}&open=%{open}">开放/不开放</s:a> </td>
							<td> <s:a action = "permissionAction_toAddOrUpdatePermissionPage?permissionId=%{permissionId}">修改</s:a> </td>
							<td> <s:a action = "permissionAction_deletePermission?permissionId=%{permissionId}">删除</s:a> </td>
						</tr>
					</s:iterator>
				
				
				</table>
			</s:if>
			<s:else>
				<br><br>
				还没有任何权限！
			</s:else>

 		</div>
 	</section>

</body>
</html>