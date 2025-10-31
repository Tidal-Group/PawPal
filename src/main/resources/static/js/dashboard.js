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


function verificaPasswordGenerica(passwordId, confirmId) {
    const passwordInput = document.getElementById(passwordId);
    const confirmInput = document.getElementById(confirmId);
    if (!passwordInput || !confirmInput) return;

    const validateMatch = () => {
        const pwdValue = (passwordInput.value || '').trim();
        const confValue = (confirmInput.value || '').trim();
        
       
        if (confValue !== '' && pwdValue !== confValue) {
            mostraErrore(confirmInput, 'Le password non corrispondono!');
        } else {
            nascondiErrore(confirmInput);
        }
    };

    passwordInput.addEventListener('input', validateMatch);
    confirmInput.addEventListener('input', validateMatch);

    
    const parentForm = passwordInput.closest('form');
    if (parentForm) {
        parentForm.addEventListener('submit', (e) => {
            const pwdValue = (passwordInput.value || '').trim();
            const confValue = (confirmInput.value || '').trim();
            if (pwdValue !== confValue) {
                e.preventDefault();
                mostraErrore(confirmInput, 'Le password non corrispondono!');
                confirmInput.focus();
            }
        });
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


function validaEmail(input) {
    const value = (input.value || '').trim();
    if (value === '') {
         mostraErrore(input, 'L\'email non può essere vuota.');
        return false;
    }
    const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!pattern.test(value)) {
        mostraErrore(input, 'Email non valida (es. esempio@dominio.com)');
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


function validaTestoNonVuoto(input, nomeCampo) {
    const value = (input.value || '').trim();
    if (value === '') {
        mostraErrore(input, `Il campo '${nomeCampo}' non può essere vuoto.`);
        return false;
    }
    nascondiErrore(input);
    return true;
}




document.addEventListener('DOMContentLoaded', () => {

   
    const formModificaDati = document.querySelector('#modifica_dati form');
    if (formModificaDati) {
        const cf = formModificaDati.querySelector('#codiceFiscale');
        const telefono = formModificaDati.querySelector('#telefono');
        const indirizzo = formModificaDati.querySelector('#indirizzo'); 
        const iscrizione = formModificaDati.querySelector('#iscrizione_albo'); 
        const submitBtn = formModificaDati.querySelector('button[type="submit"]');

        
        if (cf) cf.addEventListener('input', () => validaCodiceFiscale(cf));
        if (telefono) telefono.addEventListener('input', () => validaTelefono(telefono));
        if (indirizzo) indirizzo.addEventListener('input', () => validaIndirizzo(indirizzo));
        if (iscrizione) iscrizione.addEventListener('input', () => validaIscrizioneAlbo(iscrizione));

       
        formModificaDati.addEventListener('submit', (e) => {
            let isValid = true;
            
            
            if (cf && !validaCodiceFiscale(cf)) isValid = false;
            if (telefono && !validaTelefono(telefono)) isValid = false;
            if (indirizzo && !validaIndirizzo(indirizzo)) isValid = false;
            if (iscrizione && !validaIscrizioneAlbo(iscrizione)) isValid = false;
            
            if (!isValid) {
                e.preventDefault(); 
            } else {
                 if(submitBtn) nascondiErrore(submitBtn);
            }
        });
    }

    
    const formPassword = document.querySelector('#modal-password form');
    if (formPassword) {
        const currentPass = formPassword.querySelector('#current_password');
        const newPass = formPassword.querySelector('#new_password');
        
        newPass.addEventListener('input', () => validaPassword(newPass));
        verificaPasswordGenerica('new_password', 'confirm_password');

        formPassword.addEventListener('submit', (e) => {
            let isValid = true;
            if (!validaTestoNonVuoto(currentPass, 'Password corrente')) isValid = false;
            if (!validaPassword(newPass)) isValid = false;
            
            if (!isValid) {
                e.preventDefault();
            }
        });
    }

    const formEmail = document.querySelector('#modal-email form');
    if (formEmail) {
       
        const currentPass = formEmail.querySelector('#current_password'); 
        const emailInput = formEmail.querySelector('#new_email');         
        const confirmInput = formEmail.querySelector('#confirm_email');

        if (emailInput) emailInput.addEventListener('input', () => validaEmail(emailInput));
        
        
        const validateEmailMatch = () => {
            const emailValue = (emailInput.value || '').trim();
            const confValue = (confirmInput.value || '').trim();
            
            if (confValue !== '' && emailValue !== confValue) {
                mostraErrore(confirmInput, 'Le email non corrispondono!');
            } else {
                nascondiErrore(confirmInput);
            }
        };
        if (emailInput && confirmInput) {
            emailInput.addEventListener('input', validateEmailMatch);
            confirmInput.addEventListener('input', validateEmailMatch);
        }

        formEmail.addEventListener('submit', (e) => {
            let isValid = true;
            
            
            if (currentPass && !validaTestoNonVuoto(currentPass, 'Password corrente')) isValid = false;

           
            if (emailInput && !validaEmail(emailInput)) isValid = false;
            
           
            const emailValue = (emailInput.value || '').trim();
            const confValue = (confirmInput.value || '').trim();
            if (emailValue !== confValue) {
                 mostraErrore(confirmInput, 'Le email non corrispondono!');
                 isValid = false;
            } else {
                 nascondiErrore(confirmInput);
            }

            if (!isValid) {
                e.preventDefault();
            }
        });
    }

    
    const formUsername = document.querySelector('#modal-username form');
    if (formUsername) {
        
        const currentPass = formUsername.querySelector('#current_password'); 
        const usernameInput = formUsername.querySelector('#new_username');  
        const confirmInput = formUsername.querySelector('#confirm_username');

        if (usernameInput) usernameInput.addEventListener('input', () => validaTestoNonVuoto(usernameInput, 'Username'));

        
        const validateUsernameMatch = () => {
            const usernameValue = (usernameInput.value || '').trim();
            const confValue = (confirmInput.value || '').trim();
            
            if (confValue !== '' && usernameValue !== confValue) {
                mostraErrore(confirmInput, "L'username non corrisponde alla conferma!");
            } else {
                nascondiErrore(confirmInput);
            }
        };
        if (usernameInput && confirmInput) {
            usernameInput.addEventListener('input', validateUsernameMatch);
            confirmInput.addEventListener('input', validateUsernameMatch);
        }

        formUsername.addEventListener('submit', (e) => {
            let isValid = true;
            
            
            if (currentPass && !validaTestoNonVuoto(currentPass, 'Password corrente')) isValid = false;
            
            
            if (usernameInput && !validaTestoNonVuoto(usernameInput, 'Username')) isValid = false;

        
            const usernameValue = (usernameInput.value || '').trim();
            const confValue = (confirmInput.value || '').trim();
            if (usernameValue !== confValue) {
                 mostraErrore(confirmInput, "L'username non corrisponde alla conferma!");
                 isValid = false;
            } else {
                 nascondiErrore(confirmInput);
            }

            if (!isValid) {
                e.preventDefault();
            }
        });
    }
    
    

    const modalIds = ['modal-email', 'modal-username', 'modal-password'];
    modalIds.forEach(id => {
        const modalElement = document.getElementById(id);
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
    });

});