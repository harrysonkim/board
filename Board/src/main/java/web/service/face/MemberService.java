package web.service.face;

import javax.servlet.http.HttpServletRequest;

import web.dto.Member;

public interface MemberService {

	public Member getLoginMember(HttpServletRequest request);

	public boolean login(Member memberLogin);

	public Member info(Member memberLogin);

	public Member getJoinMember(HttpServletRequest request);

	public void join(Member member);

}
