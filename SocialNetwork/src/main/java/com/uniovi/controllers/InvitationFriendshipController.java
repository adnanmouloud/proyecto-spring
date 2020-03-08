package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.InvitationFriendship;
import com.uniovi.entities.User;
import com.uniovi.services.InvitationFriendshipService;
import com.uniovi.services.UsersService;

@Controller
public class InvitationFriendshipController {
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private InvitationFriendshipService invitationFriendshipService;
	
	
	@RequestMapping(value="/friendship/send", method = RequestMethod.POST)
	public String sendRequest(@RequestParam Long Idreceptor , Model model, Principal principal )
	{
		User emisor = usersService.getUserByEmail( principal.getName() );
		invitationFriendshipService.sendRequest(emisor, Idreceptor);
		
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/friendship/list")
	public String getRequestList(Model model, Principal principal, Pageable pageable)
	{
		User user = usersService.getUserByEmail( principal.getName() );
		Page<InvitationFriendship> invitations = new PageImpl<InvitationFriendship>(new LinkedList<InvitationFriendship>());
		
		invitations = invitationFriendshipService.getFriendRequests(pageable, user);
		
		model.addAttribute("invitationsList", invitations );
		model.addAttribute("page", invitations);
		
		return "friendship/list";
	}

}
