const passwordStatus = document.querySelectorAll('.input-group-prepend.password-status .input-group-text .fa-solid')
const passwordInput = document.querySelectorAll('.form-control.password-first, .form-control.password-confirm')

passwordInput.forEach((e) => {
    e.addEventListener('change', () => {
        compare()
    })
})

function compare() {
    if (passwordInput[0].value === passwordInput[1].value) {
        passwordStatus.forEach(e => {
            e.classList.add('fa-check', 'text-success')
            e.classList.remove('fa-xmark', 'text-danger')
        })
    } else {
        passwordStatus.forEach(e => {
            e.classList.remove('fa-check', 'text-success')
            e.classList.add('fa-xmark', 'text-danger')
        })
    }
}