<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <div>
    <form method="get" action="${contextPath}/test/list.do" >
      <div>
        <label for="id">아이디</label>
        <input type="text" name="id" id="id" placeholder="아이디">
      </div>
      <div>
        <label for="pw">비밀번호</label>
        <input type="password" name="pw" id="pw" placeholder="비밀번호">
      </div>
      <div>
        <label for="name">이름</label>
        <input type="text" name="name" id="name" placeholder="이름">
      </div>
      <div>
        <label for="age">나이</label>
        <input type="text" name="age" id="age" placeholder="나이">
      </div>
      <div>
        <button type="submit">확인</button>
      </div>
    </form>
  </div>
</body>
</html>