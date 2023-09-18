document.addEventListener('DOMContentLoaded', function () {

    var links = document.getElementsByClassName("link-name")
    for (const link of links) {
        link.onclick = function (){
            alert(link.id)
            localStorage.setItem("barType",link.id)
            window.location.href = 'bar.html'
        }
    }

})