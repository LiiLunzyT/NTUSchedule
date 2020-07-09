package vn.ntu.edu.vothanhluan.ntuschedule.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import vn.ntu.edu.vothanhluan.ntuschedule.R;
import vn.ntu.edu.vothanhluan.ntuschedule.models.Course;

public class CourseSection extends StatelessSection {
    String dateWeek;
    List<Course> courses;

    public CourseSection(String dateWeek, List<Course> courses) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.course_card)
                .headerResourceId(R.layout.course_section)
                .build());
        this.dateWeek = dateWeek;
        this.courses = courses;
    }

    @Override
    public int getContentItemsTotal() {
        return courses.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new CourseItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        CourseItemViewHolder itemHolder = (CourseItemViewHolder) holder;
        itemHolder.txtName.setText(courses.get(position).getName());
        itemHolder.txtTeacher.setText("Giảng viên: " + courses.get(position).getTeacher());
        itemHolder.txtTime.setText(courses.get(position).getTime());
        itemHolder.txtRoom.setText("Phòng học: " + courses.get(position).getRoom());
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        // return an empty instance of ViewHolder for the headers of this section
        return new CourseHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        CourseHeaderViewHolder headerHolder = (CourseHeaderViewHolder) holder;
        headerHolder.txtDayWeek.setText(dateWeek);
    }

    class CourseItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtTeacher, txtTime, txtRoom;

        public CourseItemViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtTeacher = itemView.findViewById(R.id.txtTeacher);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtRoom = itemView.findViewById(R.id.txtRoom);
        }
    }

    class CourseHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView txtDayWeek;

        public CourseHeaderViewHolder(View itemView) {
            super(itemView);

            txtDayWeek = (TextView) itemView.findViewById(R.id.txtDayWeek);
        }
    }
}
