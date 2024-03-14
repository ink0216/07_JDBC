// selectAll.jsp에서 쓰는 js
/* selectAll.js */
/* 클라이언트가 이 파일 다운로드 받아서 해석함 */
//클래스 = .  // 아이디=# // 전체=* // 특정 속성 =[] 

//삭제 버튼 클릭 시
//클릭된 삭제 버튼의 해당 행의 부서코드 얻어오기 ->PK제약조건 걸린 컬럼으로 검색하면 PK설정과 동시에 인덱스 만들어져서 검색 속도 빠름
// -> 행 검색 시 PK가 설정된 컬럼으로 하는게 좋음!

//delete버튼들 모두 얻어와 배열로 반환 받기
//배열이므로 -> deleteBtnList.addEventListener() 안됨!!!!
//왜 ? ->addEventListener는 특정 요소에서 일어나는 동작을 감지하는 것이므로 앞에 요소가 써져있어야 하는데, 앞이 배열이므로 안됨
//이벤트 리스너는 DOM 요소에 추가해야 동작하는데,
//deleteBtnList는 DOM 요소가 아니라 유사배열인 NodeList여서 
//이벤트 리스너를 배열에 추가할 수 없다!!!
//해결 방법 -> 배열 내 각 인덱스 요소(==DOM 요소)(버튼)
//                  -> 배열 내 요소를 하나씩 꺼내서 이벤트 리스너를 추가하면 된다!!!!
const deleteBtnList = document.querySelectorAll(".delete-btn"); //All붙여야 nodeList 배열 형태로 얻어와진다
for(let btn of deleteBtnList){
    //향상된 for문
    //하나씩 써내서 btn이라고 부르겠다
    btn.addEventListener("click", e=>{
        //매개변수가 하나인 경우(e)에는 소괄호를 생략해서 쓸 수 있다

        //클릭된 삭제 버튼이 존재하는 행의 부서코드 얻어오기
        //const deptId = e.target.parentElement.parentElement.children[1].innerText;
        //버튼의 부모의 부모 tr태그의 모든 요소들을 얻어와서() 여러개면 배열로 얻어와져서
        //그 배열의 1번 인덱스의 안에 써져있는 내용을 얻어와라
        //클릭한 버튼==e.target==이벤트가 발생한 요소

        //위의 것을 더 짧게 하기! ->closest 이용(가장 가까운 것을 찾는 코드)
        const deptId = e.target.closest("tr").children[1].innerText;
        //요소.closest("css선택자")
        //지정된 요소의 상위 요소(부모 방향, 루트(최상위)까지) 중에서
        //css 선택자가 일치하는 요소를 요소와 가까운 곳 부터 찾아서 찾을 때까지 검색해서
        //일치하는 요소가 있으면 해당 요소를 반환 / 없으면 null
        //==삭제버튼보다 위쪽부터 검색 (th -> tr ->그 부모, ....)
        console.log(deptId); //이렇게 하면 삭제버튼 클릭한 행의 부서코드가 콘솔에 찍힘

        //이 부서코드를 db까지 넘겨서 검색하기 전에
        //confirm() : 확인 취소같은 alert같은 창이 뜬다
        if(confirm(`${deptId} 부서를 정말 삭제하시겠습니까?`)){
            //확인 클릭했을 때 동작하는 코드 -> confirm해서 alert처럼 뜬 창에서 확인 버튼 누르면 true를 반환하는듯!!
            //클라이언트가 서버에게 이거 삭제해줘 하고 요청 보내는 방법
            //==주소를 이용해서 요청을 보내야 한다

            //삭제 요청 보내기 1) (쉬운데 권장하지 않는 방법!):  location.href 이용
            //location.href = "/department/delete?deptId="+deptId;
            /* deptId가 A9인 삭제버튼 클릭 시의 주소
            http://localhost/department/delete?deptId=A9 주소 중
            /department/delete : 요청 주소
            ?deptId=A1 : 쿼리스트링(주소에 담긴 데이터, 주소에 데이터 담아서 보내는 것) */
            //location.href로 요청하면 get방식 요청이 된다!
            //브라우저 주소창에 입력하는 방법도 get방식 요청이다
            //  ->삭제의 경우,
            //  ->임의의 사용자가 마음대로 주소에 b5도 삭제 해야지~하고 삭제 요청을 마음대로 보내는 문제가 발생할 수 있다!!!! 

            //삭제 요청 보내기 2) (form태그를 만들어서 input값을 담아서 POST방식으로 요청 보내기)
            //form 생성
            const form = document.createElement("form"); //form태그 없으니까 만들기
            form.action="/department/delete";
            form.method="POST"; //post방식으로 요청을 한다

            console.log(form);

            //form 안에 input type="hidden" 생성하기
            const input = document.createElement("input");
            //input에 deptId 값 담기
            input.type="hidden"; //hidden == 눈에 안보이는 애
            input.value=deptId; //삭제 버튼 클릭했을 때 그 행의 부서코드 대입
            input.name="deptId"; //제출되는 파라미터의 key값 지정

            //form 자식으로 input 추가하기
            form.append(input);

            //body 태그 제일 밑에 form 추가하기
            //form을 추가하려면 body가 존재해야해서
            //바디태그 얻어와서 form태그 넣기
            document.querySelector("body").append(form);

            //화면에 추가된 form 제출하기
            form.submit(); //제출버튼 없어도 이렇게 js로 강제 제출할 수 있다->페이지 넘어감
            //삭제의 경우 get방식으로 하면 안되고 post방식으로 해야하는데 form태그가 없으면 만들어서 제출하기
            //데이터를 주소가 아닌,겉으로 안보이는 body태그에 담아서 보내는 post방식 이용!! 
            //인터넷 주소에 계속 쳐봤자 post방식 되지 않음
        }else{
            //취소 클릭했을 때 동작하는 코드
            alert("삭제 취소");
        }
        //`${deptId}` : deptId에 저장된 값이 이 자리에 출력됨
    })
}
//------------------------------------------------------------------------------
//.update-btn 요소 모두 얻어오기
const updateBtnList = document.querySelectorAll(".update-btn");

//updateBtnList 배열의 모든 요소에 순차접근하며 이벤트 리스너 추가하기
//이번에는 향상된 for문 말고
//NodeList로 얻어와지는데, NodeList는 forEach(이게 기능 가장 많음)라는 향상된 for문을 제공해서 이거 써보기
// updateBtnList.forEach((요소,인덱스)=>{})
updateBtnList.forEach((btn,index)=>{
    //하나씩 꺼낸게 btn에 저장돼서 이 중괄호 안에서 btn을 쓸 수 있게 된다
    btn.addEventListener("click", e=>{
        
    // const tr = e.target.parentElement.parentElement 이렇게 해도되고
    // const tr = e.target.closest("tr") 이렇게 해도되고
    // == 부모 요소 중 가장 가까운 tr태그 찾기
    const tr = e.target.closest("tr");

    //부서 코드 얻어오기
    const deptId = tr.children[1].innerText; //1번 인덱스 자식

    //JS에서 요청하기(Get방식!!!)
    //이러면 데이터가 주소에 담겨서 삭제 할 때엔 안좋은데 수정은 그나마 괜찮음
    location.href = "/department/update?deptId="+deptId; //이런식으로 deptId의 정보를 보내겠다
    //http://localhost/department/update?deptId=B3 주소가 이렇게 돼야 함(요청은 잘 보낸거임->서블릿만 잘 만들면 됨)
    //404-찾을 수 없음 == 이 요청을 처리할 서블릿이 없다
    });
}) 

