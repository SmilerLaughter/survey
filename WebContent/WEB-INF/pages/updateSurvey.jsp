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
 			<br><br><br>
 			<s:debug></s:debug>
 			<s:form action = "surveyAction_update?surveyId=%{surveyId }" method = "post"> 
 				<s:textfield name = "title" label="标题"></s:textfield>
 				<s:textfield name = "preText" label="上一步提示名"></s:textfield>
 				<s:textfield name = "nextText" label="下一步提示名"></s:textfield>
 				<s:textfield name = "exitText" label="退出提示名称"></s:textfield>
 				<s:textfield name = "doneText" label="完成提示名"></s:textfield>
 				<s:submit value = "修改"></s:submit>
 			</s:form>
 		
 		</div>
 	</section>

</body>
</html>