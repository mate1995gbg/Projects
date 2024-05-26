<?php
session_start();
?>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <title>Retro Websites.com</title>
    <link rel="stylesheet" href="style.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
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
                echo "<button onclick=\"document.location='changePassword.php'\">Change Password</button>";
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
            <button class="button_search"></button>
            <button class="button_archive"></button>
            <button class="button_filter" ></button>
            <button class="button_about"></button>
        </div>
        <form class="deleteform" action="db_deleteUser.php" method="POST"> <!-- form för att hantera användarinput med php. -->
            <h3>userid: </h3>
            <?php
            $useridShow = $_SESSION["userId"];
            echo "<h4>$useridShow</h4>";
            ?>
            <h3>click here to delete your user profile</h3>
            <button name="button_deleteUser">Delete</button> <!-- knapp -->
            <?php
            if(isset($_GET['error'])){ /*IF there is an error in url, this code will run. check function signupUser for more info. */
                if ($_GET['error'] == "success"){
                    echo "<p>you have successfully deleted your user profile !</p>";
                }
                if ($_GET['error'] == "stmtfailed"){
                    echo "<p>statementfailed! !</p>";
                }
            }
            ?>
            </form>                    
        </div>
    </div>
</body>
</html>