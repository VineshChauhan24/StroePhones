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

$sql = "SELECT * FROM phones_tb";
$result = mysqli_query($conn, $sql);


$phonesData = array();
if (mysqli_num_rows($result) > 0) {
    while ($row = mysqli_fetch_row($result)) {
        $phonesData[] = array(
            'id' => $row[0],
            'quantity' => $row[1],
            'price' => $row[2],
            'type' => $row[3],
            'image' => $row[4],
            'date_modified' => $row[5],
        );
    }
}

mysqli_close($conn);
echo json_encode($phonesData);
?>