package com.dmitrysukhov.contactmanager;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_table")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String surname;

    private String email;

    private byte[] image;

    public Contact(String name, String surname, String email, byte[] image) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getImage() {
        return image;
    }
}
