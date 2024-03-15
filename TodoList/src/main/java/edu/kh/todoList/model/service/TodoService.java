package edu.kh.todoList.model.service;

import java.sql.SQLException;
import java.util.Map;

import edu.kh.todoList.model.dto.Todo;

public interface TodoService {

	/**할 일 목록 + 완료된 할 일 개수 조회
	 * @return map(두 개를 맵에 담아서 반환할 거다)
	 * @throws SQLException
	 */
	Map<String, Object> selectAll() throws SQLException;

	/**할 일 추가
	 * @param todoTitle
	 * @param todoContent
	 * @return
	 * @throws SQLException
	 */
	int addTodo(String todoTitle, String todoContent)throws SQLException;

	
	/**할 일 상세 조회
	 * @param todoNo
	 * @return todo 객체
	 * @throws SQLException
	 */
	Todo selectTodo(int todoNo)throws SQLException;

	/**완료 여부 변경하기
	 * @param todoNo
	 * @param complete
	 * @return result
	 * @throws SQLException
	 */
	int changeComplete(int todoNo, String complete)throws SQLException;

	/**할 일 수정하기
	 * @param todo
	 * @return result
	 * @throws SQLException
	 */
	int updateTodo(Todo todo)throws SQLException;

	/** 할 일 삭제하기
	 * @param todoNo
	 * @return result
	 * @throws SQLException
	 */
	int deleteTodo(int todoNo)throws SQLException;

}
