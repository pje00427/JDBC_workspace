
CREATE TABLE PRODUCT(
    PRODUCT_ID VARCHAR2(30) PRIMARY KEY,
    P_NAME VARCHAR2(30) NOT NULL,
    PRICE NUMBER NOT NULL,
    DESCRIPTION VARCHAR2(30),
    STOCK NUMBER NOT NULL
);
