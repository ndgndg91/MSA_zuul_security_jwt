# MSA_zuul_security_jwt
MicroServiceArchitecture Study

- API gateway : zuul 사용
    - port : 8080
    - pre-zuul-filter
    - post-zuul-filter
    
- authserver
    - port : 9090
    - JWT
    - Security
    - MariaDB10
    - JPA
  
- foos
    - port : 8081
    - MariaDB10
    - JPA
- bars
    - port : 8082
    - MariaDB10
    - JPA
