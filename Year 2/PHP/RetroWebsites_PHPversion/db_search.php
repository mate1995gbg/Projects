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
                <button id="myBtn">New Search</button>
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
                <?php
                    include "functions.php";
                    if (isset($_POST["button_newsearchbtn"])){
                        $keyword = $_POST["keyword"];
                        $date = $_POST["date"];
                        
                        $searchResultSet = searchQuery($mysqli, $keyword, $date);

                        if(empty($searchResultSet)){
                            echo "<p> No results found!</p>";
                        }

                        if($searchResultSet -> num_rows > 0){
                            while($searchRow = $searchResultSet -> fetch_assoc()){
                        ?>
                                <div class="content-group">
                                    <div class="image-container">
                                        <img src="<?php echo $searchRow['ImageURL']; ?>" class="post_thumbnail"/>
                                    </div>
                                    <div class="dialog-container">
                                        <div class="comment-container">
                                            <h6 id="postedBy">Posted By: <?php echo $searchRow['UserID']; ?> </h6>
                                            <h3 id="postTit"><a href="<?php echo $searchRow['PostTitle']; ?>"> <?php echo $searchRow['PostTitle']; ?> </a></h3>
                                            <h4 id="postDesc"><?php echo $searchRow['PostDescription']; ?></h4>
                                            <h5 id="postId"><?php echo $searchRow['PostID']; ?></h5>
                                        </div>
                                        <div class="rating-container">
                                        </div>
                                    </div>
                                </div>
                        <?php
                            }
                        }
                    }
                ?>
			</div>
        </div>	
        <script src="search_modal_js.js">
        </script>	
    </body>
</html>