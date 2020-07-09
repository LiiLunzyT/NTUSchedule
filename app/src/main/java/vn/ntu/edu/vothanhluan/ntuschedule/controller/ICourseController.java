package vn.ntu.edu.vothanhluan.ntuschedule.controller;

import java.util.List;

import vn.ntu.edu.vothanhluan.ntuschedule.models.Course;

public interface ICourseController {
    public void insertCourse(Course... courses);
    public void emptyCourse();
    public List<Course> getListCourses();
    public List<Course> getCourseByWeekDay(String weekday);
    public Course findById(int id);
}
