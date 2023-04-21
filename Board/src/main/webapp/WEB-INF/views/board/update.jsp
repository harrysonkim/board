<%@page import="web.dto.BoardFile"%>
<%@page import="web.dto.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% Board board = (Board) request.getAttribute("Param"); %>
<% BoardFile boardFile = (BoardFile) request.getAttribute("ParamFile"); %>

<!-- header -->
<jsp:include page="../layout/header.jsp"/>

<!-- body -->
<h1 class="mt-5">게시글 수정</h1>
<hr>

<form action="/board/update?boardno=<%= board.getBoardNo()%>" method="post" enctype="multipart/form-data">

<div class="mb-3">
  <label for="title" class="form-label">제목</label>
  <input type="text" class="form-control" id="title" name="title" value="<%= board.getTitle()%>">
</div>

<div class="mb-3">
	<label for="userid" class="form-label">아이디</label>
	<p class="form-control" id="userid"><%= board.getUserId()%></p>
	<label for="write_date" class="form-label">작성일</label>
	<p class="form-control" id="write_date"><%= board.getwrite_Date() %></p>
</div>

<div class="mb-3">
	<label for="content" class="form-label">내용</label>
	<textarea class="form-control" id="content" name="content" rows="3"><%= board.getContent()%></textarea>
</div>

<div>
	<label>업로드된 파일 : <%=boardFile.getOriginname() %></label><br><br>
</div>

<div>
	<label>파일 : <input type="file" name="upfile"></label><br><br>
</div>

<div class="d-grid gap-2 d-md-flex justify-content-md-end">
<button class="btn btn-primary">수정</button>
<a href="javascript:window.history.go(-1);">
	<button type="button" class="btn btn-primary">취소</button></a>
</div>
	
</form>

<!-- footer -->
<jsp:include page="../layout/footer.jsp"/>