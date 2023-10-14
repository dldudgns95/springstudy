<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>
  
  $(function(){
	fnShopSearch();
  })
  
  function fnShopSearch(){
    $('#btn_search').click(function(){
  	  if($('#query').val() === '') {
  	    alert('검색어를 입력해주세요.');
  	    return;
  	  }
      $.ajax({
      	type: 'get',
      	url: '${contextPath}/shop/list.do',
      	data:'query='+ $('#query').val() +'&display=' + $('#search_display').val() + '&sort=' + $('.search_sort').val(),
      	dataTyp: 'json',
      	success: function(resData){
      	  fnSearchResult(resData);      		
      	}
      })
    })
  }
  
  function fnSearchResult(resData){
    $('#search_result').empty();
    for(let i=0; i<resData.length; i++){
      var result = '<tr><td><a href="'+ resData[i].link + '">' + resData[i].title + '</a></td><td><a href="'+ resData[i].link +'"><img src="' + resData[i].image + '" width="192px"></a></td><td>' + resData[i].lprice + '</td><td>' + resData[i].mallName + '</td></tr>';
      $('#search_result').append(result);
    }
      console.log(resData[0]);
      console.log(resData);
  }
  
</script>
</head>
<body>

  <h1>쇼핑 하기</h1>  
  <div>
    검색결과건수
    <select id="search_display">
      <option>10</option>
      <option>20</option>
      <option>30</option>
      <option>40</option>
      <option>50</option>
    </select>  
  </div>
  <div>
    <input type="radio" class="search_sort" name="search_sort" id="sim" value="sim" checked="checked">
    <label for="sim">유사도순</label>
    <input type="radio" class="search_sort" name="search_sort" id="date" value="date">
    <label for="date">날짜순</label>
    <input type="radio" class="search_sort" name="search_sort" id="asc" value="asc">
    <label for="asc">낮은가격순</label>
    <input type="radio" class="search_sort" name="search_sort" id="dsc" value="dsc">
    <label for="dsc">높은가격순</label>
  </div>
  <div>
  <label for="query">검색어 입력 </label>
  <input type="text" id="query" name="query">
  <button type="button" id="btn_search">검색</button>
  </div>
  
  <hr>
  
  <table border="1">
    <thead>
      <tr>
        <td>상품명</td>
        <td>썸네일</td>
        <td>최저가</td>
        <td>판매처</td>
      </tr>
    </thead>
    <tbody id="search_result">
    </tbody>
  </table>
  
</body>
</html>