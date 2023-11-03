<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis() %>" />
<!-- jsp:include 는 파라미터 전달이 필요할 때 사용 -->    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="비밀번호 찾기" name="title"/>
</jsp:include>


  
<form method="post">
  <div>
    <label for="email">아이디</label>
    <input type="text" name="email" id="email">
    <button type="button" id="btn_check_email">인증코드 발송</button>
  </div>
  <div>
    <input type="text" name="checkCode" disabled>
    <button type="button" id="btn_check_code" disabled>코드입력</button>
  </div>
</form>

<div>
  <ul>
    <li><a href="${contextPath}/user/findId.form">아이디찾기</a></li>
  </ul>
</div>


<!-- %@ include 는 파라미터 전달이 필요없을 때 사용 -->
<%@ include file="../layout/footer.jsp"%>