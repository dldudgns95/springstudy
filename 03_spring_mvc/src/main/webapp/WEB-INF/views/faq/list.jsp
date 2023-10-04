<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
  // ${addResult}값이 null 이면 컴파일에러가 나기 때문에 문자열로 묶어준다.
  var addResult = '${addResult}';	// var addResult = '1';   add 성공
									// var addResult = '0';   add 실패
									// var addResult = '';    add와 상관 없음
  if(addResult !== ''){
	  if(addResult === '1'){
		  alert('add 성공했습니다.');
	  } else {
		  alert('add 실패했습니다.');
	  }
  }
</script>
</head>
<body>
  faq 목록
</body>
</html>