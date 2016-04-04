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
 		<br><br><p style="font-size: 16px;text-align: center">
			<s:a action="questionAction_toQuestionType?pageId=%{pageId}&questionTypeId=1&surveyId=%{surveyId}" >添加 单选（行）式问题</s:a><br><br>
			<s:a action="questionAction_toQuestionType?pageId=%{pageId}&questionTypeId=2&surveyId=%{surveyId}" >添加 单选（列）式问题</s:a><br><br>
			<s:a action="questionAction_toQuestionType?pageId=%{pageId}&questionTypeId=3&surveyId=%{surveyId}" >添加 多选（行）式问题</s:a><br><br>
			<s:a action="questionAction_toQuestionType?pageId=%{pageId}&questionTypeId=4&surveyId=%{surveyId}" >添加 多选（列）式问题</s:a><br><br>
			<s:a action="questionAction_toQuestionType?pageId=%{pageId}&questionTypeId=5&surveyId=%{surveyId}" >添加 下拉 式问题</s:a><br><br>
			<s:a action="questionAction_toQuestionType?pageId=%{pageId}&questionTypeId=6&surveyId=%{surveyId}" >添加 文本 式问题</s:a><br><br>
			<s:a action="questionAction_toQuestionType?pageId=%{pageId}&questionTypeId=7&surveyId=%{surveyId}" >添加 矩阵（单选）式问题</s:a><br><br>
			<s:a action="questionAction_toQuestionType?pageId=%{pageId}&questionTypeId=8&surveyId=%{surveyId}" >添加 矩阵（多选）式问题</s:a><br><br>
			<s:a action="questionAction_toQuestionType?pageId=%{pageId}&questionTypeId=9&surveyId=%{surveyId}" >添加 矩阵（下拉）式问题</s:a><br><br>
 		
 		</p>
 		</div>
 	</section>
	

</body>
</html>