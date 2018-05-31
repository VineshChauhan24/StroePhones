# StroePhones
Sync Realm and MySQL Database in Android Application

Android Application Store Phones With The App Server.
This Code demonstrates how to synchronize the local Realm Database with the remote server MySQL database.
 
 # Watch on YouTube
 [How to Sync Realm and MySQL Database in Android Application on YouTube](https://youtu.be/CMtVn_73jR8)
 
 ![cover](https://user-images.githubusercontent.com/32569345/40809888-ade9d7e4-6534-11e8-8eca-fd7fd01487c3.png)
 
 # Web Screenshots
 
 ![store phones](https://user-images.githubusercontent.com/32569345/40811736-15d36126-653b-11e8-9986-0954f7749bf4.PNG)

 
 # App Screenshots
 
![store phones](https://user-images.githubusercontent.com/32569345/40810256-f7a08602-6535-11e8-8164-fb3b449c00de.gif)

# Database (Mysql)

| Names         | Type          | Extra |
| ------------- |:-------------:| -----:|
| id            | int(11)       | AUTO_INCREMENT       |
| quantity      | int(11)       |                      |
| price         | float         |                      |
| type          | varchar(50)   |                      |
| image         | varchar(250)  |                      |
| date_modified | datetime      |                      |

# Database (Realm)

| Names         | Type          | Extra |
| ------------- |:-------------:| -----:|
| id            | int           | get(), set()      |
| quantity      | int           | get(), set()      |
| price         | double        | get(), set()      |
| type          | String        | get(), set()      |
| imageName     | String        | get(), set()      |
| image         | byte[]        | get(), set()      |
| date_modified | Date          | get(), set()      |


# let's go to start in code

```android
dependencies {
        classpath 'com.android.tools.build:gradle:3.1.2'
        classpath "io.realm:realm-gradle-plugin:5.1.0"

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
```

