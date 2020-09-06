package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItem extends AppCompatActivity {

    EditText etItem;
    Button btnSave;
    Button btnCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etItem = findViewById(R.id.etItem);
        btnSave = findViewById(R.id.btnSave);
        btnCom = findViewById(R.id.btnCom);

        getSupportActionBar().setTitle("Edit Item");


        btnCom.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();

                String completed = etItem.getText()+" - Completed";
                intent1.putExtra(MainActivity.Key_text,completed);
                intent1.putExtra(MainActivity.Key_position,getIntent().getExtras().getInt(MainActivity.Key_position));

                setResult(RESULT_OK,intent1);

                finish();
            }
        });


        etItem.setText(getIntent().getStringExtra(MainActivity.Key_text));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.Key_text,etItem.getText().toString());
                intent.putExtra(MainActivity.Key_position,getIntent().getExtras().getInt(MainActivity.Key_position));

                setResult(RESULT_OK,intent);
                finish();

            }
        });




    }
}