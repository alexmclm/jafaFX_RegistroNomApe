-- create database usuariosRegistro;
/*
create table usuariosRegistro.users(
	idUsers int unsigned auto_increment primary key,
	nombreUser varchar(50),
    apellidoUser varchar(50)
);
*/
-- alter table usuariosRegistro.users change nombUser nombreUser varchar(50) not null;
-- alter table usuariosRegistro.users change apUser apellidoUser varchar(50) not null;
 select users.apellidoUser from usuariosRegistro.users;	
