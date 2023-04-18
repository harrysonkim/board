<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>메인 페이지</h1>
<hr>

<% if( (boolean)session.getAttribute("login") ) {  %>

	게시판 목록 버튼 : <a href="/board/list"><button>글 목록</button></a> 
	로그아웃 버튼 :  <a href="/member/logout"><button>로그 아웃</button></a>

<% } else { %>

	회원가입 버튼 : <a href="/member/join"><button>회원가입</button></a>
	로그인 버튼 : <a href="/member/login"><button>로그인</button></a>

<% }%>


</body>
</html>