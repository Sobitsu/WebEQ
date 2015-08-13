/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sobit.webeq.services.dao.data.users;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.*;

import com.sobit.webeq.services.dao.data.parameters.users.UsersListParameters;
import com.sobit.webeq.entities.Users;

public class UsersDAO implements IUsersDAO {

	Map<Integer, Users> profsMap = new HashMap<Integer, Users>();

	DataSource datasource;

	private SimpleJdbcInsert insertUsers;
	private JdbcTemplate templUsers;

	public void setDataSource(DataSource dataSource) {
		this.templUsers = new JdbcTemplate(dataSource);
		this.insertUsers = new SimpleJdbcInsert(dataSource)
				.withTableName("users");
	}

	public Users getUser(Integer id) {
		if ((templUsers
				.queryForInt("Select count(1) FROM users WHERE id = '" + id
						+ "'")) > 0) {
			Users Users = (Users) templUsers.queryForObject(
					"SELECT * FROM users WHERE id = '" + id + "'",
					new RowMapper<Users>() {
						public Users mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							Users Users = new Users();
							Users.setUser(rs.getString("user"));
							Users.setPassword(rs.getString("password"));
							Users.setFio(rs.getString("fio"));
							Users.setPodraz(rs.getInt("podraz"));
							Users.setFilial(rs.getInt("filial"));
							Users.setRole(rs.getInt("role"));
							return Users;
						}
					});

			return Users;
		} else {
			return null;
		}
	}

	public Users createUser(Users Users) {
		if (Users != null) {
			Map<String, Object> parameters = new HashMap<String, Object>(3);
			Integer uuid = UUID.randomUUID().variant();
			Users.setId(uuid);
			parameters.put("id", uuid);
			if (Users.getUser() != null)
				parameters.put("user", Users.getUser());
			if (Users.getPassword() != null)
				parameters.put("password", Users.getPassword());
			parameters.put("fio", Users.getFio());
			parameters.put("podraz", Users.getPodraz());
			parameters.put("filial", Users.getFilial());
			parameters.put("role", Users.getRole());
			insertUsers.execute(parameters);

			return Users;
		} else {
			return null;
		}
	}

	public Users updateUser(Users Users) {
		if (Users != null && Users.getId() != null) {
			Users oldUsers = getUser(Users.getId());
			String sqlUpdate = String
					.format("UPDATE users SET user = %s, password = %s, fio = %s, podraz = %s, filial = %s, role = %s WHERE id = %s",
							"'" + Users.getUser() + "'", "'" +  Users.getPassword()+ "'",
							"'" + Users.getFio()+ "'", "'" + Users.getPodraz().toString()+ "'",
							"'" + Users.getFilial().toString()+ "'", "'" + Users.getRole().toString()+ "'",
							"'" + Users.getId().toString() + "'");
			System.out.println(sqlUpdate);
			templUsers.update(sqlUpdate);
			return oldUsers;
		} else {
			return null;
		}
	}

	public boolean removeUser(Integer id) {
		if (templUsers
				.update("DELETE FROM users WHERE id = '" + id.toString() + "'") > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Users> getUsersList(UsersListParameters parameters) {
		List<Users> UsersList = (List<Users>) templUsers.query(
				"SELECT * FROM users.users;", new RowMapper<Users>() {
					public Users mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Users users = new Users();
						users.setId(rs.getInt("id"));
						users.setUser(rs.getString("user"));
						users.setPassword(rs.getString("password"));
						users.setFio(rs.getString("fio"));
						users.setPodraz(rs.getInt("podraz"));
						users.setFilial(rs.getInt("filial"));
						users.setRole(rs.getInt("role"));
						return users;
					}
				});

		return UsersList;
	}
}

