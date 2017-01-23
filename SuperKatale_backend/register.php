<?php
 define('HOST','localhost');
 define('USER','root');
 define('PASS','');
 define('DB','superkatale_db');
 
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 $fullnames = $_POST['fullnames'];
 $email = $_POST['email_address'];
 $phone = $_POST['phone_number'];
 $password = $_POST['password'];
 
 
 $sql = "INSERT INTO register_tb (fullnames,email_address,phone_number,password) VALUES ('$fullnames','$email','$phone','$password')";
 
 
 if(mysqli_query($con,$sql)){
 echo "Successfully Registered";
 }else{
 echo "Could not register";
 
 }
 }else{
echo 'error';
}
