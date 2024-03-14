<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%-- forEach쓰기 위해서 --%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.keyword} 검색 결과</title>
    <%--//${param.keyword} ==제출된 parameter중에 keyword를 가져와라
    //forward할 때 req 그대로 넘김 --%>
</head>
<body>
    <h1>${param.keyword} 검색 결과</h1>
    <table border="1">
        <%-- selectAll.jsp참고하기 --%>
        <thead>
		
			<tr>
				<th>행 번호</th> <%--몇 번 행인지 --%>
				<th>부서 코드(DEPT_ID)</th>
				<th>부서명(DEPT_TITLE)</th>
				<th>지역 코드(LOCATION_ID)</th>
			</tr>
		</thead>
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
				</tr>
			</c:forEach>
		</tbody>
    </table>
    
</body>
</html>