package edu.kh.budget.controller;

import java.io.IOException;

import edu.kh.budget.model.dto.Budget;
import edu.kh.budget.model.service.BudgetService;
import edu.kh.budget.model.service.BudgetServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/budget/edit")
public class EditServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//서비스 호출
		BudgetService service = new BudgetServiceImpl();
		int result=0;
		try {
			
			int budgetNo = Integer.parseInt(req.getParameter("budgetNo"));
			Budget budget = service.selectBudget(budgetNo);
			if(budget !=null) { //존재하면
				req.setAttribute("budget", budget);
				String path = "/WEB-INF/views/update.jsp";
				req.getRequestDispatcher(path).forward(req, resp);
				
			} else {
				req.getSession().setAttribute("message", "존재하지 않는 내역입니다");
				resp.sendRedirect("/"); //메인페이지 
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//서비스 호출
		BudgetService service = new BudgetServiceImpl();
		
		int budgetNo = Integer.parseInt(req.getParameter("budgetNo")); //번호
		String budgetContent = req.getParameter("budgetContent"); //내용
		String budgetOption = req.getParameter("budgetOption"); //내용
		int budgetAmount = Integer.parseInt(req.getParameter("budgetAmount")); //변화량
		int result = 0;
		try {
			result=service.edit(budgetNo, budgetContent, budgetAmount, budgetOption);
			String message;
			if(result>0) { //상세페이지로 redirect
				message="할 일 수정 성공!!";
			}else {
				message="할 일 수정 실패...";
				//수정 실패 시 상세페이지로 redirect
			} //http://localhost/budget/edit?budgetNo=4
			req.getSession().setAttribute("message", message);
			resp.sendRedirect("/budget/edit?budgetNo="+budgetNo); //상세 페이지
		}catch(Exception e) {
			e.printStackTrace();
		}
		
				
		
	}
}
