create table member
(
    id            bigint auto_increment
        primary key,
    created_date  datetime(6) null,
    modified_date datetime(6) null
);

create table reserve
(
    id            bigint auto_increment
        primary key,
    created_date  datetime(6)  null,
    modified_date datetime(6)  null,
    amount        int          not null,
    balance       int          not null,
    is_deleted    bit          not null,
    status        varchar(255) null,
    member_id     bigint       null,
    constraint FKktaxwc9vryh5v78n280p3fq1o
        foreign key (member_id) references member (id)
);

CREATE TABLE shedlock
(
    name       VARCHAR(64),
    lock_until TIMESTAMP(3) NULL,
    locked_at  TIMESTAMP(3) NULL,
    locked_by  VARCHAR(255),
    PRIMARY KEY (name)
);
