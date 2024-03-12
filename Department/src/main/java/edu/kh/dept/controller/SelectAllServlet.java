package edu.kh.dept.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import edu.kh.dept.common.JDBCTemplate;
import edu.kh.dept.model.dto.Department;
import edu.kh.dept.model.service.DepartmentService;
import edu.kh.dept.model.service.DepartmentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//상속받으면 doGet, doPost 오버라이딩해서 사용 가능
//@WebServlet : 현재 클래스를 서블릿으로 등록(->서버 실행 시 객체 생성)
//								+ URL 매핑해줌
@WebServlet("/department/selectAll") //이 주소로 인터넷 요청 오면 처리하겠다
public class SelectAllServlet extends HttpServlet{
//전체 조회 서블릿
	//a태그 요청은 get방식 요청이어서 doGet 오버라이딩
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Get방식 요청 처리
		try {
			//전체 조회하기
			//Service 객체 생성하기
			DepartmentService service = new DepartmentServiceImpl(); //부모타입이 자식 타입을 참조하는 다형성!
			//여기서 객체는 DepartmentServiceImpl 객체만 만들어짐
			
			//모든 부서 조회 Service 호출 후 결과 반환 받기 ->리스트로 바꿔서 가져옴
			List<Department> deptList = service.selectAll();
			
			//DB 조회 결과를 request scope에 세팅하여
			//jsp로 요청 위임(forward)하기
			//요청 객체 이용
			req.setAttribute("deptList", deptList);
			
			//요청 위임
			String path = "/WEB-INF/views/selectAll.jsp"; //이 위치의 jsp에게 위임할거다
			
			req.getRequestDispatcher(path).forward(req, resp);
			//getRequestDispatcher : 요청 발송자(얘가 요청을 위임해줌)
			//요청 내용과 응답 방법을 담은 두 객체 보냄
			//req에 deptList를 담아서 보냄
			//jsp가 deptList 이용해서 화면 만들 수 있다
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
