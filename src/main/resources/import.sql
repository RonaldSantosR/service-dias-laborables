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

INSERT INTO [dbo].[Feriado] ([fecha],[Ambito_id]) VALUES ('10-02-2020',1)
INSERT INTO [dbo].[Feriado] ([fecha],[Ambito_id]) VALUES ('28-02-1970',1)

INSERT INTO [dbo].[Feriado] ([fecha],[Ambito_id]) VALUES ('10-02-2019',2)
INSERT INTO [dbo].[Feriado] ([fecha],[Ambito_id]) VALUES ('05-02-1970',2)

INSERT INTO [dbo].[Feriado] ([fecha],[Ambito_id]) VALUES ('12-02-1970',1)
INSERT INTO [dbo].[Feriado] ([fecha],[Ambito_id]) VALUES ('19-02-2019',1)



INSERT INTO [dbo].[Feriado_año]([asunto],[nombre],[Feriado_id]) VALUES ('TERREMOTO','DIA DE LA PAZ',1)
INSERT INTO [dbo].[Feriado_año]([asunto],[nombre],[Feriado_id]) VALUES ('DIA DE LA TIERRA','DIA MUNDIAL',3) 

INSERT INTO [dbo].[Feriado_permanente]([nombre],[Feriado_id]) VALUES ('DIA DEL NIÑO',2)
INSERT INTO [dbo].[Feriado_permanente]([nombre],[Feriado_id]) VALUES ('DIA DE LA AMISTAD',4) 

INSERT INTO [dbo].[Feriado_permanente]([nombre],[Feriado_id]) VALUES ('DIA DEL AMOR',5) 
INSERT INTO [dbo].[Feriado_año]([asunto],[nombre],[Feriado_id]) VALUES ('DIA DE LA TIERRA','DIA MUNDIAL',6) 
