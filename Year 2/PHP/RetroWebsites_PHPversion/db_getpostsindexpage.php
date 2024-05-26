<?php
    include "db_connect.php";
    $sql = "SELECT PostID, UserID, PostTitle, PostDescription, DateCreated, ImageURL FROM projekt_post";
    /* $sqlcomment = "SELECT CommentID, DatePosted, PostComment, PostID, UserID FROM projekt_comment"; */
    $result = $mysqli->query($sql);
    /* $result2 = $mysqli->query($sqlcomment2); */
    if($result->num_rows > 0){
        while($row=$result->fetch_assoc()){ 
            /*hämtar antal kommentarer för varje post */
            $result2 = $mysqli->query("SELECT COUNT(*) as coun FROM projekt_comment WHERE PostID =" .$row['PostID']); 
            /*lägger in result2 i row2 med fetch_assoc()*/
            $row2=$result2->fetch_assoc();
            /*hämtar antal likes för varje post */
            $result3 = $mysqli->query("SELECT COUNT(*) as coun2 FROM projekt_rating WHERE PostID =" .$row['PostID']); 
            /*lägger in result3 i row3 med fetch_assoc()*/
            $row3=$result3->fetch_assoc();   
?>
            <div class="content-group">
                <div class="image-container">
                    <img src="<?php echo $row['ImageURL']; ?>" class="post_thumbnail"/>
                </div>
                <div class="dialog-container">
                    <div class="comment-container">
                        <h6 id="postedBy">Posted By: <?php echo $row['UserID']; ?> </h6>
                        <h3 id="postTit"><a href="<?php echo $row['PostTitle']; ?>"> <?php echo $row['PostTitle']; ?> </a></h3>
                        <h4 id ="postDesc"><?php echo $row['PostDescription']; ?></h4>
                        <h5 id ="postId"><?php echo $row['PostID']; ?></h5>
                    </div>
                    <div class="rating-container">
                        <label for="commentBtn"><?php echo $row2['coun']?></label> 
                        <button id="commentBtn">Comments</button>
                        <label for="likesBtn"><?php echo $row3['coun2']?></label>
                        <button id="likesBtn">Likes</button>
                        <button id="shareBtn">Share</button>
                    </div>
                </div>
            </div>
<?php
        }
    }
    
?>