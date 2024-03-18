const editBtn = document.querySelector("#editBtn");
const deleteBtn = document.querySelector("#deleteBtn");

//수정 버튼 클릭 시
editBtn.addEventListener("click", ()=>{
    location.href="/"; //이 버튼이 클릭되었을 때 목록이 있는 메인페이지 요청하겠다(get방식!!!)

});
//삭제 버튼 클릭 시
deleteBtn.addEventListener("click", e=>{
    //html에서 제공해주는 data속성 이용

    location.href = "/todo/update?todoNo=${todoNo}";
    //get방식 요청 시 쿼리스트링 이용해서 파라미터 todoNo 전달함
    // 요청 주소는 ? 앞까지만!!!

});
