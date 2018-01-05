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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.br4dev.imob.infraestrutura.execao.InfraException;
import com.br4dev.imob.infraestrutura.execao.NegocioException;
import com.br4dev.imob.negocio.dto.ImovelDTO;
import com.br4dev.imob.negocio.dto.filtros.FiltroDTO;
import com.br4dev.imob.negocio.servico.IServicoImagemImovel;
import com.br4dev.imob.negocio.servico.IServicoImovel;

@Path("/imovel")
@RequestScoped
public class ImovelServicoRest {
	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private IServicoImovel imovelService;

	@Inject
	private IServicoImagemImovel imagemService;

	@POST
	@Path("/filtro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ImovelDTO> filtrar(FiltroDTO filtro) {
		List<ImovelDTO> imoveis = null;
		try {
			imoveis = imovelService.filtrar(filtro);
		} catch (NegocioException | InfraException e) {
			e.printStackTrace();
		}
		if (imoveis == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return imoveis;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/buscar/{page:[0-9][0-9]*}/{results:[0-9][0-9]*}")
	public List<ImovelDTO> buscar(@PathParam("page") int page, @PathParam("results") int results, FiltroDTO filtroDTO) {
		List<ImovelDTO> imoveis = null;
		try {
			imoveis = imovelService.listaComFiltro(filtroDTO, results, page);
		} catch (InfraException | NegocioException e) {
			e.printStackTrace();
		}
		if (imoveis == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return imoveis;
	}


	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public ImovelDTO buscarPorId(@PathParam("id") long id) {
		ImovelDTO imovel = null;
		try {
			imovel = imovelService.buscarPorId(id);
		} catch (NegocioException | InfraException e) {
			e.printStackTrace();
		}
		if (imovel == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return imovel;
	}

	/**
	 * Creates a new member from the values provided. Performs validation, and
	 * will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response criarImovel(ImovelDTO imovel) {

		Response.ResponseBuilder builder = null;

		try {
			validateImovel(imovel);

			imovelService.salvar(imovel);

			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
		} catch (Exception e) {
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}

		return builder.build();
	}

	private void validateImovel(ImovelDTO imovel) throws ConstraintViolationException, ValidationException {
		Set<ConstraintViolation<ImovelDTO>> violations = validator.validate(imovel);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}

	private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {

		Map<String, String> responseObj = new HashMap<>();

		for (ConstraintViolation<?> violation : violations) {
			responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
		}

		return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	}

}
