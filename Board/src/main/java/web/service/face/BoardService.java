package web.service.face;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import web.dto.Board;

public interface BoardService {

	public List<Board> getList();
	
	public Board getBoardno(HttpServletRequest request);
	
	public Board view(Board board);
}
