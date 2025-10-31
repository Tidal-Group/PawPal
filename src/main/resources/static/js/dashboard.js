/*
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


function validaEmail(input) {
    const value = (input.value || '').trim();
    if (value === '') {
        mostraErrore(input, 'L\'email non può essere vuota.');
        return false;
    }

    const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!pattern.test(value)) {
        mostraErrore(input, 'Email non valida. Assicurati che l\'indirizzo sia corretto (es. nome@dominio.it)');
        return false;
    } else {
        nascondiErrore(input);
        return true;
    }
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

function validaPassword(input) {
    const value = (input.value || '').trim();
    if (value === '') {
        mostraErrore(input, 'La password non può essere vuota.');
        return false;
    }

    const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,22}$/;
    if (!pattern.test(value)) {
        mostraErrore(input, 'Password non valida. Deve avere 8-22 caratteri, almeno una minuscola, una maiuscola, un numero e un carattere speciale.');
        return false;
    } else {
        nascondiErrore(input);
        return true;
    }
}

function verificaCorrispondenzaGenerica(inputElement, confirmInput, errorMessageText) {
    if (!inputElement || !confirmInput) return;

    const validateMatch = () => {
        const val1 = (inputElement.value || '').trim();
        const val2 = (confirmInput.value || '').trim();
        
        if (val2 !== '' && val1 !== val2) {
            mostraErrore(confirmInput, errorMessageText);
        } else {
            nascondiErrore(confirmInput);
        }
    };

    inputElement.addEventListener('input', validateMatch);
    confirmInput.addEventListener('input', validateMatch);

    const parentForm = inputElement.closest('form');
    if (parentForm) {
        parentForm.addEventListener('submit', (e) => {
            const val1 = (inputElement.value || '').trim();
            const val2 = (confirmInput.value || '').trim();
            if (val1 !== val2) {
                e.preventDefault();
                mostraErrore(confirmInput, errorMessageText);
                confirmInput.focus();
            }
        });
    }
}


document.addEventListener('DOMContentLoaded', () => {

    const formEmail = document.querySelector('#modal-email form');
    if (formEmail) {

        const currentEmail = formEmail.querySelector('#current_email');
        const newEmail = formEmail.querySelector('#new_email');
        const confirmEmail = formEmail.querySelector('#confirm_email');
        
        const newEmailInput = newEmail || formEmail.querySelector('#email');
        const confirmEmailInput = confirmEmail || formEmail.querySelector('#confirm_email');

        if (newEmailInput) {
            
            newEmailInput.addEventListener('input', () => validaEmail(newEmailInput));
        }

        formEmail.addEventListener('submit', (e) => {
            let isValid = true;
            
            if (currentEmail && !validaTestoNonVuoto(currentEmail, 'Email corrente')) isValid = false;
            
            if (newEmailInput && !validaEmail(newEmailInput)) isValid = false;

            if (confirmEmailInput && !validaTestoNonVuoto(confirmEmailInput, 'Conferma')) isValid = false; 
            
            if (!isValid) {
                e.preventDefault();
            }
        });
    }

    const formPassword = document.querySelector('#modal-password form');
    if (formPassword) {
        const currentPass = formPassword.querySelector('#current_password');
        const newPass = formPassword.querySelector('#new_password');

        const confirmPass = formPassword.querySelector('#confirm_password');
        
        if (newPass && confirmPass) {

            if (newPass) newPass.addEventListener('input', () => validaPassword(newPass));

            verificaCorrispondenzaGenerica(newPass, confirmPass, 'Le password non corrispondono!');

            formPassword.addEventListener('submit', (e) => {
                let isValid = true;

                if (currentPass && !validaTestoNonVuoto(currentPass, 'Password corrente')) isValid = false;

                if (newPass && !validaPassword(newPass)) isValid = false;

                if (confirmPass && !validaTestoNonVuoto(confirmPass, 'Conferma Password')) isValid = false;
                
                const val1 = (newPass.value || '').trim();
                const val2 = (confirmPass.value || '').trim();
                if (val1 !== val2) {
                    mostraErrore(confirmPass, 'Le password non corrispondono!');
                    isValid = false;
                }
                
                if (!isValid) {
                    e.preventDefault();
                }
            });
        }
    }

    const cleanupModal = (modalElement) => {
        if (!modalElement) return;

        modalElement.addEventListener('hidden.bs.modal', () => {
            const form = modalElement.querySelector('form');
            if (!form) return;

            const fields = form.querySelectorAll('input');
            fields.forEach(field => {
                field.value = field.defaultValue || ''; 
                const error = document.getElementById(field.id + '_error');
                if (error) error.style.display = 'none';
            });
            
            const submitBtn = form.querySelector('button[type="submit"]');
            if(submitBtn) nascondiErrore(submitBtn); 
        });
    }

    cleanupModal(document.getElementById('modal-password'));
    cleanupModal(document.getElementById('modal-email'));

    const formModificaDati = document.querySelector('#modifica_dati form');
    if (formModificaDati) {

        const nome = formModificaDati.querySelector('#nome');
        const cognome = formModificaDati.querySelector('#cognome');
        const cf = formModificaDati.querySelector('#codiceFiscale');
        const telefono = formModificaDati.querySelector('#telefono');
        const indirizzo = formModificaDati.querySelector('#indirizzo');
        const iscrizione = formModificaDati.querySelector('#iscrizione_albo');
        
        // Nuovi campi per Specie e Prestazioni (assunti come input)
        const specieTrattate = formModificaDati.querySelector('#specieTrattate');
        const prestazioniOfferte = formModificaDati.querySelector('#prestazioniOfferte');

        const submitBtn = formModificaDati.querySelector('button[type="submit"]');


        if (nome) nome.addEventListener('input', () => validaTestoNonVuoto(nome, 'Nome'));
        if (cognome) cognome.addEventListener('input', () => validaTestoNonVuoto(cognome, 'Cognome'));
        if (cf) cf.addEventListener('input', () => validaCodiceFiscale(cf));
        if (telefono) telefono.addEventListener('input', () => validaTelefono(telefono));
        if (indirizzo) indirizzo.addEventListener('input', () => validaIndirizzo(indirizzo));
        if (iscrizione) iscrizione.addEventListener('input', () => validaIscrizioneAlbo(iscrizione));

        // Aggiunta gestione real-time per i nuovi campi (assumendo che richiedano un valore non vuoto)
        if (specieTrattate) specieTrattate.addEventListener('input', () => validaTestoNonVuoto(specieTrattate, 'Specie Trattate'));
        if (prestazioniOfferte) prestazioniOfferte.addEventListener('input', () => validaTestoNonVuoto(prestazioniOfferte, 'Prestazioni Offerte'));


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
            
            // Aggiunta validazione per i nuovi campi al momento del submit
            if (specieTrattate && !validaTestoNonVuoto(specieTrattate, 'Specie Trattate')) isValid = false;
            if (prestazioniOfferte && !validaTestoNonVuoto(prestazioniOfferte, 'Prestazioni Offerte')) isValid = false;


            if (!isValid) {
                e.preventDefault();
            } else {
                if (submitBtn) nascondiErrore(submitBtn);
            }
        });
    }

});
*/

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


function validaEmail(input) {
    const value = (input.value || '').trim();
    if (value === '') {
        mostraErrore(input, 'L\'email non può essere vuota.');
        return false;
    }

    const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!pattern.test(value)) {
        mostraErrore(input, 'Email non valida. Assicurati che l\'indirizzo sia corretto (es. nome@dominio.it)');
        return false;
    } else {
        nascondiErrore(input);
        return true;
    }
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
        mostraErrore(input, 'Il numero di iscrizione all\'albo non può essere vuoto.');
        return false;
    }
    const pattern = /^\d{10}$/;
    if (!pattern.test(value)) {
        mostraErrore(input, 'Numero iscrizione all\'albo deve avere esattamente 10 cifre');
        return false;
    } else {
        nascondiErrore(input);
        return true;
    }
}

function validaIndirizzo(input) {
    const value = (input.value || '').trim();
    if (value === '') {
        mostraErrore(input, 'L\'indirizzo non può essere vuoto.');
        return false;
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

function validaPassword(input) {
    const value = (input.value || '').trim();
    if (value === '') {
        mostraErrore(input, 'La password non può essere vuota.');
        return false;
    }

    const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,22}$/;
    if (!pattern.test(value)) {
        mostraErrore(input, 'Password non valida. Deve avere 8-22 caratteri, almeno una minuscola, una maiuscola, un numero e un carattere speciale.');
        return false;
    } else {
        nascondiErrore(input);
        return true;
    }
}

function verificaCorrispondenzaGenerica(inputElement, confirmInput, errorMessageText) {
    if (!inputElement || !confirmInput) return;

    const validateMatch = () => {
        const val1 = (inputElement.value || '').trim();
        const val2 = (confirmInput.value || '').trim();
        
        if (val2 !== '' && val1 !== val2) {
            mostraErrore(confirmInput, errorMessageText);
        } else {
            nascondiErrore(confirmInput);
        }
    };

    inputElement.addEventListener('input', validateMatch);
    confirmInput.addEventListener('input', validateMatch);

    const parentForm = inputElement.closest('form');
    if (parentForm) {
        parentForm.addEventListener('submit', (e) => {
            const val1 = (inputElement.value || '').trim();
            const val2 = (confirmInput.value || '').trim();
            if (val1 !== val2) {
                e.preventDefault();
                mostraErrore(confirmInput, errorMessageText);
                confirmInput.focus();
            }
        });
    }
}


document.addEventListener('DOMContentLoaded', () => {

    const formEmail = document.querySelector('#modal-email form');
    if (formEmail) {

        const currentEmail = formEmail.querySelector('#current_email');
        const newEmail = formEmail.querySelector('#new_email');
        const confirmEmail = formEmail.querySelector('#confirm_email');
        
        const newEmailInput = newEmail || formEmail.querySelector('#email');
        const confirmEmailInput = confirmEmail || formEmail.querySelector('#confirm_email');

        if (newEmailInput) {
            
            newEmailInput.addEventListener('input', () => validaEmail(newEmailInput));
        }

        formEmail.addEventListener('submit', (e) => {
            let isValid = true;
            
            if (currentEmail && !validaTestoNonVuoto(currentEmail, 'Email corrente')) isValid = false;
            
            if (newEmailInput && !validaEmail(newEmailInput)) isValid = false;

            if (confirmEmailInput && !validaTestoNonVuoto(confirmEmailInput, 'Conferma')) isValid = false; 
            
            if (!isValid) {
                e.preventDefault();
            }
        });
    }

    const formPassword = document.querySelector('#modal-password form');
    if (formPassword) {
        const currentPass = formPassword.querySelector('#current_password');
        const newPass = formPassword.querySelector('#new_password');

        const confirmPass = formPassword.querySelector('#confirm_password');
        
        if (newPass && confirmPass) {

            if (newPass) newPass.addEventListener('input', () => validaPassword(newPass));

            verificaCorrispondenzaGenerica(newPass, confirmPass, 'Le password non corrispondono!');

            formPassword.addEventListener('submit', (e) => {
                let isValid = true;

                if (currentPass && !validaTestoNonVuoto(currentPass, 'Password corrente')) isValid = false;

                if (newPass && !validaPassword(newPass)) isValid = false;

                if (confirmPass && !validaTestoNonVuoto(confirmPass, 'Conferma Password')) isValid = false;
                
                const val1 = (newPass.value || '').trim();
                const val2 = (confirmPass.value || '').trim();
                if (val1 !== val2) {
                    mostraErrore(confirmPass, 'Le password non corrispondono!');
                    isValid = false;
                }
                
                if (!isValid) {
                    e.preventDefault();
                }
            });
        }
    }

    const cleanupModal = (modalElement) => {
        if (!modalElement) return;

        modalElement.addEventListener('hidden.bs.modal', () => {
            const form = modalElement.querySelector('form');
            if (!form) return;

            const fields = form.querySelectorAll('input');
            fields.forEach(field => {
                field.value = field.defaultValue || ''; 
                const error = document.getElementById(field.id + '_error');
                if (error) error.style.display = 'none';
            });
            
            const submitBtn = form.querySelector('button[type="submit"]');
            if(submitBtn) nascondiErrore(submitBtn); 
        });
    }

    cleanupModal(document.getElementById('modal-password'));
    cleanupModal(document.getElementById('modal-email'));

    const formModificaDati = document.querySelector('#modifica_dati form');
    if (formModificaDati) {

        const nome = formModificaDati.querySelector('#nome');
        const cognome = formModificaDati.querySelector('#cognome');
        const cf = formModificaDati.querySelector('#codiceFiscale');
        const telefono = formModificaDati.querySelector('#telefono');
        const indirizzo = formModificaDati.querySelector('#indirizzo');
        const iscrizione = formModificaDati.querySelector('#iscrizione_albo');
        
        // Campi aggiuntivi che il cliente vuole rendere obbligatori
        const specializzazione = formModificaDati.querySelector('#specializzazione'); // ⚠️ Nuovo campo
        const disponibilita = formModificaDati.querySelector('#disponibilita');       // ⚠️ Nuovo campo
        const descrizione = formModificaDati.querySelector('#descrizione');           // ⚠️ Nuovo campo

        // Campi presenti nel codice originale che assumiamo siano rimasti
        const specieTrattate = formModificaDati.querySelector('#specieTrattate');
        const prestazioniOfferte = formModificaDati.querySelector('#prestazioniOfferte');

        const submitBtn = formModificaDati.querySelector('button[type="submit"]');


        // Event Listeners (Validazione in tempo reale)
        if (nome) nome.addEventListener('input', () => validaTestoNonVuoto(nome, 'Nome'));
        if (cognome) cognome.addEventListener('input', () => validaTestoNonVuoto(cognome, 'Cognome'));
        if (cf) cf.addEventListener('input', () => validaCodiceFiscale(cf));
        if (telefono) telefono.addEventListener('input', () => validaTelefono(telefono));
        
        // ⚠️ Ora l'indirizzo è obbligatorio
        if (indirizzo) indirizzo.addEventListener('input', () => validaIndirizzo(indirizzo));
        // ⚠️ Ora l'iscrizione all'albo è obbligatoria
        if (iscrizione) iscrizione.addEventListener('input', () => validaIscrizioneAlbo(iscrizione));
        
        // ⚠️ Nuovi campi resi obbligatori (usando validaTestoNonVuoto)
        if (specializzazione) specializzazione.addEventListener('input', () => validaTestoNonVuoto(specializzazione, 'Specializzazione'));
        if (disponibilita) disponibilita.addEventListener('input', () => validaTestoNonVuoto(disponibilita, 'Disponibilità'));
        if (descrizione) descrizione.addEventListener('input', () => validaTestoNonVuoto(descrizione, 'Descrizione'));
        
        // Campi del codice originale, mantenuti come obbligatori
        if (specieTrattate) specieTrattate.addEventListener('input', () => validaTestoNonVuoto(specieTrattate, 'Specie Trattate'));
        if (prestazioniOfferte) prestazioniOfferte.addEventListener('input', () => validaTestoNonVuoto(prestazioniOfferte, 'Prestazioni Offerte'));


        formModificaDati.addEventListener('submit', (e) => {
            let isValid = true;


            if (nome && !validaTestoNonVuoto(nome, 'Nome')) isValid = false;
            if (cognome && !validaTestoNonVuoto(cognome, 'Cognome')) isValid = false;


            if (cf) {
                // Rendo il CF obbligatorio
                if (!validaTestoNonVuoto(cf, 'Codice Fiscale')) isValid = false;
                else if (!validaCodiceFiscale(cf)) isValid = false;
            }
            if (telefono) {
                // Rendo il Telefono obbligatorio
                if (!validaTestoNonVuoto(telefono, 'Telefono')) isValid = false;
                else if (!validaTelefono(telefono)) isValid = false;
            }


            // ⚠️ Validazione Indirizzo e Iscrizione (ora includono controllo non-vuoto)
            if (indirizzo && !validaIndirizzo(indirizzo)) isValid = false;
            if (iscrizione && !validaIscrizioneAlbo(iscrizione)) isValid = false;
            
            // ⚠️ Validazione per i nuovi campi (obbligatori)
            if (specializzazione && !validaTestoNonVuoto(specializzazione, 'Specializzazione')) isValid = false;
            if (disponibilita && !validaTestoNonVuoto(disponibilita, 'Disponibilità')) isValid = false;
            if (descrizione && !validaTestoNonVuoto(descrizione, 'Descrizione')) isValid = false;
            
            // Validazione per i campi esistenti che erano già nel codice iniziale
            if (specieTrattate && !validaTestoNonVuoto(specieTrattate, 'Specie Trattate')) isValid = false;
            if (prestazioniOfferte && !validaTestoNonVuoto(prestazioniOfferte, 'Prestazioni Offerte')) isValid = false;


            if (!isValid) {
                e.preventDefault();
            } else {
                if (submitBtn) nascondiErrore(submitBtn);
            }
        });
    }

});