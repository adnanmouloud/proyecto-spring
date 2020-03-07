package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;

	@Autowired
	private RolesService rolesService;
	
	@PostConstruct
	public void init() {
		User user1 = new User("adnan@gmail.es", "Adnan", "Cyberpapi");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[0]);
		
		User user2 = new User("victorgon@gmail.es", "Víctor", "Gonzalo");
		user2.setPassword("123456");
		user2.setRole(rolesService.getRoles()[0]);
		
		User user3 = new User("maria@outlook.es", "María", "Rodríguez");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[0]);
		
		User user4 = new User("fatema@outlook.es", "Fatema", "aiQueTobillos");
		user4.setPassword("123456");
		user4.setRole(rolesService.getRoles()[0]);
		
		User user5 = new User("elmoha@gmail.es", "Moha", "seco");
		user5.setPassword("123456");
		user5.setRole(rolesService.getRoles()[0]);
		
		User user6 = new User("kiko@outlook.es", "Kiko", "Saenz");
		user6.setPassword("123456");
		user6.setRole(rolesService.getRoles()[0]);
		
		User user7 = new User("kanute@outlook.es", "Kanute", "Ribas");
		user7.setPassword("123456");
		user7.setRole(rolesService.getRoles()[0]);
		
		User user8 = new User("dora@outlook.es", "Dora", "La exploradora");
		user8.setPassword("123456");
		user8.setRole(rolesService.getRoles()[0]);
		
		User user9 = new User("admin@email.com", "admin", "admin");
		user9.setPassword("123456");
		user9.setRole(rolesService.getRoles()[1]);
		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		usersService.addUser(user7);
		usersService.addUser(user8);
		usersService.addUser(user9);
	}
}
