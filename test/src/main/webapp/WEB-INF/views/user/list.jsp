<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

  <div>
    <c:forEach items="userList" var="user">
      <div>번호 : ${user.userNo}</div>
      <div>ID   : ${user.userId}</div>
      <div>이름 : ${user.userName}</div>
    </c:forEach>
  </div>

</body>
</html>