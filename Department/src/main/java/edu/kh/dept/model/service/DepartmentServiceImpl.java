package edu.kh.dept.model.service;
//Service 
// - 비즈니스 로직 처리하는 역할(실제 일)
// - 실제로 요청에 대한 일을 하는 것
// - 데이터 가공(가공해서 db에 넣거나 db에서 꺼낸 것을 가공해서 보여주거나), 트랜잭션 제어 처리 
//import static edu.kh.dept.common.JDBCTemplate.*; : JDBCTemplate의 static 메서드 모두 가져와서 내것처럼 쓰기
import static edu.kh.dept.common.JDBCTemplate.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import edu.kh.dept.model.dao.DepartmentDAO;
import edu.kh.dept.model.dao.DepartmentDAOImpl;
import edu.kh.dept.model.dto.Department;
import edu.kh.dept.model.exception.DepartmentInsertException;

//트랜잭션 제어 처리 
// -> 하나의 Service 메서드가 여러 DAO 메서드를 호출할 수 있다!
// -> 호출한 모든 DAO 메서드가 성공했을 때에만 Commit
//		- 하나라도 실패하면 Rollback 수행
public class DepartmentServiceImpl implements DepartmentService{
//public void method() {
//	dao.method1(); //insert
//	dao.method2(); //update
//	dao.method3(); //delete
//	//두개 성공하고 하나 실패 시, 앞의 dml도 롤백해야함
//	//세 개 다 성공했을 때에만 커밋하겠다 이런것을 여기서 처리
//}
	//기본생성자 없으면 자동으로 만들어줌
	private DepartmentDAO dao = new DepartmentDAOImpl(); //여러 번 호출할 거여서 필드에 DAO 객체생성

	//모든 부서 조회
	@Override
	public List<Department> selectAll() throws SQLException {
		/* 1. 커넥션 얻어오기*/
		//커넥션을 서비스에서 얻어오는 이유 
		//트랜잭션 처리를 서비스가 하는데 트랜잭션 처리하려면 커넥션이 꼭 있어야 해서
		//커넥션을 여기서 만듦
		Connection conn = getConnection();
		
		/* 2. DAO 메서드 호출(매개 변수로 Connection을 같이 전달해줘야함)*/
		//connection이 있어야 statement등을 만들 수 있어서 잠깐 전달해줘야함
		List<Department> deptList = dao.selectAll(conn); 
		//conn 빌려줘서 모든 부서 조회해서 돌려받을거임(조회 결과 반환)
		
		/* 3. SELECT는 DML과 달리 테이블이 변한 것 아니므로 트랜잭션 제어 필요 없음!!->패스!*/
		/* 4. 사용 완료된 Connection 닫기*/
		close(conn);
		
		/* 5. 결과 반환하기*/
		return deptList;
	}
	
	//부서 추가 서비스
	//서비스에서 트랜잭션 제어 처리 해야함
	//커밋 롤백을 하려면 커넥션이 있어야 함!
	@Override
	public int insertDepartment(Department dept) throws DepartmentInsertException {
		int result=0; //결과 저장 변수
		
		//1. 커넥션 얻어오기
		Connection conn = getConnection(); //JDBCTemplate에 만듬 
		try {
			
			//2. 데이터 가공 할 필요 없이 있는 그대로 DB에 넣기
			// DAO 메서드 호출 후 결과 반환 받기
			//(DAO 메서드 수행 시 커넥션이 필요하기 때문에 매개변수로 conn을 전달해야함!!)
			//(DB와의 연결 정보 담고있는 conn을 넘겨줘야 DAO가 DB와 연결할 수 있다)
			result = dao.insertDepartment(conn,dept); //두 값을 보낸다
			//여기서 에러 던지면 밑의 코드가 수행 안되나???*****************************************************
			
			//3. DAO 수행 결과가 오면, 결과에 따라 트랜잭션 제어 처리하기
			if(result>0) commit(conn); //성공
			else				rollback(conn); //실패
		}catch(SQLException e) {
			//dao에서 예외 발생하면 여기서 처리
			//dao에서 예외 발생한것을 바로 던지면 그 밑의 코드가 수행 안돼서
			//PK 제약조건 위배 / NOT NULL 제약조건 위배(부서코드 안 적은 경우 발생)
			e.printStackTrace(); //->나중에는 log라고 해서 파일에 에러 내용 적음
			//예외 발생 시 무조건 rollback하기
			rollback(conn);
			
			/* 제약조건 위배로 인해서 정상 수행되지 않음을 표현하기 위해
			 * 강제로 예외를 하나 더 발생시키기!!! ->이 때 "사용자 정의 예외"를 이용한다!!*/
			//실수했다는 것을 알려주기 위해서
			//throw이용해서 강제 예외 발생시키기
			throw new DepartmentInsertException(); //호출한 곳으로 던짐
		} finally { //무조건적으로 실행하므로 ->try 구문에서 오류가 생겨도 무조건적으로 finally구문 수행하므로 커넥션이 안닫히는 문제 해결함
		//4. 사용한 커넥션 반환 처리하기
			close(conn);
		}
		
		
		
		//5. 결과 반환하기
		return result;
		
	}
	//여러 부서 한번에 추가하기
	@Override
		public int multiInsert(List<Department> deptList) throws DepartmentInsertException{
		// 1. connection 얻어오기
		//트랜잭션 제어를 서비스가 하는데, 그걸 하려면 커넥션이 필요해서 여기서 만든다
		Connection conn = getConnection(); //이걸 JDBCTemplate에 써놨는데 위에 import static해서 그냥 쓸 수 있다
		
		//INSERT INTO VALUES => 1행만 삽입됨
		//여러 행을 한번에 삽입하려면 VALUES 대신 서브쿼리 사용하면 됨 ->근데 이걸로 안하고
		//이렇게 안하고 1행 삽입하는 것을 FOR문을 돌려서 여러 행 삽입하는 걸로 하기!
		int result = 0; //삽입된 행의 개수를 누적할 변수
		//for 반복문 돌면서 성공한 개수를 여기다 누적할거임
		
		String currentDeptId = null; //현재 삽입하려는 부서 코드를 저장하는 변수
		try {
			//2. 전달 받은 deptList의 크기(길이)만큼 반복하며
			//		DB에 INSERT하는 DAO 메서드 호출하기
			for(Department dept : deptList) {
				currentDeptId = dept.getDeptId(); //현재 삽입할 차례인 deptId를 변수에 저장해둠
				//deptList의 크기만큼 반복할거임
				result +=dao.insertDepartment(conn, dept); //dao의 이 메서드 수행하면 1행이 정상적으로 수행되면 1이 반환됨
				//만약 insertDepartment 호출해서 두번째 행 삽입하다가 에러 발생 시 바로 아래의 catch문으로 이동해서  
				//currentDeptId에 두 번째 행의 deptId 값이 들어있을 것임!->아래에서 그 값을 에러 메시지에 담아서 던지기!
			}
			//모두 입력 성공 시 result와 deptList의 길이가 같아짐!
			//그때에만 commit되도록 트랜잭션 처리하기!
			
			//3. 트랜잭션 제어 처리하기
			//		result에 누적된 값(==삽입 성공한 행의 개수)과
			//		deptList의 길이가 같은 경우
			//		==모두 삽입 성공한 경우 ==>이 때에만 commit을 하기
			
			// result != deptList의 길이
		  // ==중간에 삽입 실패한 것이 하나라도 있는 경우
			// == 실패 (insert 성공한 나머지 것들도 다 rollback을 시켜야 함!)
			// == 삽입 실패한 경우가 존재 -> rollback하기
			if(result == deptList.size()) commit(conn);
			else													rollback(conn);
				//배열 -> length // 컬렉션 ->size
				//deptList는 배열이 아닌 컬렉션이다!
		}catch(SQLException e) {
			//4. SQL 수행 중 오류 발생 시 
			//		(PK, NOT NULL 제약조건 위배 , 데이터 크기 초과한 경우(20바이트인데 30바이트를 입력했을 때), DB 연결 종료되는 등의 경우)
			e.printStackTrace(); //에러 원인 콘솔에 출력
			rollback(conn); //SQL이 한 행이라도 실패하면 전체 rollback하기
			
			//여기서 예외를 처리하면 이 캐치문 밖에서는 예외의 존재를 알 수 없음
			//서블릿에서는 여기서 예외가 발생했는지 알 수 없음
			//에러 페이지까지 만들어서 알려주고싶으면
			//사용자 정의 예외를 강제 발생시켜서 신호 주기
			
			// 예외가 발생되었음을 Controller(Servlet)에 전달하기
			//	-> 사용자 정의 예외를 강제 발생시키기 (예외 발생했었어. 에러 페이지를 보여줘)
			
			//PK 제약조건 위배만 생각하고 코드 작성함!(나머지 위배도 존재하지만, 다 구분하려면 CATCH문을 더 세분화해서 나눠야 함)
			throw new DepartmentInsertException(currentDeptId+" 부서 코드가 이미 존재합니다");//여기에 메세지 담아서 던지면 그 메세지를 그대로 볼 수 있음
			//던질 때 객체로 만들어서 던져야 함
			//어떤 부서코드가 이미 존재하는지 알려주려고 메세지를 이렇게 만듦
			//DepartmentInsertException가 Unchecked Exception을 상속 받아서 예외 처리를 굳이 안해도 됨
			//(안하는 경우 자동으로 throws DepartmentInsertException이 추가됨)
			//추가 자동으로 되지만 그래도 명시적으로 추가하기****************************************************************
		}finally {
			// 5. 사용 완료된 커넥션 반환하기
			close(conn);
		}
		// 6. 결과 반환해주기
			return result;
		} //성공, 실패, 예외==> 세 가지 경우 존재.. 각각의 경우에 어떻게 할 지 준비하기 
	
	//행 삭제
	@Override
		public int delete(String deptId) throws SQLException{
			// 1. Connection 얻어오기
		Connection conn = getConnection();
		
		//(DAO 메서드 수행 시 커넥션이 필요하기 때문에 매개변수로 conn을 전달해야함!!)
			//(DB와의 연결 정보 담고있는 conn을 넘겨줘야 DAO가 DB와 연결할 수 있다)
			int result = dao.delete(conn,deptId); //두 값을 보낸다
			
			if(result>0) commit(conn); //성공
			else				rollback(conn); //실패
			
		
		
			close(conn);
			return result;
			
		}
	//부서 1행 조회
	@Override
		public Department selectOne(String deptId) throws SQLException{
		//서비스가 다오를 호출하려면 커넥션 있어야 함
		//커넥션을 JDBCTemplate에서 얻어옴!
		Connection conn = getConnection();
		
		//2. DAO 메서드 호출 후 결과 반환 받기
		Department dept = dao.selectOne(conn,deptId); //커넥션에는 DB연결 정보가 담겨있는데 이 두개를 보내주기
		
		//3. SELECT 했기 때문에 트랜잭션 제어할 필요 없다
		//바로 커넥션 반환하기
		close(conn);
		
		//4. 결과 반환하기
			return dept;
		}
	//부서 수정하기
	//서비스 오면 커넥션 얻어오기부터 하기
	@Override
		public int updateDepartment(Department dept) throws SQLException {
		//트랜잭션 컨트롤
		Connection conn = getConnection();
		int result = dao.updateDepartment(conn,dept);
		if(result>0) commit(conn);
		else				rollback(conn);
		close(conn);
			return result; //서블릿으로 다시 보내주자
		}
	//부서 검색하기
	@Override
		public List<Department> searchDepartment(String keyword) throws SQLException {
			//트랜잭션의 주체가 커넥션이어서 서비스에서 커넥션 만듦 ->스프링에서는 자동으로 해줘서 안해도됨
			//트랜잭션 제어 -> 할 필요 없음
		//1. 커넥션 생성
		Connection conn = getConnection();
		//2. DAO 메서드 호출 후 결과 반환 받기
		List<Department> deptList = dao.searchDepartment(conn, keyword); //조회된 결과를 옮겨담아서 가져옴
		
		//3. 커넥션 반환
		close(conn);
		
		//4. 결과 반환
			return deptList;
		}
}
