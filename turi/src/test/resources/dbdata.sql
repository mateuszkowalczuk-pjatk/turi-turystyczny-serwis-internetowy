INSERT INTO "user" (username, email, password)
VALUES ('Janek', 'jan@gmail.com', 'JanKowalski123!');

INSERT INTO address (country, city, zipcode, street, buildingnumber, apartmentnumber)
VALUES ('Polska', 'Warszawa', '01-000', 'Warszawska', '1', 10);

INSERT INTO account (userid, addressid, accounttype, firstname, lastname, birthdate, phonenumber, gender)
VALUES (1, 1, 1, 'Jan', 'Kowalski', '2000-01-01', '710212453', 1);