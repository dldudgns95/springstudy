<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

  <div>
    <h3>기사 작성하기</h3>
    <form method="post" action="${contextPath}/register.do">
      <div>
        <label for="articleNo">기사번호</label>
        <input type="text" name="articleNo" id="articleNo">
      </div>
      <div>
        <label for="title">기사제목</label>
        <input type="text" name="title" id="title">
      </div>
      <div>
        <label for="content">기사내용</label>
        <input type="text" name="content" id="content">
      </div>
      <div>
        <button type="submit">기사작성완료</button>
      </div>
    </form>
  </div>
  

</body>
</html>