USE [db_service_horario]

INSERT [dbo].[Dia] ([nombre]) VALUES ('Lunes')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Martes')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Miércoles')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Jueves')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Viernes')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Sábado')
INSERT [dbo].[Dia] ([nombre]) VALUES ('Domingo')


INSERT [dbo].[Ambito] ([nombre]) VALUES ('LIMA')
INSERT [dbo].[Ambito] ([nombre]) VALUES ('PROVINCIA')

SET IDENTITY_INSERT [dbo].[tipo_periodo] ON 
INSERT [dbo].[tipo_periodo] ([tipo_periodo_id], [nombre]) VALUES (1, N'FERIADO PERMANENTE')
INSERT [dbo].[tipo_periodo] ([tipo_periodo_id], [nombre]) VALUES (2, N'FERIADO AÑO')
SET IDENTITY_INSERT [dbo].[tipo_periodo] OFF

INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',1,1,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',2,1,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',3,1,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',4,1,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',5,1,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',6,1,0)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',7,1,0)


INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',1,2,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',2,2,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',3,2,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',4,2,1)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',5,2,0)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',6,2,0)
INSERT [dbo].[Dia_Hora] ([inicio],[fin],[dia_id],[Ambito_id],[activo]) VALUES ('08:00:00.000','17:00:00.000',7,2,1)



INSERT INTO [dbo].[Feriado] ([fecha],[nombre],[tipo_periodo_id]) VALUES ('10-02-2020','navidad',2)
INSERT INTO [dbo].[Feriado] ([fecha],[nombre],[tipo_periodo_id]) VALUES ('28-02-1970','pascuas',1)

INSERT INTO [dbo].[Feriado] ([fecha],[nombre],[tipo_periodo_id]) VALUES ('10-02-2019','san valentin',2)
INSERT INTO [dbo].[Feriado] ([fecha],[nombre],[tipo_periodo_id]) VALUES ('05-02-1970','año nuevo',1)

INSERT INTO [dbo].[Feriado] ([fecha],[nombre],[tipo_periodo_id]) VALUES ('12-02-1970','combate',1)
INSERT INTO [dbo].[Feriado] ([fecha],[nombre],[tipo_periodo_id]) VALUES ('19-02-2019','halloween',2)

INSERT INTO [dbo].[Feriado] ([fecha],[nombre],[tipo_periodo_id]) VALUES ('11-05-2019','Combate de Angamos',1)

INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (1,1);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (2,1);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (3,1);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (1,2);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (2,2);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (3,2);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (4,1);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (5,1);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (6,2);
INSERT INTO [dbo].[feriado_ambito]([feriado_id],[ambito_id]) VALUES (7,1);


SET IDENTITY_INSERT [dbo].[sub_ambito] ON 
INSERT [dbo].[sub_ambito] ([sub_ambito_id], [nombre], [activo], [ambito_id]) VALUES (1, 'LIMA', 1, 1)
INSERT [dbo].[sub_ambito] ([sub_ambito_id], [nombre], [activo], [ambito_id]) VALUES (2, 'PROVINCIA URBANA', 1, 2)
INSERT [dbo].[sub_ambito] ([sub_ambito_id], [nombre], [activo], [ambito_id]) VALUES (3, 'PROVINCIA RURAL', 1, 2 )
INSERT [dbo].[sub_ambito] ([sub_ambito_id], [nombre], [activo], [ambito_id]) VALUES (4, 'PROVINCIA LEJANA', 1, 2 )
SET IDENTITY_INSERT [dbo].[sub_ambito] OFF


