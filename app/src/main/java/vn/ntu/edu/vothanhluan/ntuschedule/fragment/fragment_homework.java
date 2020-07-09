package vn.ntu.edu.vothanhluan.ntuschedule.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import vn.ntu.edu.vothanhluan.ntuschedule.R;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.HomeworkController;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.IHomeworkController;
import vn.ntu.edu.vothanhluan.ntuschedule.models.Homework;

public class fragment_homework extends Fragment {
    IHomeworkController controller;

    FloatingActionButton fbtnAdd;
    RecyclerView rvListHomework;
    HomeworkAdapter adapter;

    List<Homework> listHomeworks;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_homework, container, false);
        addViews(root);
        return root;
    }

    public void addViews(View view) {
        controller = new HomeworkController((MainActivity)getActivity());
        fbtnAdd = view.findViewById(R.id.fbtnAdd);
        fbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("mode", "Thêm bài tập");
                HomeworkDialog dialog = new HomeworkDialog();
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "Thêm bài tập");
            }
        });

        TextView txtEmptyHomework = view.findViewById(R.id.txtEmptyHW);
        rvListHomework = view.findViewById(R.id.rvListHomework);
        rvListHomework.setLayoutManager(new LinearLayoutManager(getActivity()));
        listHomeworks = controller.getListHomeworks();
        if(listHomeworks.size() > 0) {
            txtEmptyHomework.setVisibility(View.GONE);
        }
        adapter = new HomeworkAdapter(listHomeworks);
        rvListHomework.setAdapter(adapter);
    }
}