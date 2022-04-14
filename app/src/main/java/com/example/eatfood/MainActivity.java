package com.example.eatfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    Button getSignin , getSignup ;
    TextInputEditText enterMail , enterPass;
    TextView  txt_account , forgotPassword , subtext , txt1 , regRePass;


     @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        subtext = findViewById(R.id.subtext);
        txt1 = findViewById(R.id.txt1);
           txt_account = findViewById(R.id.txt_account);
         forgotPassword=  findViewById(R.id.forgotPassword);
         enterMail =  findViewById(R.id.regMail);
         enterPass = findViewById(R.id.enterPass);
         regRePass = findViewById(R.id.regRePass);
         mAuth = FirebaseAuth.getInstance();
        getSignup = (Button) findViewById(R.id.getSignup);
        getSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , Signup.class));
                finish();
            }
        });


       getSignin = (Button) findViewById(R.id.getSignin);
        getSignin.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Giriş edilir...");
                progressDialog.show();
                // firebase ile elaqelendirme kodlari asagidakilardir
                String inputMail = enterMail.getText().toString();
                String inputPass = enterPass.getText().toString();

                if(TextUtils.isEmpty(inputMail) || TextUtils.isEmpty(inputPass)) {
                    Toast.makeText(MainActivity.this, "Bütün xanaları doldurun", Toast.LENGTH_LONG).show();
                }
                else  {
                    // Girish etmek uchun
                    mAuth.signInWithEmailAndPassword(inputMail,inputPass)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        DatabaseReference yol = FirebaseDatabase.getInstance().getReference().child("İstifadeciler").child(mAuth.getCurrentUser().getUid());
                                        yol.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                progressDialog.dismiss();
                                                Intent intent = new Intent(MainActivity.this, main_page.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                        progressDialog.dismiss();
                                            }
                                        });
                                    }
                                    else {
                                        progressDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "Giris ugursuz oldu", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }

        });
    }
}