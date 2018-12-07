package com.project.muhammedbas.tarifdefterim;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AddRecipes extends AppCompatActivity {

    Spinner kackişilikspinner,hazırlanmaspinner,kategorispinner,pişirmespinner;
    EditText tarifadıET,malzemelerET,hazırlanısET;

    private DatabaseReference mUserDatabase;
    private DatabaseReference mRecipes;
    private String currentUser;

    private String kackişilikspinnerString="Seçiniz";
    private String hazırlanmaspinnerString="Seçiniz";
    private String pişirmespinnerString="Seçiniz";
    private String kategorispinnerString="Seçiniz";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Tarif Ekle");

        init();
        spinnerAdapters();
        selectedCount();


    }

    public void init(){

        kackişilikspinner=findViewById(R.id.kackişilik);
        hazırlanmaspinner=findViewById(R.id.hazırlanma);
        kategorispinner=findViewById(R.id.kategori);
        pişirmespinner=findViewById(R.id.pişirme);
        hazırlanısET=findViewById(R.id.hazırlanısEditText);
        malzemelerET=findViewById(R.id.malzemelerEditText);
        tarifadıET=findViewById(R.id.tarifadı);


        currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserDatabase=FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);
        mRecipes=FirebaseDatabase.getInstance().getReference().child("recipes");

        progressDialog=new ProgressDialog(AddRecipes.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                this.finish();
                return true;

            case R.id.save:
                saveClick();
                return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveClick(){

        if (TextUtils.isEmpty(tarifadıET.getText().toString())) {
            Toast.makeText(getApplicationContext(),"Lütfen doldurun", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (TextUtils.isEmpty(malzemelerET.getText().toString())) {
            Toast.makeText(getApplicationContext(),"Lütfen doldurun", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (TextUtils.isEmpty(hazırlanısET.getText().toString())) {
            Toast.makeText(getApplicationContext(),"Lütfen doldurun", Toast.LENGTH_SHORT).show();
            return;
        }else{


            if(kackişilikspinnerString.equals("Seçiniz")){

                Toast.makeText(getApplicationContext(),"Kaç kişilik seçiniz", Toast.LENGTH_SHORT).show();


            }else if(hazırlanmaspinnerString.equals("Seçiniz")){

                Toast.makeText(getApplicationContext(),"Hazırlanma süresini seçiniz", Toast.LENGTH_SHORT).show();


            }else if(pişirmespinnerString.equals("Seçiniz")){

                Toast.makeText(getApplicationContext(),"Pişirme süresini seçiniz", Toast.LENGTH_SHORT).show();


            }else if(kategorispinnerString.equals("Seçiniz")){

                Toast.makeText(getApplicationContext(),"Lütfen Kategori seçiniz", Toast.LENGTH_SHORT).show();

            }else{


                progressDialog.setMessage("Tarifiniz Ekleniyor");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                String tarifAdıString =tarifadıET.getText().toString();
                String malzemelerString =malzemelerET.getText().toString();
                String hazırlanışString =hazırlanısET.getText().toString();

                Log.d("StringAll",kackişilikspinnerString+hazırlanmaspinnerString+pişirmespinnerString+kategorispinnerString);

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                HashMap add = new HashMap();
                add.put("personCount",kackişilikspinnerString);
                add.put("preparationTime",hazırlanmaspinnerString);
                add.put("cookingTime",pişirmespinnerString);
                add.put("category",kategorispinnerString);
                add.put("personcount",kackişilikspinnerString);
                add.put("recipeName",tarifAdıString);
                add.put("materials",malzemelerString);
                add.put("preparation",hazırlanışString);

                DatabaseReference pushAdd = mRecipes.child(currentUser).push();
                final String push_id = pushAdd.getKey();

                mRecipes.child(currentUser).child(push_id).setValue(add).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(),"Tarif Ekleme Başarılı",Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();

                    }
                });














            }
        }

    }


    public void spinnerAdapters(){

        //////////////////////////////////////////////////////////

        ArrayList<String> kackisilik = new ArrayList<>();
        kackisilik.add("Seçiniz");
        kackisilik.add("1-2  kişilik");
        kackisilik.add("2-4  kişilik");
        kackisilik.add("4-6  kişilik");
        kackisilik.add("6-8  kişilik");
        kackisilik.add("8-10 kişilik");

        ArrayAdapter kackisilikadapter = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,kackisilik);
        kackisilikadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kackişilikspinner.setAdapter(kackisilikadapter);

        //////////////////////////////////////////////////////////

        ArrayList<String> hazırlanma = new ArrayList<>();
        hazırlanma.add("Seçiniz");
        hazırlanma.add("5-10 dk");
        hazırlanma.add("15-20 dk");
        hazırlanma.add("25-30 dk");
        hazırlanma.add("35-40 dk");
        hazırlanma.add("45-50 dk");
        hazırlanma.add("55-60 dk");
        hazırlanma.add("1 saat");
        hazırlanma.add("1 saat 30 dk");
        hazırlanma.add("2 saat");
        hazırlanma.add("2 saat 30 dk");
        hazırlanma.add("3 saat");


        ArrayAdapter hazırlanmaadapter = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,hazırlanma);
        hazırlanmaspinner.setAdapter(hazırlanmaadapter);

        //////////////////////////////////////////////////////////

        ArrayList<String> kategori = new ArrayList<>();
        kategori.add("Seçiniz");
        kategori.add("Ana Yemekler");
        kategori.add("Salatalar");
        kategori.add("Çorbalar");
        kategori.add("Aparatif Yemekler");
        kategori.add("Hamur İşleri");
        kategori.add("Tatlilar");
        kategori.add("Bebek Yemekleri");
        kategori.add("Diyet Yemekleri");
        kategori.add("Kahvaltılıklar");
        kategori.add("Deniz Ürünleri");
        kategori.add("Atıştırmalıklar");
        kategori.add("İçeçekler");

        ArrayAdapter kategoriadapter = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,kategori);
        kategorispinner.setAdapter(kategoriadapter);


        //////////////////////////////////////////////////////////

        ArrayList<String> pişirme = new ArrayList<>();
        pişirme.add("Seçiniz");
        pişirme.add("5-10 dk");
        pişirme.add("15-20 dk");
        pişirme.add("25-30 dk");
        pişirme.add("35-40 dk");
        pişirme.add("45-50 dk");
        pişirme.add("55-60 dk");
        pişirme.add("1 saat");
        pişirme.add("1 saat 30 dk");
        pişirme.add("2 saat");
        pişirme.add("2 saat 30 dk");
        pişirme.add("3 saat");


        ArrayAdapter pişirmeadapter = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,pişirme);
        pişirmespinner.setAdapter(pişirmeadapter);

    }

    public  void selectedCount(){

        kackişilikspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                    kackişilikspinnerString="Seçiniz";
                }
                if(position==1){

                    kackişilikspinnerString="1-2  kişilik";
                }
                if(position==2){

                    kackişilikspinnerString="2-4  kişilik";
                }
                if(position==3){

                    kackişilikspinnerString="4-6  kişilik";

                }
                if(position==4){

                    kackişilikspinnerString="6-8  kişilik";
                }
                if(position==5){

                    kackişilikspinnerString="8-10 kişilik";

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        hazırlanmaspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                    hazırlanmaspinnerString="Seçiniz";
                }
                if(position==1){

                    hazırlanmaspinnerString="5-10 dk";
                }
                if(position==2){

                    hazırlanmaspinnerString="15-20 dk";
                }
                if(position==3){

                    hazırlanmaspinnerString="25-30 dk";

                }
                if(position==4){

                    hazırlanmaspinnerString="35-40 dk";
                }
                if(position==5){

                    hazırlanmaspinnerString="45-50 dk";

                }
                if(position==6){
                    hazırlanmaspinnerString="55-60 dk";

                }
                if(position==7){

                    hazırlanmaspinnerString="1 saat";
                }
                if(position==8){

                    hazırlanmaspinnerString="1 saat 30 dk";
                }
                if(position==9){

                    hazırlanmaspinnerString="2 saat";
                }
                if(position==10){

                    hazırlanmaspinnerString="2 saat 30 dk";
                }
                if(position==11){

                    hazırlanmaspinnerString="3 saat";
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pişirmespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                    pişirmespinnerString="Seçiniz";
                }
                if(position==1){

                    pişirmespinnerString="5-10 dk";
                }
                if(position==2){

                    pişirmespinnerString="15-20 dk";
                }
                if(position==3){

                    pişirmespinnerString="25-30 dk";

                }
                if(position==4){

                    pişirmespinnerString="35-40 dk";
                }
                if(position==5){

                    pişirmespinnerString="45-50 dk";

                }
                if(position==6){
                    pişirmespinnerString="55-60 dk";

                }
                if(position==7){

                    pişirmespinnerString="1 saat";
                }
                if(position==8){

                    pişirmespinnerString="1 saat 30 dk";
                }
                if(position==9){

                    pişirmespinnerString="2 saat";
                }
                if(position==10){

                    pişirmespinnerString="2 saat 30 dk";
                }
                if(position==11){

                    pişirmespinnerString="3 saat";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        kategorispinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                    kategorispinnerString="Seçiniz";
                }
                if(position==1){

                    kategorispinnerString="Ana Yemekler";
                }
                if(position==2){

                    kategorispinnerString="Salatalar";
                }
                if(position==3){

                    kategorispinnerString="Çorbalar";

                }
                if(position==4){

                    kategorispinnerString="Aparatif Yemekler";
                }
                if(position==5){

                    kategorispinnerString="Hamur İşleri";

                }
                if(position==6){
                    kategorispinnerString="Tatlilar";

                }
                if(position==7){

                    kategorispinnerString="Bebek Yemekleri";
                }
                if(position==8){

                    kategorispinnerString="Diyet Yemekleri";
                }
                if(position==9){

                    kategorispinnerString="Kahvaltılıklar";
                }
                if(position==10){

                    kategorispinnerString="Deniz Ürünleri";
                }
                if(position==11){

                    kategorispinnerString="Atıştırmalıklar";
                }
                if(position==12){

                    kategorispinnerString="İçeçekler";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
