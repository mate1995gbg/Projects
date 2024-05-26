<?php
    session_start();
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

                <?php 
                if(isset($_SESSION['userName'])){
                    echo "<button class=\"profile-button\" onclick=\"document.location='profile.php'\"></button>";
                    echo "<button class=\"logout-button\" onclick=\"document.location='logout.php'\"></button>";
                }
                else{
                    echo "<button class=\"signup-button\" onclick=\"document.location='signup.php'\"></button>";
                    echo "<button class=\"login-button\" onclick=\"document.location='login.php'\"></button>";
                }
                ?>
            </div>
        </div>
        <div class="header_piping"> 
        </div>
        <div class="main">
            <div class="sidebar">
                <button class="button_home" onclick="document.location='index.php'"></button>
                <button class="button_search" onclick="document.location='search.php'"></button>
                <button class="button_archive"></button>
                <button class="button_addpost"></button>
                <button class="button_about" onclick="document.location='about.php'"></button>
            </div>
            <div class="content-container">
				<?php
                if(isset($_GET["error"])){
                    if ($_GET['error'] == "fileuploaded"){
                        include "db_connect.php";
                    }
                    else if ($_GET['error'] == "uploaderror"){
                        echo "<p>could not upload file, try again!</p>";
                    }
                }
				?>
			</div>
        </div>		
    </body>
</html>