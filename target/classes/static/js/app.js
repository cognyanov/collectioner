window.onload = () => {
    if (window.location.pathname !== '/admin') {
        let currEnergy = document.getElementById("currEnergy");


        document.getElementById("energyBar").style.width = currEnergy.textContent * 10 + "%";
    }


    if (window.location.pathname === '/admin') {
        let promoteUserDiv = document.getElementById("divPromoteUser");
        let demoteUserDiv = document.getElementById("divDemoteUser");


        document.getElementById("promoteUserBtn").addEventListener("click", () => {
            promoteUserDiv.style.display = "block";
            demoteUserDiv.style.display = "none";

        });

        document.getElementById("demoteUserBtn").addEventListener("click", () => {
            demoteUserDiv.style.display = "block";
            promoteUserDiv.style.display = "none";

        })

    }
};