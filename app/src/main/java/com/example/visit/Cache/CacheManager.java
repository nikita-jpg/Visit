package com.example.visit.Cache;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import com.example.visit.Person;

import java.util.List;

public class CacheManager
{
    private TextDb textDb;
    private TextElementDao textElementDao;
    private Context context;

    public CacheManager(Context context)
    {
        this.context = context;
        textDb = Room.databaseBuilder(context, TextDb.class,"database")
                .allowMainThreadQueries()
                .build();
        textElementDao = textDb.textElementDao();
    }

    public List<Person> getAllText()
    {
        return textElementDao.getAll();
    }

    public long addPerson(Person person)
    {
        long id = textElementDao.insert(person);
        /*
        int a = (int) getNumberOfRecords();
        long min = (int) textElementDao.getMinId();
        if(getNumberOfRecords()>=100)
        {
            textElementDao.delete(getTextById(min));
        }

         */
        return id;
    }

    public Person getTextById(long id)
    {
        Person person = textElementDao.getElementById(id);
        return person;
    }

    public long getNumberOfRecords()
    {
        return textElementDao.getNumberOfRecords();
    }


                    //Текстовая Бд
    @Database(entities =  {Person.class}, version = 1,exportSchema = false)
    public abstract static class TextDb extends RoomDatabase
    {
        public abstract TextElementDao textElementDao();
    }


    @Dao
    public interface TextElementDao
    {
        @Query("SELECT * FROM myTable")
        List<Person> getAll();

        @Query("SELECT * FROM myTable WHERE id = :id")
        Person getElementById(long id);

        @Query("SELECT COUNT(id) FROM myTable")
        long getNumberOfRecords();

        @Query("SELECT MIN(id) FROM myTable")
        long getMinId();

        @Insert
        long insert(Person person);
        @Update
        void update(Person person);
        @Delete
        void delete(Person person);
    }
}
