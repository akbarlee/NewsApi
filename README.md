 
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

Qeyd: Aşağıdaki birləşdirilmiş görüntülərdə göründüyü kimi News API ödənişsiz versiyada simvol və sorğu limiti tətbiq edir.

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

  Aşağıdaki ```save(...)``` metodunun gördüyü işlərə baxaq:

  İlk iş ``` mAuth.createUserWithEmailAndPassword(mail , pass) ``` metodunu çağırırıq. Bu metod mail və pass'ın qəbul etdiyi qiymətləri yoxlayaraq Firebase Realtime Database içində istifadəçi hesabı yaradır. Bu metod ``` Task<AuthResult> ``` obyekti return edərək qeydiyyat prosesinin uğurlu olub olmamağını təyin edir.

  Ardınca ```  .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() ``` metodunu çağırırıq. Bu metod ilə 1 dənə listener çağıraraq həm Uğurlu həm də Uğursuz calling'ləri idarə etmək mümkündür. Praktiki olaraq desək bu metod OnSuccessListener və  OnFailureListener üçün eyni anda iş görmüş olur.

```onComplete(@NonNull Task<AuthResult> task)``` metodunda, register prosesinin nəticəsini test edirik. Əgər ```task.isSuccessful()```'dirsə , qeydiyyat uğurludur. Deyilsə , uğursuzdur.

 Class'ımızın içində  bu 2 dəyişən istifadə edilib: 
 ``` java
  private FirebaseAuth mAuth;
  DatabaseReference yol;
```
  Save metodu: 
``` java
private void save (String username , String name , String mail , String pass) {
      
            mAuth.createUserWithEmailAndPassword(mail , pass)
                    .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                              //  Yeni yaradılmış hesabın referansını alırıq
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            //  Hesabın uid'sini alırıq
                            String userID = firebaseUser.getUid();
                            // Realtime bazamızda "İstifadeciler" düyümünün içində uid adlı alt düyümdə istifadəçi məlumatları saxlamaq üçün
                            yol = FirebaseDatabase.getInstance().getReference().child("İstifadeciler").child(userID);
                            // Birdən çox data göndərmək üçün HashMap istifadə etməliyik.
                            HashMap<String , Object> hashMap = new HashMap<>();
                            hashMap.put("id" , userID);
                            hashMap.put("username" , username.toLowerCase());
                            hashMap.put("name" , name);
                            hashMap.put("mail" , mail);
                            hashMap.put("pass" , pass);
                            hashMap.put("bio" , "");
                            hashMap.put("photourl" , "https://firebasestorage.googleapis.com/v0/b/android-c01c1.appspot.com/o/placeholder.jpg?alt=media&token=5131ce0a-75ec-4644-8928-d6b2aff04509");

                            yol.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                  if (task.isSuccessful()) {
                                                                                      pd.dismiss();
                                                                                      Intent intent = new Intent(Signup.this , main_page.class);
                                                                                      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                                         startActivity(intent);
                                                                                  }
                                                                            }
                                                                        }
                            );
                        }
                        else {
                             pd.dismiss();
                                Toast.makeText(Signup.this, "Bu mail və ya şifrə ilə qeydiyyat mümkün olmadı.", Toast.LENGTH_LONG).show();
                        }
                        }
                    });
     }
```
 ## Firebase iearxiyası: 

![firebase](https://github.com/akbarlee/NewsApi/assets/62420106/69a8dfe0-067a-41d9-bf3e-7ba2e8c3a839)


