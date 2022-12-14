package model2_study.com.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model2_study.com.dao.BoardDao;
import model2_study.com.dao.BoardDaoImp;
import model2_study.com.dao.BoardImgDao;
import model2_study.com.dao.BoardImgDaoImp;
import model2_study.com.dto.BoardDto;
import model2_study.com.dto.BoardImgDto;

@WebServlet("/boardFileUp.do")
public class BoardInsertFileUpServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String imgPath = req.getServletContext().getRealPath("./public/img");
		List<File> newFileList = new ArrayList<File>();  // 저장한 파일들
		int insert = 0;
		int imgInsert = 0;
		int boardNo = 0;  // 등록된 게시글 번호
		String redirectPage = "./boardDetail.do?boardNo=";
		String errorPage = "./boardInsert.do";
		BoardDao boardDao = null;
		BoardImgDao boardImgDao = null;
		
		
		
		// 톰캣서버 기본설정이 웹앱을 배포하는 것이기 때문에 웹앱을 배포하지 않도록 설정해야 저장되는 이미지를 볼 수 있다.
		// Server modules without publishing 을 설정해야 함
		System.out.println(imgPath);
		int maxSize = 1024*1024*1;  // 1mb 이상 저장할 수 없음 (IOException)
		DefaultFileRenamePolicy dfp = new DefaultFileRenamePolicy();  // 같은이름의 파일이 있으면 숫자
		try {
			MultipartRequest multiReq = new MultipartRequest(req, imgPath, maxSize, "UTF-8", dfp);
			String title = multiReq.getParameter("title");
			String user_id = multiReq.getParameter("user_id");
			String contents = multiReq.getParameter("contents");
			List<String> img_paths = new ArrayList<String>(); // db에 저장할 이미지 경로
			
			Iterator fileNames = (Iterator) multiReq.getFileNames();
			while(fileNames.hasNext()) {
				String key = fileNames.next().toString();
				File imgFile = multiReq.getFile(key);  // 저장된 파일
				if(imgFile!=null && imgFile.exists()) {
					String[] imgTypes = multiReq.getContentType(key).split("/");  // image/png or image/jpeg
					String imgType=imgTypes[0];
					String imgExt = imgTypes[1];
					System.out.println(imgType);
					if(!imgType.equals("image") && (imgExt.equals("jpeg") || imgExt.equals("png"))) {  // 이미지가 아닌것을 삭제
						boolean delete = imgFile.delete();
						System.out.println("이미지가 아닌 파일 삭제: "+ delete);
					}else {  // 저장된 파일의 이름 규칙을 정하는 것 table_시간_랜덤수
						Date date = new Date();
						System.out.println(date.getTime());
						System.out.println(System.currentTimeMillis());
						String random = (int)(Math.random()*10000)+"";
						String fileName = "board_"+System.currentTimeMillis()+"_"+random+"."+imgExt;
						System.out.println(fileName);
						File newFile = new File(imgPath+"/"+fileName);
						File newTHumbFile = new File(imgPath+"/thumb/"+fileName);
						imgFile.renameTo(newFile);  //
						// 썸네일 이미지 생성
						BufferedImage originImg = ImageIO.read(newFile);  
						// 원본이미지 불러오기
						BufferedImage thumbImg = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
						// 100*100 크기의 빈 이미지 버퍼 준비
						thumbImg.getGraphics().drawImage(originImg, 0, 0, 100, 100, null);
						// 큰이미지를 작은 이미지로 변환
						ImageIO.write(thumbImg, imgExt, newTHumbFile);
						// 버퍼에 저장된 이미지를 파일로 변환
						img_paths.add(fileName);  // null이 아닌것은 파일 저장이 완료되었다는 것
						newFileList.add(newFile);
						newFileList.add(newTHumbFile);
					}
				}
			}
			//MultipartRequest : 파일 blob은 저장하고 문자열 blob은 파라미터 처리를 해준다.
			boardDao = new BoardDaoImp();
			BoardDto board = new BoardDto();
			board.setTitle(title);
			board.setContents(contents);
			board.setUser_id(user_id);
			insert = boardDao.insert(board);
			boardNo = boardDao.lastInsertId();
			System.out.println("게시글 등록 성공 : "+insert);
			for(String img_path : img_paths) {
				boardImgDao  = new BoardImgDaoImp();
				BoardImgDto boardImg = new BoardImgDto();
				boardImg.setImg_path(img_path);
				boardImg.setBoard_no(boardNo);
				imgInsert += boardImgDao.insert(boardImg);
				
			}
			System.out.println("이미지 등록 성공: "+imgInsert);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(insert>0) {
			resp.sendRedirect(redirectPage + boardNo);
		}else {
			for(File newFile : newFileList) {
				System.out.println("등록된 이미지 삭제: "+newFile.delete());
			}
			resp.sendRedirect(errorPage);
		}
	}
}
