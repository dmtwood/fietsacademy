insert into docenten(voornaam, familienaam, wedde, emailAdres, geslacht, campusid)
VALUES ('testM', 'testM', 1000, 'testM@test.be','MAN', (select id from campussen where naam='testc'));

insert into docenten(voornaam, familienaam, wedde, emailAdres, geslacht, campusid)
VALUES ('testV', 'testV', 1000, 'testV@test.be','VROUW', (select id from campussen where naam='testc'));

insert into docentenbijnamen (docentid, bijnaam)
values ((select id from docenten where voornaam = 'testM'), 'test');
