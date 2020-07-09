package vn.ntu.edu.vothanhluan.ntuschedule.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "Homework")
public class Homework {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;

    @NonNull
    String subject;

    @NonNull
    String note;

    @NonNull
    Date deadline;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getSubject() {
        return subject;
    }

    public void setSubject(@NonNull String subject) {
        this.subject = subject;
    }

    @NonNull
    public String getNote() {
        return note;
    }

    public void setNote(@NonNull String note) {
        this.note = note;
    }

    @NonNull
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(@NonNull Date deadline) {
        this.deadline = deadline;
    }
}
