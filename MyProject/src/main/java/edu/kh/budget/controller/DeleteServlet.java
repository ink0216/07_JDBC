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

@WebServlet("/budget/delete")
public class DeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	//서비스 호출
			BudgetService service = new BudgetServiceImpl();
			int result=0;
			try {
				int budgetNo = Integer.parseInt(req.getParameter("budgetNo"));
				Budget budget = service.selectBudget(budgetNo);
				if(budget !=null) {
					result = service.delete(budgetNo);
				}else {
					req.getSession().setAttribute("message", "존재하지 않는 내역입니다");
					resp.sendRedirect("/"); //메인페이지 
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
}
