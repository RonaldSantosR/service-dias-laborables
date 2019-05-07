USE [db_service_horario]

INSERT [dbo].[Dia] ([nombre]) VALUES ('Lunes')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Martes')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Miércoles')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Jueves')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Viernes')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Sábado')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Domingo')


INSERT [dbo].[Ambito] ([nombre]) VALUES ('Lima')
INSERT [dbo].[Ambito] ([nombre]) VALUES ('Provincia')


INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',1,1,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',2,1,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',3,1,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',4,1,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',5,1,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',6,1,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',7,1,1)


INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',1,2,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',2,2,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',3,2,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',4,2,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',5,2,0)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',6,2,0)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',7,2,1)

INSERT INTO [dbo].[Feriado] ([fecha],[nombre],[periodo]) VALUES ('10-02-2020','navidad',0)
INSERT INTO [dbo].[Feriado] ([fecha],[nombre],[periodo]) VALUES ('28-02-1970','pascuas',1)

INSERT INTO [dbo].[Feriado] ([fecha],[nombre],[periodo]) VALUES ('10-02-2019','san valentin',0)
INSERT INTO [dbo].[Feriado] ([fecha],[nombre],[periodo]) VALUES ('05-02-1970','año nuevo',1)

INSERT INTO [dbo].[Feriado] ([fecha],[nombre],[periodo]) VALUES ('12-02-1970','combate',1)
INSERT INTO [dbo].[Feriado] ([fecha],[nombre],[periodo]) VALUES ('19-02-2019','halloween',0)

INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (1,1);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (2,1);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (3,1);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (1,2);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (2,2);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (3,2);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (4,1);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (5,1);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (6,2);

