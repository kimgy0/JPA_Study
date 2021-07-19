## JPA 7일 기초 완성하기

##### 1일차

- 이번 1일 차에는 persistence.xml 환경정보와 pom.xml을 이용하였습니다.

- 기초를 다지는 만큼 Spring Boot는 끌고 오지 않았고 maven project를 생성하여 구성했습니다.

- 영속성 컨텍스트의 동작방식과 원리 , 영속성 컨텍스트의 상태, flush의 개념등을 정리하였고 코드는 개념적인 것들 말고 이해가 안가는 코드 위주로 작성하고 생각하고 이해하였습니다.

- 또 매핑하는 간단한 @Entity @Id @Table @Column 정보도 대충 뭔지만 이해하고 매핑했습니다. 여기에 추가적인 어노테이션 공부는 2일차 부터 할 예정입니다.<br>

  <img src="정리\1일차정리.PNG" alt="1일차정리" style="zoom: 67%;" /> 

  

  <center> 이 변경감지 그림을 보면 영속성 컨텍스트의 동작방식이 어떤지 떠오른다.</center>

  <center> 두고두고 보려면 올려놔야지 ! </center>

  <img src="정리\변경감지캡처.PNG" alt="변경감지캡처" style="zoom:50%;" />

  #### 2일차

  <hr>

  - 간단하게 객체와 객체필드를 테이블과 엔티티 관리범위에 넣어서 매핑시키고 각 타입에 대해서 db 컬럼과 매핑하는 방법을 공부했습니다.

  - 그리고 @id 와 @generatedValue(stratgy=generationType.Auto) ... 등 어노테이션을 사용하여 테이블 기본키할당 전략과 identity 전략 등 외 2가지를 더 공부해서 매핑과 원리가 어떻게 돌아가는지 이해했습니다.

  - 그리고 매핑하는 어노테이션의 속성에 대해서도 공부를 하며 persistence.xml 설정 방법을 공부하며 자동테이블 생성 설정 방법에 대해서도 공부하며 테스트서버에서는 어떤 속성을 주로 이용하며 실무에서는 어떤 속성을 이용하면 안되는지에대해 공부했습니다.

    <img src="정리\2일차정리.PNG" alt="1일차정리" style="zoom: 67%;" />

  <center>아직도 배울게 태산이다! 빨리 스프링mvc해야되는데 </center>

  <center> 언제 다해 ... </center>

#### 3일차

<hr>

- 연관관계 매핑에대해서 연관관계를 끊어내는 방법 수정하는 방법 조회하는 방법 , 일대다 매핑을 하고 실제 sql테이블의 구조와 jpa객체관점에서의 시점을 공부함.
- 연관관계의 주인은 항상 외래키가 있는 테이블에서 관리를 해야하며 연관관계편의 메서드 작성시 tostring , lombok , json 라이브러리를 조심하여 무한 호출을 미연에 방지해야 합니다.
- 프로젝트 db를 설계할 때 무조건 양방향으로 매핑하면 좋지않다. -> 이거는 관심사를 잘 끊어내지 못한 것이고 만약 양방향으로 해주고 싶거든 비지니스적으로 관계가 있는 것들에 한해서 매핑해줄 수 있도록 하자.

<img src="정리\3일차.jpg" alt="3일차정리" style="zoom: 55%;" />



#### 4일차

<hr>

- 일대일, 다대일, 일대다, 다대다 매핑에 대해서 알아봤습니다. 다 각각 맨앞의 다 , 일 이 연관관계의 주인이 되어서 데이터베이스 테이블 구성이 어떻게 되어지는지, 실무에서는 어떤식으로 매핑되어야 하는지에 대해서 강의를 듣고 정리를 했습니다. 그리고 어려웠던 부분들은 코드를 직접 쳐서 데이터베이스에 따로 들어가서 이해가 될 때 까지 drop - create를 반복했습니다.

<img src="정리\4일차.png" alt="4일차" style="zoom: 80%;" />



#### 5일차

<hr>

- 생각보다 양이 얼마 안되어보이는데 jpa교재 에서 다대일 일대다 일대일 다대다 매핑을 조인 테이블로 구성하는 방법과 식별관계, 비식별관계를 식별자와 serialize 인터페이스를 이용해 hash함수와 equals를 재구현하는 방법에 대해서 배웠음.
  - 배우려고 해도 이해가 잘 되지 않는다,,, 코드적인 면에서는 이해가 잘 되어지는데 db구성에 대해서 아직은 약한것 같다... 데이터베이스 설계,,,, 잘 가르쳐주시겠지,,,, 제발,,,,, 클래스좀 높여서 가르쳐주셨으면 좋겠다.... select쿼리같은거로 일주일 다 날리실까봐 겁난다.



- @MappedSuperClass 어노테이션을 이용한 Base Abstract class 를 만들어서 공통 컬럼들을 매핑하는 것.
- @Inheritance 를 이용해서 전략 3가지 (조인, 싱글테이블, 테이블마다 전략) 를 공부했음.
  - 그 외 부가적인 discriminatorcolumn, value를 이용해서 테이블을 구성하는 방법을 배움
  - 각각의 전략에 대한 장점과 단점을 정리했음.
  - 테이블마다 전략은 절대 어디서도 쓰지 말 것. (성능면)

- 이번에는 아무것도 안적었슴다....

  왜냐..... 너무 분량이 많아서 안적었슴다...

