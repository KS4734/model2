package model2_study.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model2_study.com.dto.BoardDto;
import model2_study.com.dto.ReplyDto;
import model2_study.com.dto.UserDto;

public class BoardDaoImp implements BoardDao{
	// db 접속해서 dql or dml 을 실행하는 객체
	private Connection conn;
	private PreparedStatement pstmt;
	private  ResultSet rs;
	private String insertSql="INSERT INTO BOARD (user_id,title,contents) VALUES (?,?,?)";
	private String deleteSql="DELETE FROM BOARD WHERE board_no=?";
	private String updateSql="UPDATE BOARD SET title=?,contents=? WHERE board_No=?";
	private String viewUpdateSql="";
//	private String detailSql="SELECT b.*,u.*,r.reply_no,r.img_path, "
//			+ " r.title r_title, "
//			+ " r.contents r_contents,"
//			+ " r.post_time r_post_time,"
//			+ " r.user_id r_user_id"
//			+ " FROM BOARD b"
//			+ " INNER JOIN USER u USING(user_id) "
//			+ " LEFT JOIN REPLY r USING (board_no) "
//			+ " WHERE board_no=?";
	private String detailSql="SELECT *, board_no no, "
			+ " (SELECT COUNT(*)FROM BOARD_PREFER WHERE board_no=no AND prefer=1) likes,"
			+ " (SELECT COUNT(*)FROM BOARD_PREFER WHERE board_no=no AND prefer=0) bads"
			+ " FROM BOARD INNER JOIN USER USING(user_id) WHERE board_no=?";
	private String listSql="SELECT *, board_no no,"
			+ " (SELECT COUNT(*) FROM REPLY WHERE board_no=no) reply_count,"
			+ " (SELECT COUNT(*) FROM BOARD_PREFER WHERE board_no=no AND prefer=1) likes,"
			+ " (SELECT COUNT(*) FROM BOARD_PREFER WHERE board_no=no AND prefer=0) bads,"
			+ " (SELECT img_path FROM BOARD_IMG WHERE board_no=no LIMIT 0,1) thumb_path"
			+ " FROM BOARD ORDER BY board_no DESC LIMIT ?,?";
	private String countSql = "SELECT COUNT(*) cnt FROM BOARD";
	private final int ROWS=7;  // list출력시 한페이지에 보이는 row의 수
	// 생성자를 호출할 때 db 를접속해서 쿼리를 실행할 때 접속한 객체를 사용 ??(싱글톤패턴과 유사)
	private String lastInsertId="SELECT LAST_INSERT_ID() id";
	public BoardDaoImp() throws ClassNotFoundException, SQLException {
		conn=SpringBoardDB.getConn();
		System.out.println(conn);
	}
	public BoardDaoImp(Connection conn) throws Exception {
		this.conn=conn;
		System.out.println(this.conn);
	}
	
	@Override
	public int insert(BoardDto board) throws ClassNotFoundException, SQLException {
		int insert=0;
		pstmt=conn.prepareStatement(insertSql);
		pstmt.setString(1, board.getUser_id());
		pstmt.setString(2, board.getTitle());
		pstmt.setString(3, board.getContents());
		insert=pstmt.executeUpdate();
		return insert;
	}

	@Override
	public int delete(int boardno) throws ClassNotFoundException, SQLException {
		int delete = 0;
		pstmt=conn.prepareStatement(deleteSql);
		pstmt.setInt(1, boardno);
		delete=pstmt.executeUpdate();
		return delete;
	}

	@Override
	public int update(BoardDto board) throws ClassNotFoundException, SQLException {
		int update=0;
		pstmt=conn.prepareStatement(updateSql);
		pstmt.setString(1, board.getTitle());
		pstmt.setString(2, board.getContents());
		pstmt.setInt(3, board.getBoard_no());
		update=pstmt.executeUpdate();
		
		return update;
	}

	@Override
	public int viewUpdate(int boardno) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public BoardDto detail(int boardNo) throws ClassNotFoundException, SQLException {
		BoardDto board = null;
		pstmt = conn.prepareStatement(detailSql);
		pstmt.setInt(1, boardNo);
		rs = pstmt.executeQuery();
		int count=0;
		if(rs.next()) {
				board = new BoardDto();
				board.setBoard_no(rs.getInt("board_no"));
				board.setViews(rs.getInt("views"));
				board.setTitle(rs.getString("title"));
				board.setContents(rs.getString("contents"));
				board.setUser_id(rs.getString("user_id"));
				board.setPost_time(rs.getDate("post_time"));
				board.setLikes(rs.getInt("likes"));
				board.setBads(rs.getInt("bads"));
				UserDto user=new UserDto();
				user.setUserId(rs.getString("user_id"));
				user.setPw(rs.getString("pw"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setName(rs.getString("name"));
				user.setSignup(rs.getDate("signup"));
				user.setBirth(rs.getDate("birth"));
				board.setUser(user);
		}
		System.out.println(board);
		return board;
	}

	@Override
	public List<BoardDto> list(int page) throws ClassNotFoundException, SQLException {
		List<BoardDto> boardList=null;
		//conn=SpringBoardDB.getConn();  // 생성할 때 (초기화) conn을 생성했기 대문에 생략
		pstmt=conn.prepareStatement(listSql);
		// list ?,7;
		int startrow=(page-1)*ROWS;  // 1 => 0 , 2 => 7, 3 => 14 ...
		pstmt.setInt(1, startrow);
		pstmt.setInt(2, ROWS);
		rs=pstmt.executeQuery();
		boardList=new ArrayList<BoardDto>();
		while(rs.next()) {
			BoardDto board=new BoardDto();
			board.setBoard_no(rs.getInt("board_no"));
			board.setViews(rs.getInt("views"));
			board.setTitle(rs.getString("title"));
			board.setContents(rs.getString("contents"));
			board.setUser_id(rs.getString("User_id"));
			board.setPost_time(rs.getDate("post_time"));
			board.setReplyCount(rs.getInt("reply_count"));
			board.setThumbPath(rs.getString("thumb_path"));
			board.setLikes(rs.getInt("likes"));
			board.setBads(rs.getInt("bads"));
			boardList.add(board);
		}
		return boardList;
	}

	@Override
	public int count() throws ClassNotFoundException, SQLException {
		int count = 0;
		pstmt=conn.prepareStatement(countSql);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			count=rs.getInt("cnt");
		}
		return count;
	}
	
	@Override
	public int lastInsertId() throws Exception {
		int id = 0;
		pstmt=conn.prepareStatement(lastInsertId);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			id=rs.getInt("id");
		}
		return id;
	}
	
	@Override
	public void close() {
		try {
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		BoardDao boardDao=null;
		try {
			boardDao=new BoardDaoImp();
			System.out.println(boardDao.count());
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			boardDao.close();
		}
	}
	

}
