package model2_study.com.dao;
//import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SpringBoardDB {
	// 싱글턴패턴 (static 필드와 유사): 객체 지향 언어는 사용할 때 마다 객체를 생성하는 방식으로 메모리의 부담을 줄이기 위해 객체를 한번만 생성하고 호출하는 방식
//	private static SpringBoardDB db = new SpringBoardDB();
//	public static SpringBoardDB get() {
//		if(db!=null) {
//			return db;
//		}else {
//			db=new SpringBoardDB();
//			return db;
//		}
//	}
	
	
	private static String url="jdbc:mysql://localhost:3306/spring_board";
	private static String user="board_user";
	private static String pw="1234";
	private static String driver="com.mysql.cj.jdbc.Driver";
	private static Connection conn;
	public static Connection getConn() throws ClassNotFoundException, SQLException{
		if(conn==null || conn.isClosed()) {
			Class.forName(driver);
			conn=DriverManager.getConnection(url,user,pw);
		}
		return conn;
	}
	
//	public static void main(String[] args) {
//		try {
//			System.out.println(SpringBoardDB.getConn());
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
}
