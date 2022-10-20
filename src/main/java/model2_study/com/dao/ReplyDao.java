package model2_study.com.dao;

import java.util.List;

import model2_study.com.dto.ReplyDto;

public interface ReplyDao extends Dao<ReplyDto, Integer>{
	// 특정 게시글의 리플
	List<ReplyDto> listFindByBoardNo(int page, int boardNo) throws Exception;
	// 로그인한 유저가 작성하는 리플
	List<ReplyDto> listFindByUserId(int page, String userId) throws Exception;
	
}
