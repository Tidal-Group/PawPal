package com.tidal.pawpal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.repositories.VeterinarioRepository;
import com.tidal.pawpal.services.contracts.VeterinarioServiceContract;

import jakarta.persistence.criteria.Predicate;
@Service
public class VeterinarioService extends VeterinarioServiceContract {

    @Autowired
    public VeterinarioRepository veterinarioRepository;

    @Override
    public List<Veterinario> cercaPerSpecie(Long idSpecie) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerSpecie'");
    }

    @Override
    public List<Veterinario> cercaPerPrestazione(Long idPrestazione) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerPrestazione'");
    }

    @Override
    public List<Veterinario> cercaPerNominativo(String nome, String cognome) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerNominativo'");
    }

    @Override
    public List<Veterinario> cercaPerIndirizzo(String indirizzo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cercaPerIndirizzo'");
    }

    // IMPLEMENT: error handling
    @Override
    public List<Veterinario> cercaConFiltri(Map<String, String> filtri) {

        Specification<Veterinario> specification = (root, query, criteriaBuilder) -> {
            
            List<Predicate> predicates = new ArrayList<>();

            if(filtri.containsKey("idPrestazione")) {
                Long idPrestazione = Long.parseLong(filtri.get("idPrestazione"));
                predicates.add(criteriaBuilder.equal(root.join("prestazioni").get("id"), idPrestazione));
            }
            
            if(filtri.containsKey("idSpecie")) {
                Long idSpecie = Long.parseLong(filtri.get("idSpecie"));
                predicates.add(criteriaBuilder.equal(root.join("specie").get("id"), idSpecie));
            }

            if(filtri.containsKey("nome")) {
                String nome = filtri.get("nome").toLowerCase();
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome + "%"));
            }

            if(filtri.containsKey("cognome")) {
                String cognome = filtri.get("cognome").toLowerCase();
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("cognome")), "%" + cognome + "%"));
            }

            // DEBUG: modificare logica -> ricerca per città?
            if(filtri.containsKey("indirizzo")) {
                String indirizzo = filtri.get("indirizzo").toLowerCase();
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("indirizzo")), "%" + indirizzo + "%"));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return veterinarioRepository.findAll(specification);
    }

}
