package com.project.muhammedbas.tarifdefterim.LoginRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.project.muhammedbas.tarifdefterim.R;
import com.project.muhammedbas.tarifdefterim.Utils.Users;

public class RegisterScreen extends AppCompatActivity {

    private EditText name,pass,email;
    private Button register;
    private TextView login;

    private FirebaseAuth mAuth;
    private String currentUser;
    private ProgressDialog progressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginScreen.class);
                startActivity(intent);
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(email.getText().toString())||(TextUtils.isEmpty(pass.getText().toString()))
                        ||(TextUtils.isEmpty(name.getText().toString()))){
                    Toast.makeText(getApplicationContext(),"Lütfen Boşlukları Doldurun", Toast.LENGTH_SHORT).show();

                }else
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"Lütfen e-mail giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                if (TextUtils.isEmpty(pass.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Lütfen şifrenizi doldurunuz", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                if(TextUtils.isEmpty(name.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Lütfen isminizi doldurunuz", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{

                    login.setEnabled(false);
                    register.setEnabled(false);

                    final String namestring =name.getText().toString();
                    final String passstring=pass.getText().toString();
                    final String emailstring=email.getText().toString();

                    progressDialog.setMessage("Giriş Yapılıyor..");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.createUserWithEmailAndPassword(emailstring,passstring).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();

                                Users student = new Users(namestring,emailstring);

                                FirebaseDatabase.getInstance().getReference().child("users").child(currentUser).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            progressDialog.dismiss();

                                            Intent intent = new Intent(getApplicationContext(),LoginScreen.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();

                            login.setEnabled(true);
                            register.setEnabled(true);

                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }

    private void init() {

        name=findViewById(R.id.usernametext);
        pass=findViewById(R.id.passwordtext);
        email=findViewById(R.id.emailtext);
        login=findViewById(R.id.loginbutton);
        register=findViewById(R.id.registerbutton);

        mAuth=FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(RegisterScreen.this);

    }
}
