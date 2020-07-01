# single table Inheritance >>
# insert into cursussen(naam, van, tot, soort) values('testGroep', '2020-01-01', '2020-01-01', 'G');
# insert into cursussen(naam, duurtijd, soort) values('testIndividueel', '3', 'I');
#
# joined table Inheritance >>
# insert into cursussen(naam) values('abc');
# insert into groepscursussen(id, van, tot) values((select id from cursussen where naam = 'abc'),'2020-01-01','2020-01-01');
# insert into cursussen(naam) values('def');
# insert into individuelecursussen(id, duurtijd) values((select id from cursussen where naam = 'def'), 3);
#
# table per concrete class Inheritance >>
insert into groepscursussen(id, naam, van, tot)
values(uuid(), 'testGroep', '2018-01-01', '2018-01-01');
insert into individuelecursussen(id, naam,duurtijd)
values(uuid(), 'testIndividueel', 3);