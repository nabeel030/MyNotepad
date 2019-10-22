package com.example.mynotepad;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText username, password;
    TextView incorrect, register;
    Button login;
    CheckBox check;
    DatabaseHelper dbhelper;
    boolean status = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        incorrect = findViewById(R.id.incorrect);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        check = findViewById(R.id.check);

        incorrect.setVisibility(View.INVISIBLE);

        dbhelper = new DatabaseHelper(this);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v==register)
        {
            Intent intent = new Intent(this,Register.class);
            startActivity(intent);
        }
        else
        {
            String name = username.getText().toString();
            String pass = password.getText().toString();

            if (!name.isEmpty() && !pass.isEmpty())
            {
                Cursor cursor = dbhelper.getUsers();

                while (cursor.moveToNext())
                {
                    if (name.equals(cursor.getString(1)) && pass.equals(cursor.getString(2)))
                    {
                        status = true;
                        break;
                    }
                }

                if (status)
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("username",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name",name);
                    editor.apply();

                    Intent intent = new Intent(Login.this,NotesHome.class);
               //     intent.putExtra("username",name);
                    startActivity(intent);
                }
                else
                    incorrect.setVisibility(View.VISIBLE);
            }
            else
                Toast.makeText(getApplicationContext(),"Both Fields Required!", Toast.LENGTH_SHORT).show();
        }
    }
}
