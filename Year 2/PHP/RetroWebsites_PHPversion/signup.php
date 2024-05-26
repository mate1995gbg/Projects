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
                <form action="includes/signup.inc.php" method="POST">
                    <div class="signup-group">
                        <h1 class="h6 mb-3 font-weight-normal" id="login-text">Please enter  username:</h1>
                         <!-- inputs NEED to have name attribute to be able to be processed with php. -->
                        <input type="text" name="username" id="inputusername" class="form-control" placeholder="Username" required autofocus>
                        <h1 class="h6 mb-3 font-weight-normal" id="login-text">Please enter password:</h1>
                        <input type="text" name="password" id="inputpassword" class="form-control" placeholder="Password" required autofocus>
                        <h1 class="h6 mb-3 font-weight-normal" id="login-text">Please repeat password:</h1>
                        <input type="text" name="passwordrepeat" id="inputpasswordrepeat" class="form-control" placeholder="Password" required autofocus>
                        <h1 class="h6 mb-3 font-weight-normal" id="login-text">Please enter e-mail adress:</h1>
                        <input type="text" name="email" id="inputemail" class="form-control" placeholder="E-mail" required autofocus>
                        <button class="btn btn-sm btn-primary btn-block" id="signup-button" name="signupbtn" type="submit">Sign up</button>
                        <?php 
                            if(isset($_GET['error'])){ /*IF there is an error in url, this code will run. check function signupUser for more info. */
                                if ($_GET['error'] == "emptyinput"){
                                    echo "<p>Please fill in all fields!</p>";
                                }
                                else if  ($_GET['error'] == "invalidusername"){
                                    echo "<p>Choose a proper username!</p>";
                                }
                                else if  ($_GET['error'] == "invalidemail"){
                                    echo "<p>Choose a proper email!</p>";
                                }
                                else if  ($_GET['error'] == "invalidpassword"){
                                    echo "<p>Passwords don't match!</p>";
                                }
                                else if  ($_GET['error'] == "emptysignupfields"){
                                    echo "<p>Please fill in all fields.</p>";
                                }
                                else if  ($_GET['error'] == "usernametaken"){
                                    echo "<p>Username already exists in the database!</p>";
                                }
                                else if  ($_GET['error'] == "stmtfailed"){
                                    echo "<p>Database does not match with entered values!</p>";
                                }
                                else if  ($_GET['error'] == "bindparamfailed"){
                                    echo "<p>chosen values for signup info does not match the requirements for entering into the database!</p>";
                                }
                                else if  ($_GET['error'] == "executestmtfailed"){
                                    echo "<p>could not execute statement.</p>";
                                }
                                else if  ($_GET['error'] == "none"){
                                    echo "<p>Successfully signed up!</p>";
                                }
                            }
                        ?>
                    </div>
                </form>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous">
        </script>
    </body>
</html>