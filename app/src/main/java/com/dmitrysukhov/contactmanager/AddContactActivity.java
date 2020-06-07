package com.dmitrysukhov.contactmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    public static final String EXTRA_NAME ="com.dmitrysukhov.contactmanager.EXTRA_NAME";
    public static final String EXTRA_SURNAME ="com.dmitrysukhov.contactmanager.EXTRA_SURNAME";
    public static final String EXTRA_EMAIL ="com.dmitrysukhov.contactmanager.EXTRA_EMAIL";

    private EditText name;
    private EditText surname;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        name = findViewById(R.id.edit_text_name);
        surname = findViewById(R.id.edit_text_surname);
        email = findViewById(R.id.edit_text_email);
        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Contact");
    }

    private void saveContact(){
        String nameStr = name.getText().toString();
        String surnameStr = surname.getText().toString();
        String emailStr = email.getText().toString();

        if(nameStr.trim().isEmpty() || surnameStr.trim().isEmpty() || emailStr.trim().isEmpty()){
            Toast.makeText(this, "Please, insert data", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, nameStr);
        data.putExtra(EXTRA_SURNAME, surnameStr);
        data.putExtra(EXTRA_EMAIL, emailStr);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                saveContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
    }
}}
