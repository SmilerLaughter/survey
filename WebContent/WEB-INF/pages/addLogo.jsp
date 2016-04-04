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
 				<s:form action = "uploadAction_addImage?surveyId=%{surveyId}" method = "post" enctype="multipart/form-data" >
 				<s:file name = "image"  label="添加 或 修改图片"></s:file>
 				<s:submit value = "上传"></s:submit>
 			
 			</s:form>
 	
 		
 		</div>
 		</section>

</body>
</html>