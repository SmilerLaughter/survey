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
	<table class= "table">
						<tr>
 					 		<td>
 					 			${question.title }<br>
 					 			共有 ${statistic.count }人次作答
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
 						<s:if test="%{maxtrixRows != null}">
 						<s:iterator value = "maxtrixRows" var = "maxtrixRow">
 							<tr>
 								<td>${content }</td>
 								<s:iterator value = "maxtrixCols" var = "maxtrixCol">
 									<td>
 										<s:if test="%{questionTypeId != 9}">
 											 <s:property value = "getPercent(rowId,colId,null)" />
 										</s:if>
 									
 										<s:else>
 											<s:if test="%{ maxtrixSelectOptions != null}">
 												<s:iterator value="maxtrixSelectOptions" var = "maxtrixSelectOption">
													
													${content }--- <s:property value = "getPercent(rowId,colId,optionId)" /> <br>
													
 														
 												</s:iterator>
 										
 											</s:if>
 										</s:else>
 									
 									 </td>
 								</s:iterator>
 							</tr>
 						</s:iterator>
 					</s:if>
 					</table>

	</div>
	</section>
</body>
</html>