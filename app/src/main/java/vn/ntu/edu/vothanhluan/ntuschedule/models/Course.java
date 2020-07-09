package vn.ntu.edu.vothanhluan.ntuschedule.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Course")
public class Course {
    @PrimaryKey
    @NonNull
    String id;

    @NonNull
    String code;

    @NonNull
    String group;

    @NonNull
    String name;

    @NonNull
    String credit;

    @NonNull
    String _class;

    @NonNull
    String weekdays;

    @NonNull
    String time;

    @NonNull
    String teacher;

    @NonNull
    String room;

    @NonNull
    String dayStart;

    public Course() {
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }

    @NonNull
    public String getGroup() {
        return group;
    }

    public void setGroup(@NonNull String group) {
        this.group = group;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getCredit() {
        return credit;
    }

    public void setCredit(@NonNull String credit) {
        this.credit = credit;
    }

    @NonNull
    public String get_class() {
        return _class;
    }

    public void set_class(@NonNull String _class) {
        this._class = _class;
    }

    @NonNull
    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(@NonNull String weekdays) {
        this.weekdays = weekdays;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }

    @NonNull
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(@NonNull String teacher) {
        this.teacher = teacher;
    }

    @NonNull
    public String getRoom() {
        return room;
    }

    public void setRoom(@NonNull String room) {
        this.room = room;
    }

    @NonNull
    public String getDayStart() {
        return dayStart;
    }

    public void setDayStart(@NonNull String dayStart) {
        this.dayStart = dayStart;
    }
}


