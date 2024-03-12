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
}
