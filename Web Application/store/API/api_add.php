<?php

/*
 * Copyright 2018 Ahmed Mahmoud.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Created by Dev Ahmed Mahmoud on 25/5/2018
 * email : dev.ahmed.m@gmail.com
 * phone : +9700597503338
 */

$res = new StdClass();
$res->response = "Add Successfully";
$err = new StdClass();
$err->response = "failed to Add";

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "store_db";

// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}


if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $result = mysqli_query($conn, "SHOW TABLE STATUS LIKE 'phones_tb'");
    $data = mysqli_fetch_assoc($result);
    $next_id = $data['Auto_increment'];

    $Quantity = $_POST["quantity"];
    $Price = $_POST["price"];
    $Type = $_POST["type"];

    $image = $_POST["image"];
    $upload_path = "../img/$next_id.jpg";

    
    $sql = "INSERT INTO phones_tb (quantity, price, type, image, date_modified) VALUES ('$Quantity', '$Price', '$Type', '$next_id.jpg', now())";
    $result = mysqli_query($conn, $sql);

    if ($result) {
        file_put_contents($upload_path, base64_decode($image));
        echo json_encode($res);
    } else {
        echo json_encode($err);
    }
}

mysqli_close($conn);
?>