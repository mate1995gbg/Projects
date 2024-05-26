<?php
    session_start();
    session_destroy();
?>

<!DOCTYPE html>
<html lang="sv">
    <head>
        <meta charset="UTF-8">
        <title>Retro Websites.com</title>
        <link rel="stylesheet" href="style.css" />
    </head>
    <body>
        <div class="header">
			<img src="images/projekt_logo.png" id="header_logo" width="96px" height="80px"/>
			<h1 class="header_text"></h1>
            <div id="Webpage Name"></div>
            <div class="user-buttons-container">
                    <button class="signup-button" onclick="document.location='signup.php'"></button>
                    <button class="login-button" onclick="document.location='login.php'"></button>
            </div>
        </div>
        <div class="header_piping"> 
        </div>
        <div class="main">
            <div class="sidebar">
                <button class="button_home" onclick="document.location='index.php'"></button>
                <button class="button_search"></button>
                <button class="button_archive"></button>
                <button class="button_filter" ></button>
                <button class="button_about"></button>
            </div>
            <div class="content-container">
                <h1>You have successfully logged out!</h1>
			</div>
        </div>
		
    </body>
</html>