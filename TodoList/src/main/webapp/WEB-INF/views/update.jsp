<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${todo.todoNo}번 할 일 수정</title>
    <%-- 서블릿에서 포워드할 때 todo를 req에 담아서 여기로 보내서 쓸 수 있다! --%>
</head>
<body>
    <h1>${todo.todoNo}번 할 일 수정</h1>
    <hr>
    <form action="/todo/update" method="POST">
        <%-- 입력할 게 제목과 내용만 하면 된다 --%>
        <div>
            제목 : <input type="text" name="todoTitle" value="${todo.todoTitle}">
            <%-- input의 value속성에 적으면 내용이 이미 그렇게 채워져있다 --%>
            <%-- 기존에 있던 제목이 보여질거다 --%>
        </div>
        <div>
            <textarea name="todoContent" cols="50" rows="5" placeholder="상세 내용">${todo.todoContent}</textarea>
            <%-- 가로로 50글자 세로로 5글자 들어가는 크기 --%>
            <%-- textarea도 input의 변형된 형태여서 여기에도 값을 미리 채워져있게 할 수 있다 --%>
            <%-- select에 option 태그도 input 태그 형태라 value 사용 가능 --%>
            <%-- 종료 태그가 없는 input태그에 값을 기록하는 방법 : value --%>
            <%-- 종료 태그가 있는 경우 시작/종료태그 사이의 내용==innerText --%>
            <%-- textarea는 시작/종료태그가 있어서
                textarea에 값을 세팅하고 싶으면 시작/종료태그 사이에 작성하면 된다!(value 사용하지 말기!)--%>
            <%-- textarea는 pre태그처럼 화면에 그대로 보여지는 성질 있다->사이에 공백,엔터 치지 말자--%>
            <%-- textarea는 white-space:pre-wrap; 으로 설정되어 있어
                시작, 종료 태그 사이 공백이 있으면 모두 인식된다!!
                ==>특정 값만 작성해놓고 싶으면 시작/종료 태그 사이에 값을 공백없이 작성해라!!!--%>
            <%-- innerText 해당 영역 내에 쓰여지는 내용 --%>
        </div>
        <%-- 할 일 번호를 숨겨서 제출할 때 같이 제출해서 쓸 수 있게 만들기 --%>
        <%-- 이게 있어야 sql에서 할 일 번호 이용해서 할 수 있음 --%>
        <input type="hidden" name="todoNo" value="${param.todoNo}">
        <button>수정하기</button> <%-- 수정하기 버튼 누르면 세 개가 제출된다 --%>
        <%--  todoTitle, todoContent라는 이름으로 제출--%>
    </form>
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