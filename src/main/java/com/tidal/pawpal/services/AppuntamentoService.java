package com.tidal.pawpal.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.dto.AppuntamentoDto;
import com.tidal.pawpal.events.UserDeletionEvent;
import com.tidal.pawpal.models.Appuntamento;
import com.tidal.pawpal.models.Cliente;
import com.tidal.pawpal.models.Veterinario;
import com.tidal.pawpal.repositories.AppuntamentoRepository;
import com.tidal.pawpal.services.contracts.AppuntamentoServiceContract;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class AppuntamentoService extends AppuntamentoServiceContract {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;

    @Autowired
    private VeterinarioService veterinarioService;

    @Autowired
    private ClienteService clienteService;

    @Override
    public Appuntamento registra(Map<String, String> data) {
        return super.registra(data, (appuntamento) -> {
            Veterinario veterinario = veterinarioService.cercaPerId(Long.parseLong(data.get("idVeterinario")));
            appuntamento.setVeterinario(veterinario);
            Cliente cliente = clienteService.cercaPerId(Long.parseLong(data.get("idCliente")));
            appuntamento.setCliente(cliente);
        });
    }

    @Override
    public void elimina(Long id) {
        super.elimina(id, (appuntamento) -> {
            appuntamento.detachFromParents();
        });
    }

    @Override
    public Integer gestisciEliminazioneCliente(UserDeletionEvent event) {
        return appuntamentoRepository.clearClienteForeignKey(event.getUserId());
    }

    @Override
    public Integer gestisciEliminazioneVeterinario(UserDeletionEvent event) {
        return appuntamentoRepository.clearVeterinarioForeignKey(event.getUserId());
    }

    @Override
    public List<AppuntamentoDto> cercaPerVeterinario(Long idVeterinario) {
        return appuntamentoRepository.findByVeterinario(idVeterinario);
    }

    @Override
    public List<AppuntamentoDto> cercaPerCliente(Long idCliente) {
        return appuntamentoRepository.findByCliente(idCliente);
    }

    @Override
    public List<AppuntamentoDto> cercaPerVeterinarioConFiltri(Long idVeterinario, Map<String, Object> filtri) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AppuntamentoDto> query = criteriaBuilder.createQuery(AppuntamentoDto.class);
        Root<Appuntamento> root = query.from(Appuntamento.class);

        Join<Appuntamento, Veterinario> joinVeterinario = root.join("veterinario");
        Join<Appuntamento, Cliente> joinCliente = root.join("cliente");

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(joinVeterinario.get("id"), idVeterinario));

        if(filtri.containsKey("nominativo")) {
            String nominativo = (String) filtri.get("nominativo");
            Expression<String> nominativoExpression = criteriaBuilder.concat(
                criteriaBuilder.concat(
                    criteriaBuilder.lower(
                        joinCliente.get("nome")
                    ),
                    criteriaBuilder.literal(" ")
                ),
                criteriaBuilder.lower(
                    joinCliente.get("cognome")
                )
            );

            predicates.add(criteriaBuilder.like(nominativoExpression, "%" + nominativo.toLowerCase() + "%"));
        }
        
        if(filtri.containsKey("data")) {
            LocalDate data = (LocalDate) filtri.get("data");
            Expression<LocalDate> dataExpression = criteriaBuilder.function(
                "DATE",
                LocalDate.class,
                root.get("dataOra")
            );
            predicates.add(criteriaBuilder.equal(dataExpression, data));
        }

        if(!predicates.isEmpty()) query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        query.select(
                criteriaBuilder.construct(
                    AppuntamentoDto.class,
                    root.get("id"),
                    joinCliente.get("id"),
                    root.get("dataOra"),
                    root.get("note"),
                    joinCliente.get("nome"),
                    joinCliente.get("cognome"),
                    joinCliente.get("telefono"),
                    criteriaBuilder.nullLiteral(String.class)
                )
            );

        return entityManager.createQuery(query).getResultList();
    }

    // DEBUG: dovrebbe essere una left join
    @Override
    public List<AppuntamentoDto> cercaPerClienteConFiltri(Long idCliente, Map<String, Object> filtri) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AppuntamentoDto> query = criteriaBuilder.createQuery(AppuntamentoDto.class);
        Root<Appuntamento> root = query.from(Appuntamento.class);

        Join<Appuntamento, Veterinario> joinVeterinario = root.join("veterinario");
        Join<Appuntamento, Cliente> joinCliente = root.join("cliente");

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(joinCliente.get("id"), idCliente));

        if(filtri.containsKey("nominativo")) {
            String nominativo = (String) filtri.get("nominativo");
            Expression<String> nominativoExpression = criteriaBuilder.concat(
                criteriaBuilder.concat(
                    criteriaBuilder.lower(
                        joinVeterinario.get("nome")
                    ),
                    criteriaBuilder.literal(" ")
                ),
                criteriaBuilder.lower(
                    joinVeterinario.get("cognome")
                )
            );

            predicates.add(criteriaBuilder.like(nominativoExpression, "%" + nominativo.toLowerCase() + "%"));
        }
        
        if(filtri.containsKey("data")) {
            LocalDate data = (LocalDate) filtri.get("data");
            Expression<LocalDate> dataExpression = criteriaBuilder.function(
                "DATE",
                LocalDate.class,
                root.get("dataOra")
            );
            predicates.add(criteriaBuilder.equal(dataExpression, data));
        }

        if(!predicates.isEmpty()) query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        query.select(
                criteriaBuilder.construct(
                    AppuntamentoDto.class,
                    root.get("id"),
                    joinVeterinario.get("id"),
                    root.get("dataOra"),
                    root.get("note"),
                    joinVeterinario.get("nome"),
                    joinVeterinario.get("cognome"),
                    joinVeterinario.get("telefono"),
                    joinVeterinario.get("indirizzoStudio")
                )
            );

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Long contaAppuntamentiPerCliente(Long idCliente) {
        return appuntamentoRepository.countByClienteId(idCliente);
    }

    @Override
    public Optional<Appuntamento> cercaProssimoAppuntamento(Long idCliente) {
        return appuntamentoRepository.findFirstByClienteIdAndDataOraAfterOrderByDataOraAsc(idCliente, LocalDateTime.now());
    }

    @Override
    public List<AppuntamentoDto> cercaAppuntamentiDiOggi(Long veterinarioId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
        List<AppuntamentoDto> appuntamenti = appuntamentoRepository.findAppuntamentiOdierni(veterinarioId, startOfDay, endOfDay);
        return appuntamenti;
    }

    public Long contaAppuntamentiPerVeterinario(Long veterinarioId) {
        return appuntamentoRepository.countByVeterinarioId(veterinarioId);
    }

}
