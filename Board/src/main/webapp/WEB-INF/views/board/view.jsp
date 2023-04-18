<%@page import="web.dto.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% Board board = (Board) request.getAttribute("selectBoardNo"); %>
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
	<div>
	<%= board.getBoardNo() %>
	<%= board.getTitle() %>
	<%= board.getUserId() %>
	<%= board.getContent() %>
	<%= board.getHit() %>
	<%= board.getwrite_Date() %>
	</div>	

</div>

<a href="/board/list"><button>목록</button></a>
<a href="#"><button>삭제</button></a>

</body>
</html>