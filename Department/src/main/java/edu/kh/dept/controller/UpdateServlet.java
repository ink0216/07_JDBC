package edu.kh.dept.controller;

import java.io.IOException;

import edu.kh.dept.model.dto.Department;
import edu.kh.dept.model.service.DepartmentService;
import edu.kh.dept.model.service.DepartmentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/department/update")
public class UpdateServlet extends HttpServlet{
//JS에서 여기로  location.href로 요청했는데 그건 Get방식!
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//전달된 파라미터 deptId 라는 키값으로 deptId값 넘어옴 그거 얻어오기
		//파라미터 얻어오기
		String deptId = req.getParameter("deptId");
		try {
			//서비스 객체 생성
			DepartmentService service = new DepartmentServiceImpl();
			
			//deptId가 일치하는 1행 조회하는 서비스 호출하기 ->딱 1행만 저장하는 Department 객체 생성!!!!
			Department dept = service.selectOne(deptId); //deptId 전달할거다
			
			//조회 결과 검사하기
			//조회 결과가 없을 경우 
			if(dept==null) {
				//DAO에서 조회한 결과를 dept에 담아서 보내주는데 dept를 처음에 null로 설정해놓고
				//조회 결과가 없으면 if문 실행이 안돼서 계속 null상태임
				//	->조회 결과가 없는 경우
				req.getSession().setAttribute("message", "해당 부서가 존재하지 않습니다");; //Session 객체를 얻어와서 바로 message 속성 추가해주기
				resp.sendRedirect("/department/selectAll"); //전체 조회로 redirect하기
			} else {
				//조회 결과가 있는 경우 ->다른 jsp로 forward하기
				req.setAttribute("dept", dept); //dept객체를 request scope에 세팅해놓고
				String path = "/WEB-INF/views/update.jsp";
				req.getRequestDispatcher(path).forward(req, resp); //jsp로 forward(여기에 대한 전용 응답 화면 존재!)
				//request에 dept(조회한 한 줄 들어있음)를 담아서 보내줌
			}
			
			
			
			
			
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//부서 수정하기
	@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			try {
				//서비스 객체 생성
				DepartmentService service = new DepartmentServiceImpl();
				
				//전달 받은 파라미터를 모두 얻어와 Department 객체에 저장하기
				String deptId = req.getParameter("deptId"); //input태그의 name속성값을 작성해주면 얻어올 수 있다
				String deptTitle = req.getParameter("deptTitle"); //input태그의 name속성값을 작성해주면 얻어올 수 있다
				String locationId = req.getParameter("locationId"); //input태그의 name속성값을 작성해주면 얻어올 수 있다
				
				//저 세개를 묶어서 다닐 Department 객체 필요
				Department dept = new Department(deptId, deptTitle, locationId);
				
				//부서 수정 서비스 호출 후 결과 반환 받기
				//그럼 서비스에서 DAO를 호출하고
				//DAO가 update 수행하고 결과는 int로 나온다
				int result = service.updateDepartment(dept); //dept 하나 전달한다
				
				//수정 결과에 따라 message 지정하기
				String message = null;
				if(result>0) message = "부서 수정 성공!!!";
				else					message="부서 수정 실패...";
				
				//message를 Session에 속성으로 추가하기
				req.getSession().setAttribute("message", message);
				
				//전체 부서 조회하는 페이지 재요청
				resp.sendRedirect("/department/selectAll");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
}
