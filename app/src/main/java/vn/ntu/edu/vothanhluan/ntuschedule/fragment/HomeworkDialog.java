package vn.ntu.edu.vothanhluan.ntuschedule.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vn.ntu.edu.vothanhluan.ntuschedule.R;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.HomeworkController;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.IHomeworkController;
import vn.ntu.edu.vothanhluan.ntuschedule.models.Homework;

public class HomeworkDialog extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {
    String mode;
    Homework homework;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.homework_dialog, null);

        final IHomeworkController controller = new HomeworkController(getActivity());
        final EditText edtSubject = view.findViewById(R.id.edtSubject);
        final EditText edtDeadline = view.findViewById(R.id.edtDeadline);
        final EditText edtNote = view.findViewById(R.id.edtNote);

        Bundle bundle = getArguments();
        mode = bundle.getString("mode");
        if(mode.equals("Thêm bài tập")) {
            homework = new Homework();
        } else if(mode.equals("Sửa bài tập")) {
            homework = controller.findById(bundle.getInt("id"));
            edtSubject.setText(homework.getSubject());
            edtNote.setText(homework.getNote());
            edtDeadline.setText(sdf.format(homework.getDeadline()));
        }

        ImageView imgCalendar = view.findViewById(R.id.imgCalendar);
        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        String day = dayOfMonth + "/" + (month+1) + "/" + year;
                        try {
                            edtDeadline.setText(sdf.format(sdf.parse(day)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),listener
                        ,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        builder.setView(view)
                .setTitle(mode)
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Date date = new Date();
                        try {
                            date = sdf.parse(edtDeadline.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        homework.setSubject(edtSubject.getText().toString());
                        homework.setNote(edtNote.getText().toString());
                        homework.setDeadline(date);
                        switch (mode) {
                            case "Thêm bài tập":
                                controller.insertHomework(homework);
                                break;
                            case "Sửa bài tập": {
                                controller.updateHomework(homework);
                            }
                        }
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new fragment_homework()).commit();
                    }
                })
                .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        if(mode.equals("Sửa bài tập")) {
                builder.setNeutralButton("Xoá", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    controller.deleteHomework(homework);
                }
            });
        }
        return builder.create();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}
