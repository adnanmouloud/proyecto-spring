package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {

<<<<<<< HEAD
	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE (LOWER (u.name) LIKE LOWER(?1) OR LOWER(u.lastName) LIKE LOWER(?1) OR LOWER(u.email) LIKE LOWER(?1))")
	Page<User> searchUsersByNameOrSurnameorEmail(Pageable pageable, String searchText);

	
	Page<User> findAll(Pageable pageable);
	
	@Query("SELECT u FROM User u where u.email <> 'admin@email.com' and u.email <> ?1")
	Page<User> findAllMinusAdminAndUserAuthenticated(Pageable pageable, String email);


=======
	User findByDni(String dni);

	@Query("SELECT u FROM User u WHERE (LOWER (u.name) LIKE LOWER(?1) OR LOWER(u.lastName) LIKE LOWER(?1))")
	Page<User> searchUsersByNameOrSurname(Pageable pageable, String searchText);

	Page<User> findAll(Pageable pageable);
>>>>>>> branch 'Adnan' of https://github.com/adnanmouloud/proyecto-spring.git
}
