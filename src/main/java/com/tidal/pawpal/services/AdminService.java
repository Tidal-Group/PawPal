package com.tidal.pawpal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.services.contracts.AdminServiceContract;

@Service
public class AdminService extends AdminServiceContract {

    // remove the warning once used
    @SuppressWarnings("unused")
    @Autowired
    private ClienteService clienteService;

    // remove the warning once used
    @SuppressWarnings("unused")
    @Autowired
    private VeterinarioService veterinarioService;

    // remove the warning once used
    @SuppressWarnings("unused")
    @Autowired
    private SpecieService specieService;

    // remove the warning once used
    @SuppressWarnings("unused")
    @Autowired
    private PrestazioneService prestazioneService;
    
}
