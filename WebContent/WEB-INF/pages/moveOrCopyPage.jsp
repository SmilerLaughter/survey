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
<!--  			<font style="font-size: 20px"> -->
 			<div style="margin-top: 50px">
				<p>移动或复制：同一调查时移动，不同调查为复制！<p><br>
				
				<p style="background-color: gray">${survey.title}</p>&nbsp;&nbsp;&nbsp;
				
				
				<p style="background-color: #f8f8f8;color: blue"> ${title}</p>
				
				<s:iterator value = "survey.pages" var = "page">
						<table class="table">
							<tr>
								<td>${title}</td>
								<td> 
									<form action="pageMoveAndCopyAction_moveOrCopyPage?pageId=${model.pageId }&surveyId=${model.surveyId}&targetPageId=${page.pageId}&targetSurveyId=${page.surveyId}" method = "post">
										<input  type = "radio" name = "pose" value = "0"/>之前  &nbsp;  &nbsp;  &nbsp;
										<input  type = "radio" name = "pose" value = "1"/>之后&nbsp;  &nbsp;  &nbsp;
										<input type = "submit" value = "确定"/>
									</form>
								</td>
							</tr>
						</table>
				
				</s:iterator>
				
				<s:iterator value="%{otherSurveys}" var = "otherSurvey"><br><br>
					<p style="background-color: gray">${otherSurvey.title }</p>
						
						<s:iterator value = "%{pages}" var = "page">
							<table class="table">
							<tr>
								<td>${ page.title}</td> 
								<td> 
									<form action="pageMoveAndCopyAction_moveOrCopyPage?pageId=${model.pageId }&surveyId=${model.surveyId}&targetPageId=${page.pageId}&targetSurveyId=${page.surveyId}" method = "post">
										<input  type = "radio" name = "pose" value = "0"/>之前  &nbsp;  &nbsp;  &nbsp;
										<input  type = "radio" name = "pose" value = "1"/>之后 &nbsp;  &nbsp;  &nbsp;
										<input type = "submit" value = "确定"/>
									</form>
								</td>
							</tr>
						</table>
						</s:iterator>
				
				</s:iterator>
				<s:debug></s:debug>
 			</div>
 			</font>
 		</div>
 		</section>

</body>
</html>