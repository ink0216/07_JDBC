package edu.kh.dept.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import edu.kh.dept.model.dto.Department;

public interface DepartmentDAO {

	/**부서 전체 조회하기
	 * @param conn
	 * @return deptList
	 * @throws SQLException
	 */
	List<Department> selectAll(Connection conn) throws SQLException;
	//인터페이스에서 구현

	/**부서 추가
	 * @param conn
	 * @param dept
	 * @return result
	 * @throws SQLException
	 */
	int insertDepartment(Connection conn, Department dept) throws SQLException;

	/** 부서 삭제
	 * @param conn
	 * @param deptId
	 * @return
	 */
	int delete(Connection conn, String deptId) throws SQLException;

	/**부서 1행 조회
	 * @param conn
	 * @param deptId
	 * @return dept
	 */
	Department selectOne(Connection conn, String deptId)throws SQLException;

	/**부서 수정하기
	 * @param conn
	 * @param dept
	 * @return result
	 */
	int updateDepartment(Connection conn, Department dept)throws SQLException;

	/**부서 검색하기
	 * @param conn
	 * @return deptList
	 */
	List<Department> searchDepartment(Connection conn, String keyword)throws SQLException;
}
