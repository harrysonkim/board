package web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import DBUtill.JDBCTemplate;
import web.dao.face.BoardDao;
import web.dto.Board;

public class BoardDaoImpl implements BoardDao{

	PreparedStatement ps =null;
	ResultSet rs = null;
	
	@Override
	public List<Board> selectAll(Connection conn) {

		String sql = "";
		sql += "SELECT * FROM board";

		List<Board> list = new ArrayList<>();

		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Board board = new Board();
				board.setBoardNo(rs.getInt("boardno"));
				board.setTitle(rs.getString("title"));
				board.setUserId(rs.getString("userid"));
				board.setContent(rs.getString("content"));
				board.setHit(rs.getInt("hit"));
				board.setwrite_Date(rs.getDate("write_date"));
				
				list.add(board);
				
			}
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		System.out.println("=========================================");
		System.out.println("BoardDaoImpl selectAll() 반환값 : " + list);
		System.out.println("=========================================");
		return list;
	}

	@Override
	public Board selectBoardByBordno(Connection conn,Board board) {

		String sql = "";
		sql += "SELECT * FROM board";
		sql += " WHERE boardno = ?";

		Board result = new Board();
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, board.getBoardNo());
			rs = ps.executeQuery();
			
			rs.next();
			
			result.setBoardNo(rs.getInt("boardno"));
			result.setTitle(rs.getString("title"));
			result.setUserId(rs.getString("userid"));
			result.setContent(rs.getString("content"));
			result.setHit(rs.getInt("hit"));
			result.setwrite_Date(rs.getDate("write_date"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		System.out.println("=========================================");
		System.out.println("BoardDaoImpl selectBoardByBordno() 반환값 : " + result);
		System.out.println("=========================================");
		return result;
	}

	@Override
	public int updateHit(Connection conn, Board board) {

		String sql = "";
		sql += "UPDATE board";
		sql += " SET";
		sql += " hit = hit+1";
		sql += " WHERE = ?";
		
		int i = 0;
		
		try {
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, board.getBoardNo() );

			i = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		
		return i;
	}


}
