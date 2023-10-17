<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
  #paging a {
    margin: 10px;
  }
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>

  $(function(){
	fnMemberRegister();
	fnMemberList();
	fnInit();
	fnChkAll();
	fnChkOne();
	fnMemberDetail();
	fnMemberModify();
	fnDeleteMember();
	fnDeleteMembers();
  })
  
  // 입력란 초기화
  function fnInit(){
	$('#memberNo').val('');
	$('#id').val('').prop('disabled', false);
	$('#name').val('');
	$(':radio[value=none]').prop('checked', true);
	$('#address').val('');
	$('#btn_register').prop('disabled', false);
	$('#btn_modify').prop('disabled', true);
	$('#btn_delete').prop('disabled', true);
  }
  
  // 회원 등록
  function fnMemberRegister(){
	$('#btn_register').click(function(){
      $.ajax({
    	// 요청
    	type: 'post',
    	url: '${contextPath}/members',
    	contentType: 'application/json',
    	data: JSON.stringify({
    	  id: $('#id').val(),
    	  name: $('#name').val(),
    	  gender: $(':radio:checked').val(),
    	  address: $('#address').val()
    	}),
    	//응답
    	dataType: 'json',
    	success: function(resData){	// resData === {"addResult": 1}
    	  if(resData.addResult === 1){
    	    alert('회원 정보가 등록되었습니다.');
    	    page = 1;
    	    fnMemberList();
    	    fnInit();
    	  }else {
    	    alert('회원 정보 등록에 실패했습니다.');	
    	  }
    	},
    	error: function(jqXHR){
    	  alert(jqXHR.responseText + '(예외코드 ' + jqXHR.status + ')');
    	  
    	}
      })
	})
  }
  
  // 전역 변수 (모든 함수에서 사용할 수 있는 변수)
  var page = 1;
  
  //회원 목록
  function fnMemberList(){
	$.ajax({
	  // 요청
	  type: 'get',
	  url: '${contextPath}/members/page/' + page,
	  // 응답
	  dataType: 'json',
	  success: function(resData){
		// 회원 목록을 테이블로 만들기
		$('#member_list').empty();
		$.each(resData.memberList, function(i, member){
		 var tr = '<tr>';
		 tr += '<td><input type="checkbox" class="chk_one" value="'+member.memberNo+'"></td>';
		 tr += '<td>'+member.id+'</td>';
		 tr += '<td>'+member.name+'</td>';
		 tr += '<td>'+member.gender+'</td>';
		 tr += '<td>'+member.address+'</td>';
		 tr += '<td><button type="button" class="btn_detail" data-member_no="'+member.memberNo+'">조회</button></td>';
		 tr += '</tr>';
		 $('#member_list').append(tr);
		})
		// 페이징
		$('#paging').html(resData.paging);
	  }
	})
  }
  
  // 페이지를 바꿀때마다 호출되는 fnAjaxPaging 함수
  function fnAjaxPaging(p) {
	page = p;			// 페이지 번호를 바꾼다.
	fnMemberList();		// 새로운 목록을 가져온다.
  }
  
  function fnChkAll(){
	$('#chk_all').click(function(){
	  if( $('#chk_all').prop('checked')){
		$('.chk_one').prop('checked', true);
	  } else {
		$('.chk_one').prop('checked', false);
	  }
	  
	})
  }
  
  function fnChkOne(){
	$(document).on('click', '.chk_one', function(){
	  var total = $('.chk_one').length;
	  var checked = $('.chk_one:checked').length;
	  if(total == checked) {
		$('#chk_all').prop('checked', true);
	  } else {
		  $('#chk_all').prop('checked', false);
	  }
	})
  }
  
  // 회원 정보 상세 조회하기
  function fnMemberDetail(){
	$(document).on('click', '.btn_detail', function(){
	  $.ajax({
		// 요청
		type: 'get',
		url: '${contextPath}/members/' + $(this).data('member_no'),
		//url: '${contextPath}/members/0',
		// 응답
		dataType: 'json',
		success: function(resData){
		  var member = resData.member;
		  if(!member){
			alert('회원 정보를 조회할 수 없습니다.');
		  } else {
			$('#memberNo').val(member.memberNo);
			$('#id').val(member.id).prop('disabled', true);
			$('#name').val(member.name);
			$(':radio[value='+member.gender+']').prop('checked', true);
			$('#address').val(member.address);
			$('#btn_register').prop('disabled', true);
			$('#btn_modify').prop('disabled', false);
			$('#btn_delete').prop('disabled', false);
		  }
		}
	  })
	})
  }
  
  // 회원 정보 수정하기
  function fnMemberModify(){
	$('#btn_modify').click(function(){
	  $.ajax({
		// 요청
		type: 'put',
		url: '${contextPath}/members',
		contentType: 'application/json',
		data: JSON.stringify({
		  memberNo: $('#memberNo').val(),
		  name: $('#name').val(),
		  gender: $(':radio:checked').val(),
		  address: $('#address').val()
		}),
		// 응답
		dataType: 'json',
		success: function(resData){
		  if(resData.modifyResult === 1){
			alert('회원 정보가 수정되었습니다.');
			fnMemberList();
		  } else {
			alert('회원 정보가 수정되지 않았습니다.');
		  }
		}
		
	  })
	})
  }
  
  // 회원 정보 삭제
  function fnDeleteMember(){
	$('#btn_delete').click(function(){
	  if(confirm('회원 정보를 삭제하시겠습니까?')){
	    $.ajax({
	      // 요청
		  type: 'delete',
		  url: '${contextPath}/member/' + $('#memberNo').val(),
		  // 응답
		  dataType:'json',
		  success: function(resData){
		    if(resData.deleteResult === 1){
		  	  alert('회원 정보가 삭제되었습니다.');
		  	  fnInit();
		  	  page = 1;
			  fnMemberList();
			} else {
			  alert('회원 정보가 삭제되지 않았습니다.');
		    }
		  }
		})
	  }
	})
  }
  
  //회원들의 정보 삭제
  function fnDeleteMembers(){
	// 체크된 요소의 value를 arr에 저장하기(push 메소드)
	var arr = [];
	var chkOne = $('.chk_one');
	$.each(chkOne, function(i, elem){
	  if($(elem).is(':checked')){
	    arr.push($(elem).val());
	  }
	})
	// 체크된 요소가 없으면 삭제 중지
	if(arr.length === 0){
	  alert('선택된 회원 정보가 없습니다. 다시 시도하세요.');
	  return;
	}
	$('#btn_delete_list').click(function(){
	if(!confirm('선택한 회원의 정보를 삭제하시겠습니까?')){
	  return;
	}		
	// 선택된 회원 삭제
	$.ajax({
	  type: 'delete',
	  url: '${contextPath}/members/' + arr.join(','),
	  dataType: 'json',
	  success: function(resData){
		if(resData.deleteResult === arr.length){
		  alert('선택한 회원 정보들이 삭제되었습니다.');
		  fnInit();
		  page = 1;
		  fnMemberList();
		} else {
		  alert('선택한 회원 정보들이 삭제되지 않았습니다.');
		}
	  }
	})
	  
	  
	  /*
	  // 체크된 요소의 value를 arr에 저장하기(push 메소드)
	  var arr = [];
	  $('.chk_one:checked').each(function(i){
		arr.push($(this).val());
	  })
	  console.log(arr);
	  */
	})
  }
  
</script>

</head>
<body>

  <div>
    <h1>회원 관리하기</h1>
    <input type="hidden" id="memberNo">
    <div>
      <label for="id">아이디</label>
      <input type="text" id="id">
    </div>
    <div>
      <label for="name">이름</label>
      <input type="text" id="name">
    </div>
    <div>
      <input type="radio" id="none" name="gender" value="none" checked>
      <label for="none">선택안함</label>
      <input type="radio" id="man" name="gender" value="man">
      <label for="man">남자</label>
      <input type="radio" id="woman" name="gender" value="woman">
      <label for="woman">여자</label>
    </div>
    <div>
      <label for="address">주소</label>
      <select id="address">
        <option value="">:::선택:::</option>
        <option>서울</option>
        <option>경기</option>
        <option>인천</option>
      </select>
    </div>
    <div>
      <button type="button" onclick="fnInit()">초기화</button>
      <button type="button" id="btn_register">등록</button>
      <button type="button" id="btn_modify">수정</button>
      <button type="button" id="btn_delete">삭제</button>
    </div>
  </div>
  
  <hr>
  
  <div>
    <div>
      <button type="button" id="btn_delete_list">선택삭제</button>
    </div>
    <table border="1">
      <thead>
        <tr>
          <td><input type="checkbox" id="chk_all"><label for="chk_all">전체선택</label></td>
          <td>아이디</td>
          <td>이름</td>
          <td>성별</td>
          <td>주소</td>
          <td></td>
        </tr>
      </thead>
      <tbody id="member_list"></tbody>
      <tfoot>
        <tr>
          <td colspan="6" id="paging"></td>
        </tr>
      </tfoot>
    </table>
  </div>  
  
</body>
</html>