package web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utill.Paging;
import web.dto.Board;
import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/board/list")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/Board/list [GET]");

		// 전달 파라미터를 이용해서 현제 페이징 객체 알아내기
		Paging paging = boardService.getPaging(request);
		System.out.println("BoardListController - doGet() : " + paging);
		
		// 모델값으로 페이징 객체 전달
		
		request.setAttribute("paging", paging);

		// 게시글 페이징 조회
		List<Board> list = boardService.getList(paging);
		
		List<Board> board_list = boardService.getList();
		
		request.setAttribute("board_list", list);
		
		request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/Board/list [GET]");
	
		response.sendRedirect("/board/view");
	}

}
