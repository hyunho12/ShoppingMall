# Portfolio -  ShoppingMall

### 사용한 기술스택
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"> <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white"> <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white"> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white"> <img src="https://img.shields.io/badge/CSS3-4479A1?style=for-the-badge&logo=CSS3&logoColor=white"> <img src="https://img.shields.io/badge/Apache Tomcat-F8DC75?style=for-the-badge&logo=Apache Tomcat&logoColor=white">

----------------------------------

### 구현 기능
+ 관리자 : 회원, 카테고리, 브랜드, 제품 CRUD 기능 구현, 페이징, 검색 기능 구현
+ 고객 : 회원가입, 로그인, 장바구니 담기,상품목록 조회, 리뷰 포스팅
+ 판매자 : 제품가격 수정, 고객, 주문, 배송 관리
+ 편집자 : 제품,브랜드, 카테고리 메뉴 관리
+ QA 보조 : 질문,리뷰 관리

### DB ERD - 설계도
![erd](https://github.com/hyunho12/ShoppingMall/assets/63361993/7280bb22-a38a-40d3-bac9-cc3017bb49cc)

-------------------------------------------------------------------

## 구현 페이지

### 관리자 페이지
```
 + 관리자 메인페이지 구현
 + 회원 로그인, 검색, 정렬, 페이징 ,수정, 삭제 기능 구현
```
<details>
<summary>더보기</summary>
 
관리자 로그인 화면
![관리자로그인페이지](https://github.com/hyunho12/ShoppingMall/assets/63361993/1ced4dcb-3345-4b4b-b877-3519db2b8290)

  관리자 로그인후 첫 화면
![관리자로그인페이지](https://github.com/hyunho12/ShoppingMall/assets/63361993/3b4fe7cf-4214-4b7f-8f4a-99ed1f7748b2)

  회원생성 기능
![유저저장](https://github.com/hyunho12/ShoppingMall/assets/63361993/e15da353-0fc9-4e76-90a8-fbb2749db0a2)

  회원검색 기능
![유저검색](https://github.com/hyunho12/ShoppingMall/assets/63361993/7899c3aa-0d4d-472d-b5b2-dae24dfb02e2)

  회원 수정, 삭제, 페이징 기능
![유저페이징](https://github.com/hyunho12/ShoppingMall/assets/63361993/d82449c3-132d-4cdc-9ed9-49b52aa43996)
![회원삭제](https://github.com/hyunho12/ShoppingMall/assets/63361993/25e4cd8d-ace1-42b0-b138-7736f5d8e8f6)

  
</details>

### 카테고리 페이지
```
 + 카테고리 페이지구현
 + 카테고리 계층적단계 구현
 + 등록, 수정, 삭제, 검색 구현
```
<details>
 <summary>더보기</summary>

 카테고리 등록
 ![카테고리저장](https://github.com/hyunho12/ShoppingMall/assets/63361993/ed2f0cf4-ffe0-4223-93d8-c3ad3572fa14)
 
 카테고리 삭제
 ![카테고리삭제](https://github.com/hyunho12/ShoppingMall/assets/63361993/d0f20136-87b3-43e6-b663-22b2b3d7c6fe)
 
 카테고리 검색
 ![카테고리검색](https://github.com/hyunho12/ShoppingMall/assets/63361993/d7cff9c6-6846-48ff-9b28-49e607685a8e)

</details>


### 브랜드 페이지
```
 + 브랜드 페이지구현
 + 등록, 수정, 삭제, 검색, 페이징 구현
```
<details>
 <summary>더보기</summary>

 브랜드 저장
 ![브랜드저장](https://github.com/hyunho12/ShoppingMall/assets/63361993/f74630ed-ff70-4613-bdb6-61e03e2b6b08)

 브랜드 페이징
 ![브랜드페이징](https://github.com/hyunho12/ShoppingMall/assets/63361993/47fc72d2-591d-4180-9fac-77f8db878eb2)

</details>


### 제품 페이지
```
 + 제품 페이지구현
 + 리뷰 관리페이지 구현
 + 등록, 수정, 삭제, 검색, 페이징, 미리보기 구현
```
<details>
 <summary>더보기</summary>

 제품 저장
 ![image](https://github.com/hyunho12/ShoppingMall/assets/63361993/981b02a0-a40e-4c62-b2ee-4554232081e9)

 제품 미리보기
 ![제품미리보기](https://github.com/hyunho12/ShoppingMall/assets/63361993/908bdfde-6046-4c56-a91d-d51898e3d4dd)

 리뷰 페이지
 ![리뷰수정페이징미리](https://github.com/hyunho12/ShoppingMall/assets/63361993/38cf03c4-a703-4b7f-b1f3-27192b8b7026)

</details>


### 고객 페이지
```
 + 고객관리 페이지구현
 + 등록, 수정, 삭제, 검색, 페이징, 미리보기구현
```
<details>
 <summary>더보기</summary>

 고객관리
 ![고객관리](https://github.com/hyunho12/ShoppingMall/assets/63361993/e9e0a0c7-bac6-427e-af7f-bf98e9cfedf8)

</details>


### 회원가입, 로그인 페이지
```
 + 회원가입 페이지구현
 + 회원가입시 이메일 중복확인
 + 로그인 페이지구현
 + OAuth2.0 사용 구글,페이스북 계정으로 로그인 기능구현
```
<details>
 <summary>더보기</summary>
 
  회원가입 로그인 페이지
 
  ![회원가입로그인](https://github.com/hyunho12/ShoppingMall/assets/63361993/66255b0d-7bf7-4968-98da-4c6c508656e2)

</details>


### 장바구니 페이지
```
 + 내가 담은 장바구니 목록 조회, 수량 추가, 삭제 기능구현

```
<details>
 <summary>더보기</summary>
 
 장바구니 
 ![장바구니](https://github.com/hyunho12/ShoppingMall/assets/63361993/1e8c012f-2c1d-4bc5-a74e-37fb7b1c0a8d)

</details>


### 제품 페이지
```
 + 제품 장바구니 담기
 + 리뷰 3개확인, 리뷰 자세히보기 선택시 전체리뷰 확인가능
```
<details>
 <summary>더보기</summary>
 
  제품페이지, 리뷰 전체보기
  ![리뷰자세히보기](https://github.com/hyunho12/ShoppingMall/assets/63361993/3d008fc1-4527-4880-9f52-4bfe2c749586)
</details>
