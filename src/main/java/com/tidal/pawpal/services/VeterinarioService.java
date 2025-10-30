// package com.tidal.pawpal.services;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.jpa.domain.Specification;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.stereotype.Service;

// import com.tidal.pawpal.models.Veterinario;
// import com.tidal.pawpal.repositories.VeterinarioRepository;
// import com.tidal.pawpal.services.contracts.VeterinarioServiceContract;

// import jakarta.persistence.criteria.Predicate;
// import jakarta.transaction.Transactional;

// @Service
// public class VeterinarioService extends VeterinarioServiceContract {

//     @Autowired
//     private VeterinarioRepository veterinarioRepository;

//     // @Autowired
//     // private AppuntamentoServiceContract appuntamentoService;

//     // @Autowired
//     // private RecensioneServiceContract recensioneService;

//     @Autowired
//     private SpecieService specieService;

//     @Autowired
//     private PrestazioneService prestazioneService;

//     @Transactional
//     @PreAuthorize("permitAll")
//     public Veterinario registra(List<Long> listaIdSpecie, List<Long> listaIdPrestazione, Map<String, String> data) {
//         return super.registra(data, (veterinario) -> {
//             for(Long id : listaIdSpecie)
//                 veterinario.getSpecieTrattate().add(specieService.cercaPerId(id));
//             for(Long id : listaIdPrestazione)
//                 veterinario.getPrestazioniOfferte().add(prestazioneService.cercaPerId(id));
//         });
//     }

//     // DEBUG: circular referencing
//     // @Override
//     // public void elimina(Long id) {
//     //     super.elimina(id, (veterinario) -> {
//     //         List<Appuntamento> listaAppuntamenti = appuntamentoService.cercaPerVeterinario(id);
//     //         listaAppuntamenti.forEach((appuntamento) -> appuntamento.setVeterinario(null));
//     //         appuntamentoService.getRepository().saveAll(listaAppuntamenti);
//     //         List<Recensione> listaRecensioni = recensioneService.cercaPerVeterinario(id);
//     //         listaRecensioni.forEach((recensione) -> recensione.setVeterinario(null));
//     //         recensioneService.getRepository().saveAll(listaRecensioni);
//     //     });
//     // }

//     @Override
//     public List<Veterinario> cercaPerSpecie(String nomeSpecie) {
//         return veterinarioRepository.findByNomeSpecie(nomeSpecie);
//     }

//     @Override
//     public List<Veterinario> cercaPerPrestazione(Long idPrestazione) {
//         return veterinarioRepository.findByIdPrestazione(idPrestazione);
//     }

//     @Override
//     public List<Veterinario> cercaPerIndirizzoStudio(String indirizzo) {
//         return veterinarioRepository.findByIndirizzoStudio(indirizzo);
//     }

//     @Override 
//     public List<Veterinario> cercaPerCitta(String citta) {
//         return veterinarioRepository.findByCitta(citta);
//     }
  
//     @Override
//     public Veterinario cercaPerEmail(String email) {
//         return veterinarioRepository.findByEmail(email);

//     }
//     @Override
//     public Veterinario cercaPerTelefono(String telefono) {
//         return veterinarioRepository.findByTelefono(telefono);
//     }

//     // IMPLEMENT: error handling
//     @Override
//     public List<Veterinario> cercaConFiltri(Map<String, String> filtri) {

//         Specification<Veterinario> specification = (root, query, criteriaBuilder) -> {
            
//             List<Predicate> predicates = new ArrayList<>();

//             if(filtri.containsKey("idPrestazione")) {
//                 Long idPrestazione = Long.parseLong(filtri.get("idPrestazione"));
//                 predicates.add(criteriaBuilder.equal(root.join("prestazioni").get("id"), idPrestazione));
//             }
            
//             if(filtri.containsKey("idSpecie")) {
//                 Long idSpecie = Long.parseLong(filtri.get("idSpecie"));
//                 predicates.add(criteriaBuilder.equal(root.join("specie").get("id"), idSpecie));
//             }

//             if(filtri.containsKey("nome")) {
//                 String nome = filtri.get("nome").toLowerCase();
//                 predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome + "%"));
//             }

//             if(filtri.containsKey("cognome")) {
//                 String cognome = filtri.get("cognome").toLowerCase();
//                 predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("cognome")), "%" + cognome + "%"));
//             }

//             if(filtri.containsKey("citta")) {
//                 String citta = filtri.get("citta").toLowerCase();
//                 predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("indirizzo")), "%" + citta + "%"));
//             }
            
//             return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//         };

//         return veterinarioRepository.findAll(specification);


//     }

    

// }

package com.tidal.pawpal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // Necessario per la mappatura, se usassi Object[]

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest; // Aggiunto per gestire il LIMIT
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.repositories.VeterinarioRepository;
import com.tidal.pawpal.services.contracts.VeterinarioServiceContract;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;

@Service
public class VeterinarioService extends VeterinarioServiceContract {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    // ... (altri servizi iniettati)
    @Autowired
    private SpecieService specieService;

    @Autowired
    private PrestazioneService prestazioneService;

    // --- METODI NUOVI PER LE QUERY RICHIESTE ---

    /**
     * Recupera la lista delle specializzazioni più popolari in base al numero di veterinari.
     * Richiama la query NATVA con CTE che restituisce List<String>.
     * * @return List<String> contenente i nomi delle specializzazioni ordinati per popolarità.
     */
    public List<String> getMostPopularSpecializationNames() {
        return veterinarioRepository.findPopularSpecializationNamesNative();
    }

    /**
     * Recupera i veterinari in evidenza, ordinati per rating medio decrescente.
     * Utilizza PageRequest per limitare i risultati ai "primi N".
     * * @param limit Il numero massimo di veterinari da restituire (es. 5 per i "Top 5").
     * @return List<Veterinario> ordinata e limitata.
     */
    public List<Veterinario> getTopRatedVeterinarians() {
        
       // Chiama la query JQL che usa il raggruppamento e l'ordinamento.
        return veterinarioRepository.findTopVeterinariansByAverageRating();
    }

    // --- METODI ESISTENTI ---

    @Transactional
    @PreAuthorize("permitAll")
    public Veterinario registra(List<Long> listaIdSpecie, List<Long> listaIdPrestazione, Map<String, String> data) {
        return super.registra(data, (veterinario) -> {
            for(Long id : listaIdSpecie)
                veterinario.getSpecieTrattate().add(specieService.cercaPerId(id));
            for(Long id : listaIdPrestazione)
                veterinario.getPrestazioniOfferte().add(prestazioneService.cercaPerId(id));
        });
    }

    @Override
    public List<Veterinario> cercaPerSpecie(String nomeSpecie) {
        return veterinarioRepository.findByNomeSpecie(nomeSpecie);
    }
    // ... (altri metodi di ricerca esistenti)

    @Override
    public List<Veterinario> cercaPerPrestazione(Long idPrestazione) {
        return veterinarioRepository.findByIdPrestazione(idPrestazione);
    }

    @Override
    public List<Veterinario> cercaPerIndirizzoStudio(String indirizzo) {
        return veterinarioRepository.findByIndirizzoStudio(indirizzo);
    }

    @Override 
    public List<Veterinario> cercaPerCitta(String citta) {
        return veterinarioRepository.findByCitta(citta);
    }
 
    @Override
    public Veterinario cercaPerEmail(String email) {
        return veterinarioRepository.findByEmail(email);

    }
    @Override
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

            if(filtri.containsKey("citta")) {
                String citta = filtri.get("citta").toLowerCase();
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("indirizzo")), "%" + citta + "%"));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return veterinarioRepository.findAll(specification);


    }

}