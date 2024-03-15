package edu.kh.todoList.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import edu.kh.todoList.model.dto.Todo;
import edu.kh.todoList.model.service.TodoService;
import edu.kh.todoList.model.service.TodoServiceImpl;
//WEB-INF 폴더 내부의 lib 폴더에 tools 폴더 lib폴더 안에있는 압축파일 4개 넣기
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//localhost 뒤에 아무것도 안쓴 요청이 오면 main.jsp로 요청 위임할거다
@WebServlet("") //요청 주소를 빈칸으로 두기! ->그럼 index파일 안만들어도 된다
//이렇게 주소에 아무것도 안써놓은것 == 최상위 주소 요청 (근데 스프링부트에서는 /라고 써야함!)
public class MainServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			TodoService service = new TodoServiceImpl(); //서비스 객체 생성
			
			//할 일 목록 조회+ 완료된 할 일 개수를 조회하는 
			//하는 서비스 호출 후 결과 반환받기
			//서비스 하나에서 원하는 데이터가 두 종류가 있음(전체 목록, 카운트)
			Map<String, Object> map= service.selectAll(); //service 호출
			
			//map에 담긴 데이터 분리하기
			List<Todo> todoList = (List<Todo>)map.get("todoList"); 
			//map의 value를 object 타입인데 자식 객체인 list객체로 다운캐스팅(강제 형변환) 해준다
			int completeCount = (int)map.get("completeCount"); 
			
			//분리된 데이터를 request 객체에 속성으로 추가하기
			req.setAttribute("todoList", todoList);
			req.setAttribute("completeCount", completeCount);
			
			
			
			//메서드 하나가 반환(return)할 수 있는 값은 1개!!
			// ->근데 두 개를 묶어서
			//배열이든지 컬렉션이든지 이용하면 된다
			//Object는 모든 것의 최상위부모이므로 모든 것을 저장할 수 있다
			String path = "/WEB-INF/views/main.jsp"; //WEB-INF폴더 내부여서 직접접근 안되고 주소로도 안열림
			
			//main.jsp로 포워드하기
			req.getRequestDispatcher(path).forward(req, resp);
			
			
			
			
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
