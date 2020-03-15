package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.Post;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;

	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private FriendshipService friendshipService;
	
	@Autowired
	private PostsService postsService;
	
	
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
		
		Friendship fr19 = new Friendship(user1, user9);
		Friendship fr91 = new Friendship(user9, user1);
		
		Friendship fr29 = new Friendship(user2, user9);
		Friendship fr92 = new Friendship(user9, user2);
		
		Friendship fr39 = new Friendship(user3, user9);
		Friendship fr93 = new Friendship(user9, user3);
		
		Friendship fr49 = new Friendship(user4, user9);
		Friendship fr94 = new Friendship(user9, user4);
		
		Friendship fr59 = new Friendship(user5, user9);
		Friendship fr95 = new Friendship(user9, user5);
		
		Friendship fr69 = new Friendship(user6, user9);
		Friendship fr96 = new Friendship(user9, user6);
		
		friendshipService.addFriendship(fr19);
		friendshipService.addFriendship(fr91);
		
		friendshipService.addFriendship(fr29);
		friendshipService.addFriendship(fr92);
		
		friendshipService.addFriendship(fr39);
		friendshipService.addFriendship(fr93);
		
		friendshipService.addFriendship(fr49);
		friendshipService.addFriendship(fr94);
		
		friendshipService.addFriendship(fr59);
		friendshipService.addFriendship(fr95);
		
		friendshipService.addFriendship(fr69);
		friendshipService.addFriendship(fr96);
		
		//Para pruebas del opcional 14
		Post post1 = new Post("Coronavirus", "Sálvese quien pueda", user2);
		Post post2 = new Post("Papito", "keloke", user2);
		
		postsService.addPost(post1);
		postsService.addPost(post2);
	}
}
