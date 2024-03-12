package edu.kh.jdbc.view;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.model.dao.DepartmentDAO;
import edu.kh.jdbc.model.dto.Department;

public class DepartmentView { //데이터 입력/출력만 해줌
//while문으로 무한히 돌리면서 부서 수정/삽입/삭제/조회하기
	private Scanner sc; 
	private DepartmentDAO dao; //객체 선언할 때 굳이 null안써도 되긴 함!
	//아무것도 참조하지 않는 빈칸이어서 항상 참조형인 기본값인 null로 채워짐
	//heap 영역에서는 빈칸으로 존재할 수 없음
	
	//객체의 필드가 초기화되지 않은 경우
	//생성 시 기본 값(null)으로 자동 초기화된다(자바에서의 null ==참조하는 게 없다 !=빈칸)
	//클래스로 객체를 만들면
	//Stack : 호출된 메서드가 쌓임
	//heap : 만들어진 객체가 저장됨 : 
	//Static : static이 붙은 것이 저장되는 메모리
	
	//기본 생성자
	public DepartmentView() {
		sc=new Scanner(System.in);
		dao=new DepartmentDAO();
	}
	public void displayMenu() {
		//메뉴 출력함
		//이걸 Run에서 호출할거임
		int input=0; //입력받은 메뉴 번호를 저장할 변수
		
		do { 
			//일단은 한 번 실행하고 그 후에 조건 따져봄 -> 최소 한바퀴는 돈다
			try {
				System.out.println("\n===== 부서 관리 프로그램 =====\n");
				System.out.println("1. 부서 추가");
				System.out.println("2. 부서 전체 조회"); //1,2번은 DAO로 이미 만들어놨다
				System.out.println("3. 검색어가 부서명에 포함되는 부서 조회"); //검색 기능
				System.out.println("4. 부서 삭제"); //부서코드 입력해서 삭제함
				System.out.println("5. 부서명 변경"); // 부서코드, 변경할 부서명 입력해서 변경
				System.out.println("0. 프로그램 종료"); 
				
				System.out.print("메뉴 선택 >>");
				input=sc.nextInt();
				switch(input) {
				case 1 : insertDepartment(); break; //이 메서드를 호출할거다 //제일 밑에 만들기
				case 2 : selectAll(); break; //이 이름의 메서드를 밑에 만든다
				case 3 : selectDepartmentTitle(); break; //부서 이름으로 조회하는 것 해보기
				case 4 : deleteDepartment(); break;
				case 5 : updateDepartment(); break;
				case 0 : System.out.println("\n[프로그램 종료]\n"); break;
				default : System.out.println("\n[메뉴에 존재하는 번호만 입력하세요]\n");
				}
			}catch(InputMismatchException e) {
				System.out.println("\n[입력 형식이 잘못되었습니다]\n");
				input=-1; //처음부터 오류 발생하면 input이 -1이 돼서(0이 아닌 값으로 만들기) while문 실행되지 않음
				sc.nextLine(); //잘못 입력한 값이 스캐너 버퍼에 남아있어서, 그 잘못된 값 비우기
				//숫자 입력했는데 다른 것 입력한 경우 발생
				//InputMismatchException : Scanner를 이용한 입력 시 자료형이 잘못되면 발생하는 예외
			}catch(Exception e) {
				e.printStackTrace();
			}
		}while(input !=0); 
		
	}
	private void insertDepartment() throws SQLException {
		//private : 같은 클래스 안에서만 접근 가능 -> 외부에서는 이 메서드 호출 불가(이 클래스에서만 호출 가능)
		System.out.println("\n----- 부서 추가 -----\n");
		
		System.out.println("부서 코드(deptId) 입력 : ");
		String deptId = sc.next();
		
		System.out.println("부서명 (deptTitle) 입력 : ");
		String deptTitle = sc.next();
		
		System.out.println("지역 코드(locationId) 입력 : ");
		String locationId = sc.next();
		
		//입력 받은 값을 한 번에 저장할 Department 객체를 생성하기
		Department dept = new Department(deptId, deptTitle, locationId); //생성되는 객체의 필드가 전달받은 값으로 초기화되는 생성자 코드가 실행됨
		
		//DAO 메서드 호출 후 결과 반환 받기(insert인 dml을 수행하면 수행 결과로 정수(몇 행이 변화됐는지)가 나옴)
		//결과 == 삽입/삭제/교체 성공한 행의 개수(int)
		int result = dao.insertDepartment(dept); //또 던지기 ->이걸 호출했던 display로 던짐 ->display에 catch문이 돼있어서 모든 예외를 저기서 처리
		//예외처리 두 방법 : throw / try-catch
		if(result>0) {
			//삽입 성공!
			System.out.println("\n[삽입 성공]\n");
		} else System.out.println("\n[삽입 실패]\n");
	}
	/**부서 전체 조회
	 * @throws SQLException
	 */
	private void selectAll() throws SQLException{
		System.out.println("\n-----[부서 전체 조회]-----");
		//리스트로 받아오기
		List<Department> deptList = dao.selectAll(); 
		//테이블 다 조회해서 1행씩 접근해서 deptList에 담아서 그걸 리턴함->호출 시 그 리스트가 오므로 그걸 출력하면 된다
		for(Department dept : deptList) {
			System.out.println(dept); //참조변수명만 쓰면 자동으로 dept.toString() 메서드가 호출돼서 참조변수만 써도 된다
		}
	}
	/**검색어가 부서명에 포함되는 부서 조회
	 * @throws SQLException
	 */
	private void selectDepartmentTitle() throws SQLException{
		System.out.println("\n-----[검색어가 부서명에 포함되는 부서 조회]-----\n");
		//검색할 부서명 입력받기
		System.out.print("검색할 부서명 : ");
		String title=sc.next();
		
		List<Department> deptList = dao.selectDepartmentTitle(title); //title 전달할거고 결과로 리스트 받아올거다
		//입력 받은 값을 DAO에 넘겨야 함 그러면 DAO가 그걸로 SQL 실행해서 View로 돌려줌 ->View가 출력?
		//하나하나의 행이 Department에 저장되고, 여러 행이므로 리스트로 받음
		
		//deptList : DAO에서 반환받은 결과
		
		if(deptList.isEmpty()) {
		//deptList가 비어 있는 경우
			//==검색 결과가 없다
			System.out.println(title+"이 포함된 부서가 없습니다.");
		}else {
			//검색 결과가 존재하는 경우 ->향상된 for문으로 하나씩 출력
			for(Department dept : deptList) {
				System.out.println(dept);
			}
		}
	}
	private void deleteDepartment() throws SQLException{
		System.out.println("\n----- 부서 삭제 -----\n");
		
		System.out.println("삭제할 부서 코드(deptId) 입력 : ");
		String deptId = sc.next();
		
		//입력 받은 deptId를 가지고 있는 부서가 있는 지 검사해서
		//->있을 경우 "정말 삭제하시겠습니까?"로 한번 더 물어보기
		//->없을 경우 "부서코드가 일치하는 부서가 존재하지 않습니다" 출력하기
		int check = dao.checkDepartment(deptId); //있으면 1, 없으면 0을 반환받을거다 (count(*))
		if(check==0) {
			//부서가 존재하지 않은 경우
			System.out.println("부서코드가 일치하는 부서가 존재하지 않습니다");
			return; //더 할 거 없으니까 여기서 메서드 종료하겠다
		}
		//위의 if문 실행 안돼서 여기까지 오면 부서가 존재하는거임
		System.out.println("정말 삭제하시겠습니까?(Y/N)");
		char ch =sc.next().toUpperCase().charAt(0);
		if(ch=='Y') {
			//deptId 가 존재한다는 것을 앞에서 검사했기 때문에 result>0 이런거 또 검사 안해도 된다
		//executeUpdate() 수행 시 정수가 반환돼서 옴
			int result = dao.deleteDepartment(deptId);
			System.out.println("[삭제 되었습니다]");
		} else {
			//삭제 안할래 하고 마음 바꾼 경우
			System.out.println("[삭제 취소]");
		}
	}
	private void updateDepartment() throws SQLException{ 
System.out.println("\n----- 부서명 수정하기 -----\n");
		
		System.out.println("수정할 부서 코드(deptId) 입력 : ");
		String deptId = sc.next();
		//입력받은 부서가 있는지 검사하기 --위에서 한 코드 재활용하기
		int check = dao.checkDepartment(deptId); //있으면 1, 없으면 0을 반환받을거다 (count(*))
		if(check==0) {
			//부서가 존재하지 않은 경우
			System.out.println("부서코드가 일치하는 부서가 존재하지 않습니다");
			return; //더 할 거 없으니까 여기서 메서드 종료하겠다
		}
		
		//여기 넘어왔으면 제대로 입력한 것임!
		System.out.println("수정할 부서명 (deptTitle) 입력 : ");
		String deptTitle = sc.next();
		
		//수정 DAO 메서드 호출 후 결과 반환 받기 ->DML은 수행 결과로 삽입/수정/삭제된 행의 개수인 int 형이 반환됨
		int result = dao.updateDepartment(deptId,deptTitle);
		if(result>0) {
			//변경 성공!
			System.out.println("\n[수정 성공]\n");
		} else System.out.println("\n[수정 실패]\n"); //실패할 일 없긴 한데 
	}
}
