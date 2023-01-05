# 요구사항
### 회원
* API에서 회원정보는 '회원번호(회원에게 부여된 유니크한 번호)' 이외의 다른 정보는 전달받지 않음

### 적립금
* 회원별 적립금 합계 조회
  * 회원별 적립금 합계는 마이너스가 될 수 없음
* 회원별 적립금 적립/사용 내역 조회
  * 페이징 처리
* 회원별 적립금 적립
* 회원별 적립금 사용
  * 적립금 사용시 우선순위는 먼저 적립된 순서로 사용(FIFO)
* 적립금의 유효기간 구현 (1년)

### 공통
* ORM 사용 (ex: JPA / typeorm 등)
* 각 API 에서 ‘회원번호’ 이외에 request, response는 자유롭게 구성
* 트래픽이 많고, 저장되어 있는 데이터가 많음을 염두에 둔 개발
* 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 개발

### 용어사전
| 한글명 | 영문명     | 설명                 |
|-----|---------|--------------------|
| 회원  | Member  | 회원 관리의 기준이 되는 데이터  |
| 적립금 | Reserve | 적립금 관리의 기준이 되는 데이터 |
| 사용 | USE     | 사용한 적립금 상태         |
| 적립 | SAVE    | 적립한 적립금 상태         |
| 적립금 금액 | amount | 최초 적립된 금액          |
| 적립금 잔액 | balance | 사용하고 남은 적립금 금액     |
| 삭제 여부 | isDeleted | 삭제 여부              |

# 개발 환경
* Java 1.8, Spring Boot 2.7, Spring Data JPA, QueryDSL, Redis, MySQL, H2, Lombok, Flyway, RestAssured 

# API 문서
http://localhost:8080/swagger-ui/index.html

# 설정
* master DB 설정
````shell
docker run --name mysql-master -p 13306:3306 -v ~/mysql/master:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=masterpw -d mysql

docker exec -it mysql-master /bin/bash
mysql -u root -p

mysql> CREATE DATABASE marketboro;
mysql> GRANT ALL PRIVILEGES ON marketboro.* to root@'%';
mysql> FLUSH PRIVILEGES;
  
mysql> CREATE USER 'replication_user'@'%' IDENTIFIED WITH mysql_native_password by 'replication_pw';  
mysql> GRANT REPLICATION SLAVE ON *.* TO 'replication_user'@'%'; 

mysql> SHOW MASTER STATUS\G  
*************************** 1. row ***************************
             File: binlog.000002
         Position: 683
     Binlog_Do_DB: 
 Binlog_Ignore_DB: 
Executed_Gtid_Set: 
1 row in set (0.00 sec)
````

* slave DB 설정
````shell
docker run --name mysql-slave -p 13307:3306 -v ~/mysql/slave:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=slavepw -d mysql

docker exec -it mysql-slave /bin/bash
mysql -u root -p  

mysql> CREATE DATABASE marketboro;
mysql> GRANT ALL PRIVILEGES ON marketboro.* to root@'%';
mysql> FLUSH PRIVILEGES;

mysql> SET GLOBAL server_id = 2;
mysql> CHANGE MASTER TO MASTER_HOST='172.17.0.1', MASTER_PORT = 13306, MASTER_USER='replication_user', MASTER_PASSWORD='replication_pw', MASTER_LOG_FILE='binlog.000002', MASTER_LOG_POS=683;  

mysql> START SLAVE;  
mysql> SHOW SLAVE STATUS\G
...
            Slave_IO_Running: Yes
            Slave_SQL_Running: Yes
````

* redis 설정
````shell
docker pull redis
docker run -d -p 6379:6379 redis
````
