package web.service.impl;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import DBUtill.JDBCTemplate;
import web.dao.face.BoardDao;
import web.dao.impl.BoardDaoImpl;
import web.dto.Board;
import web.service.face.BoardService;

public class BoardServiceImpl implements BoardService{

	private BoardDao boardDao = new BoardDaoImpl();
	private Connection conn = JDBCTemplate.getConnection();
	@Override
	public List<Board> getList() {

		
		List<Board> list = boardDao.selectAll(conn);
		
		
		return list;
	}

	@Override
	public Board getBoardno(HttpServletRequest request) {

		Board board = new Board();
		board.setBoardNo( Integer.parseInt(request.getParameter("boardno")) );
		
		return board;
	}

	@Override
	public Board view(Board board) {
		
		Board result = boardDao.selectBoardByBordno(conn, board);
		int i = boardDao.updateHit(conn,board);
		
		if(i > 0) {
			JDBCTemplate.commit(conn);
			System.out.println("조회수 +1");
		} else {
			JDBCTemplate.rollback(conn);
			System.out.println("조회수 변동없음");
		}
		
		return result;
	}

}
