<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%-- 반복문 쓰려면 core해야함 --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- 문자열, 함수, list 사용하려면 fn 써야함 --%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Todo List</title>
</head>
<body>
    <h1>Todo List</h1>
    <hr>
    <%-- 메인페이지 들어오자마자 DB에 SQL 보내 요청 보내서 TB_TODO 테이블의 전체 내용 보여지게 할거다 --%>
    <%-- 메인페이지 어떻게 보이게 할 것인지 틀 만들어놓기 --%>
    <form action="/todo/add" method="POST">
        <h4>할 일 추가</h4> <%-- 할 일 추가 기능  --%>
        <%-- 입력할 게 제목과 내용만 하면 된다 --%>
        <%-- 추가 성공 시 메인 페이지로 redirect --%>
        <div>
            제목 : <input type="text" name="todoTitle">
        </div>
        <div>
            <textarea name="todoContent" cols="50" rows="5" placeholder="상세 내용"></textarea>
            <%-- 가로로 50글자 세로로 5글자 들어가는 크기 --%>
        </div>
        <button>추가하기</button>
        <%--  todoTitle, todoContent라는 이름으로 제출--%>
    </form>
    <hr>
    <h3>전체 Todo 개수 : ${fn:length(todoList)}개 
        / 
        완료된 Todo 개수 : ${completeCount}개</h3>
    <%-- list의 size로 하면 전체 Todo개수 얻을 수 있음 --%>
    <%-- fn:length(문자열 ||컬렉션) : 문자열, 컬렉션의 길이를 반환해주는 함수 --%>

    <%-- request에 속성 저장된 completeCount 출력하면 된다 --%>
    <table border="1" style="border-collapse : collapse"> <%-- inline style --%>
    <%-- border-collapse : collapse  == 테두리가 예쁘게 만들어짐 --%>
    <thead>
        <th>할 일 번호</th>
        <th>할 일 제목</th>
        <th>완료 여부</th>
        <th>등록 날짜</th> <%-- 컬럼 네개 --%>
    </thead> 
    <tbody> <%-- 여기에 db에서 가져온 데이터 하나씩 추가할거임 --%>
        <c:forEach items="${todoList}" var="todo"> <%-- 향상된 for문 --%>
        <%-- 거기서 하나씩 꺼낸 것을 todo라고 할거다 --%>
            <tr>
                <td>${todo.todoNo}</td>
                <td><a href="/todo/detail?todoNo=${todo.todoNo}">${todo.todoTitle}</a></td>
                <%-- 블럭치고 alt+w 하면 태그로 감싸짐 --%>
                <%-- 이 게시글 하나만을 조회하고싶은 것 -> PK가 설정된 컬럼(todoNo)이용하기 --%>
                <%-- 쿼리스트링 : 각각의 할 일 별로 todoNo가 달라지므로. 화면이 달라진다 --%>
                <%-- 주소에 값 넣어서 하는 요청 --%>
                <td>${todo.complete}</td>
                <td>${todo.regDate}</td>
            </tr>
        <%-- DB조회 결과를 이용해서 화면 출력 --%>
        </c:forEach>
    </tbody>
    </table>
    <c:if test="${not empty message}" >
    <%-- 메세지 전달받아서 출력하는 구문 --%>
    <%-- 메세지가 존재한다면 --%>
    <script>
        alert("${message}")
    </script>
    <c:remove var="message"></c:remove>
    </c:if>
</body>
</html>