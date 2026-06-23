create table empresa (
        id                      integer                 not null auto_increment,
        nombre_empresa          varchar(255)            not null unique,
        rut_empresa             varchar(15)             not null unique,
        numero_empresa          varchar(15),
        razon_social            varchar(255),
        primary key (id)
) 
