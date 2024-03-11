package edu.kh.jdbc.run;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import edu.kh.jdbc.common.JDBCTemplate;

public class JDBCRun1 {
public static void main(String[] args) {
	//부서 코드를 입력 받아
	//일치하는 부서의 부서명을 수정하기(update ->result set 필요 없음)
	/* 1. JDBC 객체 참조 변수 선언하기*/
	Connection conn = null;
	PreparedStatement pstmt = null;
	try {
		//catch구문 따로 안씀 --왜?
		/* 2. Connection 객체 얻어오기(생성하기 아님!)*/
		conn=JDBCTemplate.getConnection(); //실행 -> driver.xml 읽어와서 설정해서 리턴해준다 conn을
		
		/* 3. SQL 작성*/
		Scanner sc = new Scanner(System.in);
		
		System.out.print("수정할 부서 코드 : ");
		String deptCode = sc.next();
		
		System.out.print("수정할 부서명 : ");
		String deptTitle = sc.next();
		
		//SQL 만들기
		String sql = "UPDATE DEPARTMENT4 SET DEPT_TITLE=? WHERE DEPT_ID=?";
		
		/* 4. PreparedStatement 객체 생성하기*/
		//PreparedStatement 얘는 만들어지면서 자동으로 sql을 적재시킴
		pstmt = conn.prepareStatement(sql);
		
		/* 5. ?(placeholder)에 알맞은 값 채워넣기*/
		pstmt.setString(1, deptTitle);
		pstmt.setString(2, deptCode);
		
		/* 6. SQL 수행 후 결과 반환 받기*/
		//update는 dml에 속해있는데 수행 결과로 몇 행 수행됐는지 정수가 반환됨
		int result = pstmt.executeUpdate(); //실행 시 수정된 행의 개수가 반환된다(몇 행이 수정됐다)
		
		/* 7. SQL 수행 결과에 따라서 트랜잭션 제어 처리하기*/
		if(result>0) {
			//1행이라도 수정 성공한 것임!
			System.out.println("수정 성공");
			JDBCTemplate.commit(conn);
		} else {
			//부서코드가 일치하는 부서가 없어서 수정 실패한 경우
			System.out.println("부서코드가 일치하는 부서가 없습니다.");
			JDBCTemplate.rollback(conn);
		}
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		/* 8. 사용한 JDBC 객체 자원 반환*/
		JDBCTemplate.close(pstmt); //이 안에 try-catch다 들어있으니까 여기서 그냥 써도 된다
		JDBCTemplate.close(conn); //이 안에 try-catch다 들어있으니까 여기서 그냥 써도 된다
	}
}
}
