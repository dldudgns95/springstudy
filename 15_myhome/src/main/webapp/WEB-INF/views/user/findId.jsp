<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis() %>" />
<!-- jsp:include 는 파라미터 전달이 필요할 때 사용 -->    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="아이디찾기" name="title"/>
</jsp:include>

<!-- script src를 사용할 때 ?dt=${dt}를 사용해서 항상 새로 업데이트되게 해줘야 한다. -->
<script src="${contextPath}/resources/js/user_find_id.js?dt=${dt}"></script>
  
<form id="frm_find_id" action="${contextPath}/user/findId.do" method="post">
  <div>
    <label for="name">이름</label>
    <input type="text" name="name" id="name">
    <span id="msg_name"></span>
  </div>
  <div>
    <label for="mobile">전화번호</label>
    <input type="text" name="mobile" id="mobile">
    <span id="msg_mobile"></span>
  </div>
  <div>
    <button type="submit">아이디찾기</button>
  </div>
</form>

<div>
  <ul>
    <li><a href="${contextPath}/user/findPw.form">비밀번호찾기</a></li>
  </ul>
</div>


<!-- %@ include 는 파라미터 전달이 필요없을 때 사용 -->
<%@ include file="../layout/footer.jsp"%>