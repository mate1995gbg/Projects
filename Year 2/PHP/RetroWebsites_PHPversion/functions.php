<?php
include "db_connect.php";
function emptyLoginFields($username, $password){
    if(empty($username) || empty($password)){
        $result = true;
        header("location: ../signup.php?error=invalidusernameorpassword");
        exit();
    }
    else if(empty($username) && empty($password)){
        $result = true;
        header("location: ../signup.php?error=nousernameandpassword");
        exit();
    }
    else {
        $result = false;
    }
    return $result;

}
function invalidUsername($username){
    if(!preg_match("/^[a-zA-Z0-9]*$/", $username)){
        $result = true;
        header("location: ../signup.php?error=invalidusername");
        exit();
    }
    else {
        $result = false;
    }
    return $result;
}
function invalidPassword($password, $passwordrepeat){
    if($password != $passwordrepeat){
        $result = true;
        header("location: ../signup.php?error=invalidpassword");
        exit();
    }
    else {
        $result = false;
    }
    return $result;
}
function invalidEmail($email){
    
    if(!filter_var($email, FILTER_VALIDATE_EMAIL)){
        $result = true;
        header("location: ../signup.php?error=invalidemail");
        exit();
    }
    else {
        $result = false;
    }
    return $result;
}
function emptySignupFields($username, $password, $passwordrepeat, $email){
    if(empty($username) || empty($password) || empty($passwordrepeat)  || empty($email)){
        $result = true;
        header("location: ../signup.php?error=emptysignupfields");
        exit();
    }
    else {
        $result = false;
    }
    return $result;
}
function usernameExists($conn, $username, $email){
    $sql = "SELECT * FROM projekt_user WHERE Username = ? OR Email = ?;";
    $stmt = mysqli_stmt_init($conn);

    if(!mysqli_stmt_prepare($stmt, $sql)) {
        /*OK, run this sql statement inside the database, and seee if theres any sort of errors. if there is the user will be sent back with erro message. */
        header("location: ../signup.php?error=usernametaken");
        exit();
    }
    mysqli_stmt_bind_param($stmt, "ss", $username, $email); /* ss for string, string, username and email, strings. */
    mysqli_stmt_execute($stmt);

    $resultData = mysqli_stmt_get_result($stmt);

    if($row4 = mysqli_fetch_assoc($resultData)){
        return $row4;
    }
    else{
        $result = false;
        return $result;
    }
    mysqli_stmt_close($stmt);
}
function createUser($conn, $username, $email, $password){
    $sql = "INSERT INTO projekt_user (Username, userPassword, Email) VALUES (?, ?, ?);";
    $stmt = mysqli_stmt_init($conn);

    if(!mysqli_stmt_prepare($stmt, $sql)) {
        /*OK, run this sql statement inside the database, and seee if theres any sort of errors. if there is the user will be sent back with erro message. */
        header("location: ../signup.php?error=stmtfailed");
        exit();
    }

    $hashedPwd = password_hash($password, PASSWORD_DEFAULT); /*hashes the password with the latest hashing algorithm with this built in function in php with the default hashing algorithm.*/

    /*mysqli_stmt_bind_param($stmt, "ssss", $username, $hashedPwd, $email);*/ /* ss for string, string, username and email, strings. */

    if(!mysqli_stmt_bind_param($stmt, "sss", $username, $hashedPwd, $email)) {
        /*OK, run this sql statement inside the database, and seee if theres any sort of errors. if there is the user will be sent back with erro message. */
        header("location: ../signup.php?error=bindparamfailed");
        exit();
    }
    /* mysqli_stmt_execute($stmt); */
    if(!mysqli_stmt_execute($stmt)) {
        /*OK, run this sql statement inside the database, and seee if theres any sort of errors. if there is the user will be sent back with erro message. */
        header("location: ../signup.php?error=executestmtfailed");
        exit();
    }
    mysqli_stmt_close($stmt);
    header("location: ../signup.php?error=none");
    exit();
}
function loginUser($conn, $username, $pwd){
    $usernameExists = usernameExists($conn, $username, $username);
    print_r($usernameExists); /* finns två username variabler då användare kan logga in med antingen username eller email. se funktion UserExists. */
    if ($usernameExists == false){
        header("location: ../login.php?error=wronglogin");
        exit();
    }
    $pwdHashed = $usernameExists["userPassword"]; /*gettar det hashade lösenordet från databasen. */
    $checkPassword = password_verify($pwd, $pwdHashed); /*verifierar användarens submittade lösenord med det hashade lösenordet från databasen. metoden returnerar endast true/false. */

    if($checkPassword == false){
        header("location: ../login.php?error=wrongpassword".$pwd."=".$pwdHashed."".$usernameExists);
        exit();
    }
    else if($checkPassword == true){
        session_start();
        $_SESSION['userName'] = $usernameExists['Username'];
        $_SESSION["userNumber"] = $usernameExists['UserID'];
        header("location: /index.php?error=successfullogin");
        exit();
    }

}
function searchQuery($conn, $keyword, $date){
    $searchQuery = "SELECT * FROM projekt_post WHERE PostDescription LIKE ? OR DateCreated LIKE ?;";
    $stmt = mysqli_stmt_init($conn);

    if(!mysqli_stmt_prepare($stmt, $searchQuery)) {
        /*OK, run this sql statement inside the database, and seee if theres any sort of errors. if there is the user will be sent back with erro message. */
        header("location: ../search.php?error=stmtfailed");
        exit();
    }

    if(!mysqli_stmt_bind_param($stmt, "ss", $keyword, $date)) {
        /*OK, run this sql statement inside the database, and seee if theres any sort of errors. if there is the user will be sent back with erro message. */
        header("location: ../search.php?error=bindparamfailed");
        exit();
    }

    if(!mysqli_stmt_execute($stmt)) {
        /*OK, run this sql statement inside the database, and seee if theres any sort of errors. if there is the user will be sent back with erro message. */
        header("location: ../search.php?error=executestmtfailed");
        exit();
    }

    if($searchResult = mysqli_stmt_get_result($stmt)){
        return $searchResult;
    }
    
    else{
        $result = false;
        return $result;
    }
    mysqli_stmt_close($stmt);
}

function emptySearchFields($keyword, $date){
    if(empty($keyword) || empty($date)){
        $result = true;
        header("location: /search.php?error=invalidkeywordordate");
        exit();
    }
    else if(empty($keyword) && empty($date)){
        $result = true;
        header("location: /search.php?error=nokeywordordate");
        exit();
    }
    else {
        $result = false;
    }
    return $result;

}


function uploadFile($fileName, $file_forTypeCheck, $file_forSizeCheck){
    ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);
    //kollar vilken användaren webbservern körs som
    echo exec('whoami'). "<br>";


    $target_dir = "./images/";
    $target_file = $target_dir . basename($fileName);
    $uploadOk = 1;
    $imageFileType = strtolower(pathinfo($target_file, PATHINFO_EXTENSION));

    $check = getimagesize($file_forTypeCheck);

    if($check !== false) {
      echo "File is an image - " . $check["mime"] . ".";
      $uploadOk = 1;
    } 
    else {
      echo "File is not an image.";
      $uploadOk = 0;
    }

    // Check if file already exists
    if (file_exists($target_file)) {
        echo "Sorry, file already exists.";
        $uploadOk = 0;
    }
    
    // Check file size
    if ($file_forSizeCheck > 500000) {
        echo "Sorry, your file is too large.";
        $uploadOk = 0;
    }
    
    // Allow certain file formats
    if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg"
    && $imageFileType != "gif") {
        echo "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
        $uploadOk = 0;
    }
    
    // Check if $uploadOk is set to 0 by an error
    if ($uploadOk == 0) {
        echo "Sorry, your file was not uploaded.";
    // if everything is ok, try to upload file
    } 
    else {
        if (move_uploaded_file($file_forTypeCheck, $target_file)) {
        //echo "The file ". htmlspecialchars(basename($fileName)). " has been uploaded.";
        //header("location: db_addpost.php?error=fileuploaded");
        } 
        else {
        echo "Sorry, there was an error uploading your file.";
        header("location: db_addpost.php?error=uploaderror");
        }
    }
}