function isPristine(inputElement) {
    return inputElement.value.trim() === inputElement.defaultValue.trim();
}

function verificaPasswordCliente() {

    const password = document.getElementById("password_cliente");
    const checkPassword = document.getElementById("check_password_cliente");

    const checkPasswordError = document.getElementById("check_password_error_cliente");

    if(password && checkPassword && checkPasswordError) {
        if(!isPristine(checkPassword)) {
            if(password.value !== checkPassword.value) {
                checkPasswordError.style.display = "block";
            } else checkPasswordError.style.display = "none";
        }
    }

}

function verificaPasswordVeterinario() {

}