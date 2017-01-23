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
if($_SERVER['REQUEST_METHOD']=='POST'){
 $phone_number = $_POST['phone_number'];


 
 
// get all products from products table
$result = mysql_query("SELECT *FROM spmkt_purchase WHERE phone_number= '$phone_number'") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["cart"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $cart = array();
  	$cart["id"] = $row["id"];
    	$cart["price"] = $row["price"];
	$cart["quantity"] = $row["quantity"];
	$cart["supermkt_name"] = $row["supermkt_name"];
	$cart["fulldescription"] = $row["fulldescription"];
	$cart["phone_number"] = $row["phone_number"];
	$cart["image"] = $row["image"];
	$cart["status"] = $row["status"];	
	
        
        // push single product into final response array
        array_push($response["cart"],  $cart);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No events found";
 
    // echo no users JSON
    echo json_encode($response);
}
}
?>