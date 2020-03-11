package com.uniovi.services;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.uniovi.entities.InvitationFriendship;
import com.uniovi.entities.User;
import com.uniovi.repositories.InvitationFriendshipRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class InvitationFriendshipService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private InvitationFriendshipRepository invitationFriendshipRepository;
	

	public void sendRequest(User emisor, Long idReceptor) {
		
		User receptor = usersRepository.findById(idReceptor).get();
		InvitationFriendship invitation = new InvitationFriendship(emisor, receptor);
		
		invitationFriendshipRepository.save(invitation);
		
		
		
	}


	public Page<InvitationFriendship> getFriendRequests(Pageable pageable, User user) {
		return invitationFriendshipRepository.getFriendRequests(pageable, user);
	}

	/**
	 * 
	 * @param currentlyUser
	 * @param content
	 * @return usuarios que han enviado una solicitud al usuario en sesión
	 */
	public Set<User> getUserSendedRequest(User currentlyUser, List<User> content) {
		List<User> usersReceivedRequestFrom = invitationFriendshipRepository.findPendingRequestsToCurrentlyUser(currentlyUser).stream()
				.map(i -> i.getEmisor()).collect(Collectors.toList());
		
		Set<User> listUsers = new HashSet<User>();
		
		for (User user : content) {
			
			if (usersReceivedRequestFrom.contains(user)) {
				listUsers.add(user);
			}
		}
		
		return listUsers;
	}





	public Set<User> getFriends(User currentlyUser, List<User> content) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<User> getUsersWhoReceivedFriendshipInvitationFromCurrentUser(User currentUser) {
		List<User> usersList = new LinkedList<User>();

		for (InvitationFriendship i : invitationFriendshipRepository.findAllSentByUser(currentUser))
			usersList.add(i.getReceptor());

		return usersList;
	}


	public InvitationFriendship getInvitationById(Long idInvitation) {
		return invitationFriendshipRepository.findById(idInvitation).get();
	}


	public void setInvitationAcceptedIfExiststs(User currentUser, Long idNewFriend) {
		Optional<InvitationFriendship> oi = invitationFriendshipRepository.getInvitationBySenderAndReceiverID(currentUser, idNewFriend);
		
		if (oi.isPresent())
			oi.get().setAceptada(true);
	}
}
