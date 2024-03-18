package edu.kh.budget.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	/*Template : 주형, 양식, 본뜨기 위한 틀
	 * JDBCTemplate : JDBC 관련 작업을 위한 코드를 제공하는 클래스(실제로 존재함!)
	 * 			-->여기에 JDBC 관련된 공통 코드를 미리 작성해둘 예정
	 * 뭐가 있을까
	 * - Connection 생성하는 코드(+자동 커밋 false하는 코드)
	 * - commit / rollback 하는 것도 예외 처리가 필요해서 여기서 제어해주기
	 * - JDBC 객체 자원 반환 구문 close()
	 * ** 어디서든지 별도의 객체 생성 없이 사용할 수 있게
	 * 		모든 메서드는 public static(static 영역에 만들어짐)으로 생성하기*/
		
		//필드
		private static Connection conn=null; 
		//->필드는 왜 static?
		//	static 메서드가 참조 가능한 필드는 static 필드밖에 없기 때문에!!
		
		//메서드
		/**호출 시 연결 정보가 담긴 새로운 Connection 객체를 생성해서 반환해줌
		 * @return conn
		 */
		public static Connection getConnection() {//이 메서드 수행하면 Connection이 반환됨
			//왜 그냥 내보내면 안되고 필드에 담아서 반환해야할까?
			try {
				if(conn ==null || conn.isClosed()) {
					//이전에 connection이 없었거나 connection이 닫혀있는 상태인 경우
					//==>새 Connection 생성하기
					
					//새 connection에 필요한 정보가 driver.xml에 담겨있음
					//그 내용을 읽어올 때 Properties가 필요
					//Properties : key와 value가 모두 String인 맵
					//					- 파일 입출력하기에 편리한 기능을 제공해줌
					Properties prop = new Properties();
					//---------------------------------------------------
					//driver.xml의 경로 찾는 방법!!!!
					//컴파일된 driver.xml 파일의 위치를 얻어오는 코드!!!!
					//패키지를 폴더 형식으로 작성
					String path = JDBCTemplate.class.getResource("/edu/kh/budget/sql/driver.xml").getPath();//******여기 패키지명수정
							//JDBCTemplate를 컴파일하면 JDBCTemplate.class 이렇게 됨
							//2진법으로 된 것의 자원을 얻어오겠다
							//xml 파일까지의 경로를 얻어오겠다
							//driver.xml은 저 폴더에 있어야 한다
					prop.loadFromXML(new FileInputStream(path)); //이러면 driver.xml을 읽어올 수 있다
					//프로젝트 최상단 폴더에 존재하는
					//driver.xml 파일을 읽어와 Properties 객체에 저장하겠다/적재하겠다
					//entry
					//load : 가져와서 싣다/적재하다
					//xml것을 읽어와서 여기에 적재할거다
					
					//prop에 저장된 값 얻어오기
					String driver = prop.getProperty("driver"); //oracle.jdbc.driver.OracleDriver가 담아져서 옴
					String url = prop.getProperty("url"); //jdbc:oracle:thin:@localhost:1521:xe
					String user = prop.getProperty("user"); // KH_LIK
					String pw = prop.getProperty("pw");//KH1234
					//입출력을 쉽게 해주는 Properties를 이용해서 입출력
					
					//오라클 드라이버 클래스를 읽어와 메모리에 적재하기
					Class.forName(driver);
					
					//커넥션 객체 생성하기
					conn = DriverManager.getConnection(url, user, pw);
					
					//자동 커밋 false 설정
					conn.setAutoCommit(false);
					
					//이걸 사용하면 커넥션이 여러개 생성되지 않고 하나만 생성돼서 메모리 효율을 좋게 할 수 있다
					//이전에 close하지 않았을 경우 새로 만들지 않고 그것을 다시 사용
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return conn; //필드에 담아서 반환
			//static이 붙으면 프로그램 시작 시 생성되고, static이 없으면 인스턴스가 생성될 때 생성됨
			//static 유무에 따라 해석 순서에 오류가 발생해서 둘다 static을 붙여서 같은 순서로 해석됨(둘 다 static붙으면 위에서 아래로 해석돼서 문제 없음)
		}
		//---------------------------------------------------------------------------------------------------------------------------------------------
		/*트랜잭션 제어 코드(커밋, 롤백)*/
		public static void commit(Connection conn) {
			//매개변수로 Connection을 받음
			try {
				if(conn !=null && !conn.isClosed()) conn.commit();  //열려있는 경우
				//전달받은 Connection을 commit해줄거임 ->근데 예외처리를 해줘야함(외부에 있는 프로그램과 연결하기 때문에)
			} catch (SQLException e) {
				e.printStackTrace();
			}
			} 
			public static void rollback(Connection conn) {
				//매개변수로 Connection을 받음
				try {
					if(conn !=null && !conn.isClosed()) conn.rollback();  //열려있는 경우
					//전달받은 Connection을 rollback해줄거임 ->근데 예외처리를 해줘야함(외부에 있는 프로그램과 연결하기 때문에)
				} catch (SQLException e) {
					e.printStackTrace();
				} 
		}
		//---------------------------------------------------------------------------------------------------------------------------------------------
			/*JDBC 객체 자원 반환 구문(close())*/
			//이름이 똑같이 close인 것 세개 있다 ==>오버로딩(매개변수가 달라서 가능)
			public static void close(Connection conn) { //conn를 닫는 것
				try {
				if(conn !=null && !conn.isClosed())	conn.close(); //참조하는 커넥션이 있고, 닫혀있지 않은 경우
				//매개변수로 받아서 닫아주기 ->예외 처리해주기
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
			
			public static void close(Statement stmt) {//stmt를 닫는 것
				//얘는 매개변수로 Statement, PreparedStatement 두 객체를 모두 매개변수로 받아서 둘다 close처리할 수 있음
				//둘이 상속 관계이기 때문에 다형성 적용
				try {
					if(stmt !=null && !stmt.isClosed())	stmt.close(); //참조하는 stmt가 있고, 닫혀있지 않은 경우
					//매개변수로 받아서 닫아주기 ->예외 처리해주기
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
			
			public static void close(ResultSet rs) { //rs를 닫는 것
				//얘는 매개변수로 Statement, PreparedStatement 두 객체를 모두 매개변수로 받아서 둘다 close처리할 수 있음
				//둘이 상속 관계이기 때문에 다형성 적용
				try {
					if(rs !=null && !rs.isClosed())	rs.close(); //참조하는 stmt가 있고, 닫혀있지 않은 경우
					//매개변수로 받아서 닫아주기 ->예외 처리해주기
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
}
