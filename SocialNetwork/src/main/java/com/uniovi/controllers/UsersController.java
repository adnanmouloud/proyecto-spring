package com.uniovi.controllers;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.FriendshipService;
import com.uniovi.services.InvitationFriendshipService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private InvitationFriendshipService invitationFriendshipService;

	@Autowired
	private FriendshipService friendshipService;

	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText) {

		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUsersByNameOrSurnameorEmail(pageable, searchText);
		} else {
			users = usersService.getUsers(pageable);
		}

		User currentlyUser = (User) httpSession.getAttribute("currentlyUser");

		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);

		model.addAttribute("invitationsList",
				invitationFriendshipService.getUsersWhoReceivedFriendshipInvitationFromCurrentUser(currentlyUser));
		model.addAttribute("friendsList", friendshipService.getFriendsOfCurrentUser(currentlyUser));

		return "user/list";

	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {

		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		httpSession.setAttribute("currentlyUser", activeUser);

		if (activeUser.getRole().equals(rolesService.getRoles()[1]))
			return "redirect:/user/list";

		return "home";
	}

	@RequestMapping("/user/list/update")
	public String updateList(Model model, Pageable pageable) {
		model.addAttribute("usersList", usersService.getUsers(pageable));
		model.addAttribute("invitationsList",
				invitationFriendshipService.getUsersWhoReceivedFriendshipInvitationFromCurrentUser(
						(User) httpSession.getAttribute("currentlyUser")));
		model.addAttribute("friendsList",
				friendshipService.getFriendsOfCurrentUser((User) httpSession.getAttribute("currentlyUser")));
		return "user/list :: tableUsers";
	}

	@RequestMapping("/user/manage")
	public String getListadoAdmin(Model model, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		if (!activeUser.getRole().equals(rolesService.getRoles()[1]))
			return "forbidden";

		Page<User> users = usersService.getUsers(pageable);

		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);

		return "user/manage";
	}

	@RequestMapping("/user/manage/update")
	public String updateListAdmin(Model model, Pageable pageable) {
		model.addAttribute("usersList", usersService.getUsers(pageable));
		return "user/manage :: tableUsers";
	}
}
