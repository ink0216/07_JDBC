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

@WebServlet("/todo/update")
public class TodoUpdateServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//수정 화면으로 전환하기
		//기존 내용 써져있고 그걸 수정할 수 있는 화면으로 바꾸기
		
		//doPost도 만들면 코드 길이 줄일 수 있는데 나중에 해보기
		try {
			TodoService service = new TodoServiceImpl(); //서비스 객체 생성
			
			//파라미터 얻어오기 (+int로 변환) 
			//요청 주소 뒤의 쿼리스트링에서 todoNo를 파라미터로 넘겨줬음!!!
			int todoNo = Integer.parseInt(req.getParameter("todoNo"));
			
			//상세 조회 서비스 호출
			Todo todo = service.selectTodo(todoNo); //todoNo 넘겨줘서 제목 내용 이런거 가져오는 함수
			//미리 만들어둔 것 재활용함
			if(todo !=null) {
				//해당 글이 존재하는 글이었어서 수정 가능
				req.setAttribute("todo", todo);
				String path = "/WEB-INF/views/update.jsp";
				req.getRequestDispatcher(path).forward(req, resp);
				//글이 존재하면 그 글을 수정하는 화면으로 넘어가겠다
			} else {
				//주소창에 get방식이라 직접 입력해서 요청할 수 있는데, 존재하지 않는 번호일 경우
				//존재하지 않는 글 ->수정 불가 ->redirect로 날려버리기
				req.getSession().setAttribute("message", "존재하지 않는 할 일 입니다");
				//세션 얻어와서 메시지 추가
				
				//redirect하기
				resp.sendRedirect("/"); //메인페이지로 이동시키기 ->메인페이지에 메시지 출력하는 거 있으니까 출력될거임
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//할 일 제목, 내용 수정하기
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//서비스 객체 생성하기
			TodoService service = new TodoServiceImpl();
			//파라미터 얻어오기
			int todoNo = Integer.parseInt(req.getParameter("todoNo")); //hidden으로 숨겨서 넘긴 것임
			String todoTitle=req.getParameter("todoTitle");
			String todoContent=req.getParameter("todoContent");
			
			//파라미터가 세개이니까 Todo를 이용해서 묶어서 보내기
			Todo todo = new Todo(todoNo, todoTitle, todoContent);
			
			//서비스 메서드 호출 후 결과 반환 받기
			int result = service.updateTodo(todo); //전달받은 todo 그대로 전달해주기
					//update할거니까 int로 반환된다
			
			//이번엔 무조건 redirect할거임
			String path = null;
			String message=null;
			
			//수정 성공하면 -> 상세 조회 페이지로 갔으면 좋겠다
			if(result>0) {
				path="/todo/detail?todoNo="+todoNo;
				message="수정 성공!";
			}else {
				//수정 실패했으면 -> 수정하고 있던 페이지로 넘어간다
				path="/todo/update?todoNo="+todoNo;
				message="수정 실패";
						//get방식이라 doGet이 수행됨
			}
			req.getSession().setAttribute("message", message);
			resp.sendRedirect(path); //성공/실패 시의 redirect 주소가 다르다!
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
