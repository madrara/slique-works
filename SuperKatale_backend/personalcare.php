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
 $supermkt_id = $_POST['supermkt_id'];
 $supermkt_name = $_POST['supermkt_name'];

 
 
// get all products from products table
$result = mysql_query("SELECT *FROM personalcare WHERE spmkt_id = '$supermkt_id' AND spmkt_name='$supermkt_name'") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["bread"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $cat = array();
  	$cat["id"] = $row["id"];
    	$cat["description"] = $row["description"];
	$cat["price"] = $row["price"];
	$cat["fulldescription"] = $row["fulldescription"];
	$cat["image"] = $row["image"];
        
        // push single product into final response array
        array_push($response["bread"],  $cat);
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