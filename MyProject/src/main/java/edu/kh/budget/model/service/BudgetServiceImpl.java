package edu.kh.budget.model.service;
import static edu.kh.budget.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import edu.kh.budget.model.dao.BudgetDAO;
import edu.kh.budget.model.dao.BudgetDAOImpl;
import edu.kh.budget.model.dto.Budget;

public class BudgetServiceImpl implements BudgetService{
	//DAO 객체 생성하기
	private BudgetDAO dao = null;
	
	//기본생성자가 객체 초기화하는 데에 가장 우선순위가 높아서
	//기본생성자로 생성하는게 좋다!!!!
	public BudgetServiceImpl() {
		dao = new BudgetDAOImpl();
	}
	//조회
	@Override
	public List<Budget> selectAll()throws SQLException{
		Connection conn = getConnection();
		List<Budget> budgetList=dao.selectAll(conn);
		close(conn);
		return budgetList;
	}
	//삽입
	@Override
	public int insert(String budgetContent, String budgetOption, int budgetAmount) throws SQLException {
		int result=0;
		//Connection
		Connection conn = getConnection();
		if(budgetOption.equals("+")) result=dao.plus(conn, budgetContent, budgetOption, budgetAmount);
		else 			result=dao.minus(conn, budgetContent, budgetOption, budgetAmount);
		
		if(result>0) commit(conn);
		else				rollback(conn);
		close(conn);
		return result;
	}
	//수정
	@Override
	public int edit(int budgetNo,String budgetContent, int budgetAmount) throws SQLException {
		int result=0;
		Connection conn = getConnection();
		try {
			
			result=dao.edit(conn,budgetNo);
			if(result>0) commit(conn);
			else				rollback(conn);
		}finally {
			close(conn);
		}
		return result;
	}
	//1행 조회
	@Override
	public Budget selectBudget(int budgetNo) throws SQLException {
		Connection conn = getConnection();
		Budget budget =null;
		try {
			budget=dao.selectBudget(budgetNo, conn);
		}finally {
			close(conn);
		}
		
		return budget;
	}
	//삭제
	@Override
	public int delete(int budgetNo) throws SQLException {
		int result=0;
		Connection conn=getConnection();
		try {
			
			result=dao.delete(conn,budgetNo);
		}finally {
			close(conn);
		}
		return result;
	}
}
