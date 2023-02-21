INSERT INTO room ( name, address, total_desk ) VALUES ( 'Stanza 1','Via Roma 11', 99 );
INSERT INTO room ( name, address, total_desk ) VALUES ( 'Stanza 2','V', 11 );

INSERT INTO _user_ ( first_name, last_name , email , password , authority )
VALUES ( 'Matteo', 'Rosso', 'm@gmail.com' , 'password' , 'ADMIN');
INSERT INTO _user_ ( first_name, last_name , email , password , authority )
VALUES ( 'Luca', 'Rosso', 'l@gmail.com' , 'password' , 'USER');

INSERT INTO desk ( desk_name, room_id ) SELECT 'A1', ( 1 );
INSERT INTO desk ( desk_name, room_id ) SELECT 'C1' , ( 2 );


