 
 ### İstifadə edilən gradle üçün kitabxanalar:
``` java 
dependencies {
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation  'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.google.firebase:firebase-core:20.1.2'
    implementation 'com.squareup.picasso:picasso:2.71828'
}
```

 https://newsapi.org/  web saytından api key  götürdükdən sonra app/src/main/res/values/strings.xml faylını tapıb içində API key əlavə etmək lazımdır
 ``` java
  <resources>
    <string name="app_name">News</string>
    <string name="txtSample">Test text type</string>
    <string name="api_key">test123api123key123</string>
 </resources>
```

###  İstifadə etdiyim versiyalar:
  Android studio Chipmunk 2021.2.1 Patch 2                                      
   Android Api 32 (Sv2) ( minSdk 21)                                                         
   Android studio JDK 11                                       
   Build gradle 7.1.3
  


