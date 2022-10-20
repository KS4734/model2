package model2_study.com.dao;

import java.sql.SQLException;
import java.util.List;

import model2_study.com.dto.BoardDto;

// 게시글 CRUD interface
public interface BoardDao {
	//dml
	int insert(BoardDto board) throws ClassNotFoundException,SQLException;
	int delete(int boardno) throws ClassNotFoundException,SQLException;
	int update(BoardDto board) throws ClassNotFoundException,SQLException;
	int viewUpdate(int boardno) throws ClassNotFoundException,SQLException;
	// dql
	BoardDto detail(int boardNo) throws ClassNotFoundException,SQLException;
	List<BoardDto> list(int page) throws ClassNotFoundException,SQLException;
	int count() throws ClassNotFoundException,SQLException;
	int lastInsertId() throws Exception;
	void close();
}