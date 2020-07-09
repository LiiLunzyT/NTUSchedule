package vn.ntu.edu.vothanhluan.ntuschedule.controller;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import vn.ntu.edu.vothanhluan.ntuschedule.models.AppDatabase;
import vn.ntu.edu.vothanhluan.ntuschedule.models.Homework;
import vn.ntu.edu.vothanhluan.ntuschedule.models.HomeworkDao;

public class HomeworkController implements IHomeworkController {
    HomeworkDao dao;

    public HomeworkController(Context context) {
        AppDatabase db;
        db = Room.databaseBuilder(context,
                AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();
        dao = db.getHomeworkDao();
    }

    @Override
    public void insertHomework(Homework... homeworks) {
        dao.insertHomework(homeworks);
    }

    @Override
    public void updateHomework(Homework... homeworks) {
        dao.updateHomework(homeworks);
    }

    @Override
    public void deleteHomework(Homework homework) {
        dao.delete(homework);
    }

    @Override
    public void emptyHomework() {
        dao.deleteAll();
    }

    @Override
    public List<Homework> getListHomeworks() {
        List<Homework> homeworks = dao.getAll();
        Collections.sort(homeworks, new Comparator<Homework>() {
            @Override
            public int compare(Homework h1, Homework h2) {
                return h1.getDeadline().compareTo(h2.getDeadline());
            }
        });
        return homeworks;
    }

    @Override
    public Homework findById(int id) {
        return dao.findById(id);
    }

    @Override
    public List<Homework> getListHomeworksInDay(int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String strDay = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR);
        Log.d("debug", strDay);
        Date curDay = null;
        try {
            curDay = sdf.parse(strDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Homework> homeworks = dao.getAll();
        List<Homework> rs = new ArrayList<>();
        for(Homework hm : homeworks) {
            long diff = hm.getDeadline().getTime() - curDay.getTime();
            if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 7) {
                Log.d("debug", "" + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                rs.add(hm);
            }
        }
        return rs;
    }

    @Override
    public int countDayLeft(int id) {
        Homework hm = dao.findById(id);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String strDay = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR);
        Log.d("debug", strDay);
        Date curDay = null;
        try {
            curDay = sdf.parse(strDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = hm.getDeadline().getTime() - curDay.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
