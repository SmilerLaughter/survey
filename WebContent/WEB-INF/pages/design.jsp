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
 	
 			<br>
 			<font style="font-size: 15px">
 			
 		
			<img  src="<s:url value ="%{logoPath}" />"  height="50px" width="50px"/>
 			标题：${ model.title }             
 			<s:a action = "surveyAction_toUpdate?surveyId=%{surveyId }">编辑 </s:a>
 			<s:a action = "pageAction_toAddOrUpdatePage?surveyId=%{surveyId }">增加页面板块</s:a>
 			<s:a action ="uploadAction_toAddImage?surveyId=%{surveyId}" >添加或修改logo</s:a>
 			<br><br>
 			<hr/>

 			<s:iterator value="pages" var = "page">
 				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${title }
 				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:a action = "pageAction_toAddOrUpdatePage?pageId=%{pageId}">编辑页标题</s:a> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:a action = "pageMoveAndCopyAction_toMoveOrCopyPage?pageId=%{pageId}&surveyId=%{surveyId}">移动/复制页</s:a> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:a action = "pageAction_toDeletePage?pageId=%{pageId}&surveyId=%{surveyId}">删除页</s:a> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:a action = "questionAction_toAddQuestion?pageId=%{pageId}&surveyId=%{surveyId }">增加问题</s:a>
 				<br><br>
 				<s:iterator value = "questions" var = "question">
 				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 				${title}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:a action = "questionAction_toEditQuestion?questionId=%{questionId}&surveyId=%{surveyId}">编辑问题</s:a> 
 				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:a action = "questionAction_deleteQuestion?questionId=%{questionId}&surveyId=%{surveyId}">删除问题</s:a> 
 				<br><br>
 				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 				<s:if test="%{questionTypeId == 1 && options != null}">
 					<s:radio list="%{options}" listValue="content" ></s:radio>
 				</s:if>
 				 				
 				<s:elseif test="%{questionTypeId == 2 && options != null}">
 					<s:radio list="%{options}" listValue="content" ></s:radio>
 				</s:elseif>
 				
 				<s:elseif test="%{questionTypeId == 3 && options != null}">
 					<s:iterator value="options">
 						<input type = "checkbox"> ${content }
 					</s:iterator>
 				</s:elseif>
 				
 					
 				<s:elseif test="%{questionTypeId == 4 && options != null}">
 					<s:iterator value="options">
 						<input type = "checkbox"> ${content }<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 					</s:iterator>
 				</s:elseif>
 				
 				
 				<s:elseif test="%{questionTypeId == 5 && options != null}">
 					<s:select list="%{options}" listValue="content"></s:select>
 				</s:elseif>
 				
 				<s:elseif test="%{questionTypeId == 6}">
 					<s:textfield></s:textfield>
 				</s:elseif>
 				
 				<s:else>
 					<table style="text-align: center">
 						<tr >
 							<th style="padding: 25px"></th>
 							<s:if test="%{maxtrixCols != null}">
	 							<s:iterator value = "maxtrixCols" var = "maxtrixCol">
	 							<th style="padding: 25px"> ${content} </th>	
	 							</s:iterator>
 							</s:if>
 						</tr>
 						<s:if test="%{maxtrixCols != null}">
 						<s:iterator value = "maxtrixRows" var = "maxtrixRow">
 							<tr>
 								<td>${content }</td>
 								<s:iterator value = "maxtrixCols">
 									<td>
 										<s:if test="%{questionTypeId == 7}">
 											<input type = "radio" />
 										</s:if>
 										<s:elseif test="%{questionTypeId == 8}">
 											<input type = "checkbox"/>
 										</s:elseif>
 										<s:else>
 											<s:if test="%{ maxtrixSelectOptions != null}">
 											<select>
 												<s:iterator value="maxtrixSelectOptions">
													<option>${content }</option>
 														
 												</s:iterator>
 											</select>
 											</s:if>
 										</s:else>
 									
 									 </td>
 								</s:iterator>
 							</tr>
 						</s:iterator>
 					</s:if>
 					</table>
 					
 				</s:else>
 				
 				<!-- 其他项 -->
 				<s:if test="%{otherType == 1}">
 					<br>
 					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 					<input type = "radio" >其他
 				</s:if>
 				<s:elseif test="%{otherType == 2}">
 				
 					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 					<input type = "radio" >其他 &nbsp;<s:textfield></s:textfield>
 				</s:elseif>
 				<s:elseif test ="%{otherType == 3}">
 					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 					其他：<s:select list="%{otherSelecOptions}" listValue="content"></s:select>
 				</s:elseif>
 				
 				<br><br>
 				</s:iterator>
 				<br><br><br>
 				<hr>
 			</s:iterator>
 		</font>
 			<s:debug></s:debug>
 			
 		</div>
 	</section>
 
</body>
</html>