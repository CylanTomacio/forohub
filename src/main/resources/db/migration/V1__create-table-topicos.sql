create table topicos(

    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(200) not null,
    fechaDeCreacion date not null,
    estado varchar(30) not null,
    autor varchar(100) not null,
    curso varchar(100) not null,

    primary key(id)

);