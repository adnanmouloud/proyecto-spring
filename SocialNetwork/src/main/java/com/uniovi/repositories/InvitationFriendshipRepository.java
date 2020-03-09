package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.InvitationFriendship;
import com.uniovi.entities.User;

@Repository
public interface InvitationFriendshipRepository extends CrudRepository<InvitationFriendship, Long> {

	@Query("SELECT p from InvitationFriendship p WHERE p.receptor = ?1 and p.aceptada = false ORDER BY p.id ASC")
	Page<InvitationFriendship> getFriendRequests(Pageable pageable, User user);

	@Query("SELECT p FROM InvitationFriendship p WHERE p.receptor = ?1 and p.aceptada = false ORDER BY p.id ASC")
	Streamable<InvitationFriendship> findPendingRequestsToCurrentlyUser(User currentlyUser);

	@Query("SELECT p FROM InvitationFriendship p WHERE p.emisor = ?1")
	List<InvitationFriendship> findAllSentByUser(User currentUser);

}
