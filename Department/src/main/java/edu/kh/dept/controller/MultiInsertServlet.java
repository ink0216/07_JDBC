package edu.kh.dept.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.kh.dept.model.dto.Department;
import edu.kh.dept.model.exception.DepartmentInsertException;
import edu.kh.dept.model.service.DepartmentService;
import edu.kh.dept.model.service.DepartmentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/department/multiInsert")
public class MultiInsertServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//다중 insert하는 서비스 호출할거임!
			DepartmentService service = new DepartmentServiceImpl(); //서비스 객체 생성(인터페이스로는 객체 생성 안돼서 그 상속받은 자식 객체로 만듦)
			
			//전달받은 파라미터 얻어오기
			//	->name 속성 값이 같은 파라미터가 2개 이상 전달된 경우
			//String[]	req.getParameterValues(name속성값)를 이용하면 된다(String 배열로 반환됨)
			String[] deptIdArr = req.getParameterValues("deptId"); //부서 코드 빈칸에 입력된 1,2,3번째 값이 이 배열의 0,1,2번째 인덱스에 저장돼 반환됨
			//deptId, deptTitle, locationId로 각각 name값 같은 게 들어옴
			String[] deptTitleArr = req.getParameterValues("deptTitle"); 
			String[] locationIdArr = req.getParameterValues("locationId"); 
			//길이가 같은 배열 세개 ->같은 인덱스들이 한 행을 구성
			//각각의 0번 인덱스들이 제출된 것들의 첫 번재 행이 되고
			//각각의 1번 인덱스들이 제출된 것들의 두 번재 행이 되고
			
			//0번끼리 묶는 등 같은 인덱스 데이터를 Department 객체로 만들어서 List 형태로 저장하는 가공하기
			//Department : 값 저장용 객체
			List<Department> deptList = new ArrayList<Department>(); //리스트인 인터페이스로는 객체 못만들어서 그걸 상속받은 ArrayList로 만들기
			
			for(int i=0;i<deptIdArr.length;i++) {
				//deptIdArr이든 deptTitleArr이든 locationIdArr이든 길이 다 같아서 아무거나로 해도 됨
				deptList.add(new Department(deptIdArr[i], deptTitleArr[i], locationIdArr[i])); //객체를 하나 만들어서 
				//입력 추가 버튼 눌러서 제출한 횟수만큼 저장함
			}
			//이걸 서비스에 넘겨서 db에 insert하기
			//서비스 메서드 호출 후 결과(삽입된 행의 개수) 반환 받기
			int result = service.multiInsert(deptList); //deptList를 전달해주기 
			//성공,실패,예외 -> 세 가지 경우 존재 
			
			String message = null;
			HttpSession session = req.getSession(); //세션을 이용해서 메시지 전달할거임
			//성공이나 실패 시 redirect를 할 것이기 때문에 세션에 메시지 저장해서 보내기!(request에 저장하면 안됨)
			//성공 
			if(result ==deptList.size()) { //성공
				message=result+"개의 부서가 추가 되었습니다";
			} else {
				//실패(있을 수 없는 경우이긴 한데)
				message="부서 추가 실패...";
			}
			session.setAttribute("message", message); //필드에 변수 만들어서 그 변수에 값을 집어넣겠다
			
			//redirect : 재요청(여기서 별도의 응답 화면을 보여주는 것이 아닌, 다른 페이지를 다시 요청해서 걔가 보여줄거야)
			//resp 객체 이용!
			//resp.sendRedirect("/department/selectAll"); 이렇게 절대경로 방식으로 해도 되고
			resp.sendRedirect("selectAll"); //이렇게 현재 주소(/department/multiInsert)를 기준으로 맨 끝부분만 변하는 상대경로로 해도 됨
			//현재 주소의 맨 뒷부분이 selectAll로 바뀜
			
		}catch(DepartmentInsertException e) {
			//PK 제약조건 위배만 고려하기로 했기 때문에 PK 제약조건 위배인 경우임
			req.setAttribute("errorMessage", e.getMessage()); 
			//req에 변수를 추가하는데 에러메시지라는 변수에 
			//예외 강제로 발생시켜서 던질 때 쓴 메시지가 e.getMessage()가 됨
			
			String path = "/WEB-INF/views/error.jsp";
			
			//forward : 요청 위임(대신 응답 화면 만들어줘 하고 일 떠넘김)
			req.getRequestDispatcher(path).forward(req, resp);
			
			//예외가 발생하면 여기서 error.jsp로 넘어가서 에러메시지 나올거고
			//성공 시 위에서 selectAll로 redirect넘어감
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
