package edu.kh.jdbc.run;

import edu.kh.jdbc.view.DepartmentView;

//화면을 Run - View -DAO 순으로 위에 탭 두기 (Department-)
public class DepartmentRun {
public static void main(String[] args) {
	new DepartmentView().displayMenu();
	//객체를 새로 생성하자마자 메서드를 한번 호출함(근데 그 메서드가 0이 입력될 떄까지 계속 반복을 돌아서 한번만 호출하면 된다)
}
}
