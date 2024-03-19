package edu.kh.budget.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import edu.kh.budget.model.dto.Budget;

public interface BudgetDAO {

	List<Budget> selectAll(Connection conn)throws SQLException;

	int plus(Connection conn, String budgetContent, String budgetOption, int budgetAmount)throws SQLException;

	int minus(Connection conn, String budgetContent, String budgetOption, int budgetAmount)throws SQLException;

	int edit(Connection conn, int budgetNo)throws SQLException;

	int delete(Connection conn, int budgetNo)throws SQLException;

	Budget selectBudget(int budgetNo, Connection conn)throws SQLException;

}
