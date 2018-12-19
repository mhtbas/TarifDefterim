package com.project.muhammedbas.tarifdefterim;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.project.muhammedbas.tarifdefterim.Utils.SearchList;

import java.util.ArrayList;

public class SearchScreen extends AppCompatActivity {

    private EditText searchEdit;
    private ImageView searchbutton;
    private String searchString;
    private String currentUser;
    private RecyclerView searchRecyclerView;
    private DatabaseReference mRecipeDatabase;
    private Spinner kategorispinner;
    private String kategorispinnerString="Seçiniz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);
        
        init();

        //////////////////// Search Click/////////////////////////////////

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(kategorispinnerString.equals("Seçiniz")){

                    Toast.makeText(getApplicationContext(),"Lütfen arama yapmak istediginiz kategoriyi seciniz",Toast.LENGTH_LONG).show();

                }else{

                    searchString=searchEdit.getText().toString();
                    search(searchString);
                }

            }
        });


        ///////////////////////////////////////////////////// Spinner Arraylist////////////////////////////////////////////////

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

        ArrayAdapter kategoriadapter = new ArrayAdapter(getApplicationContext(),R.layout.spinner_layout,kategori);
        kategoriadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        kategorispinner.setAdapter(kategoriadapter);

        ///////////////////////////////////////////////////// Spinner Select////////////////////////////////////////////////

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

    private void init() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Tarif Ara");

        searchEdit=findViewById(R.id.search_field);
        searchbutton=findViewById(R.id.search_btn);
        kategorispinner=findViewById(R.id.spinner);

        searchRecyclerView=findViewById(R.id.searchlist);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        searchRecyclerView.setHasFixedSize(true);

        currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRecipeDatabase=FirebaseDatabase.getInstance().getReference().child("recipes").child(currentUser);

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

    @Override
    public void onStart() {
        super.onStart();

        search("");
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public void search(String searchString){

        Query querysearch =mRecipeDatabase.child(kategorispinnerString).orderByChild("recipeName").startAt(searchString).endAt(searchString+"\uf8ff");

        FirebaseRecyclerOptions<SearchList> options = new FirebaseRecyclerOptions.Builder<SearchList>()
                .setQuery(querysearch,SearchList.class).build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<SearchList,SearchRecipeViewHolder>(options){

            @Override
            protected void onBindViewHolder(SearchRecipeViewHolder holder, int position,final SearchList model) {

                final String id=getRef(position).getKey();


                holder.setRecipeCookTime(model.getCookingTime());
                holder.setRecipename(model.getRecipeName());
                holder.setRecipePerson(model.getPersonCount());
                holder.setRecipePreTime(model.getPreparationTime());


                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(),RecipeFullScreen.class);
                        intent.putExtra("personCount",model.getPersonCount());
                        intent.putExtra("preparationTime",model.getPreparationTime());
                        intent.putExtra("cookingTime",model.getCookingTime());
                        intent.putExtra("category",model.getCategory());
                        intent.putExtra("recipeName",model.getRecipeName());
                        intent.putExtra("materials",model.getMaterials());
                        intent.putExtra("preparation",model.getPreparation());
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }
                });


            }

            @Override
            public SearchRecipeViewHolder onCreateViewHolder(ViewGroup parent, int i) {

                View recipe =LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.recipescreen_view,parent,false);

                return  new SearchRecipeViewHolder(recipe);

            }

        };

        searchRecyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    private class SearchRecipeViewHolder extends  RecyclerView.ViewHolder{

        private View mView;
        private TextView recipename,recipecooktime,recipepretime,recipeperson;


        public SearchRecipeViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            recipename = mView.findViewById(R.id.recipename);
            recipecooktime=mView.findViewById(R.id.cookingTime);
            recipepretime=mView.findViewById(R.id.preparationTime);
            recipeperson=mView.findViewById(R.id.personCount);


        }

        public void setRecipename(String recipenamestring){
            recipename.setText(recipenamestring);
        }

        public void setRecipeCookTime(String recipeCookTime){
            recipecooktime.setText(recipeCookTime);
        }

        public void setRecipePreTime(String recipePreTime){

            recipepretime.setText(recipePreTime);
        }

        public void setRecipePerson(String recipePerson){

            recipeperson.setText(recipePerson);
        }

    }





}
