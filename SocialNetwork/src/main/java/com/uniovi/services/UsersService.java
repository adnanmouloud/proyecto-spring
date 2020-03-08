package com.uniovi.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.InvitationFriendship;
import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;


@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired  
	private HttpSession httpSession;
	
	
	public Page<User> getUsers(Pageable pageable) {
		
		User activeUser = (User) httpSession.getAttribute("currentlyUser");
		System.err.println("Email del usuario actual: "+activeUser.getEmail());
		Page<User> users = usersRepository.findAllMinusAdminAndUserAuthenticated(pageable, activeUser.getEmail());
		
		return users;
	}
	


	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}
	
	public List<User> getUsersForTest() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		User activeUser = (User) httpSession.getAttribute("currentlyUser");
		List<User> usersUpdate = eliminarAdminYUsuarioActual(users, activeUser.getEmail());
		
		return usersUpdate;
	}

	private List<User> eliminarAdminYUsuarioActual(List<User> users, String email) {
		List<User> usersUpdate = new ArrayList<User>();
		for (User user : users) {
			if(!user.getEmail().equals("admin@email.com") && !user.getEmail().equals(email) ) {
				usersUpdate.add(user);
			}
		}
		
		return usersUpdate;
		
	}



	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}
	
	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
		}
	
	

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}
	
	
	public Page<User> searchUsersByNameOrSurnameorEmail(Pageable pageable, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText = "%"+searchText+"%";
		users = usersRepository.searchUsersByNameOrSurnameorEmail(pageable, searchText);
		
		
		return users;
	}


	/**
	 * 
	 * @param currentlyUser
	 * @param content
	 * @return usuarios que han recibido una solicitud del usuario en sesión
	 */
	public Set<User> getUsersReceivedFriendshipFromCurrentlyUser(User currentlyUser, List<User> content) {

		Set<User> usersWhoCouldReceiveARequest = getUsersWhoCouldReceiveARequestByActiveUser(currentlyUser);
		Set<User> requestedUsersFromCurrentlyUser = new HashSet<User>();
		
		for (User u : usersWhoCouldReceiveARequest) {
			if (content.contains(u)) {
				requestedUsersFromCurrentlyUser.add(u);
			}

		}
		return requestedUsersFromCurrentlyUser;
	}



	private Set<User> getUsersWhoCouldReceiveARequestByActiveUser(User currentlyUser) {
		Set<User> listaUsuarios = new HashSet<User>();
		
		for (InvitationFriendship ifs : currentlyUser.getListaSolicitudesEnviadas()) {
			listaUsuarios.add(ifs.getReceptor());
		}
		return listaUsuarios;
	}
	
}

