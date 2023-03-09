# 📝JPA_Public
> 2022.12.20 ~ ing
> 
> 참여자 : 양상철
- Back-end
  - SpringBoot 2.7.6 ver
  - JDK 11.0.16 ver
  - gradle
  - h2 Database 1.4.200 ver
  - jpa
  

- Front-end
  - thymeleaf 3.0.15 ver
  - jquery 3.5.1 ver
  - bootstrap 4.3.1 ver

---

### Description

 온라인 강의로 학습한 Spring Boot 와 JPA 내용들을 직접 코드로 
실현해보고자 MVC 패턴을 이용하여 게시판 만들기를 목표로 프로젝트를 시작했습니다.

로그인, 게시판, 댓글달기 기능을 구현해 가면서 프로젝트의 크기를 키워보고자 하는 욕심이 생겼고,
간단한 게시판 프로젝트와 접목시킬 여러가지 API를 검색해보다가 **NEWS API** 를 발견하게 되었습니다.

**오늘의 Top Headline 뉴스**와 **검색을 통해 내가 원하는 뉴스를 찾아볼 수 있는** 기능이 있어, **NEWS API**를 활용하면
좀 더 재미있는 프로젝트가 탄생할 수 있을 거 같아 **NEWS 컨텐츠**까지 포함하여 프로젝트를 구성하였습니다.

지금까지 구현된 기능들로는
````
 - 로그인
 - 회원가입
 - 마이페이지 (My info, Bookmarked Board, My Article)
 - 게시글 작성
 - 게시글에 댓글 & 대댓글 달기
 - 원하는 게시글 북마크 하기
 - 원하는 뉴스기사 저장하기
 - 게시글 및 댓글 신고
````
등을 구현한 상태이며 앞으로 다양한 기능들을 추가해 나갈 계획입니다. 

프로젝트 구조와 구현된 기능들에 대한 설명은 **Notion**에 자세히 설명해 놓았습니다.



#### Notion Link - JPA_Public 구현 기능 리스트

https://spiny-voyage-87c.notion.site/JPA_public-7828a026836941deac47e6fd0f3f4f05
