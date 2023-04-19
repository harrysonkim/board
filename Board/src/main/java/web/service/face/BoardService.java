package web.service.face;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import utill.Paging;
import web.dto.Board;

public interface BoardService {

	public List<Board> getList();
	
	public Board getBoardno(HttpServletRequest request);
	
	public Board view(Board board);

	
	/**
	 * 페이징 객체 생성
	 * 
	 * @param request - 요청 정보 객체
	 * @return 페이징 계산이 완료된 Paging객체
	 */
	public Paging getPaging(HttpServletRequest request);

	public List<Board> getList(Paging paging);

}
