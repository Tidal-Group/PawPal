package com.tidal.pawpal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidal.pawpal.services.contracts.AdminServiceContract;
import com.tidal.pawpal.services.contracts.ClienteServiceContract;
import com.tidal.pawpal.services.contracts.PrestazioneServiceContract;
import com.tidal.pawpal.services.contracts.SpecieServiceContract;
import com.tidal.pawpal.services.contracts.VeterinarioServiceContract;

@Service
public class AdminService extends AdminServiceContract {

    // remove the warning once used
    @SuppressWarnings("unused")
    @Autowired
    private ClienteServiceContract clienteService;

    // remove the warning once used
    @SuppressWarnings("unused")
    @Autowired
    private VeterinarioServiceContract veterinarioService;

    // remove the warning once used
    @SuppressWarnings("unused")
    @Autowired
    private SpecieServiceContract specieService;

    // remove the warning once used
    @SuppressWarnings("unused")
    @Autowired
    private PrestazioneServiceContract prestazioneService;
    
}
