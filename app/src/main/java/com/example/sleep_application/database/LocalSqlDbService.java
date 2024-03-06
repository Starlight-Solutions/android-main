package com.example.sleep_application.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.sleep_application.database.dao.SleepDao;
import com.example.sleep_application.database.entity.SleepEntity;

@Database(entities = {SleepEntity.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class LocalSqlDbService extends RoomDatabase {
    public abstract SleepDao sleepDao();

}