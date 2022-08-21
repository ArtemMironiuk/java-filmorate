merge into MPA (mpa_id, mpa_name)
values(1, 'G');
merge into MPA (mpa_id, mpa_name)
    values(2, 'PG');
merge into MPA (mpa_id, mpa_name)
    values(3, 'PG-13');
merge into MPA (mpa_id, mpa_name)
    values(4, 'R');
merge into MPA (mpa_id, mpa_name)
    values(5, 'NC-17');

insert into GENRES(GENRE_ID, GENRE_NAME)
values (1 ,'Комедия');
insert into GENRES(GENRE_ID, GENRE_NAME)
values (2 ,'Драма');
insert into GENRES(GENRE_ID, GENRE_NAME)
values (3 ,'Мультфильм');
insert into GENRES(GENRE_ID, GENRE_NAME)
values (4 ,'Триллер');
insert into GENRES(GENRE_ID, GENRE_NAME)
values (5 ,'Документальный');
insert into GENRES(GENRE_ID, GENRE_NAME)
values (6 ,'Боевик');
-- заполнение таблички с жанрами