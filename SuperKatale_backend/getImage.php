<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
 if($_SERVER['REQUEST_METHOD']=='GET'){
 $id = $_GET['id'];
 $sql = "select * from images where id = '$id'";
 
 $r = mysqli_query($con,$sql);
 
 $result = mysqli_fetch_array($r);
 
 header('content-type: images/png');
 
 echo base64_decode($result['image']);
 
 mysqli_close($con);
 
 }else{
 echo "Error";
 }
?>