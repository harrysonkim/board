package web.dao.face;

import java.sql.Connection;

import web.dto.Member;

public interface MemberDao {

	public int selectCntMemberByUseridUserpw(Connection conn, Member memberLogin);

	public Member selectMemberByUserid(Connection conn, Member memberLogin);

	public int insert(Connection conn, Member member);

}
