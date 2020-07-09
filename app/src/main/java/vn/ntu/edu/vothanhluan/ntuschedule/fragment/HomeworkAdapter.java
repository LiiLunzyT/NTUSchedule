package vn.ntu.edu.vothanhluan.ntuschedule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.ntu.edu.vothanhluan.ntuschedule.R;
import vn.ntu.edu.vothanhluan.ntuschedule.models.Homework;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkViewHolder> {
    List<Homework> listHomework = new ArrayList<>();

    public HomeworkAdapter(List<Homework> listHomework) {
        this.listHomework = listHomework;
    }

    @NonNull
    @Override
    public HomeworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.homework_view, parent, false);
        return new HomeworkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkViewHolder holder, final int position) {
        holder.bind(listHomework.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("mode", "Sửa bài tập");
                bundle.putInt("id", listHomework.get(position).getId());
                HomeworkDialog dialog = new HomeworkDialog();
                dialog.setArguments(bundle);
                dialog.show(((MainActivity) view.getContext()).getSupportFragmentManager(), "Sửa bài tập");
            }
        });

    }

    @Override
    public int getItemCount() {
        return listHomework.size() ;
    }
}