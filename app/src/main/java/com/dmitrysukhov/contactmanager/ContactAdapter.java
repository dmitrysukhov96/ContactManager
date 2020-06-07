package com.dmitrysukhov.contactmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    private List<Contact> contacts = new ArrayList<>();
    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact currentContact = contacts.get(position);
        String text1 = currentContact.getName()+" "+currentContact.getSurname();
        holder.textViewNameSurname.setText(text1);
        holder.textViewEmail.setText(currentContact.getEmail());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<Contact> contacts){
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    class ContactHolder extends RecyclerView.ViewHolder{
        private TextView textViewNameSurname;
        private TextView textViewEmail;

        public ContactHolder(View itemView) {
            super(itemView);
            textViewNameSurname=itemView.findViewById(R.id.cardView_name_surname);
            textViewEmail=itemView.findViewById(R.id.cardView_email);
        }
    }
}
