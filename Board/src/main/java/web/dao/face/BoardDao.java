package web.dao.face;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import web.dto.Board;

public interface BoardDao {

	public List<Board> selectAll(Connection conn);

	public Board selectBoardByBordno(Connection conn,Board board);

	public int updateHit(Connection conn, Board board);

}
