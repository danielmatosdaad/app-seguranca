/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.br4dev.imob.rest.imp;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.br4dev.imob.infraestrutura.dto.MonitorDTO;
import com.br4dev.imob.infraestrutura.execao.InfraException;
import com.br4dev.imob.infraestrutura.execao.NegocioException;
import com.br4dev.imob.infraestrutura.servico.IServicoMonitor;
import com.br4dev.imob.negocio.dto.filtros.FiltroDTO;

@Path("/monitor")
@RequestScoped
public class MonitoracaoRest {

	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private IServicoMonitor servicoMonitor;

	@POST
	@Path("/filtro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<MonitorDTO> filtrar(FiltroDTO filtro) {
		List<MonitorDTO> monitores = null;
		try {
			monitores = servicoMonitor.filtrar(filtro);
		} catch (NegocioException | InfraException e) {
			e.printStackTrace();
		}
		if (monitores == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return monitores;
	}

}
