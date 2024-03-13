/* 삽입과 관련된 js */

//id가 addBtn인 버튼 요소 얻어오기
const addBtn=document.querySelector("#addBtn"); //아이디가(#) addBtn인 요소를 얻어와라
//querySelector : css선택자를 이용해서 그것과 일치하는 요소를 찾는 애!

//id가 tbody인 요소 얻어오기
const tbody = document.querySelector("#tbody");

//addBtn이 클릭 되었을 때(이벤트 리스너) 추가
addBtn.addEventListener("click", ()=>{
    //이벤트 핸들러 : 이벤트가 감지되었을 때 수행할 동작을 지정
    //한 줄을 추가할거임
    //아이디가 tbody인 것 얻어와서 tr 요소 만들고 그 안에 td 만드는데 그 안에 input 태그랑 버튼 만들어서 한 줄 만들고
    //tbody 안에다 추가한다

    //한 행을 나타내는 tr 요소 생성하기
    const tr = document.createElement("tr"); 

    //tr안에 들어가야 할 것들

    //name 속성 값이 저장된 배열 하나 생성하기(효율적으로 하기 위해서!)
    const names=['deptId', 'deptTitle', 'locationId'];

    //이 배열 이용해서 배열 요소 순차접근하는 향상된 for문 작성
    for(let name of names){
        //names 배열에서 하나씩 꺼낸 것을 name에 저장한다
        const td = document.createElement("td"); //td 요소 만들고
        const input = document.createElement("input");  //input 태그 만들고

        input.type="text"; //만들어진 input의 type="text" 설정
        input.name= name;//만들어진 input의 name속성값 설정
        //하나씩 꺼낸 배열 요소를 name속성 값으로 설정
        //세바퀴 돌면서 input이 세개 만들어져서 각각 name속성이 달라짐

        //조립하기
        td.append(input);//td안에 input이 들어간다
        tr.append(td); //td는 tr에 들어간다 
        //역순으로 조립하기!
    }
    //얘네들은 규칙성에 어긋나서 따로 만들어야한다
    //규칙성이 어긋난 th>button 따로 만들기!
    const th = document.createElement("th");
    const button = document.createElement("button");

    //새로 생기는 버튼들 설정하기
    //버튼 그냥 만들면 submit으로 돼서 제출돼버려서 button 타입 설정
    button.type="button"; //type="button" 설정
    button.classList.add("remove-btn"); //클래스 추가하기
    button.innerText = "삭제"; //textcontent로 해도 됨
    //삭제 버튼 클릭 시 동작하는 이벤트도 추가하기
    button.addEventListener("click", removeFn); //클릭 시 removeFn 함수의 내용('function'부터 끝까지)을 이 위치로 가져와라!
    // <함수명 과 함수명() 의 차이>
    //함수명 : 함수 코드(정의한 내용)를 현재 위치로 가져오기
    //함수명() : 함수 호출 (함수 내용을 그대로 수행하라는 의미)



    //조립
    th.append(button);
    tr.append(th);
    tbody.append(tr); //#tbody에 조립된 한 줄 추가하기
});
//이벤트(동작)리스너 : 동작을 감지한다
/* 삭제 버튼 클릭 시 해당 행 제거하는 함수 정의하기(그 버튼의 부모의 부모?요소를 제거) */
function removeFn(e){
//매개변수 e 전달받음
//e : 이벤트 객체
//버튼에게 이벤트 줄 거여서
//e.target = 이벤트가 발생한 요소 == 삭제버튼
const tr = e.target.parentElement.parentElement;
//parentElement : 부모요소
//삭제 버튼의 부모요소 = th태그
//또 그 부모가 tr태그임
tr.remove(); //tr 요소 제거
//tr 요소 제거하는 함수 만듦 ->이 함수를 누군가가 호출하면 써먹을 수 있음
}
//원래 존재하던 삭제 버튼에도 removeFn 함수 동작 추가해주기
//위의 코드는 입력 추가 버튼 클릭 시 새로 생긴 삭제 버튼에만 removeFn 함수 추가해준 거여서 
document.querySelector(".remove-btn").addEventListener("click", removeFn);
