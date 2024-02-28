package com.example.sleep_application.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Entity(primaryKeys = {"date", "startTime"})
public class SleepEntity {
    LocalDate date;

    Date startTime;



}
