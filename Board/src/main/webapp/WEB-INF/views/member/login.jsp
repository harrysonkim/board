<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="../layout/header.jsp"/>

<h1 class="mt-5">로그인하기</h1>
<hr>

<div>

<form action="/member/login" method="post" class="mt-5">
<div class="form-floating mt-3">
  <input type="text" class="form-control" id="userId" name="userId" placeholder="아이디">
  <label for="userId">아이디</label>
</div>
<div class="form-floating mt-3">
  <input type="text" class="form-control" id="userPw" name="userPw" placeholder="아이디">
  <label for="userPw">비밀번호</label>
</div>

<div class="d-grid gap-2 col-6 mx-auto mt-5">
  <button class="btn btn-primary">확인</button>
</div>

</form>

</div>
</body>
</html>