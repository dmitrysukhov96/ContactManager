package com.dmitrysukhov.contactmanager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactAdapter extends ListAdapter<Contact, ContactAdapter.ContactHolder> {
    private OnItemClickListener listener;

    ContactAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Contact> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(Contact oldItem, Contact newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Contact oldItem, Contact newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getSurname().equals(newItem.getSurname()) &&
                    oldItem.getEmail().equals(newItem.getEmail());
        }
    };

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact currentContact = getItem(position);
        String namePlusSurname = currentContact.getName() + " " + currentContact.getSurname();
        holder.textViewNameSurname.setText(namePlusSurname);
        holder.textViewEmail.setText(currentContact.getEmail());
    }

    Contact getContactAt(int position) {
        return getItem(position);
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        private TextView textViewNameSurname;
        private TextView textViewEmail;

        ContactHolder(View itemView) {
            super(itemView);
            textViewNameSurname = itemView.findViewById(R.id.cardView_name_surname);
            textViewEmail = itemView.findViewById(R.id.cardView_email);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}