<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis() %>" />
<!-- jsp:include 는 파라미터 전달이 필요할 때 사용 -->    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="업로드게시글작성" name="title"/>
</jsp:include>

<div>
  <h1>Upload 게시글 작성하기</h1>
  <!-- 파일 첨부를 위해서는 method="post", enctype="multipart/form-data"는 필수이다. -->
  <form method="post" action="${contextPath}/upload/add.do" enctype="multipart/form-data">
  
    <div class="mb-3 row">
      <label for="email" class="col-sm-2 col-form-label">작성자</label>
      <div class="col-sm-10">
        <input type="text" class="form-control-plaintext" id="email" value="${sessionScope.user.email}" readonly>
      </div>
    </div>
    <div>
      <label for="title" class="form-label">제목</label>
      <input type="text" class="form-control" id="title" name="title">
    </div>
    <div>
      <label for="contents" class="form-label">내용</label>
      <textarea class="form-control" rows="3" cols="50" name="contents" id="contents"></textarea>
    </div>
    
    <div>
      <label for="files" class="form-label">첨부</label>
      <input type="file" class="form-control" id="files" name="files" multiple>
    </div>
    <div class="d-grid gap-2 col-6 mx-auto">
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
      <button type="submit" class="btn btn-primary" style="margin: 32px;">작성완료</button>
    </div>
  </form>
  
  <div id="file_list"></div>
  
  
</div>
  
<script>

  const fnFileCheck = () => {
    $('#files').change((ev) => {
      $('#file_list').empty();
      var maxSize = 1024 * 1024 * 100;
      var maxSizePerFile = 1024 * 1024 * 10;
      var totalSize = 0;
      var files = ev.target.files;
      for(let i = 0; i < files.length; i++){
        totalSize += files[i].size;
        if(files[i].size > maxSizePerFile){
          alert('각 첨부파일의 최대 크기는 10MB입니다.');
          $(ev.target).val('');
          $('#file_list').empty();
          return;
        }
        $('#file_list').append('<div>' + files[i].name + '</div>');
      }
      if(totalSize > maxSize){
        alert('전체 첨부파일의 최대 크기는 100MB입니다.');
        $(ev.target).val('');
        $('#file_list').empty();
        return;
      }
    })
  }
  
  fnFileCheck();
  
</script>
  
<%@ include file="../layout/footer.jsp"%>