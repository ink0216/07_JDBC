<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>가계부</title>
</head>
<body>
    <h1>가계부</h1>
    <hr>
    <form action="/budget/insert" method="GET">
        <h4>내역 추가</h4>
        <div>
            내용 : <textarea name="budgetContent" cols="50" rows="1" placeholder="내용"></textarea>
        </div>
        <div>
            입출금(입금 : + / 출금 : -) : <input type="text" name="budgetOption">
        </div>
        <div>
            변화량: <input type="number" name="budgetAmount">
        </div>
    
        <button>추가하기</button>
    </form>
    <hr>
    <table border="1" style="border-collapse : collapse"> <%-- inline style --%>
    <thead>
        <th>번호</th>
        <th>상세 내용</th>
        <th>입/출금</th>
        <th>변화량</th>
        <th>잔액</th> <%-- 컬럼 다섯개 --%>
    </thead> 
    <tbody> <%-- 여기에 db에서 가져온 데이터 하나씩 추가할거임 --%>
        <c:forEach items="${budgetList}" var="budget"> <%-- 향상된 for문 --%>
        <%-- 거기서 하나씩 꺼낸 것을 todo라고 할거다 --%>
            <tr>
                <td>${budget.budgetNo}</td>
                <td>${budget.budgetContent}</td>
                <td>${budget.budgetOption}</td>
                <td>${budget.budgetAmount}</td>
                <td>${budget.budgetLeft}</td>
                <td><button id="editBtn" data-budget-no="${budget.budgetNo}">수정</button></td>
                <td><button id="deleteBtn" data-budget-no="${budget.budgetNo}">삭제</button></td>
            </tr>
        <%-- DB조회 결과를 이용해서 화면 출력 --%>
        </c:forEach>
    </tbody>
    </table>
    <script src="/resources/js/main.js"></script>
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