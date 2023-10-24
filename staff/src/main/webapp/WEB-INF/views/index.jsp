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
  $(() => {
	fnRegisterStaff();
	fnGetStaffList();
	fnGetStaffDetail();
  })
  
  const fnRegisterStaff = () => {
	$('#btn_register').click(() => {
	  $.ajax({
		type: 'post',
		url: '${contextPath}/add.do',
		data: $('#frm_register').serialize(),
		dataType: 'json',
		success: (resData) => {
		  if(resData.addResult === 1) {
			alert('사원 등록이 성공했습니다.');
			fnGetStaffList();	// 목록 갱신
			fnInit();			// 입력란 초기화
		  } else {
			alert('사원 등록을 실패했습니다.');
		  }
		},
		error: (jqXHR) => {
			  if(jqXHR.responseJSON.addResult === 0){
				alert('사원 등록을 실패했습니다.');
			  }
			}
	  })
	}) 
  }
  
  const fnGetStaffList = () => {
	$.ajax({
	  type: 'get',
	  url: '${contextPath}/list.do',
	  dataType: 'json',
	  success: (resData) => {
		$('#staff_list').empty();
		$.each(resData, (i, staff) => {
		  var tr = '<tr>';
		  tr += '<td>' + staff.sno + '</td>';
		  tr += '<td>' + staff.name + '</td>';
		  tr += '<td>' + staff.dept + '</td>';
		  tr += '<td>' + staff.salary + '</td>';
		  tr += '</tr>';
		  $('#staff_list').append(tr);
		})
	  }
	})
  }
  
  const fnInit = () => {
	$('#sno').val('');
	$('#name').val('');
	$('#dept').val('');
  }
  
  const fnGetStaffDetail = () => {
	$('#btn_query').click(() => {
	  $.ajax({
		type: 'post',
		url: '${contextPath}/detail.do',
		data: 'sno=' + $('#query').val(),
		dataType: 'json',
		success: (resData) => {
		  if(resData.staff != null){
			$('#staff_list').empty();
		    var tr = '<tr>';
		    tr += '<td>' + resData.staff.sno + '</td>';
		    tr += '<td>' + resData.staff.name + '</td>';
		    tr += '<td>' + resData.staff.dept + '</td>';
		    tr += '<td>' + resData.staff.salary + '</td>';
		    tr += '</tr>';
		    $('#staff_list').append(tr);
		  } else if(resData.staff == null) {
			alert('조회된 사원 정보가 없습니다.');
		  }
		},
		error: (jqXHR) => {
		  alert('검색하신 번호는 없는 번호입니다.');
		}
	  })
	})
	$('#btn_list').click(() => {
	  fnGetStaffList();
	})
  }
  
</script>

</head>
<body>

  <div>
    <h1>사원등록</h1>
    <div>
      <form id="frm_register">
        <input type="text" name="sno" id="sno" placeholder="사원번호">
        <input type="text" name="name" id="name" placeholder="사원명">
        <input type="text" name="dept" id="dept" placeholder="부서명">
        <button type="button" id="btn_register">등록</button>
      </form>
    </div>
  </div>
  
  <hr>
  
  <div>
    <h1>사원조회</h1>
    <div>
      <input type="text" name="query" id="query" placeholder="사원번호입력">
      <button type="button" id="btn_query">조회</button>
      <button type="button" id="btn_list">전체</button>
    </div>
  </div>
  
  
  <hr>
  
  <div>
    <h1>사원목록</h1>
    <div>
      <table border="1">
        <thead>
          <tr>
            <td>사원번호</td>
            <td>사원명</td>
            <td>부서명</td>
            <td>연봉</td>
          </tr>
        </thead>
        <tbody id="staff_list"></tbody>
      </table>
    </div>
  </div>
  
</body>
</html>