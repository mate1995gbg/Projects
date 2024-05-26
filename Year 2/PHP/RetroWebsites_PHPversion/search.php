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
                <button class="button_search"></button>
                <button class="button_archive"></button>
                <button class="button_filter" ></button>
                <button class="button_about" onclick="document.location='about.php'"></button>
            </div>
            <div class="content-container">
                <button id="myBtn">Search</button>
                <div id="myModal" class="modal">
                    <div class="modal-content">
                        <form action="db_search.php" method="POST">
                            <span class="close">&times;</span>
                            <input type="text" name="keyword" placeholder="search by keyword...">
                            <input type="text" name="date" placeholder="search by date posted...">
                            <button name="button_newsearchbtn">Search</button>
                        </form>
                    </div>
                </div>
			</div>
        </div>
        <script src="search_modal_js.js">
        </script>
    </body>
</html>