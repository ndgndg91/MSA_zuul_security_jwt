# MSA_zuul_security_jwt
MicroServiceArchitecture Study

- API gateway : zuul 사용
    - port : 8080
    - was : undertow
    - pre-zuul-filter
    - post-zuul-filter
    
- authserver
    - port : 9090
    - was : undertow
    - JWT
    - Security
    - MariaDB10
    - JPA
  
- foos
    - port : 8081
    - was : undertow
    - MariaDB10
    - JPA
- bars
    - port : 8082
    - was : undertow
    - MariaDB10
    - JPA
