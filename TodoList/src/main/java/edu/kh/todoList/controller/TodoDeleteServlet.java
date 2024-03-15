package edu.kh.todoList.controller;

import java.io.IOException;

import edu.kh.todoList.model.service.TodoService;
import edu.kh.todoList.model.service.TodoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/todo/delete")
public class TodoDeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//서비스 객체 생성
			TodoService service = new TodoServiceImpl();
			int todoNo = Integer.parseInt(req.getParameter("todoNo"));//쿼리스트링으로 todoNo가 전달받아와짐!!!!
			int result = service.deleteTodo(todoNo);//todoNo전달하기
			String path =null;
			String message=null;
			//결과에 따라서 경로와 메세지가 달라지게 해보기!
			
			//if문 else문 둘다 redirect임!!!
			if(result>0) {
				//삭제 성공 ->해당 글 사라지니까 상세조회 못보고, 메인페이지로 돌아가기!
				path = "/" ; //메인페이지
				//if문 else문 둘다 redirect임!!!
				message="삭제 되었습니다";
						
			}else {
				//삭제 실패 -> 다시 상세조회 페이지로 돌아가기
				path="/todo/detail?todoNo="+todoNo; //상세조회
				message="삭제 실패";
			}
			req.getSession().setAttribute("message", message);
			resp.sendRedirect(path);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
