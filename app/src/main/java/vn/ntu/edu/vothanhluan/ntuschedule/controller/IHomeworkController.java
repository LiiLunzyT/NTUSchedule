package vn.ntu.edu.vothanhluan.ntuschedule.controller;

import java.text.ParseException;
import java.util.List;

import vn.ntu.edu.vothanhluan.ntuschedule.models.Homework;

public interface IHomeworkController {
    public void insertHomework(Homework... homeworks);
    public void updateHomework(Homework... homeworks);
    public void deleteHomework(Homework homework);
    public void emptyHomework();
    public List<Homework> getListHomeworks();
    public Homework findById(int id);
    public List<Homework> getListHomeworksInDay(int day) throws ParseException;
    public int countDayLeft(int id);
}
