package com.project.muhammedbas.tarifdefterim;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddRecipes extends AppCompatActivity {

    Spinner kackişilikspinner,hazırlanmaspinner,kategorispinner;
    EditText tarifadıET,malzemelerET,hazırlanısET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Tarif Ekle");

        init();
        spinnerAdapters();



    }

    public void init(){

        kackişilikspinner=findViewById(R.id.kackişilik);
        hazırlanmaspinner=findViewById(R.id.hazırlanma);
        kategorispinner=findViewById(R.id.kategori);
        hazırlanısET=findViewById(R.id.hazırlanısEditText);
        malzemelerET=findViewById(R.id.malzemelerEditText);
        tarifadıET=findViewById(R.id.tarifadı);

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


    }
}
