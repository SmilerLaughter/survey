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

			<s:if test="users != null">
				<table class = "table">
					<tr>
						<th>ID</th>
						<th>用户名</th>
						<th>昵称</th>
						<th>授权（修改）</th>
						<th>清除授权</th>
					</tr>
					<s:iterator value = "users" var = "user">
						<tr>
							<td>${user.userId }</td>
							<td>${user.name }</td>
							<td>${user.nikeName }</td>
							<td><s:a action = "authorizeAction_toUpdateAuthorization?userId=%{userId}">修改</s:a> </td>
							<td> <s:a action = "authorizeAction_clearAuthorization?userId=%{userId}">清除权利</s:a>  </td>
						</tr>
					</s:iterator>
				</table>
				
			</s:if>
			<s:else>
				还没有任何用户
			</s:else>
			
			<s:debug></s:debug> 	
 		</div>
 	</section>

</body>
</html>