<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${budget.budgetNo}번 내역 수정</title>
</head>
<body>
    <h1>${budget.budgetNo}번 내역 수정</h1>
    <hr>
    <form action="/budget/edit" method="POST">
        <%-- 입력할 게 제목과 내용만 하면 된다 --%>
        <div>
            내용: <input type="text" name="budgetContent" value="${budget.budgetContent}">
            <%-- input의 value속성에 적으면 내용이 이미 그렇게 채워져있다 --%>
            <%-- 기존에 있던 제목이 보여질거다 --%>
        </div>
        <div>
            입출금(입금 : + / 출금 : -): <input type="text" name="budgetOption" value="${budget.budgetOption}">
            <%-- input의 value속성에 적으면 내용이 이미 그렇게 채워져있다 --%>
            <%-- 기존에 있던 제목이 보여질거다 --%>
        </div>
        <div>
            변화량 : <input type="number" name="budgetAmount" value="${budget.budgetAmount}">
            
        </div>
        <%-- 할 일 번호를 숨겨서 제출할 때 같이 제출해서 쓸 수 있게 만들기 --%>
        <%-- 이게 있어야 sql에서 할 일 번호 이용해서 할 수 있음 --%>
        <input type="hidden" name="budgetNo" value="${budget.budgetNo}"> <%-- 안되면 여기를 param.budgetNo로 해보기 --%>
        <button>수정하기</button> <%-- 수정하기 버튼 누르면 세 개가 제출된다 --%>
    </form>
    
</body>
</html>