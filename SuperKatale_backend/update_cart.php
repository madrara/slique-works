<?php

 $response = array();
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

 if($_SERVER['REQUEST_METHOD']=='POST'){
 $id = $_POST['id'];
 $qnty =$_POST['quantity'];	
 
 // mysql update row with matched pid
    $result = mysql_query("UPDATE spmkt_purchase SET quantity = '$qnty' WHERE id = $id");
 
    // check if row deleted or not
    if (mysql_affected_rows() > 0) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully deleted";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";
 
        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>