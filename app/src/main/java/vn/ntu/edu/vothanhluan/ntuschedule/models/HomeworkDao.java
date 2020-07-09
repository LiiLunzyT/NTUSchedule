package vn.ntu.edu.vothanhluan.ntuschedule.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HomeworkDao {
    @Query("SELECT * FROM Homework")
    public List<Homework> getAll();

    @Insert
    public void insertHomework(Homework... homeworks);

    @Update
    public void updateHomework(Homework... homeworks);

    @Delete
    public void delete(Homework... homework);

    @Query("DELETE FROM Homework")
    public void deleteAll();

    @Query("SELECT * FROM Homework WHERE id = :id")
    public Homework findById(int id);
}
