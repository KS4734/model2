package model2_study.com.dto;

// 한 페이지에 5개
// << < 1~5 > >>  ->  << < 6~10 > >>  ->  << < 25 26 27 28 29 > >>

// 현재 페이지 앞뒤로 2개
// << < 5 6 7 8 9 > >>

public class PagingDto {
	private int totalPage;
	private int startPage;
	private int endPage;
	private int page;
	private int rows;
	private int nextPage;
	private int prevPage;
	private boolean next;
	private boolean prev;
	private int totalRows;
	public PagingDto(int page, int totalRows, int rows) {
		this.page=page;
		this.rows=rows;
		this.totalRows=totalRows;
		// totalRows => 22
		// rows => 7
		// 22 / 7 = 3
		// 22 % 7 =나머지가 있음
		// 3
		this.totalPage = totalRows/rows+((totalRows%rows>0)?1:0);
		startPage = page-2;
		endPage = page+2;
		if(startPage<1) {
			endPage += -1*(startPage-1);
			startPage=1;
		}
		if(endPage > totalPage) {
			startPage -= (endPage - totalPage);
			endPage = totalPage;
		}
		this.nextPage = page + 1;
		this.prevPage = page - 1;
		this.next = (nextPage > totalPage)?false:true;
		this.prev = (nextPage < 1)?false:true;
	}
		
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	@Override
	public String toString() {
		return "\"paging\":{ \"totalPage\":\"" + totalPage + "\",\"startPage\":\"" + startPage + "\",\"endPage\":\""
				+ endPage + "\",\"page\":\"" + page + "\",\"rows\":\"" + rows + "\",\"nextPage\":\"" + nextPage
				+ "\",\"prevPage\":\"" + prevPage + "\",\"next\":\"" + next + "\",\"prev\":\"" + prev
				+ "\",\"totalRows\":\"" + totalRows + "\" }\n";
	}

	
	
}
