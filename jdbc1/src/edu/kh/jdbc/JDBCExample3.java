package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {
public static void main(String[] args) {
	// 입력 받은 최소 급여 보다 많이 받고(이상)
	// 입력 받은 최고 급여보단 적게 받는(이하)
	// 사원의  사번, 이름, 급여를 급여 내림차순 조회
	
	// [실행화면]
	// 최소 급여 : 1000000
	// 최대 급여 : 3000000
	
	// (사번) / (이름) / (급여)
	// (사번) / (이름) / (급여)
	// (사번) / (이름) / (급여)
	
	/* 1. JDBC 객체 참조 변수 3개 선언하기 */
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
		
		/* 3. SQL 작성하기(dbeaver에서)*/
		// 입력 받은 최소 급여 보다 많이 받고(이상)
		// 입력 받은 최고 급여보단 적게 받는(이하)
		
		Scanner sc = new Scanner(System.in);
		System.out.print("최소 급여 : ");
		int input1 = sc.nextInt(); //입력받기
		
		System.out.print("최대 급여 : ");
		int input2 = sc.nextInt(); //입력받기
		System.out.println("-----------------------------------------------------------------------");
		
		String sql="SELECT EMP_ID, EMP_NAME, SALARY\r\n"
				+ "	FROM EMPLOYEE \r\n"
				+ "	JOIN JOB USING(JOB_CODE)\r\n"
				+ "	WHERE SALARY BETWEEN "+input1 +" AND "+input2
				+ "	ORDER BY SALARY DESC";
		
		/* 4. Statement 객체 생성하기*/
		stmt = conn.createStatement(); //Connection을 이용해서 생성하기
		
		/* 5. Statement 객체를 이용해서 SQL 수행 후 결과 반환받기*/
		rs = stmt.executeQuery(sql); //6번과 비교해보기
		
		/* 6. ResultSet 객체를 1행 씩 접근하며 컬럼 값 얻어오기*/
		while(rs.next()) {
			//rs.next() : 해당 행으로 이동해서 해당 행이 있으면 true, 없으면 false를 반환
			
			//200 선동일 8000000 대표 로 얻어와지는데
			//하나씩 골라서 저장
			String empId = rs.getString("EMP_ID");
			String empName = rs.getString("EMP_NAME");
			int salary = rs.getInt("SALARY");
			System.out.printf("%s / %s / %d  \n",
					empId, empName, salary);
		}
	}catch(Exception e){
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
