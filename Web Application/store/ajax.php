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
 
include "Database.php";
$db = new Database();
$id = $_GET['id'];

// Buy..........................
if($id > 0 ){
    $sql = "SELECT id, quantity FROM phones_tb WHERE id='$id'";
    $row = $db->dbRecord($db->dbQuery($sql));
    $quantity = $row[1];
    $quantity_buy = $quantity - 1;
    if ($quantity != 0) {
        $sql = "UPDATE phones_tb SET quantity='$quantity_buy', date_modified = now() WHERE id='$id'";
        $db->dbQuery($sql); 
    }
}
// Delete.......................
elseif ($id < 0) {
    $id = $id * -1;
    $sql = "SELECT id, image FROM phones_tb WHERE id='$id'";
    $images = $db->dbRecord($db->dbQuery($sql));
    $image = $images[1];
    // Delete image from folder img
    unlink("img/$image");
    $sql = "DELETE FROM phones_tb WHERE id='$id'";
    $db->dbQuery($sql); 
 }


// Auto refresh..................
$sql = "SELECT id, quantity, price, type, image, date_modified FROM phones_tb LIMIT 0,4";
$rs = $db->dbQuery($sql);
$rows = $db->row($rs); 
foreach ($rows as $row) {
    ?>

    <div class="proudct">
        <img src="img/<?= $row[4] ?>">
        <h2>Quantity : <?= $row[1] ?></h2>
        <p>Price : <?= $row[2] ?> $</p>
        <p>Type : <?= $row[3] ?></p>
        <label hidden="true"><?= $row[0] ?></label>
        <button class="buttonBuy" type="submit" onclick="ajax(<?= $row[0] ?>)" name="buy">Buy</button>
        <a href="edit.php?id=<?= $row[0] ?>"><button class="buttonBuy" type="submit" name="edit">Edit</button></a>
        <button class="buttonBuy" type="submit" onclick="ajax(<?= $row[0] * -1 ?>)" name="buy">Delete</button>
    </div>

    <?php
}
?>