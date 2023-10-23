/**
 * 회원가입 페이지
 */
 
  /* 함수 호출 */
  $(() => {
	fnCheckName();
	fnCheckMobile();
	fnModifyUser();
	fnPwModify();
	fnLeaveUser();
  })
  
  /* 전역변수 선언 */
  var namePassed = false;
  var mobilePassed = false;
  
  /* 함수 정의 */
  
  const getContextPath = () => {
    let begin = location.href.indexOf(location.host) + location.host.length;
  	let end = location.href.indexOf('/',begin+1);
  	return location.href.substring(begin, end);
  }
  
  const fnCheckName = () => {
	$('#name').blur((ev) => {
	  let name = ev.target.value;
	  // 한글 2~4자 제한
	  let regName = /^[가-힣]{2,4}$/;
	  namePassed = regName.test(name);
	  if(namePassed){
		$('#msg_name').text('');
	  } else {
		$('#msg_name').text('이름을 정확히 입력해주세요.');
	  }
	})
  }
  
  const fnCheckMobile = () => {
	$('#mobile').keyup((ev) => {
	  let mobile = ev.target.value.replaceAll('-', '');
	  let regMobile = /^010[0-9]{8}$/;
	  mobilePassed = regMobile.test(mobile);
	  if(mobilePassed){
		$('#msg_mobile').text('');
	  } else {
		$('#msg_mobile').text('핸드폰 번호를 정확히 입력해주세요.');
	  }
	})	
  }
  
  const fnModifyUser = () => {
	$('#btn_modify').click(() => {
	  if(!namePassed){
	  	alert('이름을 확인하세요');
		ev.preventDefault();
		return;
	  } else if(!mobilePassed){
	  	alert('휴대전화번호를 확인하세요');
		ev.preventDefault();
		return;
	  }
  	  $.ajax({
  	    //요청
  	    type: 'post',
  	    url: getContextPath() + '/user/modify.do',
  	    data: $('#frm_mypage').serialize(),
  	    //응답
  	    dataType: 'json',
  	    success: (resData) => {	// {"modifyResult": 1}
  		  if(resData.modifyResult === 1) {
  		    alert('회원 정보가 수정되었습니다.');
  		  } else {
  		    alert('회원 정보가 수정되지 않았습니다.');
  		  }
  	    }
  	  })
	})
  }
  
  const fnPwModify = () => {
	$('#btn_pw_modify').click(() => {
	  location.href= getContextPath() + '/user/pwModify.form';
	})
  }
  
  const fnJoin = () => {
	$('#btn_modify').click((ev) => {
	  if(!namePassed){
	  	alert('이름을 확인하세요');
		ev.preventDefault();
		return;
	  } else if(!mobilePassed){
	  	alert('휴대전화번호를 확인하세요');
		ev.preventDefault();
		return;
	  }
	})
  }
  
  const fnLeaveUser = () => {
  	$('#btn_leave').click(() => {
  	  if(confirm('회원 탈퇴를 하시겠습니까?')) {
  	    $('#frm_mypage').prop('action', getContextPath() + '/user/leave.do');
  	    $('#frm_mypage').submit();
  	  }
  	})
  }
  
  
  
  
  
  
  
  
  
  
  
  
