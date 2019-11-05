# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table categorias (
  id                            bigserial not null,
  eliminado                     boolean default false,
  nombre                        varchar(255),
  estado                        varchar(1) default 'A',
  fecha_creacion                timestamp not null,
  fecha_actualizacion           timestamp not null,
  constraint pk_categorias primary key (id)
);

create table componentes (
  id                            bigserial not null,
  eliminado                     boolean default false,
  nombre                        varchar(255),
  estado                        varchar(1) default 'A',
  contenido                     jsonb,
  fecha_creacion                timestamp not null,
  fecha_actualizacion           timestamp not null,
  constraint pk_componentes primary key (id)
);

create table equipos (
  id                            bigserial not null,
  eliminado                     boolean default false,
  nombre                        varchar(255),
  estado                        varchar(1) default 'A',
  superintendencia_id           bigint,
  modelo_id                     bigint,
  dias_optimo                   integer,
  dias_critico                  integer,
  fecha_creacion                timestamp not null,
  fecha_actualizacion           timestamp not null,
  constraint pk_equipos primary key (id)
);

create table furs (
  id                            bigserial not null,
  eliminado                     boolean default false,
  nombre                        varchar(255),
  estado                        varchar(1) default 'A',
  modelo_id                     bigint,
  mina_id                       bigint,
  tipo_inspeccion               varchar(2),
  fecha_creacion                timestamp not null,
  fecha_actualizacion           timestamp not null,
  constraint pk_furs primary key (id)
);

create table historial_inspeccion (
  id                            bigserial not null,
  eliminado                     boolean default false,
  estado                        varchar(3),
  inspeccion_id                 bigint,
  usuario_id                    bigint,
  contenido                     jsonb,
  fecha_creacion                timestamp not null,
  fecha_actualizacion           timestamp not null,
  constraint pk_historial_inspeccion primary key (id)
);

create table inspecciones (
  id                            bigserial not null,
  eliminado                     boolean default false,
  estado                        varchar(1) default 'P',
  tipo                          varchar(2),
  equipo_id                     bigint,
  fur_id                        bigint,
  orden_trabajo                 varchar(255),
  prioridad                     varchar(2),
  responsable_id                bigint,
  resumen_actividad             varchar(255),
  observaciones                 varchar(255),
  aprobado_soporte              boolean,
  compartido                    boolean,
  horometro                     bigint,
  inspeccion_cerrada_id         bigint,
  fecha_creacion                timestamp not null,
  fecha_actualizacion           timestamp not null,
  constraint uq_inspecciones_inspeccion_cerrada_id unique (inspeccion_cerrada_id),
  constraint pk_inspecciones primary key (id)
);

create table items_menu (
  id                            bigserial not null,
  eliminado                     boolean default false,
  item_id                       bigint,
  title                         varchar(255),
  sref                          varchar(255),
  icon                          varchar(255),
  indice_posicion               varchar(255),
  tipo_usuario                  varchar(255),
  fecha_creacion                timestamp not null,
  fecha_actualizacion           timestamp not null,
  constraint pk_items_menu primary key (id)
);

create table minas (
  id                            bigserial not null,
  eliminado                     boolean default false,
  nombre                        varchar(255),
  estado                        varchar(1) default 'A',
  logo                          bytea,
  fecha_creacion                timestamp not null,
  fecha_actualizacion           timestamp not null,
  constraint pk_minas primary key (id)
);

create table modelos (
  id                            bigserial not null,
  eliminado                     boolean default false,
  nombre                        varchar(255),
  estado                        varchar(1) default 'A',
  categoria_id                  bigint,
  fecha_creacion                timestamp not null,
  fecha_actualizacion           timestamp not null,
  constraint pk_modelos primary key (id)
);

create table secciones (
  id                            bigserial not null,
  eliminado                     boolean default false,
  nombre                        varchar(255),
  estado                        varchar(1) default 'A',
  tipo                          varchar(2),
  contenido                     jsonb,
  imagen_ayuda                  bytea,
  reporte_temperatura           boolean default false,
  modelo_id                     bigint,
  fecha_creacion                timestamp not null,
  fecha_actualizacion           timestamp not null,
  constraint pk_secciones primary key (id)
);

create table seccion_fur (
  id                            bigserial not null,
  eliminado                     boolean default false,
  fur_id                        bigint,
  seccion_id                    bigint,
  orden                         integer,
  fecha_creacion                timestamp not null,
  fecha_actualizacion           timestamp not null,
  constraint pk_seccion_fur primary key (id)
);

create table superintendencias (
  id                            bigserial not null,
  eliminado                     boolean default false,
  nombre                        varchar(255),
  estado                        varchar(1) default 'A',
  mina_id                       bigint,
  fecha_creacion                timestamp not null,
  fecha_actualizacion           timestamp not null,
  constraint pk_superintendencias primary key (id)
);

create table usuarios (
  id                            bigserial not null,
  eliminado                     boolean default false,
  nombres                       varchar(255),
  apellidos                     varchar(255),
  correo                        varchar(255),
  usuario                       varchar(255),
  clave                         varchar(255),
  tipo_usuario                  varchar(255),
  telefono                      varchar(255),
  estado                        varchar(1) default 'A',
  mina_id                       bigint,
  fecha_creacion                timestamp not null,
  fecha_actualizacion           timestamp not null,
  constraint uq_usuarios_correo unique (correo),
  constraint uq_usuarios_usuario unique (usuario),
  constraint pk_usuarios primary key (id)
);

alter table equipos add constraint fk_equipos_superintendencia_id foreign key (superintendencia_id) references superintendencias (id) on delete restrict on update restrict;
create index ix_equipos_superintendencia_id on equipos (superintendencia_id);

alter table equipos add constraint fk_equipos_modelo_id foreign key (modelo_id) references modelos (id) on delete restrict on update restrict;
create index ix_equipos_modelo_id on equipos (modelo_id);

alter table furs add constraint fk_furs_modelo_id foreign key (modelo_id) references modelos (id) on delete restrict on update restrict;
create index ix_furs_modelo_id on furs (modelo_id);

alter table furs add constraint fk_furs_mina_id foreign key (mina_id) references minas (id) on delete restrict on update restrict;
create index ix_furs_mina_id on furs (mina_id);

alter table historial_inspeccion add constraint fk_historial_inspeccion_inspeccion_id foreign key (inspeccion_id) references inspecciones (id) on delete restrict on update restrict;
create index ix_historial_inspeccion_inspeccion_id on historial_inspeccion (inspeccion_id);

alter table historial_inspeccion add constraint fk_historial_inspeccion_usuario_id foreign key (usuario_id) references usuarios (id) on delete restrict on update restrict;
create index ix_historial_inspeccion_usuario_id on historial_inspeccion (usuario_id);

alter table inspecciones add constraint fk_inspecciones_equipo_id foreign key (equipo_id) references equipos (id) on delete restrict on update restrict;
create index ix_inspecciones_equipo_id on inspecciones (equipo_id);

alter table inspecciones add constraint fk_inspecciones_fur_id foreign key (fur_id) references furs (id) on delete restrict on update restrict;
create index ix_inspecciones_fur_id on inspecciones (fur_id);

alter table inspecciones add constraint fk_inspecciones_responsable_id foreign key (responsable_id) references usuarios (id) on delete restrict on update restrict;
create index ix_inspecciones_responsable_id on inspecciones (responsable_id);

alter table inspecciones add constraint fk_inspecciones_inspeccion_cerrada_id foreign key (inspeccion_cerrada_id) references inspecciones (id) on delete restrict on update restrict;

alter table modelos add constraint fk_modelos_categoria_id foreign key (categoria_id) references categorias (id) on delete restrict on update restrict;
create index ix_modelos_categoria_id on modelos (categoria_id);

alter table secciones add constraint fk_secciones_modelo_id foreign key (modelo_id) references modelos (id) on delete restrict on update restrict;
create index ix_secciones_modelo_id on secciones (modelo_id);

alter table seccion_fur add constraint fk_seccion_fur_fur_id foreign key (fur_id) references furs (id) on delete restrict on update restrict;
create index ix_seccion_fur_fur_id on seccion_fur (fur_id);

alter table seccion_fur add constraint fk_seccion_fur_seccion_id foreign key (seccion_id) references secciones (id) on delete restrict on update restrict;
create index ix_seccion_fur_seccion_id on seccion_fur (seccion_id);

alter table superintendencias add constraint fk_superintendencias_mina_id foreign key (mina_id) references minas (id) on delete restrict on update restrict;
create index ix_superintendencias_mina_id on superintendencias (mina_id);

alter table usuarios add constraint fk_usuarios_mina_id foreign key (mina_id) references minas (id) on delete restrict on update restrict;
create index ix_usuarios_mina_id on usuarios (mina_id);


# --- !Downs

alter table if exists equipos drop constraint if exists fk_equipos_superintendencia_id;
drop index if exists ix_equipos_superintendencia_id;

alter table if exists equipos drop constraint if exists fk_equipos_modelo_id;
drop index if exists ix_equipos_modelo_id;

alter table if exists furs drop constraint if exists fk_furs_modelo_id;
drop index if exists ix_furs_modelo_id;

alter table if exists furs drop constraint if exists fk_furs_mina_id;
drop index if exists ix_furs_mina_id;

alter table if exists historial_inspeccion drop constraint if exists fk_historial_inspeccion_inspeccion_id;
drop index if exists ix_historial_inspeccion_inspeccion_id;

alter table if exists historial_inspeccion drop constraint if exists fk_historial_inspeccion_usuario_id;
drop index if exists ix_historial_inspeccion_usuario_id;

alter table if exists inspecciones drop constraint if exists fk_inspecciones_equipo_id;
drop index if exists ix_inspecciones_equipo_id;

alter table if exists inspecciones drop constraint if exists fk_inspecciones_fur_id;
drop index if exists ix_inspecciones_fur_id;

alter table if exists inspecciones drop constraint if exists fk_inspecciones_responsable_id;
drop index if exists ix_inspecciones_responsable_id;

alter table if exists inspecciones drop constraint if exists fk_inspecciones_inspeccion_cerrada_id;

alter table if exists modelos drop constraint if exists fk_modelos_categoria_id;
drop index if exists ix_modelos_categoria_id;

alter table if exists secciones drop constraint if exists fk_secciones_modelo_id;
drop index if exists ix_secciones_modelo_id;

alter table if exists seccion_fur drop constraint if exists fk_seccion_fur_fur_id;
drop index if exists ix_seccion_fur_fur_id;

alter table if exists seccion_fur drop constraint if exists fk_seccion_fur_seccion_id;
drop index if exists ix_seccion_fur_seccion_id;

alter table if exists superintendencias drop constraint if exists fk_superintendencias_mina_id;
drop index if exists ix_superintendencias_mina_id;

alter table if exists usuarios drop constraint if exists fk_usuarios_mina_id;
drop index if exists ix_usuarios_mina_id;

drop table if exists categorias cascade;

drop table if exists componentes cascade;

drop table if exists equipos cascade;

drop table if exists furs cascade;

drop table if exists historial_inspeccion cascade;

drop table if exists inspecciones cascade;

drop table if exists items_menu cascade;

drop table if exists minas cascade;

drop table if exists modelos cascade;

drop table if exists secciones cascade;

drop table if exists seccion_fur cascade;

drop table if exists superintendencias cascade;

drop table if exists usuarios cascade;

