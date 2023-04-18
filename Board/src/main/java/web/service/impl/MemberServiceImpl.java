package web.service.impl;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import DBUtill.JDBCTemplate;
import web.dao.face.MemberDao;
import web.dao.impl.MemberDaoImpl;
import web.dto.Member;
import web.service.face.MemberService;

public class MemberServiceImpl implements MemberService {

	MemberDao memberDao = new MemberDaoImpl();
	Connection conn = JDBCTemplate.getConnection();
	
	@Override
	public Member getLoginMember(HttpServletRequest request) {

		Member member = new Member();
		
		member.setUserId( request.getParameter("userId") );
		member.setUserPw( request.getParameter("userPw") );
		
		return member;
	}

	@Override
	public boolean login(Member memberLogin) {

		int i = memberDao.selectCntMemberByUseridUserpw(conn ,memberLogin);
		
		boolean result = false;

		if ( i > 0) {
			result = true;
		}		
		
		return result;
	}

	@Override
	public Member info(Member memberLogin) {
		
		Member member = memberDao.selectMemberByUserid(conn, memberLogin);
		
		
		
		
		
		return member;
	}

	@Override
	public Member getJoinMember(HttpServletRequest request) {

		Member member = new Member();
		member.setUserId(request.getParameter("userId"));
		member.setUserPw(request.getParameter("userPw"));
		member.setUserNick(request.getParameter("userNick"));
		
		return member;
	}

	@Override
	public void join(Member member) {

		int i = memberDao.insert(conn, member);
		
		if( i > 0) {
			JDBCTemplate.commit(conn);
			System.out.println("회원가입 추가 성공");
		}else {
			JDBCTemplate.rollback(conn);
			System.out.println("회원가입 추가 실패");
		}
		
	}

}
