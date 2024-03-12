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
			//sql.xml 파일을 만들어서 거기에 있는 것을 읽어오겠다
			prop.loadFromXML(new FileInputStream(path));
			//
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
			String sql = prop.getProperty("selectAll"); //key값이 selectAll인 것을 읽어옴
			
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
}
