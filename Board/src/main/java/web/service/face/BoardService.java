package web.service.face;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import utill.Paging;
import web.dto.Board;
import web.dto.BoardFile;

public interface BoardService {

	public List<Board> getList();
	
	/**
	 * 전달파라미터를 BoardDTO로 저장하여 반환
	 * 
	 * @param request - 요청 정보 객체
	 * @return 전달파라미터 boardno를 저장한 DTO 객체
	 */
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

	public void write(Board board);

	public boolean write(HttpServletRequest request);

	Board getWriteParam(HttpServletRequest request);

	public void update(HttpServletRequest request, Board board);

	public BoardFile viewFile(Board board);

	public Board updateView(Board board);

}
