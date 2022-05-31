package com.example.avalanche;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDbDao { // dao=database access object
    @Query("SELECT * FROM scoredb")
    List<ScoreDb> getAll();

    @Query("SELECT * FROM scoredb ORDER BY score DESC LIMIT 10")
    List<ScoreDb> getTopTen();

    @Insert
    void insertAll(ScoreDb... scoreDbs);

    @Delete
    void delete(ScoreDb scoreDb);
}
