const editBtn = document.querySelectorAll("#editBtn");
//수정 버튼 클릭 시
for( let i=0;i<editBtn.length; i++){
    editBtn[i].addEventListener("click", e=>{
        const budgetNo=e.target.dataset.budgetNo;
        location.href=`/budget/edit?budgetNo=${budgetNo}`;

    });
}



//삭제 버튼 클릭 시
const deleteBtn = document.querySelectorAll("#deleteBtn");
for(let i=0;i<deleteBtn.length; i++){
    deleteBtn[i].addEventListener("click", e=>{
    //html에서 제공해주는 data속성 이용
    const budgetNo=e.target.dataset.budgetNo;
    location.href = `/budget/delete?budgetNo=${budgetNo}`;
    });
}
