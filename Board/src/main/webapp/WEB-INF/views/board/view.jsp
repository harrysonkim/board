<%@page import="web.dto.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% Board board = (Board) request.getAttribute("selectBoardNo"); %>

<!-- header -->
<jsp:include page="../layout/header.jsp"/>

<!-- body -->
<h1 class="mt-5">글 상세보기</h1>
<hr>
<div class="d-grid gap-3">
	<input type="hidden" class="p-2 bg-light border" name="boardNo" value="<%= board.getBoardNo() %>">
	<div class="p-2 bg-light border">제목 : <%= board.getTitle() %></div>
	<div class="p-2 bg-light border">아이디 : <%= board.getUserId() %></div>
	<div class="p-2 bg-light border">내용 : <%= board.getContent() %></div>
	<div class="p-2 bg-light border">조회수 : <%= board.getHit() %></div>
	<div class="p-2 bg-light border">작성일 : <%= board.getwrite_Date() %></div>
</div>	

<div class="d-grid gap-2 d-md-flex justify-content-md-end mt-3">

		<a href="/board/list"><button class="btn btn-primary">목록</button></a>
		<a href="/board/update?boardno=<%= board.getBoardNo() %>"><button class="btn btn-primary">수정</button></a>
		<a href="/board/delete"><button class="btn btn-primary">삭제</button></a>
	
</div>

<!-- footer -->
<jsp:include page="../layout/footer.jsp"/>