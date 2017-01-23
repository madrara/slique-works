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
$result = mysql_query("SELECT *FROM mechanicalserviceco_reg_db") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["service_company"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $service_co = array();
        $service_co["mservice_id"] = $row["mservice_id"];
	$service_co["mservice_name"] = $row["mservice_name"];
        $service_co["mservice_slogan"] = $row["mservice_slogan"];
        $service_co["mservice_location"] = $row["mservice_location"];
	$service_co["mservice_contact"] = $row["mservice_contact"];
	  	
        // push single product into final response array
        array_push($response["service_company"],  $service_co);
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