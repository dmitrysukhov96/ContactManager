package com.dmitrysukhov.contactmanager;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_CONTACT_REQUEST = 1;
    public static final int EDIT_CONTACT_REQUEST = 2;

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
                Intent intent = new Intent(MainActivity.this, AddEditContactActivity.class);
                startActivityForResult(intent, ADD_CONTACT_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ContactAdapter adapter = new ContactAdapter();
        recyclerView.setAdapter(adapter);

        //tut shto-to mozhet byt
        contactViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.getInstance(this.getApplication())).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.submitList(contacts);
            }
        });

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            contactViewModel.delete(adapter.getContactAt(viewHolder.getAdapterPosition()));
            Toast.makeText(MainActivity.this, "Contact deleted", Toast.LENGTH_SHORT).show();
        }
    }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Contact contact) {
            Intent intent = new Intent(MainActivity.this, AddEditContactActivity.class);
            intent.putExtra(AddEditContactActivity.EXTRA_ID, contact.getId());
            intent.putExtra(AddEditContactActivity.EXTRA_NAME, contact.getName());
            intent.putExtra(AddEditContactActivity.EXTRA_SURNAME, contact.getSurname());
            intent.putExtra(AddEditContactActivity.EXTRA_EMAIL, contact.getEmail());
            startActivityForResult(intent, EDIT_CONTACT_REQUEST);
        }
    });
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditContactActivity.EXTRA_NAME);
            String surname = data.getStringExtra(AddEditContactActivity.EXTRA_SURNAME);
            String email = data.getStringExtra(AddEditContactActivity.EXTRA_EMAIL);
            Contact contact = new Contact(name, surname, email);
            contactViewModel.insert(contact);
            Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_CONTACT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditContactActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Contact can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(AddEditContactActivity.EXTRA_NAME);
            String surname = data.getStringExtra(AddEditContactActivity.EXTRA_SURNAME);
            String email = data.getStringExtra(AddEditContactActivity.EXTRA_EMAIL);
            Contact contact = new Contact(name, surname, email);
            contact.setId(id);
            contactViewModel.update(contact);
            Toast.makeText(this, "Contact updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Contact not saved", Toast.LENGTH_SHORT).show();
        }
    }




    private void deleteContacts() {
        ImageView buttonDelete = findViewById(R.id.imageButtonDelete);
        if (visibility == 0) {
            visibility = 1;
        } else {
            visibility = 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                deleteContacts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
