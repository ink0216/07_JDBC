package edu.kh.jdbc.run;

import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.model.dao.DepartmentDAO;
import edu.kh.jdbc.model.dto.Department;

public class JDBCRun2 {
public static void main(String[] args) {
	//insert하자마자 select해서 결과로 보기
	//한 번에 두 개 이상의 SQL을 수행할 수 있다(추가되면 바로 전체가 조회됨)
	//클래스 분리
	
	//DAO 객체 만들기
	DepartmentDAO dao = new DepartmentDAO(); //객체 만듦
	try {
		//1. 부서 추가하기
		Scanner sc = new Scanner(System.in);
		
		System.out.print("부서 코드 입력 :");
		String deptId = sc.next();
		
		System.out.print("부서명 입력 :");
		String deptTitle = sc.next();
		
		System.out.print("지역 코드 입력 :");
		String locationId = sc.next();
		
		//** DAO 메서드 호출 후 결과 반환 받기 **
		Department dept=new Department(deptId, deptTitle, locationId);//위에서 입력받은 값 세개를 이용해서 Department객체를 만든다
		//DAO에게 묶어서 보내야해서 Department 객체로 만들었다
		
		//DB에 삽입 후 결과를 반환받아오기
		int result = dao.insertDepartment(dept);
		
		if(result>0) {
			System.out.println("[삽입 성공]");
		//2. 부서 전체 조회하기(성공했을 때에만 부서 전체 조회하는 코드 실행)
			List<Department> deptList = dao.selectAll();
			for(Department d : deptList) {
				System.out.println(d.toString());
			}
		} else {
			System.out.println("[삽입 실패]");
		}
		
	} catch(Exception e) {
		e.printStackTrace();
	}
	
	
	
}
}
