<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis() %>" />
<!-- jsp:include 는 파라미터 전달이 필요할 때 사용 -->    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="로그인" name="title"/>
</jsp:include>


  
<form action="${contextPath}/user/login.do" method="post">
  <div>
    <label for="email">아이디</label>
    <input type="text" name="email" id="email" placeholder="admin@gmail.com">
  </div>
  <div>
    <label for="pw">비밀번호</label>
    <input type="password" name="pw" id="pw" placeholder="●●●●●●">
  </div>
  <div>
    <input type="hidden" name="referer" value="${referer}">
    <button type="submit">로그인</button>
  </div>
</form>

<div>
  <ul>
    <li><a href="${contextPath}">자동로그인</a></li>
    <li><a href="${contextPath}/user/findId.form">아이디/비밀번호찾기</a></li>
  </ul>
  <hr>
  <div>
    <a href="${naverLoginURL}"><img src="${contextPath}/resources/image/btnG_완성형.png" width="200px"></a>
  </div>
</div>


<!-- %@ include 는 파라미터 전달이 필요없을 때 사용 -->
<%@ include file="../layout/footer.jsp"%>