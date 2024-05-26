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
                <button class="button_filter" ></button>
                <button class="button_about" onclick="document.location='about.php'"></button>
            </div>
            <div class="content-container">
                <?php
                    include "functions.php";

                    if (isset($_POST["button_submitpost"])){
                        $postDesc = $_POST["postDescription"];
                        $posturlNew = $_POST["postUrl"];
                        $userID = $_SESSION["userNumber"];
                        $file_Name = $_FILES["file"]["name"];
                        $file_NameAndDir = "images/".$file_Name; //för databasen
                        $file_forTypeCheck = $_FILES["file"]["tmp_name"];
                        $file_forSizeCheck = $_FILES["file"]["size"];
                        $currentDate = date("Y-m-d");
                        uploadFile($file_Name, $file_forTypeCheck, $file_forSizeCheck);

                        include "db_connect.php";
                        $postQuery = "INSERT INTO projekt_post (UserID, PostTitle, PostDescription, DateCreated, ImageURL) VALUES (?, ?, ?, ?, ?);";
                        $stmt = mysqli_stmt_init($mysqli);
                        if(!mysqli_stmt_prepare($stmt, $postQuery)) {
                            /*OK, run this sql statement inside the database, and seee if theres any sort of errors. if there is the user will be sent back with erro message. */
                            header("location: ~mate0025/db_addpost.php?error=stmtfailed");
                            exit();
                        }
                    
                        if(!mysqli_stmt_bind_param($stmt, "issss", $userID, $posturlNew, $postDesc, $currentDate, $file_NameAndDir)) {
                            /*OK, run this sql statement inside the database, and seee if theres any sort of errors. if there is the user will be sent back with erro message. */
                            header("location: ~mate0025/db_addpost.php?error=bindparamfailed");
                            exit();
                        }
                        /* mysqli_stmt_execute($stmt); */
                        if(!mysqli_stmt_execute($stmt)) {
                            /*OK, run this sql statement inside the database, and seee if theres any sort of errors. if there is the user will be sent back with erro message. */
                            echo ("UserID: " .$userID). "<br>";
                            echo ("PostUrlNew: " .$postUrlNew). "<br>";
                            echo ("PostDesc: " .$postDesc). "<br>";
                            echo ("currentDate: " .$currentDate). "<br>";
                            echo ("fileNameAndDir: " .$file_NameAndDir). "<br>";
                            echo("Error description: " . $mysqli -> error);
                            exit();
                        }
                        mysqli_stmt_close($stmt);
                        header("location: ~mate0025/db_addpost.php?error=successaddpost");
                    }
            
                ?>
			</div>
        </div>	
        <script src="addpost_modal_js.js">
        </script>	
    </body>
</html>