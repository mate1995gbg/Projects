/*Här finns min geolocationkod (VG) där även 3 vanliga jsfunktioner finns (G) */
function weather(){
    var location = document.getElementById("location");
    var temp = document.getElementById("temp");
    var weather = document.getElementById("weather");
    var url ="https://api.openweathermap.org/data/2.5/weather?lat=" /* +lat+"&lon="+lon+"&appid="+apikey; */
    var apikey = "203b5f0d33e51630733ec2f91e24c631";
    var lat;
    var lon;

    navigator.geolocation.getCurrentPosition(success, error);
   
    function success(position){
        lat = position.coords.latitude;
        lon = position.coords.longitude;
        $.getJSON(url + lat + "&lon=" + lon + "&appid=" + apikey, function(data){ /*obs function(data) msåte vara med som villkor i getJSON */
            console.log(data);
            console.log(url);
            var items = data.weather[0].description; //hämtar från objectarrayen i JSON API:t istället för fristående objekt.
            location.innerHTML = "location: " + data.name;
            weather.innerHTML = "weather: "+ items;
            tempInKelvin = data.main.temp;
            tempInCelsius = tempInKelvin-273.15;
            tempInCelsiusRounded = returnValue(tempInCelsius);
            temp.innerHTML = "temperature: "+ tempInCelsiusRounded + " C°";
        });
    }
    
    function error(error){
        switch(error.code){
            case error.PERMISSION_UNAVAILABLE:
                x.innerHTML = "Location info not avaiable"
                break;
            case error.POSITION_UNAVAILABLE:
                x.innerHTML = "Position info not avaiable"
                break;
            case error.TIMEOUT:
                x.innerHTML = "request timeout"
                break;
            case error.UNKNOWN_ERROR:
                x.innerHTML = "Unknown error has occured"
                break;           
        }
    }
}
weather();