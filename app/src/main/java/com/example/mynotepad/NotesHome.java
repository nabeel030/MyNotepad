package com.example.mynotepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesHome extends AppCompatActivity {

    TextView recent, number, empty;
    RecyclerView myrecycler;
    FloatingActionButton write;
    DatabaseHelper dbhelper;
    ArrayList<MyNote> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_home);

        recent = findViewById(R.id.recent);
        number = findViewById(R.id.number);
        empty = findViewById(R.id.empty);
        myrecycler = findViewById(R.id.myrecycler);
        write = findViewById(R.id.write);

        myrecycler.setLayoutManager(new LinearLayoutManager(this));
        dbhelper = new DatabaseHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("username",MODE_PRIVATE);

        String username = sharedPreferences.getString("name","");

        mList = dbhelper.getNotes(username);

        if (mList.size()==0)
        {
            empty.setVisibility(View.VISIBLE);
            number.setText(String.valueOf(0));
        }
        else {
            number.setText(String.valueOf(mList.size()));
            myrecycler.setAdapter(new MyAdapter(mList, this));
        }

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesHome.this,NewNote.class);
                startActivity(intent);
            }
        });
    }
}
