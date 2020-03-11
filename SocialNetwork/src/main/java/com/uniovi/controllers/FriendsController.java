package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.User;
import com.uniovi.services.FriendshipService;
import com.uniovi.services.UsersService;

@Controller
public class FriendsController {
	
	@Autowired
	private FriendshipService friendshipService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private HttpSession httpSession;
	
	@RequestMapping("/friend/list")
	public String getListado(Model model, Principal principal, Pageable pageable) {
		User user = usersService.getUserByEmail( principal.getName() );
		
		Page<User> friends = new PageImpl<User>(new LinkedList<User>());
		friends = friendshipService.getFriendsOfUser(pageable, user);

		model.addAttribute("friendsList", friends.getContent());
		model.addAttribute("page", friends);

		return "friend/list";

	}
	
	@RequestMapping("/friend/list/update")
	public String updateList(Model model, Pageable pageable) {
		User user = (User) httpSession.getAttribute("currentlyUser");
		model.addAttribute("friendsList", friendshipService.getFriendsOfUser(pageable, user));
		return "user/list :: tableUsers";
	}
}
