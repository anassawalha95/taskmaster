package com.example.taskmaster;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    // order items in database in descending order
    // return a list of tasks
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    List<TaskModel> getall();

    @Insert
    void saveNewTask(TaskModel task);

    @Update
    void updateTask(TaskModel task);

    @Delete
    void deleteTask(TaskModel task);
}