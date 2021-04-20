
## 개발 프레임웍크
- Spring Boot 2.4.5
- Java 11
- Intellij Tools 사용

## 문제해결 방법
- Spring Data JPA를 통해 Entity 구성 및 Schema 구성 (임베디드 h2 인메모리 디비 사용)
- 스프링 배치를 통해 csv 파일을 읽어 서버 기동 시 초기 데이터 세팅
- Spring Data JPA를 통해 구현된 JPA 구현체를 통해 DB 데이터 조회
- 서비스 단 로직을 통해 데이터 가공 혹은 Native Query를 통해 가공된 데이터 조회
- Repository 별 Test 클래스 작성
- Service 별 Test 클래스 작성
- Custom Exception Handler를 통해 에러 처리

## 빌드 및 실행 방법
- **java 11 설치 및 확인**
   - `$ java --version`  


- **소스 받기**
  - git clone https://github.com/heybys/securities-world.git
  


- **Windows 인 경우,**
  - `\securities-world > .\gradlew.bat build`
- **Mac/Linux 인 경우,**
  - `/securities-world $ ./gradlew build`
    

- **서버 실행** (Port 겹침 우려로 인해 8088로 실행하도록 함)
  - 📌 단 !!!! Windows 환경에서 실행하는 경우, `/securities-world/src/main/resources/` 경로에 가서 csv 파일을 기존과 동일한 파일명으로 재저장(다른 이름으로 저장) 후 서버 기동 바람. 건너뛸 시  encoding 문제로 네번쨰 과제 확인이 어려울 수 있음.   
  - `/securities-world/build/libs $ java -jar securities-world-0.0.1-SNAPSHOT.jar`
  

- **H2 인메모리 DB 및 Batch 작업으로 인한 데이터 생성 확인**
  - http://localhost:8088/h2-console 접속
  - JDBC URL 항목 변경 → jdbc:h2:mem:testdb
  - connect 버튼 클릭 및 확인 

## API 과제 테스트
HTTP Client (ex. Postman)으로 API 요청 및 응답 확인

- 첫번째 과제
  - GET, http://localhost:8088/account/statistics
- 두번째 과제
  - GET, http://localhost:8088/account/dormancy
- 세번째 과제
  - GET, http://localhost:8088/branch/statistics/year
- 네번쨰 과제
  - POST, http://localhost:8088/branch/merge, Body : { "from" : "분당점", "to" : "판교점" }
  - GET, http://localhost:8088/branch/statistics?brName=판교점
  - GET, http://localhost:8088/branch/statistics?brName=분당점
