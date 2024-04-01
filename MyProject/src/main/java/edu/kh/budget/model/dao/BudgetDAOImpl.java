package edu.kh.budget.model.dao;
import static edu.kh.budget.common.JDBCTemplate.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.budget.model.dto.Budget;

public class BudgetDAOImpl implements BudgetDAO{
//JDBC 객체 참조변수 선언하기
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//Properties 객체 참조변수 선언하기
	//Properties : xml파일 읽어올 때 써먹는, k:v가 모두 String인 Map
	private Properties prop; //null 안써도 자동으로 null대입됨
	
	//기본 생성자로 객체 생성 시
	//Properties 객체 생성 + xml 파일 내용 읽어오기
	public BudgetDAOImpl() {
		try {
			prop = new Properties();//Properties 객체 생성
			String path = BudgetDAOImpl.class.getResource("/edu/kh/budget/sql/sql.xml").getPath();
					//우리가 읽어와야 하는 sql 파일(sql.xml)의 경로 
					// == 이 클래스가 2진수로 저장되는 곳에서 자원을 얻어올거다
					//클래스 파일이 모이는 곳까지 가는 데 까지의 경로를 얻어올거다
			
			prop.loadFromXML(new FileInputStream(path));
			//path에 있는 xml에 있는 내용을 스트림으로 얻어와서 prop에 적재한다
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<Budget> selectAll(Connection conn) throws SQLException {
		//결과 저장용 변수 선언
		List<Budget> budgetList = new ArrayList<Budget>(); 
		try {
		//sql 만들기
			String sql = prop.getProperty("selectAll");
			
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				int budgetNo = rs.getInt("BUDGET_NO");
				String budgetContent = rs.getString("BUDGET_CONTENT");
				String budgetOption = rs.getString("BUDGET_OPTION");
				int budgetAmount = rs.getInt("BUDGET_AMOUNT");
				int budgetLeft = rs.getInt("BUDGET_LEFT");
				
				Budget budget = new Budget(budgetNo,budgetContent, budgetOption, budgetAmount, budgetLeft);
				budgetList.add(budget);
			}
		}finally {
			close(rs);
			close(stmt);
		}
		
		return budgetList;
	}
	//입금
	@Override
		public int plus(Connection conn, String budgetContent, String budgetOption, int budgetAmount) throws SQLException {
		int result=0;
		try {
			//SQL 만들기
			String sql = prop.getProperty("plus");
			pstmt=conn.prepareStatement(sql);
			//내용, +, 양
			pstmt.setString(1, budgetContent);
			pstmt.setString(2, budgetOption);
			pstmt.setInt(3, budgetAmount);
			pstmt.setInt(4, budgetAmount);
			
			result=pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
			return result;
		}
	//출금
	@Override
		public int minus(Connection conn, String budgetContent, String budgetOption, int budgetAmount) throws SQLException {
		int result=0;
		try {
			//SQL 만들기
			String sql = prop.getProperty("minus");
			pstmt=conn.prepareStatement(sql);
			//내용, +, 양
			pstmt.setString(1, budgetContent);
			pstmt.setString(2, budgetOption);
			pstmt.setInt(3, budgetAmount);
			pstmt.setInt(4, budgetAmount);
			
			result=pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
			return result;
		}
	//수정
	//내용/변화량 수정
	@Override
		public int editContentAmount(Connection conn, int budgetNo, String budgetContent, int budgetAmount) {
		int result=0;
		try {
			//SQL 만들기
			String sql = prop.getProperty("editContentAmount");
			pstmt=conn.prepareStatement(sql);
			
		}finally {
			
		}
			return result;
		}
	//한 행 조회
	@Override
		public Budget selectBudget(int budgetNo, Connection conn) throws SQLException {
			Budget budget = null;
			ResultSet rs = null;
			try {
				String sql = prop.getProperty("selectBudget");
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, budgetNo);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					int no = rs.getInt("BUDGET_NO");
					String budgetContent = rs.getString("BUDGET_CONTENT");
					String budgetOption = rs.getString("BUDGET_OPTION");
					int budgetAmount = rs.getInt("BUDGET_AMOUNT");
					int budgetLeft = rs.getInt("BUDGET_LEFT");
					
					budget = new Budget(no, budgetContent, budgetOption, budgetAmount, budgetLeft);
				}
			}finally {
				close(rs);
				close(pstmt);
			}
			return budget;
		}
	//삭제
	@Override
		public int delete(Connection conn, int budgetNo) throws SQLException {
		int result=0;
		try {
			String sql = prop.getProperty("delete");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, budgetNo);
			result=pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
			return result;
		}
}
