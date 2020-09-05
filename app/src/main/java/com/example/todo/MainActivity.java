package com.example.todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ArrayList<String> items;
    public static final String Key_text = "item_text";
    public static final String Key_position = "item_position";
    public static final int EDIT_TEXT_CODE = 20;
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);
        items = new ArrayList<String>();
        LoadItem();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onLongClickListener(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                SaveItem();
                Toast.makeText(getApplicationContext(),"Item removed from the todolist sucessfully",Toast.LENGTH_SHORT).show();
            }
        };

        ItemsAdapter.OnClickListener clickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onClickListener(int position) {
                Log.d("MainActivities","Single Click at position"+position);
                Intent i = new Intent(MainActivity.this,EditItem.class);
                i.putExtra(Key_text,items.get(position));
                i.putExtra(Key_position,position);
                startActivityForResult(i,EDIT_TEXT_CODE);
            }
        };

        itemsAdapter = new ItemsAdapter(items,onLongClickListener,clickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               String additem = etItem.getText().toString();
               items.add(additem);
               itemsAdapter.notifyItemInserted(items.size()-1);
               etItem.setText("");
                Toast.makeText(getApplicationContext(),"Item added sucessfully",Toast.LENGTH_SHORT).show();
                SaveItem();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            String itemtext = data.getStringExtra(Key_text);
            int position = data.getExtras().getInt(Key_position);
            items.set(position,itemtext);
            itemsAdapter.notifyItemChanged(position);
            SaveItem();
            Toast.makeText(getApplicationContext(), "Item Updated sucessfully",Toast.LENGTH_SHORT).show();


        }else{
            Log.w("MainActivities","Unkown call to OnActivityResult");
        }

    }

    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");
    }
    private void LoadItem(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("Main activities","Fail to read items",e);
            items = new ArrayList<String>();
            e.printStackTrace();
        }

    }
    private void SaveItem(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("Main activities","Fail to save items",e);
            e.printStackTrace();
        }
    }
}