package com.tidal.pawpal.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import com.tidal.pawpal.repositories.UserRepository;       
import com.tidal.pawpal.models.User;
import com.tidal.pawpal.services.contracts.UserServiceContract;
@Service
public class UserService extends UserServiceContract {

    private final UserRepository userRepository;

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

}