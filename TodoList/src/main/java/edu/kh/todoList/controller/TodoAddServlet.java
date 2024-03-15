package edu.kh.todoList.controller;

import java.io.IOException;

import edu.kh.todoList.model.service.TodoService;
import edu.kh.todoList.model.service.TodoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/todo/add")
public class TodoAddServlet extends HttpServlet{ 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	//method가 POST방식이므로
		try {
			//서비스 객체 생성
			TodoService service = new TodoServiceImpl();
			//파라미터 얻어오기
			//제목이랑 내용을 req에 담아서 받아옴
			String todoTitle = req.getParameter("todoTitle");
			String todoContent = req.getParameter("todoContent");
			
			//할 일 추가 서비스 호출 후 결과 반환 받기
			//insert수행하면 int나옴
			int result = service.addTodo(todoTitle, todoContent); //두 값을 넘겨줘야 한다
			//전달할 것이 네개 넘어가면 객체로 묶어서 한번에 넘겨주고 그 이하면 그냥 하나씩 넘겨줌
			
			//결과에 따라 메세지 담아서 세션에 담아서 보내기
			String message = null;
			if(result>0) message="할 일 추가 성공!";
			else message="할 일 추가 실패...";
			
			//redirect할 때 세션 이용(redirect는 새 request가 만들어져서)
			HttpSession session = req.getSession();
			//세션 : 브라우저 마다 하나씩 생성되는 사용자 객체
			session.setAttribute("message", message);
			resp.sendRedirect("/"); //메인페이지 재요청
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
