package com.project.muhammedbas.tarifdefterim.SettingPage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.muhammedbas.tarifdefterim.R;

import java.util.HashMap;

public class AccountSetting extends AppCompatActivity {

    private EditText userName;
    private EditText userEmail;

    private DatabaseReference userDatabase;
    private String currentUser;

    private String userNameString;
    private String userEmailString;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_account);

        init();

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userNameString=dataSnapshot.child("name").getValue().toString();
                userEmailString=dataSnapshot.child("email").getValue().toString();

                userName.setHint(userNameString);
                userEmail.setHint(userEmailString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void init() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.accountsetting);

        userName=findViewById(R.id.userNameET);
        userEmail=findViewById(R.id.useremailET);

        currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        userDatabase=FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);

        progressDialog=new ProgressDialog(AccountSetting.this);
    }

    private void saveClick() {


        String clickusername=userName.getText().toString();
        if(TextUtils.isEmpty(userName.getText().toString())){

            Toast.makeText(getApplicationContext(),"Adınız boş bırakılamaz!",Toast.LENGTH_LONG).show();

        }else
        if(!clickusername.equals(userNameString)){

            progressDialog.setTitle("İsim Degişikligi");
            progressDialog.setMessage("Degişiklikler Kaydetiliyor Lütfen Bekleyin");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();

            HashMap username = new HashMap();
            username.put("name",clickusername);

            userDatabase.updateChildren(username).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(),"Adınız Başarılı Şekilde Degiştirildi",Toast.LENGTH_LONG).show();
                    finish();

                }
            });

        } else{

            Toast.makeText(getApplicationContext(),"Farklı bir isim deneyin .",Toast.LENGTH_LONG).show();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;

            case R.id.save:
                saveClick();
                return  true;
        }
        return super.onOptionsItemSelected(item);

    }



}
