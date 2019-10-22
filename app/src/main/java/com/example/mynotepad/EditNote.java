package com.example.mynotepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditNote extends AppCompatActivity implements View.OnClickListener {

    EditText get_title, get_note;
    Button edit, update;
    DatabaseHelper helper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.noteupdate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.share: {
                Intent intent = getIntent();

                final String actualNote = intent.getStringExtra("textNote");
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, actualNote);
                startActivity(Intent.createChooser(shareIntent, "Share Using"));
                return true;
            }

            case R.id.delete:
            {
                Intent intent = getIntent();
                final String title = intent.getStringExtra("note");

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Caustion!");
                alert.setMessage("Are You Sure You Want To Delete This Note?");

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deleteNote(title);
                        Intent intent1 = new Intent(EditNote.this,NotesHome.class);
                        startActivity(intent1);
                    }
                });
                alert.create().show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        get_title = findViewById(R.id.get_title);
        get_note = findViewById(R.id.get_note);
        edit = findViewById(R.id.edit);
        update = findViewById(R.id.update);

        edit.setOnClickListener(this);
        update.setOnClickListener(this);

        Intent intent = getIntent();
        final String title = intent.getStringExtra("note");

        helper = new DatabaseHelper(this);

        Cursor cursor = helper.getSelectedNote();

        while (cursor.moveToNext())
        {
            if (title.equals(cursor.getString(2)))
            {
                get_title.setText(cursor.getString(2));
                get_note.setText(cursor.getString(3));
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v==edit)
        {
            get_note.setEnabled(true);
            edit.setVisibility(View.INVISIBLE);
            update.setVisibility(View.VISIBLE);
        }
        else
        {
            Intent intent = getIntent();
            String title = intent.getStringExtra("note");
            String note = get_note.getText().toString();
            helper.updateNote(title,note);
            update.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            get_note.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditNote.this, NotesHome.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
