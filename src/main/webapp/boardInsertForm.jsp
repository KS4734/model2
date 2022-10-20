<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 등록</title>
</head>
<body>
	<header>
		<%@include file="./template/headerNav.jsp" %>
	</header>
	<main style="display: flex; justify-content: center;">
		<div>
			<h1>게시글 등록 폼</h1>
			<p>(성공하면 boardList.do, 실패하면 boardInsertForm.jsp)</p>
			<p>default form : 파라미터 를 Text로 보낸다.</p>
			<p>multipart/form-data : 파라미터를 Blob으로 보낸다. (파일을 전송가능)</p>
			<p>톰캣의 request.getParameter() 는 text 파라미터만 처리할 수 있었다. 
				(톰캣에서 Blob 데이터처리 메서드를 제공하지 않아서 cos jar를 이용)</p>
			<form enctype="multipart/form-data" 
			action="./boardFileUp.do" method="post" name="boardInsertForm">
				<p>
					<label>
						제목 : 
						<input type="text" name="title" value="글 제목입니다.">
					</label>
				</p>
				<p>
					<label>
						글쓴이 : 
						<%if(loginUser!=null){ // headreNav.jsp 에 추가된 변수 %>
						<input type="text" name="user_id" value="<%=loginUser.getUserId()%>">
						<%  }else{
								response.sendRedirect("./loginForm.jsp");
							}
						%>
					</label>
				</p>
				<p>
					<label>
						사진1 : 
						<input type="file" name="img1">
					</label>
				</p>
				<p>
					<label>
						사진2 : 
						<input type="file" name="img2">
					</label>
				</p>
				<p>
					<label>
						사진3 : 
						<input type="file" name="img3">
					</label>
				</p>
				<p>
					<label>
						사진4 : 
						<input type="file" name="img4">
					</label>
				</p>
				<p>
					<label>
						사진5 : 
						<input type="file" name="img5">
					</label>
				</p>
				<p>
					<label>
						글내용 : 
						<textarea name="contents" rows="" cols="">글 내용입니다.</textarea>
					</label>
				</p>
				<p>
					<button type="reset">초기화</button>
					<button>등록</button>
				</p>			
			</form>	
		</div>
	</main>
	
</body>
</html>