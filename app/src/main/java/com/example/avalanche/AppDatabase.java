package com.example.avalanche;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ScoreDb.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ScoreDbDao scoreDbDao();
}
