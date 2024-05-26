<?php
    require_once "db_connect.php";
    require_once "functions.php";
    if (isset($_POST['loginbtn'])){
        $username=$_POST['username'];
        $password=$_POST['password'];
    }
?>