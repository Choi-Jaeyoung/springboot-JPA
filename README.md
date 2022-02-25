# springboot-JPA
스프링부트를 이용한 JPA 샘플 코드




## 주요 설정

- h2 (DB)
`springboot-JPA/src/main/resources/application-develop.yml`
- JPA
`springboot-JPA/src/main/resources/application-develop.yml`
- Swagger (OAS)
`springboot-JPA/src/main/resources/application-develop.yml`
- Spring 시큐리티 설정
`springboot-JPA/src/main/java/com/springboot/jpa/config/security/SecurityConfig.java`
- Exception 핸들러 ( 시큐리티/컨트롤러 구분 )
`springboot-JPA/src/main/java/com/springboot/jpa/exception/ControllerExceptionHandler.java`
`springboot-JPA/src/main/java/com/springboot/jpa/exception/SecurityExceptionHandler.java`
- 샘플 Data
`springboot-JPA/src/main/resources/data.sql`
`springboot-JPA/src/main/resources/schema.sql`

## 구동 순서

- maven install
- springboot 내부 톰캣 실행 (기본포트 8080)
- 웹브라우저에서 {아이피}:8080/api-docs 접속
- 로그인 계정 : (ID) user001 / (PWD) a123456

