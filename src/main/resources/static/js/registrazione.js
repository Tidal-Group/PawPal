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

function verificaPasswordGenerica(passwordId, confirmId, errorId) {
    const passwordInput = document.getElementById(passwordId);
    const confirmInput = document.getElementById(confirmId);
    if (!passwordInput || !confirmInput) return;

    const validateMatch = () => {
        if (isPristine(confirmInput)) {
            nascondiErrore(confirmInput, errorId);
            return;
        }
        const pwdValue = (passwordInput.value || '').trim();
        const confValue = (confirmInput.value || '').trim();
        if (pwdValue !== confValue) {
            mostraErrore(confirmInput, 'Le password non corrispondono!', errorId);
        } else {
            nascondiErrore(confirmInput, errorId);
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
                mostraErrore(confirmInput, 'Le password non corrispondono!', errorId);
                confirmInput.focus();
            }
        });
    }
}

function validaCodiceFiscale(input) {
    const errorId = input.id + '_error';
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
        nascondiErrore(input);
        return true;
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
        nascondiErrore(input);
        return true;
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

document.addEventListener('DOMContentLoaded', () => {

    verificaPasswordGenerica('password_cliente', 'check_password_cliente', 'check_password_error_cliente');
    verificaPasswordGenerica('password_veterinario', 'check_password_veterinario', 'check_password_error_veterinario');

    const formCliente = document.querySelector('#registrazioneClienteModal form');
    if (formCliente) {
        const cf = formCliente.querySelector('#codice_fiscale_cliente');
        const telefono = formCliente.querySelector('#telefono_cliente');
        const email = formCliente.querySelector('#email_cliente');
        const password = formCliente.querySelector('#password_cliente');

        cf.addEventListener('input', () => validaCodiceFiscale(cf));
        telefono.addEventListener('input', () => validaTelefono(telefono));
        email.addEventListener('input', () => validaEmail(email));
        password.addEventListener('input', () => validaPassword(password));

        formCliente.addEventListener('submit', (e) => {
            if (!validaCodiceFiscale(cf) || !validaTelefono(telefono) ||
                !validaEmail(email) || !validaPassword(password)) {
                e.preventDefault();
            }
        });
    }

     const formVet = document.querySelector('#registrazioneVeterinarioModal form');
    if (formVet) {
        const indirizzo = formVet.querySelector('#indirizzo_studio_veterinario');
        const iscrizione = formVet.querySelector('#iscrizione_albo_veterinario');
        const cfVet = formVet.querySelector('#codice_fiscale_veterinario');
        const telefonoVet = formVet.querySelector('#telefono_veterinario');
        const emailVet = formVet.querySelector('#email_veterinario');
        const passwordVet = formVet.querySelector('#password_veterinario');

        indirizzo.addEventListener('input', () => validaIndirizzo(indirizzo));
        iscrizione.addEventListener('input', () => validaIscrizioneAlbo(iscrizione));
        cfVet.addEventListener('input', () => validaCodiceFiscale(cfVet));
        telefonoVet.addEventListener('input', () => validaTelefono(telefonoVet));
        emailVet.addEventListener('input', () => validaEmail(emailVet));
        passwordVet.addEventListener('input', () => validaPassword(passwordVet));

        formVet.addEventListener('submit', (e) => {
            if (!validaIndirizzo(indirizzo) || !validaIscrizioneAlbo(iscrizione) ||
                !validaCodiceFiscale(cfVet) || !validaTelefono(telefonoVet) ||
                !validaEmail(emailVet) || !validaPassword(passwordVet)) {
                e.preventDefault();
            }
        });
    }

    const modalId = ['registrazioneClienteModal', 'registrazioneVeterinarioModal'];
    modalId.forEach(id => {
        const modalElement = document.getElementById(id);
        if (!modalElement) return;

        modalElement.addEventListener('hide.bs.modal', (e) => {
            const form = modalElement.querySelector('form');
            if (!form) return;

            const inputs = form.querySelectorAll('input, textarea, select');
            const hasChanges = Array.from(inputs).some(input => !isPristine(input));

            if (hasChanges) {
                const conferma = confirm("Hai modifiche non salvate. Vuoi davvero chiudere?");
                if (!conferma) e.preventDefault(); 
            }
        });

        modalElement.addEventListener('hidden.bs.modal', () => {
            const form = modalElement.querySelector('form');
            if (!form) return;

            const fields = form.querySelectorAll('input, textarea, select');
            fields.forEach(field => {
                if (field.tagName.toLowerCase() === 'select') field.selectedIndex = 0;
                else if (field.type === 'checkbox') field.checked = false;
                else field.value = '';

                const error = document.getElementById(field.id + '_error');
                if (error) error.style.display = 'none';
            });
        });
    });

});

