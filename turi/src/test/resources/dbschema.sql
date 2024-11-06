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