<?php
 define('HOST','localhost');
 define('USER','root');
 define('PASS','');
 define('DB','superkatale_db');
 
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
 
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 $phone = $_POST['phone_number'];
 $password = $_POST['password'];
 
 
 $sql = "SELECT * FROM register_tb WHERE phone_number = '$phone' AND password='$password'";
 
 $result = mysqli_query($con,$sql);
 
 $check = mysqli_fetch_array($result);
 
 if(isset($check)){
 echo 'success';
 }else{

 echo 'failure';
 }
 }
?>