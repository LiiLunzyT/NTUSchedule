package vn.ntu.edu.vothanhluan.ntuschedule.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.ntu.edu.vothanhluan.ntuschedule.R;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.HomeworkController;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.IHomeworkController;
import vn.ntu.edu.vothanhluan.ntuschedule.models.Homework;

public class HomeworkViewHolder extends RecyclerView.ViewHolder {

    IHomeworkController controller;
    TextView txtSubject, txtDayLeft, txtNote;

    public HomeworkViewHolder(@NonNull View itemView) {
        super(itemView);
        controller = new HomeworkController(itemView.getContext());
        txtSubject = itemView.findViewById(R.id.txtSubject);
        txtDayLeft = itemView.findViewById(R.id.txtDayLeft);
        txtNote = itemView.findViewById(R.id.txtNote);
    }

    public void bind(Homework homework) {
        int dayLeft = controller.countDayLeft(homework.getId());
        if(dayLeft <= 5) {
            txtDayLeft.setTextColor(Color.YELLOW);
        }
        if(dayLeft <= 3) txtDayLeft.setTextColor(Color.RED);

        txtSubject.setText(homework.getSubject());
        txtDayLeft.setText("Còn lại: " + dayLeft + "ngày");
        txtNote.setText(homework.getNote());
    }
}
