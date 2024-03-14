<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>부서 추가 페이지</title>
</head>
<body>
    <h1>부서 추가 페이지</h1>
    <%-- 주소에서 절대/상대 경로
    주소 형태 
: http(프로토콜, html왔다갔다)://IP주소(또는 도메인(네이버닷컴 등등)):PORT(근데 80번->안보임)/(/가 webapp폴더 최상위)/요청주소
    ex) 현재 페이지 주소 : http://localhost/department/insert (GET) 이렇게 돼 있을거다
    localhost바로 뒤의 /부터가 요청주소임
    절대경로는 /로 시작
    /로 시작 안하는 것은 상대경로임
    절대 경로 : 페이지 주소에서, PORT번호 뒤에 '/'부터 요청하려는 주소를 모두 작성하는 것 
    EX)   <form action="/department/insert">

    상대 경로 : 주소 제일 마지막 경로부터 원하는 요청 주소까지를 작성하는 것
    EX)   <form action="insert">
    뒤에서부터 주소 바꿔나가는 것! 
    --%>
    <%-- 상대 경로 방식으로 주소 작성함 --%>
    <form action="insert" method="POST"> <%-- /department/insert를 처리하는 서블릿으로 요청 감. 이번에는 post방식! --%>
    <div>
    부서 코드(DEPT_ID) : <input type="text" name="deptId">
    <%-- input태그로 제출되는 값의 key 이름 지정 : name속성! --%>
    </div>
    <div>
    부서 이름(DEPT_TITLE) : <input type="text" name="deptTitle">
    </div>
    <div>
    지역 코드(LOCATION_ID) : <input type="text" name="locationId">
    </div>
    <%-- 버튼 태그의 type 기본값 : submit --%>
    <button type="submit">추가 하기</button>
    </form>
    <hr><hr><hr>
    
<h1>여러 부서 한 번에 추가 하기</h1>
<%-- 같은 deptId 라는 name속성을 가진 파라미터가 여러개일 경우 파라미터배열로 넘어감 
->index 0,1,2,...번끼리 묶어서 insert 여러번 진행  --%>

<form action="/department/multiInsert" method="post" name="multiInsertForm">
    
    <button type="button" id="addBtn">입력 추가</button>

    <table>
    <thead>
        <tr>
        <th>부서 코드</th>
        <th>부서명</th>
        <th>지역 코드</th>
        <th>삭제버튼</th>
        </tr>
    </thead>

    <tbody id="tbody">
        <tr>
        <td>
            <input type="text" name="deptId">
        </td>
        <td>            
            <input type="text" name="deptTitle">
        </td>
        <td>
            <input type="text" name="locationId">
        </td>
        <th>
            <button type="button" class="remove-btn">삭제</button>
        </th>
        </tr>
    </tbody>
    </table>

    <button>부서 추가 하기</button>
</form>
<script src="/resources/js/insert.js"></script>
<%-- webapp폴더 ==/ 그 안의 resources폴더 그 안의 js폴더 --%>
<%-- web-inf폴더 내부가 아니어서 주소창에 쓰면 들어갈 수 있음 --%>
<%-- 클라이언트가 이 주소 다운받을 수 있게 이 주소로 제공해줌 --%>
</body>
</html>