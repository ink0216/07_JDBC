package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample5 {
public static void main(String[] args) {
	/* 1. JDBC 객체 참조 변수 선언*/
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	try {
		/* 2. DriverManager 객체를 이용해서 Connection 객체 생성하기 */
		/* 2-1. Oracle JDBC Driver객체를 메모리에 적재하기 */
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		/* 2-2. DB 연결 정보 작성해서 Connection 객체 생성하기 */
		String type = "jdbc:oracle:thin:@"; //type이라는 변수, 드라이버 종류를 어떤 것을 쓸 지
		String host = "localhost"; //DB 서버 컴퓨터의 IP주소 작성
		String port = ":1521"; //DB 서버 컴퓨터에 DB 프로그램이 어떤 포트번호로 연결돼있는지
		String dbName = ":xe"; //DB이름 (XE버전)
		String userName = "KH_LIK"; //사용자 계정명
		String pw = "KH1234"; //계정 비밀번호
		
		conn = DriverManager.getConnection(type+host+port+dbName, userName, pw); //이러면 Connection만들어진다 
		
		/*만들어진 커넥션으로 SQL 수행 시 자동 커밋을 비활성화할 거임(connection 만들자 마자 해야함)*/
		//기본적으로 자동 커밋이 활성화돼있음
		conn.setAutoCommit(false);
		
		/* 3. SQL 작성하기*/
		Scanner sc = new Scanner(System.in);
		
		System.out.print("부서코드 입력 : ");
		String deptCode = sc.next();
		
		System.out.print("부서명 입력 : ");
		String deptTitle = sc.next();
		
		System.out.print("지역코드 입력 : ");
		String locationId = sc.next();
		
		String sql = String.format("INSERT INTO DEPARTMENT4 VALUES('%s', '%s', '%s')", 
							deptCode, deptTitle, locationId); //이것도 간단하긴 한데 다른 종류의 statement 사용하면 더 간단하게 할 수 있다
		
		/*sql 준비 끝났으면 sql을 실행할 statement 객체 생성*/
		/* 4. Statement 객체 생성*/
		stmt = conn.createStatement();
		
		/* 5. SQL 수행 후 결과 반환 받기*/
		/*DML 수행 결과는 변화된 행의 수가 정수로 반환된다(몇 행이 추가/제거/변화됐는지)*/
		//이번에는 insert를 수행할 것임!
		//insert, update, delete 묶어서 update로 취급해도 됨
		//executeInsert이런건 없고, executeUpdate로 다 있음 (세 개+ddl 을 다 이걸로 커버)
		int result = stmt.executeUpdate(sql); //결과가 정수로 반환됨  //6번과 비교해보기
		
		/* 6. 수행 결과에 따라 트랜잭션 제어 처리해주기*/
		//여기서는 결과로 result set이 안나오므로 while문 쓸 필요 없음
		if(result>0) {
			System.out.println("삽입 성공!!");
			conn.commit();
		} else {
			System.out.println("삽입 실패");
			conn.rollback();
		}
	}catch(Exception e) {
		e.printStackTrace();
	} finally {
		try {
			/* 7. 사용 완료된 JDBC 객체 반환하기(생성된 역순으로 하는 것을 권장)*/
			// if (rs !=null) rs.close(); 이번에는 resultset이 없으므로 이거 쓰지 말기!!!
			if (stmt !=null) stmt.close();
			if (conn !=null) conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
}
