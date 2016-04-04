<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file= "/common/common.jsp" %>
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
 			<p style="font-size: 18px;background-color: grey;color: white">
 			${session.currentSurvey.title } <br> &nbsp;&nbsp; 	${page.title }
 			
 			</p>
 		
 			 <br><br>
 			
 			<form method = "post">
 			
 			<s:iterator value = "page.questions" var = "question" >
 				<s:if test="%{questionTypeId < 5 }">
 					 <table class="table">
 					 	<tr>
 					 		<td>
 					 			${question.title }--  ${question.questionId }
 					 		</td>
 					 	</tr>
 					 
 					 	<tr>
 					 		<td>
 					 		<s:iterator value = "options" var = "option">
 					 			<s:if test = "questionTypeId <3">
 					 				<input type = "radio" name = "${question.questionId}" value = "${option.optionId }"  
 					 				<s:property value = "isSelect(questionId,optionId,'checked' )" />>  ${option.content }
 					 			</s:if>
 					 			<s:else>
 					 				<input type = "checkbox" name = "${question.questionId}" value = "${option.optionId }" 
 					 				 <s:property value = "isSelect(questionId,optionId,'checked' )" />> ${option.content }
 					 			</s:else>
 					 			<s:if test="%{questionTypeId == 2 || questionTypeId == 4 }">
 					 				<br>
 					 			</s:if>
 					 			<s:else> &nbsp;&nbsp;</s:else>
 					 		</s:iterator>
			 					 		
							<s:if test="%{otherType == 1}">
								<s:if test = "questionTypeId <3">
						 				<input type = "radio" name = "${question.questionId}" value = "0" <s:property value = "isSelect(questionId,0,'checked' )" />> 其他
						 		</s:if>	
						 		<s:elseif test = "questionTypeId <5">
						 				<input type = "checkbox" name = "${question.questionId}" value = "0" <s:property value = "isSelect(questionId,0,'checked' )" />> 其他
						 		</s:elseif>
							</s:if>
							
							
							<s:elseif test="%{otherType == 2}">
								<s:if test = "questionTypeId <3">
						 				<input type = "radio" name = "${question.questionId}" value = "0" <s:property value = "isSelect(questionId,0,'checked' )" />>其他
						 		</s:if>	
						 		<s:elseif test = "questionTypeId <5">
						 				<input type = "checkbox" name = "${question.questionId}" value = "0" <s:property value = "isSelect(questionId,0,'checked' )" />> 其他
						 		</s:elseif>
								  其他<input type = "text" name = "${question.questionId }"   <s:property value = "getText(questionId)" />> 
							</s:elseif>
 					 	
 					 		</td>
 					 	</tr>
 					 </table>
 				</s:if>
 				
 				
 				
 				<s:elseif test="%{questionTypeId == 5}">
 					<table class="table">
 						<tr>
 					 		<td>
 					 			${question.title }--  ${question.questionId }
 					 		</td>
 					 	</tr>
 					 	<tr>
 					 		<td>
							<select name="${question.questionId }">
	 					 		<s:iterator value = "options" var = "option">
									<option value="${option.optionId }" <s:property value = "isSelect(questionId,optionId,'selected' )" />> ${option.content }	</option>
	 					 		</s:iterator>
	 					 		
	 					 		
	 					 		<s:if test="%{otherType == 1}">
						 			<option value="0" <s:property value = "isSelect(questionId,0,'selected' )" />>其他	</option>
								</s:if>
							
							
							<s:elseif test="%{otherType == 2}">
								<option value="0" <s:property value = "isSelect(questionId,0,'selected' )" />>其他	</option>
								  其他<input type = "text" name = "${question.questionId }"   <s:property value = "getText(questionId)" />> 
							</s:elseif>
 					 	
	 					 		
							</select>
 					 		</td>
 					 	</tr>
 					 </table>
 				</s:elseif>
		
		
		
		
				<s:elseif test="%{questionTypeId == 6}">
					<table class="table">
						<tr>
 					 		<td>
 					 			${question.title }
 					 		</td>
 					 	</tr>
 					 	<tr>
 					 		<td>
								<input name = "${question.questionId }" type = "text" <s:property value = "getText(questionId)" />> 
							</td>
						</tr>
					 </table>
					 
				</s:elseif>
				
				
				
				
				<s:else>
					<table class= "table">
						<tr>
 					 		<td>
 					 			${question.title }
 					 		</td>
 					 	</tr>
 						<tr >
 							<th></th>
 							<s:if test="%{maxtrixCols != null}">
	 							<s:iterator value = "maxtrixCols" var = "maxtrixCol">
	 							<th > ${content} </th>	
	 							</s:iterator>
 							</s:if>
 						</tr>
 						<s:if test="%{maxtrixCols != null}">
 						<s:iterator value = "maxtrixRows" var = "maxtrixRow">
 							<tr>
 								<td>${content }</td>
 								<s:iterator value = "maxtrixCols" var = "maxtrixCol">
 									<td>
 										<s:if test="%{questionTypeId == 7}">
 											<input type = "radio" name = "${question.questionId }_${maxtrixRow.rowId}" value = "${maxtrixCol.colId }" 
 											 <s:property value = "isSelect(questionId + '_' +rowId,colId,'checked' )" />
 											/>
 										</s:if>
 										<s:elseif test="%{questionTypeId == 8}">
 											<input type = "checkbox" name = "${question.questionId }_${maxtrixRow.rowId}" value = "${maxtrixCol.colId }"
 											 <s:property value = "isSelect(questionId + '_' +rowId,colId,'checked')" />
 											/>
 										</s:elseif>
 										<s:else>
 											<s:if test="%{ maxtrixSelectOptions != null}">
 											<select name = "${question.questionId }_${maxtrixRow.rowId}_${maxtrixCol.colId}">
 												<s:iterator value="maxtrixSelectOptions" var = "maxtrixSelectOption">
													<option value = "${maxtrixSelectOption.optionId }"
													 <s:property value = "isSelect(questionId + '_' +rowId+'_' + colId,optionId,'selected' )" />
													>${content }</option>
 														
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
				
				
				
<%-- 				<s:elseif test = "%{otherType == 3}"> --%>
<%-- 					<s:if test = "questionTypeId <3"> --%>
<%-- 			 				<input type = "radio" name = "${question.questionId}" value = "0" <s:property value = "isSelect(questionId,0,'checked' )" />> 其他 --%>
<%-- 			 		</s:if>	 --%>
<%-- 			 		<s:elseif test = "questionTypeId <5"> --%>
<%-- 			 				<input type = "checkbox" name = "${question.questionId}" value = "0" <s:property value = "isSelect(questionId,0,'checked' )" />> 其他 --%>
<%-- 			 		</s:elseif> --%>
<%-- 					<select name = "${question.questionId }"> --%>
<%-- 						<s:iterator value = "otherSelecOptions" var = "otherOption"> --%>
<%-- 							<option value = "${otherOption.otherOptionId }"> ${otherOption.content }</option> --%>
<%-- 						</s:iterator> --%>
<!-- 					</select> -->
<%-- 				</s:elseif> --%>
				
			<br><br>
 			</s:iterator>
 			
 			
 			
 			
 			
 			<s:hidden name = "page.pageId" value = "%{page.pageId}"></s:hidden>
 			<div style="float: left">
	 			<s:if test="page.orderNo gt #session.currentSurvey.minOrder">
	 				<s:submit action = "doSurveyAction_toPrePage" value = "%{#session.currentSurvey.preText}" cssStyle="float:left;margin-left:10px"></s:submit> <br>
	 			</s:if> 
	 			
	 			<s:if test="page.orderNo lt #session.currentSurvey.maxOrder">
	 				<s:submit  action = "doSurveyAction_toNextPage" value = "%{#session.currentSurvey.nextText}" cssStyle="float:left;margin-left:10px"></s:submit> <br>
	 			</s:if>
	 			
	 			<s:submit action="doSurveyAction_toListOpenSurveys" value = "%{#session.currentSurvey.exitText}"  cssStyle="float:left;margin-left:10px"></s:submit> <br>
	 			
	 			<s:if test = "%{page.orderNo == #session.currentSurvey.maxOrder }">
	 				<s:submit action = "doSurveyAction_commitSurvey" value = "%{#session.currentSurvey.doneText}" cssStyle="float:left;margin-left:10px"></s:submit> <br>
	 			</s:if>
 			</div>
 			</form>

	</font>
	<s:debug></s:debug>
 		</div>
 	</section>
</body>
</html>