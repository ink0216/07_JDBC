package edu.kh.todoList.controller;

import java.io.IOException;

import edu.kh.todoList.model.dto.Todo;
import edu.kh.todoList.model.service.TodoService;
import edu.kh.todoList.model.service.TodoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//할 일 상세 조회 ->한 행만 조회할 것인데, PK가 적용된 컬럼인 todoNo 를 이용하는게 좋다!
@WebServlet("/todo/detail")
// <td><a href="/todo/detail?todoNo=${todoNo}">${todo.todoTitle}</a></td> 중에서
// ?앞까지만 요청 주소여서 그걸 주소에 적기
// ? 뒤부터는 요청 파라미터이고, 그게 쿼리스트링이다
public class TodoDetailServlet extends HttpServlet{
//a태그는 get방식!!!
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//서비스 객체 생성하기
			TodoService service = new TodoServiceImpl();
			
			//파라미터(todoNo) 얻어오기
			int todoNo = Integer.parseInt(req.getParameter("todoNo"));
					//html에서 전달되는 것은 전부 text(String)이어서 
					//스트링을 원하는 자료형으로 바꾸는 것이 parseInt
			
			//서비스 호출 후 결과 반환 받기
			//Todo 객체에 5개를 한번에 다 저장
			Todo todo = service.selectTodo(todoNo); //todoNo 넘겨줘서 조회하기
			
			//조회 결과에 따라 응답 제어하기
			if(todo !=null) {
				//조회 결과가 있을 경우
				req.setAttribute("todo", todo); //그대로 보내서 화면에 출력하는 코드 쓸거임
				String path = "/WEB-INF/views/detail.jsp";
				req.getRequestDispatcher(path).forward(req, resp);
			}else {
				//파라미터로 전달받은 todoNo가 DB에 존재하지 않아서 조회 결과가 없을 경우
				req.getSession().setAttribute("message", "해당 할 일이 존재하지 않습니다");
				//session scope로 메세지 보내고
				
				resp.sendRedirect("/"); // 메인페이지는 이 메시지를 출력하는 코드가 있어서
				//메인페이지로 redirect해주기
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
