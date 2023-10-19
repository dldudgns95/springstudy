<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- jsp:include 는 파라미터 전달이 필요할 때 사용 -->    
<jsp:include page="header.jsp">
  <jsp:param value="마이홈" name="title"/>
</jsp:include>


   <h1 style="text-align: center;">어서오세요</h1>
  

<!-- %@ include 는 파라미터 전달이 필요없을 때 사용 -->
<%@ include file="footer.jsp"%>