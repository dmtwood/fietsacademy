insert into docenten(voornaam, familienaam, wedde, emailAdres, geslacht)
VALUES ('testM', 'testM', 1000, 'testM@test.be','MAN');
insert into docenten(voornaam, familienaam, wedde, emailAdres, geslacht) VALUES
('testV', 'testV', 1000, 'testV@test.be','VROUW');
insert into docentenbijnamen (docentid, bijnaam)
values ((select id from docenten where voornaam = 'testM'), 'test');