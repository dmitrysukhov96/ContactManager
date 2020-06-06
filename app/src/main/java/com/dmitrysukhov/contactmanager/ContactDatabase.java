package com.dmitrysukhov.contactmanager;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Contact.class, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    private static ContactDatabase instance;
    public abstract ContactDao contactDao();

    public static synchronized ContactDatabase getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class, "contact_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private ContactDao contactDao;

        private PopulateDbAsyncTask (ContactDatabase db){
            contactDao=db.contactDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactDao.insert(new Contact("Name 1", "Surname 1", "email 1",null));
            contactDao.insert(new Contact("Name 2", "Surname 2", "email 2",null));
            contactDao.insert(new Contact("Name 3", "Surname 3", "email 3",null));
            return null;
        }
    }
}
