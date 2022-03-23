document.addEventListener("DOMContentLoaded", function() {
    let options = window.location.search.slice(1)
        .split('&')
        .reduce(function _reduce (/*Object*/ a, /*String*/ b) {
            b = b.split('=');
            a[b[0]] = decodeURIComponent(b[1]);
            return a;
        }, {});
    if (options && options.show) {
        try {
            let modal = document.querySelector('#' + options.show)
            bootstrap.Modal.getOrCreateInstance(modal).show()
        } catch (e) {
            console.log(options.show + " modal not found")
        }
    }
});
