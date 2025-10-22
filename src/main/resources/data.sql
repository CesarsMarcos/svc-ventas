/*TIPO PERSONA*/
INSERT INTO tb_tipo_personas (DESCRIPCION,IND_ESTADO)
VALUES ('CLIENTE',1), ('PROVEEDOR',1), ('EMPLEADO',1);

/*TIPO DOCUMENTO*/
INSERT INTO tb_tipo_documentos (DESCRIPCION,IND_ESTADO)
VALUES ('DNI', 1),('RUC', 1),('BOLETA', 1), ('FACTURA', 1), (',NOTA DE CREDITO', 1);

/*MARCA*/
INSERT INTO tb_marcas (DESCRIPCION,IND_ESTADO,FEC_ADD)
VALUES ('MARVEL', 1,NOW()), ('SONY', 1,NOW()), ('LG', 1,NOW());

/*UNIDAD MEDIDA*/
INSERT INTO tb_unidad_medidas  (NOMBRE,PREFIJO,IND_ESTADO)
VALUES ('CAJA','CAJ', 1), ('UNIDAD','UN', 1);

/*ROL*/
INSERT INTO tb_roles  (ID_ROL,DES_ROL)
VALUES (1,'ROLE_ADMIN'), (2,'ROLE_USER');

/*CATEGORIA*/
INSERT INTO tb_categorias  (DES_CATEGORIA,IND_ESTADO,FEC_ADD)
VALUES ('MOVILES', 1,NOW()), ('TELEVISORES', 1,NOW()), ('COMPUTO', 1,NOW());

/*GLOBAL*/
INSERT INTO tb_globales (EMPRESA,LOGO,NOMBRE_IMPUESTO,PORCENTAJE_IMPUESTO,SIMBOLO_MONEDA,IND_ESTADO)
VALUES ('REWARDS', 'LOGO', 'IGV', 0.18, 'S/', 1);

/*SUCURSAL*/
INSERT INTO tb_sucursales (DIRECCION, EMAIL,IND_ESTADO,LOGO,TIPO_DOCUMENTO)
VALUES ('MI CASA', 'cesars0711@gmail.com', 1, 'SRC', 1);

/*PERSONA*/
INSERT INTO tb_personas (ape_materno,ape_paterno,nombre,celular,correo,direccion,foto,num_documento,telefono,id_tipo_documento,id_tipo_persona)
VALUES ('MARCOS','RAMOS','CESAR','123456789','CESAR@GMAIL.COM','MI CASA','FOTO','12345678',12345678,1,3),
('PARILLO','PORTILLO','MARIA','123456789','flor@GMAIL.COM','MI CASA','FOTO','123456781',12345678,1,3),
('DAVILA','BRAVO','ARTURO','123456789','arturo@gmail.COM','MI CASA','FOTO','123456782',12345678,1,2),
('RAMOS','RODRIGUEZ','ANGEL','123456789','angel@gmail.COM','MI CASA','FOTO','123456783',12345678,1,2),
 ('PALACIOS','NOVIA','DANIEL','123456789','daniel@gmail.COM','MI CASA','FOTO','123456784',12345678,1,2);

/*EMPLEADO*/
INSERT INTO tb_empleados (ID_PERSONA,IND_ESTADO,FEC_ADD) VALUES (1, 1, NOW()), (2, 1, NOW());

/*USUARIO*/
INSERT INTO tb_usuarios (CLAVE,IND_ESTADO, USUARIO,ID_EMPLEADO, ID_SUCURSAL)
VALUES ('$2a$10$BzLfJnkkwTvR1PDdvectleeIZeMCYics3W5vrR45MscE/oqQI51hG', 1 ,'MARCOS', 1, 1);

/*CLIENTES*/
INSERT INTO tb_clientes (id_persona) VALUES (1);

/*PRODUCTO*/
INSERT INTO tb_productos (DESCRIPCION,IMAGEN,IND_ESTADO,MAX_CANTIDAD,MIN_CANTIDAD,
NOMBRE,PRECIO,PRECIO_DESCUENTO, PRECIO_PROVEEDOR,STOCK,ID_CATEGORIA,ID_MARCA,ID_UMEDIDA,COD_USUARIO_REGISTRO,FEC_ADD)
VALUES('PRODUCTO 1', 'srcruta', 1,50, 2, 'NOMBRE 1', 250,5,5,5,1,1,1,1,NOW()),
('PRODUCTO 2', 'srcruta', 1,50, 2, 'NOMBRE 2', 250,5,5,5,1,1,1,1,NOW()),
('PRODUCTO 3', 'srcruta', 1,50, 2, 'NOMBRE 3', 250,5,5,5,1,1,1,1,NOW());

/*SUCURSALES*/
INSERT INTO tb_sucursales (direccion, email, ind_estado, logo, num_documento,razon_social, representante, telefono, id_tipo_documento,
COD_USUARIO_REGISTRO, COD_USUARIO_MOD, fec_add, fec_update)
VALUES ('Av. Perú 123', 'sucursal1@empresa.com', true, 'logo1.png', '20601234567', 'Sucursal Lima Norte', 'Juan Pérez', '999999999', 1, null, null, NOW(), NOW()),
('Calle Los Olivos 456', 'olivos@empresa.com', true, 'logo2.png', '10765432109', 'Sucursal Los Olivos', 'Ana Díaz', '955555555', 2, null, null, NOW(), NOW()),
('Av. Arequipa 789', 'arequipa@empresa.com', false, 'logo3.png', '20456789123', 'Sucursal Arequipa', 'Carlos Ruiz', '944444444', 3, null, null, NOW(), NOW());

