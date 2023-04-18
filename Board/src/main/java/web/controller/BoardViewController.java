package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dto.Board;
import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/board/view")
public class BoardViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/board/view/ [GET]");
			
		BoardService boardService = new BoardServiceImpl();
	
		Board boardNo = boardService.getBoardno(request); 
		
		Board selectBoardNo = boardService.view(boardNo); 
				
		request.setAttribute("selectBoardNo", selectBoardNo);
		
		request.getRequestDispatcher("/WEB-INF/views/board/view.jsp").forward(request, response);
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/board/view/ [POST]");
	
	}

}
