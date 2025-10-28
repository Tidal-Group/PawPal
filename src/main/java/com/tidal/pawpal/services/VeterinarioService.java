package com.tidal.pawpal.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.tidal.pawpal.models.Prestazione;
import com.tidal.pawpal.models.Specie;

import java.util.Optional;
import java.util.stream.Collectors;

import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.repositories.VeterinarioRepository;
import com.tidal.pawpal.services.contracts.SpecieServiceContract;
import com.tidal.pawpal.services.contracts.VeterinarioServiceContract;


import jakarta.persistence.criteria.Predicate;
@Service
public class VeterinarioService extends VeterinarioServiceContract {

    private List<Long> convertStringToListOfLongs(String idsString) {
        return Arrays.stream(idsString.split(","))
                     .map(String::trim)
                     .filter(s -> !s.isEmpty())
                     .map(Long::valueOf)
                     .collect(Collectors.toList());
    }

    @Autowired
    public VeterinarioRepository veterinarioRepository;

    @Autowired
    public SpecieServiceContract specieService;

    @Override
    public Veterinario registra(Map<String, String> data) {
        Veterinario veterinario = super.registra(data);
        List<Long> listaIdSpecie = convertStringToListOfLongs(data.get("listaIdSpecie"));
        for(Long id : listaIdSpecie) {
            veterinario.getSpecieTrattate().add(specieService.cercaPerId(id));
        }
        // fare per disponibilita
        // fare per prestazione
        return veterinario;
    }
    @Override //inserire nel contract
    public List<Veterinario> cercaPerSpecie(String specie) {
        return veterinarioRepository.findByNomeSpecie(specie);
    }
    @Override 
    public List<Veterinario> cercaPerCitta(String citta) {
        return veterinarioRepository.findByCitta(citta);
    }

    public List<Veterinario> cercaPerPrestazione(Long  prestazione) {
        return veterinarioRepository.findByIdPrestazione(prestazione);
    }

    @Override
    public List<Veterinario> cercaPerIndirizzoStudio(String indirizzo) {
        return veterinarioRepository.findByIndirizzoStudio(indirizzo);
    }
  
    @Override //inserire nel contract
    public Veterinario cercaPerEmail(String email) {
        return veterinarioRepository.findByEmail(email);

    }
    @Override //inserire nel contract
    public Veterinario cercaPerTelefono(String telefono) {

        return veterinarioRepository.findByTelefono(telefono);
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
