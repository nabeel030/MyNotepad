package com.example.mynotepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewNote extends AppCompatActivity {

    EditText title, note;
    Button add;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        title = findViewById(R.id.title);
        note = findViewById(R.id.note);
        add = findViewById(R.id.add);

        db = new DatabaseHelper(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heading = title.getText().toString();
                String text = note.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("username",MODE_PRIVATE);
                String name = sharedPreferences.getString("name","");


                if (!heading.isEmpty() && !text.isEmpty())
                {
                    boolean status = db.insertNote(name,heading,text);

                    if (status) {
                        Intent intent = new Intent(NewNote.this,NotesHome.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Both Fields required",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
