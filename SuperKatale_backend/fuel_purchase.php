<?php
 define('HOST','localhost');
 define('USER','root');
 define('PASS','');
 define('DB','superkatale_db');
 
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 $vehicle_no = $_POST['vehicle_no'];
 $phone_number = $_POST['phone_number'];
 $fuel_quantity = $_POST['fuel_quantity'];
 $purchase_status = $_POST['purchase_status'];
 $fuelco_name = $_POST['fuelco_name']; 
 
 $sql = "INSERT INTO fuelpurchase_tb (vehicle_no,phone_number,fuel_quantity,purchase_status,fuelco_name) VALUES ('$vehicle_no','$phone_number','$fuel_quantity','pending transaction','$fuelco_name')";
 
 
 if(mysqli_query($con,$sql)){
 echo "Successfully Transacted!Re-Energize Your Vehicle!";
 }else{
 echo "Could not Make Purchase!";
 
 }
 }else{
echo 'error';
}
