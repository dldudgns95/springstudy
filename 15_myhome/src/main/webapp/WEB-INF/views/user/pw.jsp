<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis() %>" />
<!-- jsp:include 는 파라미터 전달이 필요할 때 사용 -->    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="회원가입" name="title"/>
</jsp:include>

<!-- script src를 사용할 때 ?dt=${dt}를 사용해서 항상 새로 업데이트되게 해줘야 한다. -->
<script src="${contextPath}/resources/js/user_pw_modify.js?dt=${dt}"></script>

<script>
  $(() => {
	
  })
  
  // 전역 변수
  
  
  
  
  
</script>

<div>

  <form id="frm_pwModify">
    
    <h1>비빌번호 변경</h1>
    
    <div>
      <label for="pw">비밀번호</label>
      <input type="password" id="pw" name="pw">
      <span id="msg_pw"></span>
    </div>
    
    <div>
      <label for="pw2">비밀번호 확인</label>
      <input type="password" id="pw2">
      <span id="msg_pw2"></span>
    </div>
    
    <div> 
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
      <button type="button" id="btn_pw_modify">비밀번호 변경하기</button>
    </div>
    
  </form>
  
</div>


<!-- %@ include 는 파라미터 전달이 필요없을 때 사용 -->
<%@ include file="../layout/footer.jsp"%>