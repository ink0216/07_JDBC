package edu.kh.jdbc.model.dao;
//DAO (Data Access Object)  :데이터가 저장되어있는 곳(DB나 파일)에 접근하는 객체
//DB에 접근 == SQL 을 수행하고, 결과를 받아오는 것을 여기서 함
//import static edu.kh.jdbc.common.JDBCTemplate.*; : JDBCTemplate 클래스 내부의 static이 붙은 메서드를 모두 가져온다(상속한 것처럼 됨)
//																										->가져온 메서드는 메서드 호출 시 클래스명을 작성하지 않아도 된다!(앞에 JDBCTemplate. 안써도 된다)
import static edu.kh.jdbc.common.JDBCTemplate.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.model.dto.Department;

public class DepartmentDAO { //db와 연결해서 sql 수행하고 결과 반환받아주는 역할 함
	//필드
	//db로 왔다리갔다리 함
	/* JDBC 객체 참조 변수 선언->공공재로 사용할 수 있게 올림*/
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/**부서 추가/삽입 메서드
	 * @param dept(부서코드, 부서명,지역코드가 담겨있게 해서 객체를 전달받을거임)
	 * @return result(삽입된 결과 행의 개수가 반환될거임) ==>이런 식으로 만들 예정!!!
	 * @throws SQLException 
	 */
	public int insertDepartment(Department dept) throws SQLException {
		//뭐가 들어올 지는 모르지만 너는 이 역할만 해!(분업화시키기)
		int result = 0; //결과 저장용 전역 변수 선언
		//마지막에 int인 result를 반환시킬거임
		try {
			
			//1. Connection 얻어오기
			//		->JDBCTemplate에서 얻어옴
			//conn=JDBCTemplate.getConnection() 원래는 이렇게 써야하는데 맨 위에 import static 해놔서 클래스명 안써도된다
			conn=getConnection();
			
			//2. SQL 작성하기
			String sql = "INSERT INTO DEPARTMENT4 VALUES(?,?,?)";
			
			//3. PreparedStatement 객체 생성하기
			pstmt = conn.prepareStatement(sql); //여기서 발생한 예외를 던질거다 ->catch문 없어도 된다
			
			//4. PreparedStatement의 sql에는 ?가 세개가 들어있으니까 ?에 알맞은 값 채워넣기!!
			pstmt.setString(1, dept.getDeptId()); //매개변수에 담겨있을거다
			pstmt.setString(2, dept.getDeptTitle()); //매개변수에 담겨있을거다
			pstmt.setString(3, dept.getLocationId()); //매개변수에 담겨있을거다
			//DTO에 담겨있는 것을 그대로 얻어와서 세팅하겠다
			
			//5. SQL 수행 후 결과 반환받기
			result = pstmt.executeUpdate(); //DML 수행 -> 변화한 행의 수 반환
			
			//6. 결과에 따라서 트랜잭션 제어 처리하기
			if(result>0) commit(conn);
			else				rollback(conn); //얘네들도 앞에 클래스명 안써도 된다
			
		} finally {
			//7. 사용한 JDBC 객체 자원 반환하기
			close(pstmt);
			close(conn);
		}
		return result;
		//입력값을 외부에서 전달받고, 공통코드는 다른 클래스로 만들어놔서 코드 길이 많이 짧아졌다
	}
	/**부서 전체 조회하는 메서드
	 * @return Department로 타입이 제한된 List를 반환해줌
	 * @throws SQLException 
	 */
	public List<Department> selectAll() throws SQLException{
		//결과 저장용 변수 선언
		List<Department> deptList = null;
		try {
			//1. Connection 얻어오기
			//db와 연결한 정보를 가지고 있는, JDBCTemplate에 있는 Connection을 얻어오기
			//import static을 썻기 때문에 앞의 클래스명 안써도 된다
			conn=getConnection();
			
			//2. SQL 작성
			String sql="SELECT * FROM DEPARTMENT4";
			
			//3. PreparedStatement 객체를 생성하기 ->꼭 ?안써도 됨
			pstmt = conn.prepareStatement(sql); //위에 써져있던 sql을 담는다
			//담은 sql을 보니까 ?(placeholder)가 없으므로 값 채우는 구문 패스하면 된다
			
			//4. SQL 실행 후 결과 반환 받기
			//이번에는 SQL이 DML이 아닌 Select이므로 결과로 resultset이 반환된다
			rs=pstmt.executeQuery(); //rs도 위에서 해둠
			//executeQuery() : SELECT 수행 -> ResultSet을 반환한다
			//executeUpdate() : DML(INSERT, UPDATE,DELETE) 수행 -> 변화된 행의 수를 반환한다
			
			//5. 결과를 저장할 List를 생성한 후
			//한 행 씩 접근해서 컬럼 값을 얻어와 List에 추가하기
			deptList = new ArrayList<Department>(); //Department로 타입 제한된 ArrayList
			while(rs.next()) {
				//행이 있으면 출력
				String deptId = rs.getString("DEPT_ID");
				String deptTitle = rs.getString("DEPT_TITLE");
				String locationId = rs.getString("LOCATION_ID"); //하나씩 꺼내와서 변수에 저장
				
				//DB에서 읽어온 값을 가지고 Department 객체를 생성
				Department dept = new Department(deptId, deptTitle, locationId);
				
				//이것을 deptList에 추가하기
				deptList.add(dept);
				
				//위의 과정을 전체 행 만큼 다 반복해서 deptList에 담음
				
				//ResultSet에 있는 것을 왜 굳이 deptList 에 담았을까?
				//ResultSet은 finally 구문에서 close돼야 하는데 그러면 반환할 수 없어서
			}
			
		}finally {
			close(rs);
			close(pstmt);
			close(conn);
		}
		return deptList; //이걸 반환할거야
	}
	/**부서 검색
	 * @param title
	 * @return deptList
	 * @throws SQLException
	 */
	public List<Department> selectDepartmentTitle(String title) throws SQLException{
		//어디서든지 접근 가능하고 리스트를 반환하는 메서드
		//결과 저장용 변수 선언 또는 객체 생성
		List<Department> deptList = new ArrayList<Department>();
		try {
			//1. 커넥션 얻어오기 (JDBC에서)
			conn = getConnection(); //앞에 JDBCTemplate안써도 됨 (위에 import static 썼기 때문에!)
			
			//2. SQL 작성하기
			String sql="SELECT * FROM DEPARTMENT4 "
					+ "WHERE DEPT_TITLE LIKE '%'|| ?||'%'"; //? placeholder가 문자 그대로인 ?로 인식되지 않도록 이렇게 함
			//3. PreparedStatement 객체 생성 + 이거는 생성되자마자 sql을 객체 안에 적재를 시켜놓는다
			pstmt=conn.prepareStatement(sql); //적재하니까 sql에 빈칸이 있네?
			
			//4. ?에 알맞은 값 대입하기
			pstmt.setString(1, title);
			
			//5. SQL(SELECT) 수행 후 결과(ResultSet) 반환 받기
			rs=pstmt.executeQuery();
			
			//6. rs는 한 행씩 접근하며 컬럼값 얻어오기
			//	->얻어온 컬럼 값을 이용해 Department 객체를 생성한 후
			//		deptList에 옮겨담기 (rs는 사용 후 닫아줘야 하므로) ->옮겨담은 deptList를 반환해주기
			while(rs.next()) {
				//만약 조회된 것이 하나도 없으면 while문  한 번도 실행하지 않음
				String deptId = rs.getString("DEPT_ID");
				String deptTitle = rs.getString("DEPT_TITLE");
				String locationId = rs.getString("LOCATION_ID");
				
				//Department 객체를 생성한 후
				Department dept=new Department(deptId, deptTitle, locationId);
				deptList.add(dept); //다 옮겨담는다
			}
		}finally {
			//7. 사용한 JDBC 객체 자원 반환하기
			close(rs);
			close(pstmt);
			close(conn); //JDBCTemplate에 처리 해둠
		}
		return deptList;
	}
	public int deleteDepartment(String deptId) throws SQLException {
		int result = 0;
		try {
			//1. Connection 만들기
			conn=getConnection();
			
			//2. SQL 작성하기
			String sql = "DELETE FROM DEPARTMENT4 "
					+ "WHERE DEPT_ID = ?"; //? placeholder가 문자 그대로인 ?로 인식되지 않도록 이렇게 함
			//빈칸 -> PreparedStatement 쓰겠네
			//3. ? 빈칸 채우기
			//3. PreparedStatement 객체 생성 + 이거는 생성되자마자 sql을 객체 안에 적재를 시켜놓는다
			pstmt=conn.prepareStatement(sql); //적재하니까 sql에 빈칸이 있네?
			
		//4. ?에 알맞은 값 대입하기
			pstmt.setString(1, deptId);
		//5. SQL(SELECT) 수행 후 결과(ResultSet) 반환 받기
			result=pstmt.executeUpdate();
			
		//6. 결과에 따라서 트랜잭션 제어 처리하기
			if(result>0) commit(conn);
			else				rollback(conn); //얘네들도 앞에 클래스명 안써도 된다
		}finally {
			close(pstmt);
			close(conn); //JDBCTemplate에 처리 해둠
		}
		return result;
	}
	/**부서명 수정하기
	 * @param deptId
	 * @param deptTitle
	 * @return result
	 * @throws SQLException
	 */
	public int updateDepartment(String deptId, String deptTitle) throws SQLException {
		int result = 0; //결과 저장용 변수
		try {
			conn=getConnection(); //커넥션 얻어오기
			String sql="UPDATE DEPARTMENT4 "
					+ "SET DEPT_TITLE= ? "
					+ "WHERE DEPT_ID =?";
			
			pstmt=conn.prepareStatement(sql);//? 있으므로 PreparedStatement 객체 생성 & sql 적재
			
			pstmt.setString(1, deptTitle);
			pstmt.setString(2, deptId);
			result = pstmt.executeUpdate(); //SQL 수행 후 결과 반환 받기
			
			//결과에 따라서 트랜잭션 제어 처리하기
			if(result>0) commit(conn);
			else rollback(conn);
		}finally {
			close(pstmt);
			close(conn); //JDBCTemplate에 처리 해둠
		}
		return result; //결과 반환
	}
	/**부서코드가 존재하는지 확인
	 * @param deptId
	 * @return check
	 * @throws SQLException
	 */
	public int checkDepartment(String deptId) throws SQLException{
		int result = 0; //결과 저장용 변수 선언
		try {
			//1. 커넥션 얻어오기
			conn=getConnection();
			//2. SQL 작성하기
			String sql="SELECT COUNT(*) FROM DEPARTMENT4 "
					+ "WHERE DEPT_ID= ?";
			//3.PreparedStatement 객체 생성하기
			pstmt = conn.prepareStatement(sql);
			//4. 빈칸 채우기
			pstmt.setString(1, deptId);
			//5. SQL(SELECT) 수행 후 결과(ResultSet) 반환받기
			rs=pstmt.executeQuery();//SELECT 실행
			//6. 한 행 씩 접근해서 컬럼값 얻어오기
			//근데 그룹함수는 무조건 1행만 나옴 ->while문 쓸 필요 없음
			if(rs.next()) {
				//result=rs.getInt("COUNT(*)"); --조회된 컬럼명 써도 되고
				result=rs.getInt(1);//조회된 컬럼 순서로 써도 된다 (order by절 처럼)
				//if문이든, while문이든 조건식이 true일 때 수행하는데,
				//while문 쓸 경우 한번 검사 후 첫 번째 행 존재해서 수행한 후에 또 와서 다시 검사해봄(두 번 검사하므로 비효율적)
				//->조회 결과가 1행만 있을 경우에는 if문이 while문보다 더 효율적이다
			}
		}finally {
			close(rs);
			close(pstmt);
			close(conn);
		}
		return result; //여기에 저장된 값이 마지막에 반환될것이다
	}
	
}
