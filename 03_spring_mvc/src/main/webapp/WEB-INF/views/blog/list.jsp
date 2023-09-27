<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  blogNo=${blogNo}
  <br>
  blogNo=${requestScope.blogNo}
  <br>
  blogDto.blogNo=${blogDto.blogNo}
  <br>
  blogDto.blogNo=${blogDto.getBlogNo()}
  <br>
  dto.blogNo=${dto.blogNo}
  <br>
  dto.blogNo=${dto.getBlogNo()}
  
</body>
</html>