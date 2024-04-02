package edu.kh.budget.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.kh.budget.model.dto.Budget;

public interface BudgetService {

	/**전체 목록 조회
	 * @return
	 * @throws SQLException
	 */
	List<Budget> selectAll()throws SQLException;

	/**삽입
	 * @param budgetContent
	 * @param budgetOption
	 * @param budgetAmount
	 * @return
	 * @throws SQLException
	 */
	int insert(String budgetContent, String budgetOption, int budgetAmount)throws SQLException;

	/**수정 (미완성)
	 * @param budgetNo
	 * @param budgetContent
	 * @param budgetAmount
	 * @param budgetOption 
	 * @return
	 * @throws SQLException
	 */
	int edit(int budgetNo,String budgetContent, int budgetAmount, String budgetOption)throws SQLException;

	/**삭제(미완성)
	 * @param budgetNo
	 * @return
	 * @throws SQLException
	 */
	int delete(int budgetNo)throws SQLException;

	/**특정 내역 상세 조회
	 * @param budgetNo
	 * @return
	 * @throws SQLException
	 */
	Budget selectBudget(int budgetNo)throws SQLException;


}
