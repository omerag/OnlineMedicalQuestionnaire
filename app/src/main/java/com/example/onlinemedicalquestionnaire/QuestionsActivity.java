package com.example.onlinemedicalquestionnaire;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class QuestionsActivity extends AppCompatActivity {

    int size;
    int curr_quetion_display;
    int curr_answer;

    int resultOfNumberPicker = -1;


    int currentQuestion = 0;

    TextView question_title;
    TextView question_body;

    RadioGroup answer_group;
//    RadioButton answer0_radio;
//    RadioButton answer1_radio;
//    RadioButton answer2_radio;
//    RadioButton answer3_radio;
//    RadioButton answer4_radio;
//    RadioButton answer5_radio;



    RadioButton answer0_radio_bin;
    RadioButton answer1_radio_bin;

    NumberPicker numberPicker;

    ArrayList<Question> questions;
    ArrayList<Answer> answers;
    ArrayList<RadioButton> radioButtons = new ArrayList<>();

    int curr_question = 0;

    Button backBtn;
    Button continueBtn;
    ScrollView scrollView;
    LinearLayout linearLayoutQuality;
    LinearLayout linearLayoutQuantity;
    LinearLayout linearLayoutBinary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.questions_activity);

        linearLayoutBinary = findViewById(R.id.ll_binary);
        linearLayoutQuantity = findViewById(R.id.ll_quntity);
        linearLayoutQuality = findViewById(R.id.ll_quality);

        question_title = findViewById(R.id.question_title);
        question_body = findViewById(R.id.question_body);

        answer_group = findViewById(R.id.answer_group);
        RadioButton answer0_radio = findViewById(R.id.answer0_radio);
        RadioButton answer1_radio = findViewById(R.id.answer1_radio);
        RadioButton answer2_radio = findViewById(R.id.answer2_radio);
        RadioButton answer3_radio = findViewById(R.id.answer3_radio);
        RadioButton answer4_radio = findViewById(R.id.answer4_radio);
        RadioButton answer5_radio = findViewById(R.id.answer5_radio);

        numberPicker = findViewById(R.id.num_pick);

        radioButtons.add(answer0_radio);
        radioButtons.add(answer1_radio);
        radioButtons.add(answer2_radio);
        radioButtons.add(answer3_radio);
        radioButtons.add(answer4_radio);
        radioButtons.add(answer5_radio);

        answer0_radio_bin = findViewById(R.id.answer0_radio_bin);
        answer1_radio_bin = findViewById(R.id.answer1_radio_bin);


        backBtn = findViewById(R.id.backBtn);
        continueBtn = findViewById(R.id.continueBtn);
        scrollView = findViewById(R.id.scrollView);


        NextAndBack nextAndBack = new NextAndBack();
        backBtn.setOnClickListener(nextAndBack);
        continueBtn.setOnClickListener(nextAndBack);

        final ArrayList<String> questions1 = new ArrayList<>();
        questions1.add("כמה פעמים במהלך היום הרגשת שנשאר לך שתן לאחר השתנה?");
        questions1.add("כמה פעמים במהלך היום היית צריכ/ה להשתין כל שעתיים או פחות?");
        questions1.add("כמה פעמים במהלך היום חשת בהפסקות במהלך ההשתנה?");
        questions1.add("כמה פעמים במהלך היום היה לך קשה להתאפק?");
        questions1.add("כמה פעמים במהלך היום שמת לב לזרימת שתן חלשה?");
        questions1.add("כמה פעמים במהלך היום היה לך צורך ללחוץ או להתאמץ כדי להתחיל להשתין?");
        questions1.add("כמה פעמים בלילה האחרון קמת כדי להשתין?");


        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);

        //Log.i("hola" , questions.get(1));

        questions = new ArrayList<>();



        // Random random = new Random();
        for (int i = 0; i < 7; i++) {
            //int r = random.nextInt(3);
            questions.add(new Question(i, i % 3, questions1.get(i)));
        }
        question_body.setText(questions.get(currentQuestion).text);
        size = questions.size() - 1;
        curr_quetion_display = currentQuestion + 1;

        question_title.setText("שאלה " + curr_quetion_display + " מתוך " + size);

        layoutSwitch();

        answers = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            int id = questions.get(i).id, type = questions.get(i).type;
            answers.add(new Answer(id, type, -1));
        }



        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
               // resultOfNumberPicker = i;
                answers.get(curr_answer).setResult(i);
            }
        });

        RadiosChanger radiosChanger = new RadiosChanger();
        answer_group.setOnCheckedChangeListener(radiosChanger);

    }

    class NextAndBack implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (view == continueBtn && answers.get(curr_question).result == -1) { // open dialog Did not choose Option



            } else {


                if (view == continueBtn && currentQuestion == questions.size() - 1) { // sending The

                } else if (view == backBtn && currentQuestion == 0) { // Open Dialog Are your Sure...

                } else {

                    // Saving
                    if (questions.get(currentQuestion).type == 0) { // Number Picker It is.
                     //   answers.get(currentQuestion).setResult(resultOfNumberPicker);
                    } else {
                        answers.get(currentQuestion).setResult(curr_answer); // curr_answer is between 0 to 5
                    }

                    if (view == backBtn) {
                        currentQuestion--;

                    } else {
                        currentQuestion++;
                    }
                    // Load  Ui
                    layoutSwitch();

                    //Put The text Body
                    question_body.setText(questions.get(currentQuestion).text);

                    // Load Answer
                    int typeOfQuestion = questions.get(currentQuestion).type;
                    int num = answers.get(currentQuestion).getResult();

                    if (typeOfQuestion == 0) { // Number Picker It is. Quantity
                        if (num != -1) {
                            numberPicker.setValue(num); // Scrolling to the Choosen answer
                        }
                    } else {
                        if (num != -1) {
                            resetBackground();
                            changeRadioBackground(num, typeOfQuestion);

                        }
                    }
                }
            }
        }
    }

    void resetBackground() {
        // Reset Binary RadiosBtn
        answer1_radio_bin.setBackgroundResource(R.drawable.question_shape);
        answer0_radio_bin.setBackgroundResource(R.drawable.question_shape);

        // Reset Quality RadiosBtn
        for (int i = 0; i < radioButtons.size(); i++) {
            radioButtons.get(i).setBackgroundResource(R.drawable.question_shape);
        }
    }

    void changeRadioBackground(int i, int typeOfQuestion) { // i = clicked Item ;
        if (typeOfQuestion == 2) { // Quantity
            radioButtons.get(i).setBackgroundResource(R.drawable.sel_answer_shape);
            radioButtons.get(i).setChecked(true);

        } else { // Binary
            switch (i) {
                case 0:
                    answer0_radio_bin.setBackgroundResource(R.drawable.sel_answer_shape);
                    answer0_radio_bin.setChecked(true);
                    break;
                case 1:
                    answer1_radio_bin.setChecked(true);
                    answer1_radio_bin.setBackgroundResource(R.drawable.sel_answer_shape);
                    break;
            }
        }
    }

    void layoutSwitch() {


        if (questions.get(currentQuestion).type == 0) {// כמותי
            linearLayoutQuantity.setVisibility(View.VISIBLE);

            linearLayoutBinary.setVisibility(View.GONE);
            linearLayoutQuality.setVisibility(View.GONE);
        } else if (questions.get(currentQuestion).type == 1) { // Binary
            linearLayoutBinary.setVisibility(View.VISIBLE);

            linearLayoutQuantity.setVisibility(View.GONE);
            linearLayoutQuality.setVisibility(View.GONE);
        } else {  // Quality
            linearLayoutQuality.setVisibility(View.VISIBLE);

            linearLayoutQuantity.setVisibility(View.GONE);
            linearLayoutBinary.setVisibility(View.GONE);
        }
    }

    // Listener To Quantity Layout Its Anonymous shhhhh....


    // Listener To Binary Layout
    class RadiosBinary implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            curr_answer = i;
            if (i == R.id.answer0_radio_bin) {
                answer0_radio_bin.setBackgroundResource(R.drawable.sel_answer_shape);
                answer1_radio_bin.setBackgroundResource(R.drawable.question_shape);
            } else { // i == 1
                answer0_radio_bin.setBackgroundResource(R.drawable.question_shape);
                answer1_radio_bin.setBackgroundResource(R.drawable.sel_answer_shape);
            }
        }
    }

    // Listener To The Quality Layout
    class RadiosChanger implements RadioGroup.OnCheckedChangeListener {
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


}


//        for( ; currentQuestion < questions.size() ; currentQuestion++){
//            if(questions.get(currentQuestion).type == 0){// כמותי
//                linearLayoutQuantity.setVisibility(View.VISIBLE);
//
//                linearLayoutBinary.setVisibility(View.GONE);
//                linearLayoutQuality.setVisibility(View.GONE);
//            }else if(questions.get(currentQuestion).type == 1){ // Binary
//                linearLayoutBinary.setVisibility(View.VISIBLE);
//
//                linearLayoutQuantity.setVisibility(View.GONE);
//                linearLayoutQuality.setVisibility(View.GONE);
//            }else{  // Quality
//                linearLayoutQuality.setVisibility(View.VISIBLE);
//
//                linearLayoutQuantity.setVisibility(View.GONE);
//                linearLayoutBinary.setVisibility(View.GONE);
//
//
//
//            }
//        }

//        answer1_radio.setBackgroundResource(R.drawable.question_shape);
//        answer2_radio.setBackgroundResource(R.drawable.question_shape);
//        answer3_radio.setBackgroundResource(R.drawable.question_shape);
//        answer4_radio.setBackgroundResource(R.drawable.question_shape);
//        answer5_radio.setBackgroundResource(R.drawable.question_shape);
//        answer0_radio.setBackgroundResource(R.drawable.question_shape);

//
//      switch (i){
//
//              case R.id.answer0_radio:{
//              answer1_radio.setBackgroundResource(R.drawable.question_shape);
//              answer2_radio.setBackgroundResource(R.drawable.question_shape);
//              answer3_radio.setBackgroundResource(R.drawable.question_shape);
//              answer4_radio.setBackgroundResource(R.drawable.question_shape);
//              answer5_radio.setBackgroundResource(R.drawable.question_shape);
//              answer0_radio.setBackgroundResource(R.drawable.sel_answer_shape);
//
//              // answers.set(curr_question, 0);
//              break;
//              }
//
//              case R.id.answer1_radio:{
//              answer0_radio.setBackgroundResource(R.drawable.question_shape);
//              answer2_radio.setBackgroundResource(R.drawable.question_shape);
//              answer3_radio.setBackgroundResource(R.drawable.question_shape);
//              answer4_radio.setBackgroundResource(R.drawable.question_shape);
//              answer5_radio.setBackgroundResource(R.drawable.question_shape);
//              answer1_radio.setBackgroundResource(R.drawable.sel_answer_shape);
//              //answers.set(curr_question, 1);
//              break;
//              }
//
//              case R.id.answer2_radio:{
//              answer0_radio.setBackgroundResource(R.drawable.question_shape);
//              answer1_radio.setBackgroundResource(R.drawable.question_shape);
//              answer3_radio.setBackgroundResource(R.drawable.question_shape);
//              answer4_radio.setBackgroundResource(R.drawable.question_shape);
//              answer5_radio.setBackgroundResource(R.drawable.question_shape);
//              answer2_radio.setBackgroundResource(R.drawable.sel_answer_shape);
//              //  answers.set(curr_question, 2);
//              break;
//              }
//
//              case R.id.answer3_radio:{
//              answer0_radio.setBackgroundResource(R.drawable.question_shape);
//              answer1_radio.setBackgroundResource(R.drawable.question_shape);
//              answer2_radio.setBackgroundResource(R.drawable.question_shape);
//              answer4_radio.setBackgroundResource(R.drawable.question_shape);
//              answer5_radio.setBackgroundResource(R.drawable.question_shape);
//              answer3_radio.setBackgroundResource(R.drawable.sel_answer_shape);
//              //  answers.set(curr_question, 3);
//              break;
//              }
//
//              case R.id.answer4_radio:{
//              answer0_radio.setBackgroundResource(R.drawable.question_shape);
//              answer1_radio.setBackgroundResource(R.drawable.question_shape);
//              answer2_radio.setBackgroundResource(R.drawable.question_shape);
//              answer3_radio.setBackgroundResource(R.drawable.question_shape);
//              answer5_radio.setBackgroundResource(R.drawable.question_shape);
//              answer4_radio.setBackgroundResource(R.drawable.sel_answer_shape);
//              //  answers.set(curr_question, 4);
//              break;
//              }
//
//              case R.id.answer5_radio:{
//              answer0_radio.setBackgroundResource(R.drawable.question_shape);
//              answer1_radio.setBackgroundResource(R.drawable.question_shape);
//              answer2_radio.setBackgroundResource(R.drawable.question_shape);
//              answer3_radio.setBackgroundResource(R.drawable.question_shape);
//              answer4_radio.setBackgroundResource(R.drawable.question_shape);
//              answer5_radio.setBackgroundResource(R.drawable.sel_answer_shape);
//              //  answers.set(curr_question, 5);
//              break;
//              }
//
//              }