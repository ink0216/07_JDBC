package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

//왜 자바와 db를 굳이 왜 연결해서 할까?
//일반 사용자는 sql 못쓰니까 급여만 입력하면 원하는 결과가 db로부터 나오도록 실행되게 하려고

//Statement - SELECT / DML
//PreparedStatement : Statement의 자식 객체 - SELECT /DML
public class JDBCExample2 {
public static void main(String[] args) {
	//입력 받는 급여보다 많이 받는 사원의
	//사번, 이름, 급여, 직급명 조회하기
	
	/* 1. JDBC 객체 참조 변수 3개 선언하기*/
	Connection conn = null; //db 연결 정보를 담고 있는 객체(어떤 ip, 어떤 포트번호에 접속해서 실행할 지)
	Statement stmt = null; // SQL을 db로 전달해서 SQL 수행 후 결과를 반환 받는 객체
	ResultSet rs = null; //db로부터 받아온 SELECT 결과를 저장하는 객체
	
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
		
		/* 3. SQL 작성하기(dbeaver에서)*/
		//입력 받는 급여보다 많이 받는 사원의
		//사번, 이름, 급여, 직급명 조회하기
		
		Scanner sc = new Scanner(System.in);
		System.out.print("검색할 급여 입력 : ");
		int input = sc.nextInt(); //입력받기
		System.out.println("-----------------------------------------------------------------------");
		
		String sql="SELECT EMP_ID, EMP_NAME, SALARY, JOB_NAME\r\n"
				+ "	FROM EMPLOYEE \r\n"
				+ "	JOIN JOB USING(JOB_CODE)\r\n"
				+ "	WHERE SALARY >="+input //input값에 따라 sql이 실시간으로 바뀜
				+ "	ORDER BY SALARY DESC";
		// \r : 캐리지 리턴(글씨 쓰는 곳을 맨 앞으로 옮긴다)
		// \n : new line
		//근데 \r\n 얘네 없어도 되고 있어도 되는데, 없애는 경우, 앞의 문자열 맨 뒤에 띄어쓰기 하나씩 써줘야 한다
		
		/* 4. Statement 객체 생성하기*/
		stmt = conn.createStatement(); //Connection을 이용해서 생성하기
		
		/* 5. Statement 객체를 이용해서 SQL 수행 후 결과 반환받기*/
		rs = stmt.executeQuery(sql); //쿼리를 수행해서 반환받은 ResultSet를 저장하기
		//SELECT 수행 시 RESULT SET이 반환됨!!!!
		
		/* 6. ResultSet 객체를 1행 씩 접근하며 컬럼 값 얻어오기*/
		/* 조회 결과가 있든 없든 매 행마다 접근하면서 출력하기*/
		while(rs.next()) {
			//rs.next() : 해당 행으로 이동해서 해당 행이 있으면 true, 없으면 false를 반환
			
			//200 선동일 8000000 대표 로 얻어와지는데
			//하나씩 골라서 저장
			String empId = rs.getString("EMP_ID");
			String empName = rs.getString("EMP_NAME");
			int salary = rs.getInt("SALARY");
			String jobName = rs.getString("JOB_NAME");
			System.out.printf("%s / %s / %d / %s \n",
					empId, empName, salary, jobName);
		}
	}catch(Exception e) {
		//얘가 SQLException, ClassNotFoundException 을 다 잡아서 한 번에 처리함
		//다형성 적용
		e.printStackTrace();
	} finally {
		try {
			/* 7. 사용 완료된 JDBC 객체 반환하기(생성된 역순으로 하는 것을 권장)*/
			if (rs !=null) rs.close();
			if (stmt !=null) stmt.close();
			if (conn !=null) conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
}
