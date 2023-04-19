package web.dao.face;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import utill.Paging;
import web.dto.Board;

public interface BoardDao {

	public List<Board> selectAll(Connection conn);

	public Board selectBoardByBordno(Connection conn,Board board);

	public int updateHit(Connection conn, Board board);
	/**
	 * 총 게시글 수 조회하기
	 * 
	 * @param conn - DB연결객체
	 * @return 테이블의 전체 행 수
	 */
	public int selectCntAll(Connection conn);

	/**
	 * 테이블의 페이징 리스트 조회하기
	 * 
	 * @param conn - DB연결 객체
	 * @param paging - 페이징 정보 객체
	 * @return 조회된 페이징 리스트
	 */
	public List<Board> selectAll(Connection conn, Paging paging);

}
