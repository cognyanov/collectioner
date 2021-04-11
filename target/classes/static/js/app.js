window.onload = () => {

    let currEnergy = document.getElementById("currEnergy");


    document.getElementById("energyBar").style.width = currEnergy.textContent * 10 + "%";


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

    if (window.location.pathname === '/storage') {
        let openOffer = document.getElementById('buyEnergyPotionsBtn');
        let specialOffer = document.getElementById('buyEnergyPotions');
        let specialOfferErr = document.getElementById('buyEnergyPotionsErr');

        openOffer.addEventListener('click', () => {
           specialOffer.style.display = 'block';
        });

        let closeOffer = document.getElementById('closeEnergyPotionsOffer');

        closeOffer.addEventListener('click', () => {
           specialOffer.style.display = 'none';
        });

        let okBtn = document.getElementById('okayBtnDiv').addEventListener('click', () => {
            if (document.getElementById('notEnoughGoldSpan') != null) {
                document.getElementById('notEnoughGoldSpan').style.display = 'none';
            }

            if (document.getElementById('boughtPotions') != null) {
                document.getElementById('boughtPotions').style.display = 'none';
            }

            specialOfferErr.style.display = 'none';

        })
    }




};