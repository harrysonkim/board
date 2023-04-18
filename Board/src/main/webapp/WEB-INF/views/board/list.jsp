<%@page import="java.util.List"%>
<%@page import="web.dto.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% List<Board> list = (List<Board>) request.getAttribute("board_list"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>글 전체 목록 보여주기</h1>
<div>
	<div>
		글번호
		제목
		아이디
		내용
		조회수
		작성일
	</div>
	
<%for( int i = 0; i < list.size(); i++) { %>
	<div>
		<%= list.get(i).getBoardNo() %>
		<a href="/board/view?boardno=<%= list.get(i).getBoardNo() %>"><%= list.get(i).getTitle() %></a>
		<%= list.get(i).getTitle() %>
		<%= list.get(i).getUserId() %>
		<%= list.get(i).getContent() %>
		<%= list.get(i).getHit() %>
		<%= list.get(i).getwrite_Date() %>
	</div>
<% } %>

</div>


</body>
</html>