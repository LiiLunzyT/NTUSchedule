package vn.ntu.edu.vothanhluan.ntuschedule.controller;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

import vn.ntu.edu.vothanhluan.ntuschedule.models.AppDatabase;
import vn.ntu.edu.vothanhluan.ntuschedule.models.Course;
import vn.ntu.edu.vothanhluan.ntuschedule.models.CourseDao;

public class CourseController implements ICourseController {
    CourseDao dao;

    public CourseController(Context context) {
        AppDatabase db;
        db = Room.databaseBuilder(context,
                AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();
        dao = db.getCourseDao();
    }

    @Override
    public void insertCourse(Course... courses) {
        dao.insertCourse(courses);
    }

    @Override
    public void emptyCourse() {
        dao.deleteAll();
    }

    @Override
    public List<Course> getListCourses() {
        return dao.getAll();
    }

    @Override
    public List<Course> getCourseByWeekDay(String weekday) {
        return dao.getCourseByWeekDay(weekday);
    }


    @Override
    public Course findById(int id) {
        return dao.findById(id);
    }
}
