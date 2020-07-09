package vn.ntu.edu.vothanhluan.ntuschedule.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import vn.ntu.edu.vothanhluan.ntuschedule.R;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.CourseController;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.ICourseController;
import vn.ntu.edu.vothanhluan.ntuschedule.models.Course;

public class fragment_timetable extends Fragment {

    String[] WeekDays = {"Chủ Nhật", "Thứ Hai","Thứ Ba","Thứ Tư","Thứ Năm","Thứ Sáu","Thứ Bảy"};

    RecyclerView rvListCourse;

    ICourseController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        addViews(view);
        return view;
    }
    private void addViews(View view) {
        controller = new CourseController((MainActivity)getActivity());
        rvListCourse = view.findViewById(R.id.rvListCourse);
        rvListCourse.setLayoutManager(new LinearLayoutManager(getActivity()));

        TextView txtEmptyTB = view.findViewById(R.id.txtEmptyTB);
        if(controller.getListCourses().size() > 0) {
            txtEmptyTB.setVisibility(View.GONE);
        }

        SectionedRecyclerViewAdapter adapter = new SectionedRecyclerViewAdapter();

        for(int i = 2; i <= 8; i++) {
            List<Course> list = controller.getCourseByWeekDay("" + i);
            if(list.size() > 0) {
                adapter.addSection(new CourseSection(WeekDays[i-1], list));
            }
        }

        rvListCourse.setAdapter(adapter);
    }

//    public class CourseViewHolder extends RecyclerView.ViewHolder {
//        TextView txtName, txtTeacher, txtTime, txtRoom;
//
//        public CourseViewHolder(@NonNull View itemView) {
//            super(itemView);
//            txtName = itemView.findViewById(R.id.txtName);
//            txtTeacher = itemView.findViewById(R.id.txtTeacher);
//            txtTime = itemView.findViewById(R.id.txtTime);
//            txtRoom = itemView.findViewById(R.id.txtRoom);
//        }
//
//        public void bind(Course course) {
//            Log.d("debug", course.toString());
//            txtName.setText(course.getName());
//            txtTeacher.setText("Giảng viên: " + course.getTeacher());
//            txtTime.setText(course.getTime());
//            txtRoom.setText("Phòng học: " + course.getRoom());
//        }
//    }
//
//    public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder> {
//        List<Course> listCourses = new ArrayList<>();
//
//        public CourseAdapter(List<Course> listCourses) {
//            this.listCourses = listCourses;
//        }
//
//        @NonNull
//        @Override
//        public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater inflater = getLayoutInflater();
//            View view = inflater.inflate(R.layout.course_card, parent, false);
//            return new CourseViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
//            holder.bind(listCourses.get(position));
//        }
//
//        @Override
//        public int getItemCount() {
//            return listCourses.size();
//        }
//    }
}