package com.project.muhammedbas.tarifdefterim.SettingPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.project.muhammedbas.tarifdefterim.LoginRegister.LoginScreen;
import com.project.muhammedbas.tarifdefterim.R;
import com.project.muhammedbas.tarifdefterim.Utils.ListItemSetting;

import java.util.ArrayList;

public class SettingPage extends AppCompatActivity {

    private ImageView backArrow;
    private ListView  settingList;
    private ArrayList<ListItemSetting> settingArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page);

        init();

        ///// ///// ///// ///// ///// ///// ///// ///// back arrow  ///// ///// ///// ///// ///// /////
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //////////////////////////////////listview click /////////////////////////////
        settingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                }else
                if(position==1){

                }else
                if(position==2){

                }else
                if(position==3){

                    // Alert builder

                    AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SettingPage.this);

                    CharSequence options[] = new CharSequence[]{"Çıkış","İptal"};

                    builder.setTitle("Çıkış yapmak istediginize eminmisiniz?");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            if(i==0){

                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getApplicationContext(),LoginScreen.class);
                                startActivity(intent);

                            }

                            if(i==1){

                                finish();


                            }

                        }
                    });

                    ////////////////////////////////////////////////////////

                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                }
            }
        });
    }

    private void init() {

        backArrow=findViewById(R.id.backArrow);
        settingList=findViewById(R.id.listView);

        settingArrayList=new ArrayList();

        settingArrayList.add(new ListItemSetting("Hesap Ayarları"));
        settingArrayList.add(new ListItemSetting("Şifre Degiştir"));
        settingArrayList.add(new ListItemSetting("Dil Seçimi"));
        settingArrayList.add(new ListItemSetting("Çıkış"));

        ListAdapter listAdapter = new ListAdapter(settingArrayList);
        settingList.setAdapter(listAdapter);

    }


    public class ListAdapter extends BaseAdapter {

        private ArrayList<ListItemSetting> listItems;

        public ListAdapter(ArrayList<ListItemSetting> listItems) {

            this.listItems = listItems;

        }

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public Object getItem(int position) {
            return listItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return listItems.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.setting_page_listview, null);

            TextView name = view.findViewById(R.id.textname);

            final ListItemSetting listItem = listItems.get(position);
            name.setText(listItem.getName());

            return view;
        }
    }
}
