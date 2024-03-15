/* detail.jsp와 연결할거임 */
/* 나중에는 목적에 따라 js파일 이름도 달라질거임 */

//목록으로 버튼 동작
const goToList = document.querySelector("#goToList");
goToList.addEventListener("click",()=>{
    location.href="/"; //이 버튼이 클릭되었을 때 목록이 있는 메인페이지 요청하겠다(get방식!!!)
});

//완료 여부 변경 버튼 클릭 시 동작
const completeBtn = document.querySelector(".complete-btn");
//클래스가 complete-btn인 첫 번째 요소만 하나만 얻어와서
completeBtn.addEventListener("click", e=>{
    const todoNo = e.target.dataset.todoNo;
    //console.log(todoNo);
    //버튼에 있는 data 속성값을 얻어온거임
    //완료여부 버튼 위에 있는 번호 얻어온거아님
    //data-todo-set 속성의 속성값을 얻어옴
    //e.target == 버튼(이벤트가 발생한 곳)
    //요소.dataset : data-*속성에 저장된 값을 반환함(객체 형태)

    let complete = e.target.innerText; //기존 완료 여부 값 얻어오기
    //let = 변수(값 바꿀 수 있음)
    //const = 상수(값 못바꿈)
    //complete값을 바꿀거여서 변수로 만듦

    //y<->n 반대로 바꿔서 저장하겠다
    //if문 써도 되고 삼항연산자 써도 됨
    // === 동일연산자 (자료형, 값 다 일치할 때)
    complete = (complete === 'Y') ? 'N': 'Y';
    // Y <-> N 서로 변경함

    //완료 여부 수정 요청하기(get방식!)
    location.href=`/todo/changeComplete?todoNo=${todoNo}&complete=${complete}`; 
    //? 앞까지가 요청주소!!!
    // ``(백틱) ==>변수 값을 중간에 삽입하기 편함!
    //요청을 보낼 때 매개변수를 두 개 보낸다(todoNo, complete)
});
//-------------------------------------------------------------------------------------------
//수정 버튼이 클릭되었을 때 동작
const updateBtn = document.querySelector("#updateBtn");
updateBtn.addEventListener("click", e=>{
    //data-todo-no="${todo.todoNo}"
    //html에서 제공해주는 data속성 이용
    const todoNo = e.target.dataset.todoNo;
    //이벤트가 발생한 요소에 있는 dataset속성을 이용해서 todoNo 값을 얻어오겠다

    location.href = `/todo/update?todoNo=${todoNo}`;
    //get방식 요청 시 쿼리스트링 이용해서 파라미터 todoNo 전달함
    // 요청 주소는 ? 앞까지만!!!
});
//-------------------------------------------------------------------------------------------
//삭제 버튼이 클릭되었을 때 동작
const deleteBtn = document.querySelector("#deleteBtn");
deleteBtn.addEventListener("click", e=>{
    //data-todo-no="${todo.todoNo}"
    //html에서 제공해주는 data속성 이용
    //confirm 이용해서 지울까 말까? 하기
    if(confirm("삭제 하시겠습니까?")){
        //예 누르면 true가 됨
        location.href = `/todo/delete?todoNo=${e.target.dataset.todoNo}`;
        /* 이벤트가 발생한 요소에 써져있는 dataset의 값 todoNo를 얻어온다 */
        /* get방식 요청 */
        //get방식 요청 시 쿼리스트링 이용해서 파라미터 todoNo 전달함
        // 요청 주소는 ? 앞까지만!!!
    }
    
});