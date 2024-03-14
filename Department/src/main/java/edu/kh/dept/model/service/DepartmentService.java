package edu.kh.dept.model.service;

import java.sql.SQLException;
import java.util.List;

import edu.kh.dept.model.dto.Department;
import edu.kh.dept.model.exception.DepartmentInsertException;

public interface DepartmentService {

//여기에 선언되는 메서드는 모두 public abstract 메서드이다(추상메서드)!!!!!!!->중괄호 없음->자식이 구현
	//그러면 이걸 상속받는 클래스들에서 강제 오버라이딩을 해야한다
//	DepartmentServiceImpl 클래스가 이 인터페이스를 상속받을거임
	/**모든 부서 조회
	 * @return deptList(부서 목록)
	 * @throws SQLException
	 */
	List<Department> selectAll() throws SQLException; //던지겠다

	/**부서 추가 서비스
	 * @param dept
	 * @return result(삽입된 행의 개수)
	 * @throws SQLException
	 */
	int insertDepartment(Department dept) throws DepartmentInsertException;

	/**부서 여러개 추가
	 * @param deptList
	 * @return
	 */
	int multiInsert(List<Department> deptList) throws DepartmentInsertException;

	/**부서 삭제
	 * @param deptId
	 * @return
	 * @throws SQLException
	 */
	int delete(String deptId)throws SQLException;

	/**부서 1행 조회
	 * @param deptId
	 * @return
	 * @throws SQLException
	 */
	Department selectOne(String deptId) throws SQLException;

	/**부서 수정하기
	 * @param dept
	 * @return result (0 또는 1)
	 * @throws SQLException
	 */
	int updateDepartment(Department dept)throws SQLException;

	/**부서 검색하기
	 * @return deptList
	 * @throws SQLException
	 */
	List<Department> searchDepartment(String keyword) throws SQLException;
}
