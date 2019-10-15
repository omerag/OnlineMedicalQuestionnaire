package com.example.onlinemedicalquestionnaire;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AdapterViewFlipper AVF;
    String patientName;
    TextView userNameTv;
    TextView dateTv;
    SharedPreferences sp;
    Button startBtn;
    String phone_number;
    String URL = "http://212.179.205.15/shiba/name/";//0508881919
    int start_hour, end_hour, curr_time;
    boolean isAnswer;

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


    }

    public void getUser() {

        sp = getSharedPreferences("sp", MODE_PRIVATE);

        if (sp.contains("name")) {
            patientName = sp.getString("name", "");
            phone_number = sp.getString("phone_number","");
            userNameTv.setText(userNameTv.getText() + " " + patientName);
            if (!isAnswered())
            {
                startBtn.setEnabled(false);
                startBtn.setText("בוצע");
                Toast.makeText(MainActivity.this, "מילאת את השאלון לתאריך זה", Toast.LENGTH_LONG).show();
            }
            else {
                startBtn.setEnabled(true);
                startBtn.setText("תחילת שאלון");
                check_Hours();
            }

        } else {
            // Login dialog:
            final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
            final View dialogView = getLayoutInflater().inflate(R.layout.login_dialog, null);
            TextView login_tv = dialogView.findViewById(R.id.login_tv);
            final TextView phone_et = dialogView.findViewById(R.id.phone_et);
            Button login_btn = dialogView.findViewById(R.id.login_btn);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setView(dialogView);
            dialog.setCanceledOnTouchOutside(false);

            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //// if exist ......
                    phone_number = phone_et.getText().toString();
                    if (phone_number.equals(""))
                    {
                        Toast.makeText(MainActivity.this, "Enter number", Toast.LENGTH_LONG).show();
                        return;
                    }
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL + phone_number, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String name = response.getString("name");
                                if (name.equals("Not Found")) {
                                    getUser();
                                    Toast.makeText(MainActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    userNameTv.setText(name);
                                    sp.edit().putString("name",name).commit();
                                    sp.edit().putString("phone_number", phone_number).commit();
                                    check_Hours();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("get_error", error.getMessage());
                            Toast.makeText(MainActivity.this, "GET ERROR", Toast.LENGTH_SHORT).show();
                        }
                    });

                    queue.add(objectRequest);
                    //queue.start();

                    dialog.dismiss();
                }
            });
            dialog.show();
        }
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
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL + phone_number, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    start_hour = response.getInt("startHour");
                    end_hour = response.getInt("endHour");

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("HH");
                    String strDate = mdformat.format(calendar.getTime());

                    curr_time = Integer.parseInt(strDate);

                    if (curr_time >= start_hour && curr_time <= end_hour)
                    {
                        startBtn.setEnabled(true);
                    }
                    else {
                        startBtn.setEnabled(false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("get_error", error.getMessage());
                Toast.makeText(MainActivity.this, "GET ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(objectRequest);
        //queue.start();
    }

    /*private boolean isAnswered()
    {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        StringRequest request = new StringRequest(URL + phone_number, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("false"))
                {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }*/
}