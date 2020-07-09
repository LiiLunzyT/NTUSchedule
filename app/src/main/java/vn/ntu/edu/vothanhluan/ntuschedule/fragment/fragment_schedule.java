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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.ntu.edu.vothanhluan.ntuschedule.R;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.CourseController;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.HomeworkController;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.ICourseController;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.IHomeworkController;
import vn.ntu.edu.vothanhluan.ntuschedule.models.Course;
import vn.ntu.edu.vothanhluan.ntuschedule.models.Homework;

public class fragment_schedule extends Fragment {
    RecyclerView rvSchedule, rvHomework;
    CourseAdapter courseAdapter;
    HomeworkAdapter homeworkAdapter;
    ICourseController courseController;
    IHomeworkController homeworkController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        addViews(view);
        return view;
    }

    private void addViews(View view) {
        courseController = new CourseController((MainActivity)getActivity());
        homeworkController = new HomeworkController((MainActivity)getActivity());
        rvSchedule = view.findViewById(R.id.rvSchedule);
        rvSchedule.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHomework = view.findViewById(R.id.rvHomework);
        rvHomework.setLayoutManager(new LinearLayoutManager(getActivity()));

        TextView txtEmptySC = view.findViewById(R.id.txtEmptySC);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtFreeday = view.findViewById(R.id.txtFreeday);
        TextView txtFreeHomework = view.findViewById(R.id.txtFreeHomework);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d("debug", "" + day);
        List<Course> courses = courseController.getCourseByWeekDay("" + (day));
        List<Course> allCourses = courseController.getListCourses();

        if(allCourses.size() > 0) {
            txtEmptySC.setVisibility(View.GONE);
            if(courses.size() > 0) {
                txtFreeday.setVisibility(View.GONE);
            }
        } else {
            txtTitle.setVisibility(View.GONE);
            txtFreeday.setVisibility(View.GONE);
        }
        courseAdapter = new CourseAdapter(courses);
        rvSchedule.setAdapter(courseAdapter);


        List<Homework> homeworks = null;
        try {
            homeworks = homeworkController.getListHomeworksInDay(7);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(homeworks.size() > 0) {
            txtFreeHomework.setVisibility(View.GONE);
        }
        homeworkAdapter = new HomeworkAdapter(homeworks);
        rvHomework.setAdapter(homeworkAdapter);
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtTeacher, txtTime, txtRoom;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtTeacher = itemView.findViewById(R.id.txtTeacher);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtRoom = itemView.findViewById(R.id.txtRoom);
        }

        public void bind(Course course) {
            txtName.setText(course.getName());
            txtTeacher.setText("Giảng viên: " + course.getTeacher());
            txtTime.setText(course.getTime());
            txtRoom.setText("Phòng học: " + course.getRoom());
        }
    }

    public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder> {
        List<Course> listCourses = new ArrayList<>();

        public CourseAdapter(List<Course> listCourses) {
            this.listCourses = listCourses;
        }

        @NonNull
        @Override
        public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.course_card, parent, false);
            return new CourseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
            holder.bind(listCourses.get(position));
        }

        @Override
        public int getItemCount() {
            return listCourses.size();
        }
    }
}