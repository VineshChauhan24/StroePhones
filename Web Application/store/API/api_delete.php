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
$res->response = "Delete Successfully";
$err = new StdClass();
$err->response = "Failed to Delete";


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

$id = $_GET['id'];


$sql = "SELECT id, image FROM phones_tb WHERE id='$id'";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    while ($row = mysqli_fetch_row($result)) {
        $image = $row[1];
    }
} else {
	echo json_encode($err);
}

if (mysqli_num_rows($result) > 0) {
    
    // Delete image from folder img
    unlink("../img/$image");

    if (mysqli_num_rows($result) > 0) {
        mysqli_query($conn, "DELETE FROM phones_tb WHERE id='$id'");
        echo json_encode($res);
    } else {
        echo json_encode($err);
    }
}

mysqli_close($conn);
?>