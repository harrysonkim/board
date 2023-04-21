package web.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import DBUtill.JDBCTemplate;
import utill.Paging;
import web.dao.face.BoardDao;
import web.dao.impl.BoardDaoImpl;
import web.dto.Board;
import web.dto.BoardFile;
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
		
		HttpSession session = request.getSession();
		Board board = new Board();
		board.setBoardNo( Integer.parseInt(request.getParameter("boardno")) );
		board.setUserId( (String) session.getAttribute("userid") );

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
	public Board updateView(Board board) {
		
		return boardDao.selectBoardByBordno(conn, board);
	}
	
	@Override
	public BoardFile viewFile(Board board) {
		
		return boardDao.selsectBoardFileByBordno(conn,board);
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

	@Override
	public Board getWriteParam(HttpServletRequest request) {
	
		Board board = new Board();

		HttpSession session = request.getSession();
		board.setTitle(request.getParameter("title"));
		board.setUserId( (String)session.getAttribute("userid") );
		board.setContent(request.getParameter("content"));

		return board;
	}

	@Override
	public void write(Board board) {
		
		int boardNo = boardDao.getNextBoardNo(conn);
		board.setBoardNo(boardNo);
		int complate = boardDao.insert(conn, board);
		
		if (complate > 0) {
			JDBCTemplate.commit(conn);
			System.out.println("작성한 글 저장 성공!");
		}else {
			JDBCTemplate.commit(conn);
			System.out.println("작성한 글 저장 실패!");
		}
	}

	@Override
	public boolean write(HttpServletRequest req) {
		
		System.out.println("BoardService write() 호출");
		// -----------------------------------------------------

		////////////////////////////////////////////////////
		// 1. 파일 업로드 형식의 인코딩이 맞는지 검사한다 //
		////////////////////////////////////////////////////

		// 요청 메시지의 content-type이 multipart/form-data가 맞는지 확인
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);

		if (!isMultipart) {
			// multipart 데이터가 아님
			System.out.println("BoardServiceImpl isMultipart 파일이 맞나? : " + isMultipart);
			// fileupload()메소드 중단시키기
			return false;
		}
		// ---------------------------------------------------------------------------

		///////////////////////////////////////////
		// 2.업로드된 데이터를 처리하는 방법을 설정한다
		///////////////////////////////////////////

		// FileItem
		// 클라이언트가 전송한 전달 파라미터들을 객체로 만든 것
		// 폼필드, 파일 데이터 전부를 객체로 저장할 수 있다
		// ** 폼필드 : 파일이 아닌 전달 파라미터 (input태그 데이터)

		// FileItemFactory
		// FileItem객체를 생성하는 방식을 설정해두는 클래스

		// DiskFileItemFactory
		// 하드 디스크(HDD) 기반으로 FileItem을 처리하는 팩토리 클래스
		// 업로드된 파일을 하드디스크에 임시 저장하여 처리하도록 설정한다
		// 파일의 용량이 작으면 메모리에서 처리
		// 파일의 용량이 크면 하드디스크에서 처리

		// 업로드데이터 처리 방법 설정 객체
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// ---------------------------------------------------------------------------

		///////////////////////////////////////////
		// 3. 업로드된 FileItem의 용량(크기)이 설정값보다 작으면 메모리에서 처리한다
		///////////////////////////////////////////
		int maxMem = 1 * 1024 * 1024; // 1MB == 1024*1024 == 1048576B
		factory.setSizeThreshold(maxMem);

		// ---------------------------------------------------------------------------

		///////////////////////////////////////////
		// 4. 메모리 처리 사이즈보다 크면 임시파일을 만들어서 HDD로 처리한다
		///////////////////////////////////////////

		// 임시파일을 저장할 폴더를 설정할 수 있다

		// 서블릿 컨텍스트 객체
		// 요청받은 정보를 처리하는 서블릿 컨텍스트 정보를 확인할 수 있다.
		ServletContext context = req.getServletContext();

		// 서버에 배포된 서블릿의 실제 서버 경로를 알아 온다
		String path = context.getRealPath("tmp");
		System.out.println("BoardService write(req) - tmp경로 : " + path);

		// 임시 파일을 저장할 폴더의 File객체
		File tmpRepository = new File(path);

		// mkdir() 메소드를 이용한 폴더 생성하기
		// => 폴더가 없으면 만들어주고, 있으면 생성하지 않는다 (에러 없음)
		tmpRepository.mkdir();

		// 임시 파일을 저장할 폴더를 팩토리 객체에 설정하기
		factory.setRepository(tmpRepository);

		// ---------------------------------------------------------------------------
		///////////////////////////////////////////
		// 5.파일 업로드를 수행하는 객체 설정하기
		///////////////////////////////////////////

		ServletFileUpload upload = new ServletFileUpload(factory);

		// 최대 업로드 허용 사이즈
		int maxFile = 10 * 1024 * 1024; // 10MB
		upload.setFileSizeMax(maxFile);

		// ---------------------------------------------------------------------------
		/////////////////////////////////////
		// ---- 파일 업로드 준비 끝
		/////////////////////////////////////
		// ---------------------------------------------------------------------------

		/////////////////////////////////////
		// 6. 파일 업로드 처리
		/////////////////////////////////////

		// 전달된 요청 파라미터 분석(추출)하기
		// 폼필드, 파일 전부 분석한다

		List<FileItem> items = null;

		try {

			// 요청객체에 담겨있는 전달 파라미터를 파싱한다
			// 폼필드를 추출한다
			// 파일도 업로드를 수행한다
			items = upload.parseRequest(req);

		} catch (FileUploadException e) {

			e.printStackTrace();
		}

		// TEST : 전달 파라미터의 추출상황 확인하기
//		 for (FileItem fileItem : items) {
//			System.out.println(fileItem);
//		}

		// ------------------------------------------------------

		//////////////////////////////////////////
		// 7. 파싱된 전달 파라미터 데이터 처리하기
		//////////////////////////////////////////

		// => List<FileItem> 객체에 파일과 폼필드 데이터가 들어있다

		// => 요청데이터(FileItem)종류

		// - 1. 빈 파일, 용량이 0인 파일
		// 파일의 용량이 0이어도 DB의 공간을 차지하면서 만들어지면,
		// 서버가 다운되기 때문에, 업로드 중단 처리(파일 무시)

		// - 2. 폼필드 (form-data, 일반적인 전달 파라미터)
		// 전달된 데이터들을 DB에 INSERT 한다

		// - 3. 파일
		// 파일은 디스크에 저장
		// 웹 서버의 로컬 폴더
		// 파일의 정보는 DB에 INSERT한다
		// (원본의 파일의 이름을 바꾸어 다른 사용자에게 배포하여
		// 누구에게 파일을 주었는지 확인이 가능하다)

		// - ** 처리 순서 1 -> 2 -> 3 (1에서 예외처리)

		// ++ 파일 업로드의 속사정..
		// 클라이언트가 서버에 파일을 올리면,
		// 서버는 임시파일을 만들고 이후,
		// 영구 저장을 하고,
		// DB에는 파일의 정보와 파일의 주인과 위치를 저장한다
		// (서버에 파일을 영구저장하는 방식은 서버의 물리적인 부담이
		// 증가하기 때문에 좋은 방식은 아니지만, 우리는 로컬서버에 저장하는 방식을 이용한다.)
		// (일반적으로 별도의 파일저장용 서버를 나누어 따로 두고 관리한다)

		int boardNo = boardDao.selectBoardNo(conn);
		
		// 폼필드 전달 파라미터를 저장할 DTO객체
		Board board = new Board();
		board.setBoardNo(boardNo);
		board.setUserId((String)req.getSession().getAttribute("userid"));

		// 파일 정보를 저장할 DTO 객체
		BoardFile boardFile = new BoardFile();
		boardFile.setBoardno(boardNo);

		// 파일 아이템 리스트의 반복자
		Iterator<FileItem> iter = items.iterator();

		while (iter.hasNext()) {

			// 전달 파라미터를 저장한 FileItem객체를 하나씩 꺼내서 적용하기
			FileItem item = iter.next();

			// ===== 1. 빈 파일에 대한 처리 =====
			if (item.getSize() <= 0) { // 전달 데이터의 크기가 0 이하

				// 빈 파일을 무시하고 다음 FileItem 처리로 넘어간다
				continue;

			}

			// ===== 2. 폼 필드에 대한 처리 =====

			if (item.isFormField()) {

				// 폼필드는 key = value 쌍으로 전달한다
				// key는 item.getFieldName(); 메소드로 얻어온다
				// value는 item.getString(); 메소드로 얻어온다

				// 키(key) 추출하기
				String key = item.getFieldName();
				// 값(value) 추출하기
				// * 초기화를 하지 않으면, 예외처리시 쓰레기값을 가지기 때문에 테스트출력(콘솔)쪽에서 에러가 난다
				String value = null;
				try {
					// 한글 깨짐을 인코딩하기 위한 파라미터 설정
					value = item.getString("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				System.out.println("BoardService write() - key : " + key);
				System.out.println("BoardService write() - value : " + value);

				// 전달 파라미터의 name(key)에 맞게 DTO의 setter를 호출한다
				if ("title".equals(key)) {
					board.setTitle(value);
				} else if ("content".equals(key)) {
					board.setContent(value);
				}

			} // if ( item.isFormField() ) 끝

			// ===== 3. 파일에 대한 처리 =====
			if (!item.isFormField()) {

				// 업로드된 파일은 서버 로컬 HDD에 저장한다
				// 파일의 이름을 원본과 다르게 바꿔서 저장한다

				// 서버는 원본 파일 이름, 바꾼 파일 이름 둘다 기억하고 있어야 한다
				// => DB에 원본이름, 저장한 이름 모드 INSERT한다

				// 날짜 => 문자열 변환 (java.util.Date -> String 변환)
				// SimpleDateFormat 클래스 이용
				// 년 월 일 시 분 초 밀리초 (yyyyMMddHHmmssS)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
				String rename = sdf.format(new Date()); // 현재시간을 파라미터로!

				System.out.println("BoardService write() - 원본파일명 : " + item.getName());
				System.out.println("BoardService write() - 저장파일명 : " + rename);

				// 임시 보관하고 있는 파일을 실제 업로드 저장소로 옮기기

				// 실제 파일 저장송
				File uploadFolder = new File(context.getRealPath("upload"));
				uploadFolder.mkdir();

				// 실제 저장할 파일 객체 (옮길 파일)
				File up = new File(uploadFolder, rename); // 파일 이름이 변경되어 저장됨

				try {
					// 임시파일을 실제 업로드 파일로 출력한다
					item.write(up);

					// 임시파일 제거
					item.delete();

				} catch (Exception e) {
					e.printStackTrace();
				}

				// 업로드된 파일의 정보를 DTO객체에 저장하기
				boardFile.setOriginname(item.getName());
				boardFile.setFilesize(item.getSize());
				
				boardFile.setStoredname(rename);

			} // if ( item.isFormField() ) 끝

		} // while ( iter.hasNext() ) 끝

		System.out.println("BoardService write() - board : " + board);
		System.out.println("BoardService write() - boardFile : " + boardFile);

		// ----------------------------------------------------------------
		/////////////////////////////////
		// 8. DB에 최종 데이터 삽입하기
		/////////////////////////////////

		Connection conn = JDBCTemplate.getConnection();

		int res = 0; // 결과값 저장 ( 원본파일, 바뀐파일 둘다 성공시 2가 담긴다)

		// 폼 필드 데이터 삽입
		res += boardDao.insert(conn, board);

		// 파일 데이터 삽입
		res += boardDao.insertFile(conn, boardFile);

		// 트랜잭션 관리
		if (res < 2) {
			// 두 insert중 하나라도 실패했을 경우
			JDBCTemplate.rollback(conn);
			System.out.println("BoardServiceImpl write(request) 파일 업로드 실패!! ");
		} else {
			// 두 insert 모두 성공했을 경우
			JDBCTemplate.commit(conn);
			System.out.println("BoardServiceImpl write(request) 파일 업로드 성공!! ");
		}
		
		// 파일 업로드 처리 완료
		return true;
	}

	@Override
	public void update(HttpServletRequest req, Board board) {

		BoardDao boardDao = new BoardDaoImpl();
		
		/////////////////////////////////////////////////////////////////////////////
		System.out.println("BoardService update() 호출");
		// -----------------------------------------------------

		////////////////////////////////////////////////////
		// 1. 파일 업로드 형식의 인코딩이 맞는지 검사한다 //
		////////////////////////////////////////////////////

		// 요청 메시지의 content-type이 multipart/form-data가 맞는지 확인
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);

		if (!isMultipart) {
			// multipart 데이터가 아님
			System.out.println("BoardServiceImpl isMultipart 파일이 맞나? : " + isMultipart);
			// fileupload()메소드 중단시키기
			return;
		}
		// ---------------------------------------------------------------------------

		///////////////////////////////////////////
		// 2.업로드된 데이터를 처리하는 방법을 설정한다
		///////////////////////////////////////////

		// FileItem
		// 클라이언트가 전송한 전달 파라미터들을 객체로 만든 것
		// 폼필드, 파일 데이터 전부를 객체로 저장할 수 있다
		// ** 폼필드 : 파일이 아닌 전달 파라미터 (input태그 데이터)

		// FileItemFactory
		// FileItem객체를 생성하는 방식을 설정해두는 클래스

		// DiskFileItemFactory
		// 하드 디스크(HDD) 기반으로 FileItem을 처리하는 팩토리 클래스
		// 업로드된 파일을 하드디스크에 임시 저장하여 처리하도록 설정한다
		// 파일의 용량이 작으면 메모리에서 처리
		// 파일의 용량이 크면 하드디스크에서 처리

		// 업로드데이터 처리 방법 설정 객체
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// ---------------------------------------------------------------------------

		///////////////////////////////////////////
		// 3. 업로드된 FileItem의 용량(크기)이 설정값보다 작으면 메모리에서 처리한다
		///////////////////////////////////////////
		int maxMem = 1 * 1024 * 1024; // 1MB == 1024*1024 == 1048576B
		factory.setSizeThreshold(maxMem);

		// ---------------------------------------------------------------------------

		///////////////////////////////////////////
		// 4. 메모리 처리 사이즈보다 크면 임시파일을 만들어서 HDD로 처리한다
		///////////////////////////////////////////

		// 임시파일을 저장할 폴더를 설정할 수 있다

		// 서블릿 컨텍스트 객체
		// 요청받은 정보를 처리하는 서블릿 컨텍스트 정보를 확인할 수 있다.
		ServletContext context = req.getServletContext();

		// 서버에 배포된 서블릿의 실제 서버 경로를 알아 온다
		String path = context.getRealPath("tmp");
		System.out.println("BoardService update() - tmp경로 : " + path);

		// 임시 파일을 저장할 폴더의 File객체
		File tmpRepository = new File(path);

		// mkdir() 메소드를 이용한 폴더 생성하기
		// => 폴더가 없으면 만들어주고, 있으면 생성하지 않는다 (에러 없음)
		tmpRepository.mkdir();

		// 임시 파일을 저장할 폴더를 팩토리 객체에 설정하기
		factory.setRepository(tmpRepository);

		// ---------------------------------------------------------------------------
		///////////////////////////////////////////
		// 5.파일 업로드를 수행하는 객체 설정하기
		///////////////////////////////////////////

		ServletFileUpload upload = new ServletFileUpload(factory);

		// 최대 업로드 허용 사이즈
		int maxFile = 10 * 1024 * 1024; // 10MB
		upload.setFileSizeMax(maxFile);

		// ---------------------------------------------------------------------------
		/////////////////////////////////////
		// ---- 파일 업로드 준비 끝
		/////////////////////////////////////
		// ---------------------------------------------------------------------------

		/////////////////////////////////////
		// 6. 파일 업로드 처리
		/////////////////////////////////////

		// 전달된 요청 파라미터 분석(추출)하기
		// 폼필드, 파일 전부 분석한다

		List<FileItem> items = null;

		try {

			// 요청객체에 담겨있는 전달 파라미터를 파싱한다
			// 폼필드를 추출한다
			// 파일도 업로드를 수행한다
			items = upload.parseRequest(req);

		} catch (FileUploadException e) {

			e.printStackTrace();
		}

		// TEST : 전달 파라미터의 추출상황 확인하기
//		 for (FileItem fileItem : items) {
//			System.out.println(fileItem);
//		}

		// ------------------------------------------------------

		//////////////////////////////////////////
		// 7. 파싱된 전달 파라미터 데이터 처리하기
		//////////////////////////////////////////

		// => List<FileItem> 객체에 파일과 폼필드 데이터가 들어있다

		// => 요청데이터(FileItem)종류

		// - 1. 빈 파일, 용량이 0인 파일
		// 파일의 용량이 0이어도 DB의 공간을 차지하면서 만들어지면,
		// 서버가 다운되기 때문에, 업로드 중단 처리(파일 무시)

		// - 2. 폼필드 (form-data, 일반적인 전달 파라미터)
		// 전달된 데이터들을 DB에 INSERT 한다

		// - 3. 파일
		// 파일은 디스크에 저장
		// 웹 서버의 로컬 폴더
		// 파일의 정보는 DB에 INSERT한다
		// (원본의 파일의 이름을 바꾸어 다른 사용자에게 배포하여
		// 누구에게 파일을 주었는지 확인이 가능하다)

		// - ** 처리 순서 1 -> 2 -> 3 (1에서 예외처리)

		// ++ 파일 업로드의 속사정..
		// 클라이언트가 서버에 파일을 올리면,
		// 서버는 임시파일을 만들고 이후,
		// 영구 저장을 하고,
		// DB에는 파일의 정보와 파일의 주인과 위치를 저장한다
		// (서버에 파일을 영구저장하는 방식은 서버의 물리적인 부담이
		// 증가하기 때문에 좋은 방식은 아니지만, 우리는 로컬서버에 저장하는 방식을 이용한다.)
		// (일반적으로 별도의 파일저장용 서버를 나누어 따로 두고 관리한다)

		// 폼필드 전달 파라미터를 저장할 DTO객체
		Board updateBoard = new Board();
		updateBoard.setBoardNo(Integer.parseInt(req.getParameter("boardno")));
		updateBoard.setUserId((String)req.getSession().getAttribute("userid"));

		// 파일 정보를 저장할 DTO 객체
		BoardFile updateBoardFile = new BoardFile();
		updateBoardFile.setBoardno(Integer.parseInt(req.getParameter("boardno")));

		// 파일 아이템 리스트의 반복자
		Iterator<FileItem> iter = items.iterator();

		while (iter.hasNext()) {

			// 전달 파라미터를 저장한 FileItem객체를 하나씩 꺼내서 적용하기
			FileItem item = iter.next();

			// ===== 1. 빈 파일에 대한 처리 =====
			if (item.getSize() <= 0) { // 전달 데이터의 크기가 0 이하

				// 빈 파일을 무시하고 다음 FileItem 처리로 넘어간다
				continue;

			}

			// ===== 2. 폼 필드에 대한 처리 =====

			if (item.isFormField()) {

				// 폼필드는 key = value 쌍으로 전달한다
				// key는 item.getFieldName(); 메소드로 얻어온다
				// value는 item.getString(); 메소드로 얻어온다

				// 키(key) 추출하기
				String key = item.getFieldName();
				// 값(value) 추출하기
				// * 초기화를 하지 않으면, 예외처리시 쓰레기값을 가지기 때문에 테스트출력(콘솔)쪽에서 에러가 난다
				String value = null;
				try {
					// 한글 깨짐을 인코딩하기 위한 파라미터 설정
					value = item.getString("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				System.out.println("BoardService update() - key : " + key);
				System.out.println("BoardService update() - value : " + value);

				// 전달 파라미터의 name(key)에 맞게 DTO의 setter를 호출한다
				if ("title".equals(key)) {
					updateBoard.setTitle(value);
				} else if ("content".equals(key)) {
					updateBoard.setContent(value);
				}

			} // if ( item.isFormField() ) 끝

			// ===== 3. 파일에 대한 처리 =====
			if (!item.isFormField()) {

				// 업로드된 파일은 서버 로컬 HDD에 저장한다
				// 파일의 이름을 원본과 다르게 바꿔서 저장한다

				// 서버는 원본 파일 이름, 바꾼 파일 이름 둘다 기억하고 있어야 한다
				// => DB에 원본이름, 저장한 이름 모드 INSERT한다

				// 날짜 => 문자열 변환 (java.util.Date -> String 변환)
				// SimpleDateFormat 클래스 이용
				// 년 월 일 시 분 초 밀리초 (yyyyMMddHHmmssS)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
				String rename = sdf.format(new Date()); // 현재시간을 파라미터로!

				System.out.println("BoardService write() - 원본파일명 : " + item.getName());
				System.out.println("BoardService write() - 저장파일명 : " + rename);

				// 임시 보관하고 있는 파일을 실제 업로드 저장소로 옮기기

				// 실제 파일 저장송
				File uploadFolder = new File(context.getRealPath("upload"));
				uploadFolder.mkdir();

				// 실제 저장할 파일 객체 (옮길 파일)
				File up = new File(uploadFolder, rename); // 파일 이름이 변경되어 저장됨

				try {
					// 임시파일을 실제 업로드 파일로 출력한다
					item.write(up);

					// 임시파일 제거
					item.delete();

				} catch (Exception e) {
					e.printStackTrace();
				}

				// 업로드된 파일의 정보를 DTO객체에 저장하기
				updateBoardFile.setOriginname(item.getName());
				updateBoardFile.setFilesize(item.getSize());
				
				updateBoardFile.setStoredname(rename);

			} // if ( item.isFormField() ) 끝

		} // while ( iter.hasNext() ) 끝

		System.out.println("BoardService write() - updateBoard : " + updateBoard);
		System.out.println("BoardService write() - updateBoardFile : " + updateBoardFile);

		// ----------------------------------------------------------------
		/////////////////////////////////
		// 8. DB에 최종 데이터 삽입하기
		/////////////////////////////////

		Connection conn = JDBCTemplate.getConnection();

		int res = 0; // 결과값 저장 ( 원본파일, 바뀐파일 둘다 성공시 2가 담긴다)

		// 폼 필드 데이터 삽입
		res += boardDao.update(conn, updateBoard);
		
		// 파일 데이터 삭제
		res += boardDao.deleteFile(conn, updateBoardFile);
		
		// 파일 데이터 삽입
		res += boardDao.insertFile(conn, updateBoardFile);

		// 트랜잭션 관리
		if (res < 3) {
			// 두 update중 하나라도 실패했을 경우
			JDBCTemplate.rollback(conn);
			System.out.println("BoardServiceImpl update() 파일 수정 실패!! ");
		} else {
			// 두 update 모두 성공했을 경우
			JDBCTemplate.commit(conn);
			System.out.println("BoardServiceImpl update() 파일 수정 성공!! ");
		}
		
	}

}
