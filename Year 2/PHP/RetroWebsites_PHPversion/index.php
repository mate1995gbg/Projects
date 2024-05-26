<?php
    session_start();
?>
<!DOCTYPE html>
<html lang="sv">
    <head>
        <meta charset="UTF-8">
        <title>Retro Websites.com</title>
        <link rel="stylesheet" href="style.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
        <div class="mobilesidebar">
            <button class="button_home_mb" onclick="document.location='index.php'">Home</button>
            <button class="button_search_mb" onclick="document.location='search.php'">Search</button>
            <button class="button_archive_mb">Archive</button>
            <button class="button_filter_mb">Filter</button>
            <button class="button_about_mb" onclick="document.location='about.php'">About</button>
            <?php 
                if(isset($_SESSION['userName'])){
                    echo "<button class=\"profile-button-mb\" onclick=\"document.location='profile.php'\">Profile</button>";
                    echo "<button class=\"logout-button-mb\" onclick=\"document.location='logout.php'\">Logout</button>";
                    echo "<button class=\"addpost-button-mb\" onclick=\"document.location='logout.php'\">Add Post</button>";
                }
                else{
                    echo "<button class=\"signup-button-mb\" onclick=\"document.location='signup.php'\">Signup</button>";
                    echo "<button class=\"login-button-mb\" onclick=\"document.location='login.php'\">Login</button>";
                }
            ?>
        </div>
        <div class="main">
            <div class="sidebar">
                <button class="button_home" onclick="document.location='index.php'"></button>
                <button class="button_search" onclick="document.location='search.php'"></button>
                <button class="button_archive"></button>
                <button class="button_filter"></button>
                <button class="button_about" onclick="document.location='about.php'"></button>
            </div>
            <div class="content-container">
				<?php
					include "db_getpostsindexpage.php";
				?>
			</div>
            <div class="addpost-container">
                <button id="button_addpost"></button>
                <div id="addpostModal" class="modal">
                    <div class="modal-content">
                        <form action="db_addpost.php" method="POST" enctype="multipart/form-data">
                            <span class="close-submitmodal">&times;</span>
                            <input type="url" name="postUrl" placeholder="add the URL for the website you wish to post...">
                            <input type="text" name="postDescription" placeholder="add the description for your post here..">
                            <input type="file" name="file" placeholder="add the image/thumbnail for your post here..">
                            <button name="button_submitpost">Submit Post</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="addpost_modal_js.js"> 
        </script>
    </body>
</html>