/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     29/05/2018 14:28:50                          */
/*==============================================================*/


drop table if exists CITA;

drop table if exists DOCTOR;

drop table if exists ESPECIALIDAD;

drop table if exists HORARIO;

drop table if exists PACIENTE;

drop table if exists ROL;

drop table if exists SECRETARIA;

drop table if exists TRATAMIENTO;

drop table if exists USUARIO;

/*==============================================================*/
/* Table: CITA                                                  */
/*==============================================================*/
create table CITA
(
   ID_CITA              int not null,
   ID_PACIENTE2         int,
   ID_PACIENTE          int,
   FECHA                date not null,
   HORA_INICIO          time not null,
   HORA_FIN             time not null,
   COMENTARIO           varchar(200),
   primary key (ID_CITA)
);

/*==============================================================*/
/* Table: DOCTOR                                                */
/*==============================================================*/
create table DOCTOR
(
   ID_PACIENTE2         int not null,
   ID_ESPECIALIDAD      int not null,
   NOMBRE_SEC           varchar(40) not null,
   CEDULA_SEC           char(10) not null,
   DIRECCION_SEC        varchar(100) not null,
   TELEFONO_SEC         varchar(12) not null,
   CORREO_SEC           varchar(30) not null,
   primary key (ID_PACIENTE2)
);

/*==============================================================*/
/* Table: ESPECIALIDAD                                          */
/*==============================================================*/
create table ESPECIALIDAD
(
   ID_ESPECIALIDAD      int not null,
   NOMBRE               varchar(20) not null,
   primary key (ID_ESPECIALIDAD)
);

/*==============================================================*/
/* Table: HORARIO                                               */
/*==============================================================*/
create table HORARIO
(
   ID_HORIARIO          int not null,
   ID_PACIENTE2         int,
   INTERVALO_DIAS       char(5) not null,
   HORA_INICIO          time not null,
   HORA_FIN             time not null,
   FECHA_INICIO         date not null,
   FECHA_FIN            date not null,
   primary key (ID_HORIARIO)
);

/*==============================================================*/
/* Table: PACIENTE                                              */
/*==============================================================*/
create table PACIENTE
(
   ID_PACIENTE          int not null,
   NOMBRE_SEC           varchar(40) not null,
   CEDULA_SEC           char(10) not null,
   DIRECCION_SEC        varchar(100) not null,
   TELEFONO_SEC         varchar(12) not null,
   CORREO_SEC           varchar(30) not null,
   TRATAMIENTO          bool not null,
   primary key (ID_PACIENTE)
);

/*==============================================================*/
/* Table: ROL                                                   */
/*==============================================================*/
create table ROL
(
   ID_ROL               int not null,
   NOMBRE_ROL           varchar(20) not null,
   primary key (ID_ROL)
);

/*==============================================================*/
/* Table: SECRETARIA                                            */
/*==============================================================*/
create table SECRETARIA
(
   ID_PACIENTE3         int not null,
   NOMBRE_SEC           varchar(40) not null,
   CEDULA_SEC           char(10) not null,
   DIRECCION_SEC        varchar(100) not null,
   TELEFONO_SEC         varchar(12) not null,
   CORREO_SEC           varchar(30) not null,
   primary key (ID_PACIENTE3)
);

/*==============================================================*/
/* Table: TRATAMIENTO                                           */
/*==============================================================*/
create table TRATAMIENTO
(
   ID_TRATAMIENTO       int not null,
   ID_PACIENTE2         int,
   ID_PACIENTE          int,
   FECHA_INICIO         date not null,
   FECHA_FIN            date not null,
   primary key (ID_TRATAMIENTO)
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO
(
   ID_USUARIO           int not null,
   ID_ROL               int,
   USUARIO              varchar(20) not null,
   CONTRASENA           varchar(256) not null,
   ID_ASOCIADO          int not null,
   SALT                 varchar(40) not null,
   primary key (ID_USUARIO)
);

alter table CITA add constraint FK_RELATIONSHIP_4 foreign key (ID_PACIENTE2)
      references DOCTOR (ID_PACIENTE2) on delete restrict on update restrict;

alter table CITA add constraint FK_RELATIONSHIP_5 foreign key (ID_PACIENTE)
      references PACIENTE (ID_PACIENTE) on delete restrict on update restrict;

alter table DOCTOR add constraint FK_TIENE foreign key (ID_ESPECIALIDAD)
      references ESPECIALIDAD (ID_ESPECIALIDAD) on delete restrict on update restrict;

alter table HORARIO add constraint FK_POSEE foreign key (ID_PACIENTE2)
      references DOCTOR (ID_PACIENTE2) on delete restrict on update restrict;

alter table TRATAMIENTO add constraint FK_RELATIONSHIP_6 foreign key (ID_PACIENTE2)
      references DOCTOR (ID_PACIENTE2) on delete restrict on update restrict;

alter table TRATAMIENTO add constraint FK_RELATIONSHIP_7 foreign key (ID_PACIENTE)
      references PACIENTE (ID_PACIENTE) on delete restrict on update restrict;

alter table USUARIO add constraint FK_RELATIONSHIP_3 foreign key (ID_ROL)
      references ROL (ID_ROL) on delete restrict on update restrict;

INSERT INTO `rol` VALUES (1,'Administrador'),(2,'Doctor'),(3,'Secretaria');

INSERT INTO `secretaria` VALUES (1,'Maria','1544877852','El Condado','123213478','maria@gmai.com');


INSERT INTO `usuario` VALUES (2,1,'admin','Mr5pWPRnitgNq7l4QaFv4GrdhEMFcMwaGAlaNJBmVYQ=',0,'Z7UGFTNtINRnjBV56izv4UXwyBWIYT'),(3,3,'secre','LEVVmL3RnCgVm8QDw3NzN3SaedMPewdUs992i0iiQzs=',1,'PqPbunO2Tpm45nJtQ6w1SXl3HrK3bB');

