# 개발새발(Rough Code)
## [개발새발 사이트 바로가기](https://rough-code.com)

개발자 지망생과 신입 개발자를 위한 프로젝트 공유 및 코드 리뷰 사이트

## 💡 기획 배경
토이 프로젝트는 피드백을 받기 어려워 한 번 만든 후에 폐기하는 경우가 대부분입니다. 열심히 개발한 프로젝트를 많은 사람들이 이용해 보고, 다양한 피드백을 받아 보고, 좋은 인사이트를 얻어 프로젝트를 개선해보는 경험은 하기가 쉽지 않습니다. 저희는 개발자 지망생과 주니어 개발자를 위한 “프로젝트 공유 공간” 과 “코드 공유, 코드 리뷰 및 의견 소통 공간”을 제공하고자 합니다.

## 🚩 대상
개발자 지망생, 신입 개발자

## 🌟 기대 효과
- 토이 프로젝트를 공유하고 서로에게 피드백을 자유롭게 주고 받을 수 있습니다.
- 프로젝트와 코드를 리뷰하여 발전시켜나가는 과정을 기록하고 포트폴리오로 사용할 수 있습니다.
- 깃허브 gist를 통한 통계 등록을 통해 개발자로서의 리팩토링 경험, 타인의 코드를 리뷰해준 경험 등을 어필할 수 있습니다.

## 💻 주요 기능
1. 토이 프로젝트 공유 및 피드백 페이지
    1) url 또는 apk에 대한 안전성 검사
    2) 프로젝트 버전 히스토리 기능 제공
    3) 사용한 기술 스택에 따른 필터링 제공
2. 코드 공유 및 리뷰 페이지
    1) 원본 코드와 수정 코드의 비교 UI 제공
    2) 리뷰에 대한 의견 작성 기능 제공
    3) 코드 버전 히스토리 기능 제공
    4) 사용 언어에 따른 필터링 제공
3. 프로필페이지
    1) 프로젝트 공유 횟수, 프로젝트 리팩토링 횟수(버전 업그레이드 횟수), 
       코드 리뷰 횟수, 코드 리뷰 반영 횟수(버전 업그레이드 횟수)에 대한 통계 제공
    2) 이메일 인증을 통한 실시간 알림 제공
    3) 내가 작성하거나 리뷰를 남긴 게시물, 즐겨찾기한 게시물 바로가기 제공

## 🔧 기술 스택
**Backend - Spring**

```bash
- Java openjdk-11
- Spring boot 2.7.1
- Spring Security 2.7.10
- Spring Data JPA 2.7.10
- Querydsl 5.0.0
- JWT 0.11.2
- Spring Data Redis 2.7.10
```

**Frontend - Next.js**

```bash
- Node.js 18.16.0
- React 18.2.0
- Next.js 13.2.4
- TypeScript 5.0.4
- Styled Components 5.3.9
- Tanstack Query 4.29.5
- Zustand 4.3.7
- React Hook Form 7.43.9
- Zod 3.21.4
- ESLint 8.38.0
- Prettier 2.8.7
- Axios 1.3.6
- Monaco Editor 0.38.0
- Tiptap Editor 2.0.3
```

**CI / CD**

```bash
- AWS EC2 / Ubuntu 20.04.6 LTS (Focal Fossa)
- Docker 23.0.5
- Docker Compose 1.25.0
- SSL (certbot) 1.1.1f
- Jenkins 24.0.2
- NGINX 1.23.4
- sonarqube 9.9.1.69595
- pmd 3.4.0
- checkstyle 10.9.3
```


## 📑 프로젝트 산출물

<!-- 
- [와이어프레임](roughcode-image/docs-wireframe.PNG)
- [ERD](roughcode-image/docs-erd.png) 
- [API 명세서](roughcode-image/docs-api.png)
- [플로우차트](roughcode-image/docs-flowchart.jpg)
- [아키텍쳐](roughcode-image/docs-architecture.png)
- [스토리보드](roughcode-image/docs-storyboard.JPG)
-->

<details>
<summary>와이어프레임</summary>
<a href="https://www.figma.com/file/SozgBHvf76lJnwterFq6vd/%EA%B0%9C%EB%B0%9C%EC%83%88%EB%B0%9C-%EC%99%80%EC%9D%B4%EC%96%B4%ED%94%84%EB%A0%88%EC%9E%84?type=design&node-id=124-1745&t=wxRDTyiYzkruFxz2-0"><img src="roughcode-image/docs-wireframe.PNG"/></a>
</details>

<details>
<summary>ERD</summary>
<a href="https://www.erdcloud.com/d/6iEY2W5gtL2WQsQt4"><img src="roughcode-image/docs-erd.png"/></a>
</details>

<details>
<summary>API 명세서</summary>
<a href="https://spectrum-whistle-250.notion.site/API-6ffc4ebcecb648f68c669662ba6999bd"><img src="roughcode-image/docs-api.png"/></a>
</details>

<details>
<summary>플로우차트</summary>
<a href="https://www.figma.com/file/eYg6bCFtj65F21U5ZfMKDD/%EA%B0%9C%EB%B0%9C%EC%83%88%EB%B0%9C-%ED%94%8C%EB%A1%9C%EC%9A%B0%EC%B0%A8%ED%8A%B8?type=whiteboard&node-id=0-1"><img src="roughcode-image/docs-flowchart.jpg"/></a>
</details>

<details>
<summary>아키텍쳐</summary>

![아키텍쳐](roughcode-image/docs-architecture.png)
</details>

<details>
<summary>스토리보드</summary>
<a href="https://www.figma.com/file/SozgBHvf76lJnwterFq6vd/%EA%B0%9C%EB%B0%9C%EC%83%88%EB%B0%9C-%EC%99%80%EC%9D%B4%EC%96%B4%ED%94%84%EB%A0%88%EC%9E%84?type=design&node-id=0-1&t=hl7qhMJuREdkFbCJ-0"><img src="roughcode-image/docs-storyboard.JPG"/></a>
</details>  


## 🔎 서비스 화면
### 메인 페이지
- 사이트 소개와 함께 프로젝트, 코드 리뷰, 로그인 바로가기 버튼이 존재합니다.

    - 로그인 전
    ![메인 페이지](roughcode-image/main.png)
    - 로그인 후
    ![메인 페이지-알람](roughcode-image/main-alarm.gif)


### 프로젝트 공유

- 프로젝트는 태그별 필터링이 가능하고 작성일시, 좋아요, 피드백 순으로 정렬이 가능합니다. 무한스크롤로 구현하였습니다.

![프로젝트](roughcode-image/project.gif)
    - 검색
    ![프로젝트](roughcode-image/project-search.gif)


- 프로젝트 상세 페이지에서는 버전별 업데이트 정보를 확인할 수 있고, 피드백을 남기거나 신고할 수 있습니다.
    - 프로젝트 상세
    ![프로젝트-상세](roughcode-image/project-detail.png)
    - 프로젝트 버전별 정보
    ![프로젝트-버전](roughcode-image/project-version.gif)
    - 프로젝트 피드백
    ![프로젝트-피드백](roughcode-image/project-feedback.gif)
    - 프로젝트 피드백 신고
    ![프로젝트-피드백-신고](roughcode-image/project-feedback-complain.gif)

- 프로젝트의 URL을 클릭하면 서버가 다운되었는지 확인 후에 열려있는 경우 링크로 연결하고 닫혀있는 경우는 자동으로 닫힘 신고 처리를 합니다.

![프로젝트-url-체크](roughcode-image/project-url-check.gif)

- 유해 사이트 링크가 등록되는 것을 방지하기 위해 프로젝트 등록 시 URL 검사를 필수로 진행합니다.
    - 프로젝트 등록
    ![프로젝트-등록](roughcode-image/project-insert.gif)

- 내 프로젝트에 달린 피드백 목록에서 반영하여 개선한 피드백을 선택해 등록할 수 있습니다. 
![프로젝트-버전업](roughcode-image/project-versionup.gif)


### 코드 리뷰 
- 코드는 태그별 필터링이 가능하고 작성일시, 좋아요, 피드백 순으로 정렬이 가능합니다. 무한스크롤로 구현하였습니다.
![코드 리스트](roughcode-image/code.png)

- Github URL을 입력하여 코드 리뷰를 요청할 코드를 간편하게 불러옵니다.
![코드리뷰 등록](roughcode-image/code-insert.gif)

- 코드 상세 페이지에서는 버전별 업데이트 정보를 확인할 수 있고, 리뷰를 남길 수 있습니다.
- diff 에디터를 통해 기존 코드와 리뷰 작성자가 수정한 코드를 한눈에 비교할 수 있습니다.
    - 리뷰 남기기
    ![코드 리뷰](roughcode-image/code-review.gif)
    - 리리뷰 남기기
    ![코드 리리뷰](roughcode-image/code-rereview.gif)

- 코드와 연관된 프로젝트가 있다면 기존에 등록한 프로젝트를 코드와 연결할 수 있습니다.
![코드 연결](roughcode-image/code-project-connect.gif)


### 마이페이지
- 활동 업적을 기록한 스탯 카드를 제공합니다. 이를 Github 프로필에 표시할 수 있습니다.
![마이페이지](roughcode-image/mypage.png)

- 이메일을 인증하면 실시간 알림을 제공합니다.
![마이페이지 이메일 인증](roughcode-image/mypage-emailcheck.gif)
    - 전송된 이메일
    ![코드](roughcode-image/email-validation.jpg)
    - 알림
    ![코드](roughcode-image/email-alarm.jpg)

- 내가 작성한, 리뷰한, 즐겨찾기한 프로젝트와 코드 목록을 조회합니다.
![마이페이지 게시물들](roughcode-image/mypage-posts.gif)


## 🎬 UCC
아래 사진을 클릭하면 동영상 링크로 이동합니다!
<a href="https://www.youtube.com/watch?v=Zqv37SNw6HQ"><img src="roughcode-image/docs-ucc-thumbnail.JPG"/></a>