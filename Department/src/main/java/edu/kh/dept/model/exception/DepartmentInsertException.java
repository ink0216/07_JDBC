package edu.kh.dept.model.exception;
//사용자 정의 예외 만드는 방법!
//->아무 Exception 클래스를 상속 받으면 된다!
// + RuntimeException 또는 그 자식 예외를 상속 받으면
//		Unchecked Exception으로 설정된다
//Unchecked Exception == 예외 처리를 굳이 안해도 되는 예외!!(->그래서 이걸 많이 사용함)
public class DepartmentInsertException extends RuntimeException{
//부서 삽입 중에 예외가 생겼다
	
	//기본생성자
	public DepartmentInsertException(){
		super("부서 추가(INSERT) 중 예외 발생(제약 조건 위배)"); //super 생성자 : 부모(RuntimeException) 생성자 호출
		//이 매개변수 내용이 예외 발생 시 나타나는 메세지가 됨!
	}
	//매개변수 생성자
	public DepartmentInsertException(String message) {
		super(message); //매개변수로 전달받아온 message를 오류 메세지로 쓰겠다
	}
}
