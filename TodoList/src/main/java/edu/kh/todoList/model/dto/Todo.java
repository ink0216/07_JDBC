package edu.kh.todoList.model.dto;
//DTO : 값 전달용 객체
public class Todo {
//한번에 묶어서 전달할 값들 세팅
	private int todoNo; //할 일 번호
	private String todoTitle; //할 일 제목
	private String todoContent; //할 일 내용
	private String complete; //할 일 완료 여부("Y", "N")
	//DB에서 CHAR == 문자열 == Java에서의 String
	//Java에서의 char = 문자
	private String regDate; //할 일 등록일(DB에서 String으로 바꿔서 가져올거임)
	
	//기본 생성자
	public Todo() { }

//getter/setter
	public int getTodoNo() {
		return todoNo;
	}

	public void setTodoNo(int todoNo) {
		this.todoNo = todoNo;
	}

	public String getTodoTitle() {
		return todoTitle;
	}

	public void setTodoTitle(String todoTitle) {
		this.todoTitle = todoTitle;
	}

	public String getTodoContent() {
		return todoContent;
	}

	public void setTodoContent(String todoContent) {
		this.todoContent = todoContent;
	}

	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

//toString 오버라이딩
	@Override
	public String toString() {
		return "Todo [todoNo=" + todoNo + ", todoTitle=" + todoTitle + ", todoContent=" + todoContent + ", complete="
				+ complete + ", regDate=" + regDate + "]";
	}

	//매개변수 생성자(할 일 목록 조회할 때 사용), 컬럼 하나만 빼고 만듦
	public Todo(int todoNo, String todoTitle, String complete, String regDate) {
		super();
		this.todoNo = todoNo;
		this.todoTitle = todoTitle;
		this.complete = complete;
		this.regDate = regDate;
	}
	//매개변수 생성자(할 일 수정할 때 사용)
	public Todo(int todoNo, String todoTitle, String todoContent) {
		super(); //전달받은 값으로 필드를 초기화
		this.todoNo = todoNo;
		this.todoTitle = todoTitle;
		this.todoContent = todoContent;
	}
	
	
	
	
	
	
	
	
	
	
}
