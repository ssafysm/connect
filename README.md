## 📕 Smart Store(이름 미정)

## 👨‍🏫 프로젝트 소개

### SSAFY 1학기 최종 프로젝트

- 안드로이드 컴포넌트의 구조를 이해하고 활용할 수 있다.
- SpringBoot 서버를 구현하며, 안드로이드 애플리케이션과의 Json 통신 구조를
  이해하고 활용할 수 있다.
- SpringBoot 서버와 FCM을 활용하여 Foreground/Background 앱 푸시를 구현할 수
  있다.
- 안드로이드 근거리 통신기술(NFC, BLE, Beacon)을 이해하고 이를 활용하여 다양한
  서비스를 구현할 수 있다.
- 안드로이드 JetPack 라이브러리를 활용하여 프로젝트를 확장성 있게 구현할 수 있다.

## ⏲️ 개발 기간

- 2024.11.19(화) ~ 2024.11.26(화)

## 🧑‍🤝‍🧑 개발자 소개

- 박성민, 정지원

## 💻 개발환경

- IDE : Android Studio
- 언어 : Kotlin, XML
- Framework : Android
- Git : GitLab

## ⚒ 기술스택
| Category     | TechStack                                                                |
|--------------|--------------------------------------------------------------------------|
| Architecture | MVVM                                                                     | 
| Network      | Retrofit, OkHttp, Moshi                                                  | 
| DI           | Hilt                                                                     |
| Asynchronous | Coroutines                                                               | 
| Jetpack      | DataBinding, Navigation, Fragment, Lifecycle, Material Design Components | 
| Image        | Coil, Glide                                                              |
| Logging      | Timber                                                                   | 

## 📌 구현한 기능
#### 기본
  - 회원 관리
    - 회원 가입 시 아이디 중복 체크, 회원 정보 추가 가능
    - 자동 로그인 및 로그아웃 가능
  - 상품 관리
    - 상품 전체 목록 출력 가능
    - 상품별로 이름, 이미지, 단가, 총 주문 수량 출력 가능
  - 주문 관리
    - 상품 장바구니 등록 가능
    - 주문 번호에 기반한 주문 요청 가능, 이 때 주문 상세 내용 포함
  - 상품 평가 관리
    - 상품별 상품평가 작성, 수정, 삭제, 조회 가능
  - 등급 관리
    - 회원의 스탬프 개수에 따른 등급 분류 가능
  - 비기능적 요구사항
    - RestController로 RESTful API 제공 가능
  - 모바일 특화 기능
    - GPS, Google Map을 활용하여 매장과의 거리 제공
    - FCM을 이용한 알림 기능 제공
  - 네트워크
    - NFC 태깅 시 앱 실행 가능, 테이블 번호 받아와 주문 가능
    - Beacon을 활용하여 출석 가능
  - 최근 주문 관리
    - 최근 1개월 간 주문 내역 재주문 가능

#### 심화
  - GPT API를 활용한 ChatBot 시스템
    - 기본적으로 채팅 및 사진 업로드 가능
    - 인기 메뉴, 스터디 플랜 추가, 플랜 현황 보고 및 플랜을 토대로 한 어드바이징 가능
  - 심화된 FCM 기능 제공
    - 주문 완료되면 알림 받음
    - 플랜 현황 보고 시각이 되면 알림 받음
    - 알림 수신 허용/차단 가능
  - 근처 매장 리스트 제공 및 매장 선택 기능
    - 5초마다 GPS를 활용한 거리 계산
    - 검색 모드 & 지도 모드 전환 가능
    - 지도에서 마커 터치 시 해당 매장 선택 가능
  - 테마별 UI 변경 기능
    - 기본 + 계절별 테마 변경 가능
  - 회원 비밀번호 변경 기능
    - 회원 비밀번호 변경 가능
  - 월별 출석 여부 확인 기능
    - 달력으로 월별 출석 여부 확인 가능
    - 최근 몇 일 연속으로 출석했는지 확인 가능
  - 쿠폰 지급 기능
    - 회원가입, 연속 출석 등 특수한 행위 수행 시 보상으로 쿠폰을 지급받음
  - 신규 도입 UI
    - ViewPager2와 PageTransformer로 자동 스크롤 가능한 Carousel UI 구현
    - CollapsingToolbarLayout으로 스크롤 상태에 따라 확장/축소되는 Toolbar 구현
    - TransitionName을 활용한 Hero Transition 구현
    - Bottom Layout으로 장바구니, 평가 등 리스트가 가려지는 경우, 스크롤 시 Bottom Layout이 사라지는 애니메이션 구현

## ⚙️ 개발 결과
 - 추후 작성