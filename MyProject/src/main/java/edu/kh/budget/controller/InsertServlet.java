package edu.kh.budget.controller;

import java.io.IOException;
import java.util.List;

import edu.kh.budget.model.dto.Budget;
import edu.kh.budget.model.service.BudgetService;
import edu.kh.budget.model.service.BudgetServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/budget/insert")
public class InsertServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String budgetContent = req.getParameter("budgetContent");
		String budgetOption = req.getParameter("budgetOption");
		int budgetAmount = Integer.parseInt(req.getParameter("budgetAmount"));
		int result=0;
		String message=null;
		try {
		BudgetService service=new BudgetServiceImpl();
		result = service.insert(budgetContent, budgetOption, budgetAmount);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(result>0) message="추가 성공!";
		else message="추가 실패...";
		
		//redirect할 때 세션 이용(redirect는 새 request가 만들어져서)
		HttpSession session = req.getSession();
		//세션 : 브라우저 마다 하나씩 생성되는 사용자 객체
		session.setAttribute("message", message);
		resp.sendRedirect("/"); //메인페이지 재요청
	
		
	}
}
