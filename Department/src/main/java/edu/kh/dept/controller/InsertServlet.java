package edu.kh.dept.controller;

import java.io.IOException;

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
@WebServlet("/department/insert") //이 클래스를 서블릿으로 등록->클래스로는 아무것도 못해서 이 클래스를 객체로 만듦
//현재 클래스를 서블릿으로 등록(서버 실행 시 이 클래스가 객체로 생성됨) + URL 매핑
public class InsertServlet extends HttpServlet{
//a태그는 get방식 요청 ->오버라이딩 후 안에 거 다 지우기!
	//a태그, JS(location.href), 주소창 직접 작성, form태그 method="GET" ==>모두 다 GET방식 요청임!!!!
	//form 태그 method "POST" ==>얘만 POST방식 요청임
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//JSP로 요청 위임해서 부서 추가 화면 보여주기
		
		// ** JSP 파일 경로는 /(webapp폴더)를 기준으로 작성하면 된다!!!!!!!
		String path = "/WEB-INF/views/insert.jsp";
		
		//요청 발송자(RequestDispatcher)를 이용해서 
		//요청을 위임하기(forward)
		req.getRequestDispatcher(path).forward(req, resp);
		
		//jsp : 자바임! =>서블릿 코드로 변함
		//서블릿의 메인 언어 = 자바 언어
		//jsp는 html 언어로 작성할 수 있게 함(화면을 만드는 것이므로). 근데 자바 코드임
		
		
	}
	// <form method="POST">, ajax(비동기 통신 요청 POST 방식) ==>POST방식 요청
	//ajax -> get,post,put,delete 등의 방식으로 요청 보낼 수 있음
	
	@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	//insert로 요청 보낸 것 여기서 처리!
		//서블릿은 controller임
			try {
				DepartmentService service=new DepartmentServiceImpl(); //인터페이스로는 객체 생성 안되니까 상속받은 자식 클래스로 객체생성
				//나중에 유지보수 위해서 굳이 이렇게 씀
				//new DepartmentServiceImpl2()를 새로 만들면 뒤에 2만 추가하면 되니까!
				
				//요청 시 전달 받은 데이터(==Parameter, 전달인자) 얻어오기
				String deptId = req.getParameter("deptId"); //괄호 안에 입력된 값의 name속성값을 쓴다
				String deptTitle = req.getParameter("deptTitle"); 
				String locationId = req.getParameter("locationId"); 
				//html에서 작성되는 모든 값은 다 String이므로 String으로 받기!
				//세 개의 값을 서비스로 전달해줄거임
				
				//mvc 패턴(모델 뷰 컨트롤러)
				//모델 = 비즈니스 로직을 처리하는 부분
				//뷰 = 응답화면 관련된 것
				//컨트롤러 = 어떻게 응답화면 보여줄지 제어
				/*화면에서 입력 시 요청 주소 : login.do ->여기서 .은 별 의미 없고 그냥 문자 그대로임!
				 * 아이디 비번이 파라미터로 전달됨
				 * Servlet Container(서블릿 관련된 것 모아둔 곳)으로 옴==응답화면 만드는 서버인 WAS 역할 함
				 * req(요청 데이터, 클라이언트 정보 담김),resp(응답 방법 담김) 객체 생성
				 * web.xml로 연결돼서 어떤 서블릿으로 연결할 지 하는데 webservlet으로 대체
				 * controller=제어기 ->어떤 주소로 요청 왔을 때 응답을 어떻게 할지 컨트롤. 어떨 때 누구에게 화면 넘겨서 만들게 할지 제어
				 * 컨트롤러는 요청 왔을 때 어떻게 응답할지만 제어함
				 * service : 비즈니스 로직 처리(데이터 가공 등 실질적 일을 하는 부분==service !!!)
				 * 서비스 하기 위한 재료를 DB에서 가져오는데 서비스가 직접 가지 않고 DAO에게 DB에 가서 가져오라고 시킴
				 * DAO : 창고(DB)에 가서 데이터 가져오거나, 데이터를 insert/delete/update함
				 * 
				 * DBCP : DB에 연결하는 커넥션을 미리 만들어놓고 필요할 때 빌려주는 시스템 ->커넥션의 수가 정해져있어서 DB의 과부하 안생김
				 * */
				
				//Dynamic Web Project의 가장 기본적인 요청/응답 흐름
				/*클라이언트 요청 
				 * -> Controller(Servlet)
				 * ->Service(비즈니스 로직 처리)
				 * ->DAO(DB와 연결돼있는 객체)
				 * ->DB
				 * 올 때에는 DB에서 출발해서 차례로 거꾸로 돌아옴
				 * Controller까지 돌아오는데, Controller에서 요청에 따른 응답 화면을 제어 (이러면 이런 화면 보여줘야지)*/
				
				//부서 추가 서비스 호출 후 결과를 int로 받는다(insert이니까)
				Department dept=new Department(deptId, deptTitle, locationId); //전달받은 값이 객체에 담김
				int result = service.insertDepartment(dept); //이걸 서비스에게 보낸다
				
				//삽입 성공 여부 검사할 방법!
				String message = null; //응답 화면에서 alert로 출력할 내용을 여기에 담아서 내보낼거다(삽입 성공/실패)
				//Session : 브라우저 당 1개씩 생성되는 객체
				//클라이언트가 접속 시 1개 생성되고, 
				//브라우저를 닫거나 세션에 시간을 지정했고(5분, 10분,...), 그 시간이 만료되었을 때 소멸함
				HttpSession session = req.getSession();
				if(result>0) message="부서 추가 성공!!!"; //추가된 행의 개수가 있는 경우
				else message="부서 추가 실패...";
				
				//추가가 완료된 후에 와 성공!하는 별도의 화면이 필요하지 않고 /department/selectAll로 다시 redirect함
				//모든 부서를 조회하는 Servlet을 재요청하기(redirect)
				//	->redirect는 재요청이어서->새로운 request 객체가 다시 만들어짐
				//	->기존에 있던 request 객체가 사라짐!!!
				//	->그래서, redirect시 데이터를 전달하고 싶으면 
				//		request가 아닌 session에 데이터를 담아서 전달해야 한다!!!!!(그래서 위에 session을 얻어와둠!!!!)
				session.setAttribute("message", message); //문자열 "message"라는 key값으로 message에 "저장된 값"을 속성으로 설정한다
				//session에 담으면 redirect를 해도 데이터가 유지된다
				
				//모든 부서 조회 페이지 재요청
				resp.sendRedirect("/department/selectAll"); //추가된 것 다시 전체 조회하는 서비스 호출해서 리스트를 반환 ->selectAll.jsp로 위임
				//>selectAll.jsp는 for문으로 테이블 모양으로 데이터 다 조회해서 보여줌
				//insert를 호출했지만 최종 테이블 나올 때에는 selectAll 주소가 보여진다!
			}catch(DepartmentInsertException e) {
				//제약조건 위배로 삽입 실패 예외가 발생한 경우
				//알려주기 위해서 DepartmentInsertException 예외를 강제 발생시킴
				//이거 안하면 에러 발생 시 흰 브라우저 화면 나오는데 그러면 안돼서!
				
				//request scope객체에 에러 메시지 세팅하기
				req.setAttribute("errorMessage", e.getMessage());
				//e.getMessage() 하면 super 옆에 쓴 메세지가 그대로 얻어와져서 errorMessage에 세팅된다
				
				//에러 페이지로 포워드하기
				String path="/WEB-INF/views/error.jsp";
				req.getRequestDispatcher(path).forward(req, resp); //이 페이지로 에러 메시지 가지고 요청 위임하겠다
			}
			catch(Exception e) {
				e.printStackTrace(); //이러면 콘솔에 에러내용만 나오고 끝나니까
				//에러 페이지로 포워드하는 코드 쓰기(에러났다고 )
			}
		}
}
