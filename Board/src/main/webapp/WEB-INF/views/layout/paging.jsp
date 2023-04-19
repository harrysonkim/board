<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
  <ul class="pagination justify-content-center">

	<%-- 이전 페이지 이동 --%>
	<c:if test="${paging.curPage gt 1 }" >
	    <li class="page-item"><a class="page-link" href="./list?curPage=1">&laquo;</a></li>
	    <li class="page-item"><a class="page-link" href="./list?curPage=${paging.startPage-1 }">&lt;</a></li>
	</c:if>
	<c:if test="${paging.curPage eq 1 }" >
	</c:if>
	
	<%--페이징 번호 리스트 --%>
    <c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage }">

		<c:if test="${paging.curPage eq i }">
	    <li class="page-item active"><a class="page-link" href="/board/list?curPage=${i}">${i }</a></li>
		</c:if>

		<c:if test="${paging.curPage ne i }">
	    <li class="page-item"><a class="page-link" href="/board/list?curPage=${i}">${i }</a></li>
		</c:if>

    </c:forEach >
    
   	<c:if test="${paging.curPage ne paging.totalPage }" >
	    <li class="page-item"><a class="page-link" href="/board/list?curPage=${paging.endPage+1 }">&gt;</a></li>
	    <li class="page-item"><a class="page-link" href="/board/list?curPage=${paging.totalPage }">&raquo;</a></li>
	</c:if>
	<c:if test="${paging.curPage eq paging.totalPage }" >
	</c:if>
    
  </ul>
</div>
