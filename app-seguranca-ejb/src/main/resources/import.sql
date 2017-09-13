--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
insert into parametroseguranca(nomeParametro, valor, descricao) values ('Bar12345Bar12345', 'RandomInitVector', '128 bit key,16 bytes IV') 
insert into autorizacaoservico(idRepositorio, idCatalago, idServico,idPerfil) values ('2', '1', '1','1')
insert into autorizacaoservico(idRepositorio, idCatalago, idServico,idPerfil) values ('2', '2', '1','1')
insert into autorizacaoservico(idRepositorio, idCatalago, idServico,idPerfil) values ('2', '3', '1','1')
insert into autorizacaoservico(idRepositorio, idCatalago, idServico,idPerfil) values ('2', '4', '1','1')
insert into autorizacaoservico(idRepositorio, idCatalago, idServico,idPerfil) values ('2', '5', '1','1')
insert into autorizacaoservico(idRepositorio, idCatalago, idServico,idPerfil) values ('2', '6', '1','1')
insert into autorizacaoservico(idRepositorio, idCatalago, idServico,idPerfil) values ('2', '7', '1','1')
insert into autorizacaoservico(idRepositorio, idCatalago, idServico,idPerfil) values ('2', '8', '1','1')
insert into autorizacaoservico(idRepositorio, idCatalago, idServico,idPerfil) values ('2', '9', '1','1')
insert into autorizacaoservico(idRepositorio, idCatalago, idServico,idPerfil) values ('2', '10', '1','1')
insert into autorizacaoservico(idRepositorio, idCatalago, idServico,idPerfil) values ('2', '11', '1','1')
insert into autorizacaoservico(idRepositorio, idCatalago, idServico,idPerfil) values ('2', '12', '1','1')
insert into autorizacaoservico(idRepositorio, idCatalago, idServico,idPerfil) values ('2', '13', '1','1')


insert into autenticacao(nomeAutenticacao,senhaAutenticacao,ativo,liberado) values ('daniel.matos', 'adm',1,1) 
insert into autenticacao(nomeAutenticacao,senhaAutenticacao,ativo,liberado) values ('aplicativo.imob', 'imob',1,1) 

