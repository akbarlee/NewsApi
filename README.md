 
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

  ![api_template](https://github.com/akbarlee/NewsApi/assets/62420106/2c2e2ed8-148b-4c9b-85b8-915939304a0d)


### Login və registrasiya: 
  
 <kbd> <br> REGISTRATION <br> </kbd>  buttonuna click etdikdən sonra məlumatların düzgünlüyünün yoxlanılması və qeydə alınması üçün alttaki metoddan istifadə edilib: 
  ``` java
  private void registerNewUser() {
      pd = new ProgressDialog(Signup.this);
      pd.setMessage("Xahiş olunur gözləyin");
      pd.show();
      // Register pəncərəsində edit text'lərə yazılan  məlumatı bazaya göndərmək üçün aşağıdakı String dəyişənləri qeyd edirik
        String str_username = regUsername.getText().toString();
        String str_name = regName.getText().toString();
        String str_mail = regMail.getText().toString();
        String str_pass = regPass.getText().toString();
        String str_repass = regRePass.getText().toString();

        if(TextUtils.isEmpty(str_name) || TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_mail) || TextUtils.isEmpty(str_pass)|| TextUtils.isEmpty(str_repass ) ) {
            Toast.makeText(Signup.this, "Bütün sətirləri doldurun", Toast.LENGTH_SHORT).show();
        } 
        else if (str_pass.length()<6) {
            Toast.makeText(Signup.this, "Şifrə 6 simvoldan böyük olmalıdır.", Toast.LENGTH_SHORT).show();
        }
        else if(!str_pass.equals(str_repass)) {
            Toast.makeText(Signup.this, "Şifrə eyni olmalıdır.", Toast.LENGTH_SHORT);
        }
        else {
            // Üsttəki şərtlərin heç biri ödənmədiyi halda qeydiyyatı uğurla saxlamaq üçün
                 save(str_username , str_name , str_mail , str_pass);

        }
    }
```
 Class'ımızın içində  bu 2 dəyişən istifadə edilib: 
 ``` java
  private FirebaseAuth mAuth;
  DatabaseReference yol;
....
```
  
