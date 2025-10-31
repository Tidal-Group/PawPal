package com.tidal.pawpal.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.exceptions.AuthenticationFailureException;
import com.tidal.pawpal.exceptions.ExistingEmailException;
import com.tidal.pawpal.exceptions.ExistingUsernameException;
import com.tidal.pawpal.models.Persona;
import com.tidal.pawpal.models.User;
import com.tidal.pawpal.repositories.UserRepository;
import com.tidal.pawpal.services.contracts.UserServiceContract;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;

@Service
public class UserService extends UserServiceContract {

    @Autowired
    private UserRepository userRepository;

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

    @Override
    @Transactional
    public User modificaEmail (Long idUser, String newEmail, String confirmPassword) {
        if(userRepository.findByEmail(newEmail) != null) throw new ExistingEmailException();
        Map<String, String> data = new HashMap<>();
        data.put("email", newEmail);
        return modifica(idUser, data, (user) -> {
            if(!user.getPassword().equals(confirmPassword)) throw new AuthenticationFailureException();
        });
    }
    
    @Override
    @Transactional
    public User modificaUsername(Long idUser, String username) {
        if(userRepository.findByUsername(username) != null) throw new ExistingUsernameException();
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        User updatedUser = modifica(idUser, data);
        refreshSecurityContext(updatedUser.getUsername());
        return updatedUser;
    }

    @Override
    @Transactional
    public User modificaPassword(Long idUser, String currentPassword, String newPassword) {
        Map<String, String> data = new HashMap<>();
        data.put("password", newPassword);
        User updatedUser = modifica(idUser, data, (user) -> {
            if(!user.getPassword().equals(currentPassword)) throw new AuthenticationFailureException();
        });
        refreshSecurityContext(updatedUser.getUsername());
        return updatedUser;
    }    

    @Override
    @Transactional
    public User modificaDatiPersona(Long idUser, Map<String, String> data) {
        Map<String, String> whiteMap = new HashMap<>();

        List<String> personaFieldNames = Arrays
                                         .stream(Persona.class.getDeclaredFields())
                                         .map(Field::getName)
                                         .collect(Collectors.toList());

        data.entrySet().forEach((entry) -> {
            if(personaFieldNames.contains(entry.getKey())) whiteMap.put(entry.getKey(), entry.getValue());
        });

        return modifica(idUser, whiteMap);
    }

}