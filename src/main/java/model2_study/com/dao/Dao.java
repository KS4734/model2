package model2_study.com.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao <T,P>{
	List<T> list(int page) throws ClassNotFoundException, SQLException;
	T detail(P pk) throws ClassNotFoundException, SQLException;
	int update(T dto) throws ClassNotFoundException, SQLException;
	int insert(T dto) throws ClassNotFoundException, SQLException;
	int delete(P pk) throws ClassNotFoundException, SQLException;
	void close();
}