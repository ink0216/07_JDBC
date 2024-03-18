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

@WebServlet("")
public class MainServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//서비스 호출
			BudgetService service = new BudgetServiceImpl();
			List<Budget> budgetList= service.selectAll(); //service 호출
			String path = "/WEB-INF/views/main.jsp"; 
			req.setAttribute("budgetList", budgetList);
			//main.jsp로 포워드하기
			req.getRequestDispatcher(path).forward(req, resp);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
