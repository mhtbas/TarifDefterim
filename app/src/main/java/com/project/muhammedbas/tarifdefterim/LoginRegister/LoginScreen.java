package com.project.muhammedbas.tarifdefterim.LoginRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.muhammedbas.tarifdefterim.HomeScreen;
import com.project.muhammedbas.tarifdefterim.R;

public class LoginScreen extends AppCompatActivity {

    private EditText name;
    private EditText pass;
    private Button login;
    private TextView register;

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        init();

        ////////////////////////////////////////// register //////////////////////////////////////////

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterScreen.class);
                startActivity(intent);
            }
        });


        ////////////////////////////////////////// login //////////////////////////////////////////

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(name.getText().toString())) {
                    Toast.makeText(getApplicationContext(),R.string.login_email, Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                if (TextUtils.isEmpty(pass.getText().toString())) {
                    Toast.makeText(getApplicationContext(), R.string.login_pass, Toast.LENGTH_SHORT).show();
                    return;
                }
                else{

                    login.setEnabled(false);
                    register.setEnabled(false);

                    String namestring = name.getText().toString();
                    String passstring = pass.getText().toString();

                    progressDialog.setMessage(getString(R.string.login_dialog));
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(namestring,passstring).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                            login.setEnabled(true);
                            register.setEnabled(true);
                            progressDialog.dismiss();
                        }
                    });


                }
            }
        });


    }

    public void init(){

        name=findViewById(R.id.usernametext);
        pass=findViewById(R.id.passwordtext);
        login=findViewById(R.id.loginbutton);
        register=findViewById(R.id.registerbutton);

        progressDialog=new ProgressDialog(LoginScreen.this);
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            Intent  intent = new Intent(LoginScreen.this,HomeScreen.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
