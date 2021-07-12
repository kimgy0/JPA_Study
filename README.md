## JPA 7일 기초 완성하기

##### 1일차

- 이번 1일 차에는 persistence.xml 환경정보와 pom.xml을 이용하였습니다.

- 기초를 다지는 만큼 Spring Boot는 끌고 오지 않았고 maven project를 생성하여 구성했습니다.

- 영속성 컨텍스트의 동작방식과 원리 , 영속성 컨텍스트의 상태, flush의 개념등을 정리하였고 코드는 개념적인 것들 말고 이해가 안가는 코드 위주로 작성하고 생각하고 이해하였습니다.

- 또 매핑하는 간단한 @Entity @Id @Table @Column 정보도 대충 뭔지만 이해하고 매핑했습니다. 여기에 추가적인 어노테이션 공부는 2일차 부터 할 예정입니다.<br><center> <img src="정리\1일차정리.PNG" alt="1일차정리" style="zoom: 67%;" /> </center>

  

  <center> 이 변경감지 그림을 보면 영속성 컨텍스트의 동작방식이 어떤지 떠오른다.</center>

  <center> 두고두고 보려면 올려놔야지 ! </center>

  <img src="D:\JPABasicStudy\정리\변경감지캡처.PNG" alt="변경감지캡처" style="zoom:50%;" />