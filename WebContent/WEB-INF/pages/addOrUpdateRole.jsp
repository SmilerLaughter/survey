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
 		
 			<s:form action = "roleAction_addOrUpdateRole" method = "post">
 				<table class = "table">
 			<s:hidden name = "roleId"></s:hidden>
 				<tr>
 					<td><s:textfield name = "roleName" label="角色名"></s:textfield></td>
 				</tr>
 				
 				<tr>
 					<td><s:textarea name = "roleDesc" label = "角色描述"></s:textarea></td>
 				</tr>
 				 </table>
				<br><br>
				有的权限有：
				<s:if test="permissions != null ">
					<table class = "table">
					
					
					<tr>
				
					<s:iterator value = "permissions" var = "permission" >
					
							<td><input name = "permissionId" value = "${permission.permissionId }" type = "checkbox" checked="checked">${permission.permissionName }</td>
					</s:iterator>
					</tr>
				</table>	
				</s:if> 	


 				 			<br><br>
				
					<table class = "table">
					<tr>
						<th>该角色还未拥有的权限</th>
						<th>描述</th>
					</tr>
					<s:iterator value = "otherPermissions" var = "otherPermission" >
						<tr>
						
							<td><input name = "permissionId" value = "${otherPermission.permissionId }" type = "checkbox" >${otherPermission.permissionName }</td>
							<td>${otherPermission.permissionDesc }</td>
								
						</tr>
					
					</s:iterator>
					</tr>
				</table>	
						
 				 
 				 <br>
			
 				
 				
 				
 				<s:submit value = "提交"></s:submit>
 			</s:form>
 		   
 		</div>
 	</section>

</body>
</html>