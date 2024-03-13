<%-- ! : html의 기본 구조 !=jsp 기본구조(맨 위에 jsp 지시자 있어야 함)--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error Page</title>
</head>
<body>
    <h1>${errorMessage}</h1> <%-- 예외 강제 발생 시 세팅한 메세지가 나타남 --%>
    
    <button onclick="history.back()">뒤로 가기</button> <%--WINDOW =  DOM + BOM   --%>
    <%-- 브라우저에서 흰 화면이 DOM이고 그 외의 구역 = BOM --%>
    <%-- <button onclick="history.back"> : 뒤로가기 버튼! --%>
    <button onclick="location.href='/'">메인 페이지</button>
</body>
</html>
