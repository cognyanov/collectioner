window.onload = () => {
    let currEnergy = document.getElementById("currEnergy");

    console.log(currEnergy.textContent * 10 + "%");
    document.getElementById("energyBar").style.width = currEnergy.textContent * 10 + "%";

};