package com.tidal.pawpal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.User;
import com.tidal.pawpal.repositories.UserRepository;
import com.tidal.pawpal.services.contracts.UserServiceContract;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;

@Service
public class UserService extends UserServiceContract {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User cercaPerEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User cercaPerUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User cercaPerCodiceFiscale(String codiceFiscale) {
        return userRepository.findByCodiceFiscale(codiceFiscale);
    } 

    @Override
    public User cercaPerNomeECognome(String nome, String cognome) {
        return userRepository.findByNomeAndCognome(nome, cognome);
    }

    @Override
    @Transactional
    public User modificaEmail(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email già in uso"); 
            
        }
    }
    

    @Override
    @Transactional
    public User modificaUsername(String username) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username già in uso"); 
       
        }
    }

    @Override
    @Transactional
    public User modificaPassword(String password) {
        
        throw new UnsupportedOperationException("Unimplemented method 'modificaPassword'");
    }
    

    @Override
    @Transactional
    public User modificaDatiPersona(Long idUser, Map<String, String> data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modificaDatiPersona'");
    }

    // IMPLEMENT: error handling
    @Override
    public List<User> cercaConFiltri(Map<String, String> filtri) {

        Specification<User> specification = (root, query, criteriaBuilder) -> {
            
            List<Predicate> predicates = new ArrayList<>();

            if(filtri.containsKey("username")) {
                String username = filtri.get("username").toLowerCase();
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + username + "%"));
            }

            if(filtri.containsKey("email")) {
                String email = filtri.get("email").toLowerCase();
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email + "%"));
            }

            if(filtri.containsKey("nome")) {
                String nome = filtri.get("nome").toLowerCase();
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome + "%"));
            }

            if(filtri.containsKey("cognome")) {
                String cognome = filtri.get("cognome").toLowerCase();
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("cognome")), "%" + cognome + "%"));
            }

            if(filtri.containsKey("codiceFiscale")) {
                String codiceFiscale = filtri.get("codiceFiscale").toLowerCase();
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("codiceFiscale")), "%" + codiceFiscale + "%"));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return userRepository.findAll(specification);
    }

}