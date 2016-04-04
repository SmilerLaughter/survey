<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = '/common/common.jsp' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<section class="rt_wrap content mCustomScrollbar">
 		<div class="rt_content">
 			<s:form action = "questionAction_addOrUpdateQuestion?pageId=%{pageId}&questionTypeId=%{questionTypeId}&surveyId=%{surveyId }&questionId=%{questionId}">
 				<s:textfield name = "title" label="问题标题"></s:textfield>
 				<s:textarea name = "maxtrixRowStr" label="矩阵式行标题（以回车为分隔）"></s:textarea>
 				<s:textarea name = "maxtrixColStr" label="矩阵式列标题(以回车为分隔)"></s:textarea>
 				<s:textarea name = "maxtrixOptionStr" label = "下拉列表选项（用回车键分隔）"></s:textarea>
 				<s:submit value = "提交"></s:submit>
 			</s:form>
 		
		</div>
	</section> 	

</body>
</html>