package edu.kh.dept.controller;

import java.io.IOException;

import edu.kh.dept.model.service.DepartmentService;
import edu.kh.dept.model.service.DepartmentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@WebServlet("/department/delete")
public class DeleteServlet extends HttpServlet{ //간단한 버전이니까 이걸로 연습해보기 혼자 만드는 것
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//doPost 오버라이딩
		//삭제 시 form태그의 method가 POST이니까
		String message=null;
		try {
			//서비스 객체 생성하기
			DepartmentService service = new DepartmentServiceImpl(); //DepartmentServiceImpl 클래스 객체 생성
			
			//제출된 파라미터(input안에 deptId 들어있음) 얻어오기
			String deptId = req.getParameter("deptId"); //삭제할 부서 코드가 전달돼서 온다
			//파라미터는 요청객체인 req에 담겨있음
			//request 객체는 서블릿에만 존재함!!!!!!!!!!
			//----------------------------------------------------------------------여기서부터 우리가 해보기....
			//서비스 메서드 호출 후 결과 반환 받기
			int result = service.delete(deptId);
			//서비스 결과에 따라서 
			//Session에 "삭제 성공", "삭제 실패" 메시지를 속성으로 추가
			if(result>0) message="삭제 성공";
			else message="삭제 실패";
			
			HttpSession session = req.getSession();
			session.setAttribute("message", message);
			//전체 부서 조회를 재요청하는 코드
			resp.sendRedirect("/department/selectAll");
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
