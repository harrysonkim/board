package web.service.impl;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import DBUtill.JDBCTemplate;
import utill.Paging;
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

	@Override
	public Paging getPaging(HttpServletRequest req) {

		// 전달 파라미터 curPage추출
		String param = req.getParameter("curPage");
		int curPage = 0;
		if (param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
			
			
		}else {
			System.out.println("[WARN] BoardService - getPaging() : curPage값이 null이거나 비었어요");
		}
		
		// 총 게시글 수 조회하기
		int totalCount = boardDao.selectCntAll( conn );
		System.out.println("asdasdsa"+ totalCount);
		// 페이징 객체
//		Paging paging = new Paging(totalCount, curPage, 30, 5); // listCount:30, pageCount:5
		Paging paging = new Paging(totalCount, curPage);
		
		
		return paging;
	}
	
	@Override
	public List<Board> getList(Paging paging) {
		return boardDao.selectAll(conn, paging);
	}

}
