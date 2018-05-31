  <?php session_start();

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
  
  class Database{

   protected $link, $result, $numrows;

   public function __construct(){

     $this->link = mysqli_connect("localhost", "root", "", "store_db");
   }

   public function disconnect(){
    mysqli_close($this->link);
   }

  public function dbQuery($sql){

   $this->result= mysqli_query($this->link, $sql);
   @$this->numrows = mysqli_num_rows($this->result);  
  }

 public function numRows(){
  return  $this->numrows;
 }

public function row(){

 $rows = array();   
 for($x=0; $x <$this->numrows; $x++){
  $rows[] = mysqli_fetch_array($this->result);
}
return $rows;
}

public function dbRecord(){
  return mysqli_fetch_array($this->result);
}


}

?>