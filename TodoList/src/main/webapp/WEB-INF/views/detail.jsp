<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${todo.todoTitle}</title>
    <%-- 할 일의 제목을 페이지의 제목으로 삼기! --%>
    <style>
        table{
            border-collapse : collapse; <%-- 테이블 예쁘게 만들기 --%>
        }
        .todo-content{
            <%-- white-space : pre-wrap; :공백 어떻게 처리할까? pre태그 처럼 처리해라  --%>
            <%-- HTML 에 작성된 모양 그대로 화면에 출력 --%>
            white-space : pre-wrap;
        }
    </style>
</head>
<body>
    <h3>${todo.todoTitle}</h3>
    <table border="1">
        <tr>
            <th>번호</th>
            <td>${todo.todoNo}</td>
        </tr>

        <tr>
            <th>등록 날짜</th>
            <td>${todo.regDate}</td>
        </tr>

        <tr>
            <th>완료 여부</th>
            <td>
                <button type="button" 
                class="complete-btn"
                
                
                <%-- data-* 속성 (data로 시작하는 속성)
        - 데이터에 대한 확장성을 고려하여 설계된 속성
        - js에서 요소.dataset 을 이용해 해당 값을 얻어갈 수 있음
        --%>    data-todo-no="${todo.todoNo}" <%-- data로 시작하는 속성 추가됨 --%>
                >${todo.complete}</button>
                <%-- 버튼 클릭하면 y<->n 바뀌게 만들기 --%>
            </td>
        </tr>
        <tr>
            <th>내용</th>
            <td class="todo-content">
                ${todo.todoContent}
                <%-- 이 클래스가 css로 내용에 엔터 있으면 그대로 보여지게 해줌 --%>
            </td>
        </tr>
    </table>
    <button id="goToList">목록으로 이동</button>
    <button id="updateBtn" data-todo-no="${todo.todoNo}">수정</button>
    <%-- 이걸 수정할거야 삭제할거야 ->요청 보낼 때 js에서 todoNo를 값으로 쓸 수 있게 만들어둠 --%>
    <%-- 제목이랑 내용 변경할 수 있게 하고 변경 버튼 만들어서 버튼 누르면 다시 detail 상세조회로 돌아오게 하기 --%>
    <button id="deleteBtn" data-todo-no="${todo.todoNo}">삭제</button>
    <script src="/resources/js/detail.js"></script> <%-- 클라이언트 쪽에서 js 파일 다운받아서 연결한다 --%>
    <c:if test="${not empty message}" >
    <%-- 메세지 전달받아서 출력하는 구문 --%>
    <%-- 메세지가 존재한다면 --%>
    <script>
        alert("${message}")
    </script>
    <c:remove var="message"/> <%-- 이렇게 태그 여는 것과 닫는 것 사이에 내용 필요없는 태그들은
                                        열자마자 바로 닫는 형태로 쓸 수 있다--%>
    <%-- <c:remove></c:remove>  이렇게 써도 된다--%>
    </c:if>
</body>
</html>