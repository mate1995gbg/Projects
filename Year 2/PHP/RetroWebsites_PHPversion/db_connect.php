<?php
    //php-kod för uppkoppling till MySQL servern
    $host = "localhost";
    $db_username = "root";
    $db_password = "usbw";
    $db_name = "projekt";
    $mysqli = new mysqli($host, $db_username, $db_password, $db_name);
    //connection-kod i språket PHP med en if-villkor ifall den inte kan koppla till db:n.
	if ($mysqli->connect_errno) {
		echo "Failed to connect to Database!(" . $mysqli->connect_errno . ")" . $mysqli->connection_error;
	}	
?>