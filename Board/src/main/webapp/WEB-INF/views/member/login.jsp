<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>로그인하기</h1>
<hr>

<form action="/member/login" method="post">

아이디 : <input type="text" name="userId" id="userId">
비밀번호 : <input type="text" name="userPw" id="userPw">

<button>확인</button>
</form>

</body>
</html>