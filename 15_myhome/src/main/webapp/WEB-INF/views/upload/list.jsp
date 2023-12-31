<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis() %>" />
<!-- jsp:include 는 파라미터 전달이 필요할 때 사용 -->    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="업로드게시판" name="title"/>
</jsp:include>

<style>
  div {
    box-sizing: border-box;
  }
  .upload_list {
    width: 1000px;
    margin: 10px auto;
    display: flex;
    flex-wrap: wrap;/* 박스 크기에 따라 정렬된다.*/
  }  
  .upload {
    width: 300px;
    height: 300px;
    border: 1px solid gray;
    text-align: center;
    padding-top: 100px;
    margin: 20px 15px;
  }
  .upload:hover {
    background-color: silver;
    cursor: pointer;
  }
</style>

<div>
  
  <div>
    <a href="${contextPath}/upload/write.form">
      <button type="button" class="btn btn-primary">새글작성</button>
    </a>
  </div>
  
  <hr>
  
  <div id="upload_list" class="upload_list"></div>
  
</div>

<script>

  // 전역 변수
  var page = 1;
  var totalPage = 0;
  
  const fnGetUploadList = () => {
	$.ajax({
	  // 요청
	  type: 'get',
	  url: '${contextPath}/upload/getList.do',
	  data: 'page=' + page,
	  //응답
	  dataType: 'json',
	  success: (resData) => {  // resData = {"uploadList": [], "totalPage": 10}
	    totalPage = resData.totalPage;
		$.each(resData.uploadList, (i, upload) => { 
		  let str = '<a href="${contextPath}/upload/detail.do?uploadNo='+upload.uploadNo+'"><div class="upload">';
		  str += '<div>제목: ' + upload.title        + '</div>';
		  if(upload.userDto === null){
    		str += '<div>작성: 정보없음</div>';
		  } else {
		    str += '<div>작성: ' + upload.userDto.name + '</div>';
		  }
		  str += '<div>생성: ' + upload.createdAt    + '</div>';
		  str += '<div>첨부: ' + upload.attachCount  + '개</div>';
		  str += '</div></a>';
		  $('#upload_list').append(str);
		})
	  }
	})
  }
  
  
  const fnScroll = () => {
	  
	var timerId;  // 최초 undefined
	  
	$(window).on('scroll', () => {
		
		if(timerId){  // timerId가 undefined이면 false로 인식, timerId가 값을 가지면 true로 인식
		  clearTimeout(timerId);
		}
		
		timerId = setTimeout(() => {  // setTimeout 실행 전에서 timerId가 undefined 상태, setTImeout이 한 번이라도 동작하면 timerId가 값을 가짐
		
		  let scrollTop = $(window).scrollTop();     // 스크롤바 위치(스크롤 된 길이)
		  let windowHeight = $(window).height();     // 화면 전체 크기
		  let documentHeight = $(document).height(); // 문서 전체 크기
		  
		  if(scrollTop + windowHeight + 100 >= documentHeight) {  // 스크롤이 바닥에 닿기 50px 전에 true가 됨
			if(page > totalPage) {  // 마지막 페이지를 보여준 이후에 true가 됨
			  return;				// 마지막 페이지를 보여준 이후에 아래 코드를 수행하지 말 것
			}
		    page++;
		    fnGetUploadList();
		  }
			
	  }, 200);  // 200밀리초(0.2초) 후 동작(시간은 임의로 조정 가능)
	  
	})
	
  }
  
  
  const fnAddResult = () => {
	let addResult = '${addResult}'; // '', 'true', 'false'
	if(addResult !== '') {
	  if(addResult === 'true') {
		alert('성공적으로 업로드 되었습니다.');
		// 목록 갱신
		$('#upload_list').empty();
		fnGetUploadList();
	  } else {
		alert('업로드가 실패하였습니다.');
	  }
	}
  }
  
  const fnRemoveResult = () => {
	let removeResult = '${removeResult}'; // '', 'true', 'false'
	if(removeResult !== '') {
	  if(removeResult === '1') {
		alert('게시글이 삭제 되었습니다.');
		// 목록 갱신
		$('#upload_list').empty();
		fnGetUploadList();
	  } else {
		alert('게시글 삭제가 실패했습니다.');
	  }
	}
  }
  
  fnGetUploadList();
  fnScroll();
  fnAddResult();
  fnRemoveResult();
  
</script>



<!-- %@ include 는 파라미터 전달이 필요없을 때 사용 -->
<%@ include file="../layout/footer.jsp"%>