package com.tidal.pawpal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tidal.pawpal.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByEmail(String email);
   
    User findByUsername(String username);

    User findByCodiceFiscale(String codiceFiscale);

    User findByNomeAndCognome(String nome, String cognome);

    /* @Modifying
    @Transactional
    @Query("UPDATE User u SET u.username = ?2 WHERE u.id = ?1")
    User updateUsernameUser(Long id, String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.email = ?2 WHERE u.id = ?1")
    User updateEmailUser(Long id, String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = ?2 WHERE u.id = ?1")
    User updatePasswordUser(Long id, String password);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.nome = ?2, u.cognome = ?3, u.codiceFiscale = ?4 WHERE u.id = ?1")
    User updateDatiPersona(Long id, Map <String, String> dati); */

} 
