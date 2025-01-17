CREATE SCHEMA IF NOT EXISTS turi;

SET search_path TO turi;


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

CREATE INDEX IF NOT EXISTS userusernameindex           ON "user" (username);
CREATE INDEX IF NOT EXISTS useremailindex              ON "user" (email);
CREATE INDEX IF NOT EXISTS userpasswordresettokenindex ON "user" (passwordresettoken);

COMMENT ON TABLE  "user"                        IS 'Table to store user details authentication.';
COMMENT ON COLUMN "user".userid                 IS 'Unique required primary key of the user table.';
COMMENT ON COLUMN "user".username               IS 'Unique required user username.';
COMMENT ON COLUMN "user".email                  IS 'Unique required user email.';
COMMENT ON COLUMN "user".password               IS 'Required user password.';
COMMENT ON COLUMN "user".passwordresetcode      IS 'User code for reset password.';
COMMENT ON COLUMN "user".passwordresettoken     IS 'User token for reset password.';
COMMENT ON COLUMN "user".passwordresetexpiresat IS 'User code and token expiry date for reset password.';


CREATE TABLE IF NOT EXISTS refreshtoken
(
    refreshtokenid SERIAL       PRIMARY KEY,
    userid         INTEGER      NOT NULL,
    token          VARCHAR(255) NOT NULL,
    expiresat      TIMESTAMP    NOT NULL,
    CONSTRAINT refreshtokenuser FOREIGN KEY (userid) REFERENCES "user" (userid)
);

CREATE INDEX IF NOT EXISTS refreshtokenindex ON refreshtoken (token);

COMMENT ON TABLE  refreshtoken                IS 'Table to store user refresh token details';
COMMENT ON COLUMN refreshtoken.refreshtokenid IS 'Unique required primary key of the refresh token table';
COMMENT ON COLUMN refreshtoken.userid         IS 'Required foreign key of user.';
COMMENT ON COLUMN refreshtoken.token          IS 'Required user refresh token.';
COMMENT ON COLUMN refreshtoken.expiresat      IS 'Required expiry date for refresh token.';


CREATE TABLE IF NOT EXISTS address
(
    addressid       SERIAL      PRIMARY KEY,
    country         VARCHAR(50) NOT NULL,
    city            VARCHAR(50) NOT NULL,
    zipcode         VARCHAR(6)  NOT NULL,
    street          VARCHAR(50) NOT NULL,
    buildingnumber  VARCHAR(5)  NOT NULL,
    apartmentnumber INTEGER,
    CONSTRAINT addressunique UNIQUE (country, city, zipcode, street, buildingnumber, apartmentnumber)
);

CREATE INDEX IF NOT EXISTS addressindex ON address (country, city, zipcode, street, buildingnumber, apartmentnumber);

COMMENT ON TABLE  address                 IS 'Table to store address for the account and the turistic place, where one address can be for both of them.';
COMMENT ON COLUMN address.addressid       IS 'Unique required primary key of the address table.';
COMMENT ON COLUMN address.country         IS 'Required country of address.';
COMMENT ON COLUMN address.city            IS 'Required city of address.';
COMMENT ON COLUMN address.zipcode         IS 'Required ZIP code of address in format xx-xxx (x is a digit).';
COMMENT ON COLUMN address.street          IS 'Required street name of address.';
COMMENT ON COLUMN address.buildingnumber  IS 'Required building number on street of address.';
COMMENT ON COLUMN address.apartmentnumber IS 'Optional apartment number, if there is more than one apartment under a building number.';


CREATE TABLE IF NOT EXISTS account
(
    accountid             SERIAL       PRIMARY KEY,
    userid                INTEGER      NOT NULL UNIQUE,
    addressid             INTEGER               UNIQUE,
    accounttype           INTEGER      NOT NULL,
    activatecode          INTEGER,
    activatecodeexpiresat TIMESTAMP,
    firstname             VARCHAR(50),
    lastname              VARCHAR(50),
    birthdate             DATE,
    phonenumber           VARCHAR(20)           UNIQUE,
    gender                INTEGER,
    CONSTRAINT accountuser FOREIGN KEY (userid) REFERENCES "user" (userid),
    CONSTRAINT accountaddress FOREIGN KEY (addressid) REFERENCES address (addressid)
);

CREATE INDEX IF NOT EXISTS accountuserindex        ON account (userid);
CREATE INDEX IF NOT EXISTS accountaddressindex     ON account (addressid);
CREATE INDEX IF NOT EXISTS accountphonenumberindex ON account (phonenumber);

COMMENT ON TABLE  account                       IS 'Table to store account user details.';
COMMENT ON COLUMN account.accountid             IS 'Unique required primary key of the account table.';
COMMENT ON COLUMN account.userid                IS 'Unique required foreign key of account user.';
COMMENT ON COLUMN account.addressid             IS 'Unique foreign key address of account user.';
COMMENT ON COLUMN account.accounttype           IS 'Required type of account (0 - Inactive, 1 - Normal, 2 - Premium).';
COMMENT ON COLUMN account.activatecode          IS 'Account activation code.';
COMMENT ON COLUMN account.activatecodeexpiresat IS 'Account activation code expired date.';
COMMENT ON COLUMN account.firstname             IS 'First name of account user.';
COMMENT ON COLUMN account.lastname              IS 'Last name of account user.';
COMMENT ON COLUMN account.birthdate             IS 'Birth date of account user.';
COMMENT ON COLUMN account.phonenumber           IS 'Unique phone number of account user.';
COMMENT ON COLUMN account.gender                IS 'Gander of account user (1 - Male, 2 - Female).';


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
    loginexpiresat    TIMESTAMP,
    CONSTRAINT premiumaccount FOREIGN KEY (accountid) REFERENCES account (accountid)
);

CREATE INDEX IF NOT EXISTS premiumaccountindex    ON premium (accountid);
CREATE INDEX IF NOT EXISTS premiumlogintokenindex ON premium (logintoken);

COMMENT ON TABLE  premium                   IS 'Table to store premium data for the account.';
COMMENT ON COLUMN premium.premiumid         IS 'Unique required primary key of the premium table.';
COMMENT ON COLUMN premium.accountid         IS 'Unique required foreign key of premium account.';
COMMENT ON COLUMN premium.companyname       IS 'Unique required user company name.';
COMMENT ON COLUMN premium.nip               IS 'Unique required user company NIP.';
COMMENT ON COLUMN premium.bankaccountnumber IS 'Unique required user bank account number.';
COMMENT ON COLUMN premium.buydate           IS 'Required date of buy premium account.';
COMMENT ON COLUMN premium.expiresdate       IS 'Required date of expiry premium account.';
COMMENT ON COLUMN premium.status            IS 'Required status of account premium (0 - Unpaid, 1 - Active, 2 - Expired).';
COMMENT ON COLUMN premium.logincode         IS 'Code for login into premium account.';
COMMENT ON COLUMN premium.logintoken        IS 'Token for login into premium account.';
COMMENT ON COLUMN premium.loginexpiresat    IS 'Code and token expiry date for login into premium account.';


CREATE TABLE IF NOT EXISTS touristicplace
(
    touristicplaceid      SERIAL       PRIMARY KEY,
    premiumid             INTEGER      NOT NULL UNIQUE,
    addressid             INTEGER               UNIQUE,
    touristicplacetype    INTEGER,
    name                  VARCHAR(50),
    description           VARCHAR(255),
    information           VARCHAR(255),
    ownerdescription      VARCHAR(255),
    checkintimefrom       TIME,
    checkintimeto         TIME,
    checkouttimefrom      TIME,
    checkouttimeto        TIME,
    prepayment            BOOLEAN,
    cancelreservationdays INTEGER,
    CONSTRAINT touristicplacepremium FOREIGN KEY (premiumid) REFERENCES premium (premiumid),
    CONSTRAINT touristicplaceaddress FOREIGN KEY (addressid) REFERENCES address (addressid)
);

CREATE INDEX IF NOT EXISTS touristicplacepremiumindex ON touristicplace (premiumid);
CREATE INDEX IF NOT EXISTS touristicplaceaddressindex ON touristicplace (addressid);
CREATE INDEX IF NOT EXISTS touristicplacenameindex    ON touristicplace USING gin(to_tsvector('simple', name));

COMMENT ON TABLE  touristicplace                       IS 'Table to store data about premium user touristic place.';
COMMENT ON COLUMN touristicplace.touristicplaceid      IS 'Unique required primary key of the touristicplace table.';
COMMENT ON COLUMN touristicplace.premiumid             IS 'Unique required foreign key of user premium.';
COMMENT ON COLUMN touristicplace.addressid             IS 'Unique foreign key of touristic place address.';
COMMENT ON COLUMN touristicplace.touristicplacetype    IS 'Touristic place type (0 - Unassigned, 1 - Guesthouse, 2 - Apartment, 3 - Cottages, 4 - Hotel, 5 - B&B, 6 - Hostel).';
COMMENT ON COLUMN touristicplace.name                  IS 'Touristic place name.';
COMMENT ON COLUMN touristicplace.description           IS 'Touristic place description.';
COMMENT ON COLUMN touristicplace.information           IS 'Touristic place important information.';
COMMENT ON COLUMN touristicplace.ownerdescription      IS 'Touristic place owner description.';
COMMENT ON COLUMN touristicplace.checkintimefrom       IS 'Touristic place check-in time from.';
COMMENT ON COLUMN touristicplace.checkintimeto         IS 'Touristic place check-in time to.';
COMMENT ON COLUMN touristicplace.checkouttimefrom      IS 'Touristic place check-out time from.';
COMMENT ON COLUMN touristicplace.checkouttimeto        IS 'Touristic place check-out time to.';
COMMENT ON COLUMN touristicplace.prepayment            IS 'Touristic place is pre-payment information.';
COMMENT ON COLUMN touristicplace.cancelreservationdays IS 'Optional touristic place information about max number of days before cancel reservation.';


CREATE TABLE IF NOT EXISTS guaranteedservice
(
    guaranteedserviceid SERIAL       PRIMARY KEY,
    touristicplaceid    INTEGER      NOT NULL,
    service             VARCHAR(255) NOT NULL,
    CONSTRAINT guaranteedserviceunique UNIQUE (touristicplaceid, service),
    CONSTRAINT guaranteedservicetouristicplace FOREIGN KEY (touristicplaceid) REFERENCES touristicplace (touristicplaceid)
);

CREATE INDEX IF NOT EXISTS guaranteedservicetouristicplaceindex ON guaranteedservice (touristicplaceid);

COMMENT ON TABLE  guaranteedservice                     IS 'Table to store touristic place guaranteed services information.';
COMMENT ON COLUMN guaranteedservice.guaranteedserviceid IS 'Unique required primary key of the guaranteedservice table.';
COMMENT ON COLUMN guaranteedservice.touristicplaceid    IS 'Required foreign key of touristicplace table.';
COMMENT ON COLUMN guaranteedservice.service             IS 'Required name of touristic place guaranteed service.';


CREATE TABLE IF NOT EXISTS stay
(
    stayid           SERIAL         PRIMARY KEY,
    touristicplaceid INTEGER        NOT NULL,
    name             VARCHAR(50)    NOT NULL,
    description      VARCHAR(255)   NOT NULL,
    price            DECIMAL(10, 2) NOT NULL,
    peoplenumber     INTEGER        NOT NULL,
    datefrom         DATE           NOT NULL,
    dateto           DATE
);

CREATE INDEX IF NOT EXISTS staytouristicplaceindex ON stay (touristicplaceid);
CREATE INDEX IF NOT EXISTS staynameindex           ON stay USING gin(to_tsvector('simple', name));
CREATE INDEX IF NOT EXISTS staysearchindex         ON stay (touristicplaceid, dateFrom, dateTo);

COMMENT ON TABLE  stay                  IS 'Table to store touristic place stays offers.';
COMMENT ON COLUMN stay.stayid           IS 'Unique required primary key of the stay table.';
COMMENT ON COLUMN stay.touristicplaceid IS 'Required foreign key of touristicplace table.';
COMMENT ON COLUMN stay.name             IS 'Required stay name.';
COMMENT ON COLUMN stay.description      IS 'Required stay descritpion.';
COMMENT ON COLUMN stay.price            IS 'Required stay price per day, calculated from the minimum check-in hour, to the maximum check-out hour the next day.';
COMMENT ON COLUMN stay.peoplenumber     IS 'Required stay max people number.';
COMMENT ON COLUMN stay.datefrom         IS 'Required stay availibility date from.';
COMMENT ON COLUMN stay.dateto           IS 'Required stay availibility date to.';


CREATE TABLE IF NOT EXISTS stayinformation
(
    stayinformationid SERIAL       PRIMARY KEY,
    stayid            INTEGER      NOT NULL,
    information       VARCHAR(255) NOT NULL,
    CONSTRAINT stayinformationstay FOREIGN KEY (stayid) REFERENCES stay (stayid)
);

CREATE INDEX IF NOT EXISTS stayinformationstayindex ON stayinformation (stayid);

COMMENT ON TABLE  stayinformation                   IS 'Table to store touristic place stay information.';
COMMENT ON COLUMN stayinformation.stayinformationid IS 'Unique required primary key of the stayinformation table.';
COMMENT ON COLUMN stayinformation.stayid            IS 'Required foreign key of stay table.';
COMMENT ON COLUMN stayinformation.information       IS 'Required name of stay information.';


CREATE TABLE IF NOT EXISTS attraction
(
    attractionid          SERIAL         PRIMARY KEY,
    touristicplaceid      INTEGER        NOT NULL,
    attractiontype        INTEGER        NOT NULL,
    name                  VARCHAR(50)    NOT NULL,
    description           VARCHAR(255)   NOT NULL,
    price                 DECIMAL(10, 2) NOT NULL,
    pricetype             INTEGER        NOT NULL,
    prepayment            BOOLEAN        NOT NULL,
    cancelreservationdays INTEGER,
    maxpeoplenumber       INTEGER,
    maxitems              INTEGER,
    datefrom              DATE           NOT NULL,
    dateto                DATE           NOT NULL,
    hourfrom              TIME           NOT NULL,
    hourto                TIME           NOT NULL,
    daysreservationbefore INTEGER        NOT NULL,
    CONSTRAINT attractiontouristicplace FOREIGN KEY (touristicplaceid) REFERENCES touristicplace (touristicplaceid)
);

CREATE INDEX IF NOT EXISTS attractiontouristicplaceindex ON attraction (touristicplaceid);
CREATE INDEX IF NOT EXISTS attractionnameindex           ON attraction USING gin(to_tsvector('simple', name));
CREATE INDEX IF NOT EXISTS attractionsearchindex         ON attraction (touristicplaceid, dateFrom, dateTo);

COMMENT ON TABLE  attraction                       IS 'TTable to store touristic place attractions offers.';
COMMENT ON COLUMN attraction.attractionid          IS 'Unique required primary key of the attraction table.';
COMMENT ON COLUMN attraction.touristicplaceid      IS 'Required foreign key of touristicplace table.';
COMMENT ON COLUMN attraction.attractiontype        IS 'Required attraction type (0 - Unassigned, 1 - Relax, 2 - Sport, 3 - Recreation, 4 - Entertainment, 5 - Food, 6 - Event, 7 - Children, 8 - Other).';
COMMENT ON COLUMN attraction.name                  IS 'Required attraction name.';
COMMENT ON COLUMN attraction.description           IS 'Required attraction description.';
COMMENT ON COLUMN attraction.price                 IS 'Required attraction price.';
COMMENT ON COLUMN attraction.pricetype             IS 'Required attraction type of price (0 - Unassigned, 1 - Hour, 2 - Person, 3 - Item).';
COMMENT ON COLUMN attraction.prepayment            IS 'Required attraction is pre-payment information.';
COMMENT ON COLUMN attraction.cancelreservationdays IS 'Optional attraction information about max number of days before cancel reservation.';
COMMENT ON COLUMN attraction.maxpeoplenumber       IS 'Optional attraction information about max people number.';
COMMENT ON COLUMN attraction.maxitems              IS 'Optional attraction information about max items number.';
COMMENT ON COLUMN attraction.datefrom              IS 'Required attraction availibility date from.';
COMMENT ON COLUMN attraction.dateto                IS 'Required attraction availibility date to.';
COMMENT ON COLUMN attraction.hourfrom              IS 'Required attraction availibility hour from.';
COMMENT ON COLUMN attraction.hourto                IS 'Required attraction availibility hour to.';
COMMENT ON COLUMN attraction.daysreservationbefore IS 'Required attraction information about max number of days before make reservation.';


CREATE TABLE IF NOT EXISTS image
(
    imageid          SERIAL       PRIMARY KEY,
    accountid        INTEGER,
    touristicplaceid INTEGER,
    stayid           INTEGER,
    attractionid     INTEGER,
    path             VARCHAR(255) NOT NULL,
    CONSTRAINT imageaccount        FOREIGN KEY (accountid)        REFERENCES account        (accountid),
    CONSTRAINT imagetouristicplace FOREIGN KEY (touristicplaceid) REFERENCES touristicplace (touristicplaceid),
    CONSTRAINT imagestay           FOREIGN KEY (stayid)           REFERENCES stay           (stayid),
    CONSTRAINT imageattraction     FOREIGN KEY (attractionid)     REFERENCES attraction     (attractionid)
);

CREATE INDEX IF NOT EXISTS imageaccountindex        ON image (accountid);
CREATE INDEX IF NOT EXISTS imagetouristicplaceindex ON image (touristicplaceid);
CREATE INDEX IF NOT EXISTS imagestayindex           ON image (stayid);
CREATE INDEX IF NOT EXISTS imageattractionindex     ON image (attractionid);

COMMENT ON TABLE  image                  IS 'Table to store images paths related to account, touristicplace, stay or attraction tables.';
COMMENT ON COLUMN image.imageid          IS 'Unique required primary key of the image table.';
COMMENT ON COLUMN image.accountid        IS 'Optional foreign key of account table.';
COMMENT ON COLUMN image.touristicplaceid IS 'Optional foreign key of touristicplace table.';
COMMENT ON COLUMN image.stayid           IS 'Optional foreign key of stay table.';
COMMENT ON COLUMN image.attractionid     IS 'Optional foreign key of attraction table.';
COMMENT ON COLUMN image.path             IS 'Required image path.';


CREATE TABLE IF NOT EXISTS favourite
(
    accountid        INTEGER NOT NULL,
    touristicplaceid INTEGER NOT NULL,
    CONSTRAINT favouriteunique         UNIQUE (accountid, touristicplaceid),
    CONSTRAINT favouriteaccount        FOREIGN KEY (accountid)        REFERENCES account        (accountid),
    CONSTRAINT favouritetouristicplace FOREIGN KEY (touristicplaceid) REFERENCES touristicplace (touristicplaceid)
);

CREATE INDEX IF NOT EXISTS favouriteaccountindex        ON favourite (accountid);
CREATE INDEX IF NOT EXISTS favouritetouristicplaceindex ON favourite (touristicplaceid);

COMMENT ON TABLE  favourite                  IS 'Table to store offers (touristic places) that user has pinned to his account as favorites.';
COMMENT ON COLUMN favourite.accountid        IS 'Required foreign key of user account table.';
COMMENT ON COLUMN favourite.touristicplaceid IS 'Required foreign key of touristicplace (offer) table.';


CREATE TABLE IF NOT EXISTS reservation
(
    reservationid SERIAL         PRIMARY KEY,
    stayid        INTEGER        NOT NULL,
    accountid     INTEGER        NOT NULL,
    datefrom      DATE           NOT NULL,
    dateto        DATE           NOT NULL,
    price         DECIMAL(10, 2),
    checkintime   TIME,
    request       VARCHAR(255),
    rating        DECIMAL(1, 1),
    opinion       VARCHAR(255),
    modifydate    TIMESTAMP      NOT NULL,
    status        INTEGER        NOT NULL,
    CONSTRAINT reservationstay    FOREIGN KEY (stayid)    REFERENCES stay    (stayid),
    CONSTRAINT reservationaccount FOREIGN KEY (accountid) REFERENCES account (accountid)
);

CREATE INDEX IF NOT EXISTS reservationstayindex    ON reservation (stayid);
CREATE INDEX IF NOT EXISTS reservationaccountindex ON reservation (accountid);

COMMENT ON TABLE  reservation               IS 'Table to store created by user reservations in touristic places.';
COMMENT ON COLUMN reservation.reservationid IS 'Unique required primary key of the reservation table.';
COMMENT ON COLUMN reservation.stayid        IS 'Required foreign key of stay table.';
COMMENT ON COLUMN reservation.accountid     IS 'Required foreign key of account table.';
COMMENT ON COLUMN reservation.datefrom      IS 'Required reservation date from.';
COMMENT ON COLUMN reservation.dateto        IS 'Required reservation date to.';
COMMENT ON COLUMN reservation.price         IS 'Optional reservation price.';
COMMENT ON COLUMN reservation.checkintime   IS 'Optional required user check-in hour.';
COMMENT ON COLUMN reservation.request       IS 'Optional user reservation special request.';
COMMENT ON COLUMN reservation.rating        IS 'Optional user reservation rating after realization.';
COMMENT ON COLUMN reservation.opinion       IS 'Optional user reservation opinion after realization.';
COMMENT ON COLUMN reservation.modifydate    IS 'Required reservation last modify date.';
COMMENT ON COLUMN reservation.status        IS 'Required reservation status (0 - Locked, 1 - Unpaid, 2 - Pay on site, 3 - Reservation, 4 - Reservation with unpaid date extension, 5 - Realization, 6 - Realization with unpaid date extension, 7 - Realized, 8 - Canceled).';


CREATE TABLE IF NOT EXISTS reservationattraction
(
    reservationattractionid SERIAL         PRIMARY KEY,
    reservationid           INTEGER        NOT NULL,
    attractionid            INTEGER        NOT NULL,
    datefrom                DATE           NOT NULL,
    dateto                  DATE           NOT NULL,
    hourfrom                TIME           NOT NULL,
    hourto                  TIME           NOT NULL,
    people                  INTEGER,
    items                   INTEGER,
    message                 VARCHAR(255),
    price                   DECIMAL(10, 2) NOT NULL,
    modifydate              TIMESTAMP      NOT NULL,
    status                  INTEGER        NOT NULL,
    CONSTRAINT reservationattractionreservation FOREIGN KEY (reservationid) REFERENCES reservation (reservationid),
    CONSTRAINT reservationattractionattraction  FOREIGN KEY (attractionid)  REFERENCES attraction  (attractionid)
);

CREATE INDEX IF NOT EXISTS reservationattractionreservationindex ON reservationattraction (reservationid);
CREATE INDEX IF NOT EXISTS reservationattractionattractionindex  ON reservationattraction (attractionid);

COMMENT ON TABLE  reservationattraction                         IS 'Table to store attraction added by user to reservation.';
COMMENT ON COLUMN reservationattraction.reservationattractionid IS 'Unique required primary key of the reservationattraction table.';
COMMENT ON COLUMN reservationattraction.reservationid           IS 'Required foreign key of reservation table.';
COMMENT ON COLUMN reservationattraction.attractionid            IS 'Required foreign key of attraction table.';
COMMENT ON COLUMN reservationattraction.datefrom                IS 'Required attraction date from.';
COMMENT ON COLUMN reservationattraction.dateto                  IS 'Required attraction date to.';
COMMENT ON COLUMN reservationattraction.hourfrom                IS 'Required attraction hour from.';
COMMENT ON COLUMN reservationattraction.hourto                  IS 'Required attraction hour to.';
COMMENT ON COLUMN reservationattraction.people                  IS 'Optional attraction people number, depends from attraction type.';
COMMENT ON COLUMN reservationattraction.items                   IS 'Optional attraction items, depends from attraction type.';
COMMENT ON COLUMN reservationattraction.message                 IS 'Optional user message about attraction reservation.';
COMMENT ON COLUMN reservationattraction.modifydate              IS 'Required reservation attraction last modify date.';
COMMENT ON COLUMN reservationattraction.status                  IS 'Required attraction reservation status (0 - Locked, 1 - Unpaid, 2 - Pay on site, 3 - Reservation, 4 - Reservation with unpaid date extension, 5 - Realization, 6 - Realization with unpaid date extension, 7 - Realized, 8 - Canceled).';


CREATE TABLE IF NOT EXISTS stripepayment
(
    stripepaymentid SERIAL       PRIMARY KEY,
    intent          VARCHAR(255) NOT NULL UNIQUE,
    status          INTEGER      NOT NULL,
    paymentdate     TIMESTAMP    NOT NULL
);

CREATE INDEX IF NOT EXISTS stripepaymentintentindex ON stripepayment (intent);

COMMENT ON TABLE  stripepayment                 IS 'Table to store stripe payment intent information for webhook process.';
COMMENT ON COLUMN stripepayment.stripepaymentid IS 'Unique required primary key of the stripe payment table.';
COMMENT ON COLUMN stripepayment.intent          IS 'Unique required intent information for webhook process.';
COMMENT ON COLUMN stripepayment.status          IS 'Required stripe payment status (1 - Pending, 2 - Succeeded, 3  - Failed).';
COMMENT ON COLUMN stripepayment.paymentdate     IS 'Required date of payment.';


CREATE TABLE IF NOT EXISTS payment
(
    paymentid           SERIAL         PRIMARY KEY,
    premiumid           INTEGER,
    reservationid       INTEGER,
    stripeid            VARCHAR(255)   NOT NULL UNIQUE,
    stripepaymentintent VARCHAR(255)            UNIQUE,
    amount              DECIMAL(10, 2) NOT NULL,
    paymentdate         TIMESTAMP      NOT NULL,
    method              INTEGER        NOT NULL,
    status              INTEGER        NOT NULL,
    CONSTRAINT paymentpremium     FOREIGN KEY (premiumid)     REFERENCES premium (premiumid),
    CONSTRAINT paymentreservation FOREIGN KEY (reservationid) REFERENCES reservation (reservationid)
);

CREATE INDEX IF NOT EXISTS paymentpremiumindex     ON payment (premiumid);
CREATE INDEX IF NOT EXISTS paymentreservationindex ON payment (reservationid);
CREATE INDEX IF NOT EXISTS paymentstripeindex      ON payment (stripeid);

COMMENT ON TABLE  payment                     IS 'Table to store payment data for buying a premium account or paying for reservation or reservation attraction.';
COMMENT ON COLUMN payment.paymentid           IS 'Unique required primary key of the payment table.';
COMMENT ON COLUMN payment.premiumid           IS 'Optional foreign key of premium.';
COMMENT ON COLUMN payment.reservationid       IS 'Optional foreign key of reservation.';
COMMENT ON COLUMN payment.stripeid            IS 'Unique required ID of stripe payment.';
COMMENT ON COLUMN payment.stripepaymentintent IS 'Unique stripe payment intent information for webhook process.';
COMMENT ON COLUMN payment.amount              IS 'Required amount of payment.';
COMMENT ON COLUMN payment.paymentdate         IS 'Required date of payment.';
COMMENT ON COLUMN payment.method              IS 'Required payment method (1 - Card, 2 - Blik).';
COMMENT ON COLUMN payment.status              IS 'Required payment status (1 - Pending, 2 - Succeeded, 3 - Failed).';


CREATE TABLE IF NOT EXISTS paymentreservationattraction
(
    paymentid               INTEGER NOT NULL,
    reservationattractionid INTEGER NOT NULL,
    CONSTRAINT paymentreservationattractionunique                UNIQUE (paymentid, reservationattractionid),
    CONSTRAINT paymentreservationattractionpayment               FOREIGN KEY (paymentid)               REFERENCES payment (paymentid),
    CONSTRAINT paymentreservationattractionreservationattraction FOREIGN KEY (reservationattractionid) REFERENCES reservationattraction (reservationattractionid)
);

CREATE INDEX IF NOT EXISTS paymentpaymentreservationattractionindex               ON paymentreservationattraction (paymentid);
CREATE INDEX IF NOT EXISTS reservationattractionpaymentreservationattractionindex ON paymentreservationattraction (reservationattractionid);

COMMENT ON TABLE  paymentreservationattraction                         IS 'Table to store payment reservation attractions information.';
COMMENT ON COLUMN paymentreservationattraction.paymentid               IS 'Required foreign key of the payment table.';
COMMENT ON COLUMN paymentreservationattraction.reservationattractionid IS 'Required foreign key of the reservation attraction table.';
