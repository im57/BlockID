# BlockID
DID기반의 학생증

###### DID 기반의 학생증으로 손쉬운 출석체크

- QR코드만 스캔하면 자동으로 출석체크 완료

-------

### For

- 나의 신원정보를 직접 관리

- 스마트 카드로 단말기 인식 필요 없이 QR코드 스캔으로 출석체크


----

### About APP

<br>

#### 시스템 구성도
![image](https://user-images.githubusercontent.com/57435148/98774265-0079a600-242e-11eb-9a86-8d460583f536.png)

- 학생증 발행
  - DID 학생증 발급 요청 : 학교 인증 완료한 사용자의 DID 학생증 발급 요청
  - DID 학생증 발급 이력 저장 : 발급 이력 저장
  - DID 학생증 스키마 저장 : 학생증 DID, verkey 스키마 저장
  - DID 학생증 발급 응답 : 요청한 학생증 발급 완료 후 응답

- 출석 검증
  - 출석체크 요청 : 수업 QR코드 스캔 후 DID 학생증 검증 요청
  - DID 학생증 검증 : DID 학생증 검증 후 응답
  - 출석체크 응답 : DID 학생증 검증 완료 후 응답


<br>

#### 소프트웨어 구성도
![image](https://user-images.githubusercontent.com/57435148/98774466-6108e300-242e-11eb-9854-df8bb0faae90.png)


<br>


#### ERD
![image](https://user-images.githubusercontent.com/57435148/98774771-08861580-242f-11eb-9836-c6fffe975309.png)


<br>

#### 메뉴 구성도
![image](https://user-images.githubusercontent.com/57435148/98774565-98778f80-242e-11eb-8076-8d004cc42f89.png)


<br>


#### 서비스 흐름도
![image](https://user-images.githubusercontent.com/57435148/98774635-c1982000-242e-11eb-8bd8-3a1e7429a22b.png)
