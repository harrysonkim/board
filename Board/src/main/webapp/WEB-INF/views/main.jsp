<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</head>
<body>

<h1>메인 페이지</h1>
<hr>

<% if( session.getAttribute("login")== null || !(boolean)session.getAttribute("login") ){%>

	게시판 목록 버튼 : <a href="/board/list"><button>글 목록</button></a> 
	로그아웃 버튼 :  <a href="/member/logout"><button>로그 아웃</button></a>

<% } else if( (boolean)session.getAttribute("login") ) { %>

	회원가입 버튼 : <a href="/member/join"><button>회원가입</button></a>
	로그인 버튼 : <a href="/member/login"><button>로그인</button></a>

<% }%>

</body>
</html>