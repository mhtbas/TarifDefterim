package com.project.muhammedbas.tarifdefterim;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RecipeFullScreen extends AppCompatActivity {

    private String personCount;
    private String preparationTime;
    private String cookingTime;
    private String category;
    private String recipeName;
    private String materials;
    private String preparation;
    private String recipeID;
    private String currentUser;
    private ImageView favIcon;
    private Drawable staryellow;
    private Drawable stargrey;

    private Boolean favornot=false;
    private DatabaseReference favDatabaseRef;
    private TextView recipename,recipeperson,recipecook
            ,recipetime,recipematerials,recipeprepa;

    private String newmaterials="";
    private String newpreparation="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_full_screen);

        init();

        /////////////////////////////materials alt alta yazdırma /////////////////////////
        String[] kelimematerials = null;
        kelimematerials = materials.split(",");
        materials(kelimematerials);
        ////////////////////////////////////////////////////////////////////////

        /////////////////////////////materials alt alta yazdırma /////////////////////////
        String[] kelimepreparation = null;
        kelimepreparation = preparation.split(",");
        preparation(kelimepreparation);
        ////////////////////////////////////////////////////////////////////////



        recipename.setText(recipeName);
        recipeperson.setText(personCount);
        recipecook.setText(cookingTime);
        recipetime.setText(preparationTime);
        recipematerials.setText(newmaterials);
        recipeprepa.setText(newpreparation);

        //////////////////////////////// fav check  ////////////////////////////////
        favDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(recipeID)){

                    favornot=true;
                    favIcon.setImageDrawable(staryellow);

                }else{

                    favornot=false;
                    favIcon.setImageDrawable(stargrey);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ///////////////////////////// fav icon click////////////////////////////////////////////////////////
        favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(favornot==false){

                    HashMap fav = new HashMap();
                    fav.put("time",ServerValue.TIMESTAMP);
                    fav.put("category",category);

                    favDatabaseRef.child(recipeID).setValue(fav).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                Toast.makeText(getApplicationContext(),"Favorilerinize Eklendi",Toast.LENGTH_LONG).show();
                                favIcon.setImageDrawable(staryellow);
                                favornot=true;
                            }
                        }
                    });

                }else{

                    favDatabaseRef.child(recipeID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(getApplicationContext(),"Favorilerinizden Çıkardınız",Toast.LENGTH_LONG).show();
                            favornot=false;
                            favIcon.setImageDrawable(stargrey);
                        }
                    });

                }

            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////

    }

    public void materials(String[] meta) {

        for (int i=0;i<meta.length;i++){
            System.out.println(meta[i]);

            newmaterials+="•  "+meta[i]+" \n";

        }


    }

    public void preparation(String[] meta) {

        for (int i=0;i<meta.length;i++){
            System.out.println(meta[i]);

            newpreparation+=(i+1)+"- "+meta[i]+" \n";

        }


    }

    private void init() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        personCount=intent.getStringExtra("personCount");
        preparationTime=intent.getStringExtra("preparationTime");
        cookingTime=intent.getStringExtra("cookingTime");
        category=intent.getStringExtra("category");
        recipeName=intent.getStringExtra("recipeName");
        materials=intent.getStringExtra("materials");
        preparation=intent.getStringExtra("preparation");
        recipeID=intent.getStringExtra("id");


        recipename=findViewById(R.id.recipename);
        recipeperson=findViewById(R.id.recipeperson);
        recipecook=findViewById(R.id.recipecooktime);
        recipetime=findViewById(R.id.recipetime);
        recipematerials=findViewById(R.id.recipemate);
        recipeprepa=findViewById(R.id.recipeprepa);
        favIcon=findViewById(R.id.favbutton);

        currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        favDatabaseRef=FirebaseDatabase.getInstance().getReference().child("favorite").child(currentUser);

        stargrey=getResources().getDrawable(R.drawable.stargrey);
        staryellow=getResources().getDrawable(R.drawable.staryellow);
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
