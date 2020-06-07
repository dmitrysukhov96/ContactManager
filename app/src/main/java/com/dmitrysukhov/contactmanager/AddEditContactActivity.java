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

public class AddEditContactActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.dmitrysukhov.contactmanager.EXTRA_ID";
    public static final String EXTRA_NAME = "com.dmitrysukhov.contactmanager.EXTRA_NAME";
    public static final String EXTRA_SURNAME = "com.dmitrysukhov.contactmanager.EXTRA_SURNAME";
    public static final String EXTRA_EMAIL = "com.dmitrysukhov.contactmanager.EXTRA_EMAIL";

    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        editTextName = findViewById(R.id.edit_text_name);
        editTextSurname = findViewById(R.id.edit_text_surname);
        editTextEmail = findViewById(R.id.edit_text_email);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Contact");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextSurname.setText(intent.getStringExtra(EXTRA_SURNAME));
            editTextEmail.setText(intent.getStringExtra(EXTRA_EMAIL));
        } else {
            setTitle("Add Contact");
        }
    }

    private void saveContact() {
        String name = editTextName.getText().toString();
        String surname = editTextSurname.getText().toString();
        String email = editTextEmail.getText().toString();

        if (name.trim().isEmpty() || surname.trim().isEmpty() || email.trim().isEmpty()) {
            Toast.makeText(this, "Please, insert data", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_SURNAME, surname);
        data.putExtra(EXTRA_EMAIL, email);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

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
        switch (item.getItemId()) {
            case R.id.save:
                saveContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
