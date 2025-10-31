
function isPristine(inputElement) {
    return !!inputElement && (inputElement.defaultValue || '').trim() === (inputElement.value || '').trim();
}


function mostraErrore(input, message) {
    if (!input) return;
    const errorId = input.id + '_error';
    let errorMessage = document.getElementById(errorId);

    if (!errorMessage) {
        errorMessage = document.createElement('div');
        errorMessage.id = errorId;
        errorMessage.className = 'alert alert-danger mt-1';
        errorMessage.setAttribute('role', 'alert');
        errorMessage.style.fontSize = '12px';
        if (input.parentNode) input.parentNode.insertBefore(errorMessage, input.nextSibling);
    }

    errorMessage.textContent = message;
    errorMessage.style.display = 'block';
}


function nascondiErrore(input) {
    if (!input) return;
    const errorId = input.id + '_error';
    const errorMessage = document.getElementById(errorId);
    if (errorMessage) errorMessage.style.display = 'none';
}


function validaTestoNonVuoto(input, nomeCampo) {
    const value = (input.value || '').trim();
    if (value === '') {
        mostraErrore(input, `Il campo '${nomeCampo}' non può essere vuoto.`);
        return false;
    }
    nascondiErrore(input);
    return true;
}


function validaCodiceFiscale(input) {
    const value = (input.value || '').trim();
    if (value === '') {
        nascondiErrore(input);
        return true;
    }
    const pattern = /^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$/i;
    if (!pattern.test(value) || value.length > 16) {
        mostraErrore(input, 'Codice fiscale non valido (max 16 caratteri, es. RSSMRA85M01H501Z)');
        return false;
    } else {
        nascondiErrore(input);
        return true;
    }
}

function validaTelefono(input) {
    const value = (input.value || '').trim();
    if (value === '') {
        nascondiErrore(input);
        return true;
    }
    const pattern = /^\d{10}$/;
    if (!pattern.test(value)) {
        mostraErrore(input, 'Telefono non valido (deve contenere esattamente 10 cifre)');
        return false;
    } else {
        nascondiErrore(input);
        return true;
    }
}


function validaIscrizioneAlbo(input) {
    const value = (input.value || '').trim();
    if (value === '') {
        nascondiErrore(input);
        return true;
    }
    const pattern = /^\d{10}$/;
    if (!pattern.test(value)) {
        mostraErrore(input, 'Numero iscrizione all\'albo deve avere 10 cifre');
        return false;
    } else {
        nascondiErrore(input);
        return true;
    }
}

function validaIndirizzo(input) {
    const value = (input.value || '').trim();
    if (value === '') {
        nascondiErrore(input);
        return true;
    }

    const pattern = /^.+\s\d+,\s\d{5},\s.+$/; 
    if (!pattern.test(value)) {
        mostraErrore(input, 'Indirizzo non valido. Formato: Nome Via n.civico, CAP, Città');
        return false;
    } else {
        nascondiErrore(input);
        return true;
    }
}

document.addEventListener('DOMContentLoaded', () => {


    const formModificaDati = document.querySelector('#modifica_dati form');
    if (formModificaDati) {
    
        const nome = formModificaDati.querySelector('#nome');
        const cognome = formModificaDati.querySelector('#cognome');
        const cf = formModificaDati.querySelector('#codiceFiscale');
        const telefono = formModificaDati.querySelector('#telefono');
        const indirizzo = formModificaDati.querySelector('#indirizzo'); 
        const iscrizione = formModificaDati.querySelector('#iscrizione_albo'); 
        const submitBtn = formModificaDati.querySelector('button[type="submit"]');

       
        if (nome) nome.addEventListener('input', () => validaTestoNonVuoto(nome, 'Nome'));
        if (cognome) cognome.addEventListener('input', () => validaTestoNonVuoto(cognome, 'Cognome'));
        if (cf) cf.addEventListener('input', () => validaCodiceFiscale(cf));
        if (telefono) telefono.addEventListener('input', () => validaTelefono(telefono));
        if (indirizzo) indirizzo.addEventListener('input', () => validaIndirizzo(indirizzo));
        if (iscrizione) iscrizione.addEventListener('input', () => validaIscrizioneAlbo(iscrizione));

        
        formModificaDati.addEventListener('submit', (e) => {
            let isValid = true;
            
            
            if (nome && !validaTestoNonVuoto(nome, 'Nome')) isValid = false;
            if (cognome && !validaTestoNonVuoto(cognome, 'Cognome')) isValid = false;
            
            
            if (cf) {
                if (!validaTestoNonVuoto(cf, 'Codice Fiscale')) isValid = false;
                else if (!validaCodiceFiscale(cf)) isValid = false;
            }
            if (telefono) {
                if (!validaTestoNonVuoto(telefono, 'Telefono')) isValid = false;
                else if (!validaTelefono(telefono)) isValid = false;
            }
            
           
            if (indirizzo && !validaIndirizzo(indirizzo)) isValid = false;
            if (iscrizione && !validaIscrizioneAlbo(iscrizione)) isValid = false;
            
            if (!isValid) {
                e.preventDefault(); 
            } else {
                 if(submitBtn) nascondiErrore(submitBtn);
            }
        });
    }

});