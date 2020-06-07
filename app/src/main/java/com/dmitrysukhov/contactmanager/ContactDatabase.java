package com.dmitrysukhov.contactmanager;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

@Database(entities = {Contact.class}, version = 1)
abstract class ContactDatabase extends RoomDatabase {

    private static ContactDatabase instance;

    abstract ContactDao contactDao();

    static synchronized ContactDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class, "contact_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDao contactDao;

        private PopulateDbAsyncTask(ContactDatabase db) {
            contactDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactDao.insert(new Contact("Name", "Surname", "example@email.com"));
            contactDao.insert(new Contact("Dmitry", "Sukhov", "dmitrysukhov96@gmail.com"));
            contactDao.insert(new Contact("First", "App", "mvvm@contactmanager.com"));
            return null;
        }
    }
}
