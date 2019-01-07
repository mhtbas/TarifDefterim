package com.project.muhammedbas.tarifdefterim.SettingPage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.project.muhammedbas.tarifdefterim.R;

public class PasswordChange extends AppCompatActivity {

    private EditText password,passwordagain;
    private Button save;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_password_change);

        init();

        /////////////////////////////save button ////////////////////////////
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setTitle("Şifre Degişikligi");
                progressDialog.setMessage("Degişiklikler Kaydetiliyor Lütfen Bekleyin");
                progressDialog.setCanceledOnTouchOutside(true);
                progressDialog.show();


                if(TextUtils.isEmpty(password.getText())||TextUtils.isEmpty(passwordagain.getText())){

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Lütfen Şifre Alanını Doldurun",Toast.LENGTH_LONG).show();

                }else{

                    String passwordString = password.getText().toString();
                    String passwordAgainString = passwordagain.getText().toString();

                    if(passwordString.equals(passwordAgainString)){

                        FirebaseAuth.getInstance().getCurrentUser().updatePassword(passwordString).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){

                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Şifre Degiştirme Başarılı",Toast.LENGTH_LONG).show();

                                    finish();

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        });

                    }else{

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Şifreler Eşleşmiyor",Toast.LENGTH_LONG).show();

                    }




                }
            }
        });


    }

    public void init(){

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.passwordchange);

        password=findViewById(R.id.password);
        passwordagain=findViewById(R.id.passwordAgain);
        save=findViewById(R.id.saveButton);

        progressDialog=new ProgressDialog(PasswordChange.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                this.finish();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }
}
