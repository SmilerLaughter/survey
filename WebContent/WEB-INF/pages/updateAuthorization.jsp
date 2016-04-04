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
 		
 			<s:form action = "authorizeAction_updateAuthorization" method = "post">
 				<table class = "table">
 			<s:hidden name = "userId"></s:hidden>
 				<tr>
 					<td><s:textfield name = "name" label="用户名" disabled="true"></s:textfield></td>
 				</tr>
 				
 				 </table>
				<br><br>
				拥有的角色有：
				<s:if test="roles != null ">
					<table class = "table">
					
					
					<tr>
				
					<s:iterator value = "roles" var = "role" >
					
							<td><input name = "roleId" value = "${role.roleId }" type = "checkbox" checked="checked">${role.roleName }</td>
					
						<s:else>
							<tr>
								<td><input name = "roleId" value = "${role.roleId }" type = "checkbox" checked="checked"></td>
								<td>${permission.permissionDesc }</td>
							</tr>
						</s:else>
							
					</s:iterator>
					</tr>
				</table>	
				</s:if> 	


 				 			<br><br>
				
					<table class = "table">
					<tr>
						<th>该用户还未拥有的角色</th>
						<th>描述</th>
					</tr>
					<s:iterator value = "otherRoles" var = "otherRole" >
						<tr>
						
							<td><input name = "roleId" value = "${otherRole.roleId }" type = "checkbox" >${otherRole.roleName }</td>
							<td>${otherRole.roleDesc }</td>
								
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