package web.dto;

import java.util.Date;

public class BoardFile {

	private int fileno;
	private int boardno;
	private String originname;
	private String storedname;
	private long filesize;
	private Date write_date;

	public BoardFile() {
	}

	public BoardFile(int fileno, int boardno, String originname, String storedname, long filesize, Date write_date) {
		super();
		this.fileno = fileno;
		this.boardno = boardno;
		this.originname = originname;
		this.storedname = storedname;
		this.filesize = filesize;
		this.write_date = write_date;
	}

	@Override
	public String toString() {
		return "BoardFile [fileno=" + fileno + ", boardno=" + boardno + ", originname=" + originname + ", storedname="
				+ storedname + ", filesize=" + filesize + ", write_date=" + write_date + "]";
	}

	public int getFileno() {
		return fileno;
	}

	public void setFileno(int fileno) {
		this.fileno = fileno;
	}

	public int getBoardno() {
		return boardno;
	}

	public void setBoardno(int boardno) {
		this.boardno = boardno;
	}

	public String getOriginname() {
		return originname;
	}

	public void setOriginname(String originname) {
		this.originname = originname;
	}

	public String getStoredname() {
		return storedname;
	}

	public void setStoredname(String storedname) {
		this.storedname = storedname;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public Date getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}

}
