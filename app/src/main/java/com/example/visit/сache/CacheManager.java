package com.example.visit.сache;

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
import com.example.visit.TeamEvent;

import java.util.List;

public class CacheManager
{
    private static final int versionBd = 1;
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


    //Person
    public List<Person> PersonGetAllText()
    {
        return textElementDao.personGetAll();
    }
    public long personAdd(Person person)
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
    public void personEdit(Person person)
    {
        textElementDao.update(person);
    }
    public void personDelete(Person person) {
        textElementDao.delete(person);
    }
    public Person PersonGetTextById(long id)
    {
        Person person = textElementDao.personGetById(id);
        return person;
    }
    public long PersonGetNumberOfRecords()
    {
        return textElementDao.personGetNumberOfRecords();
    }



    //TeamEvent
    public List<TeamEvent> teamGetAllText()
    {
        return textElementDao.teamGetAll();
    }
    public long teamAdd(TeamEvent event)
    {
        long id = textElementDao.insert(event);
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
    public void teamEdit(TeamEvent event)
    {
        textElementDao.update(event);
    }
    public TeamEvent teamGetTextById(long id)
    {
        TeamEvent event = textElementDao.teamGetById(id);
        return event;
    }
    public long teamGetNumberOfRecords()
    {
        return textElementDao.teamGetNumberOfRecords();
    }


    //Текстовая Бд
    @Database(entities =  {Person.class, TeamEvent.class}, version = versionBd,exportSchema = false)
    public abstract static class TextDb extends RoomDatabase
    {
        public abstract TextElementDao textElementDao();
    }


    @Dao
    public interface TextElementDao
    {

        //Person
        @Query("SELECT * FROM persons")
        List<Person> personGetAll();

        @Query("SELECT * FROM persons WHERE id = :id")
        Person personGetById(long id);

        @Query("SELECT COUNT(id) FROM persons")
        long personGetNumberOfRecords();

        @Query("SELECT MIN(id) FROM persons")
        long personGetMinId();

        @Insert
        long insert(Person person);
        @Update
        void update(Person person);
        @Delete
        void delete(Person person);



        //TeamEvent
        @Query("SELECT * FROM events")
        List<TeamEvent> teamGetAll();

        @Query("SELECT * FROM events WHERE id = :id")
        TeamEvent teamGetById(long id);

        @Query("SELECT COUNT(id) FROM events")
        long teamGetNumberOfRecords();

        @Query("SELECT MIN(id) FROM events")
        long teamGetMinId();

        @Insert
        long insert(TeamEvent teamEvent);
        @Update
        void update(TeamEvent teamEvent);
        @Delete
        void delete(TeamEvent teamEvent);

    }
}
