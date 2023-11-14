# pioneerkids
피오니키즈 프로젝트
-------------


## 개발기간
***
~ 2023.12.03


## 개발환경
***
 - java : 17 (jdk 17)
 - OS : windows / mac
 - language : java
 - framework : spring, spring-data-jpa
 - spring Version : 2.7.17
 - server : spring boot
 - database : postgresql 11.0
 - web : thymeleaf , bootstrap , jQuery , html5
  
## 멤버구성
***
- 안성기 : 전체적인 기능 개발 및 관리.
- 이은희 : 게시판 관련 개발담당. (~11/24)

## 개발 관련 메모 
***
하단에 필요한 내용 계속 추가하겠음. 

*************
<details>
<summary> 프로젝트 setting 관련</summary>

<!-- summary 아래 한칸 공백 두어야함 -->

####  프로젝트 setting 관련
 - eclipse , intellij 모두 실행 가능
 - eclipse 에서는 string boot 로 설정 해야함.  (https://diary-developer.tistory.com/9 )
 - eclipse 롬복 설치 진행 필요 (미설치시 프로젝트 에러 발생 : https://congsong.tistory.com/31 )

</details>


<details>

*************
<summary>
DB 관련 정보
</summary>

<!-- summary 아래 한칸 공백 두어야함 -->

#### DB 정보
Host : db.jjeovi.gabia.io
IP : 211.47.74.33
Port : 5432
Database : dbjjeovi

Username : jjeovi
password : ponkids2023!

DB phpPgAdmin : http://db.jjeovi.gabia.io/pgadmin/?_gl=1*15jm1ly*_ga*ODI1NTQ5MzYuMTY5MTk3MTUzMA..*_ga_C1WCKH1K26*MTY5Nzc3MjU3NC4xNC4xLjE2OTc3NzI5MjkuMjAuMC4w


데이터베이스 명 : dbjjeovi


계정 / 비밀번호 : ponkids / ponkids2023!
스키마 : ponkids 
개발 스키마 : ponkids_dev (개발용으로 우선 작업  ponkids 스키마는 추후 운영에서 사용 예정... ) 
</details>


<details>

<summary> 공통소스 관리 가이드</summary>

<!-- summary 아래 한칸 공백 두어야함 -->

*************
#### 공통소스 관리 가이드
 - 공통적으로 사용하는 js,css 내용을 공통소스로 통합시키기 위함
 - 공통소스 경로 : resources/static/common/*
 - 현재기준 js, css 2가지 파일을 공통소스로 사용중
 - 이과장님은 공통소스에 내용추가시 common_eh.css , common_eh.js 파일로 작업해주시면 제가 확인 후 머지하겠습니다.
 
*************
</details>



<details>

<summary> 231113 : DB 접속시 '[name]롤의 최대 동시 접속수를 초과했습니다' 증상 시. (조치방법. 원인분석중..)</summary>

<!-- summary 아래 한칸 공백 두어야함 -->

*************
#### 231113 : DB 접속시 '[name]롤의 최대 동시 접속수를 초과했습니다' 증상 시. (조치방법. 원인분석중..)
 - 현재 가비아 DB서버 사용중. DB서버의 동시세션 개수는 30개
 - 유휴상태인 세션이 계속 서버상에 올라오는데, 이 개수들이 30개가 차면서, 해당 증상이 뜨는 것으로 확인.
 - 유휴 상태인 세션이 뜨는 원인은 분석중입니다.
 - 해결책으로는, 유휴상태세션을 강제로 종료시키는 방법이 있습니다.

 - 
-- 1.해당 쿼리를 날려 pid값을 확인 해놓음. (위에서부터 차례로)
   
-- 현재 유휴 세션 조회. state = idle  

select * from pg_catalog.pg_stat_activity   
where datname = 'dbjjeovi'
and state = 'idle'
order by backend_start asc;


-- 2. 1에서 확인한 pid 값을 대입시켜 세션을 강제종료 ex : SELECT pg_terminate_backend('177283');  

-- 세션 종료 명령어  

SELECT pg_terminate_backend(pid);  



 - 추가적으로 원인파악이되면 조치를 취하겠습니다.!!
 
*************
</details>


<details>

<summary> 231113 : JPAQueryFactory 에러 발생 시 - build.gradle 우클릭 하여 Gradle - Refresh Gradle Project 클릭하여 라이브러리 추가작업 필요.</summary> 

<!-- summary 아래 한칸 공백 두어야함 -->

*************
#### 
 - 231113 : JPAQueryFactory 에러 발생 시 - build.gradle 우클릭 하여 Gradle - Refresh Gradle Project 클릭하여 라이브러리 추가작업 필요.
 

*************
</details>

