<html>

<!-- 
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
-->

<!-- 
/**
 * Created by Dev Ahmed Mahmoud on 25/5/2018
 * email : dev.ahmed.m@gmail.com
 * phone : +9700597503338
 */
-->

    <head>
        <title></title>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body class="body">
        <a name="top"></a>
        <!--topFooter-->
        <footer class="topFooter">
            <nav>
                <ul>
                    <li><a href=""><img src="img/a.png"></a></li>
                    <li><a href=""><img src="img/e.png"></a></li>
                    <li><a href=""><img src="img/l.png"></a></li>
                    <li><a href=""><img src="img/p.png"></a></li>
                    <li><a href="#">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                    <li><a href="#">تسجيل الدخول</a></li>
                    <li><a href="#">انشاء حساب</a></li>  
                </ul>
            </nav>
        </footer>

        <!--logo and Main menu-->
        <header class="mainHeader">
         <!--<img src="img/logo.gif">-->
            <h1><a href="index.html">Phones.Store</a></h1>

        </header>

        <!--Menu Sarach and Basket--> 
        <header class="sarchandbasket">
            <nav>
                <ul>
                    <li></li>
                    <li><a href="add.php"><button class="buttonItem" type="reset" name="btnreset">Add Phone</button></a></li>
                    <li><input type="image" name="fimage" src="img/basket.png" alt="submite" width="50" height="30"></li>
                    <li><input type="search" name="search" placeholder="Search..." width="50" height="20"></li>
                    <li><input type="image" name="fimage" src="img/search2.PNG" alt="submite" width="30" height="30"></li>
                </ul>
            </nav>
        </header>
        <!--Content (1) 4 photos in 4 div (Phones)-->
        <div class="mainContent" id="get">
            
        </div>  


        <!-- footer (2) Contact Us, About Us, Help, Shipping and handling-->
        <footer class="mainFooter1">
            <div class="shipping">
                <h2>نشحن الى</h2>
                <ul>
                    <li>فلسطين</li>
                    <li>لبنان</li>
                    <li>مصر</li>
                    <li>السعودية</li>
                </ul>
            </div>
            <div class="shipping">
                <h2>خدمة العملاء</h2>
                <ul>
                    <li><a href="">اتصل بنا</a></li>
                    <li><a href="">الشحن و التسليم</a></li>
                    <li><a href="">الخصوصية و الأمان</a></li>
                    <li><a href="">وسائل الشراء و العروض</a></li>
                </ul>
            </div>
            <div class="shipping">
                <h2>المساعدة</h2>
                <ul>
                    <li><a href="">كيفية الشراء</a></li>
                    <li><a href="">تحديث بيانات الحساب</a></li>
                    <li><a href="">دليل المقاسات</a></li>
                    <li><a href="">الأسئلة المتكررة</a></li>
                </ul>
            </div>

            <!-- Social Media -->
            <h3>... تابعنا</h3>
            <link rel="stylesheet" href="Social Media/follow_us.css" type="text/css" />
        </div>
        <div class="clearbreak"></div>
        <div id="jsn-social-icons">
            <ul>
                <li class="facebook">
                    <a href="رابط Facebook" title="Facebook" target="_blank">
                        Facebook</a>
                </li>
                <li class="twitter">
                    <a href="رابط Twitter" title="Twitter" target="_blank">
                        Twitter</a>  
                </li>
                <li class="googleplus">
                    <a href="رابط googleplus" title="googleplus" target="_blank">
                        googleplus</a>
                </li>
                <li class="linkedin">
                    <a href="رابط linkedin" title="linkedin" target="_blank">
                        linkedin</a>
                </li>
            </ul>
        </div>

    </footer>

    <!-- footer (1) Copyright Store Phones 2018 -->
    <footer class="mainFooter2">
        <p> جميع حقوق النشر محفوظة &copy; 2018 Phones.Store</p>
    </footer> 

    <script> 
  function ajax(id){
    var req = new XMLHttpRequest();
    req.onreadystatechange = function(){
      if(req.readyState == 4 && req.status == 200){
        document.getElementById('get').innerHTML = req.responseText;
      } 
    }
    req.open("GET","ajax.php?id=" + id,true);
    req.send();
  }
  setInterval(function(){ajax(0)},300); // It was 5

</script>

</body>
</html>