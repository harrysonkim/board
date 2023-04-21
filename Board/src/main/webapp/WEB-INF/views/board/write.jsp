<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="../layout/header.jsp"/>

<h1 class="mt-5">글 작성</h1>
<hr>

<form action="/board/write" method="post" class="form" enctype="multipart/form-data">

<div class="form-floating mb-3 mt-5">
	<input type="text" class="form-control" id="title" name="title" required="required">
	<label for="title">제목</label>
</div>

<div class="form-floating mb-3 mt-3">
  <input type="text" class="form-control" id="content" name="content" required="required">
  <label for="content">내용</label>
</div>

<label>파일<input type="file" name="upfile"></label><br><br>

<div class="d-grid gap-2 d-md-flex justify-content-md-end">
<button class="btn btn-primary">작성</button>
<a href="javascript:history.go(-1);"><button type="button" class="btn btn-primary">취소</button></a>
</div>

</form>
<jsp:include page="../layout/footer.jsp"/>