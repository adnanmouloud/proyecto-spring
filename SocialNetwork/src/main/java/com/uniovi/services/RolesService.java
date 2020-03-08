package com.uniovi.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {
<<<<<<< HEAD
	String[] roles = { "ROLE_STANDARD", "ROLE_ADMIN" };
=======
	String[] roles = { "ROLE_STUDENT", "ROLE_PROFESSOR", "ROLE_ADMIN" };
>>>>>>> branch 'Adnan' of https://github.com/adnanmouloud/proyecto-spring.git

	public String[] getRoles() {
		return roles;
	}
}