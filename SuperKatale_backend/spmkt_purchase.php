<?php
 define('HOST','localhost');
 define('USER','root');
 define('PASS','');
 define('DB','superkatale_db');
 
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 $image = $_POST['image'];
 $price = $_POST['price'];
 $spmkt_name = $_POST['supermkt_name'];
 $quantity = $_POST['quantity']; 
 $fulldescription = $_POST['fulldescription']; 
 $phone_number = $_POST['phone_number'];
 $status = $_POST['status']; 
 
 $cost =$price*$quantity;

 
 
 $sql = "INSERT INTO spmkt_purchase (image,price,quantity,supermkt_name,fulldescription,phone_number,status) VALUES ('$image','$price','$quantity','$spmkt_name','$fulldescription','$phone_number','clearance pending')";
 
 
 if(mysqli_query($con,$sql)){
 echo "Successfully added to your cart!";
 }else{
 echo "Could not Make Purchase!";
 
 }
 }else{
echo 'error';
}
