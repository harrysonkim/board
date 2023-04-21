package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dto.Board;
import web.dto.BoardFile;
import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/board/update")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardServiceImpl();
       
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/board/update [GET]");

		Board board = new Board();
		board.setBoardNo( Integer.parseInt(request.getParameter("boardno") ) );
		
		Board Param = boardService.updateView(board);
		BoardFile ParamFile = boardService.viewFile(board);
		request.setAttribute("Param", Param);
		request.setAttribute("ParamFile", ParamFile);
		
		request.getRequestDispatcher("/WEB-INF/views/board/update.jsp").forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/board/update [POST]");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		Board board = new Board();
		boardService.update(request, board);
	
		response.sendRedirect("/board/list");
	}

}
