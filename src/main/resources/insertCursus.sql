# single table >>
# insert into cursussen(naam, van, tot, soort) values('testGroep', '2020-01-01', '2020-01-01', 'G');
# insert into cursussen(naam, duurtijd, soort) values('testIndividueel', '3', 'I');
#
# joined table >>
insert into cursussen(naam) values('abc');
insert into groepscursussen(id, van, tot) values((select id from cursussen where naam = 'abc'),'2020-01-01','2020-01-01');
insert into cursussen(naam) values('def');
insert into individuelecursussen(id, duurtijd) values((select id from cursussen where naam = 'def'), 3);
#
#
