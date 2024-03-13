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
}
