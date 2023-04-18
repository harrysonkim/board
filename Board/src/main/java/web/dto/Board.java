package web.dto;

import java.util.Date;

public class Board {

	private int boardNo;
	private String title;
	private String userId;
	private String content;
	private int hit;
	private Date write_Date;

	public Board() {
	}

	public Board(int boardNo, String title, String userId, String content, int hit, Date write_Date) {
		super();
		this.boardNo = boardNo;
		this.title = title;
		this.userId = userId;
		this.content = content;
		this.hit = hit;
		this.write_Date = write_Date;
	}

	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", title=" + title + ", userId=" + userId + ", content=" + content
				+ ", hit=" + hit + ", write_Date=" + write_Date + "]";
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public Date getwrite_Date() {
		return write_Date;
	}

	public void setwrite_Date(Date write_Date) {
		this.write_Date = write_Date;
	}

}
