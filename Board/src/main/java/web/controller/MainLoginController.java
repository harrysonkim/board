package web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.dto.Member;
import web.service.face.MemberService;
import web.service.impl.MemberServiceImpl;

@WebServlet("/member/login")
public class MainLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/member/login [GET]");

		request.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/member/login [POST]");

		session = request.getSession();

		MemberService memberService = new MemberServiceImpl();

		Member memberLogin = memberService.getLoginMember(request);
		boolean login = memberService.login(memberLogin);
		System.out.println("로그인 성공 실패? " + login);
		Member info = memberService.info(memberLogin);

		if (login == true) {

			session.setAttribute("login", login);
			session.setAttribute("userid", info.getUserId());
			session.setAttribute("usernick", info.getUserNick());
			session.setAttribute("info", info);

			response.sendRedirect("/views/main");
			return;
		}

		session.setAttribute("login", login);

		request.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(request, response);
	}
}
