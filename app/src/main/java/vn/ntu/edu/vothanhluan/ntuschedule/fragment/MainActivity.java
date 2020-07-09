package vn.ntu.edu.vothanhluan.ntuschedule.fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import vn.ntu.edu.vothanhluan.ntuschedule.R;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.CourseController;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.HomeworkController;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.ICourseController;
import vn.ntu.edu.vothanhluan.ntuschedule.controller.IHomeworkController;
import vn.ntu.edu.vothanhluan.ntuschedule.models.Course;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        LoginDialog.LoginDialogListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Button btnLogin;

    ICourseController controller;
    IHomeworkController homeworkController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new CourseController(this);
        homeworkController = new HomeworkController(this);

        addViews(savedInstanceState);
    }

    private void addViews(Bundle savedInstanceState) {
        drawer = findViewById(R.id.main_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginDialog loginDialog = new LoginDialog();
                loginDialog.show(getSupportFragmentManager(), "Đăng nhập");
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if( savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new fragment_schedule()).commit();
            navigationView.setCheckedItem(R.id.nav_schedule);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_schedule()).commit();
                break;
            case R.id.nav_timetable:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_timetable()).commit();
                break;
            case R.id.nav_homework:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_homework()).commit();
                break;
            case R.id.nav_noti:
                startAlarm(true, true);
            case R.id.nav_clear:
                eraseDatabase();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void eraseDatabase() {
        controller.emptyCourse();
        homeworkController.emptyHomework();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new fragment_schedule()).commit();
        navigationView.setCheckedItem(R.id.nav_schedule);
    }

    private void startAlarm(boolean isNotification, boolean isRepeat) {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        // SET TIME HERE
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);

        myIntent = new Intent(MainActivity.this,AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0);


        if(!isRepeat)
            manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000,pendingIntent);
        else
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    @Override
    public void loadData(final String username, final String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://ntu-time-table.herokuapp.com/api";

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Đang truy xuất dữ liệu");
        progress.setMessage("Có thể sẽ hơi lâu, mọi người chờ một tí");
        progress.setCancelable(true); // disable dismiss by tapping outside of the dialog
        progress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("debug", response);
                            if(!response.equals("[]")) {
                                controller.emptyCourse();
                                Log.d("debug", response);
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Gson gson = new Gson();
                                    Course course = gson.fromJson(jsonObject.toString(), Course.class);
                                    controller.insertCourse(course);
                                }
                                Toast.makeText(MainActivity.this,
                                        "Lấy thời khoá biểu thành công",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this,
                                        "Lấy thời khoá biểu thấy bại\n Có vẻ bạn đã nhập sai tài khoản",
                                        Toast.LENGTH_SHORT).show();
                            }
                            progress.dismiss();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new fragment_schedule()).commit();
                            navigationView.setCheckedItem(R.id.nav_schedule);
                        } catch (JSONException e) {
                            Log.d("debug", "Exception");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("debug", error.toString());
                Log.d("debug", "Response have failure");
                progress.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> para = new HashMap<String, String>();
                para.put("username", username);
                para.put("password", password);
                return para;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
}