package vn.ntu.edu.vothanhluan.ntuschedule.models;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import vn.ntu.edu.vothanhluan.ntuschedule.models.Course;
import vn.ntu.edu.vothanhluan.ntuschedule.models.CourseDao;

@Database(entities = {Course.class, Homework.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CourseDao getCourseDao();
    public abstract HomeworkDao getHomeworkDao();
}
