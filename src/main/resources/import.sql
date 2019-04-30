USE [db_service_horario]

INSERT [dbo].[Dia] ([nombre]) VALUES ('Lunes')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Martes')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Miércoles')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Jueves')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Viernes')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Sabado')
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
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',5,2,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',6,2,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',7,2,1)

INSERT INTO [dbo].[Feriado] ([fecha],[Ambito_id],[nombre],[periodo]) VALUES ('10-02-2020',1,'navidad',0)
INSERT INTO [dbo].[Feriado] ([fecha],[Ambito_id],[nombre],[periodo]) VALUES ('28-02-1970',1,'pascuas',1)

INSERT INTO [dbo].[Feriado] ([fecha],[Ambito_id],[nombre],[periodo]) VALUES ('10-02-2019',2,'san valentin',0)
INSERT INTO [dbo].[Feriado] ([fecha],[Ambito_id],[nombre],[periodo]) VALUES ('05-02-1970',2,'año nuevo',1)

INSERT INTO [dbo].[Feriado] ([fecha],[Ambito_id],[nombre],[periodo]) VALUES ('12-02-1970',1,'combate',1)
INSERT INTO [dbo].[Feriado] ([fecha],[Ambito_id],[nombre],[periodo]) VALUES ('19-02-2019',1,'halloween',0)


