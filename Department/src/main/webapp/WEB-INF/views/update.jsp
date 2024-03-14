<%-- jsp치고 자동완성하면 기본 틀 생김 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${dept.deptId} 부서 수정 페이지</title>
    <%-- dept 객체의 deptId라는 컬럼값 나옴 --%>
</head>
<body>
    <h1>${dept.deptId} 부서 수정 페이지</h1>
    <%-- 이 페이지 넘어왔을 때, 주소가 GET방식 요청으로 넘어온 /department/update 일거임  --%>
    <%-- 상대 경로로 요청하면 맨 뒤의 update가 지워지고 새롭게 update로 쓰여지고, 근데 이번에는 POST방식으로 요청감 --%>
    <form action="update" method="POST">
        <div>
            부서 코드(DEPT_ID) : 
            <input type="text" name="deptId" value="${dept.deptId}" readonly>
            <%-- value를 쓰면 기존 부서 코드가 value값으로서 input 안에 먼저 쓰여진채로 켜짐 --%>
            <%-- readonly : disabled(비활성화)(이 값을 아예 사용 안하겠다는 뜻)와는 조금 다름 --%>
            <%--  readonly 속성 : 읽기 전용(그 값을 사용하기는 하지만, 해당 input 태그 값 수정 불가)--%>
        </div>
        <div>
            부서명(DEPT_TITLE) : 
            <input type="text" name="deptTitle" value="${dept.deptTitle}" placeholder="수정할 부서명 입력">
            <%-- 기존에 있던 기존 부서를 보여주기 위해  --%>
        </div>
        <div>
            지역 코드(LOCATION_ID) :
            <input type="text" name="locationId" value="${dept.locationId}" placeholder="수정할 지역 코드 입력">
            <%-- ${dept.locationId} : el구문 --%>
        </div>
        <button>수정 하기</button> <%-- 제출 버튼 --%>
        <%-- 제출 주소 : /department/update인데 이번에는 post방식!! --%>
    </form>
</body>
</html>