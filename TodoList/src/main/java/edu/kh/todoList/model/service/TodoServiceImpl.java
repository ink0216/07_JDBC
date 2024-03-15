package edu.kh.todoList.model.service;
//지정된 위치의 static을 모두 가져와 사용하겠다는 뜻
import static edu.kh.todoList.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kh.todoList.model.dao.TodoDAO;
import edu.kh.todoList.model.dao.TodoDAOImpl;
import edu.kh.todoList.model.dto.Todo;

public class TodoServiceImpl implements TodoService{
//interface 상속 구문은 extends가 아닌 implements로 써야함!!!!!!!
	
	//Service : 비즈니스 로직 처리(실제 업무) - 데이터 가공, 트랜잭션 제어 처리
	
	//DAO 객체 생성하기
	private TodoDAO dao = null;
	
	//기본생성자가 객체 초기화하는 데에 가장 우선순위가 높아서
	//기본생성자로 생성하는게 좋다!!!!
	public TodoServiceImpl() {
		dao = new TodoDAOImpl();
	}
	//할 일 목록 + 완료된 할 일 개수 조회
	@Override
		public Map<String, Object> selectAll() throws SQLException {
		//1. 커넥션 얻어오기 <-JDBCTemplate에서 얻어와야 하는데 import static 안하면 JDBCTemplate.getConnection해야함
		Connection conn = getConnection();
		
		//2. 두개를 한 번에 조회할 수는 없어서 하나씩 조회하기
		//2-1. 할 일 목록 조회하는 DAO 메서드 호출 후 결과 반환 받기
		//	여러 행을 얻어와야 하므로 List이용했었음
		List<Todo> todoList = dao.selectAll(conn); //conn 전달해준다
		//-------------------------------------------------------------------------------------
		//3 . 완료된 할 일 개수를 조회하는 DAO 메서드 호출 후 결과 반환 받기
		int completeCount = dao.getCompleteCount(conn); //DAO 메서드를 두개 만들어야한다
		
		//4. 둘다 SELECT이니까 이제 conn이 할 일 없어서 Connection 반환하기(트랜잭션 처리 안해도 돼서)
		//트랜잭션 처리는 dml구문일 때 해야함(commit / rollback)
		close(conn);
		
		//5. Map을 생성해서 DAO 호출 결과 두개를 한 번에 묶어서 반환하기
		Map<String, Object> map = new HashMap<String, Object>();
		//key는 String, value는 Object
		//hash가 붙은 것은 hashcode와 오버라이딩 해야하는데 String은 이미 오버라이딩 돼있어서 할 게 없다
		/*키  객체는  hashCode()와  equals()를  재정의해  동등  객체가  될  조건을  정해야  함.
		때문에 키 타입은 hashCode와 equals()메소드가 재정의되어 있는 String타입을 주로 사용*/
		
		/*Properties
		 * : 키와  값을  String타입으로  제한한 Map컬렉션.
				주로 Properties는 프로퍼티(*.properties)파일을 읽어 들일 때 주로 사용*/
		map.put("todoList", todoList);
		map.put("completeCount", completeCount);
		
		return map;
		}
	//할 일 추가
	@Override
		public int addTodo(String todoTitle, String todoContent) throws SQLException {
		//커넥션 만들기
		Connection conn = getConnection();
		
		//DAO 메서드 호출하기
		int result = dao.addTodo(conn,todoTitle, todoContent); 
		
		//insert라는 dml을 수행했기 때문에 트랜잭션 제어 처리해야함!!
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn); //커넥션 반환
		
		return result; //결과 반환
		}
	//할 일 상세 조회
	@Override
		public Todo selectTodo(int todoNo) throws SQLException {
		Connection conn = getConnection();
		Todo todo = dao.selectTodo(conn,todoNo);
		//dml아니어서 트랜잭션 처리할 필요 없다
		close(conn);
		return todo;
		}
	//완료 여부 변경하기
	@Override
		public int changeComplete(int todoNo, String complete) throws SQLException {
		Connection conn = getConnection();
		int result = dao.changeComplete(conn,todoNo, complete); 
				//수정하는 dml이므로 트랜잭션 처리
		if(result>0) commit(conn);
		else					rollback(conn);
		close(conn);
			return result;
		}
	//할 일 수정하기
	@Override
		public int updateTodo(Todo todo) throws SQLException {
		//Connection 만들기
		Connection conn = getConnection();
		int result = dao.updateTodo(conn,todo);
		if(result>0) commit(conn);
		else 				rollback(conn);
		close(conn);
			return result;
		}
	//할 일 삭제하기
	@Override
		public int deleteTodo(int todoNo) throws SQLException {
		//Connection 만들기
			Connection conn = getConnection();
			int result = dao.deleteTodo(conn,todoNo);
			if(result>0) commit(conn);
			else 				rollback(conn);
			close(conn);
				return result;
		}
}
