package model2_study.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model2_study.com.dto.UserDto;

public class UserDaoImp implements UserDao{
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String listsql = "";
	private String detailsql = "";
	private String updatesql = "";
	private String insertsql = "";
	private String deletesql = "";
	private String loginsql = "SELECT * FROM USER WHERE user_id=? AND pw=?";

	@Override
	public java.util.List<UserDto> List(int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto detail(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto login(String userId, String pw) throws ClassNotFoundException, SQLException {
		UserDto user=null;
		conn=SpringBoardDB.getConn();
		pstmt=conn.prepareStatement(loginsql);
		pstmt.setString(1, userId);
		pstmt.setString(2, pw);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			user = new UserDto();
			user.setUserId(rs.getString("user_id"));
			user.setPw(rs.getString("pw"));
			user.setEmail(rs.getString("email"));
			user.setPhone(rs.getString("phone"));
			user.setName(rs.getString("name"));
			user.setSignup(rs.getDate("signup"));
			user.setBirth(rs.getDate("birth"));

		}
		return user;
	}

	@Override
	public int update(UserDto user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(UserDto user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void close() {
		try {
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
