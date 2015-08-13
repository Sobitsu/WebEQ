/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sobit.webeq.services.dao.data.users;

import java.util.List;

import com.sobit.webeq.services.dao.data.parameters.users.UsersListParameters;
import com.sobit.webeq.entities.Users;

public interface IUsersDAO {	
	
	Users createUser(Users Users);
	Users getUser(Integer id);	
	Users updateUser(Users Users);	
	boolean removeUser(Integer id);	
	List<Users> getUsersList(UsersListParameters parameters);
}