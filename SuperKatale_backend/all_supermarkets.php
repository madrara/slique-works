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
 
// get all products from products table
$result = mysql_query("SELECT *FROM supermarket_reg_tb") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["supermkts"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $supermkt = array();
        $supermkt["supermkt_id"] = $row["supermkt_id"];
	$supermkt["supermkt_names"] = $row["supermkt_names"];
        $supermkt["supermkt_slogan"] = $row["supermkt_slogan"];
        $supermkt["supermkt_location"] = $row["supermkt_location"];
  	
        // push single product into final response array
        array_push($response["supermkts"],  $supermkt);
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
?>