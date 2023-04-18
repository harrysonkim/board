package web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBUtill.JDBCTemplate;
import web.dao.face.MemberDao;
import web.dto.Member;

public class MemberDaoImpl implements MemberDao{

	PreparedStatement ps = null;
	ResultSet rs = null;

	@Override
	public int selectCntMemberByUseridUserpw(Connection conn, Member memberLogin) {
		
		String sql = "";
		sql += "SELECT count(*) FROM member";
		sql += " WHERE";
		sql += " userid = ? AND";
		sql += " userpw = ?";
	
		int result = 0;
		try {
			ps = conn.prepareStatement(sql);
		
			ps.setString(1, memberLogin.getUserId());
			ps.setString(2, memberLogin.getUserPw());
			rs = ps.executeQuery();
			
			rs.next();
			
			result = rs.getInt(1);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		System.out.println("MemberDaoImpl selectCntMemberByUseridUserpw() 반환값 : " + result);
		return result;
	}

	@Override
	public Member selectMemberByUserid(Connection conn, Member memberLogin) {

		String sql = "";
		sql += "SELECT * FROM member";
		sql += " WHERE";
		sql += " userid = ?";
		
		Member res = null;
		
		try {
			
			ps = conn.prepareStatement(sql);
		
			ps.setString(1, memberLogin.getUserId());
			rs = ps.executeQuery();
			
			rs.next();

			res = new Member();
			
			res.setUserId( rs.getString("userid"));
			res.setUserPw( rs.getString("userpw"));
			res.setUserNick( rs.getString("usernick"));
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		System.out.println("MemberDaoImpl selectMemberByUserid() 반환값 : " + res);
		return res;
	}

	@Override
	public int insert(Connection conn, Member member) {

		String sql = "";
		sql += "INSERT INTO member";
		sql += " values (?,?,?)";
		
		int a = 0;
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, member.getUserId());
			ps.setString(2, member.getUserPw());
			ps.setString(3, member.getUserNick());
		
			a = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		System.out.println("MemberDaoImpl insert()" + a);
		
		return a;
	}
}
