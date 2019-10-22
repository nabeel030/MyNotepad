package com.example.mynotepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText reg_username, reg_password, reg_password_confirm,password_hint;
    Button register_button;
    DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_username = findViewById(R.id.reg_username);
        reg_password = findViewById(R.id.reg_password);
        reg_password_confirm = findViewById(R.id.reg_password_confirm);
        password_hint = findViewById(R.id.password_hint);
        register_button = findViewById(R.id.register_button);

        dbhelper = new DatabaseHelper(this);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = reg_username.getText().toString();
                String pass = reg_password.getText().toString();
                String con_pass = reg_password_confirm.getText().toString();
                String pass_hint = password_hint.getText().toString();

                if (!name.isEmpty() && !pass.isEmpty() && !con_pass.isEmpty() && !pass_hint.isEmpty())
                {
                    if (pass.equals(con_pass)) {

                        boolean status = dbhelper.register_user(name, pass, pass_hint);

                        if (status)
                        {
                            Intent intent = new Intent(Register.this,Login.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Username Already Exists!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Password Not Matched!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"All Fields Required!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
