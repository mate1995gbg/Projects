<?php
$cookie_test= "test";
$cookie_test_value = "This is a test cookie";
setcookie($cookie_test, $cookie_test_value, time() + (86400 * 30), "/"); 
?>
<!DOCTYPE html>
<html lang="sv">
    <head>
        <meta charset="UTF-8">
        <title>Retro Websites.com</title>
        <link rel="stylesheet" href="style.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    </head>
    <body>
        <div class="header">
            <div id="Webpage Name"></div>
            <img src="images/projekt_logo.png" id="header_logo" width="96px" height="80px"/>
            <h1 class="header_text"></h1>
            <div class="user-buttons-container">
                <button class="signup-button" onclick="document.location='signup.php'"> </button>
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
                <button class="button_filter"></button>
                <button class="button_about"></button>
            </div>
            <div class="form-group">
                <form action="includes/login.inc.php" method="POST">
                    <div class="login-group">
                        <h1 class="h6 mb-3 font-weight-normal" id="login-text">Please enter username:</h1>
                        <input type="text" name="usernamelogin" id="inputusername" class="form-control" placeholder="Username" required autofocus>
                        <h1 class="h6 mb-3 font-weight-normal" id="login-text">Please enter password:</h1>
                        <input type="text" name="passwordlogin" id="inputpassword" class="form-control" placeholder="Password" required autofocus>
                        <button class="btn btn-sm btn-primary btn-block" id="login-button" name="loginbtn" type="submit">Log In</button>
                    </div>
                </form>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous">
        </script>
    </body>
</html>