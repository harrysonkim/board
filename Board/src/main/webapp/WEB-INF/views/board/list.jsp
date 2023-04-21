<%@page import="java.util.List"%>
<%@page import="web.dto.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<% List<Board> list = (List<Board>) request.getAttribute("board_list"); %>

<jsp:include page="../layout/header.jsp"/>

<div class="container text-center mt-5">

<h1>글 전체 목록</h1>

<table class="table table-striped table-Light mt-5">

	<tr class="col table-dark bg-red;">
		<th>글번호</th>
		<th>제목</th>
		<th>아이디</th>
		<th>조회수</th>
		<th>작성일</th>
	</tr>

<% for( Board board: list ) { %>
	<tr class="col">
		<td><%= board.getBoardNo() %></td>
		<td>
		<a href="/board/view?boardno=<%= board.getBoardNo() %>">
			<%= board.getTitle() %>
		</a>
		</td>
		<td><%= board.getUserId() %></td>
		<td><%= board.getHit() %></td>
		<td><%= board.getwrite_Date() %></td>
	</tr>
<% } %>
</table>

</div>
<div class="d-grid gap-2 d-md-flex justify-content-md-end">
<c:if test="${login eq true }">
	<a href="/board/write"><button type="button" class="btn btn-primary">글쓰기</button></a>
</c:if>
<c:if test="${empty login }">
	<a href="/views/main"><button type="button" class="btn btn-primary">글쓰기</button></a>
</c:if>
</div>


<jsp:include page="../layout/paging.jsp"/>

<jsp:include page="../layout/footer.jsp"/>