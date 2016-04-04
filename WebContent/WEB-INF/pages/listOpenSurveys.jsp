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
			
			<s:if test = "%{surveys != null && surveys.size != 0}">
				<s:iterator value = "%{surveys}" var = "survey" >
					<div style="margin-left: 25px;width: 120px;height: 120px;float: left;text-align: center;margin-top: 50px" border = "1"  >
						<img src="<s:url value = "%{logoPath}" />" height="75px" width="75px"><br><br>
						<s:a action = "doSurveyAction_toDoSurveyPage?surveyId=%{surveyId}">${survey.title }</s:a>
					</div>
				</s:iterator>
			</s:if>
			<s:else>
				<br><br>
				还没有开放的问卷！
			</s:else>

 		</div>
 	</section>

</body>
</html>