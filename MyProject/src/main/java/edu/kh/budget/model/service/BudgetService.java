package edu.kh.budget.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.kh.budget.model.dto.Budget;

public interface BudgetService {

	List<Budget> selectAll()throws SQLException;

	int insert(String budgetContent, String budgetOption, int budgetAmount)throws SQLException;


}
