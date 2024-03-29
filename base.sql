create table catedras(
     caid  varchar not null constraint catedras_pk primary key,
     caurl varchar
);

alter table catedras
    owner to postgres;

create table usuarios
(
    usmail    varchar not null constraint usuarios_pk primary key,
    usnombre  varchar not null,
    uspuntaje integer not null,
    usesadmin boolean not null,
    password  varchar not null
);

alter table usuarios
    owner to postgres;

create table elementos
(
    elnombre            varchar not null,
    elpropietario       varchar constraint fk_propietario_del_elemento  references usuarios on update cascade on delete set null,
    elfechacreacion     date,
    elfechamodificacion date,
    elpath              varchar not null constraint el_path primary key
);

alter table elementos
    owner to postgres;

create table archivos
(
    artamanio      integer not null,
    arextension    varchar not null,
    arpalabraclave varchar,
    arcaid         varchar not null constraint fk_catedra  references catedras on update cascade on delete set null,
    arfuente       bytea   not null,
    constraint archivo_pk primary key (elpath)
)inherits (elementos);

alter table archivos
    owner to postgres;

create table carpetas
(
    cadescripcion varchar,
    constraint carpeta_pk primary key (elpath)
)inherits (elementos);

alter table carpetas
    owner to postgres;


create table comentarios
(
    coid          integer not null constraint comentarios_pk primary key,
    coidelemento  varchar not null,
    coautor       varchar not null constraint fk_autor_del_comentario references usuarios on update cascade on delete cascade,
    codescripcion varchar
);

alter table comentarios
    owner to postgres;

-- triggers --
CREATE OR REPLACE FUNCTION eliminar_comentario() RETURNS trigger AS
$$BEGIN
delete from comentarios where old.elpath like coidelemento;
return old;
END;$$ LANGUAGE plpgsql;

CREATE TRIGGER eliminar_comentario_carpeta
    BEFORE DELETE ON carpetas FOR each row
    EXECUTE PROCEDURE eliminar_comentario();

CREATE TRIGGER eliminar_comentario_carpeta
    BEFORE DELETE ON archivos FOR each row
    EXECUTE PROCEDURE eliminar_comentario();

--------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION actualizar_comentario() RETURNS trigger AS
$$BEGIN
update comentarios set coidelemento = new.elpath  where old.elpath like coidelemento;
return new;
END;$$ LANGUAGE plpgsql;

CREATE TRIGGER actualizar_comentario_carpeta
    BEFORE UPDATE of elpath ON carpetas FOR each row
    EXECUTE PROCEDURE actualizar_comentario();

CREATE TRIGGER actualizar_comentario_archivo
    BEFORE UPDATE of elpath ON archivos FOR each row
    EXECUTE PROCEDURE actualizar_comentario();
