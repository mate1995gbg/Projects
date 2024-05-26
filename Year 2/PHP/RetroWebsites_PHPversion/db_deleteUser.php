<?php
    session_start();
    include "db_connect.php";
    include "deleteUser.php";
    if (isset($_POST['button_deleteUser'])){
        $userId = $_SESSION["userId"]; 
        $deleteQuery = "DELETE FROM projekt_user WHERE UserID =".$userId.";";
        $delResult = $mysqli->query($deleteQuery);
        header("location: ../~mate0025/deleteUser.php?error=success");
        session_destroy();
    }
?>