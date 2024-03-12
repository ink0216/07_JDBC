package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//자바 실행 : ctrl + f11
//dbeaver 실행 : ctrl + enter
public class JDBCExample1 {
	public static void main(String [] args) {
		//매개변수로는 스트링이 전달돼오는 메인메서드
		
		//ojdbc10 압축파일(라이브러리) 연결
		/*JDBC(Java Database Connectivity)
		 * - Java에서 DB에 접근할 수 있게 해주는
		 * 		Java 제공 코드(class, interface)
		 * 		=>java.sql 패키지에 존재
		 * */
		
		//EMPLOYEE 테이블에서
		//사번, 이름, 부서코드, 직급코드, 급여,입사일 조회
		//-->이클립스 콘솔에 출력
		//자바로 연결해서 가져와서 여기서 볼거다
		//외부 프로그램과 연결 시 많은 예외 발생할 수 있어서
		//TRY-CATCH구문 씀
		//------------------------------------------------------------------
		/*1. JDBC 객체 참조 변수 3개 선언하기 (java.sql것 import하기!)*/
		//Connection ->db와 연결
		// - 특정 DB와의 연결 정보를 저장한 객체
		//dbeaver의 계정 하나하나가 Connection임 
		//->dbeaver에서 DB 연결을 위해 주소,계정,비밀번호를 저장한 것과 같음 
		Connection conn = null; //DBeaver에서 각각의 계정과 같음
		//dbeaver 계정 클릭 후 f4 누른 창에 적었던 정보 다 conn 객체에 적을 것임
		//------------------------------------------------------------------
		//Statement 
		// - SQL을 String 형태로 DB에 전달하고
		//		결과를 받아오는 객체
		//		이걸 이용해서 보내고 받음
		Statement stmt = null;
		//------------------------------------------------------------------
		//select -> result set 나옴
		//insert, update, delete -> 행의 개수인 int형이 나옴(몇 행이 변했는지)
		//자바에서 result set을 인지하기 위한 객체
		//ResultSet -> 결과 받아온 것을 받아올거다
		// - SELECT 결과를 저장하는 객체
		//		(조회 결과는 0행 이상임)
		//		(java든 db이든 테이블을 커서를 이용해 1행 씩만 접근 가능하다)
		//		(1행씩 순차접근하는거임)
		ResultSet rs = null;
		try {
			/* 2. DriverManager 객체를 이용해서 Connection 생성하기*/
			
			/* 2-1. Oracle JDBC Driver 객체를 메모리에 로드(적재)하기*/
			//객체는 보통 new 연산자 만들지만
			//jdk가 개발자 말고 자기 혼자 만들 때에는 new안쓰고 다양한 방법 존재
			Class.forName("oracle.jdbc.driver.OracleDriver"); //->읽으면 그대로 메모리에 올라간다
			//->다 읽어서 적재를 하면 작동 가능한 객체가 된다! (개발자가 쓰는 객체가 아닌, jdk가 혼자 쓰는 객체)
			//Class.forName("클래스명") : 해당 클래스를 읽어 메모리에 적재함
			//Class : 우리가 아는 클래스가 아닌, 자바의 객체
			//OracleDriver : Java - Oracle 연결 시 필요한 코드가 담긴 클래스
			//얘가 있어야지 얘를 이용해서 Java와 Oracle을 연결할 수 있다
			
			/* 2-2. DB 연결 정보를 이용해서 Connection 객체 생성하기*/
			//DB에 연결할 정보 쭉 입력
			String type = "jdbc:oracle:thin:@"; //type이라는 변수, 드라이버 종류를 어떤 것을 쓸 지
			//dbeaver 계정에서 f4->custom들어가면 나오는 문자열
			//@까지 붙여넣기
			//드라이버 종류 엄청 많음
			String host = "localhost"; //DB 서버 컴퓨터의 IP주소 작성
			String port = ":1521"; //DB 서버 컴퓨터에 DB 프로그램이 어떤 포트번호로 연결돼있는지
			String dbName = ":xe"; //DB이름 (XE버전)
			String userName = "KH_LIK"; //사용자 계정명
			String pw = "KH1234"; //계정 비밀번호
			
			
			//DriverManager : Connection(db를 연결하는 정보 다 담고있는 객체)을 만드는 객체
			conn = DriverManager.getConnection(type+host+port+dbName, userName, pw); //dbeaver에서 본 문자열과 비슷해짐
					//new로 만들지 않음
			
			//System.out.println(conn); //중간 확인
			//여기에 이상한 문자열 나오면 성공한거임! == db와 연결 완료!
			
			/* 3. SQL 작성하기*/
			//EMPLOYEE 테이블에서
			//사번, 이름, 부서코드, 직급코드, 급여,입사일 조회
			//-->이클립스 콘솔에 출력
			//자바로 연결해서 가져와서 여기서 볼거다
			String sql = "SELECT EMP_ID, EMP_NAME, DEPT_CODE, JOB_CODE, SALARY, HIRE_DATE " //띄어쓰기 있어야 함!(이어쓰기여서)
									+"FROM EMPLOYEE";
			/* **** JDBC에서 SQL작성 시, SQL 마지막에 ; 을 작성하면 안된다!!!!! *****/
			//			->작성한다면 "SQL 명령어가 올바르게 종료되지 않았습니다"라는 오류 발생함
			
			/* 4. Statement 객체 생성하기*/
			stmt = conn.createStatement(); //conn 에는 연결된 db정보가 담겨 있어서 여기를 왔다갔다 하는 stmt 보냄
			//Connection에 연결된 DB로 SQL을 전달하고 결과를 받아오는 Statement 객체 생성함(왔다갔다 하도록)
			
			/* 5. Statement 객체를 이용해서 SQL 수행 후 결과 반환받기*/
			rs =stmt.executeQuery(sql); //반환된 것 저장 //6번과 비교해보기
			//Query를 실행하겠다 //매개변수로 우리가 만들어놓은 sql넣기
			//executeQuery - >resultset이 반환됨
			
			/* 6. 조회 결과가 담겨 있는 ResultSet을 
			 * 		커서(->)를 이용해 1행 씩 접근하며
			 * 		각 행의 컬럼 값 얻어오기 */
			
			//몇 행까지 반복할 지 모르니까 while문 사용
			while(rs.next()) {
				// rs.next() : 커서를 한 행씩 이동시켜 해당 행이 있으면 true, 없으면 false를 반환
				
				//한 행 얻어오면 다음과 같이 나옴
				// 200	선동일	D9	J1	8000000	1990-02-06 00:00:00.000
				// 이 중 특정 값만 얻어오는 방법
				//rs.get자료형(컬럼명||순서) : 현재 접근한 행의 컬럼 값 얻어오기
				
				//자료형
				//[java]												[db]
				//String										CHAR, VARCHAR2
				//int, long 								NUMBER(정수만 저장된 컬럼)
				//float, double							Number(정수 + 실수)
				//java.sql.Date(객체)  			DATE
				
				String empId = rs.getString("EMP_ID"); //아이디를 얻어올 건데 자바에서는 String이다
				String empName = rs.getString("EMP_NAME");
				String deptCode = rs.getString("DEPT_CODE");
				String jobCode = rs.getString("JOB_CODE");
				int salary = rs.getInt("SALARY");
				Date hireDate = rs.getDate("HIRE_DATE"); //Date는 java.sql 것 import해야 함
				
				System.out.printf("사번 : %s / 이름 : %s / "
						+ "부서코드 : %s / 직급코드 : %s /  "
						+ "급여 : %d / 입사일 : %s \n",
					empId, empName, deptCode, jobCode, salary, hireDate.toString());
				//date타입은 string으로 바꿔서 출력
			}
		} catch(SQLException e) {
			//SQLException : JDBC 관련  예외의 최상위 부모
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			//ClassNotFoundException : 클래스가 존재하지 않으면 발생하는 예외
			// ->추가한 라이브러리가 없거나, 클래스 경로를 잘못 작성하는 경우 발생
			e.printStackTrace();
		} finally { //io했을 때도 했었는데, 외부 연결된 것은 모두 사용 후 반환해주어야 한다
		//java와 db를 연결할 때마다 connection이 연결됨 //자바는 끝나도 외부의 connection은 남아있음 
			//db는 연결할 수 있는 connection수가 정해져있음
			//->connection다 썼으면 없애줘야함
			
			/* 7. 사용 완료된 JDBC 객체 자원 반환*/
			/* 만들어진 역순으로 반환하는 것을 권장!!!!! */
			//커넥션 안에서 sql담고 왔다갔다 하는 애 : Statement 객체
			//	-> 결과를 ResultSet으로 담아옴
			// ResultSet -> Statement -> Connection 순서로 닫아주기!!(만들어진 역순으로)
			try {
				if(rs !=null) rs.close(); //있을 때에만 닫겠다
				if(stmt !=null) stmt.close();
				if(conn !=null) conn.close();
				//안닫아주면 언젠가는 db에 오류남
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
	}
}
