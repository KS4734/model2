package model2_study.com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model2_study.com.dao.BoardDao;
import model2_study.com.dao.BoardDaoImp;
import model2_study.com.dto.BoardDto;
import model2_study.com.dto.PagingDto;

@WebServlet("/boardList.do")
public class BoardListServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String page_str=req.getParameter("page");
		// page의 기본값 = 1
		int page = 1;
		if(page_str!=null) {
			page = Integer.parseInt(page_str);
		}
		int count = 0;
		PagingDto paging = null;
		BoardDao boardDao = null;
		List<BoardDto> boardList = null;
		try {
			boardDao = new BoardDaoImp();	
			boardList=boardDao.list(page);
			count=boardDao.count();
			paging = new PagingDto(page, count, 7);
			System.out.println(paging);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if(boardDao!=null)boardDao.close();
		}
		// 오류 : index 페이지로 이동 (redirect)
		// 문제없이 실행되었으면 boardList.jsp(View)에 출력 (forward)
		System.out.println(boardList);
		if(boardList==null) {
			resp.sendRedirect("./");
		}else {
			req.setAttribute("paging", paging);
			req.setAttribute("boardList", boardList);
			req.getRequestDispatcher("./boardList.jsp").forward(req, resp);
		}
		
	}
}
