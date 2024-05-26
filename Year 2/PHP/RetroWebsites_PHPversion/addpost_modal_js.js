var modal = document.getElementById("addpostModal");
var btn = document.getElementById("button_addpost");
var span = document.getElementsByClassName("close-submitmodal")[0]; //hämtar span elementet som stänger modalen 

btn.onclick = function() { //gör att modalen visas när användaren klickar på knappen.
  modal.style.display = "block";
}


span.onclick = function() { //stänger modalen när användaren klickar på spanelementet.
  modal.style.display = "none";
}

window.onclick = function(event) { //stägner modalen ifall användaren klickar utanför modalen.
  if (event.target == modal) {
    modal.style.display = "none";
  }
}