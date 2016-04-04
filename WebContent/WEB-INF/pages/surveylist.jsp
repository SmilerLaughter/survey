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
 
 
  <section>
   <div class="page_title">
    	<h2 class="fl">${user.name }的调查如下：</h2>
   </div>
   <table class="table">
      
       
    <s:if test="surveys == null || surveys.size == 0">
    	 <h2><strong style="color:grey;font-size: 20px">你还未做任何调查！</strong> <s:a action="surveyAction_createSurvey">新建调查</s:a>  </h2>
    </s:if>
    <s:else>
     <tr>
        <th>调查标题</th>
        <th>创建时间</th>
        <th>状态</th>
        <th>设计</th>
        <th>收集信息</th>
        <th>分析</th>
        <th>打开/关闭</th>
        <th>清除调查</th>
        <th>删除</th>
       </tr>
	    <s:iterator value="surveys" var = "survey"> 
	      <tr>
	        <td>${ survey.title}</td>
	        <td> <s:date name="createTime" format="yyyy-MM-dd hh:mm:ss" /> </td>
	        <td>
	        	<s:if test="%{closed == true}">
	        		关闭
	        	</s:if>
	        	
	        	<s:else>
	        		打开
	        	</s:else>
	        
	        </td>
	        <td><a href="surveyAction_design?surveyId=${survey.surveyId }">设计 </a></td>
	        <td><s:a action = "imformationCollectionAction?surveyId=%{surveyId}"> 收集信息</s:a></td>
	        <td><s:a action = "surveyAction_toListQuestionsOfSurvey?surveyId=%{surveyId}"> 分析</s:a> </td>
	        <td><s:a action = "surveyAction_swichStatus?surveyId=%{surveyId}"> 打开/关闭</s:a> </td>
	        <td><s:a action = "surveyAction_clearAnswersOfSurvey?surveyId=%{surveyId}" > 清除调查</s:a> </td>
	        <td><s:a action = "surveyAction_deleteSurvey?surveyId=%{surveyId}"> 删除</s:a> </td>
	       </tr>
			
	    </s:iterator>
	</s:else>

   </table>
</section>
</div>     

</section>

</body>
</html>