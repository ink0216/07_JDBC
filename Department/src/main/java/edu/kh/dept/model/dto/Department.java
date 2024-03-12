package edu.kh.dept.model.dto;
//DTO 객체 하나가 DEPARTMENT4 테이블의 한 행의 데이터를 저장하는 용도임
//객체 하나가 한 행의 데이터(세 컬럼값)을 저장
//한 행씩 insert하거나 할 때 사용
public class Department {
//부서
	//Dept_id, Dept_title, location Id 있음
	//한 행의 정보를 저장할 변수 만들기
	private String deptId; //부서코드
	private String deptTitle; //부서명
	private String locationId; //지역코드
	
	public Department() {} //기본 생성자
	
	//매개변수 생성자
	public Department(String deptId, String deptTitle, String locationId) {
		super();
		this.deptId = deptId;
		this.deptTitle = deptTitle;
		this.locationId = locationId;
	}
	
	//getter/setter

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptTitle() {
		return deptTitle;
	}

	public void setDeptTitle(String deptTitle) {
		this.deptTitle = deptTitle;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
//toString 오버라이딩
	@Override
	public String toString() {
		return "Department [deptId=" + deptId + ", deptTitle=" + deptTitle + ", locationId=" + locationId + "]";
	}
	
	
	
	
	
}
