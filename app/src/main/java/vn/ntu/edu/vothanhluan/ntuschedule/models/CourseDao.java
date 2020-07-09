package vn.ntu.edu.vothanhluan.ntuschedule.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM Course")
    public List<Course> getAll();

    @Query("SELECT * FROM Course WHERE weekdays = :weekday")
    public List<Course> getCourseByWeekDay(String weekday);

    @Insert
    public void insertCourse(Course... courses);

    @Delete
    public void delete(Course course);

    @Query("DELETE FROM Course")
    public void deleteAll();

    @Query("SELECT * FROM Course WHERE id = :id")
    public Course findById(int id);
}
