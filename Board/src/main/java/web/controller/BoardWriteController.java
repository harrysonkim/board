package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/board/write")
public class BoardWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/board/write [GET]");
		
		request.getRequestDispatcher("/WEB-INF/views/board/write.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/board/write [POST]");

		BoardService boardService = new BoardServiceImpl();
		
		boolean result = boardService.write(request);
		
		if (result == true) {
			response.sendRedirect("/board/list");
			
		} else {
			
			response.sendRedirect("/error/errorPage");
		}
		
	}

}