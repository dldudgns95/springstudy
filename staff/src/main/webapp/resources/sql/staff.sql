DROP TABLE STAFF;
CREATE TABLE STAFF(
    SNO    VARCHAR2(5)  NOT NULL,
    NAME   VARCHAR2(32),
    DEPT   VARCHAR2(20) NOT NULL,
    SALARY NUMBER       NOT NULL,
    CONSTRAINT PK_STAFF PRIMARY KEY(SNO)
);