DROP ALL OBJECTS;

CREATE TABLE IF NOT EXISTS "user"
(
    userid                 SERIAL       PRIMARY KEY,
    username               VARCHAR(50)  NOT NULL UNIQUE,
    email                  VARCHAR(50)  NOT NULL UNIQUE,
    password               VARCHAR(255) NOT NULL,
    passwordresetcode      INTEGER,
    passwordresettoken     VARCHAR(255),
    passwordresetexpiresat TIMESTAMP
);

CREATE TABLE IF NOT EXISTS refreshtoken
(
    refreshtokenid SERIAL       PRIMARY KEY,
    userid         INTEGER      NOT NULL,
    token          VARCHAR(255) NOT NULL,
    expiresat      TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS address
(
    addressid       SERIAL      PRIMARY KEY,
    country         VARCHAR(50) NOT NULL,
    city            VARCHAR(50) NOT NULL,
    zipcode         VARCHAR(6)  NOT NULL,
    street          VARCHAR(50) NOT NULL,
    buildingnumber  VARCHAR(5)  NOT NULL,
    apartmentnumber INTEGER,
    UNIQUE (country, city, zipcode, street, buildingnumber, apartmentnumber)
);

CREATE TABLE IF NOT EXISTS account
(
    accountid             SERIAL       PRIMARY KEY,
    userid                INTEGER      NOT NULL UNIQUE,
    addressid             INTEGER      UNIQUE,
    accounttype           INTEGER      NOT NULL,
    activatecode          INTEGER,
    activatecodeexpiresat TIMESTAMP,
    firstname             VARCHAR(50),
    lastname              VARCHAR(50),
    birthdate             DATE,
    phonenumber           VARCHAR(20)  UNIQUE,
    gender                INTEGER
);

CREATE TABLE IF NOT EXISTS premium
(
    premiumid         SERIAL       PRIMARY KEY,
    accountid         INTEGER      NOT NULL UNIQUE,
    companyname       VARCHAR(255) NOT NULL UNIQUE,
    nip               VARCHAR(10)  NOT NULL UNIQUE,
    bankaccountnumber VARCHAR(26)  NOT NULL UNIQUE,
    buydate           DATE,
    expiresdate       DATE,
    status            INTEGER      NOT NULL,
    logincode         INTEGER,
    logintoken        VARCHAR(255),
    loginexpiresat    TIMESTAMP
);

CREATE TABLE IF NOT EXISTS stripepayment
(
    stripepaymentid SERIAL       PRIMARY KEY,
    intent          VARCHAR(255) NOT NULL UNIQUE,
    status          INTEGER      NOT NULL,
    paymentdate     TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS payment
(
    paymentid           SERIAL         PRIMARY KEY,
    premiumid           INTEGER        NOT NULL,
    stripeid            VARCHAR(255)   NOT NULL UNIQUE,
    stripepaymentintent VARCHAR(255)            UNIQUE,
    amount              DECIMAL(10, 2) NOT NULL,
    paymentdate         TIMESTAMP      NOT NULL,
    method              INTEGER        NOT NULL,
    status              INTEGER        NOT NULL
);