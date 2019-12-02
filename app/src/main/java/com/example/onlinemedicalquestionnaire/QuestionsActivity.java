package com.example.onlinemedicalquestionnaire;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class QuestionsActivity extends AppCompatActivity {

    int size;
    int curr_question = 0;
    int curr_quetion_display;
    int curr_answer = -1;
    TextView question_title;
    TextView question_body;

    String phone_number;
    String URL = "http://212.179.205.15/shiba/patient/";//0508881919

    RadioGroup answer_group;        // Quality
    RadioGroup answer_group_bin;    // Binary
    NumberPicker numberPicker;      // Quantity

    ArrayList<Question> questions;
    ArrayList<Answer> answers;
    ArrayList<RadioButton> radioButtons = new ArrayList<>();       // Quality
    ArrayList<RadioButton> radioButtonsBin = new ArrayList<>();    // Binary

    Button backBtn;
    Button nextBtn;
    ScrollView scrollView;

    LinearLayout quality_layout;
    LinearLayout binary_layout;
    LinearLayout quantity_layout;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.questions_activity);

        sp = getSharedPreferences("sp", MODE_PRIVATE);
        phone_number = sp.getString("phone_number","");
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        load_questions(phone_number);

        quality_layout = findViewById(R.id.quality_layout);
        binary_layout = findViewById(R.id.binary_layout);
        quantity_layout = findViewById(R.id.quantity_layout);


        question_title = findViewById(R.id.question_title);
        question_body = findViewById(R.id.question_body);


        numberPicker = findViewById(R.id.num_pick);

        // Quality
        answer_group = findViewById(R.id.answer_group);
        RadioButton answer0_radio = findViewById(R.id.answer0_radio);
        RadioButton answer1_radio = findViewById(R.id.answer1_radio);
        RadioButton answer2_radio = findViewById(R.id.answer2_radio);
        RadioButton answer3_radio = findViewById(R.id.answer3_radio);
        RadioButton answer4_radio = findViewById(R.id.answer4_radio);

        radioButtons.add(answer0_radio);
        radioButtons.add(answer1_radio);
        radioButtons.add(answer2_radio);
        radioButtons.add(answer3_radio);
        radioButtons.add(answer4_radio);

        // Binary
        answer_group_bin = findViewById(R.id.answer_group_bin);
        RadioButton answer0_radio_bin = findViewById(R.id.answer0_radio_bin);
        RadioButton answer1_radio_bin = findViewById(R.id.answer1_radio_bin);

        radioButtonsBin.add(answer0_radio_bin);
        radioButtonsBin.add(answer1_radio_bin);

        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);
        scrollView = findViewById(R.id.scrollView);


        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);


        qualityRadiosChanger qualityRadiosChanger = new qualityRadiosChanger();
        final binaryRadiosChanger binaryRadiosChanger = new binaryRadiosChanger();

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                curr_answer = numberPicker.getValue();
            }
        });

        answer_group_bin.setOnCheckedChangeListener(binaryRadiosChanger);
        answer_group.setOnCheckedChangeListener(qualityRadiosChanger);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curr_answer != -1)
                {
                    answers.get(curr_question).setResult(curr_answer);
                    answers.get(curr_question).setId(questions.get(curr_question).getId());
                    answers.get(curr_question).setType(questions.get(curr_question).getType());
                    setPrevQuestion();
                }
                else {
                    setPrevQuestion();
                }
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (curr_answer == -1) {
                        didntChoose();
                    } else {
                        answers.get(curr_question).setResult(curr_answer);
                        answers.get(curr_question).setId(questions.get(curr_question).getId());
                        answers.get(curr_question).setType(questions.get(curr_question).getType());
                        setNextQuestion();
                }
            }
        });
    }

    private void load_questions(String phone_number) {

        questions.clear();
        RequestQueue queue = Volley.newRequestQueue(QuestionsActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL + phone_number, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray arr = response.getJSONArray("QuestionArray");
                    for (int i = 0; i < arr.length(); i++)
                    {
                        JSONObject jsonObject = arr.getJSONObject(i);
                        String id = jsonObject.getString("_id");
                        String question_txt = jsonObject.getString("text");
                        int type = jsonObject.getInt("questionType");
                        if (type == 0)
                        {
                            int min = jsonObject.getInt("min");
                            int max = jsonObject.getInt("max");
                            questions.add(new Question(type, min, max, question_txt, id));
                        }
                        else {
                            questions.add(new Question(id, type, question_txt));
                        }
                    }

                    question_body.setText(questions.get(curr_question).text);
                    size = questions.size();
                    curr_quetion_display = curr_question + 1;
                    question_title.setText(getString(R.string.question) +" "+ curr_quetion_display + getString(R.string.from) + size);
                    layoutSwitch();
                    answers_initialization();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("GETERROR", error.toString());
                Toast.makeText(QuestionsActivity.this, getString(R.string.server_error_please_try_later), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(jsonObjectRequest);
        queue.start();
    }

    private void answers_initialization() {
        for (int i = 0; i < questions.size(); i++) {
            String id = questions.get(i).id;
            int type = questions.get(i).type;
            answers.add(new Answer(id, type, -1));
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void setPrevQuestion() {
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        if (curr_question == 0) // First question
            firstQuestion();
        else {
            curr_question--;
            curr_quetion_display = curr_question + 1;
            layoutSwitch();
            question_title.setText(getString(R.string.question) + " "+ curr_quetion_display + getString(R.string.from) + size);
            question_body.setText(questions.get(curr_question).text);

            // Load answer:
            int typeOfQuestion = questions.get(curr_question).type;
            int loaded_answer = answers.get(curr_question).getResult();

            if (typeOfQuestion == 0) { // Quantity: Number Picker
                numberPicker.setMinValue(questions.get(curr_question).getMin());
                numberPicker.setMaxValue(questions.get(curr_question).getMax());
                numberPicker.setValue(loaded_answer);
            } else { // Binary or Quality
                resetBackground();
                changeRadioBackground(loaded_answer, typeOfQuestion);
                curr_answer = loaded_answer;
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void setNextQuestion() {
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        if (curr_question == questions.size() - 1) // Last question
            lastQuestion();
        else {
            curr_question++;
            curr_quetion_display = curr_question + 1;
            layoutSwitch();
            question_title.setText(getString(R.string.question) + " "+ curr_quetion_display + getString(R.string.from) + size);
            question_body.setText(questions.get(curr_question).text);

            // Load answer
            int typeOfQuestion = questions.get(curr_question).type;
            int loaded_answer = answers.get(curr_question).getResult();
            if (loaded_answer != -1) {  // next after back
                if (typeOfQuestion == 0) { // Quantity: Number Picker
                    numberPicker.setMinValue(questions.get(curr_question).getMin());
                    numberPicker.setMaxValue(questions.get(curr_question).getMax());
                    numberPicker.setValue(loaded_answer);
                } else {
                    curr_answer = loaded_answer;
                    resetBackground();
                    changeRadioBackground(curr_answer, typeOfQuestion);
                }
            } else {    // next at the first time
                answer_group_bin.clearCheck();
                answer_group.clearCheck();
                numberPicker.setValue(0);
                resetBackground();
                curr_answer = -1;
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void firstQuestion() {
        final AlertDialog dialog = new AlertDialog.Builder(QuestionsActivity.this).create();
        final View dialogView = getLayoutInflater().inflate(R.layout.exit_questionnaire_dialog, null);
        TextView exit_tv = dialogView.findViewById(R.id.exit_tv);
        TextView exit_tv2 = dialogView.findViewById(R.id.exit_tv2);
        Button finish_btn = dialogView.findViewById(R.id.finish_btn);
        Button continueBtn = dialogView.findViewById(R.id.continue_btn);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setView(dialogView);
        dialog.setCanceledOnTouchOutside(false);


        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                // Load first answer:
                int typeOfQuestion = questions.get(curr_question).type;
                int loaded_answer = answers.get(curr_question).getResult();

                if (typeOfQuestion == 0) { // Quantity: Number Picker
                    numberPicker.setMinValue(questions.get(curr_question).getMin());
                    numberPicker.setMaxValue(questions.get(curr_question).getMax());
                    numberPicker.setValue(loaded_answer);
                } else {
                    resetBackground();
                    changeRadioBackground(loaded_answer, typeOfQuestion);
                    curr_answer = loaded_answer;
                }
            }
        });

        dialog.show();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void lastQuestion() {
        final AlertDialog dialog = new AlertDialog.Builder(QuestionsActivity.this).create();
        final View dialogView = getLayoutInflater().inflate(R.layout.finish_dialog, null);
        TextView finish_tv = dialogView.findViewById(R.id.finish_tv);
        Button send_btn = dialogView.findViewById(R.id.send_btn);
        Button back_btn = dialogView.findViewById(R.id.back_btn);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setView(dialogView);
        dialog.setCanceledOnTouchOutside(false);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    send_answers(answers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(QuestionsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                // Load first answer:
                int typeOfQuestion = questions.get(curr_question).type;
                int loaded_answer = answers.get(curr_question).getResult();

                if (typeOfQuestion == 0) { // Quantity: Number Picker
                    numberPicker.setMinValue(questions.get(curr_question).getMin());
                    numberPicker.setMaxValue(questions.get(curr_question).getMax());
                    numberPicker.setValue(loaded_answer);
                } else {
                    resetBackground();
                    changeRadioBackground(loaded_answer, typeOfQuestion);
                    curr_answer = loaded_answer;
                }
            }
        });

        dialog.show();
    }

    private void send_answers(ArrayList<Answer> answers) throws JSONException {

        final JSONObject objectJson = new JSONObject();
        JSONArray arr = new JSONArray();

        for (int i = 0 ; i < answers.size(); i++)
        {
            JSONObject answer = new JSONObject();
            answer.put("questionId", answers.get(i).getId());
            answer.put("answer", answers.get(i).getResult());
            answer.put("questionType", answers.get(i).getType());

            arr.put(i, answer);
        }

        objectJson.put("arr", arr);

        RequestQueue queue = Volley.newRequestQueue(QuestionsActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL + phone_number, objectJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(QuestionsActivity.this, getString(R.string.successfully_sent), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(QuestionsActivity.this, getString(R.string.server_error_please_try_later), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(request);
        queue.start();

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void layoutSwitch() {

        if (questions.get(curr_question).type == 0) { // Quantity
            quantity_layout.setVisibility(View.VISIBLE);
            binary_layout.setVisibility(View.GONE);
            quality_layout.setVisibility(View.GONE);
            numberPicker.setMinValue(questions.get(curr_question).getMin());
            numberPicker.setMaxValue(questions.get(curr_question).getMax());

        } else if (questions.get(curr_question).type == 1) {// Binary
            binary_layout.setVisibility(View.VISIBLE);
            quantity_layout.setVisibility(View.GONE);
            quality_layout.setVisibility(View.GONE);

        } else {  // Quality
            quality_layout.setVisibility(View.VISIBLE);
            quantity_layout.setVisibility(View.GONE);
            binary_layout.setVisibility(View.GONE);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Listener to Binary layout
    class binaryRadiosChanger implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) { // i = 12753217124342;

            for (int j = 0; j < radioButtonsBin.size(); j++) {
                if (radioButtonsBin.get(j).getId() == i) {
                    curr_answer = j;
                    radioButtonsBin.get(j).setBackgroundResource(R.drawable.sel_answer_shape);
                } else {
                    radioButtonsBin.get(j).setBackgroundResource(R.drawable.question_shape);
                }
            }
        }
    }

    // Listener to Quality layout
    class qualityRadiosChanger implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) { // i = 12753217124342;

            for (int j = 0; j < radioButtons.size(); j++) {
                if (radioButtons.get(j).getId() == i) {
                    curr_answer = j;
                    radioButtons.get(j).setBackgroundResource(R.drawable.sel_answer_shape);
                } else {
                    radioButtons.get(j).setBackgroundResource(R.drawable.question_shape);
                }
            }
        }

    }
    // Listener to Quantity layout(Number Picker) it's anonymous

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void resetBackground() {

        // Reset Binary RadioBtns
        for (int i = 0; i < radioButtonsBin.size(); i++)
            radioButtonsBin.get(i).setBackgroundResource(R.drawable.question_shape);

        // Reset Quality RadioBtns
        for (int i = 0; i < radioButtons.size(); i++) {
            radioButtons.get(i).setBackgroundResource(R.drawable.question_shape);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void changeRadioBackground(int i, int typeOfQuestion) { // i = clicked Item ;
        if (typeOfQuestion == 2) { // Quantity
            radioButtons.get(i).setBackgroundResource(R.drawable.sel_answer_shape);
            radioButtons.get(i).setChecked(true);

        } else { // Binary
            radioButtonsBin.get(i).setBackgroundResource(R.drawable.sel_answer_shape);
            radioButtonsBin.get(i).setChecked(true);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void didntChoose() {
        final AlertDialog dialog = new AlertDialog.Builder(QuestionsActivity.this).create();
        final View dialogView = getLayoutInflater().inflate(R.layout.didnt_choose_dialog, null);
        Button ok_btn = dialogView.findViewById(R.id.ok_btn);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setView(dialogView);
        dialog.setCanceledOnTouchOutside(false);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed() {
        return;
    }
}