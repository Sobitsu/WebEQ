/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sobit.webeq.services.users.rest;

/**
 *
 * @author Владелец
 */
import java.util.List;

import com.sobit.webeq.services.dao.data.users.IUsersDAO;
import com.sobit.webeq.services.dao.data.parameters.users.UsersListParameters;
import com.sobit.webeq.entities.Users;
import com.sobit.webeq.services.rest.response.ResponseCreator;
import com.sobit.webeq.services.data.parameters.Order;
import com.sobit.webeq.services.users.rest.exceptions.Errors;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
public class UsersServiceJSON implements IUsersServices {
       // link to our dao object
	private IUsersDAO UsersDAO;

	// for UsersDAO bean property injection
	public IUsersDAO getUsersDAO() {
		return UsersDAO;
	}

	public void setUsersDAO(IUsersDAO UsersDAO) {
		this.UsersDAO = UsersDAO;
	}

	// for retrieving request headers from context
	// an injectable interface that provides access to HTTP header information.
	@Context
	private HttpHeaders requestHeaders;

	private String getHeaderVersion() {
		return requestHeaders.getRequestHeader("version").get(0);
	}

	// get by id service
	@GET
	@Path("/{id}")
	public Response getUser(@PathParam("id") Integer id) {
		Users users = UsersDAO.getUser(id);
		if (users != null) {
			return ResponseCreator.success(getHeaderVersion(), users);
		} else {
			return ResponseCreator.error(404, Errors.NOT_FOUND.getCode(),
					getHeaderVersion());
		}
	}

	// remove row from the Users table according with passed id and returned
	// status message in body
	@DELETE
	@Path("/{id}")
	public Response removeUser(@PathParam("id") Integer id) {
		if (UsersDAO.removeUser(id)) {
			return ResponseCreator.success(getHeaderVersion(), "removed");
		} else {
			return ResponseCreator.success(getHeaderVersion(), "no such id");
		}
	}

	// create row representing User and returns created User as
	// object->JSON structure
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(Users users) {
		System.out.println("POST");
		Users creUser = UsersDAO.createUser(users);
		if (creUser != null) {
			return ResponseCreator.success(getHeaderVersion(), creUser);
		} else {
			return ResponseCreator.error(500, Errors.SERVER_ERROR.getCode(),
					getHeaderVersion());
		}
	}

	// update row and return previous version of row representing User as
	// object->JSON structure
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(Users users) {
		Users updUser = UsersDAO.updateUser(users);
		if (updUser != null) {
			return ResponseCreator.success(getHeaderVersion(), updUser);
		} else {
			return ResponseCreator.error(500, Errors.SERVER_ERROR.getCode(),
					getHeaderVersion());
		}
	}

	// returns list of Users meeting query params
	@GET
	//@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@QueryParam("keyword") String keyword,
			@QueryParam("orderby") String orderBy,
			@QueryParam("order") String order,
			@QueryParam("pagenum") Integer pageNum,
			@QueryParam("pagesize") Integer pageSize) {
		UsersListParameters parameters = new UsersListParameters();
		parameters.setKeyword(keyword);
		parameters.setPageNum(pageNum);
		parameters.setPageSize(pageSize);
		parameters.setOrderBy(orderBy);
		parameters.setOrder(Order.fromString(order));
		List<Users> listCust = UsersDAO.getUsersList(parameters);
		if (listCust != null) {
			GenericEntity<List<Users>> entity = new GenericEntity<List<Users>>(
					listCust) {
			};
			return ResponseCreator.success(getHeaderVersion(), entity);
		} else {
			return ResponseCreator.error(404, Errors.NOT_FOUND.getCode(),
					getHeaderVersion());
		}
	}   
}
