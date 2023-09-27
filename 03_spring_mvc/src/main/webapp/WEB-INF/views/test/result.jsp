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
    아이디 : ${requestScope.id}
  </div>
  <div>
    비밀번호 : ${requestScope.pw}
  </div>
  <div>
    이름 : ${requestScope.name}
  </div>
  <div>
    나이 : ${requestScope.age}
  </div>
  <div>
    <a href="${contextPath}/index.do">메인으로 돌아가기</a>
  </div>
</body>
</html>