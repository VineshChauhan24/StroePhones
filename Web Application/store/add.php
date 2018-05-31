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

$errMsg = "";

if (isset($_POST['btnAdd'])) {
    $Quantity = $_POST['Quantity'];
    $Type = $_POST['Type'];
    $Price = $_POST['Price'];
    $fname = $_FILES['FleImage']['name'];
    if ($Quantity == "") {
        $errMsg = "Please Enter Quantity";
    } else if ($Type == "") {
        $errMsg = "Please Enter Type";
    } else if ($Price == "") {
        $errMsg = "Please Enter Price";
    } else {
        $result = mysqli_query($conn, "SHOW TABLE STATUS LIKE 'phones_tb'");
        $data = mysqli_fetch_assoc($result);
        $next_increment = $data['Auto_increment'];

        $extension = explode('.', $_FILES['FleImage']['name']);
        $new_image = $next_increment . '.' . $extension[1];
        move_uploaded_file($_FILES['FleImage']['tmp_name'], 'img/' . $new_image);

        $sql = "INSERT INTO phones_tb (quantity, price, type, image, date_modified)
                VALUES ('$Quantity', '$Price', '$Type', '$new_image', now())";

        if ($conn->query($sql) === TRUE) {
            $errMsg = "New record created successfully";
        } else {
            echo "Error: " . $sql . "<br>" . $conn->error;
        }
    }
}

$conn->close();
?> 


<link href="css/bootstrap.min.css" rel="stylesheet">
<div class="container">
    <div class="row justify-content-md-center col-md-12">
        <form action="" method="post" enctype="multipart/form-data" style="margin:50px;">
<?php if ($errMsg != "") { ?>
                <div class="alert alert-danger" role="alert">
    <?= $errMsg; ?>
                </div>
            <?php } ?>

            <div class="form-group">
                <label for="exampleInputEmail1">Quantity</label>
                <input type="text" name="Quantity" class="form-control" placeholder="Quantity">
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Type</label>
                <input type="text" name="Type" class="form-control" placeholder="Type">
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Price</label>
                <input type="text" name="Price" class="form-control" placeholder="Price">
            </div>
            <div class="form-group">
                <input name="FleImage" type="file" />
                <p class="help-block"></p>
            </div>

            <button type="submit" class="btn btn-primary" name="btnAdd">Add</button>
            <button type="button" class="btn btn-primary" onclick="window.location.href = 'index.php'"name="btnCncl">Cancel</button>

        </form></div></div>