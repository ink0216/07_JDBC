package edu.kh.todoList.model.dao;
//DAO(Data Access Object) : DB에 접근하는 객체 (SQL 수행하고 결과 반환하는 객체)
//DB까지 직접 가는 유일한 객체

//지정된 위치의 static을 모두 가져와 사용하겠다는 뜻
import static edu.kh.todoList.common.JDBCTemplate.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.todoList.model.dto.Todo;

public class TodoDAOImpl implements TodoDAO{
	//JDBC 객체 참조변수 선언하기
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//Properties 객체 참조변수 선언하기
	//Properties : xml파일 읽어올 때 써먹는, k:v가 모두 String인 Map
	private Properties prop; //null 안써도 자동으로 null대입됨
	
	//기본 생성자로 객체 생성 시
	//Properties 객체 생성 + xml 파일 내용 읽어오기
	public TodoDAOImpl() {
		try {
			prop = new Properties();//Properties 객체 생성
			String path = TodoDAOImpl.class.getResource("/edu/kh/todoList/sql/sql.xml").getPath();
					//우리가 읽어와야 하는 sql 파일(sql.xml)의 경로 
					// == 이 클래스가 2진수로 저장되는 곳에서 자원을 얻어올거다
					//클래스 파일이 모이는 곳까지 가는 데 까지의 경로를 얻어올거다
			
			prop.loadFromXML(new FileInputStream(path));
			//path에 있는 xml에 있는 내용을 스트림으로 얻어와서 prop에 적재한다
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//할 일 목록 조회
	@Override
	public List<Todo> selectAll(Connection conn) throws SQLException {
		//결과 저장용 변수 선언 / 객체 생성
		List<Todo> todoList = new ArrayList<Todo>();
		try {
			//SQL 얻어오기
			String sql = prop.getProperty("selectAll"); //key값이 selectAll인 sql을 얻어오겠다(sql.xml파일에서)
			
			//이번엔 ? 없어서 stmt, pstmt 둘 다 써도 됨
			stmt = conn.createStatement();
			
			//SQL 수행 후 결과 반환받기
			rs=stmt.executeQuery(sql);
			
			//조회 결과 한 행씩 접근하기
			while(rs.next()) {
				int todoNo = rs.getInt("TODO_NO"); //번호는 숫자니까
				String todoTitle = rs.getString("TODO_TITLE");
				String complete = rs.getString("COMPLETE");
				String regDate = rs.getString("REG_DATE");
				
				//이걸 가지고 Todo 객체를 생성해서 값 세팅 후 todoList에 추가하기
				//객체 만들면서 바로 세팅하려면 생성자 이용하는게 편하니까
				//매개변수 생성자 알맞은 형태로 만들기
				Todo todo = new Todo(todoNo, todoTitle, complete, regDate);
				todoList.add(todo);
			}
		}finally {
			close(rs);
			close(stmt);
		}
		return todoList;
	}
	//완료된 할 일 개수 조회
	@Override
	public int getCompleteCount(Connection conn) throws SQLException {
		int completeCount = 0;
		try {
			String sql = prop.getProperty("getCompleteCount"); //이런 key를 가지는 sql을 얻어오겠다
			
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			// ->GROUP BY가 없는 SELECT의 COUNT 그룹 함수의 결과는 1행!
			//	-> if문을 이용해서 조회 결과 행 접근하는게 효율적
			if(rs.next()) {
				completeCount = rs.getInt(1); //첫번째 컬럼값을 넣어준다
			}
		}finally {
			close(rs);
			close(stmt);
		}
		return completeCount;
	}
	//할 일 추가하기
	@Override
	public int addTodo(Connection conn, String todoTitle, String todoContent) throws SQLException {
		//결과 저장할 변수 만들기
		int result = 0;
		try {
			//SQL 얻어오기
			String sql = prop.getProperty("addTodo"); //key값이 addTodo인 sql을 sql.xml에서 얻어오겠다
			
			pstmt=conn.prepareStatement(sql); //?있으므로 이거 사용
			pstmt.setString(1, todoTitle);
			pstmt.setString(2, todoContent);
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}
	//할 일 상세 조회하기
	@Override
	public Todo selectTodo(Connection conn, int todoNo) throws SQLException {
		//결과 저장용 변수 선언/객체 생성하기
		Todo todo = null; //결과가 없으면 null이고, 결과가 있을 때에만 new해서 객체 만들도록 하면 된다!
		try {
			String sql = prop.getProperty("selectTodo"); //1행 조회하는 sql을 얻어오겠다(sql.xml 파일에서)
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, todoNo);
			
			rs=pstmt.executeQuery(); //SQL 수행, 결과 반환
			
			//PK가 적용된 컬럼을 조건으로 해서 조회하면
			//조회 결과가 있어도 딱 1행만 조회되기 때문에
			//while문 보다는 if문 사용하는 것이 효율적이다
			if(rs.next()) {
				todo=new Todo(); //결과 저장용 객체
				todo.setTodoNo(rs.getInt("TODO_NO"));
				//TODO_NO 컬럼값을 방금 만든 Todo 객체에 넣겠다
				todo.setTodoTitle(rs.getString("TODO_TITLE"));
				todo.setTodoContent(rs.getString("TODO_CONTENT"));
				todo.setComplete(rs.getString("COMPLETE"));
				todo.setRegDate(rs.getString("REG_DATE"));
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		return todo; 
	}
	//완료 여부 수정하기
	@Override
	public int changeComplete(Connection conn, int todoNo, String complete) throws SQLException {
		//결과 저장 변수 선언
		int result =0;
		try {
			String sql=prop.getProperty("changeComplete");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, complete);
					pstmt.setInt(2, todoNo);
					result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	//할 일 수정하기
	@Override
	public int updateTodo(Connection conn, Todo todo) throws SQLException {
		//결과 저장 변수 선언
		int result =0;
		try {
		//sql 만들기
			String sql = prop.getProperty("updateTodo");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, todo.getTodoTitle());
			pstmt.setString(2, todo.getTodoContent());
			pstmt.setInt(3, todo.getTodoNo());
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	//할 일 삭제하기
	@Override
	public int deleteTodo(Connection conn, int todoNo) throws SQLException {
	//결과 저장 변수 선언
			int result =0;
			try {
			//sql 만들기
				String sql = prop.getProperty("deleteTodo");
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, todoNo); //전달받은 파라미터 하나임!!!->하나만 세팅
				
				
				result = pstmt.executeUpdate();
			}finally {
				close(pstmt);
			}
			
			return result;
	}
}

