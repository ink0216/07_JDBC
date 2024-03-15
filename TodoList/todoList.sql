--TB_TODO 테이블 생성하기
CREATE TABLE TB_TODO(
	TODO_NO NUMBER CONSTRAINT TB_TODO_PK PRIMARY KEY,
	--컬럼 넘버타입 제약조건 이름 PRIMARY KEY 컬럼레벨  : TODO 번호
	TODO_TITLE VARCHAR2(50) NOT NULL, --가변길이 : TODO제목
	TODO_CONTENT VARCHAR2(4000) NOT NULL, --VARCHAR2 최대크기로 해도 됨(남으면 줄어듦)
	COMPLETE CHAR(1) DEFAULT 'N'
		CONSTRAINT COMPLETE_YN CHECK(COMPLETE IN ('Y','N')),
		--1바이트 한글자만 저장 가능한데 기본값은 N이고, Y나 N만 입력할 수 있는 CHECK 제약조건
	REG_DATE DATE DEFAULT SYSDATE --등록일 기본값 현재 날짜
);

--주석
COMMENT ON COLUMN TB_TODO.TODO_NO IS '할 일 번호'; --컬럼에 대한 설명
COMMENT ON COLUMN TB_TODO.TODO_TITLE IS '할 일 제목'; 
COMMENT ON COLUMN TB_TODO.TODO_CONTENT IS '할 일 내용'; 
COMMENT ON COLUMN TB_TODO.COMPLETE IS '할 일 완료 여부(Y/N)'; 
COMMENT ON COLUMN TB_TODO.REG_DATE IS '할 일 등록일'; 

--할 일 번호(TODO_NO) 번호 생성기(SEQUENCE) 객체 생성하기
CREATE SEQUENCE SEQ_TODO_NO NOCACHE; 
--NOCACHE : 미리 숫자 만들어놓지 않을거야(얘가 숫자를 20개씩 만들어둠)
--한번 호출하면 1번 번호 생성되고 2~21번까지 미리 만들어놓음
--컴퓨터 끄면 만들어놓은거 버려서 다시 껐다 키면 숫자가 2번이 나와야하는데 22번이 나와서 안좋음

--샘플 데이터 넣기
INSERT INTO TB_TODO 
VALUES(
--할일 번호를 시퀀스를 이용해서 만들거임
SEQ_TODO_NO.NEXTVAL, '테스트 제목1', '테스트 내용1', DEFAULT, DEFAULT
);
INSERT INTO TB_TODO 
VALUES(
--할일 번호를 시퀀스를 이용해서 만들거임
SEQ_TODO_NO.NEXTVAL, '테스트 제목2', '테스트 내용2', DEFAULT, DEFAULT
);
INSERT INTO TB_TODO 
VALUES(
--할일 번호를 시퀀스를 이용해서 만들거임
SEQ_TODO_NO.NEXTVAL, '테스트 제목3', '테스트 내용3', DEFAULT, DEFAULT
);
SELECT * FROM TB_TODO;
COMMIT;
/*DATE타입을 자바에서 가져와서 다루려면 java.sql 패키지에서 제공하는 Date 객체를 이용해야 함
 * 근데 받아올 수는 있는데 그 모양을 가공하거나 그 값을 db로 집어넣는게 어려워서 이걸 잘 안씀!!!
 * --> db에서 date타입을 문자열로 변환해서(TO_CHAR 이용) 
 * DATE를 원하는 모양의 문자열로 만드는 함수
 * 이걸 자바한테 넘기면 자바에서 String으로 받으면 됨*/

--할 일 목록 조회하기(내용은 말고 제목만 보여지게 하기)
SELECT TODO_NO, TODO_TITLE, COMPLETE,
	TO_CHAR(REG_DATE,'YYYY-MM-DD HH24:MI:SS') REG_DATE --별칭
	--24시간 체계로 표현하겠다 
	--REG_DATE 를 우리가 원하는 형식의 문자열로 만들겠다 
FROM TB_TODO
ORDER BY TODO_NO ;

--특정 할 일 1개를 상세 조회하기(==>PK를 조건절에 이용하는게 가장 좋다!!!)
SELECT TODO_NO, TODO_TITLE, TODO_CONTENT, COMPLETE,
	TO_CHAR(REG_DATE,'YYYY-MM-DD HH24:MI:SS') REG_DATE --별칭
FROM TB_TODO
WHERE TODO_NO=1; --PK 적용된 컬럼을 이용해서 하나만 조회할거야

--완료된 할 일 개수 조회하기
SELECT COUNT(*)
FROM TB_TODO
WHERE COMPLETE='Y';

--특정 할 일의 완료 여부 수정하기
UPDATE TB_TODO 
SET COMPLETE = 'Y' --여기랑
WHERE TODO_NO=1; --여기의 값이 변함 ->이 두개를 전달받아오기
--완료여부 버튼 눌러졌을 때 CONPLETE의 값과 번호를 DB까지 넘겨서 바뀌게 하자

--할 일 수정
UPDATE TB_TODO SET  
TODO_TITLE=?,
TODO_CONTENT=?
WHERE TODO_NO=? 






