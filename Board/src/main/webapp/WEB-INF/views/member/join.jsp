<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>회원가입 폼</h1>
<hr>

<form action="/member/join" method="post">

아이디 : <input type="text" name="userId" id="userId"><br>
비밀번호 : <input type="text" name="userPd" id="userPd"><br>
닉네임 : <input type="text" name="userNick" id="userNuck">
<button>확인</button>
</form>

</body>
</html>