package edu.kh.dept.model.dao;
import static edu.kh.dept.common.JDBCTemplate.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.dept.common.JDBCTemplate;
import edu.kh.dept.model.dto.Department;

//DAO(Data Access Object) : DB 접근 객체(SQL수행, 결과 반환)
public class DepartmentDAOImpl implements DepartmentDAO{
	//필드
	//커넥션은 여기서 안할거임
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop; //Map<String,String> 형태이고, 파일 입출력이 쉬운 맵이다
	
	//기본생성자
	// - 객체 생성 시 sql.xml 파일 내용을 읽어와 prop에 저장하는 코드!!!!
	//		이렇게 해야 관리하기 편함
	public DepartmentDAOImpl() {
		try {
			prop = new Properties(); //Properties 객체 만들기
			
			//sql도 여기다 안쓰고 외부파일로 뺄거임
			String path = DepartmentDAOImpl.class.getResource("/edu/kh/dept/sql/sql.xml").getPath();
			//저 클래스를 2진화한 파일의 자원을 읽어오겠다
			//sql.xml 파일을 만들어서 거기에 있는 것을 읽어오겠다
			//그래서 거기까지 가는 데 까지의 경로를 path에 저장
			prop.loadFromXML(new FileInputStream(path));
			//그래서 path 경로에 있는 파일의 내용을 스트림으로 읽어와서 prop에 적재하기
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//부서 전체 조회하기
	@Override
	public List<Department> selectAll(Connection conn) throws SQLException {
		//여기에 JDBC 코드 쓰기
		//결과 저장용 변수 선언 / 객체 생성하기
		List<Department> deptList = new ArrayList<Department>(); //내용 아직 아무것도 없음
		//차례차례 담아서 돌려보내줄거다
		try {
			//SQL 작성 코드 쓰기
			//여기에 직접 안쓰고 xml에 쓴 후에 가져올거임
			//sql 패키지에 파일 읽어와서 prop에 넣겠다
			String sql = prop.getProperty("selectAll"); //key값이 selectAll인 것을 읽어옴(sql.xml에서)
			
			//Statement 객체 생성하기
			stmt = conn.createStatement();
			
			//SQL 수행 후 결과 ResultSet 반환 받기
			rs = stmt.executeQuery(sql);
			
			//ResultSet 한 행씩 접근해서 컬럼 값을 얻어온 후
			//deptList에 옮겨담기
			while(rs.next()) {
				//행이 존재할 때
				//커서 이동하면서
				String deptId = rs.getString("DEPT_ID");
				String deptTitle = rs.getString("DEPT_TITLE");
				String locationId = rs.getString("LOCATION_ID");
				
				Department dept = new Department(deptId, deptTitle, locationId); //객체 하나 만들어서
				deptList.add(dept); //담아준다
			}
			
			
		}finally {
			close(rs);
			close(stmt); //connection은 여기서 닫아주면 안됨
		}
		return deptList;
	}
	
	//부서 추가하기
	@Override
	public int insertDepartment(Connection conn, Department dept) throws SQLException {
		//1. 결과 저장용 변수 선언 / 객체 생성하기
		int result = 0; //DML할거여서 정수 반환됨
		try {
			//2. SQL 얻어오기(이제 작성 여기다 안하고, sql.xml파일에서 얻어오기!
			//자바 코드에 sql 쓰는거 어렵기도 해서 분리함
			//DAOImpl 객체가 생성될 때 sql.xml파일을 읽어와서 prop 객체에 저장함
			//properties는 맵이어서 거기서 꺼내 쓰면 된다
			String sql=prop.getProperty("insertDepartment");
			
			//3. 물음표 세개 ->PreparedStatement 객체 생성하기
			pstmt=conn.prepareStatement(sql); //PreparedStatement는 생성과 동시에 sql을 적재하는데 적재하고 보니 sql에 빈칸있음
			
			//4. ?에 알맞은 값 대입
			pstmt.setString(1, dept.getDeptId()); //dept라고 묶어서 가져왔는데 하나씩 꺼내 쓸 수 있다
			pstmt.setString(2, dept.getDeptTitle()); 
			pstmt.setString(3, dept.getLocationId()); 
			
			//5. SQL 수행 후 결과 반환 받기
			//insert라는 dml 구문을 수행하므로 삽입 성공한 행의 개수가 반환될거다
			result = pstmt.executeUpdate(); //dml은 다 update로!
			//아이디 중복돼서 무결성 제약 조건(KH_LIK.DEPT4_PK)에 위배됩니다
		}finally { //예외 발생 여부와 상관 없이 무조건 수행됨
			//6. 사용한 JDBC 객체 자원 반환(단, 커넥션은 제외 , 커넥션은 서비스에서 트랜잭션 제어처리 후 닫을거니까)
			close(pstmt);
		}
		return result;
	}
}
