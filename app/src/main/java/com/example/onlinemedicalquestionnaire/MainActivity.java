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

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AdapterViewFlipper AVF;
    String patientName;
    TextView userNameTv;
    SharedPreferences sp;
    Button startBtn;
    String phone_number;

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
        userNameTv = findViewById(R.id.user_name_tv);
        getUser();

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                intent.putExtra("phone_number", phone_number);
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
            userNameTv.setText(userNameTv.getText() + " " + patientName);
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
                    sp.edit().putString("name", "שמעון").commit();
                    userNameTv.setText(userNameTv.getText() + " שמעון");
                    phone_number = phone_et.getText().toString();
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
}
