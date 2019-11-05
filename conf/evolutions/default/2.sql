# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups -- clave : 123456
INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (1, false, 'Panel', 'admin.principal', 'fa fa-bar-chart', '1', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (2, false, 'Usuarios', 'admin.usuarios', 'fa fa-user', '2', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (3, false, 'Recursos', '', 'fa fa-angle-down', '3', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (4, false, 3, 'Categorías', 'admin.categorias', 'fa fa-bars', '1', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (5, false, 3, 'Modelos', 'admin.modelos', 'fa fa-bars', '2', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (6, false, 'Operaciones', '', 'fa fa-angle-down', '4', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (7, false, 6, 'Minas', 'admin.minas', 'fa fa-building-o', '1', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (8, false, 6, 'Superintendencias', 'admin.superintendencias', 'fa fa-building-o', '2', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (9, false, 6, 'Equipos', 'admin.equipos', 'fa fa-truck', '3', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (10, false, 'Reportería', '', 'fa fa-angle-down', '5', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (11, false, 10, 'Secciones', 'admin.secciones', 'fa fa-list-alt', '1', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (12, false, 10, 'Formatos', 'admin.formatos', 'fa fa-table', '2', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (13, false, 'Consultas en linea', '', 'fa fa-angle-down', '6', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (14, false, 13, 'Inspecciones', 'admin.consulta-linea', 'fa fa-files-o', '1', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (15, false, 'Seguimientos', '', 'fa fa-angle-down', '7', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (16, false, 15, 'Preventivo', 'admin.seguimiento-mantpre', 'fa fa-cogs', '1', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (17, false, 15, 'Eventos', 'admin.seguimiento-correctivo', 'fa fa-cogs', '2', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (18, false, 15, 'Temperatura', 'admin.seguimiento-temperatura', 'fa fa-cogs', '3', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- PERFIL SOPORTE
INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (20, false, 'Panel', 'admin.principal', 'fa fa-bar-chart', '1', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (21, false, 'Recursos', '', 'fa fa-angle-down', '2', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (22, false, 21, 'Categorías', 'admin.categorias', 'fa fa-bars', '1', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (23, false, 21, 'Modelos', 'admin.modelos', 'fa fa-bars', '2', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (24, false, 'Operaciones', '', 'fa fa-angle-down', '3', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (25, false, 24, 'Minas', 'admin.minas', 'fa fa-building-o', '1', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (26, false, 24, 'Superintendencias', 'admin.superintendencias', 'fa fa-building-o', '2', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (27, false, 24, 'Equipos', 'admin.equipos', 'fa fa-truck', '3', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (28, false, 'Reportería', '', 'fa fa-angle-down', '4', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (29, false, 28, 'Secciones', 'admin.secciones', 'fa fa-list-alt', '1', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (30, false, 28, 'Formatos', 'admin.formatos', 'fa fa-table', '2', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (31, false, 'Consultas en linea', '', 'fa fa-angle-down', '5', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (32, false, 31, 'Inspecciones', 'admin.consulta-linea', 'fa fa-files-o', '1', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (33, false, 'Seguimientos', '', 'fa fa-angle-down', '6', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (34, false, 33, 'Preventivo', 'admin.seguimiento-mantpre', 'fa fa-cogs', '1', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (35, false, 33, 'Eventos', 'admin.seguimiento-correctivo', 'fa fa-cogs', '2', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (36, false, 33, 'Temperatura', 'admin.seguimiento-temperatura', 'fa fa-cogs', '3', 'SOP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--PERFIL DE SUPERVISOR

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (48, false, 'Panel', 'admin.principal', 'fa fa-bar-chart', '1', 'SUP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (49, false, 'Consultas en linea', '', 'fa fa-angle-down', '2', 'SUP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (50, false, 49, 'Inspecciones', 'admin.consulta-linea', 'fa fa-files-o', '1', 'SUP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (51, false, 'Seguimientos', '', 'fa fa-angle-down', '3', 'SUP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (52, false, 51, 'Preventivo', 'admin.seguimiento-mantpre', 'fa fa-cogs', '1', 'SUP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (53, false, 51, 'Eventos', 'admin.seguimiento-correctivo', 'fa fa-cogs', '2', 'SUP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (54, false, 51, 'Temperatura', 'admin.seguimiento-temperatura', 'fa fa-cogs', '3', 'SUP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- PERFIL SUPERVISOR CLIENTE

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (55, false, 'Panel', 'admin.principal', 'fa fa-bar-chart', '1', 'CLI', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (56, false, 'Consultas en linea', '', 'fa fa-angle-down', '2', 'CLI', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (57, false, 56, 'Inspecciones', 'admin.consulta-linea', 'fa fa-files-o', '1', 'CLI', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (58, false, 'Seguimientos', '', 'fa fa-angle-down', '3', 'CLI', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (59, false, 58, 'Preventivo', 'admin.seguimiento-mantpre', 'fa fa-cogs', '1', 'CLI', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (60, false, 58, 'Eventos', 'admin.seguimiento-correctivo', 'fa fa-cogs', '2', 'CLI', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (61, false, 58, 'Temperatura', 'admin.seguimiento-temperatura', 'fa fa-cogs', '3', 'CLI', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- PERFIL INSPECTOR

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (62, false, 'Panel', 'admin.principal', 'fa fa-bar-chart', '1', 'INS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (63, false, 'Consultas en linea', '', 'fa fa-angle-down', '2', 'INS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (64, false, 63, 'Inspecciones', 'admin.consulta-linea', 'fa fa-files-o', '1', 'INS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO items_menu(id, eliminado, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (65, false, 'Seguimientos', '', 'fa fa-angle-down', '3', 'INS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (66, false, 65, 'Preventivo', 'admin.seguimiento-mantpre', 'fa fa-cogs', '1', 'INS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (67, false, 65, 'Eventos', 'admin.seguimiento-correctivo', 'fa fa-cogs', '2', 'INS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO items_menu(id, eliminado, item_id, title, sref, icon, indice_posicion, tipo_usuario, fecha_creacion, fecha_actualizacion)
    VALUES (68, false, 65, 'Temperatura', 'admin.seguimiento-temperatura', 'fa fa-cogs', '3', 'INS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--INSERT INTO categorias(nombre, fecha_creacion, fecha_actualizacion) values ('Equipos de acarreo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--INSERT INTO categorias(nombre, fecha_creacion, fecha_actualizacion) values ('Equipos de carguio', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--INSERT INTO categorias(nombre, fecha_creacion, fecha_actualizacion) values ('Equipos de perforación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--INSERT INTO categorias(nombre, fecha_creacion, fecha_actualizacion) values ('Equipos auxiliares', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--INSERT INTO modelos(nombre, categoria_id, fecha_creacion, fecha_actualizacion) VALUES ('ACA001', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--INSERT INTO modelos(nombre, categoria_id, fecha_creacion, fecha_actualizacion) VALUES ('CAR001', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--INSERT INTO modelos(nombre, categoria_id, fecha_creacion, fecha_actualizacion) VALUES ('PER001', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--INSERT INTO modelos(nombre, categoria_id, fecha_creacion, fecha_actualizacion) VALUES ('AUX001', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--INSERT INTO minas(nombre, fecha_creacion, fecha_actualizacion) values ('Antamina', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--INSERT INTO minas(nombre, fecha_creacion, fecha_actualizacion) values ('Espinar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
--INSERT INTO superintendencias(nombre, mina_id, fecha_creacion, fecha_actualizacion) values ('AntaSe1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--INSERT INTO superintendencias(nombre, mina_id, fecha_creacion, fecha_actualizacion) values ('EspSe1', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
--INSERT INTO equipos(nombre, superintendencia_id, modelo_id, dias_optimo, dias_critico, fecha_creacion, fecha_actualizacion) VALUES ('CAM-AC001', 1, 1, 5, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--INSERT INTO equipos(nombre, superintendencia_id, modelo_id, dias_optimo, dias_critico, fecha_creacion, fecha_actualizacion) VALUES ('CAM_AC002', 2, 1, 5, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
--INSERT INTO equipos(nombre, superintendencia_id, modelo_id, dias_optimo, dias_critico, fecha_creacion, fecha_actualizacion) VALUES ('CAM-CA001', 1, 2, 5, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--INSERT INTO equipos(nombre, superintendencia_id, modelo_id, dias_optimo, dias_critico, fecha_creacion, fecha_actualizacion) VALUES ('CAM-CA002', 2, 2, 5, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
--INSERT INTO equipos(nombre, superintendencia_id, modelo_id, dias_optimo, dias_critico, fecha_creacion, fecha_actualizacion) VALUES ('CAM-PE001', 1, 3, 5, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--INSERT INTO equipos(nombre, superintendencia_id, modelo_id, dias_optimo, dias_critico, fecha_creacion, fecha_actualizacion) VALUES ('CAM-PE002', 2, 3, 5, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
--INSERT INTO equipos(nombre, superintendencia_id, modelo_id, dias_optimo, dias_critico, fecha_creacion, fecha_actualizacion) VALUES ('CAM-AU001', 1, 4, 5, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--INSERT INTO equipos(nombre, superintendencia_id, modelo_id, dias_optimo, dias_critico, fecha_creacion, fecha_actualizacion) VALUES ('CAM-AU002', 2, 4, 5, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
insert into componentes (nombre, contenido, fecha_creacion, fecha_actualizacion) values ('Texto', '{"data":{"obj": "label","value":""}}', current_timestamp, current_timestamp);
insert into componentes (nombre, contenido, fecha_creacion, fecha_actualizacion) values ('Caja de texto (Letras)', '{"data": {"obj": "input-text","hint": "","value": ""}}', current_timestamp, current_timestamp);
insert into componentes (nombre, contenido, fecha_creacion, fecha_actualizacion) values ('Caja de texto (Números)', '{"data": {"obj": "input-num","hint": "","value": ""}}', current_timestamp, current_timestamp);
insert into componentes (nombre, contenido, fecha_creacion, fecha_actualizacion) values ('Caja de texto (Alfanumérico)', '{"data": {"obj": "input-textnum","hint": "","value": ""}}', current_timestamp, current_timestamp);
insert into componentes (nombre, contenido, fecha_creacion, fecha_actualizacion) values ('Casilla de verificación', '{"data": {"obj": "input-check","checked": false}}', current_timestamp, current_timestamp);
insert into componentes (nombre, contenido, fecha_creacion, fecha_actualizacion) values ('Combo de opciones', '{"data": {"obj": "input-option","options": [],"value": ""}}', current_timestamp, current_timestamp);
insert into componentes (nombre, contenido, fecha_creacion, fecha_actualizacion) values ('Combo de opciones (Iconos)', '{"data": {"obj": "input-option-ico","options": [{"value": "bueno"}, {"value": "alerta"}, {"value": "malo"}],"value": "bueno"}}', current_timestamp, current_timestamp);
--
--INSERT INTO usuarios(nombres, apellidos, correo, usuario, clave, mina_id, tipo_usuario, telefono, estado, fecha_creacion, fecha_actualizacion) VALUES ('Ronald', 'Villegas', 'ronald@correo.com', 'ronald', '$2a$10$S0Z9cXmvU/nIboGWYEAsdOQLy/HUpCpJr/t66AG3Gsq37st6MGZ36', 1, 'ADM', '789654123', 'A', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);