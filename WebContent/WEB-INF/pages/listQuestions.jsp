<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<section class="rt_wrap content mCustomScrollbar">
 		<div class="rt_content">
 		<br><br>
 		<p style="font-size: 24px;background-color: grey;color: white;line-height: 48px">
 			${title } <br> 
 		 <table class="table">
 		 <s:iterator  value = "pages" var = "page">
 		 		<tr>
 		 			<th colspan="3"> ${page.title }</th>
 		 			
 		 		</tr>
 		 	<s:iterator value = "questions" var = "question">
 		 		<s:if test="questionTypeId < 6">
	 		 		<form action = "statisticAction" method = "get">
	 		 		<tr>
	 		 			<td>${question.title}</td>
	 		 			<td>
							<select name = "chartType">
								<option value = "1" >平面饼图 </option>
								<option value = "2" >立体饼图 </option>
								<option value = "3" >水平平面柱状图 </option>
								<option value = "4" >竖直平面柱状图 </option>
								<option value = "5" >水平立体柱状图 </option>
								<option value = "6" >竖直立体柱状图</option>
								<option value = "7" >平面折线图</option>
								<option value = "8" >立体折线图</option>
							</select>
	 		 			</td>
	 		 			<s:hidden name = "questionId" value = "%{questionId}"></s:hidden>
	 		 			<td> <input type = "submit" value = "查看"> </td>
	 		 		</tr>
	 		 		</form>
 		 		</s:if>
 		 		
 		 		
 		 		<s:elseif test="questionTypeId > 6">
 		 		<form action = "statisticMaxtrixAction" method = "get">
 		 			<tr>
	 		 			<td>${question.title}</td>
							<s:hidden name = "questionId" value = "%{questionId}"></s:hidden>
	 		 			<td> <input type = "submit" value = "查看"> </td>
	 		 		</tr>
	 		 	</form>
	 		 	
 		 		</s:elseif>
 		 	</s:iterator>
 		</s:iterator>
 			
 		 </table>
 			
 		
 		</div>
 	</section>
</body>
</html>