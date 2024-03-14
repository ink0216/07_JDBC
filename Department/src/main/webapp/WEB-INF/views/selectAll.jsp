<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%-- JSTL Core 라이브러리 추가 -> c로 시작하는 태그를 만들 수 있게 된다--%>  
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
	<%-- if문 같은 거 쓸 수 있게 된다(core쓰고 자동완성 하면 됨!) --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전체 부서 조회</title>
</head>
<body>
	<h1>전체 부서 조회 페이지</h1>
	
	<table border="1">
		<thead>
		
			<tr>
				<th>행 번호</th> <%--몇 번 행인지 --%>
				<th>부서 코드(DEPT_ID)</th>
				<th>부서명(DEPT_TITLE)</th>
				<th>지역 코드(LOCATION_ID)</th>

				<th>수정 버튼</th>
				<th>삭제 버튼</th> <%-- 두 개의 컬럼 추가 --%>
			</tr>
		</thead>
		<%--JSTL : 자바 코드를 태그 모양으로 바꾼 것 --%>
		<tbody>
		
			<c:forEach items="${deptList}" var="dept" varStatus="vs">
			<%-- 반복하면서 한줄씩 만듦 --%>
			<%--하나씩 꺼낸 것을 dept라고 부를거다 --%>
			<%--deptList가 여기에 출력될거임 --%>
				<%--반복해서 넣어야 하므로 포이치 사용 --%>
				<%--el : 표현할 때 사용, ==${} --%>
				<%--varStatus : 반복되는 상태 기록(몇 바퀴 돌고있는지) --%>
				<tr>
				<%--vs.count : 현재 바퀴 횟수가 나옴(1부터 시작) --%>
					<td>${vs.count}</td>
					<td>${dept.deptId}</td>
					<%--el은 출력하는 언어여서 다 getter가 호출되도록 만들어져있다 원래는 dept.getDeptId임, 필드만 써도 getter가 호출된다 --%>
					<td>${dept.deptTitle}</td>
					<td>${dept.locationId}</td>
					<%--다 만들어질 때까지 반복 --%>
					<th>
						<button type="button" class="update-btn">수정</button>
					</th>
					<th>
						<button type="button" class="delete-btn">삭제</button>
					</th>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%-- 부서 추가 성공/실패 메세지가 session에 담아져서 넘어옴 -> 그걸 alert로 출력하는 코드 작성하기 --%>
	<%-- session scope로 전달 받은 message가 있으면 alert()로 출력 --%>
	<%-- 메세지가 있을 경우 하는 if문 쓰려면 jstl 써야함 --%>
	<%-- !if자동완성 --%>
	<c:if test="${not empty message}" >
	<%-- el의 특징 : null, nullPointerException 을 그냥 빈칸으로 나타냄 ->null도 empty로 인식 --%>
	<%-- 메세지가 비어있지 않으면 == 메세지가 존재하면 --%>
	<%-- page>request>session>application --%>
	<%-- 메세지라고 하면 가장 좁은 범위부터 검사해서 세션 것이 가장 좁으니까 세션 것을 자동으로 꺼냄 session.message라고 써도 됨 --%>
	<%-- page부터 application scope까지 message 속성이 있는지 확인해서 존재하는 scope의 값을 얻어온다 --%>
	<script>
		const message = "${message}";
		alert(message);
		
	</script>
	<%-- session 은 브라우저 종료 또는 만료 시까지 유지됨
			-> 현재 페이지에 들어올 때마다 session의 message가 계속 출력되는 문제가 발생함!!!!! 
				세션에 메세지가 있는 한 이 페이지에 들어오거나 새로고침 할 때마다 계속 성공 성공 뜸 
			-> 1회만 message를 출력한 후 제거하는 코드 쓰기--%>
	<%--!remove  --%>
	<c:remove var="message" scope="session" /> <%-- session scope에 있는 message를 지운다 --%>
	</c:if>
	<script src="/resources/js/selectAll.js"></script> <%-- 정적자원(webapp폴더 내부)은 절대경로로 쓰는게 좋다! --%>
	<%-- 스크립트 :  selectAll.js 이 파일을 --%>
	<%-- css, js파일을 html에 쓰면 클라이언트가 그 파일을 다운로드 해서 해석하는거임 --%>
	<%-- 클라이언트가 파일을 다운로드할 수 있게 하려면 주소로 접근 불가능한 WEB-INF 폴더가 아닌, 그 외의 폴더를 만들어서 --%>
	<%-- resources 등의 폴더를 만들어서 거기에 css랑 js 파일을 넣어놓는다->그러면 클라이언트가 접근 가능하다 ==정적자원 --%>
	<%-- 요청이 오면 서버에서 화면을 만들 때 화면을 만들어주지 않고, 화면에 필요한 코드를 클라이언트에게 전달해줌 --%>
	<%-- 클라이언트의 브라우저가 그 코드를 해석해서 화면을 보여줌 그 파일 안에 js랑 css를 다운받아야 한다고 경로가 써져있음 --%>
	<%-- 클라이언트가 그 파일을 다운로드해서 해석함 --%>
	<%-- html ,css ,js == 클라이언트 사이드 언어(클라이언트쪽에서 해석되는 언어) --%>
</body>
</html>