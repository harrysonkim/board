package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dto.Member;
import web.service.face.MemberService;
import web.service.impl.MemberServiceImpl;

@WebServlet("/member/join")
public class MemberJoinController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/member/join [GET]");
	
		request.getRequestDispatcher("/WEB-INF/views/member/join.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/member/join [POST]");

		MemberService memberService = new MemberServiceImpl();
		
		Member member = memberService.getJoinMember(request);
		memberService.join(member);
		
		request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);
	
	}

}
