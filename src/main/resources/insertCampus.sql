insert into campussen(naam, straat, huisNr, postCode, gemeente)
VALUES('test', 'test', 'test', 'test', 'test');
insert into campussentelefoonnrs (campusId, nummer, fax, opmerking)
VALUES ((select id from campussen where naam='test'), '1', false, 'test')