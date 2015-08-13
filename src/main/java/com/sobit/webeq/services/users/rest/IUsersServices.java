/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sobit.webeq.services.users.rest;

import javax.ws.rs.core.Response;

import com.sobit.webeq.entities.Users;

public interface IUsersServices {

	Response getUser(Integer id);	
	Response removeUser(Integer id);
	Response createUser(Users str);
	Response updateUser(Users str);
	Response getUser(String keyword, String orderBy, String order, Integer pageNum, Integer pageSize);
}
