package web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBUtill.JDBCTemplate;
import utill.Paging;
import web.dao.face.BoardDao;
import web.dto.Board;
import web.dto.BoardFile;

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
	public BoardFile selsectBoardFileByBordno(Connection conn, Board board) {

		String sql = "";
		sql += "SELECT * FROM boardfile";
		sql += " WHERE boardno = ?";

		BoardFile result = new BoardFile();
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, board.getBoardNo());
			rs = ps.executeQuery();
			
			rs.next();
			
			result.setFileno(rs.getInt("fileno"));
			result.setBoardno(rs.getInt("boardno"));
			result.setOriginname(rs.getString("originname"));
			result.setStoredname(rs.getString("storedname"));
			result.setFilesize(rs.getLong("filesize"));
			result.setWrite_date(rs.getDate("write_date"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		System.out.println("=========================================");
		System.out.println("BoardDaoImpl selectBoardFileByBordno() 반환값 : " + result);
		System.out.println("=========================================");
		
		return result;
	}

	@Override
	public int updateHit(Connection conn, Board board) {

		String sql = "";
		sql += "UPDATE board";
		sql += " SET";
		sql += " hit = hit+1";
		sql += " WHERE boardno = ?";
		
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
		
		System.out.println("BoardDaoImpl updateHit() 반환값 : " + i);
		return i;
	}

	@Override
	public int selectCntAll(Connection conn) {

		String sql = "SELECT count(*) FROM board";
		
		int count = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		
			while(rs.next()) {
				count = rs.getInt("count(*)");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		System.out.println("BoardDaoImpl selectCntAll() 반환값 : " + count);
		
		return count;
	}

	@Override
	public List<Board> selectAll(Connection conn, Paging paging) {
	
		String sql = "";
		sql += "SELECT * FROM ("
				+ " SELECT rownum rnum, B.* FROM ("
				+ "  SELECT"
				+ "   boardno, title, userid, hit, write_date"
				+ "  FROM board"
				+ "  ORDER BY boardno DESC"
				+ " )B"
				+ " ) BOARD"
				+ " WHERE rnum BETWEEN ? AND ?";
		
		List<Board> list = new ArrayList<>();
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, paging.getStartNo());
			ps.setInt(2, paging.getEndNo());
			rs = ps.executeQuery();
		
			while(rs.next()) {
				
				Board b = new Board();
				b.setBoardNo(rs.getInt("boardno"));
				b.setTitle(rs.getString("title"));
				b.setUserId(rs.getString("userid"));
//				b.setContent(rs.getString("content"));
				b.setHit(rs.getInt("hit"));
				b.setwrite_Date(rs.getDate("write_date"));
				
				list.add(b);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		System.out.println("BoardDaoImpl selectCntAll()" + list);
		return list;
	}

	@Override
	public int getNextBoardNo(Connection conn) {
		
		String sql = "";
		sql += "SELECT board_seq.NEXTVAL FROM dual";
		
		int boardNo = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			rs.next();
			
			boardNo = rs.getInt(1);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		System.out.println("BoardDaoImpl getNextBoardNo() 반환값 : " + boardNo);
		return boardNo;
	}
	
	
		@Override
		public int insert(Connection conn, Board board) {
			
			String sql = "";
			sql += "INSERT INTO board ( boardno, title, userid, content, hit )";
			sql += " VALUES (?,?,?,?,0)";
			
			int res = 0;
			
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, board.getBoardNo());
				ps.setString(2, board.getTitle());
				ps.setString(3, board.getUserId());
				ps.setString(4, board.getContent());
				
				res = ps.executeUpdate();
			
			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(ps);
			}
			
			System.out.println("BoardDaoImpl insert() 반환값 : " + res );
			return res;
		}

		@Override
		public int selectBoardNo(Connection conn) {

			String sql = "";
			sql += "SELECT board_seq.NEXTVAL FROM dual";
			
			int res = 0;
			
			try {
				ps=conn.prepareStatement(sql);
				rs=ps.executeQuery();
				
				rs.next();
				
				res = rs.getInt(1);
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println("BoardDaoImpl selectBoardNo() 반환값 : " + res);
			return res;
		}
		
		@Override
		public int insertFile(Connection conn, BoardFile boardFile) {
			
			PreparedStatement ps = null;
			
			String sql = "";
			sql += "INSERT INTO boardfile ( fileno, boardno, originname, storedname, filesize)";
			sql += " VALUES( boardfile_seq.nextval, ?, ?, ?, ?)";

			int res = 0; 

				try {
					ps = conn.prepareStatement(sql);
					
					ps.setInt(1, boardFile.getBoardno());
					ps.setString(2, boardFile.getOriginname());
					ps.setString(3, boardFile.getStoredname());
					ps.setLong(4, boardFile.getFilesize());
					
					res = ps.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					JDBCTemplate.close(ps);
				}
			
			System.out.println("BoardDaoImpl insertFile() 반환값 : " + res);
			return res;
		}

		@Override
		public Board selectByBoardNo(Connection conn, Board board) {

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
			}
			
			System.out.println("BoardDaoImpl selectByBoardNo() 반환값 : " + result);
			return result;
		}

		@Override
		public int update(Connection conn, Board board) {

			String sql = "";
			sql += "UPDATE board";
			sql += " SET";
			sql += " title = ?,";
			sql += " content = ?";
			sql += " WHERE boardno = ?";
			
			int res = 0;
			
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, board.getTitle());
				ps.setString(2, board.getContent());
				ps.setInt(3, board.getBoardNo());
				
				res = ps.executeUpdate();
			
			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(ps);
			}
			
			System.out.println("BoardDaoImpl insert() 반환값 : " + res );
			return res;
			
		}
	
	@Override
	public int deleteFile(Connection conn, BoardFile boardFile) {

		PreparedStatement ps = null;
		
		String sql = "";
		sql += "DELETE FROM boardfile";
		sql += " WHERE boardno = ?";
		    	
		int res = 0; 

			try {
				ps = conn.prepareStatement(sql);
				
				ps.setInt(1, boardFile.getBoardno());
				
				res = ps.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(ps);
			}
		
		System.out.println("BoardDaoImpl deleteFile() 반환값 : " + res);
		return res;
		
	}

}
