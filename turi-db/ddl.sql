-- Schema
CREATE SCHEMA IF NOT EXISTS turi; 

SET search_path TO turi;

-- Table: Account
CREATE TABLE Account (
    accountid serial  NOT NULL,
    accounttypeid int  NOT NULL,
    addressid int  NULL,
    login varchar(50)  NOT NULL,
    email varchar(50)  NOT NULL,
    password varchar(50)  NOT NULL,
    username varchar(50)  NULL,
    firstname varchar(50)  NULL,
    lastname varchar(50)  NULL,
    birthdate date  NULL,
    gender int  NULL,
    phonenumber varchar(20)  NULL,
    CONSTRAINT Account_pk PRIMARY KEY (accountid)
);

-- Table: AccountType
CREATE TABLE AccountType (
    accounttypeid serial  NOT NULL,
    name varchar(20)  NOT NULL,
    CONSTRAINT AccountType_pk PRIMARY KEY (accounttypeid)
);

-- Table: Address
CREATE TABLE Address (
    addressid serial  NOT NULL,
    country varchar(50)  NOT NULL,
    city varchar(50)  NOT NULL,
    zipcode varchar(10)  NOT NULL,
    street varchar(50)  NOT NULL,
    buildingnumber varchar(10)  NOT NULL,
    apartamentnumber varchar(10)  NULL,
    CONSTRAINT Address_pk PRIMARY KEY (addressid)
);

-- Table: Attraction
CREATE TABLE Attraction (
    attractionid serial  NOT NULL,
    turisticplaceid int  NOT NULL,
    attractiontypeid int  NOT NULL,
    pricetypeid int  NOT NULL,
    attractiondateintervalid int  NOT NULL,
    name varchar(50)  NOT NULL,
    description varchar(255)  NOT NULL,
    price money  NOT NULL,
    datefrom date  NOT NULL,
    dateto date  NOT NULL,
    hourfrom time  NOT NULL,
    hourto time  NOT NULL,
    prepayment boolean  NOT NULL,
    cancelreservationdays int  NULL,
    maxpeoples int  NULL,
    maxitems int  NULL,
    rentbeforedays int  NOT NULL,
    CONSTRAINT Attraction_pk PRIMARY KEY (attractionid)
);

-- Table: AttractionDateInterval
CREATE TABLE AttractionDateInterval (
    attractiondateintervalid serial  NOT NULL,
    name varchar(20)  NOT NULL,
    CONSTRAINT AttractionDateInterval_pk PRIMARY KEY (attractiondateintervalid)
);

-- Table: AttractionType
CREATE TABLE AttractionType (
    attractiontypeid serial  NOT NULL,
    name varchar(100)  NOT NULL,
    CONSTRAINT AttractionType_pk PRIMARY KEY (attractiontypeid)
);

-- Table: FavoriteOffer
CREATE TABLE FavoriteOffer (
    accountid int  NOT NULL,
    stayid int  NOT NULL
);

-- Table: GuaranteedService
CREATE TABLE GuaranteedService (
    guaranteedserviceid serial  NOT NULL,
    turisticplaceid int  NOT NULL,
    name varchar(100)  NOT NULL,
    CONSTRAINT GuaranteedService_pk PRIMARY KEY (guaranteedserviceid)
);

-- Table: Information
CREATE TABLE Information (
    informationid serial  NOT NULL,
    stayid int  NOT NULL,
    name varchar(100)  NOT NULL,
    CONSTRAINT Information_pk PRIMARY KEY (informationid)
);

-- Table: Item
CREATE TABLE Item (
    itemid serial  NOT NULL,
    attractionid int  NOT NULL,
    name varchar(100)  NOT NULL,
    CONSTRAINT Item_pk PRIMARY KEY (itemid)
);

-- Table: Owner
CREATE TABLE Owner (
    ownerid serial  NOT NULL,
    firstname varchar(50)  NOT NULL,
    lastname varchar(50)  NOT NULL,
    description varchar(255)  NOT NULL,
    phonenumber varchar(20)  NOT NULL,
    email varchar(30)  NOT NULL,
    CONSTRAINT Owner_pk PRIMARY KEY (ownerid)
);

-- Table: Photo
CREATE TABLE Photo (
    photoid int  NOT NULL,
    turisticplaceid int  NULL,
    stayid int  NULL,
    attractionid int  NULL,
    path varchar(255)  NOT NULL,
    CONSTRAINT Photo_pk PRIMARY KEY (photoid)
);

-- Table: Premium
CREATE TABLE Premium (
    premiumid serial  NOT NULL,
    accountid int  NOT NULL,
    buydate date  NOT NULL,
    expirationdate date  NOT NULL,
    active boolean  NOT NULL,
    pesel varchar(20)  NOT NULL,
    nip varchar(30)  NOT NULL,
    bankaccountnumber varchar(20)  NOT NULL,
    CONSTRAINT Premium_pk PRIMARY KEY (premiumid)
);

-- Table: PriceType
CREATE TABLE PriceType (
    pricetypeid serial  NOT NULL,
    name varchar(100)  NOT NULL,
    CONSTRAINT PriceType_pk PRIMARY KEY (pricetypeid)
);

-- Table: Reservation
CREATE TABLE Reservation (
    reservationid serial  NOT NULL,
    accountid int  NOT NULL,
    stayid int  NOT NULL,
    reservationstatusid int  NOT NULL,
    datefrom date  NOT NULL,
    dateto date  NOT NULL,
    checkintime time  NOT NULL,
    checkouttime time  NOT NULL,
    price money  NOT NULL,
    specialrequest text  NOT NULL,
    firstname varchar(100)  NOT NULL,
    lastname varchar(100)  NOT NULL,
    email varchar(20)  NOT NULL,
    phonenumber varchar(20)  NOT NULL,
    guestfullname varchar(100)  NULL,
    CONSTRAINT Reservation_pk PRIMARY KEY (reservationid)
);

-- Table: ReservationAttraction
CREATE TABLE ReservationAttraction (
    reservationattractionid serial  NOT NULL,
    reservationid int  NOT NULL,
    attractionid int  NOT NULL,
    datefrom date  NOT NULL,
    dateto date  NOT NULL,
    hourfrom time  NOT NULL,
    hourto time  NOT NULL,
    people int  NULL,
    items int  NULL,
    message text  NULL,
    price money  NOT NULL,
    paid boolean  NOT NULL,
    cancelreservationdate date  NULL,
    CONSTRAINT ReservationAttraction_pk PRIMARY KEY (reservationattractionid)
);

-- Table: ReservationAttractionItem
CREATE TABLE ReservationAttractionItem (
    reservationattractionid int  NOT NULL,
    itemid int  NOT NULL
);

-- Table: ReservationPlan
CREATE TABLE ReservationPlan (
    reservationplanid serial  NOT NULL,
    reservationid int  NOT NULL,
    turisticplaceid int  NOT NULL,
    name varchar(100)  NOT NULL,
    date date  NOT NULL,
    time time  NOT NULL,
    note text  NOT NULL,
    CONSTRAINT ReservationPlan_pk PRIMARY KEY (reservationplanid)
);

-- Table: ReservationStatus
CREATE TABLE ReservationStatus (
    reservationstatusid serial  NOT NULL,
    name varchar(50)  NOT NULL,
    CONSTRAINT ReservationStatus_pk PRIMARY KEY (reservationstatusid)
);

-- Table: ReservationStayOpinon
CREATE TABLE ReservationStayOpinon (
    stayid int  NOT NULL,
    reservationid int  NOT NULL,
    stars decimal(1,1)  NOT NULL,
    comment int  NULL
);

-- Table: Stay
CREATE TABLE Stay (
    stayid serial  NOT NULL,
    turisticplaceid int  NOT NULL,
    name varchar(100)  NOT NULL,
    description varchar(255)  NOT NULL,
    price money  NOT NULL,
    datefrom date  NOT NULL,
    dateto date  NULL,
    people int  NOT NULL,
    CONSTRAINT Stay_pk PRIMARY KEY (stayid)
);

-- Table: TuristicPlace
CREATE TABLE TuristicPlace (
    turisticplaceid serial  NOT NULL,
    premiumid int  NOT NULL,
    addressid int  NOT NULL,
    ownerid int  NOT NULL,
    turisticplacetypeid int  NOT NULL,
    name varchar(100)  NOT NULL,
    description text  NOT NULL,
    checkin time  NOT NULL,
    checkout time  NOT NULL,
    prepayment boolean  NOT NULL,
    cancelreservationdays int  NULL,
    importantinformation text  NULL,
    CONSTRAINT TuristicPlace_pk PRIMARY KEY (turisticplaceid)
);

-- Table: TuristicPlaceType
CREATE TABLE TuristicPlaceType (
    turisticplacetypeid serial  NOT NULL,
    name varchar(100)  NOT NULL,
    CONSTRAINT TuristicPlaceType_pk PRIMARY KEY (turisticplacetypeid)
);

-- Reference: Account_AccountType (table: Account)
ALTER TABLE Account ADD CONSTRAINT Account_AccountType
    FOREIGN KEY (accounttypeid)
    REFERENCES AccountType (accounttypeid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Account_Address (table: Account)
ALTER TABLE Account ADD CONSTRAINT Account_Address
    FOREIGN KEY (addressid)
    REFERENCES Address (addressid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Attraction_AttractionDateInterval (table: Attraction)
ALTER TABLE Attraction ADD CONSTRAINT Attraction_AttractionDateInterval
    FOREIGN KEY (attractiondateintervalid)
    REFERENCES AttractionDateInterval (attractiondateintervalid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Attraction_AttractionType (table: Attraction)
ALTER TABLE Attraction ADD CONSTRAINT Attraction_AttractionType
    FOREIGN KEY (attractiontypeid)
    REFERENCES AttractionType (attractiontypeid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Attraction_PriceType (table: Attraction)
ALTER TABLE Attraction ADD CONSTRAINT Attraction_PriceType
    FOREIGN KEY (pricetypeid)
    REFERENCES PriceType (pricetypeid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Attraction_TuristicPlace (table: Attraction)
ALTER TABLE Attraction ADD CONSTRAINT Attraction_TuristicPlace
    FOREIGN KEY (turisticplaceid)
    REFERENCES TuristicPlace (turisticplaceid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: FavoriteOffer_Account (table: FavoriteOffer)
ALTER TABLE FavoriteOffer ADD CONSTRAINT FavoriteOffer_Account
    FOREIGN KEY (accountid)
    REFERENCES Account (accountid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: FavoriteOffer_Stay (table: FavoriteOffer)
ALTER TABLE FavoriteOffer ADD CONSTRAINT FavoriteOffer_Stay
    FOREIGN KEY (stayid)
    REFERENCES Stay (stayid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: GuaranteedService_TuristicPlace (table: GuaranteedService)
ALTER TABLE GuaranteedService ADD CONSTRAINT GuaranteedService_TuristicPlace
    FOREIGN KEY (turisticplaceid)
    REFERENCES TuristicPlace (turisticplaceid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Information_Stay (table: Information)
ALTER TABLE Information ADD CONSTRAINT Information_Stay
    FOREIGN KEY (stayid)
    REFERENCES Stay (stayid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Item_Attraction (table: Item)
ALTER TABLE Item ADD CONSTRAINT Item_Attraction
    FOREIGN KEY (attractionid)
    REFERENCES Attraction (attractionid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Photo_Attraction (table: Photo)
ALTER TABLE Photo ADD CONSTRAINT Photo_Attraction
    FOREIGN KEY (attractionid)
    REFERENCES Attraction (attractionid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Photo_Stay (table: Photo)
ALTER TABLE Photo ADD CONSTRAINT Photo_Stay
    FOREIGN KEY (stayid)
    REFERENCES Stay (stayid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Photo_TuristicPlace (table: Photo)
ALTER TABLE Photo ADD CONSTRAINT Photo_TuristicPlace
    FOREIGN KEY (turisticplaceid)
    REFERENCES TuristicPlace (turisticplaceid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Premium_Account (table: Premium)
ALTER TABLE Premium ADD CONSTRAINT Premium_Account
    FOREIGN KEY (accountid)
    REFERENCES Account (accountid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: ReservationAttractionItem_Item (table: ReservationAttractionItem)
ALTER TABLE ReservationAttractionItem ADD CONSTRAINT ReservationAttractionItem_Item
    FOREIGN KEY (itemid)
    REFERENCES Item (itemid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: ReservationAttractionItem_ReservationAttraction (table: ReservationAttractionItem)
ALTER TABLE ReservationAttractionItem ADD CONSTRAINT ReservationAttractionItem_ReservationAttraction
    FOREIGN KEY (reservationattractionid)
    REFERENCES ReservationAttraction (reservationattractionid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: ReservationAttraction_Attraction (table: ReservationAttraction)
ALTER TABLE ReservationAttraction ADD CONSTRAINT ReservationAttraction_Attraction
    FOREIGN KEY (attractionid)
    REFERENCES Attraction (attractionid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: ReservationAttraction_Reservation (table: ReservationAttraction)
ALTER TABLE ReservationAttraction ADD CONSTRAINT ReservationAttraction_Reservation
    FOREIGN KEY (reservationid)
    REFERENCES Reservation (reservationid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: ReservationPlan_Reservation (table: ReservationPlan)
ALTER TABLE ReservationPlan ADD CONSTRAINT ReservationPlan_Reservation
    FOREIGN KEY (reservationid)
    REFERENCES Reservation (reservationid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: ReservationPlan_TuristicPlace (table: ReservationPlan)
ALTER TABLE ReservationPlan ADD CONSTRAINT ReservationPlan_TuristicPlace
    FOREIGN KEY (turisticplaceid)
    REFERENCES TuristicPlace (turisticplaceid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: ReservationStayOpinon_Reservation (table: ReservationStayOpinon)
ALTER TABLE ReservationStayOpinon ADD CONSTRAINT ReservationStayOpinon_Reservation
    FOREIGN KEY (reservationid)
    REFERENCES Reservation (reservationid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: ReservationStayOpinon_Stay (table: ReservationStayOpinon)
ALTER TABLE ReservationStayOpinon ADD CONSTRAINT ReservationStayOpinon_Stay
    FOREIGN KEY (stayid)
    REFERENCES Stay (stayid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Reservation_Account (table: Reservation)
ALTER TABLE Reservation ADD CONSTRAINT Reservation_Account
    FOREIGN KEY (accountid)
    REFERENCES Account (accountid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Reservation_ReservationStatus (table: Reservation)
ALTER TABLE Reservation ADD CONSTRAINT Reservation_ReservationStatus
    FOREIGN KEY (reservationstatusid)
    REFERENCES ReservationStatus (reservationstatusid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Reservation_Stay (table: Reservation)
ALTER TABLE Reservation ADD CONSTRAINT Reservation_Stay
    FOREIGN KEY (stayid)
    REFERENCES Stay (stayid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Stay_TuristicPlace (table: Stay)
ALTER TABLE Stay ADD CONSTRAINT Stay_TuristicPlace
    FOREIGN KEY (turisticplaceid)
    REFERENCES TuristicPlace (turisticplaceid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: TuristicPlace_Address (table: TuristicPlace)
ALTER TABLE TuristicPlace ADD CONSTRAINT TuristicPlace_Address
    FOREIGN KEY (addressid)
    REFERENCES Address (addressid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: TuristicPlace_Owner (table: TuristicPlace)
ALTER TABLE TuristicPlace ADD CONSTRAINT TuristicPlace_Owner
    FOREIGN KEY (ownerid)
    REFERENCES Owner (ownerid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: TuristicPlace_Premium (table: TuristicPlace)
ALTER TABLE TuristicPlace ADD CONSTRAINT TuristicPlace_Premium
    FOREIGN KEY (premiumid)
    REFERENCES Premium (premiumid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: TuristicPlace_TuristicPlaceType (table: TuristicPlace)
ALTER TABLE TuristicPlace ADD CONSTRAINT TuristicPlace_TuristicPlaceType
    FOREIGN KEY (turisticplacetypeid)
    REFERENCES TuristicPlaceType (turisticplacetypeid)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;