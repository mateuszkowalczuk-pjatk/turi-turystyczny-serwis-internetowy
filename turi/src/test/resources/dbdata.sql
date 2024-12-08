INSERT INTO "user" (username, email, password, passwordresetcode, passwordresettoken, passwordresetexpiresat)
VALUES ('Janek', 'jan@turi.com', 'JanKowalski123!', 123456, 'sample-password-reset-token', '2024-01-01 12:00:00');

INSERT INTO refreshtoken (userid, token, expiresat)
VALUES (1, 'sample-refresh-token', '2024-01-01 12:00:00');

INSERT INTO address (country, city, zipcode, street, buildingnumber, apartmentnumber)
VALUES ('Polska', 'Warszawa', '01-000', 'Warszawska', '1', 10);

INSERT INTO account (userid, addressid, accounttype, activatecode, activatecodeexpiresat, firstname, lastname, birthdate, phonenumber, gender)
VALUES (1, 1, 0, 123456, '2024-01-01 12:00:00', 'Jan', 'Kowalski', '2000-01-01', '710212453', 1);

INSERT INTO premium (accountid, companyname, nip, bankaccountnumber, buydate, expiresdate, status, logincode, logintoken, loginexpiresat)
VALUES (2, 'Jarex', '1423456812', '120023321456120023321456', '2024-10-10', '2025-10-10', 1, null, null, null);

INSERT INTO stripepayment (intent, status, paymentdate)
VALUES ('sample_payment_intent', 2, '2024-10-10 12:00:00');

INSERT INTO payment (premiumid, stripeid, stripepaymentintent, amount, paymentdate, method, status)
VALUES (1, 'sample_stripe_session_id', 'sample_payment_intent', 100.25, '2024-10-10 12:00:00', 1, 2);