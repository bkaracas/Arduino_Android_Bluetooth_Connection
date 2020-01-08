package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button signIn;
    EditText editTextName, editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);
        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        signIn=(Button)findViewById(R.id.SignIn);
        Toast.makeText(getApplicationContext(),"Name:a Password:a",Toast.LENGTH_SHORT).show();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(editTextName.getText().toString().equals("a") && editTextPassword.getText().toString().equals("a")){
                    Intent i=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });


    }
}
