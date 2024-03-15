package edu.kh.dept.controller;

import java.io.IOException;
import java.util.List;

import edu.kh.dept.model.dto.Department;
import edu.kh.dept.model.service.DepartmentService;
import edu.kh.dept.model.service.DepartmentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/department/search")
public class SearchServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//제출된 key값이 keyword
			String keyword = req.getParameter("keyword"); //검색어 얻어오기
			//req에 담겨서 넘어옴
			DepartmentService service = new DepartmentServiceImpl();
			
			List<Department> deptList = service.searchDepartment(keyword); //keyword를 전달하기
			//여러 행 검색하니까
			
			//forward할 JSP 경로
			String path="/WEB-INF/views/search.jsp";
			
			//조회 결과를 request scope의 속성으로 세팅
			req.setAttribute("deptList", deptList);
			
			//지정된 jsp로 요청 위임하기
			req.getRequestDispatcher(path).forward(req, resp);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
