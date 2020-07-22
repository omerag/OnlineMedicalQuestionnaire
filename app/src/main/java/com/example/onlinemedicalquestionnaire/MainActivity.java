package com.example.onlinemedicalquestionnaire;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private AdapterViewFlipper AVF;
    String patientName;
    TextView userNameTv;
    TextView dateTv;
    SharedPreferences sp;
    Button startBtn;
    String phone_number;
    String password;
    String URL = "http://185.60.170.80:8830/api/patient/details/";//<phone number> // http://185.60.170.80:8830/
    String URL_AUTH = "http://185.60.170.80:8830/api/patient/sms/";
    int start_hour, end_hour, curr_time;
    boolean isAnswer = false;
    NotificationManager manager;
    final int NOTIF_ID = 1;

    int[] IMAGES = {
            R.drawable.picture1,
            R.drawable.picture2,
            R.drawable.picture3,
            R.drawable.picture4,
            R.drawable.picture5,
            R.drawable.picture6,
            R.drawable.picture7
    };

    String[] NAMES = {
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.startBtn);
        startBtn.setEnabled(false);
        userNameTv = findViewById(R.id.user_name_tv);
        getUser();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        dateTv = findViewById(R.id.date_tv);
        Date today = new Date();
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateTv.setText(day+"/"+month+"/"+year);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                startActivity(intent);
                finish();
            }
        });


        AVF = (AdapterViewFlipper) findViewById(R.id.AVF);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), NAMES, IMAGES);
        AVF.setAdapter(customAdapter);
        AVF.setFlipInterval(2600);
        AVF.setAutoStart(true);



        if (getIntent().getBooleanExtra("from_notif",false))
        {
            manager.cancelAll();
        }
        else {
            onTimeSet(start_hour);
        }


    }

    public void getUser() {

        sp = getSharedPreferences("sp", MODE_PRIVATE);
        //sp.edit().clear().apply();
        if (sp.contains("name")) {
            patientName = sp.getString("name", "");
            phone_number = sp.getString("phone_number","");
            password = sp.getString("password","");
            start_hour = sp.getInt("startHour",19);
            userNameTv.setText(userNameTv.getText() + " " + patientName);

            check_Hours();

        } else {

            userAuth();

        }
    }

    private void userAuth(){
        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
        final View dialogView = getLayoutInflater().inflate(R.layout.login_dialog, null);
        TextView login_tv = dialogView.findViewById(R.id.login_tv);
        final TextView phone_et = dialogView.findViewById(R.id.phone_et);
        final TextView notValid_tv = dialogView.findViewById(R.id.notValid_tv);
        final Button login_btn = dialogView.findViewById(R.id.login_btn);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setView(dialogView);
        dialog.setCanceledOnTouchOutside(false);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phone_number = phone_et.getText().toString();
                phone_et.setText(null);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(URL_AUTH + phone_number, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        phone_et.setHint(getString(R.string.enter_pass_from_sms));

                        //wait for user to enter sms
                        login_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                password = phone_et.getText().toString();
                                RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
                                StringRequest stringRequest1 = new StringRequest(URL_AUTH + phone_number + "/" + password, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //access granted

                                            try {
                                                JSONObject rootObject = new JSONObject(response);
                                                String name = rootObject.getString("name");
                                                userNameTv.setText(name);
                                                sp.edit().putString("name",name).apply();
                                                sp.edit().putString("phone_number", phone_number).apply();
                                                sp.edit().putString("password", password).apply();
                                                check_Hours();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        dialog.dismiss();
                                    }
                                }, new ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MainActivity.this, getString(R.string.wrong_pass), Toast.LENGTH_LONG).show();

                                    }
                                });

                                queue1.add(stringRequest1);
                            }
                        });

                    }
                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, getString(R.string.number_does_not_exist_in_the_system_please_try_again), Toast.LENGTH_LONG).show();

                    }
                });

                queue.add(stringRequest);
            }
        });


        dialog.show();
    }

    class CustomAdapter extends BaseAdapter {
        Context context;
        int[] images;
        String[] names;
        LayoutInflater inflater;

        public CustomAdapter(Context applicationContext, String[] names, int[] images) {
            this.context = applicationContext;
            this.images = images;
            this.names = names;
            inflater = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            view = inflater.inflate(R.layout.pic_list, null);
            TextView name = (TextView) view.findViewById(R.id.name);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            name.setText(names[position]);
            image.setImageResource(images[position]);
            return view;
        }
    }

    private void check_Hours(){

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(URL + phone_number, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("false")) {
                    startBtn.setEnabled(false);
                    startBtn.setText(getString(R.string.done));
                    Toast.makeText(MainActivity.this, getString(R.string.you_filled_out_the_questionnaire_for_this_date), Toast.LENGTH_LONG).show();
                }
                else {

                    try {
                        JSONObject rootObject = new JSONObject(response);
                        start_hour = rootObject.getInt("startHour");
                        end_hour = rootObject.getInt("endHour");
                        sp.edit().putInt("start_hour", start_hour).apply();

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat mdformat = new SimpleDateFormat("HH");
                        String strDate = mdformat.format(calendar.getTime());

                        curr_time = Integer.parseInt(strDate);

                        if (curr_time >= start_hour && curr_time <= end_hour) {
                            startBtn.setEnabled(true);
                        } else {
                            startBtn.setEnabled(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void onTimeSet(int hour)
    {
        Calendar now = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (now.after(calendar))
        {
            calendar.add(Calendar.DATE,1);
        }

        starAlarm(calendar);
    }

    private void starAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(MainActivity.this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,1, intent,PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}


 /*
            // Login dialog:
            final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
            final View dialogView = getLayoutInflater().inflate(R.layout.login_dialog, null);
            TextView login_tv = dialogView.findViewById(R.id.login_tv);
            final TextView phone_et = dialogView.findViewById(R.id.phone_et);
            final TextView notValid_tv = dialogView.findViewById(R.id.notValid_tv);
            Button login_btn = dialogView.findViewById(R.id.login_btn);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setView(dialogView);
            dialog.setCanceledOnTouchOutside(false);

            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    phone_number = phone_et.getText().toString();
                    if (phone_number.equals(""))
                    {
                        notValid_tv.setText(getString(R.string.no_input_try_again));
                        Toast.makeText(MainActivity.this, getString(R.string.no_input_try_again), Toast.LENGTH_LONG).show();
                        return;
                    }
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    StringRequest stringRequest = new StringRequest(URL + phone_number, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            if (response.equals("Not Found"))
                            {
                                getUser();
                                Toast.makeText(MainActivity.this, getString(R.string.number_does_not_exist_in_the_system_please_try_again), Toast.LENGTH_LONG).show();
                                notValid_tv.setText(getString(R.string.number_does_not_exist_in_the_system_please_try_again));
                            }
                            else {
                                try {
                                    JSONObject rootObject = new JSONObject(response);
                                    String name = rootObject.getString("name");
                                    userNameTv.setText(name);
                                    sp.edit().putString("name",name).commit();
                                    sp.edit().putString("phone_number", phone_number).commit();
                                    check_Hours();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    queue.add(stringRequest);
                    dialog.dismiss();
                }
            });
            dialog.show();
            */