package com.dmitrysukhov.contactmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_CONTACT_REQUEST = 1;

    private ContactViewModel contactViewModel;
    private int visibility = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddContact = findViewById(R.id.button_add_contact);
        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, ADD_CONTACT_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ContactAdapter adapter = new ContactAdapter();
        recyclerView.setAdapter(adapter);

        contactViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.getInstance(this.getApplication())).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.setContacts(contacts);
            }
        });

    }


    private void deleteContactVisible() {
        ImageView buttonDelete = findViewById(R.id.imageButtonDelete);
        if (visibility == 0) {
            buttonDelete.setVisibility(View.VISIBLE);
            visibility = 1;
        } else {
            buttonDelete.setVisibility(View.GONE);
            visibility = 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                deleteContactVisible();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddContactActivity.EXTRA_NAME);
            String surname = data.getStringExtra(AddContactActivity.EXTRA_SURNAME);
            String email = data.getStringExtra(AddContactActivity.EXTRA_EMAIL);

            Contact contact = new Contact(name, surname, email);
            contactViewModel.insert(contact);

            Toast.makeText(this, "Contact is saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Contact is not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
