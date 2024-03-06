package com.example.sleep_application.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(primaryKeys = {"date", "finishTime"})
public class SleepEntity {
    @NotNull
    LocalDate date;

    @NotNull
    LocalTime finishTime;

    long duration;

    public SleepEntity(@NotNull LocalDate date, @NotNull LocalTime finishTime, long duration) {
        this.date = date;
        this.finishTime = finishTime;
        this.duration = duration;
    }

    @NonNull
    public LocalDate getDate() {
        return date;
    }

    public void setDate(@NonNull LocalDate date) {
        this.date = date;
    }

    @NonNull
    public LocalTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(@NonNull LocalTime finishTime) {
        this.finishTime = finishTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
