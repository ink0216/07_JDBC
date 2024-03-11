package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample6 {
public static void main(String[] args) {
	/* 1. JDBC 객체 참조 변수 선언*/
	Connection conn = null;
	//Statement stmt = null; ->이거 대신!!
	//PreparedStatement : 준비된 statement
	//	->외부 변수 값을 SQL에 받아들일 준비가 되어있는 Statement
	//		입력 값이 들어갈 빈칸을 PreparedStatement에 적음
	//	->성능, 속도 면에서 우위를 가지고 있음
	//	- 빈칸을 나타낼 때 ?(placeholder, 빈칸인데 무엇을 써야 할 지 지정하는 것)를 사용(html에서 쓰던 것)
	//		변수/값을 위치 시킬 자리를 지정
	//	-> placeholder를 이용한다!!!!
	PreparedStatement pstmt = null; //참조 변수 
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
		//이 위치의 db랑 연결할거야
		conn = DriverManager.getConnection(type+host+port+dbName, userName, pw); //이러면 Connection만들어진다 
		
		/*만들어진 커넥션으로 SQL 수행 시 자동 커밋을 비활성화할 거임(connection 만들자 마자 해야함)*/
		//기본적으로 자동 커밋이 활성화돼있음
		conn.setAutoCommit(false);
		//오토커밋 안할거야
		/* 3. SQL 작성하기*/
		Scanner sc = new Scanner(System.in);
		
		System.out.print("부서코드 입력 : ");
		String deptCode = sc.next();
		
		System.out.print("부서명 입력 : ");
		String deptTitle = sc.next();
		
		System.out.print("지역코드 입력 : ");
		String locationId = sc.next();
		
		//?(placeholder)가 추가된 SQL 작성
		//?가 빈칸 하나하나임
		String sql = "INSERT INTO DEPARTMENT4 VALUES(?, ?, ?)";
		
		/*sql 준비 끝났으면 sql을 실행할 statement 객체 생성*/
		/* 4. PreparedStatement 객체 생성*/
		pstmt = conn.prepareStatement(sql); //지금은 빈칸에 담아야 함
		//자바와 db를 연결해주는 Connection 객체
		//Connection 안에다가 PreparedStatement 를 만듦
		
		/*Statement 때에는 매개변수가 필요 없었는데,
		 * sql을 객체 생성 시 전달하여 ?에 값을 대입할 준비를 해놓는다*/
		
		/* 5. PreparedStatement 객체의 ? 부분에 알맞은 값을 대입하기*/
		//pstmt.set자료형(?순서, 값) : 몇 번째 ?에 이 값 세팅할거야
		//	->해당 구문으로 세팅되는 값은
		//		자동으로 양쪽에 ''(홑따옴표)가 추가된다!!!!!!(장점) (db가 인식할 수 있도록 자동으로 추가해줌)
		pstmt.setString(1,deptCode); //첫번째 한테는 deptCode를 집어넣겠다
		pstmt.setString(2,deptTitle); //두번째 한테는 deptTitle를 집어넣겠다
		pstmt.setString(3,locationId); //세번째 한테는 locationId를 집어넣겠다
		
		/* 6. SQL 수행 후 결과 반환 받기*/
		/*DML 수행 결과는 변화된 행의 수가 정수로 반환된다(몇 행이 추가/제거/변화됐는지)*/
		//이번에는 insert를 수행할 것임!
		//insert, update, delete 묶어서 update로 취급해도 됨
		//executeInsert이런건 없고, executeUpdate로 다 있음 (세 개+ddl 을 다 이걸로 커버)
		//int result = pstmt.executeUpdate(sql); //결과가 정수로 반환됨 
		int result = pstmt.executeUpdate(); //결과가 정수로 반환됨 
		//pstmt에는 생성시부터 sql을 받았기 때문에 여기서 또 넣어주면 안된다
		//빈칸에 위에서 값 넣었는데 sql을 또 적으면 다시 빈칸으로 돌아가므로 매개변수를 넣으면 안된다
		// -> PreparedStatement SQL 수행 시
		//		메서드 매개변수로 SQL을 전달하면 안된다!!!!!
		/* 7. 수행 결과에 따라 트랜잭션 제어 처리해주기*/
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
			/* 8. 사용 완료된 JDBC 객체 반환하기(생성된 역순으로 하는 것을 권장)*/
			// if (rs !=null) rs.close(); 이번에는 resultset이 없으므로 이거 쓰지 말기!!!
			if (pstmt !=null) pstmt.close();
			if (conn !=null) conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
}
